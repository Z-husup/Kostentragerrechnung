package com.prog.kostentragerrechnung.controller.dialog;

import com.prog.kostentragerrechnung.model.Auftrag;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.time.LocalDate;

public class AddOrderController {

    private boolean saved = false;

    private Stage dialogStage;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isSaved() {
        return saved;
    }

    @FXML
    private TextField auftragNummerField;

    @FXML
    public void initialize() {

    }

    @FXML
    public void handleSave(ActionEvent actionEvent) {

        Auftrag auftrag = new Auftrag();
        auftrag.setAuftragNummer(auftragNummerField.getText());

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
