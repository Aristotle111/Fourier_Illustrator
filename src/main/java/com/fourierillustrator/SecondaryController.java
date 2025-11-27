package com.fourierillustrator;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.fxml.FXML;
import javafx.scene.Scene;
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
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;


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
                phaseSlider.setValue(epicycle.getPhase());
            }
        });

        playButton.setOnAction(eh -> {
            getCurrentSceneTab().getVisualization().start();
            playButton.setDisable(true);
            pauseButton.setDisable(false);
        });

        pauseButton.setOnAction(eh -> {
            getCurrentSceneTab().getVisualization().pause();
            pauseButton.setDisable(true);
            playButton.setDisable(false);
            lastUpdate = getCurrentSceneTab().getVisualization().getLastUpdate();
            //FIX THIS, ISSUE WITH MAKING CHANGES WHILE PAUSED
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
        }
    }

    @FXML
    public void handleEpicycleUpdate() {
        
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
            });

            tab.setOnSelectionChanged(_ -> updateMenu(menuBox));
        }

        public void addEpicycle() {
            epicycles.add(new Epicycle(1, 50, 0));
            updateMenu(menuBox);
            visualization.setEpicycles(epicycles);
            visualization.updateEpicycles(lastUpdate);
        }

        public void updateMenu(ComboBox<String> comboBox) {
            comboBox.getItems().clear();
            for (int i = 0; i < epicycles.size(); i++) {
                comboBox.getItems().add("Epicycle " + (i + 1));
            }

            if (epicycles.isEmpty()) return;
            comboBox.getSelectionModel().select("Epicycle " + (epicycles.size()));
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
        }

        public ArrayList<Epicycle> getEpicycles() {
            return epicycles;
        }
    }

    private SceneTab getCurrentSceneTab() {
            Tab selectedTab = epicycleTabPane.getSelectionModel().getSelectedItem();
            return tabMap.get(selectedTab);
        }
}