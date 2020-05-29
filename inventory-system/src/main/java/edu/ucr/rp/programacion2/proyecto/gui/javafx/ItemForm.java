package edu.ucr.rp.programacion2.proyecto.gui.javafx;

import edu.ucr.rp.programacion2.proyecto.gui.model.PaneViewer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

import java.util.ArrayList;

import static edu.ucr.rp.programacion2.proyecto.gui.javafx.UIConstants.*;

public class ItemForm implements PaneViewer {
    private TextField catalogNameTextField;
    private TextField featureNameTextField;
    private ComboBox featureTypeTextField;
    private Button addFeatureButton;
    private Button saveCatalogButton;
    private Stage stage;
    private ArrayList features;
    private ArrayList items;

    public GridPane getItemFormPane() {
        GridPane pane = buildPane();
        setupControls(pane);
        addHandlers();
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
        addFeatureButton = Utility.buildButton("Add feature", pane, 6);
        saveCatalogButton = Utility.buildButton("Save catalog", pane, 8);
        addFeatureButton(pane, addFeatureButton);
    }

    private void addFeatureButton(GridPane pane, Button button) {
        button.setOnAction((arg0) -> {
            featureView(pane, featureNameTextField.getText());

            //featureBuilder.withName(featureNameTextField.getText());
            //featureBuilder.withMandatory(true);

            //Utility.showAlert(Alert.AlertType.INFORMATION, stage, "Feature added", "The feature added is: " + featureBuilder.build().getName());

            catalogNameTextField.clear();
            featureNameTextField.clear();
        });
    }

    private void featureView(GridPane pane, String text) {
        ListView viewFeatures = new ListView<>();
        viewFeatures.setPrefWidth(100);
        viewFeatures.setPrefHeight(70);
        ObservableList<String> data = FXCollections.observableArrayList(features);
        viewFeatures.setItems(data);
        Utility.controlListView(pane, viewFeatures, 4);
    }

    private void generateCatalog() {
    }

    private void addHandlers() {
        saveCatalogButton.setOnAction(actionEvent -> generateCatalog());
    }

    @Override
    public Pane getPane() {
        return getItemFormPane();
    }
}
