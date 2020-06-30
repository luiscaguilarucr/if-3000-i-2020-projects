package edu.ucr.rp.programacion2.proyecto.gui.modules.catalog;


import edu.ucr.rp.programacion2.proyecto.domain.Catalog;
import edu.ucr.rp.programacion2.proyecto.domain.Inventory;
import edu.ucr.rp.programacion2.proyecto.gui.modules.inventory.DeleteInventory;
import edu.ucr.rp.programacion2.proyecto.gui.modules.item.CreateItemForm;
import edu.ucr.rp.programacion2.proyecto.gui.manage.model.PaneName;
import edu.ucr.rp.programacion2.proyecto.gui.manage.model.PaneViewer;
import edu.ucr.rp.programacion2.proyecto.gui.manage.ManagePane;
import edu.ucr.rp.programacion2.proyecto.gui.modules.item.ManageItem;
import edu.ucr.rp.programacion2.proyecto.logic.CatalogFileService;
import edu.ucr.rp.programacion2.proyecto.logic.ServiceException;
import edu.ucr.rp.programacion2.proyecto.logic.InventoryFileService;
import edu.ucr.rp.programacion2.proyecto.util.builders.BuilderFX;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.List;
import java.util.Optional;

import static edu.ucr.rp.programacion2.proyecto.gui.modules.util.LabelConstants.*;
import static edu.ucr.rp.programacion2.proyecto.util.builders.BuilderFX.setButtonEffect;

/**
 * @author Jeison Araya Mena | B90514
 * <p>
 * This is a UI that shows the information of one catalog, also the user can edit and delete.
 * <p>
 * Can be redirected to other panes.
 */
public class CatalogConfig implements PaneViewer {
    // Variables  \\
    private static Inventory inventorySelected;
    // Components
    private static ComboBox<Inventory> inventoryComboBox;
    private static ComboBox<Catalog> catalogComboBox;
    private static Button editInventoryButton;
    private static Button deleteInventoryButton;
    private static Button editCatalogButton;
    private static Button deleteCatalogButton;
    private static Separator separator;
    private static TitledPane schemaTitledPane;
    private static TitledPane itemOptionsTitledPane;
    private static ListView<String> schemaList;
    private static VBox itemsOptionsPane;
    private static Button showItemsButton;
    private static Button addItemButton;
    private static Button deleteAllItemsButton;
    private static GridPane pane;
    private static Alert deleteAlert;
    private static ButtonType buttonTypeYes;
    private static ButtonType buttonTypeNo;
    private static TextInputDialog textInputDialog;
    //  Services
    private static InventoryFileService inventoryService;
    private static CatalogFileService catalogService;

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
        inventoryService = InventoryFileService.getInstance();
    }

    private static void updateCatalogService(Inventory inventory) {
        catalogService = new CatalogFileService(inventory);
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
        BuilderFX.buildLabelNormal(TITLE_CATALOG, pane, 0, 2);
        catalogComboBox = BuilderFX.buildComboBox(SELECT_LABEL, pane, 1, 2);
        editCatalogButton = BuilderFX.buildIconButton("", EDIT_ICON, pane, 2, 2);
        deleteCatalogButton = BuilderFX.buildIconButton("", DELETE_ICON, pane, 3, 2);
        // Row 3
        separator = BuilderFX.buildSeparator(Orientation.HORIZONTAL, pane, 0, 3, 4, 1);
        // Row 4
        // Schema
        schemaList = buildListView();
        BuilderFX.buildLabelNormal(TITLE_CATALOG_SCHEMA, pane, 0, 4);
        schemaTitledPane = BuilderFX.buildTitledPane(DETAILS_LABEL, schemaList, pane, 1, 4, 1, 1);
        // Items
        BuilderFX.buildLabelNormal(TITLE_ITEMS, pane, 2, 4);
        showItemsButton = buildItemButton(VIEW_LABEL);
        addItemButton = buildItemButton(ADD_LABEL);
        deleteAllItemsButton = buildItemButton(DELETE_LABEL);
        itemsOptionsPane = buildHBoxItemsOptions(addItemButton, showItemsButton, deleteAllItemsButton);
        itemOptionsTitledPane = BuilderFX.buildTitledPane(OPTIONS_LABEL, itemsOptionsPane, pane, 3, 4, 1, 1);
        // None Row
        buttonTypeYes = new ButtonType(YES_LABEL);
        buttonTypeNo = new ButtonType(NO_LABEL);
        deleteAlert = BuilderFX.buildConfirmDialog(DELETE_LABEL, DELETE_ICON, CONFIG_ICON, buttonTypeYes, buttonTypeNo);
        textInputDialog = BuilderFX.buildInputDialog(EDIT_LABEL, EDIT_ICON, CONFIG_ICON);
    }

    /**
     * Configure the style to the panes and the components.
     */
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
        rowConstraints1.setVgrow(Priority.ALWAYS);
        // Row #2
        RowConstraints rowConstraints2 = new RowConstraints(40, 60, 75);
        rowConstraints2.setValignment(VPos.CENTER);
        rowConstraints2.setVgrow(Priority.ALWAYS);
        // Row #3
        RowConstraints rowConstraints3 = new RowConstraints(20, 20, 20);
        rowConstraints3.setValignment(VPos.CENTER);
        rowConstraints3.setVgrow(Priority.ALWAYS);
        // Row #4
        RowConstraints rowConstraints4 = new RowConstraints(150, 175, 200);
        rowConstraints4.setValignment(VPos.TOP);
        rowConstraints4.setVgrow(Priority.ALWAYS);
        // Add Row Constraints
        pane.getRowConstraints().addAll(rowConstraints, rowConstraints1, rowConstraints2, rowConstraints3, rowConstraints4);

        // Columns Constraints
        ColumnConstraints columnConstraints = new ColumnConstraints(75, 100, 200);
        columnConstraints.setHalignment(HPos.RIGHT);
        columnConstraints.setHgrow(Priority.ALWAYS);

        ColumnConstraints columnConstraints1 = new ColumnConstraints(150, 175, 200);
        columnConstraints1.setHalignment(HPos.LEFT);
        columnConstraints1.setHgrow(Priority.ALWAYS);

        ColumnConstraints columnConstraints2 = new ColumnConstraints(100, 200, 200);
        columnConstraints2.setHalignment(HPos.RIGHT);
        columnConstraints2.setHgrow(Priority.ALWAYS);

        ColumnConstraints columnConstraints3 = new ColumnConstraints(100, 200, 200);
        columnConstraints3.setHalignment(HPos.LEFT);
        columnConstraints3.setHgrow(Priority.ALWAYS);
        pane.getColumnConstraints().addAll(columnConstraints, columnConstraints1, columnConstraints2, columnConstraints3);

        // Settings for Table Columns
        // Inventory ComboBox
        inventoryComboBox.setMinWidth(100);
        inventoryComboBox.setVisibleRowCount(10);
        // Catalog ComboBox
        catalogComboBox.setMinWidth(100);
        catalogComboBox.setVisibleRowCount(10);
        catalogComboBox.setPlaceholder(new Label("The list is empty."));
        catalogComboBox.setPromptText("Select");
        // Schema
        schemaList.setMaxSize(250, 600);
        schemaTitledPane.setMaxSize(250, 600);
        // Items
        itemOptionsTitledPane.setMaxSize(250, 600);
        // Buttons
        setButtonEffect(addItemButton);
        setButtonEffect(deleteAllItemsButton);
        setButtonEffect(showItemsButton);

        editInventoryButton.setVisible(false);// TODO realizar funciones
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

    /**
     * Build
     *
     * @param text that will appear in the button.
     *
     * @return a new Button with a label in it.
     */
    private Button buildItemButton(String text) {
        return new Button(text);
    }
    //  Handlers  \\

    /**
     * Add the handlers and set the events.
     */
    private void addHandlers() {
        inventoryComboBox.setOnAction(event -> inventoryChangedEvent());
        catalogComboBox.setOnAction(e -> catalogChangedEvent());
        deleteInventoryButton.setOnAction(e -> deleteInventoryAction());
        editCatalogButton.setOnAction(e -> editCatalogAction());
        deleteCatalogButton.setOnAction(e -> deleteCatalogAction());
        showItemsButton.setOnAction(e -> showItemsAction());
        addItemButton.setOnAction(e -> addItemsAction());
        deleteAllItemsButton.setOnAction(e -> deleteAllItemsAction());


    }

    /**
     * This method edits a catalog using the values of the comboBox and one stage to ask for new values
     */
    private void editCatalogAction() {
        // Validate selection
        if (catalogComboBox.getValue() != null) {
            // Set dialog details
            textInputDialog.setHeaderText("Edit catalog");
            textInputDialog.setContentText("Change name: ");
            // Get catalog
            Catalog catalog = null;
            try {
                catalog = catalogComboBox.getValue();
                if (catalog != null) {
                    // Show alert
                    Optional<String> result = textInputDialog.showAndWait();
                    // Wait the result and select
                    if (result.isPresent() && !result.get().isEmpty()) {
                        catalog.setName(result.get());
                        if (catalogService.edit(catalog)) {
                            System.out.println(catalog + " edited...");
                            // Remove -> invalid
                            fillCatalogComboBox(inventoryComboBox.getValue());
                        } else
                            System.out.println(catalog + " no edited...");
                    }
                }


            } catch (ServiceException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * This method ask for a confirmation to delete the catalog selected.
     */
    private void deleteCatalogAction() {
        // Validate selection
        if (notNullCatalog()) {
            // Set dialog details
            deleteAlert.setHeaderText("Delete catalog");
            deleteAlert.setContentText("Are you sure you want to delete " + catalogComboBox.getValue() + " from " + inventoryComboBox.getValue() + " catalogs list?");
            // Show alert
            Optional<ButtonType> result = deleteAlert.showAndWait();
            // Wait the result and select
            if (result.isPresent())
                // Case #1 Yes
                try {
                    if (result.get() == buttonTypeYes) {
                        Catalog catalog = catalogComboBox.getValue();
                        // Validate remove
                        if (catalog != null) {
                            if (catalogService.remove(catalog)) {
                                // Remove -> valid
                                System.out.println("deleted");
                                // Refresh catalogs list
                                inventoryChangedEvent();

                            } else // Remove -> invalid
                                System.out.println("Error: No deleted");
                        }
                    } else if (result.get() == buttonTypeNo) {
                        // Case #2 No or cancel as answer.
                        System.out.println("No");
                    }
                } catch (ServiceException e) {
                    System.out.println(e.getMessage());
                }
        }
    }

    /**
     *
     */
    private void deleteInventoryAction() {
        DeleteInventory.setInventory(inventoryComboBox.getValue());
        ManagePane.setCenterPane(ManagePane.getPanes().get(PaneName.DELETE_INVENTORY));
    }

    private void showItemsAction() {
        ManageItem.refresh();
        ManagePane.setCenterPane(ManagePane.getPanes().get(PaneName.MANAGE_ITEM));
        ManageItem.setInventorySelected(inventoryComboBox.getValue());
        ManageItem.setCatalogSelected(catalogComboBox.getValue());
        ManageItem.setPreviousPane(pane);
    }

    private static void addItemsAction() {
        // Validations
        Inventory inventory = inventoryComboBox.getValue();
        Catalog catalog = catalogComboBox.getValue();

        if (inventory != null && catalog != null) {
            CreateItemForm.refresh();
            CreateItemForm.setInventory(inventory);
            CreateItemForm.setCatalog(catalog);
            CreateItemForm.setPreviousPane(pane);
            ManagePane.setCenterPane(ManagePane.getPanes().get(PaneName.CREATE_ITEM));
        }
        else
            System.out.println("Add ItemsAction is something null");
    }

    /**
     * This method deletes the items of one catalog, the catalog must be selected.
     */
    private static void deleteAllItemsAction() {
        System.out.println("deleteAll pressed.");
        // Validate selection
        if (notNullCatalog()) {
            // Set dialog details
            deleteAlert.setHeaderText("Delete Items");
            deleteAlert.setContentText("Are you sure you want to delete all the items of " + catalogComboBox.getValue() + " from " + inventoryComboBox.getValue() + "?");
            // Show alert
            Optional<ButtonType> result = deleteAlert.showAndWait();
            // Wait the result and select
            if (result.isPresent())
                // Case #1 Yes
                try {
                    if (result.get() == buttonTypeYes) {
                        Catalog catalog = catalogComboBox.getValue();
                        // Validate edit
                        if (catalog != null) {
                            System.out.println("Before: " + catalog);
                            catalog.getItems().clear();
                            if (catalogService.edit(catalog)) {
                                // Remove -> valid
                                System.out.println("edited, items deleted");
                                System.out.println("After: " + catalog);

                                // Refresh catalogs list
                                fillCatalogComboBox(inventoryComboBox.getValue());
                            } else // Remove -> invalid
                                System.out.println("Error: No edited");
                        }
                    } else if (result.get() == buttonTypeNo) {
                        // Case #2 No or cancel as answer.
                        System.out.println("No");
                    }
                } catch (ServiceException e) {
                    System.out.println(e.getMessage());
                }

        }
    }

    /**
     * @return
     */
    private static boolean notNullCatalog() {
        return catalogComboBox.getValue() != null;
    }    // Events

    /**
     * This event is triggered when the inventory box changes it's value.
     */
    private static void inventoryChangedEvent() {
        // Inventory Config
        enableInventoryOptions(true);
        // Catalog Config
        fillCatalogComboBox(inventoryComboBox.getValue());
        catalogComboBox.setDisable(false);
        enableCatalogOptions(false);
        enableTitles(false);

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
        try {
            // Get the list of inventories
            List<Inventory> inventories = inventoryService.getAll();
            // Validate list
            if (inventories != null) {
                if (inventories.isEmpty()) {
                    // Case #1 List is empty
                    inventoryComboBox.setPlaceholder(new Label("There are no inventories."));
                } else {
                    //Case #2 The list has inventories.
                    BuilderFX.fillComboBox(inventoryComboBox, inventories);
                }
            }
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * This methods fills the catalog combo Box with all catalog's names in the inventory selected.
     *
     * @param inventory to extract the list of catalogs.
     */
    private static void fillCatalogComboBox(Inventory inventory) {
        // Validate Inventory
        // Case #1 invalid inventory.
        if (inventory == null) {
            catalogComboBox.setDisable(true);
            catalogComboBox.getItems().clear();
        } else {
            // Case #2 Valid inventory
            // Get update catalog list
            updateCatalogService(inventory);
            List<Catalog> catalogList = null;
            try {
                catalogList = catalogService.getAll();

                // Validate list
                if (catalogList != null && !catalogList.isEmpty()) {
                    // Case #1 The list has catalogs
                    BuilderFX.fillComboBox(catalogComboBox, catalogList);
                    catalogComboBox.setTooltip(new Tooltip("Select one catalog."));
                } else {
                    catalogComboBox.getItems().clear();
                }
                // Enable comboBox
                catalogComboBox.setDisable(false);
            } catch (ServiceException exception) {
                System.out.println(exception.getMessage());
            }
        }
    }

    /**
     * Updates the list of the list view if one catalog is selected.
     */
    private static void fillSchemaList(Catalog catalog) {
        // Validate catalog
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

    /**
     * This method refresh and cleans this UI.
     */
    public static void refresh() {
        // ComboBoxes to default
        refreshInventoryBox();
        refreshCatalogBox();
        // disable Buttons
        enableInventoryOptions(false);
        enableCatalogOptions(false);
        // collapse and disable titledPanes
        enableTitles(false);
    }

    /**
     * Sets the inventory combo Box default
     */
    private static void refreshInventoryBox() {
        // #1 Selection none
        inventoryComboBox.getSelectionModel().clearSelection();
        // #2 Fill with options
        fillInventoryComboBox();
        // #3 Clear InventorySelected
        inventorySelected = null;
    }

    /**
     * Sets the inventory combo Box default
     */
    private static void refreshCatalogBox() {
        // #1 Selection none
        catalogComboBox.getSelectionModel().clearSelection();
        // #2 Clear items.
        catalogComboBox.getItems().clear();
        // #3 Block items
        catalogComboBox.setDisable(true);
    }

    /**
     * Select the inventory
     *
     * @param inventory to select.
     */
    public static void setInventory(Inventory inventory) {
        if (inventory != null) {
            inventorySelected = inventory;
            // Refresh
            refresh();
            // Set the inventory
            inventoryComboBox.setValue(inventory);
            // Enable buttons
            enableInventoryOptions(true);
            // Search catalogs
            fillCatalogComboBox(inventory);
        }
    }

    /**
     * Select the catalog, the inventory most be selected first.
     *
     * @param catalog catalog to select.
     */
    public static void setCatalog(Catalog catalog) {
        // Refresh catalog.
        refreshCatalogBox();
        System.out.println(catalog);
        // Fill catalog list
        fillCatalogComboBox(inventoryComboBox.getValue());
        // Validate catalog
        if (catalogComboBox.getItems().contains(catalog)) {
            // Set the catalog
            catalogComboBox.setValue(catalog);
            catalogChangedEvent();
            fillSchemaList(catalog);
        }
    }
}
