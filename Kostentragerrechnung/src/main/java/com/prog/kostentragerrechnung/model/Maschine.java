package com.prog.kostentragerrechnung.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
@NoArgsConstructor
public class Maschine {

    private static int nextId = 1;

    private int maschineId; //id

    private String maschinenNummer; //maschine_nr

    private String bezeichnung; //Bezeichnung

    private double kostensatzProStunde; //KS

    public static final List<Maschine> maschines = new ArrayList<>();

    public Maschine(String maschinenNummer, String bezeichnung, double kostensatzProStunde) {
        this.nextId++;

        this.maschinenNummer = maschinenNummer;
        this.bezeichnung = bezeichnung;
        this.kostensatzProStunde = kostensatzProStunde;

        maschines.add(this);
    }
}