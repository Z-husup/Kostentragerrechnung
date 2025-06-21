package com.prog.kostentragerrechnung.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.stream.Collectors;

@Data
public class Report {

    private String auftragNummer;
    private String teilNummer;
    private String materialTyp; // z.B. Holz, Stahl
    private String maschineNummer;

    private int anzahl;

    private double materialkosten;
    private double materialgemeinkosten;

    private double fertigungskosten;
    private double fertigungsgemeinkosten;

    private double herstellkosten;

    private int bearbeitungsdauerMin;
    private LocalDate berechnungsdatum;

    private boolean istRecursive;
    private boolean zeitLimitUeberschritten;

    public static Report report = new Report();

    public Report createReport(Teil teil, boolean recursive) {
        teil.berechneKosten(recursive);
        Report r = new Report();

        r.setAuftragNummer(teil.getAuftrag() != null ? teil.getAuftrag().getAuftragNummer() : null);
        r.setTeilNummer(teil.getTeilNummer());
        r.setAnzahl(teil.getAnzahl());
        r.setMaterialTyp(teil.getMaterial() != null ? teil.getMaterial().getMaterialNummer() : null);
        String maschinen = teil.getArbeitsplan().stream()
                .filter(ap -> ap.getMaschine() != null)
                .map(ap -> ap.getMaschine().getMaschinenNummer())
                .distinct()
                .collect(Collectors.joining(", "));

        r.setMaschineNummer(maschinen.isEmpty() ? null : maschinen);


        r.setMaterialkosten(teil.getMaterialkosten());
        r.setMaterialgemeinkosten(teil.getMaterialgemeinkosten());
        r.setFertigungskosten(teil.getFertigungskosten());
        r.setFertigungsgemeinkosten(teil.getFertigungsgemeinkosten());
        r.setHerstellkosten(teil.getHerstellkosten());
        int dauerSum = teil.getArbeitsplan().stream()
                .mapToInt(ap -> (int) ap.getBearbeitungsdauerMin())
                .sum();

        r.setBearbeitungsdauerMin(dauerSum);

        r.setBerechnungsdatum(LocalDate.now());
        r.setIstRecursive(recursive);

        report = r;
        return r;
    }

    public String getAuftragNummer() {
        return auftragNummer;
    }

    public void setAuftragNummer(String auftragNummer) {
        this.auftragNummer = auftragNummer;
    }

    public String getTeilNummer() {
        return teilNummer;
    }

    public void setTeilNummer(String teilNummer) {
        this.teilNummer = teilNummer;
    }

    public String getMaterialTyp() {
        return materialTyp;
    }

    public void setMaterialTyp(String materialTyp) {
        this.materialTyp = materialTyp;
    }

    public String getMaschineNummer() {
        return maschineNummer;
    }

    public void setMaschineNummer(String maschineNummer) {
        this.maschineNummer = maschineNummer;
    }

    public int getAnzahl() {
        return anzahl;
    }

    public void setAnzahl(int anzahl) {
        this.anzahl = anzahl;
    }

    public double getMaterialkosten() {
        return materialkosten;
    }

    public void setMaterialkosten(double materialkosten) {
        this.materialkosten = materialkosten;
    }

    public double getMaterialgemeinkosten() {
        return materialgemeinkosten;
    }

    public void setMaterialgemeinkosten(double materialgemeinkosten) {
        this.materialgemeinkosten = materialgemeinkosten;
    }

    public double getFertigungskosten() {
        return fertigungskosten;
    }

    public void setFertigungskosten(double fertigungskosten) {
        this.fertigungskosten = fertigungskosten;
    }

    public double getFertigungsgemeinkosten() {
        return fertigungsgemeinkosten;
    }

    public void setFertigungsgemeinkosten(double fertigungsgemeinkosten) {
        this.fertigungsgemeinkosten = fertigungsgemeinkosten;
    }

    public double getHerstellkosten() {
        return herstellkosten;
    }

    public void setHerstellkosten(double herstellkosten) {
        this.herstellkosten = herstellkosten;
    }

    public int getBearbeitungsdauerMin() {
        return bearbeitungsdauerMin;
    }

    public void setBearbeitungsdauerMin(int bearbeitungsdauerMin) {
        this.bearbeitungsdauerMin = bearbeitungsdauerMin;
    }

    public LocalDate getBerechnungsdatum() {
        return berechnungsdatum;
    }

    public void setBerechnungsdatum(LocalDate berechnungsdatum) {
        this.berechnungsdatum = berechnungsdatum;
    }

    public boolean isIstRecursive() {
        return istRecursive;
    }

    public void setIstRecursive(boolean istRecursive) {
        this.istRecursive = istRecursive;
    }

    public boolean isZeitLimitUeberschritten() {
        return zeitLimitUeberschritten;
    }

    public void setZeitLimitUeberschritten(boolean zeitLimitUeberschritten) {
        this.zeitLimitUeberschritten = zeitLimitUeberschritten;
    }

    public static Report getReport() {
        return report;
    }

    public static void setReport(Report report) {
        Report.report = report;
    }
}

