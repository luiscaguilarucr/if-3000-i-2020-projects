package edu.ucr.rp.programacion2.proyecto.gui.modules.others;

import edu.ucr.rp.programacion2.proyecto.gui.manage.model.PaneViewer;
import edu.ucr.rp.programacion2.proyecto.util.ServerStatus;
import edu.ucr.rp.programacion2.proyecto.util.ThreadPool;
import edu.ucr.rp.programacion2.proyecto.util.builders.BuilderFX;
import javafx.geometry.HPos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.io.IOException;

import static edu.ucr.rp.programacion2.proyecto.gui.modules.util.LabelConstants.SERVER_STATUS_CONNECTED_ICON;
import static edu.ucr.rp.programacion2.proyecto.gui.modules.util.LabelConstants.SERVER_STATUS_FAILED_ICON;


public class ServerStatusUI implements PaneViewer {
    // Variables
    private static ImageView serverConnectionImage;
    private static final Image connectedImage = new Image(SERVER_STATUS_CONNECTED_ICON);
    private static final Image failedImage = new Image(SERVER_STATUS_FAILED_ICON);
    private static long REFRESH_RATE = 1000; // millis
    private HBox pane;
    private static boolean autoRefresh;
    //


    public ServerStatusUI() {
        pane = buildPane();
        setupControls(pane);
        setStyle();
        autoRefresh = true;
        autoRefresh();
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
        serverConnectionImage = buildImageView(pane);

    }


    private ImageView buildImageView(HBox pane) {
        ImageView imageView = new ImageView();
        pane.getChildren().add(imageView);
        return imageView;
    }

    /**
     * Stop the solicitation of data from Data base.
     */
    public static void stopRefresh() {
        autoRefresh = false;
    }

    /**
     * Refresh the table
     */
    private void autoRefresh() {
        ThreadPool.getPool().submit(() -> {
            while (autoRefresh) {
                try {
                    Thread.sleep(REFRESH_RATE);
                    if (ServerStatus.isConnected()) {
                        serverConnectionImage.setImage(connectedImage);
                        ViewMenuBar.disableMenus(false);


                    } else {
                        serverConnectionImage.setImage(failedImage);
                        System.err.println("Server status: Connection lost");
                        ViewMenuBar.disableMenus(true);

                    }


                } catch (Exception ignored) {

                }
            }
        });
    }

    @Override
    public Pane getPane() {
        return pane;
    }
}
