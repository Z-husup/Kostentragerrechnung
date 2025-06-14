package com.prog.kostentragerrechnung.service;

import com.prog.kostentragerrechnung.model.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ImportService {

    public void importExcel(Button importExcelButton, Label fileLabel, Connection conn) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выберите Excel файл");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Excel Files", "*.xlsx", "*.xls")
        );

        Stage stage = (Stage) importExcelButton.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            fileLabel.setText("Choosed: " + file.getName());

            Tooltip tooltip = new Tooltip("Selected file: " + file.getAbsolutePath());
            Tooltip.install(fileLabel, tooltip);

            saveToTemp(file);

            try {
                excelToData(file, conn);
            } catch (Exception e) {
                e.printStackTrace();
                fileLabel.setText("Import error: " + e.getMessage());
            }
        } else {
            fileLabel.setText("Not choosed file");
            Tooltip tooltip = new Tooltip("No file selected");
            Tooltip.install(fileLabel, tooltip);
        }
    }

    private void saveToTemp(File file) {
        File tempDir = new File("temp");
        if (!tempDir.exists()) tempDir.mkdir();

        File savedFile = new File(tempDir, file.getName());
        try {
            Files.copy(file.toPath(), savedFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File copied to: " + savedFile.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error copying file: " + e.getMessage());
        }
    }

    public void excelToData(File file, Connection conn) throws Exception {
        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fis)) {

            clearDatabaseTables(conn, List.of("material", "maschine", "teil", "arbeitsplan", "auftrag"));

            importMaterial(workbook.getSheet("Material"));
            importMaschine(workbook.getSheet("Maschine"));
            importTeil(workbook.getSheet("Teil"));
            importArbeitsplan(workbook.getSheet("Arbeitsplan"));
            importAuftrag(workbook.getSheet("Auftrag"));
        }
    }

    private void clearDatabaseTables(Connection conn, List<String> tables) throws Exception {
        for (String table : tables) {
            try (PreparedStatement pstmt = conn.prepareStatement("DELETE FROM " + table)) {
                pstmt.executeUpdate();
            }
        }
    }

    private double getNumericValue(Cell cell) {
        if (cell == null) return 0.0;
        return switch (cell.getCellType()) {
            case NUMERIC -> cell.getNumericCellValue();
            case STRING -> Double.parseDouble(cell.getStringCellValue().trim());
            default -> throw new IllegalStateException("Unexpected cell type: " + cell.getCellType());
        };
    }

    private void importMaterial(Sheet sheet) {
        if (sheet == null) return;
        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue;
            Cell nrCell = row.getCell(0);
            Cell kostCell = row.getCell(1);
            if (nrCell == null || kostCell == null) continue;
            String nr = nrCell.getStringCellValue();
            double kost = getNumericValue(kostCell);
            Material.materials.add(new Material(nr, kost));
        }
    }

    private void importMaschine(Sheet sheet) {
        if (sheet == null) return;
        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue;
            Cell nrCell = row.getCell(0);
            Cell bezCell = row.getCell(1);
            Cell kostenCell = row.getCell(2);
            if (nrCell == null || bezCell == null || kostenCell == null) continue;
            String nr = nrCell.getStringCellValue();
            String bez = bezCell.getStringCellValue();
            double kosten = getNumericValue(kostenCell);
            Maschine.maschines.add(new Maschine(nr, bez, kosten));
        }
    }

    private void importTeil(Sheet sheet) {
        if (sheet == null) return;
        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue;
            Cell teilNrCell = row.getCell(1);
            Cell anzahlCell = row.getCell(5);
            Cell matNrCell = row.getCell(6);
            if (teilNrCell == null || anzahlCell == null || matNrCell == null) continue;
            String teilNr = teilNrCell.getStringCellValue();
            int anzahl = (int) getNumericValue(anzahlCell);
            String matNr = matNrCell.getStringCellValue();
            Material mat = Material.materials.stream().filter(m -> m.getMaterialNummer().equals(matNr)).findFirst().orElse(null);
            Teil teil = new Teil(null, null, new ArrayList<>(), 0, 0, anzahl, null, mat, teilNr);
            Teil.teils.add(teil);
        }
    }

    private void importArbeitsplan(Sheet sheet) {
        if (sheet == null) return;
        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue;
            Cell teilIdCell = row.getCell(0);
            Cell agNrCell = row.getCell(1);
            Cell maschinenNrCell = row.getCell(2);
            Cell dauerCell = row.getCell(3);
            if (teilIdCell == null || agNrCell == null || maschinenNrCell == null || dauerCell == null) continue;
            int teilId = (int) getNumericValue(teilIdCell);
            int agNr = (int) getNumericValue(agNrCell);
            String maschinenNr = maschinenNrCell.getStringCellValue();
            int dauer = (int) getNumericValue(dauerCell);
            Maschine maschine = Maschine.maschines.stream().filter(m -> m.getMaschinenNummer().equals(maschinenNr)).findFirst().orElse(null);
            Arbeitsplan ap = new Arbeitsplan(agNr, maschine, dauer);
            if (teilId < Teil.teils.size()) {
                Teil.teils.get(teilId).setArbeitsplan(ap);
            }
            Arbeitsplan.arbeitsplans.add(ap);
        }
    }

    private void importAuftrag(Sheet sheet) {
        if (sheet == null) return;
        Iterator<Teil> teilIterator = Teil.teils.iterator();
        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue;
            Cell nrCell = row.getCell(0);
            if (nrCell == null) continue;
            String nr = nrCell.getStringCellValue();
            if (teilIterator.hasNext()) {
                Teil teil = teilIterator.next();
                Auftrag auftrag = new Auftrag(nr, 0, 0, null, teil);
                teil.setAuftrag(auftrag);
                Auftrag.auftrags.add(auftrag);
            }
        }
    }
}
