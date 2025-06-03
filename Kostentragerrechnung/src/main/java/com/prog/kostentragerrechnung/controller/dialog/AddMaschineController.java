package com.prog.kostentragerrechnung.controller.dialog;

import com.prog.kostentragerrechnung.model.Maschine;
import com.prog.kostentragerrechnung.model.repositories.MaschineRepo;
import com.prog.kostentragerrechnung.model.Material;
import com.prog.kostentragerrechnung.model.repositories.MaschiineRepo;

import com.prog.kostentragerrechnung.service.DialogService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller for the "Add Maschine" dialog.
 * <p>
 * Handles input validation and creation of new {@link Maschine} objects.
 */
public class AddMaschineController {

    /**
     * Indicates whether the new machine was successfully saved.
     */
    private boolean saved = false;

    /**
     * The dialog stage associated with this controller.
     */
    private Stage dialogStage;

    /**
     * Sets the stage used for the modal dialog.
     *
     * @param dialogStage The dialog's stage.
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Returns whether the machine was successfully saved.
     *
     * @return {@code true} if saved; otherwise {@code false}.
     */
    public boolean isSaved() {
        return saved;
    }

    // === FXML fields ===

    /**
     * Text field for entering the machine number.
     */
    @FXML
    private TextField maschinenNummer;

    /**
     * Text field for entering the machine description.
     */
    @FXML
    private TextField bezeichnung;

    /**
     * Text field for entering the hourly cost rate.
     */
    @FXML
    private TextField kostensatzProStunde;

    /**
     * Initializes the controller after the FXML fields are injected.
     */
    @FXML
    public void initialize() {
        // Optional: Initialization logic here
    }

    /**
     * Handles the Save button click event.
     * <p>
     * Validates the input, creates a new {@link Maschine}, and closes the dialog if successful.
     *
     * @param event The save button event.
     */
    @FXML
    public void handleSave(ActionEvent event) {
        String nummer = maschinenNummer.getText();
        String bez = bezeichnung.getText();
        String kostensatz = kostensatzProStunde.getText();

        if (nummer.isEmpty() || bez.isEmpty() || kostensatz.isEmpty()) {
            showAlert("Fehler", "Bitte alle Felder ausfüllen.");
            return;
        }

        try {

            new Maschine(nummer, bez, Double.parseDouble(kostensatz));

            saved = true;
            dialogStage.close();
        } catch (Exception e) {
            showAlert("Fehler", "Ungültiges Datumsformat (z.B. 2024-12-01)");
        }
    }

    /**
     * Displays an error alert with the given title and message.
     *
     * @param title   The title of the alert.
     * @param content The content text of the alert.
     */
    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

