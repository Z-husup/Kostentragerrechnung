package com.prog.kostentragerrechnung.controller.dialog;

import com.prog.kostentragerrechnung.model.Arbeitsplan;
import com.prog.kostentragerrechnung.model.Maschine;
import com.prog.kostentragerrechnung.model.Teil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.util.List;

/**
 * Controller for the "Add Work Plan" (Arbeitsplan) dialog.
 * <p>
 * Handles input and validation for creating a new {@link Arbeitsplan} entry,
 * including selecting a part, machine, and specifying duration and step number.
 */
public class AddWorkPlanController {

    /**
     * Indicates whether the work plan was successfully saved.
     */
    private boolean saved = false;

    /**
     * The stage (window) associated with this dialog.
     */
    private Stage dialogStage;

    /**
     * Sets the dialog stage for this controller.
     *
     * @param dialogStage The JavaFX stage used for the modal dialog.
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Returns whether the work plan was saved successfully.
     *
     * @return {@code true} if saved; otherwise {@code false}.
     */
    public boolean isSaved() {
        return saved;
    }

    // === FXML Fields ===

    /**
     * Text field for entering the work step number (Arbeitsgang-Nummer).
     */
    @FXML
    private TextField arbeitsgangNummer;

    /**
     * Combo box for selecting the part (Teil) to which this work step belongs.
     */
    @FXML
    private ComboBox<String> teilNummerCombo;

    /**
     * Text field for entering the processing duration in minutes.
     */
    @FXML
    private TextField bearbeitungsdauerMin;

    /**
     * Combo box for selecting the machine (Maschine) used in this work step.
     */
    @FXML
    private ComboBox<String> maschinenNummer;

    /**
     * Initializes the controller after the FXML fields have been injected.
     * <p>
     * Typically used to populate the combo boxes with available parts and machines.
     */
    @FXML
    public void initialize() {
        List<String> maschineNummern = Maschine.maschines.stream()
                .map(Maschine::getMaschinenNummer)
                .toList();

        List<String> teilNummern = Teil.teils.stream()
                .map(Teil::getTeilNummer)
                .toList();

        maschinenNummer.getItems().addAll(maschineNummern);
        teilNummerCombo.getItems().addAll(teilNummern);
    }

    /**
     * Handles the Save button action.
     * <p>
     * Validates the input fields, creates a new {@link Arbeitsplan} instance,
     * and closes the dialog if successful.
     *
     * @param actionEvent The event triggered by clicking the Save button.
     */
    @FXML
    public void handleSave(ActionEvent actionEvent) {
        String gang = arbeitsgangNummer.getText();
        String teilId = teilNummerCombo.getValue();
        String dauer = bearbeitungsdauerMin.getText();
        String maschine = maschinenNummer.getValue();

        if (gang.isEmpty() || teilId == null || dauer.isEmpty() || maschine == null) {
            showAlert("Fehler", "Bitte alle Felder ausfüllen.");
            return;
        }

        try {
            int gangNr = Integer.parseInt(gang);
            int dauerMin = Integer.parseInt(dauer);
            new Arbeitsplan(java.util.UUID.randomUUID().toString(), teilId, gangNr, maschine, dauerMin);
            saved = true;
            dialogStage.close();
        } catch (NumberFormatException e) {
            showAlert("Fehler", "Nummern und Dauer müssen Zahlen sein.");
        }
    }

    /**
     * Displays an error alert dialog with the given title and message.
     *
     * @param title   The title of the alert.
     * @param content The content message of the alert.
     */
    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}


