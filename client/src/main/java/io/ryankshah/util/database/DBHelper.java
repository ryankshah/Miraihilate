package io.ryankshah.util.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Helper class for initiating a database connection
 */
public class DBHelper
{
    private static final String DB_URL = "jdbc:mysql://localhost:8889/miraihilate";
    private static final String USER = "root", PASS = "root";

    public static Connection getDatabaseConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}