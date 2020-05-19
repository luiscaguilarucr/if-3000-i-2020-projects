package edu.ucr.rp.programacion2.proyecto.main;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application {
    public static Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        //setLoginScene(stage);
        stage.show();
    }
    //    public void setLoginScene(Stage stage)throws Exception{
//        stage.setScene(new LoginForm().toStage(stage));
//    }

    public void display(String...args){
        launch(args);
    }
}
