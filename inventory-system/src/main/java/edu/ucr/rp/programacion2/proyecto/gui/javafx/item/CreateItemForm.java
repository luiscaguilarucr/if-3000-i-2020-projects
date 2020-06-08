package edu.ucr.rp.programacion2.proyecto.gui.javafx.item;

import edu.ucr.rp.programacion2.proyecto.domain.Catalog;
import edu.ucr.rp.programacion2.proyecto.domain.Inventory;
import edu.ucr.rp.programacion2.proyecto.gui.model.PaneViewer;
import edu.ucr.rp.programacion2.proyecto.gui.panes.main.ManagePane;
import edu.ucr.rp.programacion2.proyecto.gui.panes.main.records.BuilderFX;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static edu.ucr.rp.programacion2.proyecto.gui.javafx.LabelConstants.*;

/**
 * This UI is desing to create a new Item for a Catalog previously selected.
 */
public class CreateItemForm implements PaneViewer {
    // Variables \\
    private static CreateItemForm instance;
    private static Inventory inventorySelected;
    private static Catalog catalogSelected;
    private static Map dataMap;
    private static String FEATURE_KEY = TITLE_FEATURE;
    private static String VALUE_KEY = TITLE_VALUE;
    // Components \\
    private static Label informationLabel; // Information about the inventory and catalog selected.
    private static TableView tableView; // contains 2 columns [features and values].
    private static TableColumn featuresColumn;
    private static TableColumn valuesColumn;
    private static Button createButton;
    private static Button cancelButton;
    private static GridPane pane;
    private static Pane previousPane;

    // Constructor \\
    private CreateItemForm() {
        pane = buildPane();
        setupControls(pane);
        setupTableView(tableView);
        addHandlers();
        setupStyles();
    }

    // Singleton Pattern \\
    public static CreateItemForm getInstance() {
        if (instance == null)
            instance = new CreateItemForm();
        return instance;
    }
    // Methods  \\

    private GridPane buildPane() {
        pane = new GridPane();
        return pane;
    }

    /**
     * Configure and add the required components in the pane.
     *
     * @param pane for add components.
     */
    private void setupControls(GridPane pane) {
        // Row #0
        BuilderFX.buildLabelTitle(TITLE_CREATE_ITEM, pane, 0, 0, 2, 1);
        // Row #1
        informationLabel = BuilderFX.buildLabelMinimal("Inventory/catalog/", pane, 0, 1, 1);// TODO Check if is looking good..
        // Row #2
        BuilderFX.buildLabelNormal(TITLE_ITEM, pane, 0, 2);
        // Row #3
        tableView = BuilderFX.buildTableView(pane, 0, 3, 2, 1);
        // Row #4
        createButton = BuilderFX.buildButton(CREATE_LABEL, pane, 0, 4);
        cancelButton = BuilderFX.buildButton(CANCEL_LABEL, pane, 1, 4);
        // Columns
        featuresColumn = buildTableColumn(TITLE_FEATURE, TITLE_FEATURE, tableView, false);
        valuesColumn = buildTableColumn(TITLE_VALUE, TITLE_VALUE, tableView, true);
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
    private void setupTableView(TableView tableView) {
        tableView.setEditable(true);
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
    public TableColumn<Map, String> buildTableColumn(String text, String property, TableView tableView, boolean editable) {
        TableColumn<Map, String> tableColumn = new TableColumn(text);
        // Settings
        tableColumn.setId(property);
        tableColumn.setCellValueFactory(new MapValueFactory<>(property));
        Callback<TableColumn<Map, String>, TableCell<Map, String>>
                cellFactoryForMap = param -> new TextFieldTableCell<>(new StringConverter<String>() {
            @Override
            public String toString(String object) {
                return object;
            }

            @Override
            public String fromString(String string) {
                return string;
            }
        });
        tableColumn.setCellFactory(cellFactoryForMap);
        if(editable){
            tableColumn.setEditable(editable);
            editCellAction(tableColumn);
        }
        tableView.getColumns().add(tableColumn);
        return tableColumn;
    }

    private void editCellAction(TableColumn tableColumn) {
        // Variables \\
        tableColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent>() {
            @Override
            public void handle(TableColumn.CellEditEvent event) {
                //  Variables  \\
                Integer row = event.getTablePosition().getRow();//índice fila

                Map<String, Object> feature = (Map<String, Object>) tableView.getItems().get(row);
                feature.put(VALUE_KEY, event.getNewValue());
                System.out.println("Se cambió " + event.getOldValue()+ " por " + event.getNewValue() + " en la fila [" + row + "]");
            }
        });
    }
    /**
     * This fills the the table with the schema keys.
     *
     * @param schema
     */
    private static void fillTable(List<String> schema) {
        tableView.getItems().clear();
        tableView.getItems().addAll(generateDataInMap(schema));
    }

    /**
     * Creates a list of maps, where each map represents a row.
     * <p>
     * Each row's maps has something like this {
     * Feature : feature's name,
     * Value : null
     * }
     *
     * @param list list of feature's names.
     * @return {ObservableList}  observable list with the features and values.
     */
    private static ObservableList<Map> generateDataInMap(List<String> list) {
        ObservableList<Map> data = FXCollections.observableArrayList(); // List
        for (String feature : list) {
            Map<String, String> dataRow = new HashMap<>();
            dataRow.put(FEATURE_KEY, feature);//Todo Test
            dataRow.put(VALUE_KEY, null);
            data.add(dataRow);
        }
        System.out.println(data);
        return data;

    }

    /**
     * This method brings the list of items in the table.
     * Then extract for each row the feature and the value.
     * Put each feature : value in a map.
     * @return
     */
    private static Map<String, Object> getFeaturesMap(){
        // Create Map
        Map<String, Object> features = new HashMap<>();
        // get table items.
        List<Map<String, Object>> items = tableView.getItems();
        // Validate table
        if(tableView != null){
            // For each rom, get the feature and the value.
            for(Map<String, Object> row : items){
                // Feature : Value
                String feature = (String) row.get(FEATURE_KEY);
                Object value = row.get(VALUE_KEY);
                features.put(feature, value);
            }

        }
        return features;

    }
    /**
     * Add functionality to buttons or events.
     */
    private void addHandlers() {
        createButton.setOnAction(e -> createAction());
        cancelButton.setOnAction(e -> cancelAction());
    }

    /**
     * This action finish and don't save the changes made in the table.
     * Also it goes to the previous pane.
     */
    private void cancelAction() {
        if (previousPane != null) {
            ManagePane.setCenterPane(previousPane);
        } else {
            ManagePane.clearPane();
        }
        refresh();
    }

    /**
     * This actions takes the values form the table and save it has a new item.
     */
    private void createAction() {
        System.out.println("Items in table: " +tableView.getItems());
        System.out.println("Map of features: " + getFeaturesMap() );
    }


    /**
     * Set the styles of the components.
     */
    private void setupStyles() {
    }


    /**
     * Set the inventory that owns the catalog selected.
     *
     * @param inventory
     */
    public static void setInventory(Inventory inventory) {
        inventorySelected = inventory;
        System.out.println("Inventory selected: " + inventory);

    }

    /**
     * Set the catalog that is going to add a new Item.
     *
     * @param catalog to add the item
     */
    public static void setCatalog(Catalog catalog) {
        catalogSelected = catalog;
        System.out.println("Catalog selected: " + catalog);
        refreshTable();
    }

    public static void refresh() {
        previousPane = null;
        inventorySelected = null;
        catalogSelected = null;
        System.out.println("Refresh of " + CreateItemForm.class);
        refreshTable();
    }

    /**
     * This method refresh the table with the values of the catalog's schema, if is one catalog selected.
     */
    private static void refreshTable() {
        if (catalogSelected != null) {
            fillTable(catalogSelected.getSchema());
            System.out.println("Schema in table: " + catalogSelected.getSchema());
        }
    }

    public static void setPreviousPane(Pane previousPane) {
        CreateItemForm.previousPane = previousPane;
    }

    @Override
    public Pane getPane() {
        return pane;
    }


}
