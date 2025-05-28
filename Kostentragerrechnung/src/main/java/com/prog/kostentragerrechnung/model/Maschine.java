package com.prog.kostentragerrechnung.model;

import lombok.Data;

@Data
public class Maschine {
    private String maschineId;
    private String maschinenNummer; // nr
    private String bezeichnung;     // bezeichnung
    private double kostensatzProStunde; // ks
}

