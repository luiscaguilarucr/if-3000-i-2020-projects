package edu.ucr.rp.programacion2.proyecto.gui.panes.main.records;

import edu.ucr.rp.programacion2.proyecto.gui.model.PaneViewer;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import static edu.ucr.rp.programacion2.proyecto.gui.javafx.LabelConstants.*;

/**
 * This is a UI that shows the information of one catalog, also the user can edit and delete.
 *
 * Can be redirected to other panes.
 *
 */
public class CatalogConfig implements PaneViewer {
    // Variables  \\
    private ComboBox<String> inventoryNameComboBox;
    private ComboBox<String> catalogNameComboBox;
    private Button editInventoryButton;
    private Button deleteInventoryButton;
    private Button editCatalogButton;
    private Button deleteCatalogButton;
    private Separator separator;
    private TitledPane schemaTitledPane;
    private TitledPane itemOptionsTitledPane;
    private ListView schemaList;
    private VBox itemsOptionsPane;
    private Button showItemsButton;
    private Button addItemButton;
    private Button deleteAllItemsButton;
    private GridPane pane;
    // Getter  \\
    @Override
    public Pane getPane() {
        return getUI();
    }
    // Methods  \\
    private GridPane getUI(){
        pane = buildPane();
        setupControls(pane);
        addHandlers();
        return pane;
    }



    // Setting Services  \\


    //  Builders  \\
    /**
     * Builds the main pane.
     * @return {@code GridPane} Grid pane configured.
     */
    private GridPane buildPane(){
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
        inventoryNameComboBox = BuilderFX.buildComboBox(COMBO_BOX_SELECT_LABEL, pane, 1, 1);
        editInventoryButton = BuilderFX.buildIconButton("", EDIT_ICON, pane, 2, 1);
        deleteInventoryButton = BuilderFX.buildIconButton("", DELETE_ICON, pane, 3, 1);
        // Row 2
        BuilderFX.buildLabelNormal(CATALOG_NAME_COLUMN, pane, 0, 2);
        catalogNameComboBox = BuilderFX.buildComboBox(COMBO_BOX_SELECT_LABEL, pane, 1, 2);
        editCatalogButton = BuilderFX.buildIconButton("", EDIT_ICON, pane, 2, 2);
        deleteCatalogButton = BuilderFX.buildIconButton("", DELETE_ICON, pane, 3, 2);
        // Row 3
        separator = BuilderFX.buildSeparator(Orientation.HORIZONTAL, pane, 0, 3,4,1);
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
        itemOptionsTitledPane =BuilderFX.buildTitledPane(VIEW_LABEL, itemsOptionsPane, pane, 3, 4, 1, 1);// TODO check text
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
     * @param buttons to add.
     * @return {@code VBox} vbox configured.
     */
    private VBox buildHBoxItemsOptions(Button...buttons){
       VBox vBox = new VBox(buttons);
       // More code...
       return vBox;
    }


    private Button buildItemButton(String text){
        Button button = new Button(text);
        return button;
    }

    private void addHandlers() {
    }




}
