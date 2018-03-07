package io.ryankshah.client.gui;

import io.ryankshah.util.gui.TextPrompt;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class AdvancedScan extends JFrame
{
    public static JTextField ipAddressField, cidrField;
    public static JCheckBox sshBox, telnetBox, changePasswordBox, notifyDeviceBox;

    public static JTextField timeoutField;
    public static JCheckBox executeMoreCommandsBox;
    public static JTextArea commandsBox;

    public static JButton advancedScanButton, helpButton;

    public AdvancedScan() {
        setTitle("Advanced Scan");
        setSize(new Dimension(400, 500));

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(3, 1, 0, 15));
        setResizable(false);

        //====== Scan Details Panel ======\\

        JPanel scanDetailsPanel = new JPanel();
            TitledBorder scanDetailsBorder = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Scan Details");
            scanDetailsPanel.setBorder(scanDetailsBorder);
            scanDetailsPanel.setLayout(new GridLayout(4, 1));
            addScanDetailsComponents(scanDetailsPanel);
        add(scanDetailsPanel);

        //======  Advanced Options Panel ======\\

        JPanel advancedOptionsPanel = new JPanel();
            TitledBorder advancedOptionsBorder = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Advanced Options");
            advancedOptionsPanel.setBorder(advancedOptionsBorder);
            advancedOptionsPanel.setLayout(new GridLayout(3, 1));
            addAdvancedDetailsComponents(advancedOptionsPanel);
        add(advancedOptionsPanel);

        //========= Buttons Panel ========\\

        JPanel buttonsPanel = new JPanel();
            buttonsPanel.setLayout(new GridLayout(1, 2));
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
        timeoutField.setToolTipText("Set the SSH timeout of each device being scanned");
        panel.add(timeoutField);
        TextPrompt timeoutPlaceholder = new TextPrompt("SSH Timeout", timeoutField);

        executeMoreCommandsBox = new JCheckBox("Execute more commands?");
        executeMoreCommandsBox.setToolTipText("Option to send extra commands to a vulnerable device, if detected");
        panel.add(executeMoreCommandsBox);

        commandsBox = new JTextArea();
        commandsBox.setToolTipText("Enter your commands here, each on a new line");
        commandsBox.setEnabled(false);
        commandsBox.setRows(5);
        panel.add(commandsBox);
        TextPrompt commandsPlaceholder = new TextPrompt("Enter your commands, each on a new line", commandsBox);
    }

    private void addButtons(JPanel panel) {
        advancedScanButton = new JButton("Perform Advanced Scan");
        panel.add(advancedScanButton);

        helpButton = new JButton("Help");
        panel.add(helpButton);
    }
}