package edu.ucr.rp.programacion2.proyecto.gui.javafx;

import edu.ucr.rp.programacion2.proyecto.domain.logic.Inventory;
import edu.ucr.rp.programacion2.proyecto.gui.javafx.util.PaneUtil;
import edu.ucr.rp.programacion2.proyecto.gui.model.PaneViewer;
import edu.ucr.rp.programacion2.proyecto.logic.InventoryService;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class InventorySelection implements PaneViewer {
    private InventoryService inventoryService;
    private ComboBox<String> inventoryComboBox;
    private Button confirmInventoryButton;
    private ArrayList<Inventory> inventories;

    public GridPane getInventorySelectionPane() {
        GridPane pane = PaneUtil.buildPane();
        initializeInventoryService();
        setupControls(pane);
        addHandlers(pane);
        return pane;
    }

    private void initializeInventoryService(){
        inventoryService = new InventoryService();
    }

    private void setupControls(GridPane pane) {
        initializeInventoryService();
        buildInventoryComboBox(pane);
        confirmInventoryButton = PaneUtil.buildButton("Select inventory", pane, 2, 0);
    }

    private void addHandlers(GridPane pane){
        Boolean selected = false;
        confirmInventoryButton.setOnAction((event -> {
            if (inventoryComboBox.getValue() == null) {
                PaneUtil.showAlert(Alert.AlertType.INFORMATION, "Error, did not select an inventory", "You must select an inventory");
            } else {
                inventoryComboBox.setVisible(false);
                confirmInventoryButton.setVisible(false);
                inventoryService.get(inventoryComboBox.getValue());
                PaneUtil.showAlert(Alert.AlertType.INFORMATION, "Inventory selected", "The inventory selected is :"+inventoryComboBox.getValue());
            }
        }));
    }

    private ComboBox<String> buildInventoryComboBox(GridPane pane){
        PaneUtil.buildLabel(pane, "Choose an inventory", 0, 0);
        inventoryComboBox = PaneUtil.buildComboBox(pane, getInventoryNames(), 1,0);
        return inventoryComboBox;
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
        return getInventorySelectionPane();
    }
}
