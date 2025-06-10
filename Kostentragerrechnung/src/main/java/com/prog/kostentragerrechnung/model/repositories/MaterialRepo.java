package com.prog.kostentragerrechnung.model.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.prog.kostentragerrechnung.database.DBManager;
import com.prog.kostentragerrechnung.model.Material;

public class MaterialRepo {
    Connection connection;

    public MaterialRepo(Connection conn) throws SQLException {
        this.connection = conn;
        if (this.connection == null) {
            this.connection = DBManager.getConnection();
        }
    }

    public void create(String nr, double kost) throws SQLException {
        String sql = "INSERT INTO material (nr, kost) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nr);
            stmt.setDouble(2, kost);
            stmt.executeUpdate();
        }
    }

    public void update(Material mk) throws SQLException {
        String sql = "UPDATE material SET nr = ?, kost = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, mk.getMaterialNummer());
            stmt.setDouble(2, mk.getKostenProStueck());
            stmt.setInt(3, mk.getMaterialId());
            stmt.executeUpdate();
        }
    }

    public void delete(Material mk) throws SQLException {
        String sql = "DELETE FROM material WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, mk.getMaterialId());
            stmt.executeUpdate();
        }
    }

    public void deleteAll() throws SQLException {
        String sql = "DELETE FROM material";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.executeUpdate();
        }
    }

    public void deleteByNr(String nr) throws SQLException {
        String sql = "DELETE FROM material WHERE nr = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nr);
            stmt.executeUpdate();
        }
    }

    public List<Material> getAll() throws SQLException {
        List<Material> list = new ArrayList<>();
        String sql = "SELECT * FROM material";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Material(
                    rs.getString("nr"),
                    rs.getDouble("kost")
                ));
            }
        }
        return list;
    }

    public Material getById(String id) throws SQLException {
        String sql = "SELECT * FROM material WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Material(
                        rs.getString("nr"),
                        rs.getDouble("kost")
                    );
                }
            }
        }
        return null;
    }
}
