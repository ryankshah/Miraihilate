package io.ryankshah.client.gui;

import io.ryankshah.client.User;
import io.ryankshah.util.database.DBHelper;
import io.ryankshah.util.gui.TextPrompt;
import io.ryankshah.util.resource.ResourceLoader;
import org.mindrot.jbcrypt.BCrypt;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;

public class EditProfile extends JFrame implements ActionListener
{
    public static EditProfile INSTANCE;
    private final int WIDTH = 400, HEIGHT = 350;
    private User user;

    private JPasswordField newPasswordField, newPasswordField2;
    private JButton changePasswordButton;

    public EditProfile(User user) {
        this.user = user;
        INSTANCE= this;

        setTitle("Edit Profile");
        setSize(new Dimension(WIDTH, HEIGHT));

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(null);

        addComponents();

        setLocationRelativeTo(null);
    }

    private void addComponents() {
        ImageIcon img = ResourceLoader.getImageIconResource("user-icon-placeholder.png");
        JLabel userImage = new JLabel(img);
        userImage.setBounds(10, 10, 128, 128);
        add(userImage);

        JLabel name = new JLabel(user.getFullName());
        name.setBounds(180, 40, 180, 30);
        add(name);

        JLabel email = new JLabel(user.getEmail());
        email.setBounds(180, 70, 180, 30);
        add(email);

        JPanel changePasswordPanel = new JPanel();
            TitledBorder border = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Change Password");
            changePasswordPanel.setBorder(border);
            changePasswordPanel.setLayout(new GridLayout(3, 1));
            changePasswordPanel.setBounds(20, 150, WIDTH - 30, 160);

            newPasswordField = new JPasswordField();
            changePasswordPanel.add(newPasswordField);
            TextPrompt newPasswordPrompt = new TextPrompt("New Password", newPasswordField);

            newPasswordField2 = new JPasswordField();
            changePasswordPanel.add(newPasswordField2);
            TextPrompt newPasswordPrompt2 = new TextPrompt("Re-enter Password", newPasswordField2);

            changePasswordButton = new JButton("Change Password");
            changePasswordButton.addActionListener(this);
            changePasswordPanel.add(changePasswordButton);
        add(changePasswordPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == changePasswordButton) {
            if(Arrays.equals(newPasswordField.getPassword(), newPasswordField2.getPassword())) {
                String hash = BCrypt.hashpw(new String(newPasswordField.getPassword()), BCrypt.gensalt());
                changePassword(hash);

                Object[] options = {"OK"};
                int result = JOptionPane.showOptionDialog(this,
                        "Password changed! You will now be logged out...","Password Changed",
                        JOptionPane.PLAIN_MESSAGE,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[0]);
                if(result == JOptionPane.OK_OPTION) {
                    System.exit(0);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Passwords do not match!");
            }
        }
    }

    private void changePassword(String hash) {
        Connection con = DBHelper.getDatabaseConnection();

        PreparedStatement stmt = null;
        try {
            String query = "UPDATE users SET password = ? WHERE uuid = ?";
            stmt = con.prepareStatement(query);
            stmt.setString(1, hash);
            stmt.setString(2, user.getUUID().toString());
            stmt.executeUpdate();

            // Closing up the connection
            if(stmt != null)
                stmt.close();
            if(con != null)
                con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}