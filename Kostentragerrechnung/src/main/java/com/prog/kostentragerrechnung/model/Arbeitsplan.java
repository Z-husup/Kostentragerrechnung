package com.prog.kostentragerrechnung.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Arbeitsplan {
    private String arbeitsplanId;
    private String teilId;       // teil_id
    private int arbeitsgangNummer; // ag_nr
    private String maschinenNummer; // maschine
    private int bearbeitungsdauerMin; // dauer

    public static final List<Arbeitsplan> arbeitsplans = new ArrayList<>();

    public Arbeitsplan(String arbeitsplanId, String teilId, int arbeitsgangNummer, String maschinenNummer, int bearbeitungsdauerMin) {
        this.arbeitsplanId = arbeitsplanId;
        this.teilId = teilId;
        this.arbeitsgangNummer = arbeitsgangNummer;
        this.maschinenNummer = maschinenNummer;
        this.bearbeitungsdauerMin = bearbeitungsdauerMin;
        arbeitsplans.add(this);
    }
}
