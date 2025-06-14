package com.prog.kostentragerrechnung.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
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
        if (this.teil != null) {
            this.teil.berechneKosten();

            this.materialkosten = this.teil.getMaterialkosten();
            this.fertigungskosten = this.teil.getFertigungskosten();

            this.datumKostenrechnung = LocalDate.now();
        }
    }

}