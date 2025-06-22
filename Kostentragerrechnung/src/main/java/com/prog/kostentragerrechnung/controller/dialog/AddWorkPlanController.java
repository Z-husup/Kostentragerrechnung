package com.prog.kostentragerrechnung.controller.dialog;

import com.prog.kostentragerrechnung.model.Arbeitsplan;
import com.prog.kostentragerrechnung.model.Maschine;
import com.prog.kostentragerrechnung.model.Teil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert;
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
    private TextField arbeitsgangNummerField;

    @FXML
    private TextField arbeitsplanBezeichnungField;

    @FXML
    private ComboBox<String> teilCombo;

    @FXML
    private ComboBox<String> maschinenCombo;

    @FXML
    private TextField bearbeitungsdauerMinField;

    @FXML
    public void initialize() {
        // 🧩 Fill Teil ComboBox
        teilCombo.getItems().clear();
        Teil.teils.forEach(t -> teilCombo.getItems().add(t.getTeilNummer()));

        // ⚙️ Fill Maschine ComboBox
        maschinenCombo.getItems().clear();
        Maschine.maschines.forEach(m -> maschinenCombo.getItems().add(m.getMaschinenNummer()));
    }

    @FXML
    public void handleSave(ActionEvent actionEvent) {
        try {
            int agNr = Integer.parseInt(arbeitsgangNummerField.getText().trim());
            int dauer = Integer.parseInt(bearbeitungsdauerMinField.getText().trim());

            String teilNr = teilCombo.getValue();
            String maschineNr = maschinenCombo.getValue();

            if (teilNr == null || maschineNr == null) {
                showAlert("Fehlende Auswahl", "Bitte wählen Sie sowohl Teil als auch Maschine aus.");
                return;
            }

            Teil teil = Teil.teils.stream()
                    .filter(t -> t.getTeilNummer().equals(teilNr))
                    .findFirst()
                    .orElse(null);

            Maschine maschine = Maschine.maschines.stream()
                    .filter(m -> m.getMaschinenNummer().equals(maschineNr))
                    .findFirst()
                    .orElse(null);

            if (teil == null || maschine == null) {
                showAlert("Ungültige Auswahl", "Teil oder Maschine wurde nicht gefunden.");
                return;
            }

            Arbeitsplan ap = new Arbeitsplan(agNr, maschine, dauer);
            ap.setBezeichnung(arbeitsplanBezeichnungField.getText().trim());
            teil.getArbeitsplan().add(ap); // ✅ Append instead of overwrite

            saved = true;
            dialogStage.close();

        } catch (Exception e) {
            showAlert("Fehler beim Speichern", "Bitte überprüfen Sie Ihre Eingaben:\n" + e.getMessage());
        }
    }


    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
