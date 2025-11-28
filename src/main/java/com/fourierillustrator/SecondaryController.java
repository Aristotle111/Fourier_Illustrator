package com.fourierillustrator;

import java.util.ArrayList;
import java.util.HashMap;

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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;


public class SecondaryController {

    @FXML private Pane mainPane;
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
    @FXML private ToggleGroup epicycleStrokeToggleGroup;
    @FXML private Slider epicycleStrokeOpacitySlider;
    @FXML private ComboBox<String> menuBox;
    @FXML private Slider frequencySlider;
    @FXML private Slider amplitudeSlider;
    @FXML private Slider phaseSlider;
    @FXML private Button playButton;
    @FXML private Button pauseButton;
    @FXML private Button resetButton;
    @FXML private Button removeEpicycleButton;

    private ArrayList<SceneTab> sceneTabs;
    private HashMap<Tab, SceneTab> tabMap;
    private double lastUpdate = 0;

    @FXML
    public void initialize() {
        sceneTabs = new ArrayList<>();
        tabMap = new HashMap<>();
        handleNewTab();

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


    @FXML
    public void switchScene() {
        Main.switchScenes();
    }

    @FXML
    public void handleNewTab() {
        SceneTab newTab = new SceneTab();
        epicycleTabPane.getTabs().add(newTab.getTab());
        epicycleTabPane.getSelectionModel().select(newTab.getTab());
        newTab.updateMenu(menuBox);
    }
    
    @FXML
    public void handleEpicycleStrokeSizeChanged() {
        String size = ((RadioButton) epicycleStrokeToggleGroup.getSelectedToggle()).getText();
        switch (size) {
            case "Small":
                // Logic to update stroke size to small in the selected tab
                break;
            case "Medium":
                // Logic to update stroke size to medium in the selected tab
                break;
            case "Large":
                // Logic to update stroke size to large in the selected tab
                break;
        }
    }

    @FXML
    public void handleEpicycleStrokeOpacityChanged() {
        double opacity = epicycleStrokeOpacitySlider.getValue();
        // Logic to update the opacity of the epicycles in the selected tab
    }

    @FXML
    public void handleEpicycleStrokeColorChanged() {
        Color selectedColor = epicycleStrokeColorPicker.getValue();
        // Logic to update the color of the epicycles in the selected tab
    }

    @FXML
    public void handleNewEpicycle() {
        Tab selectedTab = epicycleTabPane.getSelectionModel().getSelectedItem();
        if (selectedTab != null) {
            SceneTab sceneTab = tabMap.get(selectedTab);
            sceneTab.addEpicycle();
            removeEpicycleButton.setDisable(false);
        }
    }

    @FXML
    public void handleRemoveEpicycle() {
        Tab selectedTab = epicycleTabPane.getSelectionModel().getSelectedItem();
        if (selectedTab != null) {
            SceneTab sceneTab = tabMap.get(selectedTab);
            sceneTab.removeEpicycle();
            if (sceneTab.getEpicycles().isEmpty()) removeEpicycleButton.setDisable(true);
        }
    }

    @FXML
    public void handleEpicycleUpdate() {
        if (getCurrentSceneTab().getEpicycles().isEmpty()) return;

        Epicycle currentEpicycle = getCurrentSceneTab().getEpicycles().get(menuBox.getSelectionModel().getSelectedIndex());
        currentEpicycle.changeParams(frequencySlider.getValue(), amplitudeSlider.getValue(), phaseSlider.getValue()); 
        getCurrentSceneTab().getVisualization().updateEpicycles(lastUpdate);  
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
            visualization.updateEpicycles(lastUpdate);
            sceneTabs.add(this);
            tabMap.put(tab, this);

            tab.setOnClosed(_ -> {
                sceneTabs.remove(this);
                if (sceneTabs.isEmpty()) menuBox.getItems().clear();
            });

            epicycleTabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
               updateMenu(menuBox, newTab);
            });
        }

        public void addEpicycle() {
            epicycles.add(new Epicycle(1, 50, 0));
            updateMenu(menuBox);
            visualization.setEpicycles(epicycles);
            visualization.updateEpicycles(lastUpdate);
        }

        public void removeEpicycle() {
            epicycles.remove(menuBox.getSelectionModel().getSelectedIndex());
            updateMenu(menuBox);
            visualization.setEpicycles(epicycles);
            visualization.updateEpicycles(lastUpdate);
        }

        public void updateMenu(ComboBox<String> comboBox) {
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

        public void updateMenu(ComboBox<String> comboBox, Tab newTab) {
            comboBox.getItems().clear();
            for (int i = 0; i < epicycles.size(); i++) {
                comboBox.getItems().add("Epicycle " + (i + 1));
            }

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
            comboBox.getSelectionModel().select("Epicycle " + (epicycles.size()));
            playButton.setDisable(false);
            frequencySlider.setDisable(false);
            amplitudeSlider.setDisable(false);
            phaseSlider.setDisable(false);
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

    private SceneTab getCurrentSceneTab() {
            Tab selectedTab = epicycleTabPane.getSelectionModel().getSelectedItem();
            return tabMap.get(selectedTab);
        }
}