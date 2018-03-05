package io.ryankshah.client.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ScanEntryRenderer extends JLabel implements ListCellRenderer
{
    private static final Color HOVER_COLOR = new Color(140, 140, 140);

    public ScanEntryRenderer() {
        setForeground(Color.black);
        setOpaque(true);
        setIconTextGap(12);
        setBorder(new EmptyBorder(6,0,6,0));
    }

    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        ScanEntry entry = (ScanEntry) value;

        setText(entry.toString());
        //setIcon(entry.getIcon());
        setFont(new Font("dialoginput", Font.PLAIN, 12));

        if (isSelected)
            setBackground(HOVER_COLOR);
        else
            setBackground(Color.white);

        return this;
    }
}