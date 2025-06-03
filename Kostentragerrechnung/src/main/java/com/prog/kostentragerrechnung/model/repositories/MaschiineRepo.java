package com.prog.kostentragerrechnung.model.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.prog.kostentragerrechnung.model.Maschine;

public class MaschiineRepo {
    public final Connection connection;
    

    public MaschiineRepo(Connection connection) {
        this.connection = connection;
    }

    public void create(String nr, String bz,double ksps) throws SQLException {
        String sql = "INSERT INTO maschine (nr, bezeichnung,ks_eh) VALUES (?, ?,?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(0, nr);
            stmt.setString(1, bz);
            stmt.setDouble(2, ksps);
            stmt.executeUpdate();

        }
    }
    public void update(Maschine mk) throws SQLException {
        String sql = "UPDATE maschine SET nr = ?, bezeichnung = ?, ks_eh = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(0, mk.getMaschinenNummer());
            stmt.setString(1, mk.getBezeichnung());
            stmt.setDouble(2, mk.getKostensatzProStunde());
            stmt.executeUpdate();
        }
    }
    public void delete(Maschine mk) throws SQLException {
        String sql = "DELETE FROM maschine WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, mk.getMaschineId());
            stmt.executeUpdate();
        }
    }
    public void deleteAll() throws SQLException {
        String sql = "DELETE * FROM maschine";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.executeUpdate();
        }
    }
    public void deleteByNr(String nr) throws SQLException {
        String sql = "DELETE FROM maschine WHERE nr = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nr);
            stmt.executeUpdate();
        }
    }
    public List<Maschine> getAll() throws SQLException {
        List<Maschine> list = new ArrayList<>();
        String sql = "SELECT * FROM Maschinekosten";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new Maschine(
                    rs.getString("nr"),
                    rs.getString("bezeichnung"),
                    rs.getDouble("ks_eh")
                ));
            }
        }
        return list;
    }

    public Maschine getById(String id) throws SQLException {
        String sql = "SELECT * FROM Maschine WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(0, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Maschine(
                        rs.getString("nr"),
                        rs.getString("bezeichnung"),
                        rs.getDouble("ks_eh")
                    );
                }
            }
        }
        return null;
    }
}
