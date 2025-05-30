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
import javafx.stage.Stage;

public class InputPageController {

    final ImportService importService = new ImportService();
    final ExportService exportService = new ExportService();
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

    @FXML
    public void initialize() {
        // TODO Optional: set cell value factories and populate tables
    }

    @FXML
    public void handleImport() {
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

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Berechnung abgeschlossen");
        alert.setHeaderText(null);
        alert.setContentText("Die Kostenberechnung wurde durchgeführt.");
        alert.showAndWait();
    }


    @FXML
    private void openMaschineDialogButtonAction() {
        var controller = dialogService.openDialog("/com/prog/kostentragerrechnung/view/new-maschine-dialog.fxml", "Neue Maschine");
        if (controller instanceof AddMaschineController maschineController && maschineController.isSaved()) {
            System.out.println("✅ Neue Maschine gespeichert.");
        }
    }

    @FXML
    private void openMaterialDialogButtonAction() {
        var controller = dialogService.openDialog("/com/prog/kostentragerrechnung/view/new-material-dialog.fxml", "Neues Material");
        if (controller instanceof AddMaterialController materialController && materialController.isSaved()) {
            System.out.println("✅ Neues Material gespeichert.");
        }
    }

    @FXML
    private void openOrderDialogButtonAction() {
        var controller = dialogService.openDialog("/com/prog/kostentragerrechnung/view/new-auftrag-dialog.fxml", "Neuer Auftrag");
        if (controller instanceof AddOrderController orderController && orderController.isSaved()) {
            System.out.println("✅ Neuer Auftrag gespeichert.");
        }
    }

    @FXML
    private void openPartDialogButtonAction() {
        var controller = dialogService.openDialog("/com/prog/kostentragerrechnung/view/new-teil-dialog.fxml", "Neues Teil");
        if (controller instanceof AddPartController partController && partController.isSaved()) {
            System.out.println("✅ Neues Teil gespeichert.");
        }
    }

    @FXML
    private void openWorkPlanDialogButtonAction() {
        var controller = dialogService.openDialog("/com/prog/kostentragerrechnung/view/new-arbeitsplan-dialog.fxml", "Neuer Arbeitsplan");
        if (controller instanceof AddWorkPlanController workPlanController && workPlanController.isSaved()) {
            System.out.println("✅ Neuer Arbeitsplan gespeichert.");
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
}


