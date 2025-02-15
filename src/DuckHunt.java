import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * The DuckHunt class is the driver class that contains global configuration variables
 * SCALE and VOLUME, the field gameStage that represents the primary stage of the game and the start method
 * that starts the game.
 *
 */
public class DuckHunt extends Application {

    public static double VOLUME = 0.025;    //field that represents the volume of all game sounds.
    public static double SCALE = 3;     //field that represents the scale of all objects in the game including the screen.
    private static Stage gameStage;     //the stage that the game runs on.

    /**
     * The start method is responsible for the application to run. It sets the name and
     * the icon for the application window and starts the game by setting the first scene
     * as the title screen.
     * @param primaryStage the primary stage for this application.
     *
     */
    @Override
    public void start(Stage primaryStage) {

        gameStage = primaryStage;

        Image icon = Sprites.getIcon();


        primaryStage.setScene(new TitleScreen().getScene());
        primaryStage.getIcons().add(icon);
        primaryStage.setTitle("HUBBM Duck Hunt");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static Stage getGameStage() {
        return gameStage;
    }
}
