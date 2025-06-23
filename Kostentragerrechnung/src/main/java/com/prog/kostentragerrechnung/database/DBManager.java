package com.prog.kostentragerrechnung.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBManager {
    Connection connection;

    public static Connection getConnection() throws SQLException {
        String url = "jdbc:sqlite:database.db";
        return java.sql.DriverManager.getConnection(url);
    }
}
