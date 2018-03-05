package io.ryankshah;

import io.ryankshah.client.User;
import io.ryankshah.util.database.DBHelper;
import io.ryankshah.util.gui.TextPrompt;
import org.mindrot.jbcrypt.BCrypt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Login interface for accessing the Client interface
 */
public class Login extends JFrame implements ActionListener
{
    protected static final int WIDTH = 600, HEIGHT = 240;

    // Components
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public Login() {
        setTitle("Login");
        setSize(new Dimension(WIDTH, HEIGHT));

        //Image icon = Toolkit.getDefaultToolkit().getImage("res/img/icon.png");
        //setIconImage(icon);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        emailField = new JTextField();
        emailField.setBounds(WIDTH / 2 - 150, HEIGHT / 2 - 15 - 70, 300, 30);
        emailField.addActionListener(this);
        add(emailField);

        TextPrompt emailPrompt = new TextPrompt("Email Address", emailField);

        passwordField = new JPasswordField();
        passwordField.setBounds(WIDTH / 2 - 150, HEIGHT / 2 - 15 - 20, 300, 30);
        passwordField.addActionListener(this);
        add(passwordField);

        TextPrompt passwordPrompt = new TextPrompt("Password", passwordField);

        loginButton = new JButton("Login");
        loginButton.setBounds(WIDTH / 2 - 50, HEIGHT / 2 + 15, 100, 30);
        loginButton.addActionListener(this);
        add(loginButton);

        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        // Check if the login button was pressed, or enter/return
        // was pressed on an input field
        if (e.getSource() == loginButton
                || e.getSource() == emailField
                || e.getSource() == passwordField) {
            // Check if the input fields have a value
            if (emailField.getText() == null || emailField.getText().length() == 0
                    || passwordField.getPassword() == null || passwordField.getPassword().length == 0) {
                JOptionPane.showMessageDialog(null, "One or more fields were left blank!");
            } else {
                // TODO: Validate email format
                boolean validated = validateUser();
            }
        }
    }

    /**
     * Connect to the database and validate
     * the user's credentials
     * @return
     */
    private boolean validateUser() {
        Connection con = DBHelper.getDatabaseConnection();

        PreparedStatement stmt = null;
        try {
            String query = "SELECT * FROM users WHERE email = ?";
            stmt = con.prepareStatement(query);
            stmt.setString(1, emailField.getText());
            ResultSet results = stmt.executeQuery();

            //TODO: Check if email exists in database

            // While a result exists
            while(results.next()) {
                String dbHash = results.getString("password");
                boolean result = BCrypt.checkpw(new String(passwordField.getPassword()), dbHash);

                // Check if password matches DB
                if(result) {
                    // Retrieve the rest of the row results
                    String uuid = results.getString("uuid");
                    String firstName = results.getString("firstName");
                    String lastName = results.getString("lastName");
                    String email = results.getString("email");
                    int rank = results.getInt("uac_rank");

                    // Create new user object
                    User user = new User(UUID.fromString(uuid), firstName, lastName, email, rank);

                    // Dispose of login window and move to client window
                    new Client(user);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid email or password!");
                }
            }

            // Finished setting up user
            // Closing up the connection
            if(stmt != null)
                stmt.close();
            if(con != null)
                con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
