package io.ryankshah;

import io.ryankshah.client.User;
import io.ryankshah.client.gui.QuickScanPanel;
import io.ryankshah.util.database.ImageLoader;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * Client interface class
 */
public class Client extends JFrame
{
    protected static final int WIDTH = 800, HEIGHT = 640;
    protected static final String VERSION = "0.0.1-ALPHA";

    private User user;

    private JButton editProfileButton, scanHistoryButton, performLastScanButton, userGuideButton, logoutButton;

    public Client(User user) {
        this.user = user;

        setTitle("Miraihilate Client " + VERSION);
        setSize(new Dimension(WIDTH, HEIGHT));

        setIconImage(ImageLoader.getImageIconResource("icon.png").getImage());


        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        initComponents();

        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }


    private void initComponents() {
        ImageIcon img = ImageLoader.getImageIconResource("logo.png");
        JLabel miraihilateLogo = new JLabel(img);
        miraihilateLogo.setBounds(10, 0, 300, 100);
        add(miraihilateLogo);

        //Add Client Information
        JLabel version = new JLabel("Version " + VERSION);
        version.setBounds(30 + (110/2), 110, 200, 30);
        add(version);
        JLabel userinfo = new JLabel(user.getFullName() + " - " + user.getEmail());
        userinfo.setBounds(50, 140, 240, 30);
        add(userinfo);

        // Add QuickScan Panel
        JPanel quickScanPanel = new QuickScanPanel();
        quickScanPanel.setBounds(10, 200, 300, HEIGHT - 233);
        add(quickScanPanel);

        // Add recent scan result
        JPanel scanResultPanel = new JPanel();
        TitledBorder resultBorder = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Recent Scan");
        scanResultPanel.setBorder(resultBorder);
        scanResultPanel.setBounds(360, 10, WIDTH - (WIDTH - 420), HEIGHT - 233);
        scanResultPanel.setLayout(new GridLayout(1, 2));
            JTextArea recentScanResult = new JTextArea();
            recentScanResult.setEditable(false);
            scanResultPanel.add(recentScanResult);
        add(scanResultPanel);

        // Add client utility buttons
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBounds(360, 30 + (HEIGHT - 233), WIDTH - (WIDTH - 420), 170);
        buttonsPanel.setLayout(new GridLayout(2, 3));
            editProfileButton = new JButton("Edit Profile");
            buttonsPanel.add(editProfileButton);
            scanHistoryButton = new JButton("Scan History");
            buttonsPanel.add(scanHistoryButton);
            performLastScanButton = new JButton("Perform Last Scan");
            buttonsPanel.add(performLastScanButton);
            userGuideButton = new JButton("User Guide");
            buttonsPanel.add(userGuideButton);
            logoutButton = new JButton("Logout");
            buttonsPanel.add(logoutButton);
        add(buttonsPanel);

    }
}