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
    private TextField teilBezeichnungField;

    @FXML
    private TextField anzahlField;

    @FXML
    private ComboBox<String> materialCombo;

    @FXML
    private ComboBox<String> auftragCombo;

    @FXML
    private ComboBox<String> oberTeilCombo;

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
    }

    @FXML void makeAuftragTeil(){
        auftragCombo.setDisable(false);
        oberTeilCheckBox.setSelected(false);
    }

    @FXML void makeChildrenTeil(){
        oberTeilCombo.setDisable(false);
        auftragCheckBox.setSelected(false);
    }

    @FXML
    public void handleSave(ActionEvent actionEvent) {
        try {
            String teilNr = teilNummerField.getText().trim();
            String teilBz = teilBezeichnungField.getText().trim();
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

            // ✅ Construct Teil
            Teil teil = new Teil();
            teil.setTeilNummer(teilNr);
            teil.setBezeichnung(teilBz);
            teil.setAnzahl(anzahl);
            teil.setMaterial(material);
            teil.setChildren(new ArrayList<>());

            if (oberTeil != null) {
                oberTeil.getChildren().add(teil);
                teil.setOberteil(oberTeil);
            }

            if (auftrag != null) {
                auftrag.addTeil(teil);
                teil.setAuftrag(auftrag);
            }
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
