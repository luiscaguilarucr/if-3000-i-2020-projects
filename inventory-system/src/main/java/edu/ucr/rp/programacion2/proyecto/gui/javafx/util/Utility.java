package edu.ucr.rp.programacion2.proyecto.gui.javafx.util;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Window;

public class Utility {
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
        pane.add(button, column, row);
        return button;
    }

    public static ListView controlListView(GridPane pane, ListView listView, int row){
        pane.add(listView, 2, row);
        return listView;
    }

    public static Button buildGenerateButton(String label, GridPane pane, int row) {
        Button button = new Button(label);
        pane.add(button, 0, row, 1, 1);
        GridPane.setHalignment(button, HPos.CENTER);
        GridPane.setMargin(button, new Insets(20, 0, 20, 0));
        return button;
    }

    public static Label buildLabel(GridPane pane, String text, int column,  int row){
        Label label = new Label();
        label.setText(text);
        pane.add(label, column, row);
        return label;
    }

}
