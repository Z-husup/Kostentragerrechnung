package com.prog.kostentragerrechnung.controller.dialog;

import com.prog.kostentragerrechnung.model.Arbeitsplan;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class AddWorkPlanController {

    private boolean saved = false;
    private Stage dialogStage;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isSaved() {
        return saved;
    }

    @FXML
    private TextField arbeitsgangNummer;

    @FXML
    private ComboBox<String> teilNummerCombo;

    @FXML
    private TextField bearbeitungsdauerMin;

    @FXML
    private ComboBox<String> maschinenNummer;

    @FXML
    public void initialize() {
        // Optional: Load options for ComboBoxes
    }

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


    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

