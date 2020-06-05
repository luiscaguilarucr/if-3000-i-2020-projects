package edu.ucr.rp.programacion2.proyecto.gui.panes.main.records;


import edu.ucr.rp.programacion2.proyecto.domain.Catalog;
import edu.ucr.rp.programacion2.proyecto.domain.Inventory;
import edu.ucr.rp.programacion2.proyecto.gui.model.PaneViewer;
import edu.ucr.rp.programacion2.proyecto.logic.CatalogService;
import edu.ucr.rp.programacion2.proyecto.logic.InventoryControlService;
import edu.ucr.rp.programacion2.proyecto.logic.InventoryService;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.List;

import static edu.ucr.rp.programacion2.proyecto.gui.javafx.LabelConstants.*;

/**
 * /**
 * This is a UI that shows the information of one catalog, also the user can edit and delete.
 * <p>
 * Can be redirected to other panes.
 */
public class CatalogConfig implements PaneViewer {
    // Variables  \\
    // Components
    private static ComboBox<String> inventoryComboBox;
    private static ComboBox<String> catalogComboBox;
    private static Button editInventoryButton;
    private static Button deleteInventoryButton;
    private static Button editCatalogButton;
    private static Button deleteCatalogButton;
    private static Separator separator;
    private static TitledPane schemaTitledPane;
    private static TitledPane itemOptionsTitledPane;
    private static ListView schemaList;
    private static VBox itemsOptionsPane;
    private static Button showItemsButton;
    private static Button addItemButton;
    private static Button deleteAllItemsButton;
    private static GridPane pane;
    //  Services
    private static InventoryControlService inventoryControlService;
    private static InventoryService inventoryService;
    private static CatalogService catalogService;

    // Constructor \\


    public CatalogConfig() {
        // Services
        initializeServices();
        // UI
        pane = buildPane();
        setupControls(pane);
        addHandlers();
        setupStyles();
        refresh();

    }

    // Getter  \\
    @Override
    public Pane getPane() {
        return getUI();
    }

    // Methods  \\
    private GridPane getUI() {
        refresh();
        return pane;
    }

    // Setting Services  \\

    /**
     * This method initialize the services required.
     */
    private void initializeServices() {
        inventoryControlService = InventoryControlService.getInstance();
        inventoryService = InventoryService.getInstance();
    }

    private static void updateCatalogService(Inventory inventory) {
        catalogService = new CatalogService(inventory);
    }

    //  Builders  \\

    /**
     * Builds the main pane.
     *
     * @return {@code GridPane} Grid pane configured.
     */
    private GridPane buildPane() {
        GridPane gridPane = new GridPane();
        // more code...
        return gridPane;
    }

    /**
     * Configure and add the required components in the pane.
     *
     * @param pane for add components.
     */
    private void setupControls(GridPane pane) {
        // Row 0
        BuilderFX.buildLabelTitle(TITLE_CATALOG_CONFIG, pane, 0, 0, 2, 1);
        // Row 1
        BuilderFX.buildLabelNormal(TITLE_INVENTORY, pane, 0, 1);
        inventoryComboBox = BuilderFX.buildComboBox(SELECT_LABEL, pane, 1, 1);
        editInventoryButton = BuilderFX.buildIconButton("", EDIT_ICON, pane, 2, 1);
        deleteInventoryButton = BuilderFX.buildIconButton("", DELETE_ICON, pane, 3, 1);
        // Row 2
        BuilderFX.buildLabelNormal(CATALOG_NAME_COLUMN, pane, 0, 2);
        catalogComboBox = BuilderFX.buildComboBox(SELECT_LABEL, pane, 1, 2);
        editCatalogButton = BuilderFX.buildIconButton("", EDIT_ICON, pane, 2, 2);
        deleteCatalogButton = BuilderFX.buildIconButton("", DELETE_ICON, pane, 3, 2);
        // Row 3
        separator = BuilderFX.buildSeparator(Orientation.HORIZONTAL, pane, 0, 3, 4, 1);
        // Row 4
        // Schema
        schemaList = buildListView();
        BuilderFX.buildLabelNormal(TITLE_CATALOG_SCHEMA, pane, 0, 4);
        schemaTitledPane = BuilderFX.buildTitledPane(VIEW_LABEL, schemaList, pane, 1, 4, 1, 1);// TODO check text
        // Items
        BuilderFX.buildLabelNormal(TITLE_ITEMS, pane, 2, 4);
        showItemsButton = buildItemButton(VIEW_LABEL);
        addItemButton = buildItemButton(ADD_LABEL);
        deleteAllItemsButton = buildItemButton(DELETE_LABEL);
        itemsOptionsPane = buildHBoxItemsOptions(addItemButton, showItemsButton, deleteAllItemsButton);
        itemOptionsTitledPane = BuilderFX.buildTitledPane(VIEW_LABEL, itemsOptionsPane, pane, 3, 4, 1, 1);// TODO check text
    }

    private static void setupStyles() {
        // Pane
        pane.getStyleClass().add("default-grid-pane");
        // Row Constraints
        // Row #0
        RowConstraints rowConstraints = new RowConstraints(40, 45, 50);
        rowConstraints.setValignment(VPos.TOP);
        rowConstraints.setVgrow(Priority.ALWAYS);
        // Row #1
        RowConstraints rowConstraints1 = new RowConstraints(40, 60, 75);
        rowConstraints1.setValignment(VPos.CENTER);
        rowConstraints.setVgrow(Priority.ALWAYS);
        // Row #2
        RowConstraints rowConstraints2 = new RowConstraints(40, 60, 75);
        rowConstraints2.setValignment(VPos.CENTER);
        rowConstraints.setVgrow(Priority.ALWAYS);
        // Row #3
        RowConstraints rowConstraints3 = new RowConstraints(20, 20, 20);
        rowConstraints3.setValignment(VPos.CENTER);
        rowConstraints.setVgrow(Priority.ALWAYS);
        // Row #4
        RowConstraints rowConstraints4 = new RowConstraints(150, 175, 200);
        rowConstraints4.setValignment(VPos.TOP);
        rowConstraints.setVgrow(Priority.ALWAYS);
        // Add Row Constraints
        pane.getRowConstraints().addAll(rowConstraints, rowConstraints1, rowConstraints2, rowConstraints3, rowConstraints4);

        // Columns Constraints
        ColumnConstraints columnConstraints = new ColumnConstraints(75, 100, 200);
        columnConstraints.setHalignment(HPos.RIGHT);
        columnConstraints.setHgrow(Priority.ALWAYS);

        ColumnConstraints columnConstraints2 = new ColumnConstraints(150, 175, 200);
        columnConstraints.setHalignment(HPos.LEFT);
        columnConstraints.setHgrow(Priority.ALWAYS);

        ColumnConstraints columnConstraints3 = new ColumnConstraints(100, 200, 200);
        columnConstraints3.setHalignment(HPos.RIGHT);
        columnConstraints3.setHgrow(Priority.ALWAYS);

        ColumnConstraints columnConstraints4 = new ColumnConstraints(100, 200, 200);
        columnConstraints4.setHalignment(HPos.LEFT);
        columnConstraints4.setHgrow(Priority.ALWAYS);
        pane.getColumnConstraints().addAll(columnConstraints, columnConstraints2, columnConstraints3, columnConstraints4);

        // Settings for Table Columns
        // Inventory ComboBox
        inventoryComboBox.setMinWidth(100);
        // Catalog ComboBox
        catalogComboBox.setMinWidth(100);
        // Schema
        schemaList.setMaxSize(250, 600);
        schemaTitledPane.setMaxSize(250, 600);
        // Items
        itemOptionsTitledPane.setMaxSize(250, 600);
        // Buttons
        setButtonEffect(addItemButton);
        setButtonEffect(deleteAllItemsButton);
        setButtonEffect(showItemsButton);

    }

    private static void setButtonEffect(Button button){
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
     * Create a List view and place it in a pane.
     *
     * @return {@code ListView <>} list view ready to add columns and objects.
     */
    private ListView<String> buildListView() {
        ListView<String> listView = new ListView();
        // More code..
        return listView;
    }

    /**
     * Creates an VBox with buttons.
     *
     * @param buttons to add.
     * @return {@code VBox} vbox configured.
     */
    private VBox buildHBoxItemsOptions(Button... buttons) {
        VBox vBox = new VBox(buttons);
        vBox.getStyleClass().add("default-v-box");
        return vBox;
    }


    private Button buildItemButton(String text) {
        Button button = new Button(text);
        // More code..
        return button;
    }

    //  Handlers  \\
    private static void addHandlers() {
        inventoryComboBox.setOnAction(event -> inventoryChangedEvent());
        catalogComboBox.setOnAction(e -> catalogChangedEvent());
        showItemsButton.setOnAction(e -> showItemsAction());
        addItemButton.setOnAction(e -> addItemsAction());
        deleteAllItemsButton.setOnAction(e -> deleteAllItemsAction());


    }

    private static void showItemsAction() {// TODO add new Item
    }

    private static void addItemsAction() {// TODO showItems
    }

    private static void deleteAllItemsAction() {// TODO deleteAll

    }


    // Events

    /**
     * This event is triggered when the inventory box changes it's value.
     */
    private static void inventoryChangedEvent() {
        // Inventory Config
        enableInventoryOptions(true);
        fillInventoryComboBox();//TODO considerar
        // Catalog Config
        fillCatalogComboBox(inventoryComboBox.getValue());
        catalogComboBox.setDisable(false);
        enableCatalogOptions(false);
        enableTitles(false);
        /*try {
            Thread.sleep(1000);
            System.out.println("Inventory triggered.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }

    /**
     * This event is triggered when the catalog box changes it's value.
     */
    private static void catalogChangedEvent() {
        enableCatalogOptions(true);
        fillSchemaList(catalogComboBox.getValue());
        enableTitles(true);
    }
    // Updaters

    /**
     * Fills the inventory Combo box
     */
    private static void fillInventoryComboBox() {
        // Get the list of inventories
        List<String> inventoryNames = inventoryService.getNamesList();
        // Validate list
        if (inventoryNames != null) {
            if (inventoryNames.isEmpty()) {
                // Case #1 List is empty
                inventoryComboBox.setPlaceholder(new Label("There are no inventories."));
            } else {
                //Case #2 The list has inventories.
                BuilderFX.fillComboBox(inventoryComboBox, inventoryNames);
            }
        }

    }

    /**
     * This methods fills the catalog combo Box with all catalog's names in the inventory selected.
     *
     * @param inventoryName to extract the list of catalogs.
     */
    private static void fillCatalogComboBox(String inventoryName) {
        // Get Inventory
        Inventory inventory = inventoryService.get(inventoryName);
        // Validate Inventory
        if (inventory != null) {
            // Get catalog list
            updateCatalogService(inventory);
            List<String> catalogList = catalogService.getNamesList();
            // Validate list
            if (catalogList != null && !catalogList.isEmpty()) {
                // Case #1 The list has catalogs
                BuilderFX.fillComboBox(catalogComboBox, catalogList);
                catalogComboBox.setTooltip(new Tooltip("Select one catalog."));
                catalogComboBox.setPromptText("Select");
                catalogComboBox.setDisable(false);
            } else {
                // Case #2 The list is empty or invalid
                catalogComboBox.getItems().clear();
                catalogComboBox.setPromptText(inventoryName + " doesn't have catalogs.");
                catalogComboBox.setDisable(true);
            }
        } else {
            // Inventory invalid or none selected
            catalogComboBox.setDisable(true);
            catalogComboBox.getItems().clear();
            catalogComboBox.setPromptText("Select an inventory first.");
        }
    }

    /**
     * Updates the list of the list view if one catalog is selected.
     */
    private static void fillSchemaList(String catalogName) {

        // Validate catalog
        Catalog catalog = catalogService.get(catalogName);
        if (catalog != null) {
            // Get catalog list
            BuilderFX.fillListView(schemaList, catalog.getSchema());
        }
    }


    /**
     * Disable and collapse the titlePane
     *
     * @param state {@code true} enable and expand the panes, {@code false} disables and collapse the panes.
     */
    private static void enableTitles(boolean state) {
        // Items
        itemOptionsTitledPane.setExpanded(state);
        itemOptionsTitledPane.setDisable(!state);
        // Schema
        schemaTitledPane.setExpanded(state);
        schemaTitledPane.setDisable(!state);
    }


    /**
     * Enables the inventory's edit and delete options
     */
    private static void enableInventoryOptions(boolean state) {
        editInventoryButton.setDisable(!state);
        deleteInventoryButton.setDisable(!state);
    }

    /**
     * Enables the catalog's edit and delete options
     */
    private static void enableCatalogOptions(boolean state) {
        editCatalogButton.setDisable(!state);
        deleteCatalogButton.setDisable(!state);
    }


    public static void refresh() {
        // ComboBoxes to default
        setInventoryComboBoxDefault();
        setCatalogComboBoxDefault();
        // disable Buttons
        enableInventoryOptions(false);
        enableCatalogOptions(false);
        // collapse and disable titledPanes
        enableTitles(false);
    }

    /**
     * Sets the inventory combo Box default
     */
    private static void setInventoryComboBoxDefault() {
        // #1 Fill with options
        fillInventoryComboBox();
        // #2 Selection none
        inventoryComboBox.getSelectionModel().clearSelection();
        // #3 PromptText "Select"
        inventoryComboBox.setPromptText(SELECT_LABEL);// TODO Error: doesn't show the promptText again...
    }

    /**
     * Sets the inventory combo Box default
     */
    private static void setCatalogComboBoxDefault() {
        // #1 Fill with options
        fillCatalogComboBox(inventoryComboBox.getValue());
        // #2 Selection none
        catalogComboBox.getSelectionModel().clearSelection();
        // #3 PromptText "Select"
        catalogComboBox.setPromptText(SELECT_LABEL);// TODO Error: doesn't show the promptText again...
    }

    public static void setInventory(String inventory) {
        inventoryComboBox.setValue(inventory);
    }

    public static void setCatalog(String catalog) {
        catalogComboBox.setValue(catalog);
    }
}
