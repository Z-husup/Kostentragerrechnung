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
    @FXML private TableColumn<Material, Long> materialId;
    @FXML private TableColumn<Material, String> materialNummer;
    @FXML private TableColumn<Material, Double> kostenProStueck;
    /**
     * Table displaying {@link Maschine} entries.
     */
    @FXML private TableView<Maschine> machinesTable;
    @FXML private TableColumn<Maschine, Long> maschinenId;
    @FXML private TableColumn<Maschine, String> maschinenNummer;
    @FXML private TableColumn<Maschine, String> bezeichnung;
    @FXML private TableColumn<Maschine, Double> kostensatzProStunde;
    /**
     * Table displaying {@link Arbeitsplan} entries.
     */
    @FXML private TableView<Arbeitsplan> workPlanTable;
    @FXML private TableColumn<Arbeitsplan, Long> arbeitsplanId;
    @FXML private TableColumn<Arbeitsplan, String> teilNummerArbeitsplan;
    @FXML private TableColumn<Arbeitsplan, Integer> arbeitsgangNummer;
    @FXML private TableColumn<Maschine, String> maschinenNummerArbeitsplan;
    @FXML private TableColumn<Arbeitsplan, Integer> bearbeitungsdauerMin;

    /**
     * Table displaying {@link Auftrag} entries.
     */
    @FXML private TableView<Auftrag> auftragTable;
    @FXML private TableColumn<Auftrag, Long> auftragId;
    @FXML private TableColumn<Auftrag, String> auftragNummer;
    @FXML private TableColumn<Auftrag, LocalDate> datumKonstenrechnung;
    /**
     * Table displaying {@link Teil} entries.
     */
    @FXML private TableView<Teil> partsTable;
    @FXML private TableColumn<Teil, Long> teilId;
    @FXML private TableColumn<Teil, String> teilNummer;
    @FXML private TableColumn<Teil, String> unterstrukturPosition;
    @FXML private TableColumn<Teil, Integer> anzahl;
    @FXML private TableColumn<Teil, String> materialNummerTeil;
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
        refreshTables();
    }


    private void refreshTables() {
        // === Material Table ===
        materialId.setCellValueFactory(new PropertyValueFactory<>("materialId;"));
        materialNummer.setCellValueFactory(new PropertyValueFactory<>("materialNummer"));
        kostenProStueck.setCellValueFactory(new PropertyValueFactory<>("kostenProStueck"));
        materialsTable.getItems().setAll(Material.materials);

        // === Maschine Table ===
        maschinenId.setCellValueFactory(new PropertyValueFactory<>("maschineId"));
        maschinenNummer.setCellValueFactory(new PropertyValueFactory<>("maschinenNummer"));
        bezeichnung.setCellValueFactory(new PropertyValueFactory<>("bezeichnung"));
        kostensatzProStunde.setCellValueFactory(new PropertyValueFactory<>("kostensatzProStunde"));
        machinesTable.getItems().setAll(Maschine.maschines);

        // === Arbeitsplan Table ===
        arbeitsplanId.setCellValueFactory(new PropertyValueFactory<>("arbeitsplanId"));
        teilNummerArbeitsplan.setCellValueFactory(new PropertyValueFactory<>("teilId"));
        arbeitsgangNummer.setCellValueFactory(new PropertyValueFactory<>("arbeitsgangNummer"));
        maschinenNummerArbeitsplan.setCellValueFactory(new PropertyValueFactory<>("maschinenNummer"));
        bearbeitungsdauerMin.setCellValueFactory(new PropertyValueFactory<>("bearbeitungsdauerMin"));
        workPlanTable.getItems().setAll(Arbeitsplan.arbeitsplans);

        // === Auftrag Table ===
        auftragId.setCellValueFactory(new PropertyValueFactory<>("auftragId"));
        auftragNummer.setCellValueFactory(new PropertyValueFactory<>("auftragNummer"));
        datumKonstenrechnung.setCellValueFactory(new PropertyValueFactory<>("datumKonstenrechnung"));
        auftragTable.getItems().setAll(Auftrag.auftrags);

        // === Teil Table ===
        teilId.setCellValueFactory(new PropertyValueFactory<>("teilId"));
        teilNummer.setCellValueFactory(new PropertyValueFactory<>("teilNummer"));
        unterstrukturPosition.setCellValueFactory(new PropertyValueFactory<>("unterstrukturPosition"));
        anzahl.setCellValueFactory(new PropertyValueFactory<>("anzahl"));
        materialNummerTeil.setCellValueFactory(new PropertyValueFactory<>("materialNummer"));
        partsTable.getItems().setAll(Teil.teils);
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
        var controller = dialogService.openDialog("new-maschine-dialog.fxml", "Neue Maschine");
        if (controller instanceof AddMaschineController maschineController && maschineController.isSaved()) {
            System.out.println("✅ Neue Maschine gespeichert.");
            refreshTables();
        }
    }

    /**
     * Opens the dialog for creating a new {@link Material}.
     */
    @FXML
    private void openMaterialDialogButtonAction() {
        var controller = dialogService.openDialog("new-material-dialog.fxml", "Neues Material");
        if (controller instanceof AddMaterialController materialController && materialController.isSaved()) {
            System.out.println("✅ Neues Material gespeichert.");
            refreshTables();
        }
    }

    /**
     * Opens the dialog for creating a new {@link Auftrag}.
     */
    @FXML
    private void openOrderDialogButtonAction() {
        var controller = dialogService.openDialog("new-auftrag-dialog.fxml", "Neuer Auftrag");
        if (controller instanceof AddOrderController orderController && orderController.isSaved()) {
            System.out.println("✅ Neuer Auftrag gespeichert.");
            refreshTables();
        }
    }

    /**
     * Opens the dialog for creating a new {@link Teil}.
     */
    @FXML
    private void openPartDialogButtonAction() {
        var controller = dialogService.openDialog("new-teil-dialog.fxml", "Neues Teil");
        if (controller instanceof AddPartController partController && partController.isSaved()) {
            System.out.println("✅ Neues Teil gespeichert.");
            refreshTables();
        }
    }

    /**
     * Opens the dialog for creating a new {@link Arbeitsplan}.
     */
    @FXML
    private void openWorkPlanDialogButtonAction() {
        var controller = dialogService.openDialog("new-arbeitsplan-dialog.fxml", "Neuer Arbeitsplan");
        if (controller instanceof AddWorkPlanController workPlanController && workPlanController.isSaved()) {
            System.out.println("✅ Neuer Arbeitsplan gespeichert.");
            refreshTables();
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

    /**
     * Handles deletion of the selected {@link Material} from the table and internal list.
     * If no item is selected, the method does nothing.
     */
    @FXML
    private void handleDeleteMaterial() {
        Material selected = materialsTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Material.materials.remove(selected); // remove from static list
            materialsTable.getItems().remove(selected); // remove from table
        }
    }

    /**
     * Handles deletion of the selected {@link Maschine} from the table and internal list.
     * If no item is selected, the method does nothing.
     */
    @FXML
    private void handleDeleteMaschine() {
        Maschine selected = machinesTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Maschine.maschines.remove(selected);
            machinesTable.getItems().remove(selected);
        }
    }

    /**
     * Handles deletion of the selected {@link Arbeitsplan} from the table and internal list.
     * If no item is selected, the method does nothing.
     */
    @FXML
    private void handleDeleteArbeitsplan() {
        Arbeitsplan selected = workPlanTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Arbeitsplan.arbeitsplans.remove(selected);
            workPlanTable.getItems().remove(selected);
        }
    }

    /**
     * Handles deletion of the selected {@link Auftrag} from the table and internal list.
     * If no item is selected, the method does nothing.
     */
    @FXML
    private void handleDeleteAuftrag() {
        Auftrag selected = auftragTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Auftrag.auftrags.remove(selected);
            auftragTable.getItems().remove(selected);
        }
    }

    /**
     * Handles deletion of the selected {@link Teil} from the table and internal list.
     * If no item is selected, the method does nothing.
     */
    @FXML
    private void handleDeleteTeil() {
        Teil selected = partsTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Teil.teils.remove(selected);
            partsTable.getItems().remove(selected);
        }
    }


}



