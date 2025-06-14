package com.prog.kostentragerrechnung.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
@NoArgsConstructor
public class Teil {

    private static int nextId = 1;

    private int teilId; //id

    private String teilNummer; //teil_nr

    private Auftrag auftrag; //knoten_auftrag
    private Teil parent; //knoten_parent
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
                ", auftragNr=" + (auftrag != null ? auftrag.getAuftragNummer() : "null") +
                '}';
    }


    public Teil(Auftrag auftrag, Teil parent, List<Teil> children, double materialkosten, double fertigungskosten, int anzahl, Arbeitsplan arbeitsplan, Material material, String teilNummer) {
        this.nextId++;

        this.auftrag = auftrag;
        this.parent = parent;
        this.children = children;
        this.materialkosten = materialkosten;
        this.fertigungskosten = fertigungskosten;
        this.anzahl = anzahl;
        this.arbeitsplan = arbeitsplan;
        this.material = material;
        this.teilNummer = teilNummer;

        teils.add(this);
    }

    public void berechneKosten() {
        // 🔹 K_mat: Materialkosten berechnen
        if (this.material != null) {
            for (Material mat : Material.materials) {
                if (this.material.getMaterialNummer().equals(mat.getMaterialNummer())) {
                    this.materialkosten = mat.getKostenProStueck();
                    break;
                }
            }
        }

        // 🔹 K_fert: Fertigungskosten berechnen aus dem einen zugeordneten Arbeitsplan
        if (this.arbeitsplan != null && this.arbeitsplan.getMaschine() != null) {
            Maschine maschine = this.arbeitsplan.getMaschine();
            double ruestMinuten = 0;
            double stueckMinuten = this.arbeitsplan.getBearbeitungsdauerMin();
            double kostensatzProMinute = maschine.getKostensatzProStunde() / 60.0;
            this.fertigungskosten = (ruestMinuten + stueckMinuten) * kostensatzProMinute;
        }
    }



}