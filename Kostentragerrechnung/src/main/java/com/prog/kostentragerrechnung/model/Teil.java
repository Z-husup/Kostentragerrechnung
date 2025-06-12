package com.prog.kostentragerrechnung.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
public class Teil {

    private static int nextId = 1;

    private int teilId; //id

    private String teilNummer; //teil_nr

    private Auftrag auftrag; //knoten_auftrag

    private Teil parent; //knoten_parent
    private List<Teil> children; //knoten_childer

    private double materialkosten; //K_mat
    private double fertigungskosten; //K_fert

    private int anzahl; //anzahl

    private Maschine maschine; //maschine
    private Material material; //mat

    public static final List<Teil> teils = new ArrayList<>();

}