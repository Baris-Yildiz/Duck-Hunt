import javafx.animation.Animation;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.text.*;

/**
 * The TitleScreen class is responsible for constructing the title scene. It contains scene elements as fields
 * and methods for initializing them.
 */
public class TitleScreen extends Screen {
    private final AudioClip music = Effects.getTitleMusic();
    private Text text;

    /**
     * The key event handler for the title screen is responsible for handling the
     * ENTER and ESCAPE key presses that allow to user to advance or exit.
     */
    private final EventHandler<KeyEvent> screenController = event -> {
        switch (event.getCode()) {
            case ENTER:
                DuckHunt.getGameStage().setScene(new BackgroundSelectionScreen().getScene());
                break;
            case ESCAPE:
                Platform.exit();
                break;
        }
    };

    /**
     * The constructor for TitleScreen objects initializes the title background, the title text and
     * the title music.
     */
    public TitleScreen() {
        setBackground(Sprites.getTitleScreen());
        setBackgroundView();
        initializeText();
        initializeMusic();

        getPane().getChildren().addAll(getBackgroundView(), text);

        getScene().setOnKeyPressed(screenController);
    }

    /**
     * Initializes the title music by playing it in loop.
     */
    public void initializeMusic() {
        if (!music.isPlaying()) {
            music.setCycleCount(Animation.INDEFINITE);
            music.play();
        }
    }

    /**
     * Initializes the flashing text that prompts the user to either start or exit the game.
     */
    public void initializeText() {

        text = TextManager.generateText(WIDTH/8,HEIGHT/1.5, 15.5,
                "PRESS ENTER TO START\nPRESS ESC TO EXIT");

        TextManager.assignFlashAnimationToText(text);
    }

}