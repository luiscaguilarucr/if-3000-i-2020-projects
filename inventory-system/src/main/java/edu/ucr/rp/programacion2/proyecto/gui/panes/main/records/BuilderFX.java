package edu.ucr.rp.programacion2.proyecto.gui.panes.main.records;

import edu.ucr.rp.programacion2.proyecto.gui.javafx.util.Style;
import javafx.collections.FXCollections;
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

import static edu.ucr.rp.programacion2.proyecto.gui.javafx.util.PaletteDesign.*;
import static edu.ucr.rp.programacion2.proyecto.gui.javafx.util.UIConstants.*;

public class BuilderFX {
    /**
     * Build the main pane.
     *
     * @return {@code GridPane} Pane formated.
     */
    public static GridPane buildRecordsPane() {
        GridPane pane = new GridPane();
        pane.setMinSize(GRID_PANE_MIN_WIDTH, GRID_PANE_MIN_HEIGHT);

        ColumnConstraints columnConstraints = new ColumnConstraints(RECORDS_COLUMN_MIN, RECORDS_COLUMN_MIN, RECORDS_COLUMN_MAX);
        columnConstraints.setHalignment(HPos.LEFT);
        columnConstraints.setHgrow(Priority.ALWAYS);

        ColumnConstraints columnConstraints2 = new ColumnConstraints(RECORDS_COLUMN_MIN, RECORDS_COLUMN_MIN, RECORDS_COLUMN_MAX);
        columnConstraints.setHalignment(HPos.CENTER);
        columnConstraints.setHgrow(Priority.ALWAYS);

        ColumnConstraints columnConstraints3 = new ColumnConstraints(RECORDS_COLUMN_MIN, RECORDS_COLUMN_MIN, RECORDS_COLUMN_MAX);
        columnConstraints3.setHalignment(HPos.RIGHT);
        columnConstraints3.setHgrow(Priority.ALWAYS);

        ColumnConstraints columnConstraints4 = new ColumnConstraints(RECORDS_COLUMN_MIN, RECORDS_COLUMN_MIN, RECORDS_COLUMN_MAX);
        columnConstraints4.setHalignment(HPos.RIGHT);
        columnConstraints4.setHgrow(Priority.ALWAYS);
        pane.getColumnConstraints().addAll(columnConstraints, columnConstraints2, columnConstraints3, columnConstraints4);
        pane.setVgap(GRID_PANE_DEFAULT_V_GAP);
        pane.setHgap(GRID_PANE_DEFAULT_H_GAP);
        pane.setAlignment(Pos.CENTER);
        Style.setBackgroundColor(pane, LIGHT_PRIMARY_COLOR);
        return pane;
    }

    /**
     * Create and add a Label with default properties [TITLE]
     *
     * @param text text to display.
     * @param pane pane where it will be placed.
     * @param row  row where it will be assigned.
     */
    public static void buildLabelTitle(String text, GridPane pane, int column, int row, int numRows) {
        Label label = new Label(text);
        label.setPadding(TITLE_DEFAULT_INSETS);//TODO use css
        Style.setLabelConfig(label, 1);//TODO use css
        Style.setLabelColor(label, PRIMARY_TEXT);//TODO use css
        pane.add(label, column, row, numRows, 1);
        GridPane.setHalignment(label, HPos.LEFT);//TODO use css
    }

    public static void buildLabelNormal(String text, GridPane pane, int column, int row) {
        Label label = new Label(text);
        label.setPadding(new Insets(5, 0, 10, 0));
        Style.setLabelConfig(label, 3);
        Style.setLabelColor(label, PRIMARY_TEXT);
        pane.add(label, column, row);
        GridPane.setHalignment(label, HPos.CENTER);
    }

    public static Label buildLabelMinimal(String text, GridPane pane, int column, int row, int numColumns) {//TODO css
        Label label = new Label(text);
        label.setPadding(new Insets(5, 0, 10, 0));
        Style.setLabelConfig(label, 3);
        Style.setLabelColor(label, PRIMARY_TEXT);
        pane.add(label, column, row, numColumns, 1);
        GridPane.setHalignment(label, HPos.CENTER);
        return label;
    }

    public static ComboBox buildComboBox(String promptText, GridPane pane, int column, int row) {
        ComboBox comboBox = new ComboBox();
        comboBox.setPromptText(promptText);
        pane.add(comboBox, column, row);
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
        textField.setMaxSize(180, 40);//todo
        textField.setMinSize(150, 30);//todo
        textField.setPromptText(text);
        Style.settextFieldColor(textField, PRIMARY_COLOR_TEXTFIELD);
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
        return button;
    }

    /**
     * Fills one comboBox with the items in the list.
     *
     * @param comboBox to fill.
     * @param list     list og items.
     */
    public static void fillComboBox(ComboBox comboBox, List list) {
        comboBox.setItems(FXCollections.observableList(list));
        comboBox.getItems().sorted();

    }

    /**
     * Fills one choiceBox with the items in the list.
     *
     * @param choiceBox to fill.
     * @param list     list og items.
     */
    public static void fillChoiceBox(ChoiceBox choiceBox, List list) {
        choiceBox.setItems(FXCollections.observableList(list));
        choiceBox.getItems().sorted();
    }
}
