package edu.ucr.rp.programacion2.proyecto.gui.panes.main;

import edu.ucr.rp.programacion2.proyecto.gui.javafx.ItemForm;
import edu.ucr.rp.programacion2.proyecto.gui.model.PaneName;
import edu.ucr.rp.programacion2.proyecto.gui.model.PaneViewer;
import edu.ucr.rp.programacion2.proyecto.gui.model.SceneName;
import edu.ucr.rp.programacion2.proyecto.gui.javafx.CatalogForm;
import edu.ucr.rp.programacion2.proyecto.gui.javafx.ViewMenuBar;
import edu.ucr.rp.programacion2.proyecto.gui.javafx.InventoryForm;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class ManagePane implements PaneViewer {

    // Contains all the Panes.
    private static Map<PaneName, Pane> panes = new HashMap<>();
    private Stage stage;
    private static Pane basePane;

    public ManagePane(Stage stage) {
        this.stage = stage;
        this.basePane = buildBasePane();
        initializePanes(stage, basePane);
        setupBasePane(basePane);
    }

    /**
     * Create and store all the scenes.
     * Set up the main pane.
     */
    private void initializePanes(Stage stage, Pane basePane) {
        panes.put(PaneName.BASE, basePane);
        panes.put(PaneName.MENU_BAR, new ViewMenuBar(stage).getPane());
        panes.put(PaneName.ADD_INVENTORY, new InventoryForm().getPane());
        panes.put(PaneName.ADD_CATALOG, new CatalogForm().getPane());
        panes.put(PaneName.ADD_ITEM, new ItemForm().getPane());
        //panes.put(PaneName., new .getPane());
    }

    @Override
    public Pane getPane() {
        return panes.get(PaneName.BASE);
    }

    /**
     * Just inizialize the pane without children.
     * Build the base pane.
     *
     * @return the base pane.
     */
    private Pane buildBasePane() {
        GridPane gridPane = new GridPane();
        return gridPane;
    }

    /**
     * Configure all the panes for the basePane.
     *
     * @param pane
     */
    private void setupBasePane(Pane pane) {
        ((GridPane) pane).add(panes.get(PaneName.MENU_BAR), 0, 0);

    }

    /**
     * Change the current pane
     *
     * @param pane
     */
    public static void setCenterPane(Pane pane) {
        ((GridPane) basePane).add(pane, 0, 1);
    }

    /**
     * Returns a Map of the scenes by {@link SceneName}
     *
     * @return
     */
    public static Map<PaneName, Pane> getPanes() {
        return panes;
    }
}
