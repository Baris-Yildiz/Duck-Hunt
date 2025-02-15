import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.image.ImageView;

/**
 * The abstract class Scene is the upper class for all scene classes in the game.
 * It provides a common empty scene with constant width and height values and a common pane.
 * Also, because every scene has a background, it provides set and get methods for the background and
 * background image view of the scene.
 */
public abstract class Screen {
    public static double WIDTH = 256 * DuckHunt.SCALE;
    public static double HEIGHT = 240 * DuckHunt.SCALE;
    private final Pane pane = new Pane();
    private final Scene scene = new Scene(pane, WIDTH, HEIGHT);
    private Image background;
    private ImageView backgroundView;

    public Scene getScene() {
        return scene;
    }

    public Pane getPane() {
        return pane;
    }

    public void setBackground(Image background) {
        this.background = background;
    }

    public ImageView getBackgroundView() {
        return backgroundView;
    }

    public void setBackgroundView() {
        backgroundView = new ImageView(background);
    }

}
