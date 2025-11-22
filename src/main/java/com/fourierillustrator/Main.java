package com.fourierillustrator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private static Scene scene1;
    private static Scene scene2;
    private static Scene currentScene;
    private static Stage primaryStage;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Main.primaryStage = primaryStage;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fourierillustrator/primary.fxml"));
        scene1 = new Scene(loader.load());
        loader = new FXMLLoader(getClass().getResource("/com/fourierillustrator/secondary.fxml"));
        scene2 = new Scene(loader.load());
        primaryStage.setTitle("GUI");
        primaryStage.setScene(scene1);
        primaryStage.show();

        currentScene = scene1;
    }

    public static void switchScenes() {
        if (currentScene.equals(scene1)) {
            primaryStage.setScene(scene2);
            currentScene = scene2;
            return;
        }

        primaryStage.setScene(scene1);
        currentScene = scene1;
    }

    public static void setScene1() {
        if (currentScene.equals(scene1)) {
            return;
        }
        primaryStage.setScene(scene1);
        currentScene = scene1;
    }

    public static void setScene2() {
        if (currentScene.equals(scene2)) {
            return;
        }
        primaryStage.setScene(scene2);
        currentScene = scene2;
    }
}
