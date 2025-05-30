package com.prog.kostentragerrechnung.model;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a material entity used in the system.
 * <p>
 * Each {@code Material} instance includes an identifier, material number, and cost per unit.
 * All created materials are stored in a global static list.
 */
@Data
public class Material {

    /**
     * Unique identifier for the material.
     */
    private String materialId;

    /**
     * Material number (corresponds to database column {@code nr}).
     */
    private String materialNummer;

    /**
     * Cost per unit of the material (corresponds to database column {@code kost}).
     */
    private double kostenProStueck;

    /**
     * A global list containing all created {@code Material} instances.
     */
    public static final List<Material> materials = new ArrayList<>();

    /**
     * Constructs a new {@code Material} object with the specified attributes,
     * and automatically adds it to the {@link #materials} list.
     *
     * @param id     The unique identifier of the material.
     * @param nummer The material number.
     * @param kosten The cost per unit.
     */
    public Material(String id, String nummer, double kosten) {
        this.materialId = id;
        this.materialNummer = nummer;
        this.kostenProStueck = kosten;
        materials.add(this);
    }
}

