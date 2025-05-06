package com.prog.kostentragerrechnung.controller;

import com.prog.kostentragerrechnung.tables.MachineTable;
import com.prog.kostentragerrechnung.tables.PartTable;
import com.prog.kostentragerrechnung.tables.ProductStructureTable;
import com.prog.kostentragerrechnung.tables.WorkPlanTable;
import com.prog.kostentragerrechnung.service.CalculationService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.paint.Material;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import com.prog.kostentragerrechnung.tables.MaterialTable;

public class InputPageController {

    private final CalculationService calculationService = new CalculationService();

    // Materials
    @FXML private TableView<Material> materialsTable;
    private final ObservableList<Material> materials = FXCollections.observableArrayList();
    @FXML private TableColumn<MaterialTable, String> materialId;
    @FXML private TableColumn<MaterialTable, String> materialName;
    @FXML private TableColumn<MaterialTable, Double> unitCost;
    @FXML private TableColumn<MaterialTable, String> unit;
    @FXML private TableColumn<MaterialTable, Integer> stockQuantity;
    @FXML private Button saveMaterialButton;

    // Parts
    @FXML private TableView<PartTable> partsTable;
    private final ObservableList<PartTable> partTables = FXCollections.observableArrayList();
    @FXML private Button savePartsButton;

    // Machines
    @FXML private TableView<MachineTable> machinesTable;
    private final ObservableList<MachineTable> machineTables = FXCollections.observableArrayList();
    @FXML private Button saveMachinesButton;

    // Work Plan
    @FXML private TableView<WorkPlanTable> workPlanTable;
    private final ObservableList<WorkPlanTable> workPlanTableSteps = FXCollections.observableArrayList();
    @FXML private Button saveWorkPlanButton;

    // Product Structure
    @FXML private TableView<ProductStructureTable> productStructureTable;
    private final ObservableList<ProductStructureTable> productStructureTableSteps = FXCollections.observableArrayList();
    @FXML private Button saveProductStructureButton;

    // Control Buttons
    @FXML private Button calculateButton;
    @FXML private Button importExcelButton;
    @FXML private Button helpButton;

    @FXML
    public void initialize() {
        materialsTable.setItems(materials);
        partsTable.setItems(partTables);
        machinesTable.setItems(machineTables);
        workPlanTable.setItems(workPlanTableSteps);
        productStructureTable.setItems(productStructureTableSteps);

        materialsTable.setEditable(true);
        partsTable.setEditable(true);
        machinesTable.setEditable(true);
        workPlanTable.setEditable(true);
        productStructureTable.setEditable(true);

        configureMaterialTable();
    }

    private void configureMaterialTable() {
        materialId.setCellValueFactory(cellData -> cellData.getValue().materialIdProperty());
        materialName.setCellValueFactory(cellData -> cellData.getValue().materialNameProperty());
        unitCost.setCellValueFactory(cellData -> cellData.getValue().unitCostProperty().asObject());
        unit.setCellValueFactory(cellData -> cellData.getValue().unitProperty());
        stockQuantity.setCellValueFactory(cellData -> cellData.getValue().stockQuantityProperty().asObject());

        materialName.setCellFactory(TextFieldTableCell.forTableColumn());
        unit.setCellFactory(TextFieldTableCell.forTableColumn());
        unitCost.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        stockQuantity.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        materialName.setOnEditCommit(event -> event.getRowValue().setMaterialName(event.getNewValue()));
        unit.setOnEditCommit(event -> event.getRowValue().setUnit(event.getNewValue()));
        unitCost.setOnEditCommit(event -> event.getRowValue().setUnitCost(event.getNewValue()));
        stockQuantity.setOnEditCommit(event -> event.getRowValue().setStockQuantity(event.getNewValue()));
    }

    @FXML
    private void saveMaterialTable(ActionEvent event) {
        System.out.println("Saving materials...");
    }

    @FXML
    private void savePartsTable(ActionEvent event) {
        System.out.println("Saving parts...");
    }

    @FXML
    private void saveMachinesTable(ActionEvent event) {
        System.out.println("Saving machines...");
    }

    @FXML
    private void saveWorkPlanTable(ActionEvent event) {
        System.out.println("Saving work plan...");
    }

    @FXML
    private void saveProductStructureTable(ActionEvent event) {
        System.out.println("Saving product structure...");
    }

    @FXML
    private void calculate(ActionEvent event) {
        calculationService.calculateCosts(
                materials,
                partTables,
                machineTables,
                workPlanTableSteps,
                productStructureTableSteps
        );
        System.out.println("Calculation triggered.");
    }

    @FXML
    private void importExcel(ActionEvent event) {
        System.out.println("Importing Excel file...");
    }

    @FXML
    private void openHelpPage(ActionEvent event) {
        System.out.println("Opening help page...");
    }
}

