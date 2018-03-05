package io.ryankshah.client.event;

import io.ryankshah.Client;
import io.ryankshah.client.gui.QuickScanPanel;
import io.ryankshah.util.resource.ResourceLoader;
import io.ryankshah.util.resource.ScriptExecutor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientActionHandler implements ActionListener
{
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == QuickScanPanel.quickScanButton) {
            int cp = QuickScanPanel.changePasswordBox.isSelected() ? 1 : 0;
            int nd = QuickScanPanel.notifyDeviceBox.isSelected() ? 1 : 0;
            boolean result = ScriptExecutor.executePythonScriptWithArgs(ResourceLoader.getScriptResource("quick.py"),
                    Client.user.getUUID().toString() + " " +
                            QuickScanPanel.ipAddressField.getText() + " " + QuickScanPanel.cidrField.getText() +
                            " " + cp + " " + nd
            );
            if(result) {
                Client.updateRecentScan();
            }
        }

        if(e.getSource() == QuickScanPanel.quickScanHelpButton) {

        }
    }
}