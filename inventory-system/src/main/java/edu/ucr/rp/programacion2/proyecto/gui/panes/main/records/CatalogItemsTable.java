package edu.ucr.rp.programacion2.proyecto.gui.panes.main.records;

import edu.ucr.rp.programacion2.proyecto.domain.Inventory;
import edu.ucr.rp.programacion2.proyecto.domain.Item;
import edu.ucr.rp.programacion2.proyecto.gui.javafx.util.Style;
import edu.ucr.rp.programacion2.proyecto.gui.model.PaneViewer;
import edu.ucr.rp.programacion2.proyecto.logic.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.util.Callback;

import java.util.List;

import static edu.ucr.rp.programacion2.proyecto.gui.javafx.LabelConstants.*;
import static edu.ucr.rp.programacion2.proyecto.gui.javafx.util.PaletteDesign.*;
import static edu.ucr.rp.programacion2.proyecto.gui.javafx.util.UIConstants.*;
import static edu.ucr.rp.programacion2.proyecto.gui.panes.main.records.Properties.CATALOG_SCHEMA_PROPERTY;

/**
 * This shows the list of items of one catalog.
 */
public class CatalogItemsTable implements PaneViewer {

    //  Variables  \\
    private TableView<Item> tableView;
    private ComboBox<String> catalogNameComboBox;
    private TableColumn ItemNameColumn;
    private TableColumn catalogSchemaColumn;
    private Boolean adminAuthorization;
    private TextField searchItemTextField;
    private Button searchItemButton;
    private Button backButton;
    private GridPane pane;
    private Service catalogService;
    //  Methods  \\

    /**
     * Return the pane with all the components and styles added.
     *
     * @param adminAuthorization allowed to edit and remove information.
     * @return {@code GridPane} pane with components.
     */
    public GridPane getPane(Boolean adminAuthorization) {
        this.adminAuthorization = adminAuthorization;// TODO sacar a una variable estática
        pane = buildPane();
        setupControls(pane);
        setupTableView(tableView, adminAuthorization);
        addHandlers();
        return pane;
    }

    /**
     * Build the main pane.
     *
     * @return {@code GridPane} Pane formated.
     */
    private GridPane buildPane() {
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
     * Configure and add the required components in the pane.
     *
     * @param pane for add components.
     */
    private void setupControls(GridPane pane) {
        buildLabelTitle(TITLE_CATALOG, pane, 0);
        catalogNameComboBox = buildComboBox(TITLE_CATALOG, pane, 2, 0);
        fillCatalogNameComboBox(catalogNameComboBox, (List) catalogService.getAll());
        buildLabelTitle(TITLE_ITEM_LIST, pane, 1);
        searchItemTextField = buildTextInput(TYPE_HERE_PROMPT_TEXT, pane, 2, 1);
        searchItemButton = buildButton(REFRESH_LABEL, pane, 3, 0);
        tableView = buildTableView(pane, 0, 2);
        backButton = buildButton(BACK_LABEL, pane, 1, 3);
    }

    /**
     * Create and add a Label with default properties [TITLE]
     *
     * @param text text to display.
     * @param pane pane where it will be placed.
     * @param row  row where it will be assigned.
     */
    private void buildLabelTitle(String text, GridPane pane, int row) {
        Label label = new Label(text);
        label.setPadding(TITLE_DEFAULT_INSETS);
        Style.setLabelConfig(label, 1);
        Style.setLabelColor(label, PRIMARY_TEXT);
        pane.add(label, 0, row, 2, 1);
        GridPane.setHalignment(label, HPos.LEFT);
    }
    private void buildLabelNormal(String text, GridPane pane, int column, int row) {
        Label label = new Label(text);
        label.setPadding(new Insets(5, 0, 10, 0));
        Style.setLabelConfig(label, 3);
        Style.setLabelColor(label, PRIMARY_TEXT);
        pane.add(label, column, row);
        GridPane.setHalignment(label, HPos.CENTER);
    }
    /**
     * Add the settings to a table view.
     * It's in charge of:
     * 1. Add required columns.
     * 2. Fill the table.
     * 3. Add buttons with functionalities (some are just for administrators).
     * 4. Allows the table to be editable (for administrators only).
     *
     * @param tableView table configured and full.
     */
    private void setupTableView(TableView tableView, Boolean adminAuthorization) {
        addColumns(tableView);
        fillTable(tableView);
        addTableButtons(tableView, adminAuthorization);
        setTableEditable(adminAuthorization);
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
    private TextField buildTextInput(String text, GridPane pane, int column, int row) {
        TextField textField = new TextField();
        textField.setMaxSize(180, 40);//todo
        textField.setMinSize(150, 30);//todo
        textField.setPromptText(text);
        Style.settextFieldColor(textField, PRIMARY_COLOR_TEXTFIELD);
        pane.add(textField, column, row);
        return textField;
    }


    private ComboBox buildComboBox(String text, GridPane pane, int column, int row, String... items) {
        buildLabelNormal(text, pane, column - 1, row);
        ComboBox comboBox = new ComboBox();
        comboBox.setMaxSize(180, 40);
        comboBox.setMinSize(150, 30);
        comboBox.setPromptText(COMBO_BOX_SELECT_LABEL);
        Style.settextFieldColor(comboBox, PRIMARY_COLOR_TEXTFIELD);
        pane.add(comboBox, column, row);
        addItems(comboBox, items);
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
    private TableView<Item> buildTableView(GridPane pane, int column, int row) {
        TableView<Item> tableView = new TableView<>();
        tableView.setTableMenuButtonVisible(true);//TODO revisar lo que hace
        tableView.setMinSize(TABLE_VIEW_DEFAULT_MIN_WIDTH, TABLE_VIEW_DEFAULT_MIN_HEIGHT);
        tableView.setMaxSize(TABLE_VIEW_DEFAULT_MIN_WIDTH, TABLE_VIEW_DEFAULT_MIN_HEIGHT);
        tableView.setEditable(true);
        //tableView.setStyle();
        tableView.setPadding(TABLE_VIEW_DEFAULT_INSETS);
        pane.add(tableView, column, row, 3, 1);
        return tableView;
    }


    /**
     * Create a column and place it in a display table.
     * *
     *
     * @param text      Text of the column.
     * @param tableView Shows the table where the column will be added.
     * @return Column configured and placed.
     * @Param Property Property that identifies the column, with an attribute of the object.
     */
    public TableColumn buildTableColumn(String text, String property, TableView tableView) {
        TableColumn tableColumn = new TableColumn(text);
        //tableColumn.setStyle(TABLE_COLUMN_DEFAULT_STYLE);//TODO css
        tableColumn.setId(property);
        tableColumn.setCellValueFactory(new PropertyValueFactory(property));
        tableView.getColumns().add(tableColumn);
        return tableColumn;
    }

    private void addItems(ComboBox comboBox, String... items) {
        for (String i : items) {
            comboBox.getItems().add(i);
        }
    }

    private void fillCatalogNameComboBox(ComboBox comboBox, List list) {
        if(list!=null){
            for (Object o: list) {
                if(o instanceof Inventory) {
                    comboBox.getItems().add(((Inventory) o).getName());
                }
            }
        }
    }
    /**
     * Add necessary columns to display object information.
     *
     * @param tableView table where the columns will be added.
     */
    private void addColumns(TableView tableView) {
        tableView.getColumns().clear();
        //ItemNameColumn = buildTableColumn(Item_NAME_COLUMN, Item_NAME_PROPERTY, tableView);
        catalogSchemaColumn = buildTableColumn(CATALOG_SCHEMA_COLUMN, CATALOG_SCHEMA_PROPERTY, tableView);
    }

    /**
     * Fills the table with an list of objects.
     *
     * @param tableView table view to add items.
     */
    private void fillTable(TableView tableView) {
        try {
            ObservableList<Item> list = getList();
            tableView.setItems(list);

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    /**
     * Add buttons with functionalities to the table.
     * Check if you have administrator permissions before enabling the function.
     * Features:
     * 1. Edit.
     * 2. Delete.
     *
     * @param tableView          table to which the buttons will be added.
     * @param adminAuthorization administrator permissions.
     */
    private void addTableButtons(TableView tableView, Boolean adminAuthorization) {
        if (adminAuthorization) {
            addButtonToTable(VIEW_LABEL, VIEW_ICON, tableView);
            addButtonToTable(EDIT_LABEL, EDIT_ICON, tableView);
            addButtonToTable(DELETE_LABEL, DELETE_ICON, tableView);
        }
    }

    /**
     * Configura las celdas como editables.
     * Verifica si tiene permisos de administrador antes habilitar la función.
     *
     * @param adminAuthorization permisos de administrador.
     */
    private void setTableEditable(Boolean adminAuthorization) {
        if (adminAuthorization) {
            setEditableColumnTextFiled(ItemNameColumn);
            setEditableColumnTextFiled(catalogSchemaColumn);
        }
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
    private Button buildButton(String text, GridPane pane, int column, int row) {
        Button button = new Button(text);
        button.setMinSize(BUTTON_MIN_WIDTH, BUTTON_MIN_HEIGHT);
        pane.add(button, column, row);
        GridPane.setHalignment(button, HPos.CENTER);
        GridPane.setMargin(button, BUTTON_DEFAULT_INSETS);
        return button;
    }


    /**
     * Ask the service for the list of objects.
     *
     * @return {@code ObservableList} observable list with existing objects.
     */
    public ObservableList<Item> getList() {
        ObservableList<Item> observableList;
        List list = (List) catalogService.getAll();

        observableList = FXCollections.observableArrayList(list);

        return observableList;//TODO simplificar
    }


    /**
     * Add buttons to a tableView.
     *
     * @param label Button text
     */
    private void addButtonToTable(String label, String image, TableView tableView) {
        TableColumn<Item, Void> tcAction = new TableColumn(label);
        //tcAction.setStyle(TABLE_VIEW_BUTTONS_DEFAULT_STYLE);

        Callback<TableColumn<Item, Void>, TableCell<Item, Void>> cellFactory = new Callback<TableColumn<Item, Void>, TableCell<Item, Void>>() {
            @Override
            public TableCell<Item, Void> call(final TableColumn<Item, Void> param) {
                final TableCell<Item, Void> cell = new TableCell<Item, Void>() {
                    private final Button btn = new Button();


                    {// Definir funciones del botón
                        btn.setGraphic(new ImageView(new Image(image))); //agrega imagen
                        //TODO Style and size
                        switch (label) {
                            case VIEW_LABEL:
                                btn.setOnAction(actionEvent -> {
                                    Item data = getTableView().getItems().get(getIndex());
                                    viewAction(data);
                                    refreshTable();
                                });
                                break;
                            case EDIT_LABEL:
                                btn.setOnAction(actionEvent -> {
                                    Item data = getTableView().getItems().get(getIndex());
                                    editAction(data);
                                    refreshTable();
                                });
                                break;
                            case DELETE_LABEL:
                                btn.setOnAction((event) -> {
                                    Item data = getTableView().getItems().get(getIndex());
                                    removeAction(data);
                                    refreshTable();
                                });
                        }
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty)
                            setGraphic(null);
                        else
                            setGraphic(btn);
                    }
                };
                return cell;
            }
        };
        tcAction.setCellFactory(cellFactory);
        tableView.getColumns().add(tcAction);
    }

    private void viewAction(Item data) {//TODO
    }

    //  HANDLERS  \\

    /**
     * Add functionality to buttons or events.
     */
    private void addHandlers() {
        backButton.setOnAction(actionEvent -> backAction());
        searchItemButton.setOnAction(actionEvent -> refreshTable());
    }

    private void removeAction(Item object) {
        if (catalogService.remove(object)) {
            System.out.println("Se eliminó correctamente: " + object);//TODO
        } else
            System.out.println("No se eliminó: " + object);//TODO
    }

    private void editAction(Item data) {//TODO

    }

    private void refreshTable() {
        fillTable(tableView);
    }

    private void backAction() {

    }


    /**
     * Modify attributes of an object in a row.
     * Add a Combobox to the column.
     *
     * @param tableColumn that will be editable.
     * @param items       list of options to choose from in the Combo Box.
     */
    private void setEditableColumnComboBox(TableColumn tableColumn, String... items) {
        tableColumn.setCellFactory(ComboBoxTableCell.forTableColumn(items));
        editCellAction(tableColumn);
    }


    /**
     * Modify attributes of an object in a row.
     * Add a textfield to the column.
     *
     * @param tableColumn that will be editable.
     */
    private void setEditableColumnTextFiled(TableColumn tableColumn) {
        tableColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        editCellAction(tableColumn);
    }

    private void editCellAction(TableColumn tableColumn) {
        // Variables \\
        String columnID = tableColumn.getId();

        tableColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent>() {
            @Override
            public void handle(TableColumn.CellEditEvent event) {
                //  Variables  \\
                Integer row = event.getTablePosition().getRow();//índice fila
                Object object = event.getTableView().getItems().get(row);//Objeto a editar

                //VALIDAR INSTANCIA
                if (object instanceof Item) {
                    //Hacer cambiop según sea la columna
                    if (changeValue(columnID, (Item) object, event.getNewValue()))
                        editAction((Item) object);// Guardar
                    else
                        System.out.println("Error al modificar");//TODO mostrar respuesta.
                }
            }
        });
    }


    /**
     * Change the value according to the corresponding column.
     *
     * @param property allows to identify which attribute you want to change
     * @param object   object who will make the change.
     * @param value    new value.
     */
    private Boolean changeValue(String property, Item object, Object value) {
        try {
            switch (property) {
                //case ITEM_NAME_PROPERTY:
                    //object.setName((String) value);
                    //break;
                case CATALOG_SCHEMA_PROPERTY:
                   // object.setSchema((List<Feature>) value);//TODO revisar si funciona en table view, por ser lista
                    break;

            }
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public Pane getPane() {
        return null;
    }
}
