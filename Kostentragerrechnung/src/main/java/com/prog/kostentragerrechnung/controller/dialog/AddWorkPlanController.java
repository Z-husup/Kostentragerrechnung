package com.prog.kostentragerrechnung.controller.dialog;

import com.prog.kostentragerrechnung.model.Arbeitsplan;
import com.prog.kostentragerrechnung.model.Maschine;
import com.prog.kostentragerrechnung.model.Teil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.util.List;

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

    }

    @FXML
    public void handleSave(ActionEvent actionEvent) {

    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}


