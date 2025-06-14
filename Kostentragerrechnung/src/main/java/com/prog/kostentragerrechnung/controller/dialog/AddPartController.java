package com.prog.kostentragerrechnung.controller.dialog;

import com.prog.kostentragerrechnung.model.Arbeitsplan;
import com.prog.kostentragerrechnung.model.Auftrag;
import com.prog.kostentragerrechnung.model.Material;
import com.prog.kostentragerrechnung.model.Teil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert;
import javafx.stage.Stage;


import javafx.scene.control.*;

import java.util.ArrayList;

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
    private TextField teilNummerField;

    @FXML
    private TextField anzahlField;

    @FXML
    private ComboBox<String> materialCombo;

    @FXML
    private ComboBox<String> auftragCombo;

    @FXML
    private ComboBox<String> oberTeilCombo;

    @FXML
    private ComboBox<String> arbeitsplanCombo;

    @FXML
    private CheckBox auftragCheckBox;

    @FXML
    private CheckBox oberTeilCheckBox;

    @FXML
    public void initialize() {
        // Material ComboBox
        materialCombo.getItems().addAll(
                Material.materials.stream()
                        .map(m -> m.getMaterialNummer())
                        .toList()
        );
        // Auftrag ComboBox
        auftragCombo.getItems().addAll(
                Auftrag.auftrags.stream()
                        .map(a -> a.getAuftragNummer())
                        .toList()
        );
        // OberTeil ComboBox
        oberTeilCombo.getItems().addAll(
                Teil.teils.stream()
                        .map(t -> t.getTeilNummer())
                        .toList()
        );
        // Arbeitsplan ComboBox
        arbeitsplanCombo.getItems().addAll(
                Arbeitsplan.arbeitsplans.stream()
                        .map(ap -> "AP" + ap.getArbeitsgangNummer() + " (" +
                                (ap.getMaschine() != null ? ap.getMaschine().getMaschinenNummer() : "NoMaschine") +
                                ", " + ap.getBearbeitungsdauerMin() + "min)")
                        .toList()
        );
    }


    @FXML
    public void handleSave(ActionEvent actionEvent) {
        try {
            String teilNr = teilNummerField.getText().trim();
            int anzahl = Integer.parseInt(anzahlField.getText().trim());

            // 🧩 Material
            String selectedMaterialNr = materialCombo.getValue();
            Material material = Material.materials.stream()
                    .filter(m -> m.getMaterialNummer().equals(selectedMaterialNr))
                    .findFirst()
                    .orElse(null);

            // 📦 Oberteil (optional)
            Teil oberTeil = null;
            if (oberTeilCheckBox.isSelected()) {
                String selectedOberTeilNr = oberTeilCombo.getValue();
                oberTeil = Teil.teils.stream()
                        .filter(t -> t.getTeilNummer().equals(selectedOberTeilNr))
                        .findFirst()
                        .orElse(null);
            }

            // 📑 Auftrag (optional)
            Auftrag auftrag = null;
            if (auftragCheckBox.isSelected()) {
                String selectedAuftragNr = auftragCombo.getValue();
                auftrag = Auftrag.auftrags.stream()
                        .filter(a -> a.getAuftragNummer().equals(selectedAuftragNr))
                        .findFirst()
                        .orElse(null);
            }

            // ⚙️ Arbeitsplan (optional)
            Arbeitsplan arbeitsplan = null;
            String selectedApDisplay = arbeitsplanCombo.getValue();
            if (selectedApDisplay != null) {
                arbeitsplan = Arbeitsplan.arbeitsplans.stream()
                        .filter(ap -> selectedApDisplay.contains("AP" + ap.getArbeitsgangNummer()))
                        .findFirst()
                        .orElse(null);
            }

            // ✅ Construct Teil
            Teil teil = new Teil(auftrag, oberTeil , new ArrayList<>(), 0, 0, anzahl, arbeitsplan, material, teilNr);

            if (auftrag != null) {
                teil.setAuftrag(auftrag);
            }
            if (arbeitsplan != null) {
                teil.setArbeitsplan(arbeitsplan);
            }

            Teil.teils.add(teil);
            saved = true;
            dialogStage.close();

        } catch (Exception e) {
            showAlert("Fehler beim Speichern", "Bitte überprüfen Sie die Eingaben.\n" + e.getMessage());
        }
    }


    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
