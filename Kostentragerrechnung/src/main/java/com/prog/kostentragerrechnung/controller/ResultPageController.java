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
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Collection;

public class ResultPageController {

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
    @FXML private TableColumn<Maschine, String> maschinenNummer;
    @FXML private TableColumn<Maschine, String> bezeichnung;
    @FXML private TableColumn<Maschine, Double> kostensatzProStunde;

    @FXML private TableView<Arbeitsplan> workPlanTable;
    @FXML private TableColumn<Arbeitsplan, Integer> arbeitsgangNummer;
    @FXML private TableColumn<Arbeitsplan, String> maschinenNummerArbeitsplan;
    @FXML private TableColumn<Arbeitsplan, Integer> bearbeitungsdauerMin;

    @FXML private TableView<Auftrag> auftragTable;
    @FXML private TableColumn<Auftrag, String> auftragNummer;
    @FXML private TableColumn<Auftrag, Double> auftragMaterialkosten;
    @FXML private TableColumn<Auftrag, Double> auftragFertigungskosten;
    @FXML private TableColumn<Auftrag, String> datumKonstenrechnung;

    @FXML private TableView<Teil> partsTable;
    @FXML private TableColumn<Teil, String> teilNummer;
    @FXML private TableColumn<Teil, String> teilAuftrag;
    @FXML private TableColumn<Teil, String> teilOberTeil;
    @FXML private TableColumn<Teil, Integer> teilAnzahl;
    @FXML private TableColumn<Teil, String> teilArbeitsplanNummer;
    @FXML private TableColumn<Teil, String> teilMaterialNummer;
    @FXML private TableColumn<Teil, Double> teilMaterialkosten;
    @FXML private TableColumn<Teil, Double> teilFertigungskosten;

    // === Buttons ===
    @FXML private Button calculateButton;
    @FXML private Button importExcelButton;
    @FXML private Button helpButton;
    @FXML private Label fileLabel;

    @FXML
    public void initialize() {
        // === MATERIAL ===
        materialNummer.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getMaterialNummer()));
        kostenProStueck.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getKostenProStueck()).asObject());

        // === MASCHINE ===
        maschinenNummer.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getMaschinenNummer()));
        bezeichnung.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getBezeichnung()));
        kostensatzProStunde.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getKostensatzProStunde()).asObject());

        // === ARBEITSPLAN ===
        arbeitsgangNummer.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getArbeitsgangNummer()).asObject());
        maschinenNummerArbeitsplan.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().getMaschine() != null ? data.getValue().getMaschine().getMaschinenNummer() : ""));
        bearbeitungsdauerMin.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getBearbeitungsdauerMin()).asObject());

        // === AUFTRAG ===
        auftragNummer.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAuftragNummer()));
        auftragMaterialkosten.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getMaterialkosten()).asObject());
        auftragFertigungskosten.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getFertigungskosten()).asObject());
        datumKonstenrechnung.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().getDatumKostenrechnung() != null ? data.getValue().getDatumKostenrechnung().toString() : ""));

        // === TEIL ===
        teilNummer.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTeilNummer()));
        teilAuftrag.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().getAuftrag() != null ? data.getValue().getAuftrag().getAuftragNummer() : ""));
        teilOberTeil.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().getParent() != null ? data.getValue().getParent().getTeilNummer() : ""));
        teilAnzahl.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getAnzahl()).asObject());
        teilArbeitsplanNummer.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().getArbeitsplan() != null ? String.valueOf(data.getValue().getArbeitsplan().getArbeitsgangNummer()) : ""));
        teilMaterialNummer.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().getMaterial() != null ? data.getValue().getMaterial().getMaterialNummer() : ""));
        teilMaterialkosten.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getMaterialkosten()).asObject());
        teilFertigungskosten.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getFertigungskosten()).asObject());

        refreshTables();
    }

    private void refreshTables() {
        materialsTable.getItems().setAll(Material.materials);
        machinesTable.getItems().setAll(Maschine.maschines);
        workPlanTable.getItems().setAll(Arbeitsplan.arbeitsplans);
        auftragTable.getItems().setAll(Auftrag.auftrags);
        partsTable.getItems().setAll(Teil.teils);
    }

    @FXML
    public void handleExportSQL() throws SQLException {
        importService.importExcel(importExcelButton, fileLabel, DBManager.getConnection());
        refreshTables();
    }

    @FXML private void handleExportExcel(ActionEvent event) {
        calculationService.calculateCosts();
        Application.switchScene("result-page.fxml");
    }

    @FXML
    public void handleReturn() {
        Application.switchScene("start-page.fxml");
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