package edu.ucr.rp.programacion2.proyecto.gui.manage;

import edu.ucr.rp.programacion2.proyecto.gui.manage.model.SceneViewer;
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
        Scene scene = new Scene(managePane.getPane(), 900, 700);
        // more code...
        scene.getStylesheets().add("Style.css");
        return scene;
    }
}
