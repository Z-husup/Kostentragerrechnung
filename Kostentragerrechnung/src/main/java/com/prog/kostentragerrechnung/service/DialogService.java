package com.prog.kostentragerrechnung.service;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DialogService {

    private final Stage mainStage;

    public DialogService(Stage mainStage) {
        this.mainStage = mainStage;
    }

    public <T> T openDialog(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle(title);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainStage);
            dialogStage.setScene(new Scene(root));

            // Pass stage to controller if it has a setDialogStage method
            Object controller = loader.getController();
            try {
                controller.getClass()
                        .getMethod("setDialogStage", Stage.class)
                        .invoke(controller, dialogStage);
            } catch (NoSuchMethodException ignored) {
                // Not all controllers need this
            }

            dialogStage.showAndWait();
            return (T) controller;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
