package edu.ucr.rp.programacion2.proyecto.gui.javafx.catalog;

import edu.ucr.rp.programacion2.proyecto.domain.Catalog;
import edu.ucr.rp.programacion2.proyecto.gui.javafx.util.PaneUtil;
import edu.ucr.rp.programacion2.proyecto.gui.model.PaneViewer;
import edu.ucr.rp.programacion2.proyecto.gui.panes.main.ManagePane;
import edu.ucr.rp.programacion2.proyecto.logic.CatalogService;
import edu.ucr.rp.programacion2.proyecto.logic.InventoryService;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import edu.ucr.rp.programacion2.proyecto.domain.Inventory;
import edu.ucr.rp.programacion2.proyecto.util.builders.CatalogBuilder;

import java.util.ArrayList;

public class CatalogForm implements PaneViewer {
    private InventoryService inventoryService;
    private CatalogService catalogService;
    private CatalogBuilder catalogBuilder = new CatalogBuilder();
    private TextField catalogNameTextField;
    private TextField featureNameTextField;
    private Label inventoryIndicationLabel;
    private Label catalogNameLabel;
    private Label featureNameLabel;
    private Button confirmInventoryButton;
    private Button addFeatureButton;
    private Button saveCatalogButton;
    private Button cancelButton;
    private Button refreshButton;
    private ComboBox<String> inventoryComboBox;
    private ArrayList<String> schema = new ArrayList<>();
    private Boolean emptySpace = false;
    private Boolean wasAdded = false;
    private GridPane pane;

    public GridPane getCatalogFormPane() {
        pane = PaneUtil.buildPane();
        initializeInventoryService();
        setupInventoryControls();
        addInventoryHandlers();
        return pane;
    }

    public void validateShow(){
        initializeInventoryService();
        if(inventoryService.getAll().size() == 0){
            ManagePane.clearPane();
            PaneUtil.showAlert(Alert.AlertType.INFORMATION, "There are no inventories", "You must add at least one inventory to be able to access this function");
        }else {
            refreshInventory();
        }
    }

    public void validateShowCatalog(Inventory inventory){
        initializeCatalogService(inventory);
        if(catalogService.getAll().size() == 0){
            ManagePane.clearPane();
            PaneUtil.showAlert(Alert.AlertType.INFORMATION, "There are no catalogs", "You must add at least one catalog to the inventory "+inventory.getName()+" to be able to access this function");
        }else {
            ManagePane.clearPane();
        }
    }

    public void refreshInventory() {
        setupInventoryControls();
        initializeInventoryService();
        addInventoryHandlers();
    }

    public void refreshCatalog(){
        inventoryIndicationLabel.setVisible(false);
        inventoryComboBox.setVisible(false);
        cancelButton.setVisible(false);
    }

    private void initializeInventoryService() {
        inventoryService = InventoryService.getInstance();
    }

    private void initializeCatalogService(Inventory inventory) {
        catalogService = new CatalogService(inventory);
    }

    private void setupInventoryControls() {
            buildInventoryComboBox(pane);
            confirmInventoryButton = PaneUtil.buildButtonImage(new Image("select.png"), pane, 2, 0);
            cancelButton = PaneUtil.buildButton("Cancel", pane, 4, 0);
    }

    private void setupCatalogControls() {
        catalogNameLabel = PaneUtil.buildLabel(pane, "Catalog name: ", 0, 1);
        catalogNameTextField = PaneUtil.buildTextInput(pane, 1);
        featureNameLabel = PaneUtil.buildLabel(pane, "Feature:", 0, 2);
        featureNameTextField = PaneUtil.buildTextInput(pane, 2);
        addFeatureButton = PaneUtil.buildButtonImage(new Image("add.png"), pane, 2, 2);
        saveCatalogButton = PaneUtil.buildButton("Save catalog", pane, 1, 6);
    }

    private void addInventoryHandlers() {
            confirmInventoryButton.setOnAction((event) -> {
                if (PaneUtil.addInventoryHandlers(inventoryComboBox, confirmInventoryButton)) {
                    initializeCatalogService(inventoryService.get(inventoryComboBox.getValue()));
                    setupCatalogControls();
                    addCatalogHandlers();
                }
            });
            cancelButton.setOnAction((actionEvent) -> {
                ManagePane.clearPane();
                refreshInventory();
            });
    }

    private void addRefreshButtonHandler() {
        refreshButton = PaneUtil.buildButtonImage(new Image("refresh.png"), pane, 4, 0);
        refreshButton.setOnAction((actionEvent) -> {
            refreshInventory();
        });
    }

    private void addCatalogHandlers() {
        addFeatureButton.setOnAction(actionEvent -> addFeature());
        saveCatalogButton.setOnAction((event) -> {
            generateCatalog();
            ManagePane.clearPane();
            refreshInventory();
        });
        saveCatalogButton.setVisible(false);
    }

    private ComboBox<String> buildInventoryComboBox(GridPane pane) {
        inventoryIndicationLabel = PaneUtil.buildLabel(pane, "Chose an inventory", 0, 0);
        inventoryComboBox = PaneUtil.buildComboBox(pane, inventoryService.getNamesList(), 1, 0);
        return inventoryComboBox;
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
            featureNameTextField.clear();
            featureNameTextField.setStyle("-fx-background-color: #FFFFFF");
            catalogNameTextField.setDisable(true);
            schema.add(featureNameTextField.getText() + "");
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
            getCatalogFormPane();
            PaneUtil.showAlert(Alert.AlertType.INFORMATION, "Catalog added", "The catalog " + catalogNameTextField.getText().toString() + " was added correctly");
        } else {
            PaneUtil.showAlert(Alert.AlertType.INFORMATION, "ERROR when adding", "The catalog " + catalogNameTextField.getText().toString() + " was not added");
        }
    }


    @Override
    public GridPane getPane() {
        return getCatalogFormPane();
    }
}
