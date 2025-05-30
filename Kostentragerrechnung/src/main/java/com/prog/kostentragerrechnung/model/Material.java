package com.prog.kostentragerrechnung.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Material {
    private String materialId;
    private String materialNummer; // nr
    private double kostenProStueck; // kost

    public static final List<Material> materials = new ArrayList<>();

    public Material(String id, String nummer, double kosten) {
        this.materialId = id;
        this.materialNummer = nummer;
        this.kostenProStueck = kosten;
        materials.add(this); // add automatically to global list
    }
}
