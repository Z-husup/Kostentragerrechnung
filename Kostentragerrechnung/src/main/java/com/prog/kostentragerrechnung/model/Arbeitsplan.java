package com.prog.kostentragerrechnung.model;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a work plan ("Arbeitsplan") entry for a specific manufacturing step.
 * <p>
 * Each {@code Arbeitsplan} instance includes a part reference, a step number,
 * the machine used, and the duration of the operation in minutes.
 * All created entries are stored in a static global list.
 */
@Data
public class Arbeitsplan {

    /**
     * Unique identifier for the work plan entry.
     */
    private String arbeitsplanId;

    /**
     * The ID of the associated part (corresponds to database column {@code teil_id}).
     */
    private String teilId;

    /**
     * The sequential number of the work step (corresponds to column {@code ag_nr}).
     */
    private int arbeitsgangNummer;

    /**
     * The number of the machine used for this work step (corresponds to column {@code maschine}).
     */
    private String maschinenNummer;

    /**
     * The processing duration in minutes for this work step (corresponds to column {@code dauer}).
     */
    private int bearbeitungsdauerMin;

    /**
     * A global list of all created {@code Arbeitsplan} instances.
     */
    public static final List<Arbeitsplan> arbeitsplans = new ArrayList<>();

    /**
     * Constructs a new {@code Arbeitsplan} object with the given attributes,
     * and automatically adds it to the {@link #arbeitsplans} list.
     *
     * @param arbeitsplanId         The unique identifier of the work plan entry.
     * @param teilId                The ID of the associated part.
     * @param arbeitsgangNummer     The number of the operation step.
     * @param maschinenNummer       The machine used for the operation.
     * @param bearbeitungsdauerMin  The duration of the operation in minutes.
     */
    public Arbeitsplan(String arbeitsplanId, String teilId, int arbeitsgangNummer,
                       String maschinenNummer, int bearbeitungsdauerMin) {
        this.arbeitsplanId = arbeitsplanId;
        this.teilId = teilId;
        this.arbeitsgangNummer = arbeitsgangNummer;
        this.maschinenNummer = maschinenNummer;
        this.bearbeitungsdauerMin = bearbeitungsdauerMin;
        arbeitsplans.add(this);
    }
}

