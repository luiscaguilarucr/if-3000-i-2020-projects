package edu.ucr.rp.programacion2.proyecto.gui.javafx;

import edu.ucr.rp.programacion2.proyecto.domain.logic.Catalog;
import edu.ucr.rp.programacion2.proyecto.domain.logic.Item;
import edu.ucr.rp.programacion2.proyecto.gui.javafx.util.Utility;
import edu.ucr.rp.programacion2.proyecto.gui.model.PaneViewer;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;


import static edu.ucr.rp.programacion2.proyecto.gui.javafx.util.UIConstants.*;

public class InventoryForm implements PaneViewer {
    private TextField inventoryNameTextField;
    private Button saveInventoryButton;
    public GridPane getInvetoryFormPane() {
        GridPane pane = buildPane();
        setupControls(pane);
        addHandlers(pane);
        return pane;
    }

    private GridPane buildPane() {
        GridPane gP_Catalog = new GridPane();
        gP_Catalog.setAlignment(Pos.CENTER);
        gP_Catalog.setPadding(new Insets(40, 40, 40, 40));
        gP_Catalog.setHgap(10);
        gP_Catalog.setVgap(10);
        ColumnConstraints columnOneConstraints = new ColumnConstraints(LABEL_WITH, LABEL_WITH, LABEL_WITH_MAX);
        columnOneConstraints.setHalignment(HPos.RIGHT);
        ColumnConstraints columnTwoConstrains = new ColumnConstraints(INPUT_WITH, INPUT_WITH, INPUT_WITH_MAX);
        columnTwoConstrains.setHgrow(Priority.ALWAYS);

        gP_Catalog.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstrains);

        return gP_Catalog;
    }

    private void setupControls(GridPane pane) {
        inventoryNameTextField = Utility.buildTextInput("Inventory name: ", pane, 0);
        saveInventoryButton = Utility.buildButton("Save inventory", pane, 1,6);
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
            schema.add(0, catalogNameTextField.getText());
            catalog = new Catalog(catalogNameTextField.getText(), new ArrayList<Item>(), schema);
        }
        if (catalogService.add(catalog) && schema.size() > 1){
            Utility.showAlert(Alert.AlertType.INFORMATION, "Catalog added", "The catalog "+ catalogNameTextField.getText() + "was added correctly");
        }else {
            Utility.showAlert(Alert.AlertType.INFORMATION, "Error when adding catalog", "The catalog "+ catalogNameTextField.getText() + "had an error when it was added");
        }
    }

    @Override
    public Pane getPane() {
        return getInvetoryFormPane();
    }
}
