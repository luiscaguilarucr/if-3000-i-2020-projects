package edu.ucr.rp.programacion2.proyecto.gui.javafx.inventory;

import edu.ucr.rp.programacion2.proyecto.domain.Inventory;
import edu.ucr.rp.programacion2.proyecto.gui.javafx.util.PaneUtil;
import edu.ucr.rp.programacion2.proyecto.gui.model.PaneViewer;
import edu.ucr.rp.programacion2.proyecto.logic.InventoryService;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.controlsfx.control.CheckComboBox;
import java.util.ArrayList;

public class DeleteInventory implements PaneViewer {
    private InventoryService inventoryService;
    private TextField inventoryNameTextField;
    private Button deleteInventoryButton;
    private Label inventoryIndicationLabel;
    private ComboBox<String> inventoryComboBox;
    private CheckComboBox checkComboBox;
    private ArrayList<Inventory> inventories;

    public GridPane getDeleteInventoryPane() {
        GridPane pane = PaneUtil.buildPane();
        initializeInventoryService();
        setupControls(pane);
        addHandlers(pane);
        return pane;
    }

    private void initializeInventoryService(){
        inventoryService = InventoryService.getInstance();
    }

    private void setupControls(GridPane pane) {
        if (PaneUtil.setupInventoryControls(inventoryService.getAll())) {
            buildInventoryComboBox(pane);
            deleteInventoryButton = PaneUtil.buildButtonImage(new Image("delete.png"), pane, 2, 0);
        }
    }

    private void addHandlers(GridPane pane) {
        deleteInventoryButton.setOnAction(actionEvent -> deleteCatalog());
    }

    private void buildInventoryComboBox(GridPane pane){
        checkComboBox = PaneUtil.buildCheckComboBox(pane, inventoryService.getNamesList(), 1, 0);
        inventoryIndicationLabel = PaneUtil.buildLabel(pane, "Select the inventory you want to remove",0, 0);
    }

    private void deleteCatalog(){
        int i = 0;
        Boolean removed = false;
        ObservableList<String> list = checkComboBox.getCheckModel().getCheckedItems();
        for(String s: list){
            inventoryService.remove(inventoryService.get(s));
            i++;
        }
        if(i == list.size()){
            removed = true;
        }
        if (removed) {
            PaneUtil.showAlert(Alert.AlertType.INFORMATION, "Inventory removed", "The inventory was removed correctly");
        }else {
            PaneUtil.showAlert(Alert.AlertType.INFORMATION, "ERROR when removing", "The inventory was not removed");
        }
    }

    @Override
    public Pane getPane() {
        return getDeleteInventoryPane();
    }
}
