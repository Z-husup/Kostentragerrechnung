package com.prog.kostentragerrechnung;

import com.prog.kostentragerrechnung.database.DBManager;

import atlantafx.base.theme.NordLight;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Application extends javafx.application.Application {

    /**
     * The main application stage shared across the app.
     */
    public static Stage mainStage;

    /**
     * The wrapper that contains the scaled group for responsive layout.
     */
    private static StackPane rootWrapper;

    /**
     * The group that is scaled based on window size.
     */
    private static Group scaledGroup;

    /**
     * The base width-height used for scaling the content.
     */
    private static final double BASE_WIDTH = 1045;
    private static final double BASE_HEIGHT = 720;

    /**
     * Main entry point for launching the JavaFX application.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        launch();
    }

    /**
     * Starts the JavaFX application.
     *
     * @param stage The primary stage for this application.
     * @throws Exception If loading FXML fails.
     */
    @Override
    public void start(Stage stage) throws Exception {
        mainStage = stage;

        // Apply NordLight theme stylesheet
        Application.setUserAgentStylesheet(new NordLight().getUserAgentStylesheet());

        // Load the initial FXML scene
        FXMLLoader loader = new FXMLLoader(Application.class.getResource("start-page.fxml"));
        Parent content = loader.load();

        Scene scene = wrapAndScale(content, stage);

        stage.setTitle("Kosten-Trägerrechnung");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.initStyle(StageStyle.DECORATED);
        stage.show();

        DBManager.initDatabase();
    }

    /**
     * Wraps the root content in a scaling group and sets up listeners
     * to automatically scale content based on window resizing.
     *
     * @param content The root node of the scene.
     * @param stage   The stage to listen for resize events.
     * @return A new scene with scaling behavior applied.
     */
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

    /**
     * Adjusts the scale of the group based on the current window size
     * compared to the base width and height.
     *
     * @param stage      The application stage.
     * @param group      The group to scale.
     * @param baseWidth  The reference width.
     * @param baseHeight The reference height.
     */
    private static void scaleContent(Stage stage, Group group, double baseWidth, double baseHeight) {
        double scaleX = stage.getWidth() / baseWidth;
        double scaleY = stage.getHeight() / baseHeight;
        double scale = Math.min(scaleX, scaleY);
        group.setScaleX(scale);
        group.setScaleY(scale);
    }

    /**
     * Switches the current scene content by loading a new FXML file
     * into the scaled group, keeping the stage and scaling intact.
     *
     * @param fxmlPath Path to the FXML resource.
     */
    public static void switchScene(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(Application.class.getResource(fxmlPath));
            Parent newContent = loader.load();

            scaledGroup.getChildren().setAll(newContent);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
