package edu.ucr.rp.programacion2.proyecto.gui.panes.main.records;

import edu.ucr.rp.programacion2.proyecto.domain.logic.Inventory;
import edu.ucr.rp.programacion2.proyecto.gui.javafx.util.Style;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import static edu.ucr.rp.programacion2.proyecto.gui.javafx.LabelConstants.COMBO_BOX_SELECT_LABEL;
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

    public static Label buildLabelMinimal(String text, GridPane pane, int column, int row, int numRows) {//TODO css
        Label label = new Label(text);
        label.setPadding(new Insets(5, 0, 10, 0));
        Style.setLabelConfig(label, 3);
        Style.setLabelColor(label, PRIMARY_TEXT);
        pane.add(label, column, row, numRows, 1);
        GridPane.setHalignment(label, HPos.CENTER);
        return label;
    }

    public static ComboBox buildComboBox(String text, GridPane pane, int column, int row) {
        buildLabelNormal(text, pane, column - 1, row);
        ComboBox comboBox = new ComboBox();
        comboBox.setMaxSize(180, 40);
        comboBox.setMinSize(150, 30);
        comboBox.setPromptText(COMBO_BOX_SELECT_LABEL);
        Style.settextFieldColor(comboBox, PRIMARY_COLOR_TEXTFIELD);
        pane.add(comboBox, column, row);
        return comboBox;
    }

    /**
     * Create a table view and place it in a pane.
     *
     * @param pane   pane where it will be placed.
     * @param column column where it will be assigned.
     * @param row    row where it will be assigned.
     * @return {@code TableView <>} table view ready to add columns and objects.
     */
    public static TableView<Inventory> buildTableView(GridPane pane, int column, int row, int numColumns, int numRows) {
        TableView<Inventory> tableView = new TableView<>();
        tableView.setTableMenuButtonVisible(true);//TODO revisar lo que hace //css
        tableView.setMinSize(TABLE_VIEW_DEFAULT_MIN_WIDTH, TABLE_VIEW_DEFAULT_MIN_HEIGHT);//css
        tableView.setEditable(true);
        //tableView.setStyle();
        tableView.setPadding(TABLE_VIEW_DEFAULT_INSETS);//css
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
    public static Button buildIconButton(String text, ImageView imageView, GridPane pane, int column, int row) {
        Button button = new Button(text);
        button.setGraphic(imageView);
        pane.add(button, column, row);
        return button;
    }

}
