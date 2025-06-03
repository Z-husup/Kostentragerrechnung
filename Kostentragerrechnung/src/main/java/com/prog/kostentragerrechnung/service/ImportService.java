package com.prog.kostentragerrechnung.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.prog.kostentragerrechnung.database.DBManager;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ImportService {

    public void importExcelFile (

    ) {

        //TODO Akyl
        //Export to Excel file

        return;
    }

    @FXML
    public void importExcel(Button importExcelButton, Label fileLabel) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выберите Excel файл");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Excel Files", "*.xlsx", "*.xls")
        );


        Stage stage = (Stage) importExcelButton.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            fileLabel.setText("Choosed: " + file.getName());
            String fileName = file.getName();
            fileLabel.setText(fileName);

            Tooltip tooltip = new Tooltip("Selected file: " + file.getAbsolutePath());
            Tooltip.install(fileLabel, tooltip);
            excelToDB(file);
            File tempDir = new File("temp");
        if (!tempDir.exists()) {
            tempDir.mkdir();
        }

        File savedFile = new File(tempDir, file.getName());
        try {
            Files.copy(file.toPath(), savedFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File copied to: " + savedFile.getAbsolutePath());

        } catch (IOException e) {
            e.printStackTrace();
            fileLabel.setText("Error copying file: " + e.getMessage());
        }
        } else {
            fileLabel.setText("Not choosed file");
            Tooltip tooltip = new Tooltip("No file selected");
            Tooltip.install(fileLabel, tooltip);
        }
    }

    private void excelToDB(File file) {
        try (FileInputStream fis = new FileInputStream(file);
            Workbook workbook = new XSSFWorkbook(fis);
            Connection conn = DBManager.getConnection()) {

            conn.setAutoCommit(false); // Всё в одной транзакции

            // Очистка таблиц (опционально)
            String[] tables = {"material", "teil", "maschine", "arbeitsplan", "auftrag"};
            for (String table : tables) {
                try (PreparedStatement pstmt = conn.prepareStatement("DELETE FROM " + table)) {
                    pstmt.executeUpdate();
                }
            }

            // Material
            Sheet materialSheet = workbook.getSheet("Material");
            if (materialSheet != null) {
                for (Row row : materialSheet) {
                    if (row.getRowNum() == 0) continue; // Пропуск заголовков

                    String nr = row.getCell(0).getStringCellValue();
                    int kost = (int) getCellAsDouble(row.getCell(1));

                    String sql = "INSERT INTO material (nr, kost) VALUES (?, ?)";
                    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                        pstmt.setString(1, nr);
                        pstmt.setInt(2, kost);
                        pstmt.executeUpdate();
                    }
                }
            }

            // Teil
            Sheet teilSheet = workbook.getSheet("Teil");
            if (teilSheet != null) {
                for (Row row : teilSheet) {
                    if (row.getRowNum() == 0) continue;

                    String bezeichnung = row.getCell(1).getStringCellValue();
                    int ks_e_h = (int) getCellAsDouble(row.getCell(2));

                    String sql = "INSERT INTO teil (bezeichnung, ks_e_h) VALUES (?, ?)";
                    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                        pstmt.setString(1, bezeichnung);
                        pstmt.setInt(2, ks_e_h);
                        pstmt.executeUpdate();
                    }
                }
            }

            // Maschine
            Sheet maschineSheet = workbook.getSheet("Maschine");
            if (maschineSheet != null) {
                for (Row row : maschineSheet) {
                    if (row.getRowNum() == 0) continue;

                    String teilNr = row.getCell(0).getStringCellValue();
                    String knoten = row.getCell(1).getStringCellValue();
                    int k_mat = (int) row.getCell(2).getNumericCellValue();
                    int k_fert = (int) row.getCell(3).getNumericCellValue();
                    int anzahl = (int) row.getCell(4).getNumericCellValue();
                    String mat = row.getCell(5).getStringCellValue();

                    String sql = "INSERT INTO maschine (teil_nr, knoten, k_mat, k_fert, anzahl, mat) VALUES (?, ?, ?, ?, ?, ?)";
                    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                        pstmt.setString(1, teilNr);
                        pstmt.setString(2, knoten);
                        pstmt.setInt(3, k_mat);
                        pstmt.setInt(4, k_fert);
                        pstmt.setInt(5, anzahl);
                        pstmt.setString(6, mat);
                        pstmt.executeUpdate();
                    }
                }
            }

            // Arbeitsplan
            Sheet arbeitsplanSheet = workbook.getSheet("Arbeitsplan");
            if (arbeitsplanSheet != null) {
                for (Row row : arbeitsplanSheet) {
                    if (row.getRowNum() == 0) continue;

                    String teilNr = row.getCell(0).getStringCellValue();
                    int ag = (int) row.getCell(1).getNumericCellValue();
                    String maschine = row.getCell(2).getStringCellValue();
                    double zeit = row.getCell(3).getNumericCellValue();

                    String sql = "INSERT INTO arbeitsplan (teil_nr, ag, maschine, zeit) VALUES (?, ?, ?, ?)";
                    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                        pstmt.setString(1, teilNr);
                        pstmt.setInt(2, ag);
                        pstmt.setString(3, maschine);
                        pstmt.setDouble(4, zeit);
                        pstmt.executeUpdate();
                    }
                }
            }

            // Auftrag
            Sheet auftragSheet = workbook.getSheet("Auftrag");
            if (auftragSheet != null) {
                for (Row row : auftragSheet) {
                    if (row.getRowNum() == 0) continue;

                    int k_mat = (int) row.getCell(0).getNumericCellValue();
                    int k_fert = (int) row.getCell(1).getNumericCellValue();
                    int dat_kost = (int) row.getCell(2).getNumericCellValue(); // Пример: 20240601

                    String sql = "INSERT INTO auftrag (k_mat, k_fert, dat_kost) VALUES (?, ?, ?)";
                    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                        pstmt.setInt(1, k_mat);
                        pstmt.setInt(2, k_fert);
                        pstmt.setInt(3, dat_kost);
                        pstmt.executeUpdate();
                    }
                }
            }

            conn.commit();
            System.out.println("Импорт из Excel завершен успешно.");
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static double getCellAsDouble(Cell cell) {
    if (cell == null) return 0.0; // ячейка отсутствует

    try {
        switch (cell.getCellType()) {
            case NUMERIC:
                return cell.getNumericCellValue();
            case STRING:
                String s = cell.getStringCellValue().replace(",", ".").trim();
                return s.isEmpty() ? 0.0 : Double.parseDouble(s);
            case FORMULA:
                return cell.getNumericCellValue();
            case BLANK:
                return 0.0;
            case BOOLEAN:
            case ERROR:
            default:
                return 0.0;
        }
    } catch (Exception e) {
        System.err.println("Ошибка при чтении числового значения из ячейки: " + cell);
        return 0.0;
    }

    //todo: починить перевод данных в таблицу БД
}
}
