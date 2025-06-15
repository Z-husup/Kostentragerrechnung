package com.prog.kostentragerrechnung.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
public class Teil {

    private static int nextId = 0;

    private int teilId; //id

    private String teilNummer; //teil_nr

    private String bezeichnung; //teil_nr

    private Auftrag auftrag;    // knoten_auftrag
    private Teil oberteil;      // knoten_oberteil
    private List<Teil> children; //knoten_children

    private double materialkosten; //K_mat
    private double fertigungskosten; //K_fert

    private int anzahl; //anzahl

    private Arbeitsplan arbeitsplan; //arbeitsplan
    private Material material; //mat

    public static final List<Teil> teils = new ArrayList<>();

    // Teil.java
    @Override
    public String toString() {
        return "Teil{" +
                "teilNr='" + teilNummer + '\'' +
                ", anzahl=" + anzahl +
                ", material=" + (material != null ? material.getMaterialNummer() : "null") +
                ", arbeitsplan=" + (arbeitsplan != null ? arbeitsplan.toString() : "null") +
                '}';
    }

    public Teil(List<Teil> children, double materialkosten, double fertigungskosten, int anzahl, Arbeitsplan arbeitsplan, Material material, String teilNummer) {
        this.nextId++;

        this.teilId = nextId;
        this.children = children;
        this.materialkosten = materialkosten;
        this.fertigungskosten = fertigungskosten;
        this.anzahl = anzahl;
        this.arbeitsplan = arbeitsplan;
        this.material = material;
        this.teilNummer = teilNummer;

        teils.add(this);
    }

    public Teil() {
        this.nextId++;

        this.teilId = nextId;
        this.children = new ArrayList<>();

        teils.add(this);
    }

    public void berechneKosten(boolean recursive) {
        // 🔹 Direct Materialkosten
        if (this.material != null) {
            this.materialkosten = this.material.getKostenProStueck() * this.anzahl;
        } else {
            this.materialkosten = 0;
        }

        // 🔹 Direct Fertigungskosten
        if (this.arbeitsplan != null && this.arbeitsplan.getMaschine() != null) {
            Maschine maschine = this.arbeitsplan.getMaschine();
            double dauer = this.arbeitsplan.getBearbeitungsdauerMin();
            double kostensatzProMinute = maschine.getKostensatzProStunde() / 60.0;
            this.fertigungskosten = dauer * kostensatzProMinute;
        } else {
            this.fertigungskosten = 0;
        }

        // 🔁 Child costs (if recursive is true)
        if (recursive && this.children != null) {
            for (Teil child : this.children) {
                child.berechneKosten(true); // recursion
                this.materialkosten += child.getMaterialkosten() * child.getAnzahl();
                this.fertigungskosten += child.getFertigungskosten() * child.getAnzahl();
            }
        }

        // 🎯 Round both
        this.materialkosten = Math.round(this.materialkosten * 100.0) / 100.0;
        this.fertigungskosten = Math.round(this.fertigungskosten * 100.0) / 100.0;
    }



}