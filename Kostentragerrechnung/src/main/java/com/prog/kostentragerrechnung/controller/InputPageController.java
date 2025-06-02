package com.prog.kostentragerrechnung.controller;

import com.prog.kostentragerrechnung.tables.MachineTable;
import com.prog.kostentragerrechnung.tables.PartTable;
import com.prog.kostentragerrechnung.tables.ProductStructureTable;
import com.prog.kostentragerrechnung.tables.WorkPlanTable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import com.prog.kostentragerrechnung.service.CalculationService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import com.prog.kostentragerrechnung.tables.MaterialTable;

public class InputPageController {

    private final CalculationService calculationService = new CalculationService();

    // Materials
    @FXML private TableView<MaterialTable> materialsTable;
    private final ObservableList<MaterialTable> materialsTables = FXCollections.observableArrayList();
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
    @FXML private Label fileLabel;
    private File selectedFile;


    @FXML
    public void initialize() {
        materialsTable.setItems(materialsTables);
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

    @FXML
    private void importExcel(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выберите Excel файл");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Excel Files", "*.xlsx", "*.xls")
        );


        Stage stage = (Stage) importExcelButton.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            selectedFile = file;
            fileLabel.setText("Выбран: " + file.getName());
            String fileName = file.getName();
            fileLabel.setText(fileName);

            Tooltip tooltip = new Tooltip("Выбран файл: " + file.getAbsolutePath());
            Tooltip.install(fileLabel, tooltip);

            File tempDir = new File("temp");
        if (!tempDir.exists()) {
            tempDir.mkdir();
        }

        // Копируем файл во временную папку
        File savedFile = new File(tempDir, file.getName());
        try {
            Files.copy(file.toPath(), savedFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Файл скопирован в: " + savedFile.getAbsolutePath());
            // Здесь можешь вызвать свой ExcelParser
            // ExcelParser.parse(savedFile);
        } catch (IOException e) {
            e.printStackTrace();
            fileLabel.setText("Ошибка при копировании файла.");
                // Вызов метода парсинга Excel
                // ExcelParser.parse(file); // твой метод обработки
        }
        } else {
            fileLabel.setText("Файл не выбран");
        }
    }

    public File getSelectedFile() {
        return selectedFile;
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
                materialsTables,
                partTables,
                machineTables,
                workPlanTableSteps,
                productStructureTableSteps
        );
        System.out.println("Calculation triggered.");
    }

    @FXML
    private void openHelpPage(ActionEvent event) {
        System.out.println("Opening help page...");
    }
}

