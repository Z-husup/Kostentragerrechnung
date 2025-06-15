package com.prog.kostentragerrechnung.controller;

import com.prog.kostentragerrechnung.Application;
import com.prog.kostentragerrechnung.controller.dialog.*;
import com.prog.kostentragerrechnung.database.DBManager;
import com.prog.kostentragerrechnung.model.*;
import com.prog.kostentragerrechnung.service.CalculationService;
import com.prog.kostentragerrechnung.service.DialogService;
import com.prog.kostentragerrechnung.service.ExportService;
import com.prog.kostentragerrechnung.service.ImportService;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;

public class InputPageController {

    final ImportService importService = new ImportService();
    final CalculationService calculationService = new CalculationService();
    private DialogService dialogService;

    public void setMainStage(Stage stage) {
        this.dialogService = new DialogService(stage);
    }

    // === Tables ===
    @FXML private TableView<Material> materialsTable;
    @FXML private TableColumn<Material, String> materialNummer;
    @FXML private TableColumn<Material, Double> kostenProStueck;

    @FXML private TableView<Maschine> machinesTable;
    @FXML private TableColumn<Maschine, String> maschineId;
    @FXML private TableColumn<Maschine, String> maschinenNummer;
    @FXML private TableColumn<Maschine, String> bezeichnung;
    @FXML private TableColumn<Maschine, Double> kostensatzProStunde;

    @FXML private TableView<Arbeitsplan> workPlanTable;
    @FXML private TableColumn<Arbeitsplan, Integer> arbeitsplanId;
    @FXML private TableColumn<Arbeitsplan, Integer> arbeitsgangNummer;
    @FXML private TableColumn<Arbeitsplan, String> maschinenNummerArbeitsplan;
    @FXML private TableColumn<Arbeitsplan, Integer> bearbeitungsdauerMin;

    @FXML private TableView<Auftrag> auftragTable;
    @FXML private TableColumn<Auftrag, Integer> auftragId;
    @FXML private TableColumn<Auftrag, String> auftragNummer;
    @FXML private TableColumn<Auftrag, Double> auftragMaterialkosten;
    @FXML private TableColumn<Auftrag, Double> auftragFertigungskosten;
    @FXML private TableColumn<Auftrag, String> datumKonstenrechnung;

    @FXML private TableView<Teil> partsTable;
    @FXML private TableColumn<Teil, Integer> teilId;
    @FXML private TableColumn<Teil, String> teilNummer;
    @FXML private TableColumn<Teil, String> teilBezeichnung;
    @FXML private TableColumn<Teil, Integer> teilAnzahl;
    @FXML private TableColumn<Teil, String> teilArbeitsplanNummer;
    @FXML private TableColumn<Teil, String> teilMaterialNummer;
    @FXML private TableColumn<Teil, Double> teilMaterialkosten;
    @FXML private TableColumn<Teil, Double> teilFertigungskosten;
    @FXML private TableColumn<Teil, String> teilAuftrag;
    @FXML private TableColumn<Teil, String> teilOberTeil;

    // === Buttons ===
    @FXML private Button calculateButton;
    @FXML private Button importExcelButton;
    @FXML private Button helpButton;
    @FXML private Label fileLabel;

    @FXML
    public void initialize() {
        refreshTables();
    }

    private void refreshTables() {
        
        // === MATERIAL ===
        materialNummer.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getMaterialNummer()));
        kostenProStueck.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getKostenProStueck()).asObject());

        // === MASCHINE ===
        maschineId.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getMaschineId())));
        maschinenNummer.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getMaschinenNummer()));
        bezeichnung.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getBezeichnung()));
        kostensatzProStunde.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getKostensatzProStunde()).asObject());

        // === ARBEITSPLAN ===
        arbeitsplanId.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getArbeitsplanId()).asObject());
        arbeitsgangNummer.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getArbeitsgangNummer()).asObject());
        maschinenNummerArbeitsplan.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().getMaschine() != null ? data.getValue().getMaschine().getMaschinenNummer() : ""));
        bearbeitungsdauerMin.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getBearbeitungsdauerMin()).asObject());

        // === AUFTRAG ===
        auftragId.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getAuftragId()).asObject());
        auftragNummer.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAuftragNummer()));
        auftragMaterialkosten.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getMaterialkosten()).asObject());
        auftragFertigungskosten.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getFertigungskosten()).asObject());
        datumKonstenrechnung.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDatumKostenrechnung() != null ? data.getValue().getDatumKostenrechnung().toString() : ""));

        // === TEIL ===
        teilId.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getTeilId()).asObject());
        teilNummer.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTeilNummer()));
        teilBezeichnung.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getBezeichnung()));
        teilAnzahl.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getAnzahl()).asObject());
        teilAuftrag.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().getAuftrag() != null ? data.getValue().getAuftrag().getAuftragNummer() : ""));
        teilOberTeil.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().getOberteil() != null ? data.getValue().getOberteil().getTeilNummer() : ""));
        teilArbeitsplanNummer.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().getArbeitsplan() != null ? String.valueOf(data.getValue().getArbeitsplan().getArbeitsplanId()) : ""));
        teilMaterialNummer.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().getMaterial() != null ? data.getValue().getMaterial().getMaterialNummer() : ""));
        teilMaterialkosten.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getMaterialkosten()).asObject());
        teilFertigungskosten.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getFertigungskosten()).asObject());

        materialsTable.getItems().setAll(Material.materials);
        machinesTable.getItems().setAll(Maschine.maschines);
        workPlanTable.getItems().setAll(Arbeitsplan.arbeitsplans);
        auftragTable.getItems().setAll(Auftrag.auftrags);
        partsTable.getItems().setAll(Teil.teils);
    }

    @FXML
    public void handleImport() throws SQLException {
        importService.importExcel(importExcelButton, fileLabel, DBManager.getConnection());
        refreshTables();
    }

    @FXML private void handleCalculate(ActionEvent event) {
        calculationService.calculateCosts();
        calculationService.calculateCostsAndPrintReports();
        Application.switchScene("result-page.fxml");
    }

    @FXML private void openMaschineDialogButtonAction() {
        var controller = dialogService.openDialog("new-maschine-dialog.fxml", "Neue Maschine");
        if (controller instanceof AddMaschineController maschineController && maschineController.isSaved()) {
            refreshTables();
        }
    }

    @FXML private void openMaterialDialogButtonAction() {
        var controller = dialogService.openDialog("new-material-dialog.fxml", "Neues Material");
        if (controller instanceof AddMaterialController materialController && materialController.isSaved()) {
            refreshTables();
        }
    }

    @FXML private void openOrderDialogButtonAction() {
        var controller = dialogService.openDialog("new-auftrag-dialog.fxml", "Neuer Auftrag");
        if (controller instanceof AddOrderController orderController && orderController.isSaved()) {
            refreshTables();
        }
    }

    @FXML private void openPartDialogButtonAction() {
        var controller = dialogService.openDialog("new-teil-dialog.fxml", "Neues Teil");
        if (controller instanceof AddPartController partController && partController.isSaved()) {
            refreshTables();
        }
    }

    @FXML private void openWorkPlanDialogButtonAction() {
        var controller = dialogService.openDialog("new-arbeitsplan-dialog.fxml", "Neuer Arbeitsplan");
        if (controller instanceof AddWorkPlanController workPlanController && workPlanController.isSaved()) {
            refreshTables();
        }
    }

    @FXML
    public void handleHelp() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Hilfe");
        alert.setHeaderText("Bedienungshinweise");
        alert.setContentText("Hier können Sie Daten importieren und Berechnungen starten.");
        alert.showAndWait();
    }

    @FXML private void handleDeleteMaterial() {
        Material selected = materialsTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Material.materials.remove(selected);
            materialsTable.getItems().remove(selected);
        }
    }

    @FXML private void handleDeleteMaschine() {
        Maschine selected = machinesTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Maschine.maschines.remove(selected);
            machinesTable.getItems().remove(selected);
        }
    }

    @FXML private void handleDeleteArbeitsplan() {
        Arbeitsplan selected = workPlanTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Arbeitsplan.arbeitsplans.remove(selected);
            workPlanTable.getItems().remove(selected);
        }
    }

    @FXML private void handleDeleteAuftrag() {
        Auftrag selected = auftragTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Auftrag.auftrags.remove(selected);
            auftragTable.getItems().remove(selected);
        }
    }

    @FXML private void handleDeleteTeil() {
        Teil selected = partsTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Teil.teils.remove(selected);
            partsTable.getItems().remove(selected);
        }
    }
}