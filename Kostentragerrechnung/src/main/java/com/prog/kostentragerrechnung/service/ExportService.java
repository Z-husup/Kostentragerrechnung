package com.prog.kostentragerrechnung.service;


import com.prog.kostentragerrechnung.model.*;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class ExportService {

    public void exportExcelFile(String filePath) {
        Workbook workbook = new XSSFWorkbook();

        exportTeilSheet(workbook);
        exportMaterialSheet(workbook);
        exportMaschineSheet(workbook);
        exportArbeitsplanSheet(workbook);
        exportAuftragSheet(workbook);

        try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
            workbook.write(fileOut);
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void exportTeilSheet(Workbook workbook) {
        Sheet sheet = workbook.createSheet("Teile");
        String[] headers = { "Teil ID", "Teil Nummer", "Bezeichnung", "Anzahl", "Materialnummer", "Materialtyp", "Kosten/Stück", "Maschine", "Maschinenkosten/h", "Arbeitsplan ID", "Bearbeitungsdauer (Min)", "Auftrag Nummer", "Materialkosten", "Fertigungskosten", "Materialgemeinkosten", "Fertigungsgemeinkosten", "Herstellkosten" };
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) headerRow.createCell(i).setCellValue(headers[i]);

        int rowNum = 1;
        for (Teil teil : Teil.teils) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(teil.getTeilId());
            row.createCell(1).setCellValue(teil.getTeilNummer());
            row.createCell(2).setCellValue(teil.getBezeichnung());
            row.createCell(3).setCellValue(teil.getAnzahl());
            row.createCell(4).setCellValue(teil.getMaterial() != null ? teil.getMaterial().getMaterialNummer() : "");
            row.createCell(5).setCellValue(teil.getMaterial() != null ? teil.getMaterial().getMaterialNummer() : "");
            row.createCell(6).setCellValue(teil.getMaterial() != null ? teil.getMaterial().getKostenProStueck() : 0);
            row.createCell(7).setCellValue(teil.getArbeitsplan() != null && teil.getArbeitsplan().getMaschine() != null ? teil.getArbeitsplan().getMaschine().getMaschinenNummer() : "");
            row.createCell(8).setCellValue(teil.getArbeitsplan() != null && teil.getArbeitsplan().getMaschine() != null ? teil.getArbeitsplan().getMaschine().getKostensatzProStunde() : 0);
            row.createCell(9).setCellValue(String.valueOf(teil.getArbeitsplan() != null ? teil.getArbeitsplan().getArbeitsgangNummer() : ""));
            row.createCell(10).setCellValue(teil.getArbeitsplan() != null ? teil.getArbeitsplan().getBearbeitungsdauerMin() : 0);
            row.createCell(11).setCellValue(teil.getAuftrag() != null ? teil.getAuftrag().getAuftragNummer() : "");
            row.createCell(12).setCellValue(teil.getMaterialkosten());
            row.createCell(13).setCellValue(teil.getFertigungskosten());
            row.createCell(14).setCellValue(teil.getMaterialgemeinkosten());
            row.createCell(15).setCellValue(teil.getFertigungsgemeinkosten());
            row.createCell(16).setCellValue(teil.getHerstellkosten());
        }
        for (int i = 0; i < headers.length; i++) sheet.autoSizeColumn(i);
    }

    private void exportMaterialSheet(Workbook workbook) {
        Sheet sheet = workbook.createSheet("Materialien");
        String[] headers = { "Materialnummer", "Typ", "Kosten/Stück" };
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) headerRow.createCell(i).setCellValue(headers[i]);

        int rowNum = 1;
        for (Material m : Material.materials) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(m.getMaterialNummer());
            row.createCell(1).setCellValue(m.getMaterialNummer());
            row.createCell(2).setCellValue(m.getKostenProStueck());
        }
        for (int i = 0; i < headers.length; i++) sheet.autoSizeColumn(i);
    }

    private void exportMaschineSheet(Workbook workbook) {
        Sheet sheet = workbook.createSheet("Maschinen");
        String[] headers = { "Maschinennummer", "Kostensatz pro Stunde" };
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) headerRow.createCell(i).setCellValue(headers[i]);

        int rowNum = 1;
        for (Maschine maschine : Maschine.maschines) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(maschine.getMaschinenNummer());
            row.createCell(1).setCellValue(maschine.getKostensatzProStunde());
        }
        for (int i = 0; i < headers.length; i++) sheet.autoSizeColumn(i);
    }

    private void exportArbeitsplanSheet(Workbook workbook) {
        Sheet sheet = workbook.createSheet("Arbeitspläne");
        String[] headers = { "Arbeitsgangnummer", "Maschinen ID", "Bearbeitungsdauer (Min)" };
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) headerRow.createCell(i).setCellValue(headers[i]);

        int rowNum = 1;
        for (Arbeitsplan ap : Arbeitsplan.arbeitsplans) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(ap.getArbeitsgangNummer());
            row.createCell(1).setCellValue(ap.getMaschine() != null ? ap.getMaschine().getMaschinenNummer() : "");
            row.createCell(2).setCellValue(ap.getBearbeitungsdauerMin());
        }
        for (int i = 0; i < headers.length; i++) sheet.autoSizeColumn(i);
    }

    private void exportAuftragSheet(Workbook workbook) {
        Sheet sheet = workbook.createSheet("Aufträge");
        String[] headers = { "Auftragsnummer", "Datum" };
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) headerRow.createCell(i).setCellValue(headers[i]);

        int rowNum = 1;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (Auftrag auftrag : Auftrag.auftrags) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(auftrag.getAuftragNummer());
            row.createCell(1).setCellValue(auftrag.getDatumKostenrechnung() != null ? auftrag.getDatumKostenrechnung().format(formatter) : "");
        }
        for (int i = 0; i < headers.length; i++) sheet.autoSizeColumn(i);
    }

    public void exportPdfFile(String filePath) {
        // TODO: Implement PDF export using iText or similar library
        System.out.println("PDF Export ist noch nicht implementiert.");
    }
}
