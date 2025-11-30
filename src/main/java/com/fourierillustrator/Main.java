package com.fourierillustrator;

import java.io.IOException;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {
    private static Scene menuScene;
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

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fourierillustrator/menu.fxml"));
        BorderPane menuRoot = loader.load();
        menuRoot.setOpacity(1.0);
        menuScene = new Scene(menuRoot);

        loader = new FXMLLoader(getClass().getResource("/com/fourierillustrator/primary.fxml"));
        BorderPane root1 = loader.load();
        root1.setOpacity(0);
        scene1 = new Scene(root1);
        
        loader = new FXMLLoader(getClass().getResource("/com/fourierillustrator/secondary.fxml"));
        BorderPane root2 = loader.load();
        root2.setOpacity(0.0);
        scene2 = new Scene(root2);

        primaryStage.setTitle("GUI");
        primaryStage.setScene(menuScene);
        primaryStage.show();

        currentScene = menuScene;
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

    public static void setScene1() throws IOException {
        if (currentScene.equals(scene1)) {
            return;
        }
        BorderPane root2 = (BorderPane) scene2.getRoot();
        FadeTransition fadeOut = new FadeTransition(Duration.millis(400), root2);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(e -> {
            primaryStage.setScene(scene1);
            currentScene = scene1;
            BorderPane root1 = (BorderPane) scene1.getRoot();
            root1.setOpacity(0.0);
            FadeTransition fadeIn = new FadeTransition(Duration.millis(400), root1);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();
        });
        fadeOut.play();
    }

    public static void setScene1FromMenu() throws IOException {
        if (currentScene.equals(scene1)) {
            return;
        }
        BorderPane menuRoot = (BorderPane) menuScene.getRoot();
        FadeTransition fadeOut = new FadeTransition(Duration.millis(400), menuRoot);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(e -> {
            primaryStage.setScene(scene1);
            currentScene = scene1;
            BorderPane root1 = (BorderPane) scene1.getRoot();
            root1.setOpacity(0.0);
            FadeTransition fadeIn = new FadeTransition(Duration.millis(400), root1);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();
        });
        fadeOut.play();
    }

    public static void setScene2() throws IOException {
        if (currentScene.equals(scene2)) {
            return;
        }
        BorderPane root1 = (BorderPane) scene1.getRoot();
        FadeTransition fadeOut = new FadeTransition(Duration.millis(400), root1);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(e -> {
            primaryStage.setScene(scene2);
            currentScene = scene2;
            BorderPane root2 = (BorderPane) scene2.getRoot();
            root2.setOpacity(0.0);
            FadeTransition fadeIn = new FadeTransition(Duration.millis(400), root2);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();
        });
        fadeOut.play();
    }

    public static void setScene2FromMenu() throws IOException {
        if (currentScene.equals(scene2)) {
            return;
        }
        BorderPane menuRoot = (BorderPane) menuScene.getRoot();
        FadeTransition fadeOut = new FadeTransition(Duration.millis(400), menuRoot);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(e -> {
            primaryStage.setScene(scene2);
            currentScene = scene2;
            BorderPane root2 = (BorderPane) scene2.getRoot();
            root2.setOpacity(0.0);
            FadeTransition fadeIn = new FadeTransition(Duration.millis(400), root2);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();
        });
        fadeOut.play();
    }
}
