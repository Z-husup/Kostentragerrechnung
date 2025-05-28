package com.prog.kostentragerrechnung.controller;

import com.prog.kostentragerrechnung.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class StartPageController {

    @FXML
    private void goToInputPage(ActionEvent event) {
        Application.switchScene("input-page.fxml");
    }

}