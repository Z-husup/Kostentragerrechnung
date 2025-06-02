package com.prog.kostentragerrechnung.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ImportService {

    public void importExcelFile (

    ) {

        //TODO Akyl
        //Export to Excel file

        return;
    }

    @FXML
    public void importExcel(Button importExcelButton, Label fileLabel) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выберите Excel файл");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Excel Files", "*.xlsx", "*.xls")
        );


        Stage stage = (Stage) importExcelButton.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            fileLabel.setText("Choosed: " + file.getName());
            String fileName = file.getName();
            fileLabel.setText(fileName);

            Tooltip tooltip = new Tooltip("Selected file: " + file.getAbsolutePath());
            Tooltip.install(fileLabel, tooltip);

            File tempDir = new File("temp");
        if (!tempDir.exists()) {
            tempDir.mkdir();
        }

        File savedFile = new File(tempDir, file.getName());
        try {
            Files.copy(file.toPath(), savedFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File copied to: " + savedFile.getAbsolutePath());

        } catch (IOException e) {
            e.printStackTrace();
            fileLabel.setText("Error copying file: " + e.getMessage());
        }
        } else {
            fileLabel.setText("Not choosed file");
            Tooltip tooltip = new Tooltip("No file selected");
            Tooltip.install(fileLabel, tooltip);
        }
    }

}
