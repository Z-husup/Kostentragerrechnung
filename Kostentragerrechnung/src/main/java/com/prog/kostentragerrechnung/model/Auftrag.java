package com.prog.kostentragerrechnung.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
@NoArgsConstructor
public class Auftrag {

    private static int nextId = 1;

    private int auftragId; //id

    private String auftragNummer; //auftrag_nr

    private double materialkosten; //K_mat

    private double fertigungskosten; //K_fert

    private LocalDate datumKostenrechnung; //dat_kost

    private Teil teil; //knoten_teil

    public static final List<Auftrag> auftrags = new ArrayList<>();

    // Auftrag.java
    @Override
    public String toString() {
        return "Auftrag{" +
                "auftragNr='" + auftragNummer + '\'' +
                ", materialkosten=" + materialkosten +
                ", fertigungskosten=" + fertigungskosten +
                ", teilNr=" + (teil != null ? teil.getTeilNummer() : "null") +
                '}';
    }


    public Auftrag(String auftragNummer, double materialkosten, double fertigungskosten, LocalDate datumKostenrechnung, Teil teil) {
        this.nextId++;

        this.auftragNummer = auftragNummer;
        this.materialkosten = materialkosten;
        this.fertigungskosten = fertigungskosten;
        this.datumKostenrechnung = datumKostenrechnung;
        this.teil = teil;

        auftrags.add(this);
    }

    public void berechneKosten() {
        double sumMat = 0;
        double sumFert = 0;

        if (this.teil != null) {
            sumMat += berechneTeilKostenRekursiv(this.teil, true);
            sumFert += berechneTeilFertigungskostenRekursiv(this.teil, true);
        }

        this.materialkosten = sumMat;
        this.fertigungskosten = sumFert;
        this.datumKostenrechnung = LocalDate.now();
    }

    private double berechneTeilKostenRekursiv(Teil teil, boolean callBerechne) {
        if (callBerechne) teil.berechneKosten();
        double sum = teil.getMaterialkosten();
        if (teil.getChildren() != null) {
            for (Teil child : teil.getChildren()) {
                sum += berechneTeilKostenRekursiv(child, true);
            }
        }
        return sum;
    }

    private double berechneTeilFertigungskostenRekursiv(Teil teil, boolean callBerechne) {
        if (callBerechne) teil.berechneKosten();
        double sum = teil.getFertigungskosten();
        if (teil.getChildren() != null) {
            for (Teil child : teil.getChildren()) {
                sum += berechneTeilFertigungskostenRekursiv(child, true);
            }
        }
        return sum;
    }


}