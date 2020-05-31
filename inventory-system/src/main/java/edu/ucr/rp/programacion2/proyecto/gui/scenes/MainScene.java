package edu.ucr.rp.programacion2.proyecto.gui.scenes;

import edu.ucr.rp.programacion2.proyecto.gui.model.SceneViewer;
import edu.ucr.rp.programacion2.proyecto.gui.panes.main.*;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainScene implements SceneViewer {

    private Stage stage;
    private ManagePane managePane;
    /**
     * Receive the main stage reference.
     *
     * @param stage App stage.
     */
    public MainScene(Stage stage) {
        this.stage = stage;
        managePane = new ManagePane(stage);
    }

    @Override
    public Scene getScene() {
        return buildScene();
    }

    private Scene buildScene() {
        Scene scene = new Scene(managePane.getPane(), 600, 600);
        // more code...
        return scene;
    }
}
