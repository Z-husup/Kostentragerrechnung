package com.prog.kostentragerrechnung.model;

import lombok.Data;

@Data
public class Arbeitsplan {
    private String arbeitsplanId;
    private String teilId;       // teil_id
    private int arbeitsgangNummer; // ag_nr
    private String maschinenNummer; // maschine
    private int bearbeitungsdauerMin; // dauer
}
