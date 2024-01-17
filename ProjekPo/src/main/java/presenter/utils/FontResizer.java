package presenter.utils;

import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class FontResizer {
    public static double calculateOptimalFontSize(String text, Font font, double availableWidth){
        Text helperText = new Text();
        helperText.setFont(font);
        helperText.setText(text);

        double textWidth = helperText.getLayoutBounds().getWidth();
        double fontSize = font.getSize();
        if (textWidth > availableWidth) {
            fontSize *= availableWidth / textWidth;
        }

        return fontSize;
    }
}
