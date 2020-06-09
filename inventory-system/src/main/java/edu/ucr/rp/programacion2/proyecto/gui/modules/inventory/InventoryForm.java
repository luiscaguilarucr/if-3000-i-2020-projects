package edu.ucr.rp.programacion2.proyecto.gui.modules.inventory;

import edu.ucr.rp.programacion2.proyecto.domain.Inventory;
import edu.ucr.rp.programacion2.proyecto.gui.modules.util.PaneUtil;
import edu.ucr.rp.programacion2.proyecto.gui.manage.model.PaneViewer;
import edu.ucr.rp.programacion2.proyecto.gui.manage.ManagePane;
import edu.ucr.rp.programacion2.proyecto.logic.InventoryService;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;

public class InventoryForm implements PaneViewer {
    private Inventory inventory;
    private InventoryService inventoryService;
    private TextField inventoryNameTextField;
    private Label inventoryNameLabel;
    private Button saveInventoryButton;
    private Button cancelButton;
    private GridPane pane;

    public GridPane getInventoryFormPane() {
        pane = PaneUtil.buildPane();
        initializeInventoryService();
        setupControls();
        addHandlers();
        return pane;
    }

    private void initializeInventoryService() {
        inventoryService = InventoryService.getInstance();
    }

    public void refresh() {
        inventoryNameTextField.clear();
    }

    private void setupControls() {
        inventoryNameLabel = PaneUtil.buildLabel(pane, "Inventory name: ", 0, 1);
        inventoryNameTextField = PaneUtil.buildTextInput(pane, 1);
        saveInventoryButton = PaneUtil.buildButtonImage(new Image("save.png"), pane, 2, 1);
        cancelButton = PaneUtil.buildButton("Cancel", pane, 3, 1);
    }

    private void addHandlers() {
        saveInventoryButton.setOnAction((actionEvent) -> {
            generateInventory();
            ManagePane.clearPane();
            refresh();
        });
        cancelButton.setOnAction((actionEvent) -> {
            ManagePane.clearPane();
            refresh();
        });
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
        } else {
            PaneUtil.showAlert(Alert.AlertType.INFORMATION, "ERROR when adding", "The inventory " + inventoryNameTextField.getText() + " was not added");
        }
    }

    @Override
    public GridPane getPane() {
        return getInventoryFormPane();
    }
}
