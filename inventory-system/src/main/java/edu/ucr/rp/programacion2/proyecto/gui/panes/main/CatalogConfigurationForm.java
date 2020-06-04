package edu.ucr.rp.programacion2.proyecto.gui.panes.main;

import edu.ucr.rp.programacion2.proyecto.gui.javafx.util.Style;
import edu.ucr.rp.programacion2.proyecto.gui.panes.main.records.BuilderFX;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import static edu.ucr.rp.programacion2.proyecto.gui.javafx.LabelConstants.*;
import static edu.ucr.rp.programacion2.proyecto.gui.javafx.util.PaletteDesign.LIGHT_PRIMARY_COLOR;
import static edu.ucr.rp.programacion2.proyecto.gui.javafx.util.UIConstants.*;

public class CatalogConfigurationForm {
    //  Variables  \\
    private ComboBox inventoryComboBox;
    private TextField catalogNameTextField;
    private Button editCatalogButton;
    private Button deleteCatalogButton;
    private Button showItemsButton;
    private Pagination schemaPagination;
    private GridPane pane;
    public GridPane getPane(){
        pane = buildPane();
        setupControls(pane);
        addHandlers();
        return pane;
    }
    //  Methods  \\
    /**
     * Configure and add the required components in the pane.
     *
     * @param pane for add components.
     */
    private void setupControls(GridPane pane) {
        inventoryComboBox = BuilderFX.buildComboBox(TITLE_INVENTORY, pane, 1, 1);
        catalogNameTextField = BuilderFX.buildTextInput(TITLE_CATALOG, pane, 1, 2);
        editCatalogButton = BuilderFX.buildIconButton("", EDIT_ICON, pane, 2, 2);
        deleteCatalogButton =BuilderFX.buildIconButton("", DELETE_ICON, pane, 3, 2);
        BuilderFX.buildLabelNormal(ITEMS_COLUMN, pane, 0, 3);
        showItemsButton = BuilderFX.buildIconButton(ITEMS_COLUMN, ITEMS_ICON, pane, 1,3);
        BuilderFX.buildLabelNormal(TITLE_CATALOG_SCHEMA, pane, 0, 4);
        schemaPagination = BuilderFX.buildPagination(pane, 1, 4, 1, 1);
    }

    /**
     * Build the main pane.
     *
     * @return {@code GridPane} Pane formated.
     */
    public static GridPane buildPane() {
        GridPane pane = new GridPane();
        pane.setMinSize(GRID_PANE_MIN_WIDTH, GRID_PANE_MIN_HEIGHT);
        //TODO column sizes.
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
    private void addHandlers() {
        
    }
}
