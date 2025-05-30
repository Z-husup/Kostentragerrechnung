package com.prog.kostentragerrechnung.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Teil {
    private String teilId;       // teil_id
    private String teilNummer;   // teil_nr
    private int unterstrukturPosition; // knoten
    private int anzahl;          // anzahl
    private String materialNummer; // mat

    public static final List<Teil> teils = new ArrayList<>();

    public Teil(String teilId, String teilNummer, int unterstrukturPosition, int anzahl, String materialNummer) {
        this.teilId = teilId;
        this.teilNummer = teilNummer;
        this.unterstrukturPosition = unterstrukturPosition;
        this.anzahl = anzahl;
        this.materialNummer = materialNummer;
        teils.add(this);
    }
}