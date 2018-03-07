package io.ryankshah.client.event;

import io.ryankshah.client.gui.AdvancedScan;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdvancedScanHandler implements ActionListener
{
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == AdvancedScan.advancedScanButton) {
            // TODO: Run advanced scan
        }

        if(e.getSource() == AdvancedScan.helpButton) {
            StringBuilder sb = new StringBuilder("<html>");
            sb.append("</html>");

            JLabel label = new JLabel(sb.toString());
            label.setFont(new Font("dialoginput", Font.PLAIN, 14));

            JOptionPane.showMessageDialog(AdvancedScan.INSTANCE, label, "Advanced Scan Help", JOptionPane.PLAIN_MESSAGE);
        }
    }
}