package edu.ucr.rp.programacion2.proyecto.main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        VBox root = new VBox();
        primaryStage.setTitle("Test");

        Scene scene = new Scene(root, 300, 300);


        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
