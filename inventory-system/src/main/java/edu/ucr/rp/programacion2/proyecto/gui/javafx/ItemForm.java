package edu.ucr.rp.programacion2.proyecto.gui.javafx;

import edu.ucr.rp.programacion2.proyecto.domain.logic.Catalog;
import edu.ucr.rp.programacion2.proyecto.domain.logic.CatalogService;
import edu.ucr.rp.programacion2.proyecto.domain.logic.Inventory;
import edu.ucr.rp.programacion2.proyecto.gui.javafx.util.Utility;
import edu.ucr.rp.programacion2.proyecto.gui.model.PaneViewer;
import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

import java.util.ArrayList;
import java.util.List;

import static edu.ucr.rp.programacion2.proyecto.gui.javafx.util.UIConstants.*;

public class ItemForm implements PaneViewer {
    private Label catalogNameLabel;
    private Label catalogNameSelectedLabel;
    private TextField featureNameTextField;
    private Button saveItemButton;
    ArrayList<String> schema = new ArrayList<>();
    private int accountant = 1;
    private ComboBox comboBox;
    private Inventory inventory;
    private Catalog catalog;
    private CatalogService catalogService;

    public GridPane getItemFormPane() {
        GridPane pane = buildPane();
        builComboBox(pane);
        if(comboBox.getValue() != null){
            //setupControls(pane, inventory.getCatalog(comboBox.getValue()+""));
            addHandlers(pane);
        }
        return pane;
    }

    private ComboBox builComboBox(GridPane pane) {
        Utility.buildLabel(pane, "Choose a catalog", 0, 0);
        Catalog test1 = new Catalog("Test1", new ArrayList<>(), new ArrayList<>());
        ArrayList<String> catalogs = new ArrayList<>();
        catalogs.add(test1.getName());
        comboBox = new ComboBox(FXCollections.observableArrayList(catalogs));
        pane.add(comboBox, 1, 0);
        return comboBox;
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

    private void setupControls(GridPane pane, Catalog catalog) {
        catalogNameLabel = Utility.buildLabel(pane, "Catalog selected: ", 1, 2);
        catalogNameSelectedLabel = Utility.buildLabel(pane, "", 2, 2);
        //featureNameTextField = Utility.buildTextInput("Feature: ", pane, 4);
        //addFeatureButton = Utility.buildButton("Add feature", pane, 3, 4);
        //saveCatalogButton = Utility.buildButton("Save catalog", pane, 1, 6);

    }

    private void addHandlers(GridPane pane) {
        comboBox.setOnAction(actionEvent -> catalogNameSelectedLabel.setText(comboBox.getValue()+""));
        //addFeatureButton.setOnAction(actionEvent -> addFeature(pane));
        //saveCatalogButton.setOnAction(actionEvent -> generateCatalog());
    }

    private void addFeature(GridPane pane) {
        schema.add(accountant, featureNameTextField.getText());
        accountant++;
        //Utility.showAlert(Alert.AlertType.INFORMATION, stage, "Feature added", "The feature added is: " + featureBuilder.build().getName());

        featureNameTextField.clear();
    }

    private void generateCatalog() {
    }

    @Override
    public Pane getPane() {
        return getItemFormPane();
    }
}
