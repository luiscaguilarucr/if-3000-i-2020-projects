package edu.ucr.rp.programacion2.proyecto.gui.javafx;

import edu.ucr.rp.programacion2.proyecto.domain.logic.Feature;
import edu.ucr.rp.programacion2.proyecto.gui.model.PaneViewer;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.List;
import static edu.ucr.rp.programacion2.proyecto.gui.javafx.UIConstants.*;

public class CatalogForm implements PaneViewer {
    private TextField catalogNameTextField;
    private TextField featureTextField;
    private TextField typeOfFeatureTextField;
    private Button generateButton;
    private Button saveButton;
    private List<Feature> features;
    private Stage stage;
    private ListView viewFeatures;

    public GridPane getCatalogFormPane() {
        GridPane pane = buildPane();
        setupControls(pane);
        addFeature(pane);
        featuresView(pane);
        //addHandlers();
        return pane;
    }

    private GridPane buildPane() {
        GridPane gP_Catalog = new GridPane();
        gP_Catalog.setAlignment(Pos.CENTER_LEFT);
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
        generateButton = Utility.buildButton("Add feature", pane, 6);
        saveButton = Utility.buildButton("Save catalog", pane, 8);
        setupButton(pane, generateButton);
    }

    private void addFeature(GridPane pane) {
        featureTextField = Utility.buildTextInput("Característica: ", pane, 4);
        typeOfFeatureTextField = Utility.buildTextInput("Dato: ", pane, 5);
    }

    private void setupButton(GridPane pane, Button button) {
        button.setOnAction((arg0) -> {
            addFeature(pane);
            Utility.setView(viewFeatures, featureTextField.getText());
        });
    }

    private void saveButton(GridPane pane, Button button){
        button.setOnAction((arg0) -> {
        });
    }

    private void featuresView(GridPane pane) {
        viewFeatures = Utility.buildListView(pane, 6);
    }

    private void generateCatalog() {
    }

    public void addHandlers() {
        generateButton.setOnAction(actionEvent -> generateCatalog());
    }

    @Override
    public Pane getPane() {
        return getCatalogFormPane();
    }
}
