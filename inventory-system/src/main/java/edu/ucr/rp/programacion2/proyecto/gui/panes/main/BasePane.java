package edu.ucr.rp.programacion2.proyecto.gui.panes.main;

import edu.ucr.rp.programacion2.proyecto.gui.model.PaneName;
import edu.ucr.rp.programacion2.proyecto.gui.model.PaneViewer;
import edu.ucr.rp.programacion2.proyecto.gui.model.SceneName;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class BasePane implements PaneViewer {

    // Contains all the Panes.
    private static Map<PaneName, Pane> panes = new HashMap<>();
    private Stage stage;
    private Pane basePane;

    public BasePane(Stage stage){
        this.stage = stage;
        this.basePane = buildBasePane();
        initializePanes(stage, basePane);
        setupBasePane(basePane);
    }
    /**
     * Create and store all the scenes.
     * Set up the main pane.
     */
    private void initializePanes(Stage stage, Pane basePane){
        panes.put(PaneName.BASE, basePane);
        //panes.put(PaneName.MENU_BAR, new ViewMenuBar.getPane());
        panes.put(PaneName.MENU_BAR, new HBox(new Button()));//Test
        //panes.put(PaneName., new .getPane());
        //panes.put(PaneName., new .getPane());

    }

    @Override
    public Pane getPane() {
        return panes.get(PaneName.BASE);
    }

    /**
     * Just inizialize the pane without children.
     * Build the base pane.
     * @return the base pane.
     */
    private Pane buildBasePane(){
        VBox vBox = new VBox();
        return vBox;
    }

    /**
     * Configure all the panes for the basePane.
     * @param pane
     */
    private void setupBasePane(Pane pane){

    }
    /** Returns a Map of the scenes by {@link SceneName}
     * @return*/
    public static Map<PaneName, Pane> getPanes() {
        return panes;
    }
}
