package edu.ucr.rp.programacion2.proyecto.gui.javafx;

import edu.ucr.rp.programacion2.proyecto.domain.logic.Catalog;
import edu.ucr.rp.programacion2.proyecto.domain.logic.Inventory;
import edu.ucr.rp.programacion2.proyecto.gui.javafx.util.PaneUtil;
import edu.ucr.rp.programacion2.proyecto.gui.model.PaneViewer;
import edu.ucr.rp.programacion2.proyecto.logic.CatalogService;
import edu.ucr.rp.programacion2.proyecto.util.Utility;
import edu.ucr.rp.programacion2.proyecto.util.builder.ItemBuilder;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class ItemForm implements PaneViewer {
    private CatalogService catalogService;
    private ItemBuilder itemBuilder;
    private TextField textField;
    private Label inventoryNameLabel;
    private Label catalogNameLabel;
    private Label inventoryNameSelectedLabel;
    private Label catalogNameSelectedLabel;
    private Button confirmInventoryButton;
    private Button confirmCatalogButton;
    private Button saveItemButton;
    private Button addItemButton;
    private ComboBox<Inventory> inventoryComboBox;
    private ComboBox<Catalog> catalogComboBox;
    private int accountant = 1;
    private ArrayList<Inventory> inventories = new ArrayList<>();
    private ArrayList<Catalog> catalogs = new ArrayList<>();
    private ArrayList<String> schema = new ArrayList<>();


    public GridPane getItemFormPane() {
        GridPane pane = PaneUtil.buildPane();
        setupControlsInventory(pane);
        addHandlers(pane);
        return pane;
    }

    private void setupControlsInventory(GridPane pane){
        buildComboBoxInventory(pane);
        confirmInventoryButton = PaneUtil.buildButton("Select inventory", pane, 2, 0);
    }

    private void setupControlsCatalog(GridPane pane, Inventory inventory) {
        inventoryNameLabel = PaneUtil.buildLabel(pane, "Inventory selected: ", 0, 1);
        inventoryNameSelectedLabel = PaneUtil.buildLabel(pane, inventory.getName(), 1,0);
        buildComboBoxCatalog(pane, inventory);
        confirmCatalogButton = PaneUtil.buildButton("Select catalog", pane, 2, 1);
    }

    private void setupControlsItem(GridPane pane, Catalog catalog, Inventory inventory) {
        catalogNameLabel = PaneUtil.buildLabel(pane, "Catalog selected: ", 0, 2);
        catalogNameSelectedLabel = PaneUtil.buildLabel(pane, catalog.getName(), 1, 2);
        saveItemButton = PaneUtil.buildButton("Save item", pane, 1, catalog.getSchema().size() + 5);
        addItemButton = PaneUtil.buildButton("Add a new item and save", pane, 2, catalog.getSchema().size() + 5);
        saveItemButton.setAlignment(Pos.CENTER);
        accountant = 4;
        for (int i = 0; i < catalog.getSchema().size() - 1; i++) {
            textField = PaneUtil.buildTextInput(catalog.getSchema().get(i), pane, accountant);
            textField.setId(accountant + "");
            accountant++;
        }
    }

    private void addHandlers(GridPane pane) {
        confirmInventoryButton.setOnAction((event -> {
            if (inventoryComboBox.getValue() == null) {
                PaneUtil.showAlert(Alert.AlertType.INFORMATION, "Error, did not select an inventory", "You must select an inventory");
            } else {
                inventoryComboBox.setDisable(false);
                confirmInventoryButton.setVisible(false);

                setupControlsCatalog(pane, inventoryComboBox.getValue());
                addSecondHandlers(pane);
            }
        }));

        //saveItemButton.setOnAction(actionEvent -> generateItem());
    }

    private void addSecondHandlers(GridPane pane){
        confirmCatalogButton.setOnAction((event) -> {
            if (catalogComboBox.getValue() == null) {
                PaneUtil.showAlert(Alert.AlertType.INFORMATION, "Error, did not select a catalog", "You must select a catalog");
            } else {
                confirmCatalogButton.setDisable(true);
                setupControlsItem(pane, catalogComboBox.getValue(), inventoryComboBox.getValue());
            }
        });
    }

    private void generateItem() {

    }

    private ComboBox buildComboBoxInventory(GridPane pane) {
        PaneUtil.buildLabel(pane, "Choose an inventory", 0, 0);
        inventoryComboBox = PaneUtil.buildComboBox(pane, catalogService.getAll(), 1,0);

        return inventoryComboBox;
    }

    private ComboBox buildComboBoxCatalog(GridPane pane, Inventory inventory) {
        PaneUtil.buildLabel(pane, "Choose a catalog", 0, 1);
        catalogComboBox = PaneUtil.buildComboBox(pane, catalogService.getAll(), 1,1);
        
        return catalogComboBox;
    }

    @Override
    public Pane getPane() {
        return getItemFormPane();
    }
}
