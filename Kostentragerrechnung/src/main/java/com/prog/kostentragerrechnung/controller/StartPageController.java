package com.prog.kostentragerrechnung.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class StartPageController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}