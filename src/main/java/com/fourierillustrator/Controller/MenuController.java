package com.fourierillustrator.Controller;

import java.io.IOException;

import com.fourierillustrator.Main.Main;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class MenuController {

    @FXML private Button scene1Button;
    @FXML private Button scene2Button;
    @FXML private ImageView button1Border;
    @FXML private ImageView button2Border;

    @FXML
    private void initialize() {
        button1Border.setVisible(false);
        button2Border.setVisible(false);
    }

    @FXML
    private void openScene1() throws IOException {
        Main.setScene1FromMenu();
    }

    @FXML
    private void openScene2() throws IOException {
        Main.setScene2FromMenu();
    }

    @FXML
    private void button1Hovered() {
        button1Border.setVisible(true);
    }

    @FXML
    private void button1Exited() {
        button1Border.setVisible(false);
    }

    @FXML
    private void button2Hovered() {
        button2Border.setVisible(true);
    }

    @FXML
    private void button2Exited() {
        button2Border.setVisible(false);
    }
}