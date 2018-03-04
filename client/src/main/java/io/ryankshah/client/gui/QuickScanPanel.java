package io.ryankshah.client.gui;

import io.ryankshah.util.database.ImageLoader;
import io.ryankshah.util.gui.TextPrompt;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class QuickScanPanel extends JPanel
{
    private JTextField ipAddressField, cidrField;
    private JCheckBox sshBox, telnetBox, changePasswordBox, notifyDeviceBox;
    private JButton quickScanButton, quickScanHelpButton;

    public QuickScanPanel() {
        TitledBorder panelTestBorder = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Quick Scan");
        setBorder(panelTestBorder);

        setLayout(new GridLayout(6, 1, 0, 15));

        addComponents();

        setVisible(true);
    }

    private void addComponents() {
        // IP Address row
        ipAddressField = new JTextField();
        add(ipAddressField);
        TextPrompt ipAddressPlaceholder = new TextPrompt("IP Address", ipAddressField);

        /**
         * BEGIN CIDR, SSH + Telnet boxes row
         */
        JPanel cstPanel = new JPanel();
        cstPanel.setLayout(new GridLayout(1, 3));

        cidrField = new JTextField();
        cstPanel.add(cidrField);
        TextPrompt cidrPlaceholder = new TextPrompt("CIDR", cidrField);

        sshBox = new JCheckBox("SSH");
        cstPanel.add(sshBox);

        telnetBox = new JCheckBox("Telnet");
        telnetBox.setEnabled(false);
        cstPanel.add(telnetBox);

        add(cstPanel);
        /**
         * END CIDR, SSH + Telnet boxes row
         */

        changePasswordBox = new JCheckBox("Change Password?");
        add(changePasswordBox);

        notifyDeviceBox = new JCheckBox("Notify Device?");
        add(notifyDeviceBox);

        JPanel btnPanel = new JPanel(new GridLayout(1, 2));

        quickScanButton = new JButton("Perform Quick Scan");
        btnPanel.add(quickScanButton);

        quickScanHelpButton = new JButton("Help");
        btnPanel.add(quickScanHelpButton);

        add(btnPanel);

        JLabel helpLabel = new JLabel("All scans are logged and stored securely.");
        add(helpLabel);
    }
}