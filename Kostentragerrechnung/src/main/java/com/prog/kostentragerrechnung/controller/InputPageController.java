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

/**
 * Controller class for the input page view of the Kosten-Trägerrechnung application.
 * <p>
 * Handles importing data, opening dialogs to create new entities, and triggering cost calculations.
 */
public class InputPageController {

    /**
     * Service for importing data (e.g. from Excel).
     */
    final ImportService importService = new ImportService();

    /**
     * Service for exporting data.
     */
    final ExportService exportService = new ExportService();

    /**
     * Service responsible for calculating costs.
     */
    final CalculationService calculationService = new CalculationService();

    /**
     * Dialog service used to open modal input dialogs.
     */
    private DialogService dialogService;

    /**
     * Sets the primary stage and initializes the {@link DialogService}.
     *
     * @param stage The main application stage.
     */
    public void setMainStage(Stage stage) {
        this.dialogService = new DialogService(stage);
    }

    // === Tables ===

    /**
     * Table displaying imported or created {@link Material} entries.
     */
    @FXML private TableView<Material> materialsTable;

    /**
     * Table displaying {@link Maschine} entries.
     */
    @FXML private TableView<Maschine> machinesTable;

    /**
     * Table displaying {@link Arbeitsplan} entries.
     */
    @FXML private TableView<Arbeitsplan> workPlanTable;

    /**
     * Table displaying {@link Auftrag} entries.
     */
    @FXML private TableView<Auftrag> auftragTable;

    /**
     * Table displaying {@link Teil} entries.
     */
    @FXML private TableView<Teil> partsTable;

    // === Buttons ===

    /**
     * Button to trigger cost calculation.
     */
    @FXML private Button calculateButton;

    /**
     * Button to trigger data import (e.g., Excel files).
     */
    @FXML private Button importExcelButton;

    /**
     * Button to display help information.
     */
    @FXML private Button helpButton;

    @FXML private Label fileLabel;

    /**
     * Initializes the controller.
     * <p>
     * Can be used to set up table columns and load initial data.
     */
    @FXML
    public void initialize() {
        // TODO Optional: set cell value factories and populate tables
    }

    /**
     * Handles data import logic.
     * <p>
     * Can be extended to populate the tables after importing data.
     */
    @FXML
    public void handleImport() {
        importService.importExcel(importExcelButton, fileLabel);
        // TODO Optional: refresh table content
    }

    /**
     * Handles the calculation process by invoking the {@link CalculationService}.
     * Displays a confirmation alert when finished.
     *
     * @param event The action event triggering the calculation.
     */
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

    /**
     * Opens the dialog for creating a new {@link Maschine}.
     */
    @FXML
    private void openMaschineDialogButtonAction() {
        var controller = dialogService.openDialog("/com/prog/kostentragerrechnung/view/new-maschine-dialog.fxml", "Neue Maschine");
        if (controller instanceof AddMaschineController maschineController && maschineController.isSaved()) {
            System.out.println("✅ Neue Maschine gespeichert.");
        }
    }

    /**
     * Opens the dialog for creating a new {@link Material}.
     */
    @FXML
    private void openMaterialDialogButtonAction() {
        var controller = dialogService.openDialog("/com/prog/kostentragerrechnung/view/new-material-dialog.fxml", "Neues Material");
        if (controller instanceof AddMaterialController materialController && materialController.isSaved()) {
            System.out.println("✅ Neues Material gespeichert.");
        }
    }

    /**
     * Opens the dialog for creating a new {@link Auftrag}.
     */
    @FXML
    private void openOrderDialogButtonAction() {
        var controller = dialogService.openDialog("/com/prog/kostentragerrechnung/view/new-auftrag-dialog.fxml", "Neuer Auftrag");
        if (controller instanceof AddOrderController orderController && orderController.isSaved()) {
            System.out.println("✅ Neuer Auftrag gespeichert.");
        }
    }

    /**
     * Opens the dialog for creating a new {@link Teil}.
     */
    @FXML
    private void openPartDialogButtonAction() {
        var controller = dialogService.openDialog("/com/prog/kostentragerrechnung/view/new-teil-dialog.fxml", "Neues Teil");
        if (controller instanceof AddPartController partController && partController.isSaved()) {
            System.out.println("✅ Neues Teil gespeichert.");
        }
    }

    /**
     * Opens the dialog for creating a new {@link Arbeitsplan}.
     */
    @FXML
    private void openWorkPlanDialogButtonAction() {
        var controller = dialogService.openDialog("/com/prog/kostentragerrechnung/view/new-arbeitsplan-dialog.fxml", "Neuer Arbeitsplan");
        if (controller instanceof AddWorkPlanController workPlanController && workPlanController.isSaved()) {
            System.out.println("✅ Neuer Arbeitsplan gespeichert.");
        }
    }

    /**
     * Displays help information in an alert dialog.
     */
    @FXML
    public void handleHelp() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Hilfe");
        alert.setHeaderText("Bedienungshinweise");
        alert.setContentText("Hier können Sie Daten importieren und Berechnungen starten.");
        alert.showAndWait();
    }
}



