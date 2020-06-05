package edu.ucr.rp.programacion2.proyecto.gui.javafx.item;

import edu.ucr.rp.programacion2.proyecto.domain.Catalog;
import edu.ucr.rp.programacion2.proyecto.domain.Inventory;
import edu.ucr.rp.programacion2.proyecto.domain.Item;
import edu.ucr.rp.programacion2.proyecto.gui.javafx.util.PaneUtil;
import edu.ucr.rp.programacion2.proyecto.gui.model.PaneViewer;
import edu.ucr.rp.programacion2.proyecto.gui.panes.main.ManagePane;
import edu.ucr.rp.programacion2.proyecto.logic.CatalogService;
import edu.ucr.rp.programacion2.proyecto.logic.SetupCatalog;
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
    private SetupCatalog setupCatalog = new SetupCatalog();
    private ItemBuilder itemBuilder = new ItemBuilder();
    private TextField itemNameTextField;
    private Label inventoryIndicationLabel;
    private Label catalogIndicationLabel;
    private Label catalogSelectedNameLabel;
    private Label catalogNameLabel;
    private Label inventoryNameLabel;
    private Label inventorySelectedNameLabel;
    private Label itemIndicationLabel;
    private Button confirmInventoryButton;
    private Button confirmCatalogButton;
    private Button saveItemButton;
    private Button cancelButton;
    private ComboBox<String> inventoryComboBox;
    private ComboBox<String> catalogComboBox;
    private List<TextField> textFields = new ArrayList<>();
    private List<Label> labels = new ArrayList<>();
    private GridPane pane;

    public GridPane getItemFormPane() {
        pane = PaneUtil.buildPane();
        initializeInventoryService();
        if (inventoryService.getAll().size() > 0) {
            setupInventoryControls(pane);
            addInventoryHandlers(pane);
        }

        return pane;
    }

    private void visibleRefresh() {
        itemNameTextField.setVisible(false);
        inventoryIndicationLabel.setVisible(false);
        catalogIndicationLabel.setVisible(false);
        catalogSelectedNameLabel.setVisible(false);
        catalogNameLabel.setVisible(false);
        inventoryNameLabel.setVisible(false);
        inventorySelectedNameLabel.setVisible(false);
        itemIndicationLabel.setVisible(false);
        confirmInventoryButton.setVisible(false);
        confirmCatalogButton.setVisible(false);
        saveItemButton.setVisible(false);
        cancelButton.setVisible(false);
        inventoryComboBox.setVisible(false);
        catalogComboBox.setVisible(false);
        for (TextField t : textFields) {
            t.setVisible(false);
        }
        for (Label l : labels) {
            l.setVisible(false);
        }
        setupInventoryControls(pane);
        addInventoryHandlers(pane);
    }

    public void refresh() {
        initializeInventoryService();
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
            cancelButton = PaneUtil.buildButton("Cancel", pane, 4, 1);
        }
    }

    private void setupCatalogControls(GridPane pane, Inventory inventory) {
        if (PaneUtil.setupCatalogControls(pane, inventoryComboBox, catalogService.getAll())) {
            inventoryNameLabel = PaneUtil.buildLabel(pane, "Inventory Selected", 0, 0);
            inventorySelectedNameLabel = PaneUtil.buildLabel(pane, inventoryComboBox.getValue(), 1, 0);
            buildCatalogComboBox(pane, inventory);
            confirmCatalogButton = PaneUtil.buildButtonImage(new Image("select.png"), pane, 2, 2);
        }
    }

    private void setupItemControls(GridPane pane, Catalog catalog) {
        catalogNameLabel = PaneUtil.buildLabel(pane, "Inventory Selected", 0, 0);
        catalogSelectedNameLabel = PaneUtil.buildLabel(pane, catalogComboBox.getValue(), 1, 0);
        itemIndicationLabel = PaneUtil.buildLabel(pane, "Name", 0, 2);
        itemNameTextField = PaneUtil.buildTextField(pane, 2);
        int accountant = 3;
        for (int i = 0; i < catalog.getSchema().size(); i++) {
            Label label = PaneUtil.buildLabel(pane, catalog.getSchema().get(i), 0, accountant);
            labels.add(label);
            TextField textField = PaneUtil.buildTextInput(pane, accountant);
            textFields.add(textField);
            accountant++;
        }
        saveItemButton = PaneUtil.buildButtonImage(new Image("save.png"), pane, 1, catalog.getSchema().size() + 3);
        addItemHandlers();
    }

    private void addInventoryHandlers(GridPane pane) {
        if (PaneUtil.setupInventoryControls(inventoryService.getAll())) {
            confirmInventoryButton.setOnAction((event -> {
                if (PaneUtil.addInventoryHandlers(inventoryComboBox, inventoryIndicationLabel, confirmInventoryButton)) {
                    initializeCatalogService(inventoryService.get(inventoryComboBox.getValue()));
                    setupCatalogControls(pane, inventoryService.get(inventoryComboBox.getValue()));
                    addCatalogHandlers(pane);
                    refresh();
                }
                cancelButton.setOnAction((actionEvent) -> {
                    ManagePane.clearPane();
                    refresh();
                });
            }));
        }
    }

    private void addCatalogHandlers(GridPane pane) {
        if (catalogService.getAll().size() > 0) {
            if (PaneUtil.addCatalogHandlerConfirm(catalogService.getAll())) {
                confirmCatalogButton.setOnAction((event) -> {
                    if (PaneUtil.addCatalogHandlers(catalogComboBox, catalogIndicationLabel, confirmCatalogButton)) {
                        setupItemControls(pane, catalogService.get(catalogComboBox.getValue()));
                    }
                });
            }
        }
    }

    private void addItemHandlers() {
        saveItemButton.setOnAction((event) -> {
            Catalog catalog = catalogService.get(catalogComboBox.getValue());
            if (generateItem(catalog)) {
                PaneUtil.showAlert(Alert.AlertType.INFORMATION, "Correctly stored item", "Item " + itemNameTextField.getText() + " was added correctly");
                ManagePane.clearPane();
                refresh();
            } else {
                PaneUtil.showAlert(Alert.AlertType.INFORMATION, "Error when adding the item", "The item " + itemNameTextField.getText() + " was not added");
                ManagePane.clearPane();
                refresh();
            }
        });
    }

    private ComboBox<String> buildInventoryComboBox(GridPane pane) {
        inventoryIndicationLabel = PaneUtil.buildLabel(pane, "Choose an inventory", 0, 0);
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
        for (int i = 0; i < catalog.getSchema().size(); i++) {
            features.put(catalogService.get(catalogComboBox.getValue()).getSchema().get(i), textFields.get(i).getText());
        }
        itemBuilder.setName(itemNameTextField.getText());
        itemBuilder.setFeatures(features);
        Item item = itemBuilder.build();
        catalog = setupCatalog.addItem(catalog, item);
        if (catalog == null) {
            return false;
        }
        return catalogService.edit(catalog);
    }

    @Override
    public Pane getPane() {
        return getItemFormPane();
    }
}
