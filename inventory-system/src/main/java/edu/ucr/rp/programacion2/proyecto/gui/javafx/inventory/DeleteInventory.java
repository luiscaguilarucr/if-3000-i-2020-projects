package edu.ucr.rp.programacion2.proyecto.gui.javafx.inventory;

import edu.ucr.rp.programacion2.proyecto.gui.javafx.util.PaneUtil;
import edu.ucr.rp.programacion2.proyecto.gui.model.PaneViewer;
import edu.ucr.rp.programacion2.proyecto.gui.panes.main.ManagePane;
import edu.ucr.rp.programacion2.proyecto.logic.InventoryService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.controlsfx.control.CheckComboBox;

public class DeleteInventory implements PaneViewer {
    private static InventoryService inventoryService;
    private static Button deleteInventoryButton;
    private static Label inventoryIndicationLabel;
    private static CheckComboBox checkComboBox;
    private static ObservableList observableList;
    private static GridPane pane;

    public GridPane getDeleteInventoryPane() {
        pane = PaneUtil.buildPane();
        initializeInventoryService();
        setupControls();
        addHandlers();
        return pane;
    }

    public static void refresh() {
        initializeInventoryService();
        if (inventoryService.getAll().size() == 0) {
            ManagePane.clearPane();
            PaneUtil.showAlert(Alert.AlertType.INFORMATION, "There are no inventories", "You must add at least one inventory to be able to access this function");
        }
        refreshItems();
    }
    private static void initializeInventoryService() {
        inventoryService = InventoryService.getInstance();
    }

    private void setupControls() {
        buildCheckComboBoxComboBox();
        inventoryIndicationLabel = PaneUtil.buildLabel(pane, "Select the inventory you want to remove", 0, 0);
        deleteInventoryButton = PaneUtil.buildButtonImage(new Image("delete.png"), pane, 3, 0);
    }

    private void addHandlers() {
        deleteInventoryButton.setOnAction((actionEvent) -> {
            if (!checkComboBox.getCheckModel().isEmpty()) {
                deleteCatalog();
            } else {
                PaneUtil.showAlert(Alert.AlertType.INFORMATION, "Error, did not select an inventory", "You must select one inventory to apply this action");
            }
            refreshItems();
        });
    }

    private CheckComboBox buildCheckComboBoxComboBox() {
        observableList = FXCollections.observableArrayList(inventoryService.getNamesList());
        checkComboBox = PaneUtil.buildCheckComboBox(pane, observableList, 1, 0);
        return checkComboBox;
    }
    
    private static void refreshItems() {
        System.out.println(inventoryService.getNamesList());
        initializeInventoryService();
        checkComboBox.getCheckModel().clearChecks();
        observableList.clear();
        observableList.addAll(inventoryService.getNamesList());
    }

    private void deleteCatalog() {
        int i = 0;
        Boolean removed = false;
        ObservableList<String> list = checkComboBox.getCheckModel().getCheckedItems();
        for (String s : list) {
            inventoryService.remove(inventoryService.get(s));
            i++;
        }
        if (i == list.size()) {
            removed = true;
        }
        if (removed) {
            PaneUtil.showAlert(Alert.AlertType.INFORMATION, "Inventory removed", "The inventory was removed correctly");
            ManagePane.clearPane();
        } else {
            PaneUtil.showAlert(Alert.AlertType.INFORMATION, "ERROR when removing", "The inventory was not removed");
        }
    }

    @Override
    public Pane getPane() {
        return getDeleteInventoryPane();
    }

    public static void setInventory(String inventory){

        checkComboBox.getCheckModel().check(inventory);

    }
}
