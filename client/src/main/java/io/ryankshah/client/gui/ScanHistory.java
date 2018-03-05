package io.ryankshah.client.gui;

import io.ryankshah.util.resource.ResourceLoader;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class ScanHistory extends JFrame
{
    private ScanEntry entries[] = {
            new ScanEntry("2017-8-2 10:21:03"),
            new ScanEntry("2017-8-2 10:21:03"),
            new ScanEntry("2017-8-2 10:21:03"),
            new ScanEntry("2017-8-2 10:21:03"),
            new ScanEntry("2017-8-2 10:21:03"),
            new ScanEntry("2017-8-2 10:21:03"),
            new ScanEntry("2017-8-2 10:21:03")
    };
    private JList entryList;
    private JTextArea scanResult;

    public ScanHistory() {
        entryList = new JList(entries);
        setTitle("Scan History");
        setSize(new Dimension(600, 400));

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(1,2));

        // TODO: Add list on left side
        JPanel resultListPanel = new JPanel();
        TitledBorder resultListBorder = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Recent Scan");
        resultListPanel.setBorder(resultListBorder);
        resultListPanel.setLayout(new GridLayout(1, 2));
        entryList = new JList(entries);
        entryList.setCellRenderer(new ScanEntryRenderer());
        entryList.setVisibleRowCount(4);
        JScrollPane pane = new JScrollPane(entryList);
        resultListPanel.add(pane);
        add(resultListPanel);

        // Add scan result view area
        JPanel resultViewPanel = new JPanel();
        TitledBorder resultViewBorder = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Result View");
        resultViewPanel.setBorder(resultViewBorder);
        resultViewPanel.setLayout(new GridLayout(1, 2));
        scanResult = new JTextArea();
        scanResult.setEditable(false);
        //updateRecentScan();
        JScrollPane scroll = new JScrollPane(
                scanResult,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );
        resultViewPanel.add(scroll);
        add(resultViewPanel);

        setLocationRelativeTo(null);
        setResizable(false);
    }
}