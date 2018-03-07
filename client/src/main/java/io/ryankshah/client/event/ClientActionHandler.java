package io.ryankshah.client.event;

import io.ryankshah.Client;
import io.ryankshah.client.gui.AdvancedScan;
import io.ryankshah.client.gui.QuickScanPanel;
import io.ryankshah.client.gui.ScanHistory;
import io.ryankshah.util.resource.ResourceLoader;
import io.ryankshah.util.resource.ScriptExecutor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientActionHandler implements ActionListener
{
    public void actionPerformed(ActionEvent e) {
        // Quick scan button
        if(e.getSource() == QuickScanPanel.quickScanButton) {
            int cp = QuickScanPanel.changePasswordBox.isSelected() ? 1 : 0;
            int nd = QuickScanPanel.notifyDeviceBox.isSelected() ? 1 : 0;
            String options = Client.user.getUUID().toString() + " " +
                    QuickScanPanel.ipAddressField.getText() + " " + QuickScanPanel.cidrField.getText() +
                    " " + cp + " " + nd;
            boolean result = ScriptExecutor.executePythonScriptWithArgs(ResourceLoader.getScriptResource("quick.py"), options);
            if(result) {
                Client.recentScanOptions = options;
                Client.updateRecentScan();
                JOptionPane.showMessageDialog(Client.INSTANCE, "Scan completed successfully!", "Info", JOptionPane.PLAIN_MESSAGE);
            }
        }

        // Quick scan help/info panel
        if(e.getSource() == QuickScanPanel.quickScanHelpButton) {
            StringBuilder sb = new StringBuilder("<html>");
            sb.append("<strong>IP Address</strong><br /><p>An IP address should be given in the form of xxx.xxx.xxx.xxx</p><br /><br />");
            sb.append("<strong>CIDR</strong><br /><p>A CIDR suffix is used to define the IP address range to scan in.<br />The recommended value is 24</p><br /><br />");
            sb.append("<strong>Scanning Modes</strong><br />");
            sb.append("<ol><li><strong>SSH</strong> - SSH is a protocol for operating network services over an unsecured network</li> <li><strong>Telnet</strong> - Telnet scanning is currently not available</li></ol><br /><br />");
            sb.append("<strong>Change Password</strong><br /><p>Choose this if the device's password should be changed (recommended)</p><br /><br />");
            sb.append("<strong>Notify Device</strong><br /><p>Choose this if the device should be notified of the vulnerability (recommended)</p><br /><br />");

            JLabel label = new JLabel(sb.toString());
            label.setFont(new Font("dialoginput", Font.PLAIN, 14));

            JOptionPane.showMessageDialog(Client.INSTANCE, label, "Quick Scan Help", JOptionPane.PLAIN_MESSAGE);
        }

        if(e.getSource() == Client.advancedScanButton) {
            new AdvancedScan().setVisible(true);
        }

        if(e.getSource() == Client.scanHistoryButton) {
            new ScanHistory().setVisible(true);
        }

        if(e.getSource() == Client.performLastScanButton) {
            if (Client.recentScanOptions.equals("")) {
                JOptionPane.showMessageDialog(Client.INSTANCE, "No scans were performed since you were logged in", "Info", JOptionPane.PLAIN_MESSAGE);
            } else {
                boolean result = ScriptExecutor.executePythonScriptWithArgs(ResourceLoader.getScriptResource("quick.py"), Client.recentScanOptions);
                if (result) {
                    Client.updateRecentScan();
                    JOptionPane.showMessageDialog(Client.INSTANCE, "Scan completed successfully!", "Info", JOptionPane.PLAIN_MESSAGE);
                }
            }
        }

        if(e.getSource() == Client.editProfileButton) {
            // TODO: Show edit profile window
            System.out.println("Show edit profile window");
        }

        if(e.getSource() == Client.userGuideButton) {
            // TODO: Open user guide
        }

        // Logout
        if(e.getSource() == Client.logoutButton) {
            int result = JOptionPane.showConfirmDialog(Client.INSTANCE, "Are you sure you want to logout?", "Qutting Miraihilate?", JOptionPane.YES_NO_OPTION);
            if(result == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }
}