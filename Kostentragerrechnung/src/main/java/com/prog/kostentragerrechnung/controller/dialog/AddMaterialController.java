package com.prog.kostentragerrechnung.controller.dialog;

import com.prog.kostentragerrechnung.model.Material;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class AddMaterialController {

    private boolean saved = false;
    private Stage dialogStage;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isSaved() {
        return saved;
    }

    @FXML
    private TextField materialNummer;

    /**
     * This method is automatically called after the FXML fields are injected.
     */
    @FXML
    public void initialize() {
        // Optional: Initialize logic
    }

    /**
     * Called when the user clicks the Save button.
     */
    @FXML
    public void handleSave(ActionEvent actionEvent) {
        String nummer = materialNummer.getText();

        if (nummer.isEmpty()) {
            showAlert("Fehler", "Bitte das Materialnummer-Feld ausfüllen.");
            return;
        }

        // For now, default cost to 0.0 if not included in UI
        new Material(java.util.UUID.randomUUID().toString(), nummer, 0.0);
        saved = true;
        dialogStage.close();
    }


    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

}

