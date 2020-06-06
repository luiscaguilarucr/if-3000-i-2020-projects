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
    private InventoryService inventoryService;
    private Button deleteInventoryButton;
    private Button refreshButton;
    private Label inventoryIndicationLabel;
    private static CheckComboBox checkComboBox;
    private ObservableList observableList;
    GridPane pane;

    public GridPane getDeleteInventoryPane() {
        pane = PaneUtil.buildPane();
        initializeInventoryService();
        setupControls();
        addHandlers();
        return pane;
    }

    public void validateShow() {
        initializeInventoryService();
        if (inventoryService.getAll().size() == 0) {
            ManagePane.clearPane();
            PaneUtil.showAlert(Alert.AlertType.INFORMATION, "There are no inventories", "You must add at least one inventory to be able to access this function");
        }
    }


//     public void refresh() {
//         initializeInventoryService();
//         if (inventoryService.getAll().size() > 0) {
//             setupControls();
//             addHandlers();
//         }
//         checkComboBox.getCheckModel().clearChecks();//TODO colocar
//     }
//
//     private void clear() {
//         deleteInventoryButton.setVisible(false);
//         inventoryIndicationLabel.setVisible(false);
//         inventoryIndicationLabel.setVisible(false);
//         checkComboBox.setVisible(false);
//     }
    private void initializeInventoryService() {
        inventoryService = InventoryService.getInstance();
    }

    private void setupControls() {
        buildCheckComboBoxComboBox();
        inventoryIndicationLabel = PaneUtil.buildLabel(pane, "Select the inventory you want to remove", 0, 0);
        deleteInventoryButton = PaneUtil.buildButtonImage(new Image("delete.png"), pane, 2, 0);
        refreshButton = PaneUtil.buildButtonImage(new Image("refresh.png"), pane, 4, 0);
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
        refreshButton.setOnAction(event -> refreshItems());
        //checkComboBox.setOnMouseClicked(event -> refreshItems());
    }

    private CheckComboBox buildCheckComboBoxComboBox() {
        observableList = FXCollections.observableArrayList(inventoryService.getNamesList());
        checkComboBox = PaneUtil.buildCheckComboBox(pane, observableList, 1, 0);
        return checkComboBox;
    }

    private void refreshItems() {
        initializeInventoryService();
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
