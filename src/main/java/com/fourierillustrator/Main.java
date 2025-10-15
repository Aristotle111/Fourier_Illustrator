package com.fourierillustrator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class Main extends Application {

    private static Scene scene;

    public static void main(String[] args) {
        launch();
    }

    public void start(Stage stage) {
        stage.setScene(scene);
        stage.show();
    }

}