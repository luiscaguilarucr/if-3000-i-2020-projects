package edu.ucr.rp.programacion2.proyecto.gui.modules.others;

import edu.ucr.rp.programacion2.proyecto.gui.manage.model.PaneViewer;
import edu.ucr.rp.programacion2.proyecto.gui.manage.ManagePane;
import edu.ucr.rp.programacion2.proyecto.util.builders.BuilderFX;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import static edu.ucr.rp.programacion2.proyecto.gui.modules.util.LabelConstants.*;

/**
 * This UI Shows the some information about the project and developers.
 * @author Jeison Araya Mena | B90514
 */
public class CreditsUI implements PaneViewer {
    //  Variables  \\
    private static CreditsUI instance;
    private Text text;
    private Button button;
    private GridPane pane;

    //  Singleton  \\
    public static CreditsUI getInstance() {
        if (instance == null)
            instance = new CreditsUI();
        return instance;
    }

    //  Constructor  \\
    private CreditsUI() {
        // UI
        pane = buildPane();
        setupControls(pane);
        addHandlers();
        setupStyles();
    }

    //  Methods  \\
    private GridPane buildPane() {
        GridPane gridPane = new GridPane();
        // More code..
        return gridPane;

    }

    private void setupControls(GridPane pane) {
        // Row #0
        BuilderFX.buildLabelTitle(TITLE_CREDITS, pane, 0, 0, 1, 1);
        // Row #1
        text = BuilderFX.buildText(TEXT_CREDITS, pane, 0, 1, 1, 1);
        // Row #2
        button = BuilderFX.buildButton(BACK_LABEL, pane, 0, 2);
    }

    private void addHandlers() {
        button.setOnAction(e -> backAction());
    }

    private void setupStyles() {
        pane.getStyleClass().add("default-grid-pane");
        text.setTextAlignment(TextAlignment.JUSTIFY);
    }//TODO style

    private void backAction() {
        ManagePane.clearPane();
    }

    @Override
    public Pane getPane() {
        return pane;
    }
}
