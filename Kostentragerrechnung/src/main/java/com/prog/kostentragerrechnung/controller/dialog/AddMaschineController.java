package com.prog.kostentragerrechnung.controller.dialog;

import com.prog.kostentragerrechnung.model.Maschine;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
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

    @FXML
    public void initialize() {}

    @FXML
    public void handleSave(ActionEvent event) {

    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

