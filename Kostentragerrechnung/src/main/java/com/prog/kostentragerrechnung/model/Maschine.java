package com.prog.kostentragerrechnung.model;

import lombok.Data;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
public class Maschine {

    private int maschineId; //id

    private String maschinenNummer; //Nr

    private String bezeichnung; //Bezeichnung

    private double kostensatzProStunde; //KS

    public static final HashMap<Integer, Maschine> maschines = new HashMap<>();

}