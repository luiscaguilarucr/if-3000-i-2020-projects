package edu.ucr.rp.programacion2.proyecto.gui.javafx;

import edu.ucr.rp.programacion2.proyecto.domain.Catalog;
import edu.ucr.rp.programacion2.proyecto.gui.javafx.util.PaneUtil;
import edu.ucr.rp.programacion2.proyecto.gui.model.PaneViewer;
import edu.ucr.rp.programacion2.proyecto.logic.CatalogService;
import edu.ucr.rp.programacion2.proyecto.logic.InventoryService;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import edu.ucr.rp.programacion2.proyecto.domain.Inventory;
import edu.ucr.rp.programacion2.proyecto.util.builders.CatalogBuilder;

import java.util.ArrayList;
import java.util.List;

public class CatalogForm implements PaneViewer {
    private InventoryService inventoryService;
    private CatalogService catalogService;
    private CatalogBuilder catalogBuilder = new CatalogBuilder();
    private TextField catalogNameTextField;
    private TextField featureNameTextField;
    private Label inventoryNameLabel;
    private Label inventoryNameSelectedLabel;
    private Button confirmInventoryButton;
    private Button addFeatureButton;
    private Button saveCatalogButton;
    private ComboBox<String> inventoryComboBox;
    private ArrayList<Inventory> inventories;
    private ArrayList<String> schema = new ArrayList<>();
    private Boolean emptySpace;
    private Boolean wasAdded;

    public GridPane getCatalogFormPane() {
        GridPane pane = PaneUtil.buildPane();
        setupInventoryControls(pane);
        addInventoryHandlers(pane);
        return pane;
    }
    private void initializeInventoryService(){
        inventoryService = InventoryService.getInstance();
    }

    public void initializeCatalogService(Inventory inventory){
        catalogService = new CatalogService(inventory);
    }

    private void setupInventoryControls(GridPane pane){
        initializeInventoryService();
        buildInventoryComboBox(pane);
        confirmInventoryButton = PaneUtil.buildButton("Select inventory", pane, 2, 0);
    }

    private void setupCatalogControls(GridPane pane, Inventory inventory) {
        inventoryNameLabel = PaneUtil.buildLabel(pane, "Inventory selected: ", 0, 1);
        inventoryNameSelectedLabel = PaneUtil.buildLabel(pane, inventory.getName(), 1,1);
        catalogNameTextField = PaneUtil.buildTextInput("Catalog name: ", pane, 0);
        featureNameTextField = PaneUtil.buildTextInput("Feature: ", pane, 4);
        addFeatureButton = PaneUtil.buildButton("Add feature", pane, 3, 4);
        saveCatalogButton = PaneUtil.buildButton("Save catalog", pane, 1, 6);
    }

    private void addInventoryHandlers(GridPane pane){
        confirmInventoryButton.setOnAction((event -> {
            if (inventoryComboBox.getValue() == null) {
                PaneUtil.showAlert(Alert.AlertType.INFORMATION, "Error, did not select an inventory", "You must select an inventory");
            } else {
                inventoryComboBox.setEditable(false);
                confirmInventoryButton.setVisible(false);
                initializeCatalogService(inventoryService.get(inventoryComboBox.getValue()));
                setupCatalogControls(pane, inventoryService.get(inventoryComboBox.getValue()));
                addCatalogHandlers(pane);
            }
        }));
    }

    private void addCatalogHandlers(GridPane pane) {
        addFeatureButton.setOnAction(actionEvent -> addFeature());
        saveCatalogButton.setOnAction(actionEvent -> generateCatalog());
        saveCatalogButton.setVisible(false);
    }

    private ComboBox<String> buildInventoryComboBox(GridPane pane){
        PaneUtil.buildLabel(pane, "Choose an inventory", 0, 0);
        inventoryComboBox = PaneUtil.buildComboBox(pane, getInventoryNames(), 1,0);
        return inventoryComboBox;
    }

    private void addFeature() {
        emptySpace = false;
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
            featureNameTextField.setPromptText("");
            featureNameTextField.setStyle("-fx-background-color: #FFFFFF");
            catalogNameTextField.setDisable(true);
            schema.add(featureNameTextField.getText()+"");
            featureNameTextField.clear();
            saveCatalogButton.setVisible(true);
        }
    }

    private void generateCatalog() {
        emptySpace = false;
        if (catalogNameTextField.getText().isEmpty()) {
            emptySpace = true;
        }
        if (emptySpace) {
            catalogNameTextField.setPromptText("Obligatory field");
            catalogNameTextField.setStyle("-fx-background-color: #FDC7C7");
        } else if(schema.size() > 0) {
            featureNameTextField.setPromptText("");
            featureNameTextField.setStyle("-fx-background-color: #FFFFFF");

            initializeCatalogService(new Inventory("Carros")); //TODO cambiar lo de carros
            catalogBuilder.withName(catalogNameTextField.getText());
            catalogBuilder.withSchema(schema);

            Catalog catalog = catalogBuilder.build();
            wasAdded = catalogService.add(catalog);
        }
        if (wasAdded) {
            PaneUtil.showAlert(Alert.AlertType.INFORMATION, "Catalog added", "The catalog " + catalogNameTextField.getText().toString() + " was added correctly");
        }else {
            PaneUtil.showAlert(Alert.AlertType.INFORMATION, "ERROR when adding", "The catalog " + catalogNameTextField.getText().toString() + " was not added");
        }
    }

    public Inventory inventorySelected(){
        return inventoryService.get(inventoryComboBox.getValue());
    }

    private List<String> getInventoryNames(){
        ArrayList<String> inventoryNames = new ArrayList();
        inventories = (ArrayList<Inventory>) inventoryService.getAll();
        for (Inventory i:inventories){
            inventoryNames.add(i.getName());
        }
        return inventoryNames;
    }

    @Override
    public Pane getPane() {
        return getCatalogFormPane();
    }
}
