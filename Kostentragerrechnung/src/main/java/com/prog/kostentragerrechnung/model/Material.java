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

    public int getMaterialId() {
        return materialId;
    }

    public void setMaterialId(int materialId) {
        this.materialId = materialId;
    }

    public String getMaterialNummer() {
        return materialNummer;
    }

    public void setMaterialNummer(String materialNummer) {
        this.materialNummer = materialNummer;
    }

    public double getKostenProStueck() {
        return kostenProStueck;
    }

    public void setKostenProStueck(double kostenProStueck) {
        this.kostenProStueck = kostenProStueck;
    }
}