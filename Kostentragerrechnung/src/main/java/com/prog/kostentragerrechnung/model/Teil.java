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
    private double materialgemeinkosten;       // 10% Zuschlag
    private double fertigungsgemeinkosten;     // 10% Zuschlag
    private double herstellkosten;             // Herstellkosten

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
        double matKosten = 0;
        double fertKosten = 0;

        // 🔹 Direktkosten
        if (this.material != null) {
            matKosten = this.material.getKostenProStueck() * this.anzahl;
        }

        if (this.arbeitsplan != null && this.arbeitsplan.getMaschine() != null) {
            double dauer = this.arbeitsplan.getBearbeitungsdauerMin();
            double kostensatzMin = this.arbeitsplan.getMaschine().getKostensatzProStunde() / 60.0;
            fertKosten = dauer * kostensatzMin;
        }

        // 🔁 Rekursive Kindkosten
        if (recursive && this.children != null) {
            for (Teil child : this.children) {
                child.berechneKosten(true);
                matKosten += child.getMaterialkosten() * child.getAnzahl();
                fertKosten += child.getFertigungskosten() * child.getAnzahl();
            }
        }

        // 📌 Zuschläge
        this.materialgemeinkosten = Math.round(matKosten * 0.10 * 100.0) / 100.0;
        this.fertigungsgemeinkosten = Math.round(fertKosten * 0.10 * 100.0) / 100.0;

        // 📌 Direkte + Zuschläge
        this.materialkosten = Math.round(matKosten * 100.0) / 100.0;
        this.fertigungskosten = Math.round(fertKosten * 100.0) / 100.0;

        this.herstellkosten = Math.round(
                (this.materialkosten + this.materialgemeinkosten + this.fertigungskosten + this.fertigungsgemeinkosten) * 100.0
        ) / 100.0;
    }




}