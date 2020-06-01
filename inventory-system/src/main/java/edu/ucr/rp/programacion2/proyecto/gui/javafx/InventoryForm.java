package edu.ucr.rp.programacion2.proyecto.gui.javafx;

import edu.ucr.rp.programacion2.proyecto.gui.javafx.util.PaneUtil;
import edu.ucr.rp.programacion2.proyecto.gui.model.PaneViewer;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class InventoryForm implements PaneViewer {
    private TextField inventoryNameTextField;
    private Button saveInventoryButton;

    public GridPane getInvetoryFormPane() {
        GridPane pane = PaneUtil.buildPane();
        setupControls(pane);
        addHandlers(pane);
        return pane;
    }

    private void setupControls(GridPane pane) {
        inventoryNameTextField = PaneUtil.buildTextInput("Inventory name: ", pane, 0);
        saveInventoryButton = PaneUtil.buildButton("Save inventory", pane, 1,6);
    }

    private void addHandlers(GridPane pane) {
        saveInventoryButton.setOnAction(actionEvent -> generateInventory());
    }

    private void generateInventory() {
        Boolean emptySpace = false;
        if (inventoryNameTextField.getText().isEmpty()) {
            emptySpace = true;
        }
        if (emptySpace) {
            inventoryNameTextField.setPromptText("Obligatory field");
            inventoryNameTextField.setStyle("-fx-background-color: #FDC7C7");
        } else {
            //inventoryPersistence.createInventory(inventoryNameTextField.getText()+"");
        }
    }

    @Override
    public Pane getPane() {
        return getInvetoryFormPane();
    }
}
