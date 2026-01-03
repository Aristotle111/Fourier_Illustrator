package com.fourierillustrator.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.fourierillustrator.Main.Main;
import com.fourierillustrator.Model.Epicycle;
import com.fourierillustrator.Model.Visualization;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import javafx.util.Duration;


public class SecondaryController {

    @FXML private BorderPane scene2BorderPane;
    @FXML private ChoiceBox<String> sceneSelector2;
    @FXML private VBox sceneSelectorBox2;
    @FXML private Button changeSceneButton2;
    @FXML private Button addTabButton;
    @FXML private Button addEpicycleButton;
    @FXML private TabPane epicycleTabPane;
    @FXML private ColorPicker epicycleStrokeColorPicker;
    @FXML private RadioButton epicycleStrokeSmall;
    @FXML private RadioButton epicycleStrokeMedium;
    @FXML private RadioButton epicycleStrokeLarge;
    @FXML private ToggleGroup epicycleStrokeSizeToggleGroup;
    @FXML private Slider epicycleStrokeOpacitySlider;
    @FXML private ComboBox<String> menuBox;
    @FXML private Slider frequencySlider;
    @FXML private Slider amplitudeSlider;
    @FXML private Slider phaseSlider;
    @FXML private Button playButton;
    @FXML private Button pauseButton;
    @FXML private Button resetButton;
    @FXML private Button removeEpicycleButton;
    @FXML private Tooltip frequencyTooltip;
    @FXML private Tooltip amplitudeTooltip;
    @FXML private Tooltip phaseTooltip;
    @FXML private ImageView audioOnImage;
    @FXML private ImageView audioOffImage;

    private ArrayList<SceneTab> sceneTabs;
    private HashMap<Tab, SceneTab> tabMap;
    private Object[] guiObjects;

    /**
     * Initialize key GUI elements and prepare interface for user
     */
    @FXML
    private void initialize() {
        guiObjects = new Object[]{frequencySlider, amplitudeSlider, phaseSlider, 
                                        removeEpicycleButton, addEpicycleButton, resetButton,
                                    pauseButton, playButton, epicycleStrokeColorPicker, 
                                    epicycleStrokeLarge, epicycleStrokeMedium, epicycleStrokeSmall,
                                epicycleStrokeOpacitySlider};

        sceneTabs = new ArrayList<>();
        tabMap = new HashMap<>();
        handleNewTab();
        frequencyTooltip.setShowDelay(Duration.millis(100));
        amplitudeTooltip.setShowDelay(Duration.millis(100));
        phaseTooltip.setShowDelay(Duration.millis(100));

        menuBox.getSelectionModel().selectedIndexProperty().addListener((_, _, newValue) -> {
            if (menuBox.getItems().size() == 0) return;

            if (newValue != null) {
                Tab selectedTab = epicycleTabPane.getSelectionModel().getSelectedItem();
                SceneTab sceneTab = tabMap.get(selectedTab);
                Epicycle epicycle = sceneTab.getEpicycles().get(newValue.intValue());

                frequencySlider.setValue(epicycle.getFrequency());
                amplitudeSlider.setValue(epicycle.getRadius());
                phaseSlider.setValue(epicycle.getPhaseCoefficient());
            }
        });

        playButton.setOnAction(eh -> {
            getCurrentSceneTab().play();
            playButton.setDisable(true);
            pauseButton.setDisable(false);
            resetButton.setDisable(false);
            menuBox.setDisable(true);

            removeEpicycleButton.setDisable(true);
            addEpicycleButton.setDisable(true);
            
            frequencySlider.setDisable(true);
            amplitudeSlider.setDisable(true);
            phaseSlider.setDisable(true);
        });

        pauseButton.setOnAction(eh -> {
            getCurrentSceneTab().getVisualization().pause();
            pauseButton.setDisable(true);
            playButton.setDisable(false);
            getCurrentSceneTab().setIsPaused(true);
            getCurrentSceneTab().setIsPlaying(false);
        });

        resetButton.setOnAction(eh -> {
            resetButton.setDisable(true);
            playButton.setDisable(false);
            pauseButton.setDisable(true);
            frequencySlider.setDisable(false);
            amplitudeSlider.setDisable(false);
            phaseSlider.setDisable(false);
            menuBox.setDisable(false);
            getCurrentSceneTab().getVisualization().reset();
            getCurrentSceneTab().setIsPlaying(false);
            getCurrentSceneTab().setIsPaused(false);

            removeEpicycleButton.setDisable(false);
            addEpicycleButton.setDisable(false);

            frequencySlider.setDisable(false);
            amplitudeSlider.setDisable(false);
            phaseSlider.setDisable(false);
        });
    }

    /**
     * Switches to scene 1
     */
    @FXML
    private void switchScene() {
        try {
            Main.setScene1();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates new tab upon user request
     */
    @FXML
    private void handleNewTab() {
        SceneTab newTab = new SceneTab();
        epicycleTabPane.getTabs().add(newTab.getTab());
        epicycleTabPane.getSelectionModel().select(newTab.getTab());
        newTab.updateMenu();
    }
    
    /**
     * Changes epicycle stroke size upon user request
     */
    @FXML
    private void handleEpicycleStrokeSizeChanged() {
        String size = ((RadioButton) epicycleStrokeSizeToggleGroup.getSelectedToggle()).getText();
        Tab selectedTab = epicycleTabPane.getSelectionModel().getSelectedItem();
        if (selectedTab != null) {
            switch (size) {
                case "Small":
                    tabMap.get(selectedTab).getVisualization().getPl().setStrokeWidth(2);
                    break;
                case "Medium":
                    tabMap.get(selectedTab).getVisualization().getPl().setStrokeWidth(5);
                    break;
                case "Large":
                    tabMap.get(selectedTab).getVisualization().getPl().setStrokeWidth(10);
                    break;                
            }
        }
    }

    /**
     * Changes epicycle stroke opacity upon user request
     */
    @FXML
    private void handleEpicycleStrokeOpacityChanged() {
        double opacity = epicycleStrokeOpacitySlider.getValue();
        Tab selectedTab = epicycleTabPane.getSelectionModel().getSelectedItem();
        if (selectedTab != null) {
            tabMap.get(selectedTab).getVisualization().getPl().setOpacity(opacity);
        }
    }

    /**
     * Changes epicycle stroke color upon user request
     */
    @FXML
    private void handleEpicycleStrokeColorChanged() {
        Color selectedColor = epicycleStrokeColorPicker.getValue();
        Tab selectedTab = epicycleTabPane.getSelectionModel().getSelectedItem();
        if (selectedTab != null) {
            tabMap.get(selectedTab).getVisualization().getPl().setStroke(selectedColor);
        }
    }

    /**
     * Creates new epicycle upon user request
     */
    @FXML
    private void handleNewEpicycle() {
        Tab selectedTab = epicycleTabPane.getSelectionModel().getSelectedItem();
        if (selectedTab != null) {
            SceneTab sceneTab = tabMap.get(selectedTab);
            sceneTab.addEpicycle();
            removeEpicycleButton.setDisable(false);
        }
    }

    /**
     * Removes current epicycle upon user request
     */
    @FXML
    private void handleRemoveEpicycle() {
        Tab selectedTab = epicycleTabPane.getSelectionModel().getSelectedItem();
        if (selectedTab != null) {
            SceneTab sceneTab = tabMap.get(selectedTab);
            sceneTab.removeEpicycle();
            if (sceneTab.getEpicycles().isEmpty()) removeEpicycleButton.setDisable(true);
        }
    }

    /**
     * Updates epicycles displayed on screen with user changes
     */
    @FXML
    private void handleEpicycleUpdate() {
        if (getCurrentSceneTab().getEpicycles().isEmpty()) return;

        Epicycle currentEpicycle = getCurrentSceneTab().getEpicycles().get(menuBox.getSelectionModel().getSelectedIndex());
        currentEpicycle.changeParams(frequencySlider.getValue(), amplitudeSlider.getValue(), phaseSlider.getValue()); 
        getCurrentSceneTab().getVisualization().updateEpicycles(0);  
    }

    private class SceneTab {
        private Tab tab;
        private Pane pane;
        private Visualization visualization;
        private ArrayList<Epicycle> epicycles;
        private int index;
        private boolean isPlaying = false;
        private boolean isPaused = false;
        
        public SceneTab() {
            index = (sceneTabs.size() == 0) ? 1 : sceneTabs.getLast().getIndex() + 1;
            tab = new Tab("Epicycles " + index);
            pane = new Pane(); pane.setPrefWidth(1096); pane.setPrefHeight(718);
            tab.setContent(pane);
            epicycles = new ArrayList<>();
            visualization = new Visualization(pane, epicycles);
            visualization.setCenter(pane.getPrefWidth() / 2, pane.getPrefHeight() / 2);
            visualization.updateEpicycles(0);
            sceneTabs.add(this);
            tabMap.put(tab, this);

            tab.setOnClosed(_ -> {
                sceneTabs.remove(this);
                if (sceneTabs.isEmpty()) menuBox.getItems().clear();
            });

            epicycleTabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
               updateMenu(newTab);
            });
        }

        /**
         * Adds an epicycle to the tab
         */
        public void addEpicycle() {
            epicycles.add(new Epicycle(1, 50, 0));
            updateMenu();
            visualization.setEpicycles(epicycles);
            visualization.updateEpicycles(0);
        }

        /**
         * Removes current epicycle from the tab
         */
        public void removeEpicycle() {
            epicycles.remove(menuBox.getSelectionModel().getSelectedIndex());
            updateMenu();
            visualization.setEpicycles(epicycles);
            visualization.updateEpicycles(0);
        }

        /**
         * Updates the menu elements to insure they display the correct information
         */
        public void updateMenu() {
            ComboBox<String> comboBox = menuBox;
            epicycleStrokeColorPicker.setValue((Color) visualization.getPl().getStroke());
            epicycleStrokeSizeToggleGroup.selectToggle(
                (visualization.getPl().getStrokeWidth() == 2) ? epicycleStrokeSmall :
                (visualization.getPl().getStrokeWidth() == 5) ? epicycleStrokeMedium :
                epicycleStrokeLarge
            );
            epicycleStrokeOpacitySlider.setValue(visualization.getPl().getOpacity());
            comboBox.getItems().clear();
            for (int i = 0; i < epicycles.size(); i++) {
                comboBox.getItems().add("Epicycle " + (i + 1));
            }

            if (isPlaying) {
                removeEpicycleButton.setDisable(true);
                addEpicycleButton.setDisable(true);
                comboBox.setDisable(true);
                playButton.setDisable(true);
                pauseButton.setDisable(false);
                resetButton.setDisable(false);
                frequencySlider.setDisable(true);
                amplitudeSlider.setDisable(true);
                phaseSlider.setDisable(true);
                return;
            }

            if (epicycles.isEmpty()) {
                addEpicycleButton.setDisable(false);
                comboBox.setDisable(true);
                removeEpicycleButton.setDisable(true);
                playButton.setDisable(true);
                frequencySlider.setDisable(true);
                amplitudeSlider.setDisable(true);
                phaseSlider.setDisable(true);
                pauseButton.setDisable(true);
                resetButton.setDisable(true);
                return;
            }

            addEpicycleButton.setDisable(false);
            removeEpicycleButton.setDisable(false);
            comboBox.setDisable(false);
            comboBox.getSelectionModel().select("Epicycle " + (epicycles.size()));
            playButton.setDisable(false);
            frequencySlider.setDisable(false);
            amplitudeSlider.setDisable(false);
            phaseSlider.setDisable(false);
        }

        /**
         * Updates the menu elements when the user switches tabs
         * @param newTab the new tab that is being switched to
         */
        public void updateMenu(Tab newTab) {
            ComboBox<String> comboBox = menuBox;

            boolean disableGui = (newTab == null) ? true : false;

            
            for (Object o : guiObjects) {
                if (o instanceof Button b) {
                    if (b.getId().equals("addTabButton")) continue;
                    b.setDisable(disableGui);
                }

                else if (o instanceof Slider s) s.setDisable(disableGui);

                else if (o instanceof RadioButton r) r.setDisable(disableGui);
            }

            epicycleStrokeColorPicker.setDisable(disableGui);
            
            if (disableGui) return;

            epicycleStrokeColorPicker.setValue((Color) visualization.getPl().getStroke());
            epicycleStrokeSizeToggleGroup.selectToggle(
                (visualization.getPl().getStrokeWidth() == 2) ? epicycleStrokeSmall :
                (visualization.getPl().getStrokeWidth() == 5) ? epicycleStrokeMedium :
                epicycleStrokeLarge
            );
            epicycleStrokeOpacitySlider.setValue(visualization.getPl().getOpacity());
            comboBox.getItems().clear();
            for (int i = 0; i < tabMap.get(newTab).getEpicycles().size(); i++) {
                comboBox.getItems().add("Epicycle " + (i + 1));
            }
            comboBox.getSelectionModel().select("Epicycle " + (tabMap.get(newTab).getEpicycles().size()));

            if (tabMap.get(newTab).getIsPlaying()) {
                removeEpicycleButton.setDisable(true);
                addEpicycleButton.setDisable(true);
                comboBox.setDisable(true);
                playButton.setDisable(true);
                pauseButton.setDisable(false);
                resetButton.setDisable(false);
                frequencySlider.setDisable(true);
                amplitudeSlider.setDisable(true);
                phaseSlider.setDisable(true);
                return;
            }
            if (tabMap.get(newTab).getIsPaused()) {
                removeEpicycleButton.setDisable(true);
                addEpicycleButton.setDisable(true);
                comboBox.setDisable(true);
                playButton.setDisable(false);
                pauseButton.setDisable(true);
                resetButton.setDisable(false);
                frequencySlider.setDisable(true);
                amplitudeSlider.setDisable(true);
                phaseSlider.setDisable(true);
                return;
            }

            if (tabMap.get(newTab).getEpicycles().isEmpty()) {
                addEpicycleButton.setDisable(false);
                comboBox.setDisable(true);
                removeEpicycleButton.setDisable(true);
                playButton.setDisable(true);
                frequencySlider.setDisable(true);
                amplitudeSlider.setDisable(true);
                phaseSlider.setDisable(true);
                pauseButton.setDisable(true);
                resetButton.setDisable(true);
                return;
            }

            resetButton.setDisable(true);
            addEpicycleButton.setDisable(false);
            pauseButton.setDisable(true);
            playButton.setDisable(true);
            removeEpicycleButton.setDisable(false);
            comboBox.setDisable(false);
            comboBox.getSelectionModel().select("Epicycle " + (tabMap.get(newTab).getEpicycles().size()));
            playButton.setDisable(false);
            frequencySlider.setDisable(false);
            amplitudeSlider.setDisable(false);
            phaseSlider.setDisable(false);
        }

        public Pane getPane() {
            return pane;
        }

        public Visualization getVisualization() {
            return visualization;
        }
        
        public Tab getTab() {
            return tab;
        }

        public int getIndex() {
            return index;
        }

        public void play() {
            visualization.start();
            isPlaying = true;
            isPaused = false;
        }

        public ArrayList<Epicycle> getEpicycles() {
            return epicycles;
        }

        public void setIsPlaying(boolean isPlaying) {
            this.isPlaying = isPlaying;
        }

        public boolean getIsPlaying() {
            return isPlaying;
        }

        public void setIsPaused(boolean isPaused) {
            this.isPaused = isPaused;
        }

        public boolean getIsPaused() {
            return isPaused;
        }
    }

    /**
     * Returns the active scene tab
     * @return the currently selected scene tab
     */
    private SceneTab getCurrentSceneTab() {
            Tab selectedTab = epicycleTabPane.getSelectionModel().getSelectedItem();
            return tabMap.get(selectedTab);
        }

    /**
     * Updates the GUI based on the the newly switched to tab
     */
    @FXML
    private void handleTabChanged() {
        Tab selectedTab = epicycleTabPane.getSelectionModel().getSelectedItem();
        if (selectedTab == null) return;
        SceneTab sceneTab = tabMap.get(selectedTab);
        epicycleStrokeColorPicker.setValue((Color) sceneTab.visualization.getPl().getStroke());
            epicycleStrokeSizeToggleGroup.selectToggle(
                (sceneTab.visualization.getPl().getStrokeWidth() == 2) ? epicycleStrokeSmall :
                (sceneTab.visualization.getPl().getStrokeWidth() == 5) ? epicycleStrokeMedium :
                epicycleStrokeLarge
            );
            epicycleStrokeOpacitySlider.setValue(sceneTab.visualization.getPl().getOpacity());
    }

    @FXML
    private void handlePlayPause() {
        if (audioOnImage.isVisible()) {
            audioOnImage.setVisible(false);
            audioOffImage.setVisible(true);
            Main.pauseAudio();
            return;
        }
        audioOnImage.setVisible(true);
        audioOffImage.setVisible(false);
        Main.playAudio();
    }
}
