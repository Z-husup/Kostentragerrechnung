package com.prog.kostentragerrechnung.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


/**
 * Represents an order ("Auftrag") in the system.
 * <p>
 * Each {@code Auftrag} instance contains an ID, order number, and
 * the date of the last cost calculation. All instances are stored
 * in a static list.
 */
@Data
public class Auftrag {

    private static int nextId = 1;

    /**
     * Unique identifier for the order.
     */
    private int auftragId;

    /**
     * Order number (corresponds to database column {@code auftrag_nr}).
     */
    private String auftragNummer;

    /**
     * Date of the last cost calculation (corresponds to column {@code dat_kost}).
     */
    private LocalDate datumKostenrechnung;

    /**
     * A global list of all created {@code Auftrag} instances.
     */
    public static final List<Auftrag> auftrags = new ArrayList<>();

    /**
     * Constructs a new {@code Auftrag} object with the specified attributes
     * and automatically adds it to the {@link #auftrags} list.
     *
     * @param auftragId             The unique identifier of the order.
     * @param auftragNummer         The order number.
     * @param datumKostenrechnung   The date of the last cost calculation.
     */
    public Auftrag(String auftragNummer, LocalDate datumKostenrechnung) {
        this.auftragId = nextId++;
        this.auftragNummer = auftragNummer;
        this.datumKostenrechnung = datumKostenrechnung;
        auftrags.add(this);
    }
}
