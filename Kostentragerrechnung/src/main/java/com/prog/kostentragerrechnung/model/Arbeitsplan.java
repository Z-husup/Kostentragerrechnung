package com.prog.kostentragerrechnung.model;

import lombok.Data;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
public class Arbeitsplan {

    private int arbeitsplanId; //id

    private Teil teil; //teil_id

    private int arbeitsgangNummer; //ag_nr

    private Maschine maschine; //maschine

    private int bearbeitungsdauerMin; //dauer

    public static final HashMap<Integer, Arbeitsplan> arbeitsplans = new HashMap<>();

}