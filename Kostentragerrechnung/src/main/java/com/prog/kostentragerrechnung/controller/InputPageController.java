package com.prog.kostentragerrechnung.controller;

import com.prog.kostentragerrechnung.controller.dialog.*;
import com.prog.kostentragerrechnung.model.*;
import com.prog.kostentragerrechnung.service.CalculationService;
import com.prog.kostentragerrechnung.service.DialogService;
import com.prog.kostentragerrechnung.service.ExportService;
import com.prog.kostentragerrechnung.service.ImportService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.time.LocalDate;

public class InputPageController {

    final ImportService importService = new ImportService();

    final CalculationService calculationService = new CalculationService();

    private DialogService dialogService;

    public void setMainStage(Stage stage) {
        this.dialogService = new DialogService(stage);
    }

    // === Tables ===

    @FXML private TableView<Material> materialsTable;

    @FXML private TableView<Maschine> machinesTable;

    @FXML private TableView<Arbeitsplan> workPlanTable;

    @FXML private TableView<Auftrag> auftragTable;

    @FXML private TableView<Teil> partsTable;

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

        // === Material Table ===
        materialsTable.getItems().setAll(Material.materials);

        // === Maschine Table ===
        machinesTable.getItems().setAll(Maschine.maschines);

        // === Arbeitsplan Table ===
        workPlanTable.getItems().setAll(Arbeitsplan.arbeitsplans);

        // === Auftrag Table ===
        auftragTable.getItems().setAll(Auftrag.auftrags);

        // === Teil Table ===
        partsTable.getItems().setAll(Teil.teils);
    }

    @FXML
    public void handleImport() {
        importService.importExcel(importExcelButton, fileLabel);
        // TODO Optional: refresh table content
    }

    @FXML
    private void handleCalculate(ActionEvent event) {

        calculationService.calculateCosts(
                Arbeitsplan.arbeitsplans,
                Auftrag.auftrags,
                Maschine.maschines,
                Material.materials,
                Teil.teils
        );
    }

    @FXML
    private void openMaschineDialogButtonAction() {
        var controller = dialogService.openDialog("new-maschine-dialog.fxml", "Neue Maschine");
        if (controller instanceof AddMaschineController maschineController && maschineController.isSaved()) {
            System.out.println("✅ Neue Maschine gespeichert.");
            refreshTables();
        }
    }

    @FXML
    private void openMaterialDialogButtonAction() {
        var controller = dialogService.openDialog("new-material-dialog.fxml", "Neues Material");
        if (controller instanceof AddMaterialController materialController && materialController.isSaved()) {
            System.out.println("✅ Neues Material gespeichert.");
            refreshTables();
        }
    }

    @FXML
    private void openOrderDialogButtonAction() {
        var controller = dialogService.openDialog("new-auftrag-dialog.fxml", "Neuer Auftrag");
        if (controller instanceof AddOrderController orderController && orderController.isSaved()) {
            System.out.println("✅ Neuer Auftrag gespeichert.");
            refreshTables();
        }
    }

    @FXML
    private void openPartDialogButtonAction() {
        var controller = dialogService.openDialog("new-teil-dialog.fxml", "Neues Teil");
        if (controller instanceof AddPartController partController && partController.isSaved()) {
            System.out.println("✅ Neues Teil gespeichert.");
            refreshTables();
        }
    }

    @FXML
    private void openWorkPlanDialogButtonAction() {
        var controller = dialogService.openDialog("new-arbeitsplan-dialog.fxml", "Neuer Arbeitsplan");
        if (controller instanceof AddWorkPlanController workPlanController && workPlanController.isSaved()) {
            System.out.println("✅ Neuer Arbeitsplan gespeichert.");
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

    @FXML
    private void handleDeleteMaterial() {
        Material selected = materialsTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Material.materials.remove(selected); // remove from static list
            materialsTable.getItems().remove(selected); // remove from table
        }
    }

    @FXML
    private void handleDeleteMaschine() {
        Maschine selected = machinesTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Maschine.maschines.remove(selected);
            machinesTable.getItems().remove(selected);
        }
    }

    @FXML
    private void handleDeleteArbeitsplan() {
        Arbeitsplan selected = workPlanTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Arbeitsplan.arbeitsplans.remove(selected);
            workPlanTable.getItems().remove(selected);
        }
    }

    @FXML
    private void handleDeleteAuftrag() {
        Auftrag selected = auftragTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Auftrag.auftrags.remove(selected);
            auftragTable.getItems().remove(selected);
        }
    }

    @FXML
    private void handleDeleteTeil() {
        Teil selected = partsTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Teil.teils.remove(selected);
            partsTable.getItems().remove(selected);
        }
    }


}



