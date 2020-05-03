package edu.ucr.rp.programacion2.proyecto.main;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        VBox  root = new VBox();
        stage.setTitle("Test");
        Button button = new Button("TEST");
        root.getChildren().addAll(button);

        Scene scene = new Scene(root, 300, 300);


        stage.setScene(scene);
        stage.show();
    }
}
