package com.prog.kostentragerrechnung.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Auftrag {
    private String auftragId;
    private String auftragNummer;   // auftrag_nr
    private LocalDate datumKostenrechnung; // dat_kost
}
