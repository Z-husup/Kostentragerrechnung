package com.prog.kostentragerrechnung.model;

import lombok.Data;

@Data
public class Teil {
    private String teilId;       // teil_id
    private String teilNummer;   // teil_nr
    private int unterstrukturPosition; // knoten
    private int anzahl;          // anzahl
    private String materialNummer; // mat
}