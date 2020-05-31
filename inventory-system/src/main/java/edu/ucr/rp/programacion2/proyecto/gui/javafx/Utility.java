package edu.ucr.rp.programacion2.proyecto.gui.javafx;

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

    public void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

    public static Button buildButton(String text, GridPane pane, int row){
        Button button = new Button();
        button.setText(text);
        pane.add(button, 1, row);
        return button;
    }

    public static ListView buildListView(GridPane pane, int row){
        ListView listView = new ListView();
        pane.add(listView, 2, row);
        return listView;
    }

    public static void setView(ListView listView, String text){

    }
}
