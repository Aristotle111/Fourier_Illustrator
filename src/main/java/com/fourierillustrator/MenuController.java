package com.fourierillustrator;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MenuController {

    @FXML private Button scene1Button;
    @FXML private Button scene2Button;

    @FXML
    private void initialize() {

    }

    @FXML
    private void openScene1() throws IOException {
        Main.setScene1FromMenu();
    }

    @FXML
    private void openScene2() throws IOException {
        Main.setScene2FromMenu();
    }
}
