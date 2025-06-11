package com.prog.kostentragerrechnung.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
public class Auftrag {

    private int auftragId; //id

    private String auftragNummer; //auftrag_nr

    private double materialkosten; //K_mat

    private double fertigungskosten; //K_fert

    private LocalDate datumKostenrechnung; //dat_kost

    public static final HashMap<Integer, Auftrag> auftrags = new HashMap();

}