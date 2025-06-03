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
        CREATE TABLE IF NOT EXISTS material (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            nr TEXT NOT NULL,
            kost REAL NOT NULL
        );
        """,
        """
        CREATE TABLE IF NOT EXISTS teil (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            bezeichnung TEXT NOT NULL,
            ks_e_h REAL NOT NULL
        );
        """,
        """
        CREATE TABLE IF NOT EXISTS maschine (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            nr TEXT NOT NULL,
            bezeichnung TEXT NOT NULL,
            ks_eh REAL NOT NULL
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
        CREATE TABLE IF NOT EXISTS arbeitsplan (
            teil_nr TEXT,
            ag INTEGER,
            maschine TEXT,
            zeit REAL
        );
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
