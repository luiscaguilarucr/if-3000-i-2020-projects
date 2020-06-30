package edu.ucr.rp.programacion2.proyecto.gui.modules.util;

import edu.ucr.rp.programacion2.proyecto.gui.manage.ManagePane;
import org.controlsfx.control.CheckComboBox;
import edu.ucr.rp.programacion2.proyecto.domain.Catalog;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import java.util.List;

import static edu.ucr.rp.programacion2.proyecto.gui.modules.util.UIConstants.*;

public class PaneUtil {

    public static GridPane buildPane() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER_RIGHT);
        gridPane.setPadding(new Insets(40, 40, 40, 40));
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        ColumnConstraints columnOneConstraints = new ColumnConstraints(LABEL_WITH, LABEL_WITH, LABEL_WITH_MAX);
        columnOneConstraints.setHalignment(HPos.RIGHT);
        ColumnConstraints columnTwoConstrains = new ColumnConstraints(INPUT_WITH, INPUT_WITH, INPUT_WITH_MAX);
        columnTwoConstrains.setHgrow(Priority.ALWAYS);
        gridPane.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstrains);
        return gridPane;
    }


    public static TextField buildTextInput(GridPane pane, int row) {
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

    public static Button buildButton(String text, GridPane pane, int column, int row) {
        Button button = new Button(text);
        pane.setHalignment(button, HPos.CENTER);
        pane.setMargin(button, BUTTON_DEFAULT_INSETS);
        pane.add(button, column, row);
        return button;
    }

    public static Button buildButtonImage(Image image, GridPane pane, int column, int row) {
        Button button = new Button("", new ImageView(image));
        pane.setHalignment(button, HPos.CENTER);
        pane.setMargin(button, BUTTON_DEFAULT_INSETS);
        pane.add(button, column, row);
        return button;
    }

    public static ComboBox buildComboBox(GridPane pane, ObservableList observableList, int column, int row) {
        ComboBox comboBox = new ComboBox(observableList);
        comboBox.setMaxWidth(INPUT_WITH_MAX);
        pane.add(comboBox, column, row);
        return comboBox;
    }

    public static CheckComboBox buildCheckComboBox(GridPane pane, ObservableList observableList, int column, int row) {
        CheckComboBox checkComboBox = new CheckComboBox<>(observableList);
        checkComboBox.setMaxWidth(INPUT_WITH_MAX);
        pane.add(checkComboBox, column, row);
        return checkComboBox;
    }

    public static boolean setupCatalogControls(ComboBox<String> inventoryComboBox, List<Catalog> catalogs) {
        if (catalogs.size() > 0) {
            return true;
        } else {
            showAlert(Alert.AlertType.INFORMATION, "There are no catalogs", "You must add at least one catalog in the inventory " + inventoryComboBox.getValue() + " to be able to access this function");
            ManagePane.clearPane();
            return false;
        }
    }

    public static boolean addCatalogHandlers(ComboBox catalogComboBox, Label catalogIndicationLabel, Button confirmCatalogButton) {
        if (catalogComboBox.getValue() == null) {
            showAlert(Alert.AlertType.INFORMATION, "Error, did not select a catalog", "You must select a catalog");
            return false;
        } else {
            catalogIndicationLabel.setVisible(false);
            catalogComboBox.setVisible(false);
            confirmCatalogButton.setVisible(false);
            return true;
        }
    }


    public static Label buildLabel(GridPane pane, String text, int column, int row) {
        Label label = new Label(text);
        pane.add(label, column, row);
        return label;
    }

    public static TextField buildTextField(GridPane pane, int row) {
        TextField textField = new TextField();
        pane.add(textField, 1, row);
        return textField;
    }
}
