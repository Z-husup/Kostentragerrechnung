package com.prog.kostentragerrechnung.model;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class Material {

    private static int nextId = 1;

    private int materialId;

    private String materialNummer; //Nr

    private double kostenProStueck; //kost

    public static final List<Material> materials = new ArrayList<>();

}

