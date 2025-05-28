package com.prog.kostentragerrechnung.model;

import lombok.Data;

@Data
public class Material {
    private String materialId;
    private String materialNummer; // nr
    private double kostenProStueck; // kost
}
