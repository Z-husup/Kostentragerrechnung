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

    public static void initDatabase() {
    String[] sqlStatements = {
        """
        
        
        
        """  
    };

    try (Connection conn = getConnection();
         Statement stmt = conn.createStatement()) {
        for (String sql : sqlStatements) {
            stmt.execute(sql);
        }
        System.out.println("База данных инициализирована.");
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
}
