package edu.ucr.rp.programacion2.proyecto.gui.javafx.util;

import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Window;

import java.util.ArrayList;
import java.util.List;

import static edu.ucr.rp.programacion2.proyecto.gui.javafx.util.UIConstants.*;

public class PaneUtil {

    public static GridPane buildPane(){
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

    public static TextField buildTextInput(String text, GridPane pane, int row) {
        Label minNumberLabel = new Label(text);
        pane.add(minNumberLabel, 0, row);
        TextField textField = new TextField();
        pane.add(textField, 1, row);
        return textField;
    }

    public static void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }

    public static Button buildButton(String text, GridPane pane, int column, int row){
        Button button = new Button();
        button.setText(text);
        pane.setHalignment(button, HPos.CENTER);
        pane.setMargin(button, new Insets(20, 0, 20, 0));
        pane.add(button, column, row);
        return button;
    }

    public static ListView controlListView(GridPane pane, ListView listView, int row){
        pane.add(listView, 2, row);
        return listView;
    }

    public static ComboBox buildComboBox(GridPane pane, List list, int column, int row){
        ComboBox comboBox = new ComboBox(FXCollections.observableArrayList(list));
        comboBox.setMaxWidth(1000);
        pane.add(comboBox, column, row);
        return comboBox;
    }

    public static Label buildLabel(GridPane pane, String text, int column,  int row){
        Label label = new Label();
        label.setText(text);
        pane.add(label, column, row);
        return label;
    }

    public static TextField buildTextField(GridPane pane, String text, int row){
        Label minNumberLabel = new Label(text);
        pane.add(minNumberLabel, 0, row);
        TextField textField = new TextField();
        pane.add(textField, 1, row);
        return textField;
    }
}
