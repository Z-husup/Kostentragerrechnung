package com.prog.kostentragerrechnung.model;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class Teil {

    private static int nextId = 1;

    private int teilId; //teil_id

    private String teilNummer; //teil_nr

    private Teil parent; //knoten

    private double materialkosten; //K_mat
    private double fertigungskosten; //K_fert

    private int anzahl; //anzahl

    private Maschine maschine; //maschine
    private Material material; //mat(nr)

    public static final List<Teil> teils = new ArrayList<>();

}
