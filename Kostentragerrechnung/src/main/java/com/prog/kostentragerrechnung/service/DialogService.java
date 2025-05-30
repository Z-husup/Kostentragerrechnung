package com.prog.kostentragerrechnung.service;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * A utility service for opening modal dialog windows in a JavaFX application.
 * <p>
 * This class allows dynamic loading of FXML files as modal dialogs and optionally
 * passes the dialog {@link Stage} to the controller if it has a method named {@code setDialogStage(Stage)}.
 */
public class DialogService {

    /**
     * The primary stage of the application. Used as the owner of modal dialogs.
     */
    private final Stage mainStage;

    /**
     * Constructs a DialogService with the given main application stage.
     *
     * @param mainStage The main stage of the application used as the owner for dialogs.
     */
    public DialogService(Stage mainStage) {
        this.mainStage = mainStage;
    }

    /**
     * Opens a modal dialog window using the specified FXML file and window title.
     * <p>
     * If the controller of the FXML has a method {@code setDialogStage(Stage)},
     * the dialog stage is passed to the controller.
     * The method blocks until the dialog is closed and returns the controller.
     *
     * @param fxmlPath The path to the FXML file relative to the class location.
     * @param title    The title to be displayed on the dialog window.
     * @param <T>      The expected controller type.
     * @return The controller of the loaded FXML file, or {@code null} if loading fails.
     */
    @SuppressWarnings("unchecked")
    public <T> T openDialog(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle(title);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainStage);
            dialogStage.setScene(new Scene(root));

            Object controller = loader.getController();
            try {
                // If the controller has a method setDialogStage(Stage), inject the dialog stage
                controller.getClass()
                        .getMethod("setDialogStage", Stage.class)
                        .invoke(controller, dialogStage);
            } catch (NoSuchMethodException ignored) {
                // Method not found – it's optional
            }

            dialogStage.showAndWait();
            return (T) controller;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

