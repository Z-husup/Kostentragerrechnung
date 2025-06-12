package com.prog.kostentragerrechnung.service;

import java.sql.Connection;
import java.sql.SQLException;

import com.prog.kostentragerrechnung.database.DBManager;

public class ImportService {
    Connection conn;

    public ImportService() {
        try {
            this.conn = DBManager.getConnection();
        } catch (SQLException e) {
            System.err.println("Ошибка инициализации подключения к БД: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public void importExcelFile () {

        //TODO
        //Import Excel file

        return;
    }
}
