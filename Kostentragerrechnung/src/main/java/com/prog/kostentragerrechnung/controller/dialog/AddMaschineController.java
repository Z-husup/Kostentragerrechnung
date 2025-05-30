package com.prog.kostentragerrechnung.controller.dialog;

import com.prog.kostentragerrechnung.model.Maschine;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class AddMaschineController {

    private boolean saved = false;
    private Stage dialogStage;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isSaved() {
        return saved;
    }

    @FXML
    private TextField maschinenNummer;

    @FXML
    private TextField bezeichnung;

    @FXML
    private TextField kostensatzProStunde;

    /**
     * This method is automatically called after the FXML fields are injected.
     */
    @FXML
    public void initialize() {
        // Optional: Initialization logic here
    }

    /**
     * Called when the user clicks the Save button.
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
            double ks = Double.parseDouble(kostensatz);
            new Maschine(java.util.UUID.randomUUID().toString(), nummer, bez, ks);
            saved = true;
            dialogStage.close();
        } catch (NumberFormatException e) {
            showAlert("Fehler", "Kostensatz muss eine Zahl sein.");
        }
    }


    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

}

