package io.ryankshah.client.gui;

import io.ryankshah.client.event.AdvancedScanHandler;
import io.ryankshah.util.gui.TextPrompt;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class AdvancedScan extends JFrame
{
    public static JTextField ipAddressField, cidrField;
    public static JCheckBox sshBox, telnetBox, changePasswordBox, notifyDeviceBox;

    public static JTextField timeoutField;
    public static JCheckBox executeMoreCommandsBox;
    public static JTextArea commandsBox;

    public static JButton advancedScanButton, helpButton;

    public static AdvancedScan INSTANCE;
    private AdvancedScanHandler asHandler;
    private final int WIDTH = 400, HEIGHT = 600;

    public AdvancedScan() {
        INSTANCE= this;
        asHandler = new AdvancedScanHandler();

        setTitle("Advanced Scan");
        setSize(new Dimension(WIDTH, HEIGHT));

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(null);

        //====== Scan Details Panel ======\\

        JPanel scanDetailsPanel = new JPanel();
            TitledBorder scanDetailsBorder = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Scan Details");
            scanDetailsPanel.setBorder(scanDetailsBorder);
            scanDetailsPanel.setLayout(new GridLayout(4, 1));
            scanDetailsPanel.setBounds(0, 0, WIDTH, 200);
            addScanDetailsComponents(scanDetailsPanel);
        add(scanDetailsPanel);

        //======  Advanced Options Panel ======\\

        JPanel advancedOptionsPanel = new JPanel();
            TitledBorder advancedOptionsBorder = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Advanced Options");
            advancedOptionsPanel.setBorder(advancedOptionsBorder);
            advancedOptionsPanel.setLayout(null);
            advancedOptionsPanel.setBounds(0, 210, WIDTH, 300);
            addAdvancedDetailsComponents(advancedOptionsPanel);
        add(advancedOptionsPanel);

        //========= Buttons Panel ========\\

        JPanel buttonsPanel = new JPanel();
            buttonsPanel.setLayout(new GridLayout(1, 2));
            buttonsPanel.setBounds(0, 515, WIDTH, 60);
            addButtons(buttonsPanel);
        add(buttonsPanel);

        setLocationRelativeTo(null);
    }

    private void addScanDetailsComponents(JPanel panel) {
        // IP Address row
        ipAddressField = new JTextField();
        ipAddressField.setToolTipText("IP address in the form of (xxx.xxx.xxx.xxx)");
        panel.add(ipAddressField);
        TextPrompt ipAddressPlaceholder = new TextPrompt("IP Address", ipAddressField);

        /**
         * BEGIN CIDR, SSH + Telnet boxes row
         */
        JPanel cstPanel = new JPanel();
        cstPanel.setLayout(new GridLayout(1, 3));

        cidrField = new JTextField();
        cidrField.setToolTipText("CIDR suffix (integer), recommended: 24");
        cstPanel.add(cidrField);
        TextPrompt cidrPlaceholder = new TextPrompt("CIDR", cidrField);

        sshBox = new JCheckBox("SSH");
        sshBox.setToolTipText("Select SSH as scanning method");
        sshBox.setSelected(true);
        sshBox.setEnabled(false);
        cstPanel.add(sshBox);

        telnetBox = new JCheckBox("Telnet");
        telnetBox.setToolTipText("Telnet scanning is currently disabled");
        telnetBox.setEnabled(false);
        cstPanel.add(telnetBox);

        panel.add(cstPanel);
        /**
         * END CIDR, SSH + Telnet boxes row
         */

        changePasswordBox = new JCheckBox("Change Password?");
        changePasswordBox.setToolTipText("Change the device root password?");
        panel.add(changePasswordBox);

        notifyDeviceBox = new JCheckBox("Notify Device?");
        notifyDeviceBox.setToolTipText("Alert the device of the vulnerability?");
        panel.add(notifyDeviceBox);
    }

    private void addAdvancedDetailsComponents(JPanel panel) {
        timeoutField = new JTextField();
        timeoutField.setToolTipText("Set the bruteforce timeout of each device being scanned");
        timeoutField.setBounds(5, 20, WIDTH - 10, 50);
        panel.add(timeoutField);
        TextPrompt timeoutPlaceholder = new TextPrompt("Bruteforce Timeout", timeoutField);

        executeMoreCommandsBox = new JCheckBox("Execute more commands?");
        executeMoreCommandsBox.setToolTipText("Option to send extra commands to a vulnerable device, if detected");
        executeMoreCommandsBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if(executeMoreCommandsBox.isSelected()) {
                    commandsBox.setEnabled(true);
                    commandsBox.setBackground(Color.white);
                } else {
                    commandsBox.setEnabled(false);
                    commandsBox.setBackground(Color.decode("#F8F8F8"));
                }
            }
        });
        executeMoreCommandsBox.setBounds(5, 80, WIDTH - 10, 30);
        panel.add(executeMoreCommandsBox);

        commandsBox = new JTextArea();
        commandsBox.setToolTipText("Enter your commands here, each on a new line");
        commandsBox.setEnabled(false);
        commandsBox.setBackground(Color.decode("#F8F8F8"));
        commandsBox.setBounds(10, 120, WIDTH - 21, 160);
        panel.add(commandsBox);
    }

    private void addButtons(JPanel panel) {
        advancedScanButton = new JButton("Perform Advanced Scan");
        advancedScanButton.addActionListener(asHandler);
        panel.add(advancedScanButton);

        helpButton = new JButton("Help");
        helpButton.addActionListener(asHandler);
        panel.add(helpButton);
    }
}