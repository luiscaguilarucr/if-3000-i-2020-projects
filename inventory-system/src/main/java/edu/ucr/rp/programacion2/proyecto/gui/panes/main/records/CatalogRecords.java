
package edu.ucr.rp.programacion2.proyecto.gui.panes.main.records;
import edu.ucr.rp.programacion2.proyecto.domain.Inventory;
import edu.ucr.rp.programacion2.proyecto.domain.InventoryControl;
import edu.ucr.rp.programacion2.proyecto.gui.model.PaneViewer;
import edu.ucr.rp.programacion2.proyecto.logic.CatalogService;
import edu.ucr.rp.programacion2.proyecto.logic.InventoryControlService;
import edu.ucr.rp.programacion2.proyecto.logic.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

import java.util.List;

import static edu.ucr.rp.programacion2.proyecto.gui.javafx.LabelConstants.*;
import static edu.ucr.rp.programacion2.proyecto.gui.javafx.util.UIConstants.*;
import static edu.ucr.rp.programacion2.proyecto.gui.panes.main.records.Properties.INVENTORY_CONTROL_CATALOG_PROPERTY;
import static edu.ucr.rp.programacion2.proyecto.gui.panes.main.records.Properties.INVENTORY_CONTROL_INVENTORY_PROPERTY;

/**
 * This shows the list of catalogs that belongs to an Inventory.
 *
 * @author Jeison Araya Mena | B90514
 * @version 2.0
 */
public class CatalogRecords implements PaneViewer {
    //  Variables  \\
    private ComboBox createComboBox;
    private ComboBox inventoryComboBox;
    private ComboBox catalogComboBox;
    private TableView tableView;
    private TableColumn inventoryNameColumn;
    private TableColumn catalogNameColumn;
    private TableColumn itemsActionColumn;
    private TableColumn configActionColumn;
    private Label resultsLabel;
    private Boolean adminAuthorization;//TODO mejorar
    private GridPane pane;
    private Service inventoryService;
    private Service catalogService;
    private InventoryControlService inventoryControlService;
    //  Methods  \\

    /**
     * This method initialize the services required.
     */
    private void initializeServices(){
        //inventoryService = InventoryService.getInstance();
        inventoryControlService = InventoryControlService.getInstance();
    }

    private void updateCatalogService(Inventory inventory){
        catalogService = new CatalogService(inventory);
    }
    /**
     * Return the pane with all the components and styles added.
     *
     * @param adminAuthorization allowed to edit and remove information.
     * @return {@code GridPane} pane with components.
     */
    public GridPane getPane(Boolean adminAuthorization) {
        this.adminAuthorization = adminAuthorization;// TODO sacar a una variable estática
        pane = BuilderFX.buildRecordsPane();
        setupControls(pane);
        setupTableView(tableView, adminAuthorization);
        addHandlers();
        return pane;
    }

    /**
     * Configure and add the required components in the pane.
     *
     * @param pane for add components.
     */
    private void setupControls(GridPane pane) {
        //BuilderFX.buildLabelTitle(TITLE_CATALOG_LIST, pane, 0, 0, 2);TODO use other pane.
        createComboBox = BuilderFX.buildComboBox(CREATE_LABEL, pane, 0, 0);
        inventoryComboBox = BuilderFX.buildComboBox(TITLE_INVENTORY, pane, 1, 1);
        catalogComboBox = BuilderFX.buildComboBox(TITLE_CATALOG, pane, 3, 1);
        tableView = BuilderFX.buildTableView(pane, 0, 2,4, 0);
        resultsLabel = BuilderFX.buildLabelMinimal(SEARCH_RESULTS_LABEL, pane, 0, 3, 2);
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


    /**
     * Add necessary columns to display object information.
     *
     * @param tableView table where the columns will be added.
     */
    private void addColumns(TableView tableView) {//TODO CHECK HOW TO ADD COLUMNS
        tableView.getColumns().clear();
        inventoryNameColumn = buildTableColumn(INVENTORY_NAME_COLUMN, INVENTORY_CONTROL_INVENTORY_PROPERTY, tableView);
        catalogNameColumn = buildTableColumn(CATALOG_NAME_COLUMN, INVENTORY_CONTROL_CATALOG_PROPERTY, tableView);
    }

    /**
     * Fills the table with an list of objects.
     *
     * @param tableView table view to add items.
     */
    private void fillTable(TableView tableView) {
        try {
            ObservableList<InventoryControl> list = getList();
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
            addButtonToTable(ITEMS_COLUMN, ITEMS_ICON, tableView);
            addButtonToTable(CONFIG_NAME_COLUMN, CONFIG_ICON, tableView);
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
    public ObservableList<InventoryControl> getList() {
        ObservableList<InventoryControl> observableList;
        List list = inventoryControlService.getAll();


        observableList = FXCollections.observableArrayList(list);

        return observableList;//TODO simplificar
    }


    /**
     * Add buttons to a tableView.
     *
     * @param label Button text
     */
    private void addButtonToTable(String label, ImageView image, TableView tableView) {
        TableColumn tcAction = new TableColumn(label);
        Callback<TableColumn<InventoryControl, Void>, TableCell<InventoryControl, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<InventoryControl, Void> call(final TableColumn<InventoryControl, Void> param) {
                final TableCell<InventoryControl, Void> cell = new TableCell<>() {
                    private final Button btn = new Button();


                    {// Definir funciones del botón
                        btn.setGraphic(image); //agrega imagen
                        //TODO Style and size
                        switch (label) {
                            case ITEMS_COLUMN -> btn.setOnAction(actionEvent -> {
                                InventoryControl data = getTableView().getItems().get(getIndex());
                                viewItemsAction(data);
                                refreshTable();
                            });
                            case CONFIG_NAME_COLUMN -> btn.setOnAction(actionEvent -> {
                                InventoryControl data = getTableView().getItems().get(getIndex());
                                configAction(data);
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

    private void viewItemsAction(InventoryControl inventoryControl) {//TODO
        System.out.println("Going to items table view.. of " + inventoryControl.getCatalogName());
    }

    //  HANDLERS  \\

    /**
     * Add functionality to buttons or events.
     */
    private void addHandlers() {

    }

    private void configAction(InventoryControl inventoryControl) {//TODO

    }

    private void refreshTable() {
        fillTable(tableView);
    }


    @Override
    public Pane getPane() {
        return getPane();
    }
}
