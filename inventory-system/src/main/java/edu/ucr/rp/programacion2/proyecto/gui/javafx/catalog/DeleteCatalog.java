package edu.ucr.rp.programacion2.proyecto.gui.javafx.catalog;

import edu.ucr.rp.programacion2.proyecto.domain.Inventory;
import edu.ucr.rp.programacion2.proyecto.gui.javafx.util.PaneUtil;
import edu.ucr.rp.programacion2.proyecto.gui.model.PaneViewer;
import edu.ucr.rp.programacion2.proyecto.gui.panes.main.ManagePane;
import edu.ucr.rp.programacion2.proyecto.logic.CatalogService;
import edu.ucr.rp.programacion2.proyecto.logic.InventoryService;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.controlsfx.control.CheckComboBox;

public class DeleteCatalog implements PaneViewer {
    private InventoryService inventoryService;
    private CatalogService catalogService;
    private Button confirmInventoryButton;
    private Button deleteCatalogButton;
    private Label inventoryIndicationLabel;
    private Label catalogIndicationLabel;
    private Label inventoryNameLabel;
    private Label inventorySelectedNameLabel;
    private Button cancelButton;
    private ComboBox<String> inventoryComboBox;
    private CheckComboBox checkComboBox;
    private GridPane pane;

    public GridPane getDeleteCatalogPane() {
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
        }
    }

    public void refresh(){
        confirmInventoryButton.setVisible(false);
        deleteCatalogButton.setVisible(false);
        inventoryIndicationLabel.setVisible(false);
        catalogIndicationLabel.setVisible(false);
        inventoryNameLabel.setVisible(false);
        inventorySelectedNameLabel.setVisible(false);
        cancelButton.setVisible(false);
        inventoryComboBox.setVisible(false);
        checkComboBox.setVisible(false);

        initializeInventoryService();
        setupInventoryControls();
        addInventoryHandlers();
    }

    private void initializeInventoryService() {
        inventoryService = InventoryService.getInstance();
    }

    private void initializeCatalogService(Inventory inventory) {
        catalogService = new CatalogService(inventory);
    }

    private void setupInventoryControls() {
        if (PaneUtil.setupInventoryControls(inventoryService.getAll())) {
            buildInventoryComboBox();
            confirmInventoryButton = PaneUtil.buildButtonImage(new Image("select.png"), pane, 2, 0);
        }
    }

    private void setupCatalogControls() {
        if (PaneUtil.setupInventoryControls(inventoryService.getAll())) {
            inventoryNameLabel = PaneUtil.buildLabel(pane, "Inventory Selected", 0, 0);
            inventorySelectedNameLabel = PaneUtil.buildLabel(pane, inventoryComboBox.getValue(), 1, 0);
            buildCatalogCheckComboBox();
            cancelButton = PaneUtil.buildButton("Cancel", pane, 4,1);
            deleteCatalogButton = PaneUtil.buildButtonImage(new Image("delete.png"), pane, 2, 1);
        }
    }

    private void addInventoryHandlers() {
        if (PaneUtil.setupInventoryControls(inventoryService.getAll())) {
            confirmInventoryButton.setOnAction((event -> {
                if (PaneUtil.addInventoryHandlers(inventoryComboBox, confirmInventoryButton)) {
                    initializeCatalogService(inventoryService.get(inventoryComboBox.getValue()));
                    setupCatalogControls();
                    addCatalogHandlers();
                }
            }));
        }
    }

    private void addCatalogHandlers() {
        deleteCatalogButton.setOnAction((actionEvent) -> {
            ManagePane.clearPane();
            deleteCatalog();
            refresh();
        });
        cancelButton.setOnAction((actionEvent)->{
            ManagePane.clearPane();
            refresh();
        });
    }

    private void buildInventoryComboBox() {
        inventoryIndicationLabel = PaneUtil.buildLabel(pane, "Chose an inventory", 0, 0);
        inventoryComboBox = PaneUtil.buildComboBox(pane, inventoryService.getNamesList(), 1, 0);
    }

    private void buildCatalogCheckComboBox() {
        catalogIndicationLabel = PaneUtil.buildLabel(pane, "Select the catalog you want to remove", 0, 1);
        checkComboBox = PaneUtil.buildCheckComboBox(pane, catalogService.getNamesList(), 1, 1);
    }

    private void deleteCatalog() {
        int i = 0;
        Boolean removed = false;
        ObservableList<String> list = checkComboBox.getCheckModel().getCheckedItems();
        for (String s : list) {
            catalogService.remove(catalogService.get(s));
            i++;
        }
        if (i == list.size()) {
            removed = true;
        }
        if (removed) {
            PaneUtil.showAlert(Alert.AlertType.INFORMATION, "Catalog removed", "The catalog was removed correctly");
        } else {
            PaneUtil.showAlert(Alert.AlertType.INFORMATION, "ERROR when removing", "The catalog was not removed");
        }
    }

    @Override
    public Pane getPane() {
        return getDeleteCatalogPane();
    }
}
