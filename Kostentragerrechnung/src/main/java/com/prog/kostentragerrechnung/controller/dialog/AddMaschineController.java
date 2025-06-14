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
    private TextField maschinenNummerField;

    @FXML
    private TextField bezeichnungField;

    @FXML
    private TextField kostensatzProStundeField;

    @FXML
    public void initialize() {}

    @FXML
    public void handleSave(ActionEvent event) {

        Maschine maschine = new Maschine();
        maschine.setMaschinenNummer(maschinenNummerField.getText());
        maschine.setBezeichnung(bezeichnungField.getText());
        maschine.setKostensatzProStunde(Double.parseDouble(kostensatzProStundeField.getText()));

        dialogStage.close();
        saved = true;
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

