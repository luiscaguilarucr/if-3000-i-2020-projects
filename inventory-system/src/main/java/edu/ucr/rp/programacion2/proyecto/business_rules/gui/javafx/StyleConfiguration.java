package edu.ucr.rp.programacion2.proyecto.business_rules.gui.javafx;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
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
import static edu.ucr.rp.programacion2.proyecto.business_rules.gui.javafx.PaletteDesign.*;

public class StyleConfiguration {
    public static final String FONT = "Merriweather";
    public static final Integer TITLE_FONT_SIZE = 22;
    public static final Integer SUBTITLE_FONT_SIZE = 18;
    public static final Integer NORMAL_FONT_SIZE = 12;
    /**
     * @param textfield
     * @param hexCode   #hexadecimal code of color
     */
    public void setTextFieldConfig(TextField textfield, String hexCode, double width, double height) {
        textfield.setStyle("-fx-background-color: " + hexCode);
        textfield.setMaxSize(width, height);
        textfield.setMinSize(width, height);
    }

    public boolean isTextFieldEmpty(TextField textField) {
        if (textField.getText().isEmpty()) {
            textField.setStyle("-fx-background-color: " + ERROR_COLOR_TEXTFIELD);
            return true;
        }
        return false;

    }

    public void setTextFieldEffect(TextField textField) {
        textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                textField.setStyle("-fx-background-color: "+ PRIMARY_COLOR);
            }
        });

    }

    /**
     * @param label labelrecibe
     * @param type  int - 1 Title, 2 Subtitle, 3 normal.
     * @author Jeison
     */
    public void setLabelConfig(Label label, int type) {
        switch (type) {
            case 1://Title

                label.setFont(new Font(FONT, TITLE_FONT_SIZE));
                label.setTextAlignment(TextAlignment.CENTER);
                break;

            case 2://Subtitle

                label.setFont(new Font(FONT, SUBTITLE_FONT_SIZE));
                label.setTextAlignment(TextAlignment.CENTER);
                break;

            case 3://Normal
                label.setFont(new Font(FONT, NORMAL_FONT_SIZE));
                label.setTextAlignment(TextAlignment.CENTER);
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
    public void setButtonColor(Button button, String primaryColor, String secondaryColor) {
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


    /**
     * @param text
     */
    public void setTextFont(Text text, int size) {
        text.setFont(new Font(FONT, size));
    }

    /**
     * @param text
     * @param hexCode #hexadecimal code of color
     */
    public void setTextColor(Text text, String hexCode) {
        text.setFill(Color.valueOf(hexCode));
    }

    /**
     * @param imageview
     */
    public void setIconsConfig(ImageView imageview, double Height, double Width) {
        imageview.setFitHeight(Height);
        imageview.setFitWidth(Width);
        imageview.setPreserveRatio(true);
    }

    /**
     * @param region
     * @param hexCode #hexadecimal code,
     */
    public void setBackgroundColor(Region region, String hexCode) {
        region.setStyle("-fx-background-color: " + hexCode);
    }
}
