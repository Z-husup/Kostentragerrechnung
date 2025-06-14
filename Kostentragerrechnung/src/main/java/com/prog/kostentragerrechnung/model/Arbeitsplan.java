package com.prog.kostentragerrechnung.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
@NoArgsConstructor
public class Arbeitsplan {

    private static int nextId = 1;

    private int arbeitsplanId; //id

    private int arbeitsgangNummer; //ag_nr

    private Maschine maschine; //maschine

    private int bearbeitungsdauerMin; //dauer

    public static final List<Arbeitsplan> arbeitsplans = new ArrayList<>();

    public Arbeitsplan(int arbeitsgangNummer, Maschine maschine, int bearbeitungsdauerMin) {
        this.nextId++;

        this.arbeitsgangNummer = arbeitsgangNummer;
        this.maschine = maschine;
        this.bearbeitungsdauerMin = bearbeitungsdauerMin;

        arbeitsplans.add(this);
    }
}