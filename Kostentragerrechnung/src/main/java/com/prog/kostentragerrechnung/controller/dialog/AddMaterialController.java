package com.prog.kostentragerrechnung.controller.dialog;

import com.prog.kostentragerrechnung.model.Material;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/**
 * Controller for the "Add Material" dialog.
 * <p>
 * Handles user input to create and save a new {@link Material} object.
 */
public class AddMaterialController {

    /**
     * Indicates whether the material was successfully saved.
     */
    private boolean saved = false;

    /**
     * The stage associated with this dialog.
     */
    private Stage dialogStage;

    /**
     * Sets the dialog stage for this controller.
     *
     * @param dialogStage The stage to be used for the dialog window.
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Returns whether the material was saved.
     *
     * @return {@code true} if saved, otherwise {@code false}.
     */
    public boolean isSaved() {
        return saved;
    }

    /**
     * Text field for entering the material number.
     */
    @FXML
    private TextField materialNummer;

    /**
     * Initializes the controller.
     * <p>
     * This method is automatically called after the FXML fields are injected.
     */
    @FXML
    public void initialize() {
        // Optional: Initialize logic
    }

    /**
     * Handles the Save button click event.
     * <p>
     * Validates the input, creates a new {@link Material} object,
     * and closes the dialog if successful.
     *
     * @param actionEvent The event triggered by clicking the Save button.
     */
    @FXML
    public void handleSave(ActionEvent actionEvent) {
        String nummer = materialNummer.getText();

        if (nummer.isEmpty()) {
            showAlert("Fehler", "Bitte das Materialnummer-Feld ausfüllen.");
            return;
        }

        // Create a new Material with default cost 0.0
        try {

            new Material(nummer, 0.0);

            saved = true;
            dialogStage.close();
        } catch (Exception e) {
            showAlert("Fehler", "Ungültiges Datumsformat (z.B. 2024-12-01)");
        }
    }

    /**
     * Shows an alert dialog with the given title and message.
     *
     * @param title   The title of the alert dialog.
     * @param content The message to display in the alert.
     */
    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
