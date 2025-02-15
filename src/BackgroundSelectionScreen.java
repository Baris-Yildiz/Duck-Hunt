import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.*;

/**
 * The BackgroundSelectionScreen class is responsible for constructing the background selection screen scene.
 */
public class BackgroundSelectionScreen extends Screen {

    private Text text;
    private final Image[] backgrounds = Sprites.getBackgrounds();   //all available backgrounds
    private final Image[] crosshairs = Sprites.getCrosshairs();     //all available crosshair sprites.

    private Image crosshair;
    private ImageView crosshairView;

    private int backgroundIndex = 0;
    private int crosshairIndex = 0;

    /**
     * The key event handler for the background selection screen. It is responsible for
     * changing the backgrounds when LEFT-RIGHT arrow keys are pressed, changing the crosshair
     * when UP-DOWN arrow keys are pressed, going back to the title screen when ESCAPE key is pressed and
     * advancing into the first level when ENTER is pressed.
     */
    private final EventHandler<KeyEvent> screenController = event -> {
        switch (event.getCode()) {
            case UP:
                if (crosshairIndex < crosshairs.length-1) {
                    crosshairIndex++;
                }
                crosshairView.setImage(crosshairs[crosshairIndex]);
                break;
            case DOWN:
                if (crosshairIndex > 0) {
                    crosshairIndex--;
                }
                crosshairView.setImage(crosshairs[crosshairIndex]);
                break;
            case LEFT:
                if (backgroundIndex > 0) {
                    backgroundIndex--;
                }
                getBackgroundView().setImage(backgrounds[backgroundIndex]);
                break;
            case RIGHT:
                if (backgroundIndex < backgrounds.length-1) {
                    backgroundIndex++;
                }
                getBackgroundView().setImage(backgrounds[backgroundIndex]);
                break;
            case ENTER:
                selectBackgroundAndCrosshair();
                Effects.getTitleMusic().stop();

                Effects.getIntroMusic().play();

                while(Effects.getIntroMusic().isPlaying()) {
                    //waits for the intro music to stop.
                }

                Effects.getIntroMusic().stop();
                DuckHunt.getGameStage().setScene(new Level(1).getScene());

                break;
            case ESCAPE:
                DuckHunt.getGameStage().setScene(new TitleScreen().getScene());
                break;
        }
    };

    /**
     * The constructor for BackgroundSelectionScreen initializes the scene elements which are
     * the default background, the default crosshair and the prompt text.
     */
    public BackgroundSelectionScreen() {

        getScene().setOnKeyPressed(null);

        setBackground(backgrounds[0]);
        setBackgroundView();

        setCrosshair(crosshairs[0]);
        initializeCrosshairView();

        initializeText();

        getPane().getChildren().addAll(getBackgroundView(), crosshairView, text);

        getScene().setOnKeyPressed(screenController);
    }


    /**
     * Initializes the prompt text.
     */
    public void initializeText() {
        text = TextManager.generateText(WIDTH/3.25, HEIGHT/14, 7,
                "USE ARROW KEYS TO NAVIGATE\nPRESS ENTER TO START\nPRESS ESC TO EXIT");
    }

    /**
     * Sets the crosshair that is showcased in the screen.
     * @param crosshair the crosshair image to be set.
     */
    public void setCrosshair(Image crosshair) {
        this.crosshair = crosshair;
    }

    /**
     * Initializes the crosshair view element for the crosshair.
     */
    public void initializeCrosshairView() {
        crosshairView = new ImageView(crosshair);

        crosshairView.setX(WIDTH/2.10);
        crosshairView.setY(HEIGHT/2.25);
    }

    /**
     * Locks the crosshair and background to the selected ones. Called only when
     * the user presses ENTER.
     */
    private void selectBackgroundAndCrosshair() {
        getScene().setOnKeyPressed(null);
        Sprites.setSelectedBackgroundIndex(backgroundIndex);
        Sprites.setSelectedCrosshairIndex(crosshairIndex);
    }


}
