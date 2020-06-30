package edu.ucr.rp.programacion2.proyecto.gui.modules.item;

import edu.ucr.rp.programacion2.proyecto.domain.Catalog;
import edu.ucr.rp.programacion2.proyecto.domain.Inventory;
import edu.ucr.rp.programacion2.proyecto.gui.manage.model.PaneName;
import edu.ucr.rp.programacion2.proyecto.gui.manage.model.PaneViewer;
import edu.ucr.rp.programacion2.proyecto.gui.manage.ManagePane;
import edu.ucr.rp.programacion2.proyecto.logic.ServiceException;
import edu.ucr.rp.programacion2.proyecto.util.builders.BuilderFX;
import edu.ucr.rp.programacion2.proyecto.logic.CatalogFileService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.control.*;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static edu.ucr.rp.programacion2.proyecto.gui.modules.util.LabelConstants.*;
import static edu.ucr.rp.programacion2.proyecto.util.builders.BuilderFX.setButtonEffect;

/**
 * This UI is desing to create a new Item for a Catalog previously selected.
 * @author Jeison Araya Mena | B90514
 */
public class CreateItemForm implements PaneViewer {
    // Variables \\
    private static CreateItemForm instance;
    private static Inventory inventorySelected;
    private static Catalog catalogSelected;
    private static String FEATURE_KEY = TITLE_FEATURE;
    private static String VALUE_KEY = TITLE_VALUE;
    // Components \\
    private static TableView<Map<String, Object>> tableView; // contains 2 columns [features and values].
    private static TableColumn featuresColumn;
    private static TableColumn valuesColumn;
    private static Button createButton;
    private static Button cancelButton;
    private static GridPane pane;
    private static PaneName previousPane;
    // Service \\
    CatalogFileService catalogFileService;

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

    /**
     * This method starts a new service for the inventory selected.
     */
    private void updateCatalogService() {
        if (inventorySelected != null) {
            catalogFileService = new CatalogFileService(inventorySelected);
        }
    }

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
        tableView = BuilderFX.buildTableView(pane, 0, 1, 2, 1);
        // Row #2
        createButton = BuilderFX.buildButton(CREATE_LABEL, pane, 0, 2);
        cancelButton = BuilderFX.buildButton(CANCEL_LABEL, pane, 1, 2);
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
                cellFactoryForMap = param -> {
            return new TextFieldTableCell<>(new StringConverter<String>() {
                @Override
                public String toString(String object) {
                    return object;
                }

                @Override
                public String fromString(String string) {
                    return string;
                }
            });
        };
        tableColumn.setCellFactory(cellFactoryForMap);
        if (editable) {
            tableColumn.setEditable(editable);
            editCellAction(tableColumn);
        }
        tableView.getColumns().add(tableColumn);
        return tableColumn;
    }

    private void editCellAction(TableColumn tableColumn) {
        // Variables \\
        tableColumn.setOnEditCommit((EventHandler<TableColumn.CellEditEvent>) event -> {
            //  Variables  \\
            Object oldValue = event.getOldValue();
            Integer row = event.getTablePosition().getRow();//índice fila

            Map<String, Object> feature = (Map<String, Object>) tableView.getItems().get(row);
            feature.put(VALUE_KEY, event.getNewValue());
            System.out.println("Se cambió " + oldValue + " por " + event.getNewValue() + " en la fila [" + row + "]");
        });
    }

    /**
     * This fills the the table with the schema keys.
     *
     * @param schema of the catalog.
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
    private static ObservableList<Map<String, Object>> generateDataInMap(List<String> list) {
        ObservableList<Map<String, Object>> data = FXCollections.observableArrayList(); // List
        for (String feature : list) {
            Map<String, Object> dataRow = new HashMap<>();
            dataRow.put(FEATURE_KEY, feature);
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
     *
     * @return map of features.
     */
    private static Map<String, Object> getFeaturesMap() {
        // Create Map
        Map<String, Object> features = new HashMap<>();
        // get table items.
        List<Map<String, Object>> items = tableView.getItems();
        // Validate table
        if (tableView != null) {
            // For each rom, get the feature and the value.
            for (Map<String, Object> row : items) {
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
        cancelButton.setOnAction(e -> backAction());
    }

    /**
     * This action finish and don't save the changes made in the table.
     * Also it goes to the previous pane.
     */
    private void backAction() {
        if (previousPane != null) {
            ManagePane.setCenterPane(previousPane);
        } else {
            ManagePane.clearPane();
        }
        refresh();
    }

    /**
     * This actions takes the values form the table and save it has a new item.
     * Also add the new if to the catalog, the show a successful or error message.
     */
    private void createAction() {
        System.out.println("Items in table: " + tableView.getItems());
        System.out.println("Map of features: " + getFeaturesMap());
        // Create Features
        Map<String, Object> item = getFeaturesMap();
        // Create item
        System.out.println("Item creado : " + item);

        if (catalogSelected != null) {
            catalogSelected.getItems().add(item);
            System.out.println("Item agregado al catalogo: " + catalogSelected);

            if (saveChange(catalogSelected)) {
                System.out.println("Cambios guardados correctamente");
                ManageItem.refreshTable();
                backAction();
            }else
                System.out.println("Error al guardar el catálogo");
        }
    }

    /**
     * Ask the catalog service for save the changes done.
     *
     * @param catalogSelected catalog to save.
     * @return {@code true} if has been saved correctly, {@code false} an error occurs.
     */
    private boolean saveChange(Catalog catalogSelected) {
        // Update the service
        updateCatalogService();
        // Validate catalog and service
        try {
            if (catalogSelected != null && catalogFileService != null) {
                return catalogFileService.edit(catalogSelected);
            }
        }catch (ServiceException e){
            System.out.println(e.getMessage());
        }
        return false;
    }


    /**
     * Set the styles of the components.
     */
    private void setupStyles() {
        // Pane
        pane.getStyleClass().add("default-grid-pane");

        // Columns Constraints
        ColumnConstraints columnConstraints = new ColumnConstraints(200, 250, 300);
        columnConstraints.setHalignment(HPos.LEFT);
        columnConstraints.setHgrow(Priority.ALWAYS);

        ColumnConstraints columnConstraints2 = new ColumnConstraints(200, 250, 300);
        columnConstraints.setHalignment(HPos.LEFT);
        columnConstraints.setHgrow(Priority.ALWAYS);

        pane.getColumnConstraints().addAll(columnConstraints, columnConstraints2);
        // Table
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Buttons
        setButtonEffect(createButton);
        setButtonEffect(cancelButton);
    }


    /**
     * Set the inventory that owns the catalog selected.
     *
     * @param inventory selected.
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

    public static void setPreviousPane(PaneName previousPane) {
        CreateItemForm.previousPane = previousPane;
    }

    @Override
    public Pane getPane() {
        return pane;
    }


}
