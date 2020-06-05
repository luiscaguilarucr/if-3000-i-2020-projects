package edu.ucr.rp.programacion2.proyecto.gui.javafx.inventory;

import edu.ucr.rp.programacion2.proyecto.gui.javafx.util.PaneUtil;
import edu.ucr.rp.programacion2.proyecto.gui.model.PaneViewer;
import edu.ucr.rp.programacion2.proyecto.gui.panes.main.ManagePane;
import edu.ucr.rp.programacion2.proyecto.logic.InventoryService;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.controlsfx.control.CheckComboBox;

public class DeleteInventory implements PaneViewer {
    private InventoryService inventoryService;
    private Button deleteInventoryButton;
    private Button refreshButton;
    private Label inventoryIndicationLabel;
    private CheckComboBox checkComboBox;
    GridPane pane;

    public GridPane getDeleteInventoryPane() {
        initializeInventoryService();
        pane = PaneUtil.buildPane();
        setupControls();
        addHandlers();
        addRefreshButtonHandler();
        return pane;
    }

    public void validateShow(){
        initializeInventoryService();
        if(inventoryService.getAll().size() == 0){
            ManagePane.clearPane();
            PaneUtil.showAlert(Alert.AlertType.INFORMATION, "There are no inventories", "You must add at least one inventory to be able to access this function");
        }
    }

    public void refresh() {
        initializeInventoryService();
        if (inventoryService.getAll().size() > 0) {
            setupControls();
            addHandlers();
        }
    }

    private void clear() {
        deleteInventoryButton.setVisible(false);
        inventoryIndicationLabel.setVisible(false);
        inventoryIndicationLabel.setVisible(false);
        checkComboBox.setVisible(false);
    }

    private void initializeInventoryService() {
        inventoryService = InventoryService.getInstance();
    }

    private void setupControls() {
        buildCheckComboBoxComboBox();
        inventoryIndicationLabel = PaneUtil.buildLabel(pane, "Select the inventory you want to remove", 0, 0);
        deleteInventoryButton = PaneUtil.buildButtonImage(new Image("delete.png"), pane, 2, 0);
    }

    private void addHandlers() {
        if (PaneUtil.setupInventoryControls(inventoryService.getAll())) {
            deleteInventoryButton.setOnAction((actionEvent) -> {
                deleteCatalog();
                refresh();
            });
        }
    }

    private void addRefreshButtonHandler() {
        refreshButton = PaneUtil.buildButtonImage(new Image("refresh.png"), pane, 4, 0);
        refreshButton.setOnAction((actionEvent) -> {
            refresh();
        });
    }

    private void buildCheckComboBoxComboBox() {
        checkComboBox = PaneUtil.buildCheckComboBox(pane, inventoryService.getNamesList(), 1, 0);
    }

    private void deleteCatalog() {
        int i = 0;
        Boolean removed = false, clean = false;
        ObservableList<String> list = checkComboBox.getCheckModel().getCheckedItems();
        for (String s : list) {
            inventoryService.remove(inventoryService.get(s));
            i++;
        }
        if (i == list.size()) {
            removed = true;
        }
        if (inventoryService.getAll().size() == 0) {
            clean = true;
        }
        if (removed) {
            if (clean) {
                clear();
            }
            PaneUtil.showAlert(Alert.AlertType.INFORMATION, "Inventory removed", "The inventory was removed correctly");
        } else {
            PaneUtil.showAlert(Alert.AlertType.INFORMATION, "ERROR when removing", "The inventory was not removed");
        }
    }

    @Override
    public Pane getPane() {
        return getDeleteInventoryPane();
    }
}
