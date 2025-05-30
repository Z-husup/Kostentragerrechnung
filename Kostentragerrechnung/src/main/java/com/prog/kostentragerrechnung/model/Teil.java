package com.prog.kostentragerrechnung.model;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a component or part ("Teil") in the system.
 * <p>
 * Each {@code Teil} contains information such as its ID, part number, structural position,
 * quantity, and associated material number.
 */
@Data
public class Teil {

    /**
     * Unique identifier for the part (corresponds to database column {@code teil_id}).
     */
    private String teilId;

    /**
     * Part number (corresponds to database column {@code teil_nr}).
     */
    private String teilNummer;

    /**
     * Structural position in a hierarchy (corresponds to column {@code knoten}).
     */
    private int unterstrukturPosition;

    /**
     * Quantity of this part (corresponds to column {@code anzahl}).
     */
    private int anzahl;

    /**
     * Material number associated with this part (corresponds to column {@code mat}).
     */
    private String materialNummer;

    /**
     * A global list of all created {@code Teil} instances.
     */
    public static final List<Teil> teils = new ArrayList<>();

    /**
     * Constructs a new {@code Teil} object with the given attributes and automatically
     * adds it to the static {@code teils} list.
     *
     * @param teilId               The unique identifier of the part.
     * @param teilNummer           The part number.
     * @param unterstrukturPosition The structural position in the hierarchy.
     * @param anzahl               The quantity of the part.
     * @param materialNummer       The associated material number.
     */
    public Teil(String teilId, String teilNummer, int unterstrukturPosition, int anzahl, String materialNummer) {
        this.teilId = teilId;
        this.teilNummer = teilNummer;
        this.unterstrukturPosition = unterstrukturPosition;
        this.anzahl = anzahl;
        this.materialNummer = materialNummer;
        teils.add(this);
    }
}
