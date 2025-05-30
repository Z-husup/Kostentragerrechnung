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
    private TextField datumKonstenrechnung;

    @FXML
    private TextField auftragNummer;

    @FXML
    public void initialize() {
        // Optional initialization
    }

    @FXML
    public void handleSave(ActionEvent actionEvent) {
        String datum = datumKonstenrechnung.getText();
        String nummer = auftragNummer.getText();

        if (datum.isEmpty() || nummer.isEmpty()) {
            showAlert("Fehler", "Bitte alle Felder ausfüllen.");
            return;
        }

        try {
            LocalDate parsedDate = LocalDate.parse(datum); // format: yyyy-MM-dd
            new Auftrag(java.util.UUID.randomUUID().toString(), nummer, parsedDate);
            saved = true;
            dialogStage.close();
        } catch (Exception e) {
            showAlert("Fehler", "Ungültiges Datumsformat (z.B. 2024-12-01)");
        }
    }


    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
