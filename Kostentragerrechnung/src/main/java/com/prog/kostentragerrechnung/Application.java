package com.prog.kostentragerrechnung;

import atlantafx.base.theme.CupertinoLight;
import atlantafx.base.theme.NordLight;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Application extends javafx.application.Application {

    public static Stage mainStage;
    private static StackPane rootWrapper;
    private static Group scaledGroup;

    private static final double BASE_WIDTH = 1045;
    private static final double BASE_HEIGHT = 720;

    @Override
    public void start(Stage stage) throws Exception {
        mainStage = stage;

        Application.setUserAgentStylesheet(new NordLight().getUserAgentStylesheet());

        FXMLLoader loader = new FXMLLoader(Application.class.getResource("start-page.fxml"));
        Parent content = loader.load();

        Scene scene = wrapAndScale(content, stage);

        stage.setTitle("Kosten-Trägerrechnung");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.initStyle(StageStyle.DECORATED);
        stage.show();
    }


    private static Scene wrapAndScale(Parent content, Stage stage) {
        scaledGroup = new Group(content);
        rootWrapper = new StackPane(scaledGroup);

        Scene scene = new Scene(rootWrapper);

        stage.widthProperty().addListener((obs, oldVal, newVal) ->
                scaleContent(stage, scaledGroup, BASE_WIDTH, BASE_HEIGHT));
        stage.heightProperty().addListener((obs, oldVal, newVal) ->
                scaleContent(stage, scaledGroup, BASE_WIDTH, BASE_HEIGHT));

        scaleContent(stage, scaledGroup, BASE_WIDTH, BASE_HEIGHT);

        return scene;
    }


    private static void scaleContent(Stage stage, Group group, double baseWidth, double baseHeight) {
        double scaleX = stage.getWidth() / baseWidth;
        double scaleY = stage.getHeight() / baseHeight;
        double scale = Math.min(scaleX, scaleY);
        group.setScaleX(scale);
        group.setScaleY(scale);
    }

    public static void switchScene(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(Application.class.getResource(fxmlPath));
            Parent newContent = loader.load();

            scaledGroup.getChildren().setAll(newContent);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch();
    }
}
