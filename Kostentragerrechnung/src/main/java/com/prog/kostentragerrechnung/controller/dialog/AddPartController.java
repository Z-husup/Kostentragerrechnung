package com.prog.kostentragerrechnung.controller.dialog;

import com.prog.kostentragerrechnung.model.Teil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/**
 * Controller for the "Add Part" (Teil) dialog.
 * <p>
 * Handles user input to create a new {@link Teil} object, including validation
 * of numeric fields and selection of associated material number.
 */
public class AddPartController {

    /**
     * Indicates whether the part was successfully saved.
     */
    private boolean saved = false;

    /**
     * The dialog stage (window) assigned to this controller.
     */
    private Stage dialogStage;

    /**
     * Sets the stage of this dialog controller.
     *
     * @param dialogStage The stage to be used for the dialog window.
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Returns whether the user successfully saved the part.
     *
     * @return {@code true} if saved, otherwise {@code false}.
     */
    public boolean isSaved() {
        return saved;
    }

    // === FXML fields ===

    /**
     * Text field for entering the part number.
     */
    @FXML
    private TextField teilNummer;

    /**
     * Text field for entering the hierarchical position of the part.
     */
    @FXML
    private TextField unterstrukturPosition;

    /**
     * Text field for entering the quantity of the part.
     */
    @FXML
    private TextField anzahl;

    /**
     * Combo box for selecting the material number associated with the part.
     */
    @FXML
    private ComboBox<String> materialNummer;

    /**
     * Initializes the controller after FXML fields are injected.
     * <p>
     * This method can be used to populate the combo box with material numbers.
     */
    @FXML
    public void initialize() {
        // Optional: Initialize combo box values (e.g. from Material.materials)
    }

    /**
     * Handles the Save button click event.
     * <p>
     * Validates all fields and creates a new {@link Teil} if the input is valid.
     * If successful, the dialog is closed.
     *
     * @param actionEvent The event triggered by the save button.
     */
    @FXML
    public void handleSave(ActionEvent actionEvent) {
        String teil = teilNummer.getText();
        String pos = unterstrukturPosition.getText();
        String anzahlStr = anzahl.getText();
        String material = materialNummer.getValue();

        if (teil.isEmpty() || pos.isEmpty() || anzahlStr.isEmpty() || material == null) {
            showAlert("Fehler", "Bitte alle Felder ausfüllen.");
            return;
        }

        try {
            int position = Integer.parseInt(pos);
            int anzahlVal = Integer.parseInt(anzahlStr);
            new Teil(java.util.UUID.randomUUID().toString(), teil, position, anzahlVal, material);
            saved = true;
            dialogStage.close();
        } catch (NumberFormatException e) {
            showAlert("Fehler", "Position und Anzahl müssen Zahlen sein.");
        }
    }

    /**
     * Displays an error alert with the given title and content message.
     *
     * @param title   The title of the alert dialog.
     * @param content The message to show in the alert.
     */
    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

