package edu.ucr.rp.programacion2.proyecto.gui;

import edu.ucr.rp.programacion2.proyecto.gui.manage.MainScene;
import edu.ucr.rp.programacion2.proyecto.gui.manage.model.SceneName;
import edu.ucr.rp.programacion2.proyecto.gui.modules.others.ServerStatusUI;
import edu.ucr.rp.programacion2.proyecto.util.ThreadPool;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

/**
 * Application with the Main Stage.
 */
public class App extends Application {
    // Contains all the Scenes.
    private static Map<SceneName, Scene> scenes = new HashMap<>();

    @Override
    public void start(Stage stage) {
        // Create and store all the scenes.
        scenes.put(SceneName.MAIN, new MainScene(stage).getScene()); //pasar stage como parámetro
        //scenes.put(SceneName.LOGIN, new ) //pasar stage como parámetro
        // Start with the main scene
        stage.setScene(scenes.get(SceneName.MAIN));
        stage.setTitle("Inventory System");
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        ServerStatusUI.stopRefresh();
        ThreadPool.shutdown();
        super.stop();
    }

    /** Returns a Map of the scenes by {@link SceneName} */
    public static Map<SceneName, Scene> getScenes() {
        return scenes;
    }

    public void display(String... args) {
        launch(args);
    }
}
