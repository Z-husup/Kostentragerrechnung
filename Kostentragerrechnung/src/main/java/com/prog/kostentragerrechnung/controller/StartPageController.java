package com.prog.kostentragerrechnung.controller;

import com.prog.kostentragerrechnung.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * Controller class for the start page of the application.
 * <p>
 * Handles navigation from the start screen to the input page.
 */
public class StartPageController {

    /**
     * Handles the action event triggered by clicking the button to go to the input page.
     * <p>
     * Switches the current scene to the input page view.
     *
     * @param event The action event triggered by the user.
     */
    @FXML
    private void goToInputPage(ActionEvent event) {
        Application.switchScene("input-page.fxml");
    }
}
