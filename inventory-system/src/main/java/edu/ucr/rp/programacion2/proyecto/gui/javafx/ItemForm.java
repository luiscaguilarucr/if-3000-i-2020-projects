package edu.ucr.rp.programacion2.proyecto.gui.javafx;

import edu.ucr.rp.programacion2.proyecto.domain.logic.Catalog;
import edu.ucr.rp.programacion2.proyecto.domain.logic.Inventory;
import edu.ucr.rp.programacion2.proyecto.gui.javafx.util.PaneUtil;
import edu.ucr.rp.programacion2.proyecto.gui.model.PaneViewer;
import edu.ucr.rp.programacion2.proyecto.logic.CatalogService;
import edu.ucr.rp.programacion2.proyecto.logic.InventoryService;
import edu.ucr.rp.programacion2.proyecto.util.builder.ItemBuilder;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class ItemForm implements PaneViewer {
    private InventoryService inventoryService;
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
    private ComboBox<String> inventoryComboBox;
    private ComboBox<String> catalogComboBox;
    private int accountant = 1;
    private ArrayList<Inventory> inventories;
    private ArrayList<Catalog> catalogs;
    private ArrayList<String> schema = new ArrayList<>();

    public GridPane getItemFormPane() {
        GridPane pane = PaneUtil.buildPane();
        initializeInventoryService();
        setupInventoryControls(pane);
        addInventoryHandlers(pane);
        return pane;
    }

    private void initializeInventoryService(){
        inventoryService = new InventoryService();
    }

    private void initializeCatalogService(Inventory inventory){
        catalogService = new CatalogService(inventory);
    }

    private void setupInventoryControls(GridPane pane){
        buildInventoryComboBox(pane);
        confirmInventoryButton = PaneUtil.buildButton("Select inventory", pane, 2, 0);
    }

    private void setupCatalogControls(GridPane pane, Inventory inventory) {
        inventoryNameLabel = PaneUtil.buildLabel(pane, "Inventory selected: ", 0, 1);
        inventoryNameSelectedLabel = PaneUtil.buildLabel(pane, inventory.getName(), 1,1);
        buildCatalogComboBox(pane, inventory);
        confirmCatalogButton = PaneUtil.buildButton("Select catalog", pane, 2, 2);
    }

    private void setupItemControls(GridPane pane, Catalog catalog, Inventory inventory) {
        catalogNameLabel = PaneUtil.buildLabel(pane, "Catalog selected: ", 0, 2);
        catalogNameSelectedLabel = PaneUtil.buildLabel(pane, catalog.getName(), 1, 2);

        accountant = 4;
        for (int i = 0; i < catalog.getSchema().size() - 1; i++) {
            textField = PaneUtil.buildTextInput(catalog.getSchema().get(i), pane, accountant);
            textField.setId(accountant + "");
            accountant++;
        }
        saveItemButton = PaneUtil.buildButton("Save item", pane, 1, catalog.getSchema().size() + 5);
        addItemButton = PaneUtil.buildButton("Add a new item and save", pane, 2, catalog.getSchema().size() + 5);
    }

    private void addInventoryHandlers(GridPane pane) {
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

        //saveItemButton.setOnAction(actionEvent -> generateItem());
    }

    private void addCatalogHandlers(GridPane pane){
        confirmCatalogButton.setOnAction((event) -> {
            if (catalogComboBox.getValue() == null) {
                PaneUtil.showAlert(Alert.AlertType.INFORMATION, "Error, did not select a catalog", "You must select a catalog");
            } else {
                inventoryComboBox.setVisible(false);
                catalogComboBox.setVisible(false);
                confirmCatalogButton.setVisible(false);
                setupItemControls(pane, catalogService.get(catalogComboBox.getValue()), inventoryService.get(inventoryComboBox.getValue()));
            }
        });
    }

    private void generateItem() {

    }

    private ComboBox<String> buildInventoryComboBox(GridPane pane) {
        PaneUtil.buildLabel(pane, "Choose an inventory", 0, 0);
        inventoryComboBox = PaneUtil.buildComboBox(pane, getInventoryNames(), 1,0);
        return inventoryComboBox;
    }

    private ComboBox<String> buildCatalogComboBox(GridPane pane, Inventory inventory) {
        PaneUtil.buildLabel(pane, "Choose a catalog", 0, 1);
        catalogComboBox = PaneUtil.buildComboBox(pane, getCatalogNames(), 1,1);

        return catalogComboBox;
    }

    private List<String> getInventoryNames(){
        ArrayList<String> inventoryNames = new ArrayList();
        inventories = (ArrayList<Inventory>) inventoryService.getAll();
        for (Inventory i:inventories){
            inventoryNames.add(i.getName());
        }
        return inventoryNames;
    }

    private List<String> getCatalogNames(){
        ArrayList<String> catalogNames = new ArrayList<>();
        catalogs = (ArrayList<Catalog>) catalogService.getAll();
        for(Catalog c: catalogs){
            catalogNames.add(c.getName());
        }
        return catalogNames;
    }

    @Override
    public Pane getPane() {
        return getItemFormPane();
    }
}
