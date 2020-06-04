package edu.ucr.rp.programacion2.proyecto.gui.javafx.item;

import edu.ucr.rp.programacion2.proyecto.domain.Catalog;
import edu.ucr.rp.programacion2.proyecto.domain.Inventory;
import edu.ucr.rp.programacion2.proyecto.domain.Item;
import edu.ucr.rp.programacion2.proyecto.gui.javafx.util.PaneUtil;
import edu.ucr.rp.programacion2.proyecto.gui.model.PaneViewer;
import edu.ucr.rp.programacion2.proyecto.logic.CatalogService;
import edu.ucr.rp.programacion2.proyecto.util.builders.ItemBuilder;
import edu.ucr.rp.programacion2.proyecto.logic.InventoryService;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemForm implements PaneViewer {
    private InventoryService inventoryService;
    private CatalogService catalogService;
    private ItemBuilder itemBuilder = new ItemBuilder();
    private TextField itemNameTextField;
    private Label inventoryIndicationLabel;
    private Label catalogIndicationLabel;
    private Button confirmInventoryButton;
    private Button confirmCatalogButton;
    private Button saveItemButton;
    private ComboBox<String> inventoryComboBox;
    private ComboBox<String> catalogComboBox;
    private ArrayList<Inventory> inventories = new ArrayList<>();
    private ArrayList<Catalog> catalogs;
    private ArrayList<String> schema = new ArrayList<>();
    private ArrayList<TextField> textFields = new ArrayList<>();

    public GridPane getItemFormPane() {
        GridPane pane = PaneUtil.buildPane();
        initializeInventoryService();
        setupInventoryControls(pane);
        addInventoryHandlers(pane);
        return pane;
    }

    private void initializeInventoryService() {
        inventoryService = InventoryService.getInstance();
    }

    private void initializeCatalogService(Inventory inventory) {
        catalogService = new CatalogService(inventory);
    }

    private void setupInventoryControls(GridPane pane) {
        if (PaneUtil.setupInventoryControls(inventoryService.getAll())) {
            buildInventoryComboBox(pane);
            confirmInventoryButton = PaneUtil.buildButtonImage(new Image("select.png"), pane, 2, 0);
        }
    }

    private void setupCatalogControls(GridPane pane, Inventory inventory) {
        if (PaneUtil.setupCatalogControls(pane, inventoryComboBox, catalogService)) {
            buildCatalogComboBox(pane, inventory);
            confirmCatalogButton = PaneUtil.buildButtonImage(new Image("select.png"), pane, 2, 2);
        }
    }

    private void setupItemControls(GridPane pane, Catalog catalog) {
        PaneUtil.buildCatalogSelectedLabel(pane, catalogComboBox.getValue());
        itemNameTextField = PaneUtil.buildTextField(pane, "Name", 2);
        int accountant = 3;
        for (int i = 0; i < catalog.getSchema().size(); i++) {
            TextField textField = PaneUtil.buildTextInput(catalog.getSchema().get(i), pane, accountant);
            textFields.add(textField);
            accountant++;
        }
        saveItemButton = PaneUtil.buildButtonImage(new Image("save.png"), pane, 1, catalog.getSchema().size() + 3);
        addItemHandlers();
    }

    private void addInventoryHandlers(GridPane pane) {
        confirmInventoryButton.setOnAction((event -> {
            if(PaneUtil.addInventoryHandlers(inventoryComboBox, inventoryIndicationLabel, confirmInventoryButton)){
                initializeCatalogService(inventoryService.get(inventoryComboBox.getValue()));
                setupCatalogControls(pane, inventoryService.get(inventoryComboBox.getValue()));
                addCatalogHandlers(pane);
            }
        }));
    }

    private void addCatalogHandlers(GridPane pane) {
        confirmCatalogButton.setOnAction((event) -> {
            if (PaneUtil.addCatalogHandlers(catalogComboBox, catalogIndicationLabel, confirmCatalogButton)) {
                setupItemControls(pane, catalogService.get(catalogComboBox.getValue()));
            }
        });
    }

    private void addItemHandlers() {
        saveItemButton.setOnAction((event) -> {
            Catalog catalog = catalogService.get(catalogComboBox.getValue());
            if (generateItem(catalog))
                PaneUtil.showAlert(Alert.AlertType.INFORMATION, "Correctly stored item", "Item "+itemNameTextField.getText()+" was added correctly");

            else {
                PaneUtil.showAlert(Alert.AlertType.INFORMATION, "Error when adding the item", "The item "+itemNameTextField.getText()+" was not added");
            }
        });
    }

    private ComboBox<String> buildInventoryComboBox(GridPane pane) {
        inventoryIndicationLabel = PaneUtil.buildLabel(pane, "Choose an inventory", 0,0);
        inventoryComboBox = PaneUtil.buildComboBox(pane, inventoryService.getNamesList(), 1, 0);
        return inventoryComboBox;
    }

    private ComboBox<String> buildCatalogComboBox(GridPane pane, Inventory inventory) {
        catalogIndicationLabel = PaneUtil.buildLabel(pane, "Choose a catalog", 0, 1);
        catalogComboBox = PaneUtil.buildComboBox(pane, catalogService.getNamesList(), 1, 1);
        return catalogComboBox;
    }

    private boolean generateItem(Catalog catalog) {
        Map<String, Object> features = new HashMap<>();
        inventoryService.get(inventoryComboBox.getValue());
        catalogService.get(catalogComboBox.getValue());
        for (int i = 0; i < catalogService.get(catalogComboBox.getValue()).getSchema().size() - 1; i++) {
            features.put(catalogService.get(catalogComboBox.getValue()).getSchema().get(i), textFields.get(i).getText());
        }
        itemBuilder.setName(itemNameTextField.getText());
        itemBuilder.setFeatures(features);
        Item item = itemBuilder.build();
        catalog.getItems().add(item);
        return catalogService.edit(catalog);
    }

    @Override
    public Pane getPane() {
        return getItemFormPane();
    }
}
