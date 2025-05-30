package com.prog.kostentragerrechnung.controller.dialog;

import com.prog.kostentragerrechnung.model.Teil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class AddPartController {

    private boolean saved = false;
    private Stage dialogStage;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isSaved() {
        return saved;
    }

    @FXML
    private TextField teilNummer;

    @FXML
    private TextField unterstrukturPosition;

    @FXML
    private TextField anzahl;

    @FXML
    private ComboBox<String> materialNummer;

    @FXML
    public void initialize() {
        // Optional: Initialize combo box or values
    }

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


    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

