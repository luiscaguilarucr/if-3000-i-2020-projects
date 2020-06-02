package edu.ucr.rp.programacion2.proyecto.gui.javafx;

import edu.ucr.rp.programacion2.proyecto.domain.logic.Inventory;
import edu.ucr.rp.programacion2.proyecto.gui.javafx.util.PaneUtil;
import edu.ucr.rp.programacion2.proyecto.gui.model.PaneViewer;
import edu.ucr.rp.programacion2.proyecto.logic.InventoryService;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class InventoryForm implements PaneViewer {
    private Inventory inventory;
    private InventoryService inventoryService;
    private TextField inventoryNameTextField;
    private Button saveInventoryButton;

    public GridPane getInvetoryFormPane() {
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
        inventoryNameTextField = PaneUtil.buildTextInput("Inventory name: ", pane, 0);
        saveInventoryButton = PaneUtil.buildButton("Save inventory", pane, 1,2);
    }

    private void addHandlers(GridPane pane) {
        saveInventoryButton.setOnAction(actionEvent -> generateInventory());
    }

    private void generateInventory() {
        Boolean emptySpace = false, wasAdded = false;
        if (inventoryNameTextField.getText().isEmpty()) {
            emptySpace = true;
        }
        if (emptySpace) {
            inventoryNameTextField.setPromptText("Obligatory field");
            inventoryNameTextField.setStyle("-fx-background-color: #FDC7C7");
        } else {
            inventory = new Inventory(inventoryNameTextField.getText());
            wasAdded = inventoryService.add(inventory);
        }
        if (wasAdded) {
            PaneUtil.showAlert(Alert.AlertType.INFORMATION, "Inventory added", "The inventory " + inventoryNameTextField.getText() + " was added correctly");
        }else {
            PaneUtil.showAlert(Alert.AlertType.INFORMATION, "ERROR when adding", "The inventory " + inventoryNameTextField.getText() + " was not added");
        }
    }

    @Override
    public Pane getPane() {
        return getInvetoryFormPane();
    }
}
