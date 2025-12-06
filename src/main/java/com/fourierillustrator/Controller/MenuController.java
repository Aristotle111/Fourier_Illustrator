package com.fourierillustrator.Controller;

import java.io.IOException;

import com.fourierillustrator.Main.Main;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MenuController {

    @FXML private Button scene1Button;
    @FXML private Button scene2Button;
    @FXML private ImageView button1Border;
    @FXML private ImageView button2Border;
    @FXML private ImageView audioOnImage;
    @FXML private ImageView audioOffImage;

    private MediaPlayer player;
    private Media media;

    @FXML
    private void initialize() {
        audioOnImage.setVisible(true);
        audioOffImage.setVisible(false);

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
        scene1Button.setDisable(true);    
        scene2Button.setDisable(true);    
    }

    /**
     * Displays scene 2
     * @throws IOException scene not found error
     */
    @FXML
    private void openScene2() throws IOException {
        Main.setScene2FromMenu();
        scene1Button.setDisable(true);
        scene2Button.setDisable(true);
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

    @FXML
    private void handlePlayPause() {
        if (Main.isAudioPlaying()) {
            Main.pauseAudio();
            audioOnImage.setVisible(false);
            audioOffImage.setVisible(true);
        } else {
            Main.playAudio();
            audioOnImage.setVisible(true);
            audioOffImage.setVisible(false);
        }
    }
}
