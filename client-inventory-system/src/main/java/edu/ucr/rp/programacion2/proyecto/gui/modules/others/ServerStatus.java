package edu.ucr.rp.programacion2.proyecto.gui.modules.others;

import edu.ucr.rp.programacion2.proyecto.gui.manage.model.PaneViewer;
import edu.ucr.rp.programacion2.proyecto.util.builders.BuilderFX;
import javafx.geometry.HPos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import static edu.ucr.rp.programacion2.proyecto.gui.modules.util.LabelConstants.SERVER_STATUS_CONNECTED_ICON;
import static edu.ucr.rp.programacion2.proyecto.gui.modules.util.LabelConstants.SERVER_STATUS_FAILED_ICON;


public class ServerStatus implements PaneViewer {
    // Variables
    private static ImageView serverConnectionImage;
    private static long REFRESH_RATE = 1000; // millis
    private HBox pane;
    //


    public ServerStatus() {
        pane = buildPane();
        setupControls(pane);
        setStyle();
    }

    private void setStyle() {
        pane.getStyleClass().add("server-status-pane");
        serverConnectionImage.setPreserveRatio(true);
        serverConnectionImage.setFitHeight(16);
        serverConnectionImage.setFitWidth(16);
        serverConnectionImage.getStyleClass().add("server-status-image");

    }

    private HBox buildPane() {
        return new HBox();
    }

    private void setupControls(HBox pane) {
        // Row #0
        serverConnectionImage = buildImageView(SERVER_STATUS_FAILED_ICON, pane);

    }


    private ImageView buildImageView(String image, HBox pane){
        ImageView imageView = new ImageView(image);
        pane.getChildren().add(imageView);
        return imageView;
    }
    @Override
    public Pane getPane() {
        return pane;
    }
}
