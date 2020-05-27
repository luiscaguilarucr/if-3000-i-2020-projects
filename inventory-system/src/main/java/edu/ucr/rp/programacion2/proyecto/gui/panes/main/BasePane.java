package edu.ucr.rp.programacion2.proyecto.gui.panes.main;

import edu.ucr.rp.programacion2.proyecto.gui.model.PaneName;
import edu.ucr.rp.programacion2.proyecto.gui.model.PaneViewer;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

import static edu.ucr.rp.programacion2.proyecto.gui.model.PaneName.MENU_BAR;

public class BasePane implements PaneViewer {

    // Contains all the Panes.
    private static Map<PaneName, Pane> panes = new HashMap<>();
    private Stage stage;

    public BasePane(Stage stage){
        this.stage = stage;
        initializePanes(stage);
    }
    /**
     * Create and store all the scenes.
     * Set up the main pane.
     */
    private void initializePanes(Stage stage){
        //panes.put(PaneName.MENU_BAR, new ViewMenuBar.getPane());
        panes.put(PaneName.MENU_BAR, new HBox(new Button()));//Test
        panes.put(PaneName.BASE, buildBasePane(stage));
        //panes.put(PaneName., new      .getPane());
        //panes.put(PaneName., new .getPane());
    }

    @Override
    public Pane getPane() {
        return panes.get(PaneName.BASE);
    }

    /**
     * Build the base pane.
     * @return the base pane.
     */
    private Pane buildBasePane(Stage stage){
        VBox vBox = new VBox();
        vBox.getChildren().addAll(panes.get(MENU_BAR));//TODO
        return vBox;
    }

}
