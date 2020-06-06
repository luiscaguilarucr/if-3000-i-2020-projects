package edu.ucr.rp.programacion2.proyecto.gui.javafx.catalog;

import edu.ucr.rp.programacion2.proyecto.domain.Inventory;
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
import javafx.scene.layout.Pane;
import org.controlsfx.control.CheckComboBox;

public class DeleteCatalog implements PaneViewer {
    private InventoryService inventoryService;
    private CatalogService catalogService;
    private Label inventoryIndicationLabel;
    private Label catalogIndicationLabel;
    private Button confirmInventoryButton;
    private Button deleteCatalogButton;
    private Button refreshButton;
    private Button cancelButton;
    private ComboBox<String> inventoryComboBox;
    private CheckComboBox catalogCheckComboBox;
    private ObservableList inventoryObservableList;
    private ObservableList catalogObservableList;
    private Boolean notClean = false;
    GridPane pane;

    public GridPane getDeleteCatalogPane() {
        pane = PaneUtil.buildPane();
        initializeInventoryService();
        addControls();
        addHandlers();
        return pane;
    }

    private void initializeInventoryService() {
        inventoryService = InventoryService.getInstance();
    }

    private void addControls() {
        inventoryIndicationLabel = PaneUtil.buildLabel(pane, "Chose an inventory", 0, 0);
        catalogIndicationLabel = PaneUtil.buildLabel(pane, "Select the catalog you want to remove", 0, 1);
        deleteCatalogButton = PaneUtil.buildButtonImage(new Image("delete.png"), pane, 2, 1);
        cancelButton = PaneUtil.buildButton("Cancel", pane, 2, 0);
        catalogIndicationLabel.setVisible(false);
        deleteCatalogButton.setVisible(false);
        buildInventoryComboBox();
    }

    private void addHandlers() {
        deleteCatalogButton.setOnAction(e -> {
            if (!catalogCheckComboBox.getCheckModel().isEmpty()) {
                ManagePane.clearPane();
                deleteCatalog();
                refreshInventoryObservableList();
            } else {
                PaneUtil.showAlert(Alert.AlertType.INFORMATION, "Error, did not select a catalog", "You must select one catalog to apply this action");
            }
        });

        cancelButton.setOnAction(e -> {
            ManagePane.clearPane();
        });

        inventoryComboBox.setOnAction(e -> {
            initializeInventoryService();
            initializeCatalogService(inventoryService.get(inventoryComboBox.getValue()));
            int size = catalogService.getAll().size();
            if (size > 0) {
                catalogObservableList = FXCollections.observableArrayList(catalogService.getNamesList());
                notClean = true;
                deleteCatalogButton.setVisible(true);
                cancelButton.setOnAction(event -> {
                    ManagePane.clearPane();
                    catalogObservableList.clear();
                    initializeInventoryService();
                    deleteCatalogButton.setVisible(false);
                });
            }
            buildCatalogCheckComboBox();
            if (size == 0) {
                if(notClean){
                    catalogObservableList.clear();
                    notClean = false;
                }
                catalogCheckComboBox.setDisable(true);
            }
            catalogIndicationLabel.setVisible(true);
            cancelButton.setOnAction(event -> {
                ManagePane.clearPane();
                initializeInventoryService();
                deleteCatalogButton.setVisible(false);
            });
        });
    }

    private void deleteCatalog() {
        Boolean removed = true;
        ObservableList<String> list = catalogCheckComboBox.getCheckModel().getCheckedItems();
        for (String s : list) {
            if (!catalogService.remove(catalogService.get(s))) {
                removed = false;
            }
        }
        if (removed) {
            PaneUtil.showAlert(Alert.AlertType.INFORMATION, "Catalog removed", "The selected catalog was removed correctly");
        } else {
            PaneUtil.showAlert(Alert.AlertType.INFORMATION, "ERROR when removing", "The selected catalog was not removed");
        }
    }

    private void buildInventoryComboBox() {
        inventoryObservableList = FXCollections.observableArrayList(inventoryService.getNamesList());
        inventoryComboBox = PaneUtil.buildComboBox(pane, inventoryObservableList, 1, 0);
    }

    private void buildCatalogCheckComboBox() {
        catalogCheckComboBox = PaneUtil.buildCheckComboBox(pane, catalogObservableList, 1, 1);
    }

    private void refreshInventoryObservableList() {
        initializeInventoryService();
        inventoryObservableList.clear();
        inventoryObservableList.addAll(inventoryService.getNamesList());
        catalogObservableList.clear();
        deleteCatalogButton.setVisible(false);
    }

    public void validateShow() {
        initializeInventoryService();
        if (inventoryService.getAll().size() == 0) {
            ManagePane.clearPane();
            PaneUtil.showAlert(Alert.AlertType.INFORMATION, "There are no inventories", "You must add at least one inventory to be able to access this function");
        }
    }

    private void initializeCatalogService(Inventory inventory) {
        catalogService = new CatalogService(inventory);
    }

    @Override
    public Pane getPane() {
        return getDeleteCatalogPane();
    }
}
