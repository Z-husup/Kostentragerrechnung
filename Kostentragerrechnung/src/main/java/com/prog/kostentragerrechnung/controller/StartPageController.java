package com.prog.kostentragerrechnung.controller;

import com.prog.kostentragerrechnung.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class StartPageController {

    @FXML
    private void goToInputPage(ActionEvent event) {
        InputPageController controller = Application.switchScene("input-page.fxml");
        if (controller != null) {
            controller.setMainStage(Application.mainStage);
        }
    }
}
