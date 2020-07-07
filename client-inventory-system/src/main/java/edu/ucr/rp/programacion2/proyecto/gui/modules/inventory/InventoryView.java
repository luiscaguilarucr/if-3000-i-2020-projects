
package edu.ucr.rp.programacion2.proyecto.gui.modules.inventory;

import edu.ucr.rp.programacion2.proyecto.domain.Catalog;
import edu.ucr.rp.programacion2.proyecto.domain.Inventory;
import edu.ucr.rp.programacion2.proyecto.gui.manage.ManagePane;
import edu.ucr.rp.programacion2.proyecto.gui.manage.model.PaneName;
import edu.ucr.rp.programacion2.proyecto.gui.manage.model.PaneViewer;
import edu.ucr.rp.programacion2.proyecto.gui.modules.catalog.CatalogConfig;
import edu.ucr.rp.programacion2.proyecto.gui.modules.item.ManageItem;
import edu.ucr.rp.programacion2.proyecto.gui.modules.util.PaneUtil;
import edu.ucr.rp.programacion2.proyecto.logic.*;
import edu.ucr.rp.programacion2.proyecto.util.builders.BuilderFX;
import edu.ucr.rp.programacion2.proyecto.domain.InventoryControl;
import edu.ucr.rp.programacion2.proyecto.domain.InventoryControlManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Callback;

import java.util.List;

import static edu.ucr.rp.programacion2.proyecto.gui.modules.util.LabelConstants.*;
import static edu.ucr.rp.programacion2.proyecto.gui.modules.util.UIConstants.*;
import static edu.ucr.rp.programacion2.proyecto.util.builders.BuilderFX.setButtonEffect;

/**
 * This shows the list of catalogs that belongs to an Inventory.
 *
 * @author Jeison Araya Mena | B90514
 * @version 2.0
 */
public class InventoryView implements PaneViewer {
    //  Variables  \\
    private static TitledPane createTiledPane;
    private static HBox createOptionsHBox;
    private static Button createInventoryButton;
    private static Button createCatalogButton;
    private static TextField filterField;
    private static TableView tableView;
    private static TableColumn inventoryNameColumn;
    private static TableColumn catalogNameColumn;
    private static TableColumn itemsActionColumn;
    private static TableColumn configActionColumn;
    private static Pagination pagination;
    private static Label resultsLabel;
    private static GridPane pane;
    private static InventoryService inventoryFileService;
    private static InventoryControlManager inventoryControlManager;
    private static CatalogService catalogService;
    private static CatalogConfig catalogConfig = new CatalogConfig();
    private static Button backButton;
    //  Methods  \\

    /**
     * This method initialize the services required.
     */
    private void initializeServices() {
        inventoryFileService = InventorySocketService.getInstance();
        inventoryControlManager = InventoryControlManager.getInstance();

    }

    /**
     * Return the pane with all the components and styles added.
     *
     * @return {@code GridPane} pane with components.
     */
    public GridPane createPane() {
        initializeServices();
        pane = BuilderFX.buildRecordsPane();
        setupControls(pane);
        setupTableView(tableView);
        addHandlers();
        setupStyles();
        return pane;
    }

    /**
     * Configure and add the required components in the pane.
     *
     * @param pane for add components.
     */
    private void setupControls(GridPane pane) {
        // Row #0
        BuilderFX.buildLabelTitle(TITLE_VIEW_INVENTORY, pane, 0, 0, 2, 1);
        // Row #1
        createInventoryButton = new Button(TITLE_INVENTORY);
        createCatalogButton = new Button(TITLE_CATALOG);
        createOptionsHBox = new HBox(createInventoryButton, createCatalogButton);
        createTiledPane = BuilderFX.buildTitledPane(NEW_LABEL, createOptionsHBox, pane, 0, 1, 2, 1);
        // Row #2
        filterField = BuilderFX.buildTextInput("", pane, 3, 1);
        tableView = BuilderFX.buildTableView(pane, 0, 2, 4, 1);
        resultsLabel = BuilderFX.buildLabelMinimal("", pane, 0, 3, 2);
        backButton = BuilderFX.buildButton(BACK_LABEL, pane, 2, 3);
    }

    /**
     * Set the styles of the components.
     */
    private void setupStyles() { //TODO how to simplify. ->
        // Pane
        pane.getStyleClass().add("show-inventory-pane");
        // Row Constraints
        // Row #0
        RowConstraints rowConstraints = new RowConstraints(25, 25, 30);
        rowConstraints.setValignment(VPos.TOP);
        rowConstraints.setVgrow(Priority.SOMETIMES);
        // Row #1
        RowConstraints rowConstraints1 = new RowConstraints(50, 50, 150);
        rowConstraints1.setValignment(VPos.TOP);
        rowConstraints.setVgrow(Priority.ALWAYS);
        // Row #2
        RowConstraints rowConstraints2 = new RowConstraints(500, 600, 600);
        rowConstraints2.setValignment(VPos.TOP);
        rowConstraints.setVgrow(Priority.NEVER);
        // Row #3
        RowConstraints rowConstraints3 = new RowConstraints(25, 25, 40);
        rowConstraints3.setValignment(VPos.TOP);
        rowConstraints.setVgrow(Priority.NEVER);
        // Add Row Constraints
        pane.getRowConstraints().addAll(rowConstraints, rowConstraints1, rowConstraints2, rowConstraints3);

        // Columns Constraints
        ColumnConstraints columnConstraints = new ColumnConstraints(300, 300, 300);
        columnConstraints.setHalignment(HPos.LEFT);
        columnConstraints.setHgrow(Priority.ALWAYS);

        ColumnConstraints columnConstraints2 = new ColumnConstraints(150, 175, 200);
        columnConstraints.setHalignment(HPos.LEFT);
        columnConstraints.setHgrow(Priority.ALWAYS);

        ColumnConstraints columnConstraints3 = new ColumnConstraints(200, 200, 200);
        columnConstraints3.setHalignment(HPos.RIGHT);
        columnConstraints3.setHgrow(Priority.NEVER);

        ColumnConstraints columnConstraints4 = new ColumnConstraints(75, 75, 150);
        columnConstraints4.setHalignment(HPos.RIGHT);
        columnConstraints4.setHgrow(Priority.ALWAYS);
        pane.getColumnConstraints().addAll(columnConstraints, columnConstraints2, columnConstraints3, columnConstraints4);

        // Settings for Table Columns
        // Inventory Column
        inventoryNameColumn.setMinWidth(100);
        // Catalog Column
        catalogNameColumn.setMinWidth(100);
        // Items Column
        itemsActionColumn.getStyleClass().add("table-view-column-buttons");
        itemsActionColumn.setMaxWidth(70);
        itemsActionColumn.setMinWidth(70);
        // Config Column
        configActionColumn.getStyleClass().add("table-view-column-buttons");
        configActionColumn.setMaxWidth(70);
        configActionColumn.setMinWidth(70);
        // Settings for Table View
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.setEditable(false);
        tableView.setMinSize(TABLE_VIEW_DEFAULT_MIN_WIDTH, TABLE_VIEW_DEFAULT_MIN_HEIGHT);
        tableView.setMaxSize(TABLE_VIEW_DEFAULT_MAX_WIDTH, TABLE_VIEW_DEFAULT_MAX_HEIGHT);

        // Label
        resultsLabel.getStyleClass().add("results-label");
        createOptionsHBox.setSpacing(10);
        //Button
        setButtonEffect(createInventoryButton);
        setButtonEffect(createCatalogButton);
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
        addColumns(tableView);
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
        tableColumn.setId(property);
        tableColumn.setCellValueFactory(new PropertyValueFactory(property));
        tableView.getColumns().add(tableColumn);
        return tableColumn;
    }

    /**
     * Add necessary columns to display object information.
     *
     * @param tableView table where the columns will be added.
     */
    private void addColumns(TableView<InventoryControl> tableView) {
        tableView.getColumns().clear();
        inventoryNameColumn = buildTableColumn(TITLE_INVENTORY, "inventoryName", tableView);
        catalogNameColumn = buildTableColumn(TITLE_CATALOG, "catalogName", tableView);
        itemsActionColumn = buildButtonColumn(ITEMS_COLUMN, ITEMS_ICON, tableView);
        configActionColumn = buildButtonColumn(CONFIG_NAME_COLUMN, CONFIG_ICON, tableView);
    }

    /**
     * Fills the table with an list of objects.
     *
     * @param tableView table view to add items.
     */
    private static void fillTable(TableView<InventoryControl> tableView) {
        try {
            ObservableList<InventoryControl> listFiltered = getFilteredList();
            tableView.setItems(listFiltered);

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage() + ", in " + e.getCause());
        }
    }

    /**
     * The list is filtered using the input in the searchTextInput.
     *
     * @return
     */
    private static FilteredList<InventoryControl> getFilteredList() {
        //  Getting list
        ObservableList<InventoryControl> items = getObservableList(getList());
        FilteredList<InventoryControl> filteredList = new FilteredList<>(items);
        // Adding filters
        // Case #1 -> Show all
        filteredList.setPredicate(b -> true);

        // Case #2 -> Show content searched in filterField.
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(inventoryControl -> {
                // SubCase #1 filter is empty or null -> show all
                if (newValue == null || newValue.isEmpty()) return true;

                // Get the input.
                String inputFilter = newValue.toLowerCase();
                // SubCase #2 filter the name of the inventories.
                if (inventoryControl.getInventoryName().toLowerCase().contains(inputFilter)) {
                    return true;
                }
                // SubCase #3 filter the name of the catalogs.
                if (inventoryControl.getCatalogName() != null)
                    return inventoryControl.getCatalogName().toLowerCase().contains(inputFilter);
                return false;
            });
            // Update results message.
            updateResultsLabel();
        });

        return filteredList;
    }

    /**
     * Converts the list of inventory Control into a ObservableList.
     *
     * @return {@code ObservableList} observable list with existing objects.
     */
    private static ObservableList<InventoryControl> getObservableList(List<InventoryControl> inventoryControls) {
        return FXCollections.observableArrayList(inventoryControls);
    }

    /**
     * Get the list of all the inventories and catalogs from InventoryControl Service.
     *
     * @return {@code List} list with register in inventory service.
     */
    private static List<InventoryControl> getList() {
        return inventoryControlManager.getAll();
    }

    /**
     * Add buttons to a tableView.
     * get
     *
     * @param label Button text
     */
    private TableColumn buildButtonColumn(String label, String image, TableView tableView) {
        TableColumn tableColumn = new TableColumn(label);
        Callback<TableColumn<InventoryControl, Void>, TableCell<InventoryControl, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<InventoryControl, Void> call(final TableColumn<InventoryControl, Void> param) {
                final TableCell<InventoryControl, Void> cell = new TableCell<>() {
                    private final Button btn = new Button("");

                    {// Definir funciones del botón
                        btn.setGraphic(new ImageView(new Image(image)));
                        btn.getStyleClass().add("table-buttons");
                        switch (label) {
                            case ITEMS_COLUMN:
                                btn.setOnAction(actionEvent -> {
                                    InventoryControl data = getTableView().getItems().get(getIndex());
                                    viewItemsAction(data);
                                });
                                break;
                            case CONFIG_NAME_COLUMN:
                                btn.setOnAction(actionEvent -> {
                                    InventoryControl data = getTableView().getItems().get(getIndex());
                                    configAction(data);
                                });
                                break;
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
        tableColumn.setCellFactory(cellFactory);
        tableView.getColumns().add(tableColumn);
        return tableColumn;
    }


    //  HANDLERS  \\

    /**
     * Add functionality to buttons or events.
     */
    private void addHandlers() {
        createCatalogButton.setOnAction(e -> createCatalogAction());
        createInventoryButton.setOnAction(e -> createInventoryAction());
        backButton.setOnAction(e -> backAction());
    }

    private void backAction() {
        ManagePane.clearPane();
        refresh();
    }

    private void createInventoryAction() {
        refresh();
        ManagePane.setCenterPane(PaneName.ADD_INVENTORY);
        System.out.println("Create Inventory Button pressed");
    }

    private void createCatalogAction() {
        refresh();
        ManagePane.setCenterPane(PaneName.CREATE_CATALOG_FORM);
        System.out.println("Create Catalog Button pressed");
    }

    // Table Buttons
    private void viewItemsAction(InventoryControl inventoryControl) {//TODO actionEvent
        if (inventoryControl.getCatalogName() != null) {
            ManageItem.refresh();
            try {
                ManagePane.setCenterPane(PaneName.MANAGE_ITEM);
                Inventory inventory = inventoryFileService.get(inventoryControl.getInventoryName());
                ManageItem.setInventorySelected(inventory);
                catalogService = CatalogSocketService.getInstance();
                catalogService.setInventory(inventory);
                Catalog catalog = catalogService.get(inventoryControl.getCatalogName());
                ManageItem.setCatalogSelected(catalog);
                ManageItem.setPreviousPane(PaneName.SHOW_INVENTORY);
                System.out.println("Showing to items of " + inventoryControl);
                refresh();
            } catch (ServiceException e) {
                System.out.println(e.getMessage());
            }
        } else
            PaneUtil.showAlert(Alert.AlertType.INFORMATION, "There are no catalogs", "You must add at least one catalog on this inventory to be able to access this function");
    }

    private void configAction(InventoryControl inventoryControl) {
        ManagePane.setCenterPane(PaneName.CATALOG_CONFIG);
        CatalogConfig.refresh();
        try {
            Inventory inventory = inventoryFileService.get(inventoryControl.getInventoryName());
            if (inventory != null) {
                CatalogConfig.setInventory(inventory);
                catalogService = CatalogSocketService.getInstance();
                catalogService.setInventory(inventory);
                Catalog catalog = catalogService.get(inventoryControl.getCatalogName());
                CatalogConfig.setCatalog(catalog);
                System.out.println("Going to config table view.. of " + inventoryControl.getCatalogName());
                refresh();
            }
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * Refresh the pane. Cleans all the components.
     */
    public static void refresh() {
        filterField.clear();
        refreshTable();
        updateResultsLabel();
    }

    /**
     * This refresh the items in the table with the lastOnes.
     */
    private static void refreshTable() {
        fillTable(tableView);
        updateResultsLabel();
    }

    /**
     * Updates the label of the matches and number of items showed in the table.
     */
    private static void updateResultsLabel() {
        int total = getList().size();                   // Total of inventories and catalogs.
        int current = tableView.getItems().size();      // Number of inventories and catalogs in the table.
        resultsLabel.setText("Showing " + current + " of " + total + " results.");
    }

    @Override
    public Pane getPane() {
        return createPane();
    }
}
