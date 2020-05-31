package edu.ucr.rp.programacion2.proyecto.gui.javafx;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import static edu.ucr.rp.programacion2.proyecto.gui.javafx.PaletteDesign.ERROR_COLOR_TEXTFIELD;
import static edu.ucr.rp.programacion2.proyecto.gui.javafx.PaletteDesign.PRIMARY_COLOR;
import static edu.ucr.rp.programacion2.proyecto.gui.javafx.UIConstants.*;


public class Style {

    /**
     * @param textfield
     * @param hexCode   #hexadecimal code of color
     */
    public static void setTextFieldConfig(TextField textfield, String hexCode, double width, double height) {
        textfield.setStyle("-fx-background-color: " + hexCode);
        textfield.setMaxSize(width, height);
        textfield.setMinSize(width, height);
    }
    public static void settextFieldColor(TextField textField, String hexCode) {
        textField.setStyle("-fx-background-color: " + hexCode);
    }
    public static void emptyTextFieldColor(TextField textField) {
            textField.setStyle("-fx-background-color: " + ERROR_COLOR_TEXTFIELD);
    }

    public static void setTextFieldEffect(TextField textField) {
        textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                textField.setStyle("-fx-background-color: "+ PRIMARY_COLOR);
            }
        });

    }
    public static void settextFieldColor(ComboBox comboBox, String hexCode) {
        comboBox.setStyle("-fx-background-color: " + hexCode);
    }
    /**
     * @param label labelrecibe
     * @param type  int - 1 Title, 2 Subtitle, 3 normal.
     * @author Jeison
     */
    public static void setLabelConfig(Label label, int type) {
        label.setTextAlignment(TextAlignment.CENTER);
        switch (type) {
            case 1://Title
                label.setFont(new Font(FONT, TITLE_FONT_SIZE));
                break;
            case 2://Subtitle
                label.setFont(new Font(FONT, SUBTITLE_FONT_SIZE));
                break;
            case 3://Normal
                label.setFont(new Font(FONT, NORMAL_FONT_SIZE));
                break;
        }//End switch
    }//End setLabelConfig()

    /**
     * Effect of the mouse
     *
     * @param button
     * @param primaryColor   #hexadecimal code of color
     * @param secondaryColor #hexadecimal code of color
     */
    public static void setButtonColors(Button button, String primaryColor, String secondaryColor) {
        /////////////////////////////////////////////////////////////////////// Hand Effect

        button.setCursor(Cursor.HAND);

        /////////////////////////////////////////////////////////////////////// Color Effect
        button.setStyle("-fx-background-color: " + primaryColor);

        //////////////////////////// Config
        button.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e)
                -> {
            button.setStyle("-fx-background-color: " + secondaryColor);
        });

        button.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e)
                -> {
            button.setStyle("-fx-background-color: " + primaryColor);
        });
    }

    public static final void setLabelColor(Label label, String hexCode){
        label.setTextFill(Color.valueOf(hexCode));
    }
    /**
     * @param text
     */
    public static final void setTextFont(Text text, int size) {
        text.setFont(new Font(FONT, size));
    }

    /**
     * @param text
     * @param hexCode #hexadecimal code of color
     */
    public static final void setTextColor(Text text, String hexCode) {
        text.setFill(Color.valueOf(hexCode));
    }

    /**
     * @param imageview
     */
    public static void setIconsSize(ImageView imageview, double Height, double Width) {
        imageview.setFitHeight(Height);
        imageview.setFitWidth(Width);
        imageview.setPreserveRatio(true);
    }

    /**
     * @param region
     * @param hexCode #hexadecimal code,
     */
    public static void setBackgroundColor(Region region, String hexCode) {
        region.setStyle("-fx-background-color: " + hexCode);
    }
}
