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

    private int tabCount = 2;
    private ArrayList<SceneTab> sceneTabs;
    private HashMap<Tab, SceneTab> tabMap;

    @FXML
    public void initialize() {
        sceneTabs = new ArrayList<>();
        tabMap = new HashMap<>();
    }


    @FXML
    public void switchScene() {
        Main.switchScenes();
    }

    @FXML
    public void handleNewTab() {
        SceneTab newTab = new SceneTab();
        sceneTabs.add(newTab);
        epicycleTabPane.getTabs().add(newTab.getTab());
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

    private class SceneTab {
        private Tab tab;
        private Pane pane;
        private Visualization visualization;
        private ArrayList<Epicycle> epicycles;
        
        public SceneTab() {
            tab = new Tab("Epicycles " + tabCount++);
            pane = new Pane(); pane.setPrefWidth(1096); pane.setPrefHeight(718);
            tab.setContent(pane);
            epicycles = new ArrayList<>();
            visualization = new Visualization(pane, epicycles);
            visualization.setCenter(pane.getPrefWidth() / 2, pane.getPrefHeight() / 2);
            visualization.updateEpicycles(0);

            tabMap.put(tab, this);
        }

        public Pane getPane() {
            return pane;
        }
        
        public Tab getTab() {
            return tab;
        }

        public void play() {
            visualization.start();
        }

        public void addEpicycle() {
            epicycles.add(new Epicycle(1, 50, 0));
            updateMenu(menuBox);
            visualization.setEpicycles(epicycles);
            visualization.updateEpicycles(0);
        }

        public void updateMenu(ComboBox<String> comboBox) {
            comboBox.getItems().clear();
            for (int i = 0; i < epicycles.size(); i++) {
                comboBox.getItems().add("Epicycle " + (i + 1));
            }

            if (epicycles.isEmpty()) return;
            comboBox.getSelectionModel().select("Epicycle " + (epicycles.size()));
        }
    }
}