package edu.ucr.rp.programacion2.proyecto.gui.javafx.item;

import edu.ucr.rp.programacion2.proyecto.domain.Catalog;
import edu.ucr.rp.programacion2.proyecto.domain.Inventory;
import edu.ucr.rp.programacion2.proyecto.gui.javafx.util.PaneUtil;
import edu.ucr.rp.programacion2.proyecto.gui.model.PaneViewer;
import edu.ucr.rp.programacion2.proyecto.logic.CatalogService;
import edu.ucr.rp.programacion2.proyecto.logic.InventoryService;
import edu.ucr.rp.programacion2.proyecto.util.builders.ItemBuilder;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.controlsfx.control.CheckComboBox;

import java.util.ArrayList;

public class DeleteItem implements PaneViewer {
    private InventoryService inventoryService;
    private CatalogService catalogService;
    private ItemBuilder itemBuilder = new ItemBuilder();
    private TextField itemNameTextField;
    private Label inventoryIndicationLabel;
    private Label catalogIndicationLabel;
    private Label itemIndicationLabel;
    private Button confirmInventoryButton;
    private Button confirmCatalogButton;
    private Button deleteItemButton;
    private CheckComboBox checkComboBox;
    private ComboBox<String> inventoryComboBox;
    private ComboBox<String> catalogComboBox;
    private ArrayList<Inventory> inventories = new ArrayList<>();
    private ArrayList<Catalog> catalogs;
    private ArrayList<String> schema = new ArrayList<>();
    private ArrayList<TextField> textFields = new ArrayList<>();

    public GridPane getRemoveItemPane() {
        GridPane pane = PaneUtil.buildPane();
        initializeInventoryService();
        setupInventoryControls(pane);
        addInventoryHandlers(pane);
        return pane;
    }

    private void initializeInventoryService() {
        inventoryService = InventoryService.getInstance();
    }

    private void updateCatalogService(Inventory inventory) {
        catalogService = new CatalogService(inventory);
    }

    private void setupInventoryControls(GridPane pane) {
        if (PaneUtil.setupInventoryControls(inventoryService.getAll())) {
            buildInventoryComboBox(pane);
            confirmInventoryButton = PaneUtil.buildButtonImage(new Image("select.png"), pane, 2, 0);
        }
    }

    private void setupCatalogControls(GridPane pane) {
        if (PaneUtil.setupCatalogControls(pane, inventoryComboBox, catalogService)) {
            buildCatalogComboBox(pane);
            confirmCatalogButton = PaneUtil.buildButtonImage(new Image("select.png"), pane, 2, 1);
        }
    }

    private void setupItemControls(GridPane pane, Catalog catalog) {
        if (catalog.getItems().size() > 0) {
            deleteItemButton = PaneUtil.buildButtonImage(new Image("delete.png"), pane, 2, 2);
            addItemHandlers(catalog);
        }else {
            PaneUtil.showAlert(Alert.AlertType.INFORMATION, "Error, there are no items", "You must add at least one item to be able to access this function");
        }
    }

    private void addInventoryHandlers(GridPane pane) {
        confirmInventoryButton.setOnAction((event -> {
            if(PaneUtil.addInventoryHandlers(inventoryComboBox, inventoryIndicationLabel, confirmInventoryButton)){
                updateCatalogService(inventoryService.get(inventoryComboBox.getValue()));
                setupCatalogControls(pane);
                addCatalogHandlers(pane, catalogService.get(catalogComboBox.getValue()));
            }
        }));
    }

    private void addCatalogHandlers(GridPane pane, Catalog catalog) {
        confirmCatalogButton.setOnAction((event) -> {
            if (PaneUtil.addCatalogHandlers(catalogComboBox, catalogIndicationLabel, confirmCatalogButton)) {
                setupItemControls(pane, catalog);
                buildItemCheckComboBox(pane, catalog);
            }
        });
    }

    private void addItemHandlers(Catalog catalog) {
        deleteItemButton.setOnAction((event) -> {
            if (removeItem(catalog))
                PaneUtil.showAlert(Alert.AlertType.INFORMATION, "Correctly removed item", "Item was removed correctly");
            else {
                PaneUtil.showAlert(Alert.AlertType.INFORMATION, "Error when removing the item", "The item was not removed");
            }
        });
    }

    private void buildInventoryComboBox(GridPane pane) {
        inventoryIndicationLabel = PaneUtil.buildLabel(pane, "Choose an inventory", 0,0);
        inventoryComboBox = PaneUtil.buildComboBox(pane, inventoryService.getNamesList(), 1, 0);
    }

    private void buildCatalogComboBox(GridPane pane) {
        catalogIndicationLabel = PaneUtil.buildLabel(pane, "Choose a catalog", 0, 1);
        catalogComboBox = PaneUtil.buildComboBox(pane, catalogService.getNamesList(), 1, 1);
    }

    private void buildItemCheckComboBox(GridPane pane, Catalog catalog){
        itemIndicationLabel = PaneUtil.buildLabel(pane, "Select the inventory you want to remove",0, 2);
        checkComboBox = PaneUtil.buildCheckComboBox(pane, catalogService.getItemNames(catalog), 1, 2);
    }

    private Boolean removeItem(Catalog catalog) {
        int i = 0;
        Boolean removed = false;
        ObservableList<String> list = checkComboBox.getCheckModel().getCheckedItems();
        for(String s: list){
            catalog.getItems().remove(s);
            i++;
        }
        if(i == list.size()){
            catalogService.edit(catalog);
            removed = true;
        }
        return removed;
    }

    @Override
    public Pane getPane() {
        return getRemoveItemPane();
    }
}
