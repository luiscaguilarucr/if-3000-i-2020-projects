package edu.ucr.rp.programacion2.proyecto.gui.javafx.catalog;

import edu.ucr.rp.programacion2.proyecto.domain.Catalog;
import edu.ucr.rp.programacion2.proyecto.gui.javafx.util.PaneUtil;
import edu.ucr.rp.programacion2.proyecto.gui.model.PaneViewer;
import edu.ucr.rp.programacion2.proyecto.gui.panes.main.ManagePane;
import edu.ucr.rp.programacion2.proyecto.logic.CatalogService;
import edu.ucr.rp.programacion2.proyecto.logic.InventoryService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import edu.ucr.rp.programacion2.proyecto.domain.Inventory;
import edu.ucr.rp.programacion2.proyecto.util.builders.CatalogBuilder;

import java.util.ArrayList;
import java.util.List;

public class CatalogForm implements PaneViewer {
    private static InventoryService inventoryService;
    private static CatalogService catalogService;
    private static CatalogBuilder catalogBuilder = new CatalogBuilder();
    private static TextField catalogNameTextField;
    private static TextField featureNameTextField;
    private static Label inventoryIndicationLabel;
    private static Label catalogNameLabel;
    private static Label featureNameLabel;
    private static Button addFeatureButton;
    private static Button saveCatalogButton;
    private static Button cancelButton;
    private static ComboBox<String> inventoryComboBox;
    private static ObservableList inventoryObservableList;
    private static List<String> schema = new ArrayList<>();
    private static Boolean emptySpace = false;
    private static Boolean wasAdded = false;
    private static GridPane pane;

    public GridPane getCatalogFormPane() {
        pane = PaneUtil.buildPane();
        initializeInventoryService();
        setupControls();
        addHandlers();
        return pane;
    }

    private static void initializeInventoryService() {
        inventoryService = InventoryService.getInstance();
    }

    private void initializeCatalogService(Inventory inventory) {
        catalogService = new CatalogService(inventory);
    }

    private static void refreshItems() {
        inventoryComboBox.setDisable(false);
        addFeatureButton.setVisible(false);
        catalogNameTextField.setDisable(false);
        featureNameTextField.setDisable(false);
        catalogNameTextField.clear();
        featureNameTextField.clear();
        schema.clear();
        inventoryObservableList.clear();
        inventoryObservableList.addAll(inventoryService.getNamesList());
    }

    private void setupControls() {
        inventoryIndicationLabel = PaneUtil.buildLabel(pane, "Chose an inventory", 0, 0);
        buildInventoryComboBox();
        catalogNameLabel = PaneUtil.buildLabel(pane, "Catalog name: ", 0, 1);
        catalogNameTextField = PaneUtil.buildTextInput(pane, 1);
        featureNameLabel = PaneUtil.buildLabel(pane, "Feature:", 0, 2);
        featureNameTextField = PaneUtil.buildTextInput(pane, 2);
        addFeatureButton = PaneUtil.buildButtonImage(new Image("add.png"), pane, 2, 2);
        saveCatalogButton = PaneUtil.buildButton("Save catalog", pane, 1, 6);
        saveCatalogButton.setVisible(false);
        addFeatureButton.setVisible(false);
        cancelButton = PaneUtil.buildButton("Cancel", pane, 3, 0);
    }

    private void addHandlers() {
        cancelButton.setOnAction(e -> {
            ManagePane.clearPane();
        });
        inventoryComboBox.setOnAction(e -> {
            if (inventoryComboBox.getValue() != null) {
                initializeCatalogService(inventoryService.get(inventoryComboBox.getValue()));
            }
        });
        saveCatalogButton.setOnAction(e -> {
            saveCatalogButton.setVisible(false);
            ManagePane.clearPane();
            generateCatalog();
        });
        featureNameTextField.setOnMouseClicked(e -> {
            addFeatureButton.setVisible(true);
        });
        addFeatureButton.setOnAction(e -> {
            saveCatalogButton.setVisible(true);
            inventoryComboBox.setDisable(true);
            catalogNameTextField.setDisable(true);
            addFeature();
        });
    }

    private void buildInventoryComboBox() {
        inventoryObservableList = FXCollections.observableArrayList();
        inventoryComboBox = PaneUtil.buildComboBox(pane, inventoryObservableList, 1, 0);
    }

    private void addFeature() {
        if (catalogNameTextField.getText().isEmpty()) {
            emptySpace = true;
        }
        if (featureNameTextField.getText().isEmpty()) {
            emptySpace = true;
        }
        if (emptySpace) {
            featureNameTextField.setPromptText("Obligatory field");
            featureNameTextField.setStyle("-fx-background-color: #FDC7C7");
        } else {
            schema.add(featureNameTextField.getText() + "");
            featureNameTextField.clear();
            featureNameTextField.setStyle("-fx-background-color: #FFFFFF");
            catalogNameTextField.setDisable(true);
            featureNameTextField.clear();
            saveCatalogButton.setVisible(true);
        }
    }

    private void generateCatalog() {
        if (catalogNameTextField.getText().isEmpty()) {
            emptySpace = true;
        }
        if (emptySpace) {
            catalogNameTextField.setPromptText("Obligatory field");
            catalogNameTextField.setStyle("-fx-background-color: #FDC7C7");
        } else if (schema.size() > 0) {
            featureNameTextField.setPromptText("");
            featureNameTextField.setStyle("-fx-background-color: #FFFFFF");
            initializeCatalogService(inventoryService.get(inventoryComboBox.getValue()));
            catalogBuilder.withName(catalogNameTextField.getText());
            catalogBuilder.withSchema(schema);
            Catalog catalog = catalogBuilder.build();
            wasAdded = catalogService.add(catalog);
        }
        if (wasAdded) {
            wasAdded = false;
            PaneUtil.showAlert(Alert.AlertType.INFORMATION, "Catalog added", "The catalog was added correctly");
        } else {
            PaneUtil.showAlert(Alert.AlertType.INFORMATION, "ERROR when adding", "The catalog was not added");
        }
    }

    public static void refresh() {
        initializeInventoryService();
        if (inventoryService.getAll().size() == 0) {
            ManagePane.clearPane();
            PaneUtil.showAlert(Alert.AlertType.INFORMATION, "There are no inventories", "You must add at least one inventory to be able to access this function");
        }
        refreshItems();
    }

    @Override
    public GridPane getPane() {
        return getCatalogFormPane();
    }
}
