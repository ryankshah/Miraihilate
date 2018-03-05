package io.ryankshah.client.gui;

import io.ryankshah.client.event.ClientActionHandler;
import io.ryankshah.util.gui.TextPrompt;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class QuickScanPanel extends JPanel
{
    public static JTextField ipAddressField, cidrField;
    public static JCheckBox sshBox, telnetBox, changePasswordBox, notifyDeviceBox;
    public static JButton quickScanButton, quickScanHelpButton;
    private ActionListener clientActionListener;

    public QuickScanPanel() {
        this.clientActionListener = new ClientActionHandler();

        TitledBorder panelTestBorder = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Quick Scan");
        setBorder(panelTestBorder);

        setLayout(new GridLayout(6, 1, 0, 15));

        addComponents();

        setVisible(true);
    }

    private void addComponents() {
        // IP Address row
        ipAddressField = new JTextField();
        ipAddressField.setToolTipText("IP address in the form of (xxx.xxx.xxx.xxx)");
        add(ipAddressField);
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

        add(cstPanel);
        /**
         * END CIDR, SSH + Telnet boxes row
         */

        changePasswordBox = new JCheckBox("Change Password?");
        changePasswordBox.setToolTipText("Change the device root password?");
        add(changePasswordBox);

        notifyDeviceBox = new JCheckBox("Notify Device?");
        notifyDeviceBox.setToolTipText("Alert the device of the vulnerability?");
        add(notifyDeviceBox);

        JPanel btnPanel = new JPanel(new GridLayout(1, 2));

        quickScanButton = new JButton("Perform Quick Scan");
        quickScanButton.addActionListener(clientActionListener);
        btnPanel.add(quickScanButton);

        quickScanHelpButton = new JButton("Help");
        quickScanHelpButton.addActionListener(clientActionListener);
        btnPanel.add(quickScanHelpButton);

        add(btnPanel);

        JLabel helpLabel = new JLabel("All scans are logged and stored securely.");
        add(helpLabel);
    }
}