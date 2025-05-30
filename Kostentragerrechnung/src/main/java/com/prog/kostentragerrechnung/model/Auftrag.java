package com.prog.kostentragerrechnung.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class Auftrag {
    private String auftragId;
    private String auftragNummer;   // auftrag_nr
    private LocalDate datumKostenrechnung; // dat_kost

    public static final List<Auftrag> auftrags = new ArrayList<>();

    public Auftrag(String auftragId, String auftragNummer, LocalDate datumKostenrechnung) {
        this.auftragId = auftragId;
        this.auftragNummer = auftragNummer;
        this.datumKostenrechnung = datumKostenrechnung;
        auftrags.add(this);
    }
}
