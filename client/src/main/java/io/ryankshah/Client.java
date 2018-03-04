package io.ryankshah;

import io.ryankshah.client.User;
import io.ryankshah.client.gui.QuickScanPanel;
import io.ryankshah.util.database.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Client interface class
 */
public class Client extends JFrame
{
    protected static final int WIDTH = 800, HEIGHT = 640;
    protected static final String VERSION = "0.0.1-ALPHA";

    private User user;

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
    }
}