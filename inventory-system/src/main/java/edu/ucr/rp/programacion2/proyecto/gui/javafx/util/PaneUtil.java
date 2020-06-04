package edu.ucr.rp.programacion2.proyecto.gui.javafx.util;

import org.controlsfx.control.CheckComboBox;
import edu.ucr.rp.programacion2.proyecto.domain.Catalog;
import edu.ucr.rp.programacion2.proyecto.domain.Inventory;
import edu.ucr.rp.programacion2.proyecto.logic.CatalogService;
import edu.ucr.rp.programacion2.proyecto.logic.InventoryService;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Window;

import java.util.ArrayList;
import java.util.List;

import static edu.ucr.rp.programacion2.proyecto.gui.javafx.util.UIConstants.*;

public class PaneUtil {

    public static GridPane buildPane() {
        GridPane gP_Catalog = new GridPane();
        gP_Catalog.setAlignment(Pos.CENTER_RIGHT);
        gP_Catalog.setPadding(new Insets(40, 40, 40, 40));
        gP_Catalog.setHgap(5);
        gP_Catalog.setVgap(5);
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

    public static Button buildButton(String text, GridPane pane, int column, int row) {
        Button button = new Button();
        button.setText(text);
        pane.setHalignment(button, HPos.CENTER);
        pane.setMargin(button, new Insets(20, 0, 20, 0));
        pane.add(button, column, row);
        return button;
    }

    public static Button buildButtonImage(Image image, GridPane pane, int column, int row) {
        Button button = new Button("", new ImageView(image));
        pane.setHalignment(button, HPos.CENTER);
        pane.setMargin(button, new Insets(20, 0, 20, 0));
        pane.add(button, column, row);
        return button;
    }

    public static ComboBox buildComboBox(GridPane pane, List<String> list, int column, int row) {
        ComboBox comboBox = new ComboBox(FXCollections.observableArrayList(list));
        comboBox.setMaxWidth(1000);
        pane.add(comboBox, column, row);
        return comboBox;
    }

    public static CheckComboBox buildCheckComboBox(GridPane pane, List<String> list, int column, int row){
        ObservableList<String> strings = FXCollections.observableArrayList(list);
        CheckComboBox<String> checkComboBox = new CheckComboBox<String>(strings);
        checkComboBox.setMaxWidth(200);
        pane.add(checkComboBox, column,row);
        return checkComboBox;
    }

    public static boolean setupInventoryControls(List list) {
        if (list.size() > 0) {
            return true;
        } else {
            PaneUtil.showAlert(Alert.AlertType.INFORMATION, "Error, there are no inventories", "You must add at least one inventory to be able to access this function");
            return false;
        }
    }

    public static boolean setupCatalogControls(GridPane pane, ComboBox<String> inventoryComboBox, CatalogService catalogService) {
        PaneUtil.buildInventorySelectedLabel(pane, inventoryComboBox.getValue());
        if (catalogService.getAll().size() > 0) {
            return true;
        } else {
            PaneUtil.showAlert(Alert.AlertType.INFORMATION, "There are no catalogs", "You must add at least one catalog in the inventory "+inventoryComboBox.getValue()+" to be able to access this function");
            return false;
        }
    }

    public static boolean addInventoryHandlers(ComboBox inventoryComboBox, Label inventoryIndicationLabel, Button confirmInventoryButton) {
        if (inventoryComboBox.getValue() == null) {
            PaneUtil.showAlert(Alert.AlertType.INFORMATION, "Error, did not select an inventory", "You must select an inventory");
            return false;
        } else {
            inventoryIndicationLabel.setVisible(false);
            inventoryComboBox.setVisible(false);
            confirmInventoryButton.setVisible(false);
            return true;
        }
    }

    public static boolean addCatalogHandlers(ComboBox catalogComboBox, Label catalogIndicationLabel, Button confirmCatalogButton) {
        if (catalogComboBox.getValue() == null) {
            PaneUtil.showAlert(Alert.AlertType.INFORMATION, "Error, did not select a catalog", "You must select a catalog");
            return false;
        } else {
            catalogIndicationLabel.setVisible(false);
            catalogComboBox.setVisible(false);
            confirmCatalogButton.setVisible(false);
            return true;
        }
    }

    public static void buildInventorySelectedLabel(GridPane pane, String inventorySelected) {
        Label label = new Label("Inventory selected: ");
        pane.add(label, 0, 0);

        Label label2 = new Label(inventorySelected);
        pane.add(label2, 1, 0);
    }

    public static void buildCatalogSelectedLabel(GridPane pane, String catalogSelected) {
        Label label = new Label("Catalog selected: ");
        pane.add(label, 0, 1);

        Label label2 = new Label(catalogSelected);
        pane.add(label2, 1, 1);
    }

    public static Label buildLabel(GridPane pane, String text, int column, int row) {
        Label label = new Label();
        label.setText(text);
        pane.add(label, column, row);
        return label;
    }

    public static TextField buildTextField(GridPane pane, String text, int row) {
        Label minNumberLabel = new Label(text);
        pane.add(minNumberLabel, 0, row);
        TextField textField = new TextField();
        pane.add(textField, 1, row);
        return textField;
    }
}
