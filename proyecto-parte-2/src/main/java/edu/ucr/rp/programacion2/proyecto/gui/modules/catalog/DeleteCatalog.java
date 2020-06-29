package edu.ucr.rp.programacion2.proyecto.gui.modules.catalog;

import edu.ucr.rp.programacion2.proyecto.domain.Inventory;
import edu.ucr.rp.programacion2.proyecto.gui.modules.util.PaneUtil;
import edu.ucr.rp.programacion2.proyecto.gui.manage.model.PaneViewer;
import edu.ucr.rp.programacion2.proyecto.gui.manage.ManagePane;
import edu.ucr.rp.programacion2.proyecto.logic.CatalogFileService;
import edu.ucr.rp.programacion2.proyecto.logic.InventoryFileService;
import edu.ucr.rp.programacion2.proyecto.logic.Service;
import edu.ucr.rp.programacion2.proyecto.logic.ServiceException;
import edu.ucr.rp.programacion2.proyecto.util.builders.BuilderFX;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.controlsfx.control.CheckComboBox;

import static edu.ucr.rp.programacion2.proyecto.gui.modules.util.LabelConstants.TITLE_DELETE_CATALOG;

/**
 * This class shows the actions to remove 1 or more catalogs.
 *
 * @author Luis Carlos Aguilar Morales | B90514
 * @version 2.0
 */
public class DeleteCatalog implements PaneViewer {
    private static InventoryFileService inventoryFileService;
    private CatalogFileService catalogFileService;
    private static Label inventoryIndicationLabel;
    private static Label catalogIndicationLabel;
    private static Button deleteCatalogButton;
    private static Button cancelButton;
    private static ComboBox<String> inventoryComboBox;
    private static CheckComboBox catalogCheckComboBox;
    private static ObservableList inventoryObservableList;
    private static ObservableList catalogObservableList;
    private static Boolean notClean = false;
    private static GridPane pane;

    /**
     * Return the pane with all the components and styles added.
     *
     * @return {@code GridPane} pane with components.
     */
    public GridPane getDeleteCatalogPane() {
        pane = PaneUtil.buildPane();
        initializeInventoryService();
        addControls();
        addHandlers();
        return pane;
    }

    /**
     * This method initializes the inventory service.
     */
    private static void initializeInventoryService() {
        inventoryFileService = InventoryFileService.getInstance();
    }

    /**
     * This method initializes the catalog service.
     */
    private void updateCatalogService(Inventory inventory) {
        catalogFileService = new CatalogFileService(inventory);
    }

    /**
     * This methods restarts the GridPane to make it reusable and validates whether it is possible to remove a catalog.
     */
    public static void refresh() {
        initializeInventoryService();
        try {
            if (inventoryFileService.getAll().isEmpty()) {
                ManagePane.clearPane();
                PaneUtil.showAlert(Alert.AlertType.INFORMATION, "There are no inventories", "You must add at least one inventory to be able to access this function");
            }
            refreshItems();
        }catch (ServiceException e){
            System.out.println(e.getMessage());
        }
    }

    public static void refreshItems() {
        initializeInventoryService();
        catalogCheckComboBox.getCheckModel().clearChecks();
        catalogObservableList.clear();
        inventoryObservableList.clear();
        inventoryObservableList.addAll(inventoryFileService.getNamesList());
    }

    /**
     * Configure and add the required components in the pane.
     */
    private void addControls() {
        BuilderFX.buildLabelTitleNormal(TITLE_DELETE_CATALOG, pane, 0, 0);
        inventoryIndicationLabel = PaneUtil.buildLabel(pane, "Chose an inventory", 0, 1);
        buildInventoryComboBox();
        catalogIndicationLabel = PaneUtil.buildLabel(pane, "Select the catalog", 0, 2);
        buildCatalogCheckComboBox();
        deleteCatalogButton = PaneUtil.buildButtonImage(new Image("delete.png"), pane, 2, 2);
        cancelButton = PaneUtil.buildButton("Cancel", pane, 2, 1);
        catalogIndicationLabel.setVisible(false);
        deleteCatalogButton.setVisible(false);
        catalogCheckComboBox.setVisible(false);
    }

    /**
     * Add functionality to buttons or events.
     */
    private void addHandlers() {
        deleteCatalogButton.setOnAction(e -> {
            if (!catalogCheckComboBox.getCheckModel().isEmpty()) {
                deleteCatalog();
                ManagePane.clearPane();
                refreshInventoryObservableList();
            } else {
                PaneUtil.showAlert(Alert.AlertType.INFORMATION, "Error, did not select a catalog", "You must select one catalog to apply this action");
            }
        });

        cancelButton.setOnAction(e -> {
            ManagePane.clearPane();
        });

        inventoryComboBox.setOnAction(e -> {
            refreshCatalogObservableList();
            catalogIndicationLabel.setVisible(true);
            deleteCatalogButton.setVisible(true);
            catalogCheckComboBox.setVisible(true);
        });
    }

    /**
     * This method builds a ComboBox that displays the inventory list.
     */
    private void buildInventoryComboBox() {
        inventoryObservableList = FXCollections.observableArrayList(inventoryFileService.getNamesList());
        inventoryComboBox = PaneUtil.buildComboBox(pane, inventoryObservableList, 1, 1);
    }

    /**
     * This method builds a CheckComboBox that displays the catalog list.
     */
    private void buildCatalogCheckComboBox() {
        catalogObservableList = FXCollections.observableArrayList();
        catalogCheckComboBox = PaneUtil.buildCheckComboBox(pane, catalogObservableList, 1, 2);
    }

    /**
     * This method refreshes the ObservableList of the inventory ComboBox.
     */
    private void refreshInventoryObservableList() {
        initializeInventoryService();
        inventoryObservableList.clear();
        inventoryObservableList.setAll(inventoryFileService.getNamesList());
        deleteCatalogButton.setVisible(false);
    }

    /**
     * This method refreshes the ObservableList of the catalog CheckComboBox.
     */
    private void refreshCatalogObservableList() {
        initializeInventoryService();
        try {
            Inventory inventory = inventoryFileService.get(inventoryComboBox.getValue());
            System.out.println(inventory);
            if (inventory != null) {
                updateCatalogService(inventory);
                catalogObservableList.clear();
                try {
                    if (catalogFileService.getAll().isEmpty()) {
                        catalogObservableList.add("There are not catalogs");
                        catalogCheckComboBox.getCheckModel().check(0);
                        catalogCheckComboBox.setDisable(true);
                    } else {
                        catalogCheckComboBox.setDisable(false);
                        catalogObservableList.addAll(catalogFileService.getNamesList());
                    }
                } catch (ServiceException e) {
                    System.out.println(e.getMessage());
                }

            }
        }catch (ServiceException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * This method does the job of removing 1 or many user-selected catalogs.
     */
    private void deleteCatalog() {
        Boolean removed = true;
        ObservableList<String> list = catalogCheckComboBox.getCheckModel().getCheckedItems();
        try {
            for (String s : list) {
                if (!catalogFileService.remove(catalogFileService.get(s))) {
                    removed = false;
                }
            }

            if (removed) {
                PaneUtil.showAlert(Alert.AlertType.INFORMATION, "Catalog removed", "The selected catalog was removed correctly");
            } else {
                PaneUtil.showAlert(Alert.AlertType.INFORMATION, "ERROR when removing", "The selected catalog was not removed");
            }
        }catch (ServiceException e){
            System.out.println(e.getMessage());
        }
    }


    @Override
    public Pane getPane() {
        return getDeleteCatalogPane();
    }
}
