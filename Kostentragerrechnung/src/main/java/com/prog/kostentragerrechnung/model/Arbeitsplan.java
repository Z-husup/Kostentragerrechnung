package com.prog.kostentragerrechnung.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
public class Arbeitsplan {

    private static int nextId = 0;

    private int arbeitsplanId; //id

    private int arbeitsgangNummer; //ag_nr

    private Maschine maschine; //maschine

    private int bearbeitungsdauerMin; //dauer

    public static final List<Arbeitsplan> arbeitsplans = new ArrayList<>();

    public Arbeitsplan(int arbeitsgangNummer, Maschine maschine, int bearbeitungsdauerMin) {
        this.nextId++;

        this.arbeitsplanId = nextId;
        this.arbeitsgangNummer = arbeitsgangNummer;
        this.maschine = maschine;
        this.bearbeitungsdauerMin = bearbeitungsdauerMin;

        arbeitsplans.add(this);
    }

    public Arbeitsplan(){
        this.nextId++;

        this.arbeitsplanId = nextId;

        arbeitsplans.add(this);
    }

    public static void setNextId(int nextId) {
        Arbeitsplan.nextId = nextId;
    }

    public int getArbeitsplanId() {
        return arbeitsplanId;
    }

    public void setArbeitsplanId(int arbeitsplanId) {
        this.arbeitsplanId = arbeitsplanId;
    }

    public int getArbeitsgangNummer() {
        return arbeitsgangNummer;
    }

    public void setArbeitsgangNummer(int arbeitsgangNummer) {
        this.arbeitsgangNummer = arbeitsgangNummer;
    }

    public Maschine getMaschine() {
        return maschine;
    }

    public void setMaschine(Maschine maschine) {
        this.maschine = maschine;
    }

    public int getBearbeitungsdauerMin() {
        return bearbeitungsdauerMin;
    }

    public void setBearbeitungsdauerMin(int bearbeitungsdauerMin) {
        this.bearbeitungsdauerMin = bearbeitungsdauerMin;
    }
}