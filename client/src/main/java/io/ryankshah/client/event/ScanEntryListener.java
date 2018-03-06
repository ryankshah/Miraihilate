package io.ryankshah.client.event;

import io.ryankshah.client.gui.ScanEntry;
import io.ryankshah.client.gui.ScanHistory;
import io.ryankshah.util.database.DBHelper;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ScanEntryListener implements ListSelectionListener
{
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if(!e.getValueIsAdjusting()) {
            JList list = (JList) e.getSource();
            ScanEntry selection = (ScanEntry) list.getSelectedValue();
            getEntryResult(selection);
        }
    }

    public void getEntryResult(ScanEntry entry) {
        Connection con = DBHelper.getDatabaseConnection();
        PreparedStatement stmt = null;

        try {
            String query = "SELECT * FROM scan_logs WHERE id = ?";
            stmt = con.prepareStatement(query);
            stmt.setInt(1, entry.getID());
            ResultSet results = stmt.executeQuery();

            // While a result exists
            while(results.next()) {
                ScanHistory.scanResult.setText(results.getString("data"));
            }

            // Closing up the connection
            if(stmt != null)
                stmt.close();
            if(con != null)
                con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}