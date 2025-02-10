import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.util.Duration;

/**
 * The TextManager class is used to generate a text with the
 * appropriate font configurations used throughout the game. Also contains a method
 * to assign a "flashing" animation to a text.
 */
public class TextManager {
    private static final String fontFamily = "Arial";
    private static final FontWeight fontWeight = FontWeight.BOLD;
    private static final FontPosture fontPosture = FontPosture.REGULAR;
    private static final Color textColor = Color.ORANGE;
    private static final TextAlignment textAlignment = TextAlignment.CENTER;

    public static Text generateText(double x, double y, double fontSize, String message) {
        Text resultText = new Text(x, y, message);
        resultText.setFont(Font.font(fontFamily, fontWeight, fontPosture, fontSize * DuckHunt.SCALE));
        resultText.setFill(textColor);
        resultText.setTextAlignment(textAlignment);

        return resultText;
    }

    public static void assignFlashAnimationToText(Text text) {
        Timeline animation = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> text.setVisible(!text.isVisible())));
        animation.setCycleCount(Animation.INDEFINITE);
        animation.play();
    }


}
