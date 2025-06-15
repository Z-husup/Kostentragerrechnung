package com.prog.kostentragerrechnung.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
public class Material {

    private static int nextId = 0;

    private int materialId; //id

    private String materialNummer; //Nr

    private double kostenProStueck; //kost

    public static final List<Material> materials = new ArrayList<>();

    public Material(String materialNummer, double kostenProStueck) {
        this.nextId++;

        this.materialId = nextId;
        this.materialNummer = materialNummer;
        this.kostenProStueck = kostenProStueck;

        materials.add(this);
    }

    public Material(){
        this.nextId++;

        this.materialId = nextId;

        materials.add(this);
    }
}