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
        setupInventoryControls();
        addInventoryHandlers();
        return pane;
    }

    private void initializeInventoryService() {
        inventoryService = InventoryService.getInstance();
    }

    private void setupInventoryControls() {
        buildInventoryComboBox();
        confirmInventoryButton = PaneUtil.buildButtonImage(new Image("select.png"), pane, 2, 0);
        cancelButton = PaneUtil.buildButton("Cancel", pane, 4, 1);
    }

    private ComboBox<String> buildInventoryComboBox() {
        inventoryIndicationLabel = PaneUtil.buildLabel(pane, "Choose an inventory", 0, 0);
        //inventoryComboBox = PaneUtil.buildComboBox(pane, inventoryService.getNamesList(), 1, 0);
        return inventoryComboBox;
    }

    private void addInventoryHandlers() {
        confirmInventoryButton.setOnAction((event -> {
            /*if (PaneUtil.addInventoryHandlers(inventoryComboBox, confirmInventoryButton)) {
                initializeCatalogService(inventoryService.get(inventoryComboBox.getValue()));
                setupCatalogControls(inventoryService.get(inventoryComboBox.getValue()));
                addCatalogHandlers();
                refresh();
            }*/
            cancelButton.setOnAction((actionEvent) -> {
                ManagePane.clearPane();
                refresh();
            });
        }));
    }

    private void initializeCatalogService(Inventory inventory) {
        catalogService = new CatalogService(inventory);
    }

    private void setupCatalogControls(Inventory inventory) {
        if (PaneUtil.setupCatalogControls(inventoryComboBox, catalogService.getAll())) {
            buildCatalogComboBox(inventory);
            confirmCatalogButton = PaneUtil.buildButtonImage(new Image("select.png"), pane, 2, 2);
        }
    }

    private void addCatalogHandlers() {
        confirmCatalogButton.setOnAction((event) -> {
            if (PaneUtil.addCatalogHandlers(catalogComboBox, catalogIndicationLabel, confirmCatalogButton)) {
                setupItemControls(catalogService.get(catalogComboBox.getValue()));
            }
        });
    }

    public void refresh() {
        initializeInventoryService();
    }

    private void buildCatalogComboBox(Inventory inventory) {
        catalogIndicationLabel = PaneUtil.buildLabel(pane, "Choose a catalog", 0, 1);
        //catalogComboBox = PaneUtil.buildComboBox(pane, catalogService.getNamesList(), 1, 1);
    }

    private void setupItemControls(Catalog catalog) {
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

    private void addItemHandlers() {
        saveItemButton.setOnAction((event) -> {
            Catalog catalog = catalogService.get(catalogComboBox.getValue());
            if (generateItem(catalog)) {
                PaneUtil.showAlert(Alert.AlertType.INFORMATION, "Correctly stored item", "Item " + itemNameTextField.getText() + " was added correctly");
            } else {
                PaneUtil.showAlert(Alert.AlertType.INFORMATION, "Error when adding the item", "The item " + itemNameTextField.getText() + " was not added");
            }
            ManagePane.clearPane();
            refresh();
        });
    }

    private boolean generateItem(Catalog catalog) {
        Map<String, Object> features = new HashMap<>();
        for (int i = 0; i < catalog.getSchema().size(); i++) {
            features.put(catalogService.get(catalogComboBox.getValue()).getSchema().get(i), textFields.get(i).getText());
        }
        Item item = new Item(itemNameTextField.getText(), features);
        catalog = setupCatalog.addItem(catalog, item);

        if (catalog == null) {
            return false;
        }
        return catalogService.edit(catalog);
    }

    private void visibleRefresh() {
        inventoryIndicationLabel.setVisible(false);
        inventoryComboBox.setVisible(false);
        confirmInventoryButton.setVisible(false);

        catalogIndicationLabel.setVisible(false);
        catalogComboBox.setVisible(false);
        confirmCatalogButton.setVisible(false);

        itemIndicationLabel.setVisible(false);
        itemNameTextField.setVisible(false);

        saveItemButton.setVisible(false);
        cancelButton.setVisible(false);

        for (TextField t : textFields) {
            t.setVisible(false);
        }
        for (Label l : labels) {
            l.setVisible(false);
        }
        setupInventoryControls();
        addInventoryHandlers();
    }

    public void validateShow() {
        initializeInventoryService();
        if (inventoryService.getAll().size() == 0) {
            ManagePane.clearPane();
            PaneUtil.showAlert(Alert.AlertType.INFORMATION, "There are no inventories", "You must add at least one inventory to be able to access this function");
        }
    }

    @Override
    public Pane getPane() {
        return getItemFormPane();
    }
}
