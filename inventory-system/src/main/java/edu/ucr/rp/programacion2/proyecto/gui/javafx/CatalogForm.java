package edu.ucr.rp.programacion2.proyecto.gui.javafx;

import edu.ucr.rp.programacion2.proyecto.gui.javafx.util.Utility;
import edu.ucr.rp.programacion2.proyecto.gui.model.PaneViewer;
import edu.ucr.rp.programacion2.proyecto.logic.CatalogService;
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
import edu.ucr.rp.programacion2.proyecto.domain.logic.Inventory;
import edu.ucr.rp.programacion2.proyecto.util.builder.CatalogBuilder;

import java.util.ArrayList;

import static edu.ucr.rp.programacion2.proyecto.gui.javafx.util.UIConstants.*;

public class CatalogForm implements PaneViewer {
    private TextField catalogNameTextField;
    private TextField featureNameTextField;
    private Button addFeatureButton;
    private Button saveCatalogButton;
    ArrayList<String> schema = new ArrayList<>();
    private int accountant = 1;
    private Boolean emptySpace = false;
    private Inventory catalog;
    private CatalogService catalogService;
    private CatalogBuilder catalogBuilder;
    private Boolean wasAdded = false;

    public GridPane getCatalogFormPane() {
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
        catalogNameTextField = Utility.buildTextInput("Catalog name: ", pane, 0);
        featureNameTextField = Utility.buildTextInput("Feature: ", pane, 4);
        addFeatureButton = Utility.buildButton("Add feature", pane, 3, 4);
        saveCatalogButton = Utility.buildButton("Save catalog", pane, 1, 6);
    }

    private void addHandlers(GridPane pane) {
        addFeatureButton.setOnAction(actionEvent -> addFeature());
        saveCatalogButton.setOnAction(actionEvent -> generateCatalog());
    }

    private void addFeature() {
        emptySpace = false;
        if (catalogNameTextField.getText().isEmpty()) {
            emptySpace = true;
        }
        if (featureNameTextField.getText().isEmpty()) {
            emptySpace = true;
        }

        if (emptySpace) {
            featureNameTextField.setPromptText("Obligatory field");
            featureNameTextField.setStyle("-fx-background-color: #FDC7C7");
        } else {
            catalogNameTextField.setDisable(true);
            schema.add(accountant, featureNameTextField.getText() + "");
            accountant++;
            featureNameTextField.clear();
        }
    }

    private void generateCatalog() {
        if (catalogNameTextField.getText().isEmpty()) {
            emptySpace = true;
        }
        if (emptySpace) {
            catalogNameTextField.setPromptText("Obligatory field");
            catalogNameTextField.setStyle("-fx-background-color: #FDC7C7");
        } else if(schema.size() > 1) {
            schema.add(0, catalogNameTextField.getText() + "");
            catalogBuilder.withName(catalogNameTextField.getText() + "");
            catalogBuilder.withSchema(schema);
            wasAdded = true;
        }
        if (wasAdded) {
            Utility.showAlert(Alert.AlertType.INFORMATION, "Catalog added", "The catalog " + catalogNameTextField.getText() + "was added correctly");
        } else {
            Utility.showAlert(Alert.AlertType.INFORMATION, "Error when adding catalog", "The catalog " + catalogNameTextField.getText() + "had an error when it was added");
        }
    }

    @Override
    public Pane getPane() {
        return getCatalogFormPane();
    }
}
