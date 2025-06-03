package com.prog.kostentragerrechnung.model;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a machine ("Maschine") used in the system.
 * <p>
 * Each {@code Maschine} instance includes a unique ID, machine number,
 * a description, and an hourly cost rate.
 * All created machines are stored in a global static list.
 */
@Data
public class Maschine {

    private static int nextId = 1;

    /**
     * Unique identifier for the machine.
     */
    private int maschineId;

    /**
     * Machine number (corresponds to database column {@code nr}).
     */
    private String maschinenNummer;

    /**
     * Description or name of the machine (corresponds to column {@code bezeichnung}).
     */
    private String bezeichnung;

    /**
     * Cost rate per hour for using this machine (corresponds to column {@code ks}).
     */
    private double kostensatzProStunde;

    /**
     * A global list containing all created {@code Maschine} instances.
     */
    public static final List<Maschine> maschines = new ArrayList<>();

    /**
     * Constructs a new {@code Maschine} object with the specified attributes,
     * and automatically adds it to the {@link #maschines} list.
     *
     * @param maschineId           The unique identifier of the machine.
     * @param maschinenNummer      The machine number.
     * @param bezeichnung          The description or name of the machine.
     * @param kostensatzProStunde  The cost rate per hour.
     */
    public Maschine(String maschinenNummer, String bezeichnung, double kostensatzProStunde) {
        this.maschineId = nextId++;
        this.maschinenNummer = maschinenNummer;
        this.bezeichnung = bezeichnung;
        this.kostensatzProStunde = kostensatzProStunde;
        maschines.add(this);
    }
}


