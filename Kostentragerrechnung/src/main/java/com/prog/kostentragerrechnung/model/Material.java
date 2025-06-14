package com.prog.kostentragerrechnung.model;

import lombok.Data;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
public class Material {

    private static int nextId = 1;

    private int materialId; //id

    private String materialNummer; //Nr

    private double kostenProStueck; //kost

    public static final List<Material> materials = new ArrayList<>();

    public Material(String materialNummer, double kostenProStueck) {
        this.nextId++;

        this.materialNummer = materialNummer;
        this.kostenProStueck = kostenProStueck;

        materials.add(this);
    }
}