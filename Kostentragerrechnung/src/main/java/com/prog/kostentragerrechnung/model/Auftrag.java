package com.prog.kostentragerrechnung.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
public class Auftrag {

    private static int nextId = 0;

    private int auftragId; //id

    private String auftragNummer; //auftrag_nr

    private double materialkosten; //K_mat

    private double fertigungskosten; //K_fert

    private LocalDate datumKostenrechnung; //dat_kost

    private List<Teil> teil; //knoten_teilen

    public static final List<Auftrag> auftrags = new ArrayList<>();

    // Auftrag.java
    @Override
    public String toString() {
        return "Auftrag{" +
                "auftragNr='" + auftragNummer + '\'' +
                ", materialkosten=" + materialkosten +
                ", fertigungskosten=" + fertigungskosten +
                '}';
    }


    public Auftrag(String auftragNummer, double materialkosten, double fertigungskosten, LocalDate datumKostenrechnung, List<Teil> teil) {
        this.nextId++;

        this.auftragId = nextId;
        this.auftragNummer = auftragNummer;
        this.materialkosten = materialkosten;
        this.fertigungskosten = fertigungskosten;
        this.datumKostenrechnung = datumKostenrechnung;
        this.teil = teil;

        auftrags.add(this);
    }

    public Auftrag() {
        this.nextId++;

        this.auftragId = nextId;
        this.teil = new ArrayList<>();

        auftrags.add(this);
    }

    public void addTeil(Teil teil) {
        if (this.teil == null) {
            this.teil = new ArrayList<>();
        }
        this.teil.add(teil);
    }

    public void berechneKosten() {
        this.materialkosten = 0;
        this.fertigungskosten = 0;
        this.datumKostenrechnung = LocalDate.now();

        if (this.teil != null) {
            for (Teil teil : this.teil) {
                teil.berechneKosten(true); // 🔁 includes children
                this.materialkosten += teil.getMaterialkosten() * teil.getAnzahl();
                this.fertigungskosten += teil.getFertigungskosten() * teil.getAnzahl();
            }
        }

        // 🎯 Round both
        this.materialkosten = Math.round(this.materialkosten * 100.0) / 100.0;
        this.fertigungskosten = Math.round(this.fertigungskosten * 100.0) / 100.0;
    }


    public static void berechneAlleKosten() {
        for (Auftrag a : auftrags) {
            a.berechneKosten();
            a.printKosten();
        }
    }
    public void printKosten() {
        System.out.printf("Auftrag %s:\n  K_mat=%.2f €\n  K_fert=%.2f €\n  Gesamt=%.2f €\n",
                auftragNummer, materialkosten, fertigungskosten, materialkosten + fertigungskosten);
    }




}