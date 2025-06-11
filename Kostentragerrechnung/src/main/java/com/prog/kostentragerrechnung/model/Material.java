package com.prog.kostentragerrechnung.model;

import lombok.Data;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
public class Material {

    private int materialId; //Id

    private String materialNummer; //Nr

    private double kostenProStueck; //kost

    public static final HashMap<Integer, Material> materials = new HashMap<>();

}