package edu.ucr.rp.programacion2.proyecto.util.builders;

import edu.ucr.rp.programacion2.proyecto.gui.modules.util.Style;
import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;
import java.util.Map;

import static edu.ucr.rp.programacion2.proyecto.gui.modules.util.PaletteDesign.PRIMARY_TEXT;
import static edu.ucr.rp.programacion2.proyecto.gui.modules.util.UIConstants.*;

public class BuilderFX {
    /**
     * Build the main pane.
     *
     * @return {@code GridPane} Pane formated.
     */
    public static GridPane buildRecordsPane() {
        GridPane pane = new GridPane();
        pane.setMinSize(GRID_PANE_MIN_WIDTH, GRID_PANE_MIN_HEIGHT);
        return pane;
    }

    /**
     * Create and add a Label with default properties [TITLE]
     *
     * @param text text to display.
     * @param pane pane where it will be placed.
     * @param row  row where it will be assigned.
     */
    public static void buildLabelTitle(String text, GridPane pane, int column, int row, int numColumns, int numRows) {
        Label label = new Label(text);
        label.setPadding(TITLE_DEFAULT_INSETS);
        Style.setLabelConfig(label, 1);
        Style.setLabelColor(label, PRIMARY_TEXT);
        pane.add(label, column, row, numColumns, numRows);
        GridPane.setHalignment(label, HPos.LEFT);
    }

    public static void buildLabelTitleNormal(String text, GridPane pane, int column, int row) {
        Label label = new Label(text);
        label.setPadding(TITLE_DEFAULT_INSETS);
        Style.setLabelConfig(label, 1);
        Style.setLabelColor(label, PRIMARY_TEXT);
        pane.add(label, column, row);
        GridPane.setHalignment(label, HPos.LEFT);
    }

    public static void buildLabelNormal(String text, GridPane pane, int column, int row) {
        Label label = new Label(text);
        label.setPadding(new Insets(5, 0, 10, 0));
        Style.setLabelConfig(label, 3);
        Style.setLabelColor(label, PRIMARY_TEXT);
        pane.add(label, column, row);
        GridPane.setHalignment(label, HPos.CENTER);
    }

    public static Label buildLabelMinimal(String text, GridPane pane, int column, int row, int numColumns) {
        Label label = new Label(text);
        pane.add(label, column, row, numColumns, 1);
        return label;
    }

    /**
     * @param promptText
     * @param pane
     * @param column
     * @param row
     * @return
     */
    public static ComboBox buildComboBox(String promptText, GridPane pane, int column, int row) {
        ComboBox comboBox = new ComboBox<>();
        comboBox.setPromptText(promptText);
        pane.add(comboBox, column, row);
        comboBox.setCursor(Cursor.HAND);
        return comboBox;
    }



    public static ChoiceBox buildChoiceBox(GridPane pane, int column, int row) {
        ChoiceBox choiceBox = new ChoiceBox();
        pane.add(choiceBox, column, row);
        return choiceBox;
    }


    /**
     * Create a table view and place it in a pane.
     *
     * @param pane   pane where it will be placed.
     * @param column column where it will be assigned.
     * @param row    row where it will be assigned.
     * @return {@code TableView <>} table view ready to add columns and objects.
     */
    public static TableView buildTableView(GridPane pane, int column, int row, int numColumns, int numRows) {
        TableView tableView = new TableView<>();
        pane.add(tableView, column, row, numColumns, numRows);
        return tableView;
    }

    /**
     * Create a table view of maps and place it in a pane.
     *
     * @param pane   pane where it will be placed.
     * @param column column where it will be assigned.
     * @param row    row where it will be assigned.
     * @return {@code TableView <>} table view ready to add columns and objects.
     */
    public static TableView<Map> buildMapTableView(GridPane pane, int column, int row, int numColumns, int numRows) {
        TableView<Map> tableView = new TableView<>();
        pane.add(tableView, column, row, numColumns, numRows);
        return tableView;
    }


    /**
     * Builds and text input.
     *
     * @param text
     * @param pane
     * @param column
     * @param row
     * @return
     */
    public static TextField buildTextInput(String text, GridPane pane, int column, int row) {
        buildLabelNormal(text, pane, column - 1, row);
        TextField textField = new TextField();
        textField.setPromptText(text);
        pane.add(textField, column, row);
        return textField;
    }

    /**
     * Builds and text input.
     *
     * @param text
     * @param pane
     * @param column
     * @param row
     * @return
     */
    public static TextField buildTextInput2(String text, GridPane pane, int column, int row) {
        TextField textField = new TextField();
        textField.setPromptText(text);
        pane.add(textField, column, row);
        return textField;
    }

    /**
     * Build a button and place it in a given position.
     *
     * @param text   Text that the button will have.
     * @param pane   pane where it will be added.
     * @param column column in which it will be located.
     * @param row    row in which it will be located.
     * @return {@code Button} button configured and located in the pane.
     */
    public static Button buildButton(String text, GridPane pane, int column, int row) {
        Button button = new Button(text);
        button.setMinSize(BUTTON_MIN_WIDTH, BUTTON_MIN_HEIGHT);
        button.setCursor(Cursor.HAND);
        pane.add(button, column, row);
        GridPane.setHalignment(button, HPos.CENTER);
        GridPane.setMargin(button, BUTTON_DEFAULT_INSETS);
        return button;
    }

    public static Pagination buildPagination(GridPane pane, int column, int row, int numColumns, int numRows) {
        Pagination pagination = new Pagination();
        pane.add(pagination, column, row, numColumns, numRows);
        return pagination;
    }

    /**
     * Build a button and place it in a given position.
     *
     * @param text   Text that the button will have.
     * @param pane   pane where it will be added.
     * @param column column in which it will be located.
     * @param row    row in which it will be located.
     * @return {@code Button} button configured and located in the pane.
     */
    public static Button buildIconButton(String text, String image, GridPane pane, int column, int row) {
        Button button = new Button(text);
        button.setGraphic(new ImageView(new Image(image)));
        pane.add(button, column, row);
        button.setCursor(Cursor.HAND);
        return button;
    }

    /**
     * Fills one comboBox with the items in the list.
     *
     * @param comboBox to fill.
     * @param list     list og items.
     */
    public static void fillComboBox(ComboBox comboBox, List list) {
        comboBox.getItems().clear();
        comboBox.setItems(FXCollections.observableArrayList(list));
        comboBox.getItems().sorted();

    }

    /**
     * Fills one choiceBox with the items in the list.
     *
     * @param choiceBox to fill.
     * @param list      list og items.
     */
    public static void fillChoiceBox(ChoiceBox choiceBox, List list) {
        choiceBox.getItems().clear();
        choiceBox.setItems(FXCollections.observableList(list));
        choiceBox.getItems().sorted();
    }

    /**
     * @param label
     * @param node
     * @param pane
     * @param column
     * @param row
     * @param numColumns
     * @param numRows
     * @return
     */
    public static TitledPane buildTitledPane(String label, Node node, GridPane pane, int column, int row, int numColumns, int numRows) {
        TitledPane titledPane = new TitledPane(label, node);
        titledPane.setAnimated(true);
        titledPane.setExpanded(false);
        pane.add(titledPane, column, row, numColumns, numRows);
        titledPane.setCursor(Cursor.HAND);titledPane.setCursor(Cursor.HAND);
        return titledPane;
    }

    public static Separator buildSeparator(Orientation orientation, GridPane pane, int column, int row, int numColumns, int numRows) {
        Separator separator = new Separator(orientation);
        pane.add(separator, column, row, numColumns, numRows);
        return separator;
    }

    public static void fillListView(ListView<String> listView, List<String> list) {
        listView.setItems(FXCollections.observableList(list));

    }


    public static Text buildText(String message, GridPane pane, int column, int row, int numColumns, int numRows) {
        Text text = new Text(message);
        pane.add(text, column, row, numColumns, numRows);
        return text;
    }


    public static void setButtonEffect(Button button){
        button.getStyleClass().add("button-item-title-pane");
        button.setOnMouseEntered(e->{
            button.getStyleClass().clear();
            button.getStyleClass().add("button-item-title-pane-entered");
        });
        button.setOnMouseExited(e->{
            button.getStyleClass().clear();
            button.getStyleClass().add("button-item-title-pane");
        });
    }


    /**
     * Builds a default confirm alert, with icons title, and buttons
     * @param title
     * @param headerImage
     * @param windowIcon
     * @param buttonTypes
     * @return
     */
    public static Alert buildConfirmDialog(String title, String headerImage, String windowIcon, ButtonType...buttonTypes){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        // Set components.
        alert.setTitle(title);
        alert.setGraphic(new ImageView(new Image(headerImage)));
        alert.getButtonTypes().clear();
        alert.getButtonTypes().setAll(buttonTypes);
        //alert.getButtonTypes().addAll();
        // Stage config
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.setAlwaysOnTop(true);
        stage.getIcons().add(new Image(windowIcon));
        return alert;
    }

    public static TextInputDialog buildInputDialog(String title, String headerImage, String windowIcon){
        TextInputDialog textInputDialog = new TextInputDialog();
        // Set components.
        textInputDialog.setTitle(title);
        textInputDialog.setGraphic(new ImageView(new Image(headerImage)));
        // Stage config
        Stage stage = (Stage) textInputDialog.getDialogPane().getScene().getWindow();
        stage.setAlwaysOnTop(true);
        stage.getIcons().add(new Image(windowIcon));
        return textInputDialog;
    }
}
