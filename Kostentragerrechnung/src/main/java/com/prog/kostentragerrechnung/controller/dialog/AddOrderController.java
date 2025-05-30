package com.prog.kostentragerrechnung.controller.dialog;

import com.prog.kostentragerrechnung.model.Auftrag;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.time.LocalDate;

/**
 * Controller for the "Add Order" (Auftrag) dialog.
 * <p>
 * Handles user input to create a new {@link Auftrag} including validation
 * and error handling for the date and order number.
 */
public class AddOrderController {

    /**
     * Indicates whether the order was successfully saved.
     */
    private boolean saved = false;

    /**
     * The stage (window) used for the dialog.
     */
    private Stage dialogStage;

    /**
     * Sets the dialog stage for this controller.
     *
     * @param dialogStage The JavaFX stage for the dialog window.
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Returns whether the user successfully saved the order.
     *
     * @return {@code true} if saved, otherwise {@code false}.
     */
    public boolean isSaved() {
        return saved;
    }

    // === FXML Fields ===

    /**
     * Text field for entering the date of cost calculation (format: yyyy-MM-dd).
     */
    @FXML
    private TextField datumKonstenrechnung;

    /**
     * Text field for entering the order number.
     */
    @FXML
    private TextField auftragNummer;

    /**
     * Initializes the controller after FXML field injection.
     */
    @FXML
    public void initialize() {
        // Optional initialization
    }

    /**
     * Handles the Save button click event.
     * <p>
     * Validates the input, creates a new {@link Auftrag} if valid,
     * and closes the dialog.
     *
     * @param actionEvent The event triggered by clicking the Save button.
     */
    @FXML
    public void handleSave(ActionEvent actionEvent) {
        String datum = datumKonstenrechnung.getText();
        String nummer = auftragNummer.getText();

        if (datum.isEmpty() || nummer.isEmpty()) {
            showAlert("Fehler", "Bitte alle Felder ausfüllen.");
            return;
        }

        try {
            // Parse date in ISO format
            LocalDate parsedDate = LocalDate.parse(datum); // Expected: yyyy-MM-dd
            new Auftrag(java.util.UUID.randomUUID().toString(), nummer, parsedDate);
            saved = true;
            dialogStage.close();
        } catch (Exception e) {
            showAlert("Fehler", "Ungültiges Datumsformat (z.B. 2024-12-01)");
        }
    }

    /**
     * Displays an alert dialog with the given title and message.
     *
     * @param title   The title of the alert dialog.
     * @param content The message content to display.
     */
    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
