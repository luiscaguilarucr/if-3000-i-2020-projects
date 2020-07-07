package edu.ucr.rp.programacion2.proyecto.gui.modules.inventory;

import edu.ucr.rp.programacion2.proyecto.domain.Inventory;
import edu.ucr.rp.programacion2.proyecto.gui.manage.ManagePane;
import edu.ucr.rp.programacion2.proyecto.gui.manage.model.PaneViewer;
import edu.ucr.rp.programacion2.proyecto.gui.modules.util.PaneUtil;
import edu.ucr.rp.programacion2.proyecto.logic.InventoryService;
import edu.ucr.rp.programacion2.proyecto.logic.InventorySocketService;
import edu.ucr.rp.programacion2.proyecto.logic.ServiceException;
import edu.ucr.rp.programacion2.proyecto.util.builders.BuilderFX;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;

import static edu.ucr.rp.programacion2.proyecto.gui.modules.util.LabelConstants.TITLE_INVENTORY_FORM;

/**
 * This class shows the actions to add new inventory.
 *
 * @author Luis Carlos Aguilar Morales | B90514
 * @version 2.0
 */
public class InventoryForm implements PaneViewer {
    private Inventory inventory;
    private InventoryService inventoryService;
    private TextField inventoryNameTextField;
    private Label inventoryNameLabel;
    private Button saveInventoryButton;
    private Button cancelButton;
    private GridPane pane;

    /**
     * Return the pane with all the components and styles added.
     *
     * @return {@code GridPane} pane with components.
     */
    public GridPane getInventoryFormPane() {
        pane = PaneUtil.buildPane();
        initializeInventoryService();
        addControls();
        addHandlers();
        return pane;
    }

    /**
     * This method initializes the inventory service.
     */
    private void initializeInventoryService() {
        inventoryService = InventorySocketService.getInstance();
    }

    /**
     * This method restarts the GridPane to make it reusable.
     */
    public void refresh() {
        inventoryNameTextField.clear();
    }

    /**
     * Configure and add the required components in the pane.
     */
    private void addControls() {
        BuilderFX.buildLabelTitleNormal(TITLE_INVENTORY_FORM, pane, 0, 0);
        inventoryNameLabel = PaneUtil.buildLabel(pane, "Inventory name: ", 0, 1);
        inventoryNameTextField = PaneUtil.buildTextInput(pane, 1);
        saveInventoryButton = PaneUtil.buildButtonImage(new Image("save.png"), pane, 2, 1);
        cancelButton = PaneUtil.buildButton("Cancel", pane, 3, 1);
    }

    /**
     * Add functionality to buttons or events.
     */
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

    /**
     * This method generates a new inventory based on the information entered.
     */
    private void generateInventory() {
        boolean emptySpace = false, wasAdded = false;
        if (inventoryNameTextField.getText().isEmpty()) {
            emptySpace = true;
        }
        if (emptySpace) {
            inventoryNameTextField.setPromptText("Obligatory field");
            inventoryNameTextField.setStyle("-fx-background-color: #FDC7C7");
        } else {
            inventory = new Inventory(inventoryNameTextField.getText());
            try {
                wasAdded = inventoryService.add(inventory);
            }catch (ServiceException e){
                System.out.println(e.getMessage());
                PaneUtil.showAlert(Alert.AlertType.INFORMATION, "ERROR when adding", "The inventory " + inventoryNameTextField.getText() + " was not added, because " + e.getMessage());
            }
        }
        if (wasAdded) {
            PaneUtil.showAlert(Alert.AlertType.INFORMATION, "Inventory added", "The inventory " + inventoryNameTextField.getText() + " was added correctly");
        }
    }

    @Override
    public GridPane getPane() {
        return getInventoryFormPane();
    }
}
