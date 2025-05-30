package com.prog.kostentragerrechnung.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Maschine {
    private String maschineId;
    private String maschinenNummer; // nr
    private String bezeichnung;     // bezeichnung
    private double kostensatzProStunde; // ks

    public static final List<Maschine> maschines = new ArrayList<>();

    public Maschine(String maschineId, String maschinenNummer, String bezeichnung, double kostensatzProStunde) {
        this.maschineId = maschineId;
        this.maschinenNummer = maschinenNummer;
        this.bezeichnung = bezeichnung;
        this.kostensatzProStunde = kostensatzProStunde;
        maschines.add(this);
    }
}

