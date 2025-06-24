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

    private List<Arbeitsplan> arbeitsplanList;
    private Material material; //mat

    public static final List<Teil> teils = new ArrayList<>();

    @Override
    public String toString() {
        return "Teil: " +
                "teilNr='" + teilNummer + '\'' +
                "bezeichnung='" + bezeichnung + '\'' +
                ", anzahl=" + anzahl;
    }

    public Teil(List<Teil> children, double materialkosten, double fertigungskosten, int anzahl, List<Arbeitsplan> arbeitsplan, Material material, String teilNummer) {
        this.nextId++;

        this.teilId = nextId;
        this.children = children;
        this.materialkosten = materialkosten;
        this.fertigungskosten = fertigungskosten;
        this.anzahl = anzahl;
        this.arbeitsplanList = new ArrayList<>();
        this.arbeitsplanList.addAll(arbeitsplan);
        this.material = material;
        this.teilNummer = teilNummer;

        teils.add(this);
    }

    public Teil() {
        this.nextId++;

        this.teilId = nextId;
        this.children = new ArrayList<>();
        this.arbeitsplanList = new ArrayList<>();

        teils.add(this);
    }

    public void berechneKosten() {
        double matKosten = 0;
        double fertKosten = 0;

        if (this.material != null) {
            matKosten = this.material.getKostenProStueck() * this.anzahl;
        }

        if (this.arbeitsplanList != null) {
            for (Arbeitsplan plan : arbeitsplanList) {
                if (plan.getMaschine() != null) {
                    double dauer = plan.getBearbeitungsdauerMin();
                    double kostensatzMin = plan.getMaschine().getKostensatzProStunde() / 60.0;
                    fertKosten += dauer * kostensatzMin;
                }
            }
        }

        if (this.children != null && !this.children.isEmpty()) {
            for (Teil child : this.children) {
                child.berechneKosten(); // 🪄 recursive call
                matKosten += child.getMaterialkosten();
                fertKosten += child.getFertigungskosten();
            }
        }

        this.materialgemeinkosten = Math.round(matKosten * 0.10 * 100.0) / 100.0;
        this.fertigungsgemeinkosten = Math.round(fertKosten * 0.10 * 100.0) / 100.0;

        this.materialkosten = Math.round(matKosten * 100.0) / 100.0;
        this.fertigungskosten = Math.round(fertKosten * 100.0) / 100.0;

        this.herstellkosten = Math.round(
                (this.materialkosten + this.materialgemeinkosten + this.fertigungskosten + this.fertigungsgemeinkosten) * 100.0
        ) / 100.0;
    }

    public static void resetAll() {
        teils.clear();
        nextId = 0;
    }

    public int getTeilId() {
        return teilId;
    }

    public void setTeilId(int teilId) {
        this.teilId = teilId;
    }

    public String getTeilNummer() {
        return teilNummer;
    }

    public void setTeilNummer(String teilNummer) {
        this.teilNummer = teilNummer;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public Auftrag getAuftrag() {
        return auftrag;
    }

    public void setAuftrag(Auftrag auftrag) {
        this.auftrag = auftrag;
    }

    public Teil getOberteil() {
        return oberteil;
    }

    public void setOberteil(Teil oberteil) {
        this.oberteil = oberteil;
    }

    public List<Teil> getChildren() {
        return children;
    }

    public void setChildren(List<Teil> children) {
        this.children = children;
    }

    public double getMaterialkosten() {
        return materialkosten;
    }

    public void setMaterialkosten(double materialkosten) {
        this.materialkosten = materialkosten;
    }

    public double getFertigungskosten() {
        return fertigungskosten;
    }

    public void setFertigungskosten(double fertigungskosten) {
        this.fertigungskosten = fertigungskosten;
    }

    public double getMaterialgemeinkosten() {
        return materialgemeinkosten;
    }

    public void setMaterialgemeinkosten(double materialgemeinkosten) {
        this.materialgemeinkosten = materialgemeinkosten;
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

    public int getAnzahl() {
        return anzahl;
    }

    public void setAnzahl(int anzahl) {
        this.anzahl = anzahl;
    }

    public List<Arbeitsplan> getArbeitsplan() {
        return arbeitsplanList;
    }

    public void setArbeitsplan(List<Arbeitsplan> arbeitsplanList) {
        this.arbeitsplanList = arbeitsplanList;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }
}