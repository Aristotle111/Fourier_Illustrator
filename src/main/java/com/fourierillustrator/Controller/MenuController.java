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

    /**
     * Displays scene 1
     * @throws IOException scene not found error
     */
    @FXML
    private void openScene1() throws IOException {
        Main.setScene1FromMenu();
    }

    /**
     * Displays scene 2
     * @throws IOException scene not found error
     */
    @FXML
    private void openScene2() throws IOException {
        Main.setScene2FromMenu();
    }

    /**
     * Controls hover logic for showing button 1 border
     */
    @FXML
    private void button1Hovered() {
        button1Border.setVisible(true);
    }

    /**
     * Controls hover logic for hiding button 1 border
     */
    @FXML
    private void button1Exited() {
        button1Border.setVisible(false);
    }

    /**
     * Controls hover logic for showing button 2 border
     */
    @FXML
    private void button2Hovered() {
        button2Border.setVisible(true);
    }

    /**
     * Controls hover logic for hiding button 2 border
     */
    @FXML
    private void button2Exited() {
        button2Border.setVisible(false);
    }
}
