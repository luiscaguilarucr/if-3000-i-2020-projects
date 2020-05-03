package edu.ucr.rp.programacion2.proyecto.main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
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
        root.getChildren().addAll(getTest());

        Scene scene = new Scene(root, 300, 300);
        
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private VBox getTest() {
        VBox mainVBox = new VBox();

//        Menu menu = new Menu("menu");
//        //for (int i = 1; i <= 5; i++) {
//            MenuItem menuItem = new MenuItem("menuItem");
//            menu.getItems().addAll(menuItem);

        MenuBar menuBar = new MenuBar();

        menuBar.getMenus().addAll();

        mainVBox.getChildren().addAll(menuBar);
        return mainVBox;
    }

}
