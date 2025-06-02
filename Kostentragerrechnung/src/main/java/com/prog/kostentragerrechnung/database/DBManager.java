package com.prog.kostentragerrechnung.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBManager {
    Connection connection;

    private static Connection getConnection() throws SQLException {
        String url = "jdbc:sqlite:database.db";
        return java.sql.DriverManager.getConnection(url);
    }

    public static void initDatabase() {
    String[] sqlStatements = {
        """
        CREATE TABLE IF NOT EXISTS material (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            nr TEXT NOT NULL,
            kost INTEGER NOT NULL
        );
        """,
        """
        CREATE TABLE IF NOT EXISTS teil (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            bezeichnung TEXT NOT NULL,
            ks_e_h INTEGER NOT NULL
        );
        """,
        """
        CREATE TABLE IF NOT EXISTS maschine (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            teil_nr TEXT NOT NULL,
            knoten TEXT NOT NULL,
            k_mat INTEGER NOT NULL,
            k_fert INTEGER NOT NULL,
            anzahl INTEGER NOT NULL,
            mat TEXT NOT NULL
        );
        """,
        """
        CREATE TABLE IF NOT EXISTS auftrag (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            k_mat TEXT NOT NULL,
            k_fert INTEGER NOT NULL,
            dat_kost INTEGER NOT NULL
            );
        """, 
        """
        CREATE TABLE IF NOT EXISTS auftrag (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            k_mat INTEGER NOT NULL,
            k_fert INTEGER NOT NULL,
            dat_kost INTEGER NOT NULL
        );dat_kost INTEGER NOT NULL);
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
