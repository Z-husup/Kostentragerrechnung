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
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Collection;

import static java.lang.Math.round;

public class ResultPageController {

    final ImportService importService = new ImportService();
    final CalculationService calculationService = new CalculationService();
    private DialogService dialogService;

    public void setMainStage(Stage stage) {
        this.dialogService = new DialogService(stage);
    }

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

    @FXML private BarChart<String, Number> kostenChart;
    @FXML private TreeView<String> entityTreeView;

    // === Buttons ===
    @FXML private Button calculateButton;
    @FXML private Button importExcelButton;
    @FXML private Button helpButton;
    @FXML private Label fileLabel;

    @FXML
    public void initialize() {
        refreshTables();
        updateKostenChart();
        buildEntityTree();
    }

    private void refreshTables() {

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

    public void updateKostenChart() {
        kostenChart.getData().clear();

        XYChart.Series<String, Number> materialSeries = new XYChart.Series<>();
        materialSeries.setName("Materialkosten");

        XYChart.Series<String, Number> fertigungSeries = new XYChart.Series<>();
        fertigungSeries.setName("Fertigungskosten");

        for (Teil teil : Teil.teils) {
            teil.berechneKosten(true); // ensure costs are up-to-date

            materialSeries.getData().add(new XYChart.Data<>(teil.getTeilNummer(), round(teil.getMaterialkosten())));
            fertigungSeries.getData().add(new XYChart.Data<>(teil.getTeilNummer(), round(teil.getFertigungskosten())));
        }

        kostenChart.getData().addAll(materialSeries, fertigungSeries);
    }

    public void buildEntityTree() {
        TreeItem<String> root = new TreeItem<>("📦 Aufträge");
        root.setExpanded(true);

        for (Auftrag auftrag : Auftrag.auftrags) {
            TreeItem<String> auftragItem = new TreeItem<>("📦 Auftrag: " + auftrag.getAuftragNummer());

            if (auftrag.getTeil() != null) {
                for (Teil teil : auftrag.getTeil()) {
                    TreeItem<String> teilItem = new TreeItem<>("🧩 Teil: " + teil.getTeilNummer());

                    // Material
                    if (teil.getMaterial() != null) {
                        String matInfo = "🧱 Material: " + teil.getMaterial().getMaterialNummer() +
                                " (Kosten: " + String.format("%.2f", teil.getMaterial().getKostenProStueck()) + ")";
                        teilItem.getChildren().add(new TreeItem<>(matInfo));
                    }

                    // Arbeitsplan
                    if (teil.getArbeitsplan() != null) {
                        TreeItem<String> apItem = new TreeItem<>("⚙️ Arbeitsplan: AP" + teil.getArbeitsplan().getArbeitsgangNummer());

                        // Maschine
                        if (teil.getArbeitsplan().getMaschine() != null) {
                            Maschine maschine = teil.getArbeitsplan().getMaschine();
                            String maschineInfo = "🛠 Maschine: " + maschine.getMaschinenNummer() +
                                    " (Kosten/h: " + String.format("%.2f", maschine.getKostensatzProStunde()) + ")";
                            apItem.getChildren().add(new TreeItem<>(maschineInfo));
                        }

                        teilItem.getChildren().add(apItem);
                    }

                    auftragItem.getChildren().add(teilItem);
                }
            }

            root.getChildren().add(auftragItem);
        }

        entityTreeView.setRoot(root);
    }


}