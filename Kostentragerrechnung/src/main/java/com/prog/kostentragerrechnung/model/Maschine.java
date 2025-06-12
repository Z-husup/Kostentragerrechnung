package com.prog.kostentragerrechnung.model;

import lombok.Data;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
public class Maschine {

    private static int nextId = 1;

    private int maschineId; //id

    private String maschinenNummer; //Nr

    private String bezeichnung; //Bezeichnung

    private double kostensatzProStunde; //KS

    public static final List<Maschine> maschines = new ArrayList<>();

}