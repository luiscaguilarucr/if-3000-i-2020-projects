package edu.ucr.rp.programacion2.proyecto.gui.javafx;

import edu.ucr.rp.programacion2.proyecto.domain.Inventory;
import edu.ucr.rp.programacion2.proyecto.gui.javafx.util.PaneUtil;
import edu.ucr.rp.programacion2.proyecto.gui.model.PaneViewer;
import edu.ucr.rp.programacion2.proyecto.logic.InventoryService;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class DeleteInventory implements PaneViewer {
    private InventoryService inventoryService;
    private TextField inventoryNameTextField;
    private Button deleteInventoryButton;
    private Label inventoryIndicationLabel;
    private ComboBox<String> inventoryComboBox;
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
        deleteInventoryButton.setOnAction(actionEvent -> deleteInventory());
    }

    private ComboBox<String> buildInventoryComboBox(GridPane pane){
        inventoryIndicationLabel = PaneUtil.buildLabel(pane, "Chose an inventory",0, 0);
        inventoryComboBox = PaneUtil.buildComboBox(pane, getInventoryNames(), 1,0);
        return inventoryComboBox;
    }


    private void deleteInventory(){
        if (inventoryService.remove(inventoryService.get(inventoryComboBox.getValue()))) {
            PaneUtil.showAlert(Alert.AlertType.INFORMATION, "Inventory removed", "The inventory " + inventoryComboBox.getValue() + " was removed correctly");
        }else {
            PaneUtil.showAlert(Alert.AlertType.INFORMATION, "ERROR when removing", "The inventory " + inventoryComboBox.getValue() + " was not removed");
        }
    }

    private List<String> getInventoryNames(){
        ArrayList<String> inventoryNames = new ArrayList();
        inventories = (ArrayList<Inventory>) inventoryService.getAll();
        for (Inventory i:inventories){
            inventoryNames.add(i.getName());
        }
        return inventoryNames;
    }

    @Override
    public Pane getPane() {
        return getDeleteInventoryPane();
    }
}
