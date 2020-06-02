package edu.ucr.rp.programacion2.proyecto.gui.javafx;

import edu.ucr.rp.programacion2.proyecto.domain.logic.Catalog;
import edu.ucr.rp.programacion2.proyecto.gui.javafx.util.PaneUtil;
import edu.ucr.rp.programacion2.proyecto.gui.model.PaneViewer;
import edu.ucr.rp.programacion2.proyecto.logic.CatalogService;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import edu.ucr.rp.programacion2.proyecto.domain.logic.Inventory;
import edu.ucr.rp.programacion2.proyecto.util.builder.CatalogBuilder;

import java.util.ArrayList;

public class CatalogForm implements PaneViewer {
    private Inventory catalog;
    private CatalogService catalogService;
    private CatalogBuilder catalogBuilder = new CatalogBuilder();
    private TextField catalogNameTextField;
    private TextField featureNameTextField;
    private Button addFeatureButton;
    private Button saveCatalogButton;
    private ArrayList<String> schema = new ArrayList<>();
    private Boolean emptySpace;
    private Boolean wasAdded;

    public GridPane getCatalogFormPane() {
        GridPane pane = PaneUtil.buildPane();
        setupControls(pane);
        addHandlers(pane);
        return pane;
    }

    public void initializeCatalogService(Inventory inventory){
        catalogService = new CatalogService(inventory);
    }

    private void setupControls(GridPane pane) {
        catalogNameTextField = PaneUtil.buildTextInput("Catalog name: ", pane, 0);
        featureNameTextField = PaneUtil.buildTextInput("Feature: ", pane, 4);
        addFeatureButton = PaneUtil.buildButton("Add feature", pane, 3, 4);
        saveCatalogButton = PaneUtil.buildButton("Save catalog", pane, 1, 6);
    }

    private void addHandlers(GridPane pane) {
        addFeatureButton.setOnAction(actionEvent -> addFeature());
        saveCatalogButton.setOnAction(actionEvent -> generateCatalog());
        saveCatalogButton.setVisible(false);
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
            featureNameTextField.setPromptText("");
            featureNameTextField.setStyle("-fx-background-color: #FFFFFF");
            catalogNameTextField.setDisable(true);
            schema.add(featureNameTextField.getText()+"");
            featureNameTextField.clear();
            saveCatalogButton.setVisible(true);
        }
    }

    private void generateCatalog() {
        emptySpace = false;
        if (catalogNameTextField.getText().isEmpty()) {
            emptySpace = true;
        }
        if (emptySpace) {
            catalogNameTextField.setPromptText("Obligatory field");
            catalogNameTextField.setStyle("-fx-background-color: #FDC7C7");
        } else if(schema.size() > 0) {
            featureNameTextField.setPromptText("");
            featureNameTextField.setStyle("-fx-background-color: #FFFFFF");

            initializeCatalogService(new Inventory("Carros")); //TODO cambiar lo de carros
            catalogBuilder.withName(catalogNameTextField.getText());
            catalogBuilder.withSchema(schema);

            Catalog catalog = catalogBuilder.build();
            catalogService.add(catalog);
            wasAdded = true;
        }
        if (wasAdded) {
            PaneUtil.showAlert(Alert.AlertType.INFORMATION, "Catalog added", "The catalog " + catalogNameTextField.getText().toString() + " was added correctly");
        }
    }

    @Override
    public Pane getPane() {
        return getCatalogFormPane();
    }
}
