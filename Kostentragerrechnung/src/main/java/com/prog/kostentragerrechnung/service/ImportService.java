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

import java.time.LocalDate;
import java.util.*;

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

            importAuftrag(workbook.getSheet("Auftrag"));
            importMaterial(workbook.getSheet("Material"));
            importMaschine(workbook.getSheet("Maschine"));
            importTeil(workbook.getSheet("Teil"));
            importArbeitsplan(workbook.getSheet("Arbeitsplan"));

            Teil.teils.forEach(System.out::println);
        }
    }

    private void clearDatabaseTables(Connection conn, List<String> tables) throws Exception {
        for (String table : tables) {
            try (PreparedStatement pstmt = conn.prepareStatement("DELETE FROM " + table)) {
                pstmt.executeUpdate();
            }
        }
    }

    private void importAuftrag(Sheet sheet) {
        if (sheet == null) return;

        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue;

            Cell nrCell = row.getCell(0);          // A: auftragNummer

            if (nrCell == null || nrCell.getStringCellValue().isEmpty()) continue;

            String nr = nrCell.getStringCellValue();

            Auftrag auftrag = new Auftrag();
            auftrag.setAuftragNummer(nr);
        }
    }

    private void importMaterial(Sheet sheet) {
        if (sheet == null) return;
        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue;

            Cell nrCell = row.getCell(0);   // A: materialNummer
            Cell kostCell = row.getCell(1); // B: kosten

            if (nrCell == null || kostCell == null) continue;

            String nr = nrCell.getStringCellValue();
            double kost = getNumericValue(kostCell);

            new Material(nr, kost);
        }
    }


    private void importMaschine(Sheet sheet) {
        if (sheet == null) return;
        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue;

            Cell nrCell = row.getCell(0);      // A: maschinenNummer
            Cell bezCell = row.getCell(1);     // B: bezeichnung
            Cell kostenCell = row.getCell(2);  // C: kostensatz

            if (nrCell == null || bezCell == null || kostenCell == null) continue;

            String nr = nrCell.getStringCellValue();
            String bez = bezCell.getStringCellValue();
            double kosten = getNumericValue(kostenCell);

            new Maschine(nr, bez, kosten);
        }
    }

    private void importTeil(Sheet sheet) {
        if (sheet == null) return;

        Map<String, Teil> teilMap = new HashMap<>();
        Map<String, String> parentRelation = new HashMap<>();
        Map<String, String> auftragRelation = new HashMap<>();

        for (Row row : sheet) {
            if (row == null || row.getRowNum() == 0) continue;

            String teilId = getCellString(row.getCell(0));       // A: teil_id
            String teilNr = getCellString(row.getCell(1));       // B: teil_nr
            String auftragNr = getCellString(row.getCell(2));    // C: knoten_aufgabe
            String parentId = getCellString(row.getCell(3));     // D: knoten_teil (points to teil_id)
            int anzahl = (int) getNumericValue(row.getCell(6));  // G: Anzahl
            String materialNr = getCellString(row.getCell(7));   // H: Mat

            if (teilId.isEmpty() || teilNr.isEmpty()) continue;

            Material material = Material.materials.stream()
                    .filter(m -> m.getMaterialNummer().equalsIgnoreCase(materialNr.trim()))
                    .findFirst()
                    .orElse(null);

            Teil teil = new Teil();
            teil.setTeilId(Integer.parseInt(teilId));
            teil.setTeilNummer(teilNr);
            teil.setAnzahl(anzahl);
            teil.setMaterial(material);
            teil.setChildren(new ArrayList<>());

            teilMap.put(teilId, teil);

            if (!parentId.isEmpty()) parentRelation.put(teilId, parentId);
            if (!auftragNr.isEmpty()) auftragRelation.put(teilId, auftragNr);
        }

        // 🔁 Link parent-child relations (Oberteil)
        for (Map.Entry<String, String> entry : parentRelation.entrySet()) {
            String childId = entry.getKey();
            String parentId = entry.getValue();

            Teil child = teilMap.get(childId);
            Teil parent = teilMap.get(parentId);

            if (child != null && parent != null && !childId.equals(parentId)) {
                parent.getChildren().add(child);
                child.setOberteil(parent);
            }
        }

        // 🔁 Link Auftrag relations
        for (Map.Entry<String, String> entry : auftragRelation.entrySet()) {
            String teilId = entry.getKey();
            String auftragNr = entry.getValue();

            Teil teil = teilMap.get(teilId);
            Auftrag auftrag = Auftrag.auftrags.stream()
                    .filter(a -> a.getAuftragNummer().trim().equalsIgnoreCase(auftragNr.trim()))
                    .findFirst()
                    .orElse(null);

            if (teil != null && auftrag != null) {
                auftrag.addTeil(teil);
                teil.setAuftrag(auftrag);
            }
        }
    }



    private void importArbeitsplan(Sheet sheet) {
        if (sheet == null) return;

        for (Row row : sheet) {
            if (row == null || row.getRowNum() == 0) continue; // skip header

            String teilNr = getCellString(row.getCell(0));        // string
            String agNrStr = getCellString(row.getCell(1));       // string
            String maschinenNr = getCellString(row.getCell(2));   // string
            double dauer = getNumericValue(row.getCell(3));       // numeric

            if (teilNr.isEmpty() || agNrStr.isEmpty() || maschinenNr.isEmpty() || dauer == 0)
                continue;

            int agNr = parseIntSafe(agNrStr);

            Maschine maschine = Maschine.maschines.stream()
                    .filter(m -> m.getMaschinenNummer().equals(maschinenNr))
                    .findFirst()
                    .orElse(null);

            if (maschine == null) continue;

            Arbeitsplan ap = new Arbeitsplan(agNr, maschine, (int) dauer);

            Teil teil = Teil.teils.stream()
                    .filter(t -> t.getTeilId() == (Integer.parseInt(teilNr)))
                    .findFirst()
                    .orElse(null);

            if (teil != null) {
                teil.setArbeitsplan(ap);
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

    private String getCellString(Cell cell) {
        if (cell == null) return "";

        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue().trim();
            case NUMERIC -> String.valueOf((int) cell.getNumericCellValue()); // or `cell.getNumericCellValue()` if decimals matter
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            case FORMULA -> cell.getCellFormula();
            default -> "";
        };
    }

    private int parseIntSafe(String s) {
        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
            return 0;
        }
    }



}
