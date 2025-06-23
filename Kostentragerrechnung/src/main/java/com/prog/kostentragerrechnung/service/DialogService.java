package com.prog.kostentragerrechnung.service;

import com.prog.kostentragerrechnung.Application;
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
            FXMLLoader loader = new FXMLLoader(Application.class.getResource(fxmlPath));
            Parent root = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle(title);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.setScene(new Scene(root));

            Object controller = loader.getController();

            // Inject dialog stage if method exists
            try {
                controller.getClass()
                        .getMethod("setDialogStage", Stage.class)
                        .invoke(controller, dialogStage);
            } catch (NoSuchMethodException ignored) {}

            // Inject dialog service if method exists
            try {
                controller.getClass()
                        .getMethod("setDialogService", DialogService.class)
                        .invoke(controller, this);
            } catch (NoSuchMethodException ignored) {}

            dialogStage.showAndWait();
            return (T) controller;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}

