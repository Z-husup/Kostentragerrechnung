package com.prog.kostentragerrechnung.model;

import lombok.Data;

import java.time.LocalDate;

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
        r.setMaschineNummer(teil.getArbeitsplan() != null && teil.getArbeitsplan().getMaschine() != null
                ? teil.getArbeitsplan().getMaschine().getMaschinenNummer() : null);

        r.setMaterialkosten(teil.getMaterialkosten());
        r.setMaterialgemeinkosten(teil.getMaterialgemeinkosten());
        r.setFertigungskosten(teil.getFertigungskosten());
        r.setFertigungsgemeinkosten(teil.getFertigungsgemeinkosten());
        r.setHerstellkosten(teil.getHerstellkosten());
        r.setBearbeitungsdauerMin(teil.getArbeitsplan() != null ? (int) teil.getArbeitsplan().getBearbeitungsdauerMin() : 0);
        r.setBerechnungsdatum(LocalDate.now());
        r.setIstRecursive(recursive);

        report = r;
        return r;
    }

}

