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
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @FXML private TreeView<String> entityTreeView;
    @FXML private TreeView<String> reportTreeView;
    @FXML private BarChart<String, Number> reportBarChart;

    // === Buttons ===
    @FXML private Button exportSQLButton;
    @FXML private Button exportExcelButton;
    @FXML private Button returnButton;

    @FXML
    public void initialize() {
        refreshTables();
        fillReportTreeView(reportTreeView, reportBarChart);
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
                data.getValue().getArbeitsplan().isEmpty() ? "-" : String.valueOf(data.getValue().getArbeitsplan().get(0).getArbeitsplanId())
        ));
        teilMaterialNummer.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().getMaterial() != null ? data.getValue().getMaterial().getMaterialNummer() : ""));
        teilMaterialkosten.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getMaterialkosten()).asObject());
        teilFertigungskosten.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getFertigungskosten()).asObject());

        if (Auftrag.auftrags != null)
            auftragTable.getItems().setAll(Auftrag.auftrags);

        if (Teil.teils != null)
            partsTable.getItems().setAll(Teil.teils);
    }

    @FXML
    public void handleExportSQL() throws SQLException {

    }

    @FXML
    private void handleExportExcel(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Exportiere Excel-Datei");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Dateien", "*.xlsx"));
        fileChooser.setInitialFileName("kostenbericht.xlsx");

        File file = fileChooser.showSaveDialog(((Node) event.getSource()).getScene().getWindow());

        if (file != null) {
            ExportService service = new ExportService();
            service.exportExcelFile(file.getAbsolutePath());
        }
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

    public void fillReportTreeView(TreeView<String> reportTreeView, BarChart<String, Number> barChart) {
        TreeItem<String> root = new TreeItem<>("📦 Maschinenberichte");
        root.setExpanded(true);

        Map<String, Integer> maschinenZeiten = new HashMap<>();

        for (Teil teil : Teil.teils) {
            Report report = new Report().createReport(teil, true);
            String maschineNr = report.getMaschineNummer();

            TreeItem<String> teilItem = new TreeItem<>("🧩 Teil: " + report.getTeilNummer());

            teilItem.getChildren().add(new TreeItem<>("📦 Auftrag: " + report.getAuftragNummer()));
            teilItem.getChildren().add(new TreeItem<>("⚙️ Maschine: " + maschineNr));
            teilItem.getChildren().add(new TreeItem<>("⏱ Bearbeitungsdauer: " + report.getBearbeitungsdauerMin() + " min"));
            teilItem.getChildren().add(new TreeItem<>("🛑 Limit überschritten: " + (report.isZeitLimitUeberschritten() ? "✅ Ja" : "❌ Nein")));

            root.getChildren().add(teilItem);

            if (maschineNr != null) {
                maschinenZeiten.merge(maschineNr, report.getBearbeitungsdauerMin(), Integer::sum);
            }
        }

        reportTreeView.setRoot(root);

        // 📊 BarChart anzeigen
        barChart.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Maschinen-Zeiten (min)");

        for (Map.Entry<String, Integer> entry : maschinenZeiten.entrySet()) {
            XYChart.Data<String, Number> data = new XYChart.Data<>(entry.getKey(), entry.getValue());
            series.getData().add(data);
        }

        barChart.getData().add(series);
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
                    if (teil.getArbeitsplan() != null && !teil.getArbeitsplan().isEmpty()) {
                        TreeItem<String> apGroup = new TreeItem<>("⚙️ Arbeitspläne");

                        for (Arbeitsplan plan : teil.getArbeitsplan()) {
                            TreeItem<String> apItem = new TreeItem<>("🔧 AP" + plan.getArbeitsgangNummer() +
                                    " (" + plan.getBearbeitungsdauerMin() + " min)");

                            if (plan.getMaschine() != null) {
                                Maschine maschine = plan.getMaschine();
                                String maschineInfo = "🛠 Maschine: " + maschine.getMaschinenNummer() +
                                        " (Kosten/h: " + String.format("%.2f", maschine.getKostensatzProStunde()) + ")";
                                apItem.getChildren().add(new TreeItem<>(maschineInfo));
                            }

                            apGroup.getChildren().add(apItem);
                        }

                        teilItem.getChildren().add(apGroup);
                    }


                    // Report-Teil
                    teil.berechneKosten(true); // актуализируем
                    TreeItem<String> reportNode = new TreeItem<>("📊 Bericht:");
                    reportNode.getChildren().add(new TreeItem<>("📌 Anzahl: " + teil.getAnzahl()));
                    reportNode.getChildren().add(new TreeItem<>("💶 Materialkosten: " + String.format("%.2f", teil.getMaterialkosten()) + " €"));
                    reportNode.getChildren().add(new TreeItem<>("💰 Materialgemeinkosten: " + String.format("%.2f", teil.getMaterialgemeinkosten()) + " €"));
                    reportNode.getChildren().add(new TreeItem<>("🔧 Fertigungskosten: " + String.format("%.2f", teil.getFertigungskosten()) + " €"));
                    reportNode.getChildren().add(new TreeItem<>("📈 Fertigungsgemeinkosten: " + String.format("%.2f", teil.getFertigungsgemeinkosten()) + " €"));
                    reportNode.getChildren().add(new TreeItem<>("🧮 Herstellkosten: " + String.format("%.2f", teil.getHerstellkosten()) + " €"));
                    double dauerSum = teil.getArbeitsplan().stream()
                            .mapToDouble(Arbeitsplan::getBearbeitungsdauerMin)
                            .sum();
                    reportNode.getChildren().add(new TreeItem<>("⏱ Dauer: " + dauerSum + " min"));
                    reportNode.getChildren().add(new TreeItem<>("📅 Datum: " + LocalDate.now()));

                    teilItem.getChildren().add(reportNode);

                    addChildTeile(teilItem, teil);

                    auftragItem.getChildren().add(teilItem);
                }
            }

            root.getChildren().add(auftragItem);
        }

        entityTreeView.setRoot(root);
    }

    private void addChildTeile(TreeItem<String> parentItem, Teil parentTeil) {
        for (Teil child : parentTeil.getChildren()) {
            TreeItem<String> teilItem = new TreeItem<>("🔹 Unterteil: " + child.getTeilNummer());

            // Material
            if (child.getMaterial() != null) {
                String matInfo = "🧱 Material: " + child.getMaterial().getMaterialNummer() +
                        " (Kosten: " + String.format("%.2f", child.getMaterial().getKostenProStueck()) + ")";
                teilItem.getChildren().add(new TreeItem<>(matInfo));
            }

            // Arbeitsplan
            if (child.getArbeitsplan() != null && !child.getArbeitsplan().isEmpty()) {
                TreeItem<String> apGroup = new TreeItem<>("⚙️ Arbeitspläne");

                for (Arbeitsplan plan : child.getArbeitsplan()) {
                    TreeItem<String> apItem = new TreeItem<>("🔧 AP" + plan.getArbeitsgangNummer() +
                            " (" + plan.getBearbeitungsdauerMin() + " min)");

                    if (plan.getMaschine() != null) {
                        Maschine maschine = plan.getMaschine();
                        String maschineInfo = "🛠 Maschine: " + maschine.getMaschinenNummer() +
                                " (Kosten/h: " + String.format("%.2f", maschine.getKostensatzProStunde()) + ")";
                        apItem.getChildren().add(new TreeItem<>(maschineInfo));
                    }

                    apGroup.getChildren().add(apItem);
                }

                teilItem.getChildren().add(apGroup);
            }

            // Report-Teil
            child.berechneKosten(true); // Aktualisieren
            TreeItem<String> reportNode = new TreeItem<>("📊 Bericht:");
            reportNode.getChildren().add(new TreeItem<>("📌 Anzahl: " + child.getAnzahl()));
            reportNode.getChildren().add(new TreeItem<>("💶 Materialkosten: " + String.format("%.2f", child.getMaterialkosten()) + " €"));
            reportNode.getChildren().add(new TreeItem<>("💰 Materialgemeinkosten: " + String.format("%.2f", child.getMaterialgemeinkosten()) + " €"));
            reportNode.getChildren().add(new TreeItem<>("🔧 Fertigungskosten: " + String.format("%.2f", child.getFertigungskosten()) + " €"));
            reportNode.getChildren().add(new TreeItem<>("📈 Fertigungsgemeinkosten: " + String.format("%.2f", child.getFertigungsgemeinkosten()) + " €"));
            reportNode.getChildren().add(new TreeItem<>("🧮 Herstellkosten: " + String.format("%.2f", child.getHerstellkosten()) + " €"));
            double dauerSum = child.getArbeitsplan().stream()
                    .mapToDouble(Arbeitsplan::getBearbeitungsdauerMin)
                    .sum();
            reportNode.getChildren().add(new TreeItem<>("⏱ Dauer: " + dauerSum + " min"));
            reportNode.getChildren().add(new TreeItem<>("📅 Datum: " + LocalDate.now()));

            teilItem.getChildren().add(reportNode);

            parentItem.getChildren().add(teilItem);

            // Recursive call
            addChildTeile(teilItem, child);
        }
    }


}