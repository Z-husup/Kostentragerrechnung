package com.prog.kostentragerrechnung.controller;

import com.prog.kostentragerrechnung.model.*;
import com.prog.kostentragerrechnung.service.ExportService;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

import java.util.Collection;

public class ResultPageController {
    final ExportService exportService = new ExportService();

    @FXML
    private TableView<Auftrag> auftragTable;

    @FXML private TableView<Teil> partsTable;

    @FXML
    public void initialize() {
        refreshTables();
    }

    private void refreshTables() {

        // === Auftrag Table ===
        auftragTable.getItems().setAll((Collection<? extends Auftrag>) Auftrag.auftrags);

        // === Teil Table ===
        partsTable.getItems().setAll((Collection<? extends Teil>) Teil.teils);

    }

    private void initializeDiagram() {

    }

    private void initializeTree() {

    }

}
