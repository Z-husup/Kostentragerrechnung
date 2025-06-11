package com.prog.kostentragerrechnung.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
public class Teil {

    private int teilId; //teil_id

    private String teilNummer; //teil_nr

    private Auftrag auftrag; //knoten_auftrag
    private Teil parent; //knoten_parent
    private List<Teil> children; //knoten_childer

    private double materialkosten; //K_mat
    private double fertigungskosten; //K_fert

    private int anzahl; //anzahl

    private Material material; //mat(nr)

    public static final HashMap<Integer, Teil> teils = new HashMap<>();

}