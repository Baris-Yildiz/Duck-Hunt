import javafx.event.EventHandler;
import javafx.scene.ImageCursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.*;

import java.util.ArrayList;
import java.util.List;

/**
 * The Level class constructs the levels of the game.
 */
public class Level extends Screen {

    private int levelIndex;
    private static int ammoLeft;
    private Text levelText;
    private Text ammoText;
    private Text levelCompletedText;
    private Text levelCompletedFlashingText;

    private Image foreground;
    private ImageView foregroundView;

    private final List<Duck> ducks = new ArrayList<>();

    /**
     * Handler for the events that can occur on the game over screen such as restarting the game
     * and exiting the game.
     */
    private final EventHandler<KeyEvent> onGameOver = event -> {
        switch(event.getCode()) {
            case ENTER:
                Effects.getGameOverMusic().stop();
                DuckHunt.getGameStage().setScene(new Level(1).getScene());
                break;
            case ESCAPE:
                Effects.getGameOverMusic().stop();
                DuckHunt.getGameStage().setScene(new TitleScreen().getScene());
                break;
        }

    };
    /**
     * Handler for the events that can occur on the level completed screen such as advancing to the
     * next level and restarting the game if level 6 is completed.
     */
    private final EventHandler<KeyEvent> onLevelCompleted = event -> {
        switch(event.getCode()) {
            case ENTER:

                if (levelIndex == 6) {
                    Effects.getGameCompletedMusic().stop();
                    DuckHunt.getGameStage().setScene(new Level( 1).getScene());
                } else {
                    Effects.getLevelCompletedMusic().stop();
                    DuckHunt.getGameStage().setScene(new Level(levelIndex + 1).getScene());
                }

                break;
            case ESCAPE:

                if (levelIndex == 6) {
                    Effects.getGameCompletedMusic().stop();
                    DuckHunt.getGameStage().setScene(new TitleScreen().getScene());
                }
                break;
        }
    };

    /**
     * Handles the events that occur whenever the user shoots. Decrements the
     * ammo count by 1, checks if a duck has been hit, and decides on the endgame.
     */
    private final EventHandler<MouseEvent> onShoot = event -> {

        if (ammoLeft > 0) {

            Effects.getGunshotMusic().play();
            ammoText.setText("Ammo Left: " + --ammoLeft);

            if (Duck.getDucksRemaining() == 0) {

                initializeLevelCompletedText();
                initializeLevelCompletedFlashingText();

                if (levelIndex == 6) {
                    Effects.getGameCompletedMusic().play();
                    levelCompletedText.setText("You have completed the game!");
                    levelCompletedText.setX(WIDTH / 14);

                    levelCompletedFlashingText.setText("Press ENTER to play again\nPress ESC to exit");
                    levelCompletedFlashingText.setX(WIDTH / 8);
                } else {
                    Effects.getLevelCompletedMusic().play();
                }

                getScene().setOnMouseClicked(null);
                getScene().setOnKeyPressed(onLevelCompleted);

            } else if (ammoLeft <= 0) {
                Effects.getGameOverMusic().play();
                initializeGameOverText();
                initializeGameOverFlashingText();

                getScene().setOnMouseClicked(null);
                getScene().setOnKeyPressed(onGameOver);
            }

        }
    };

    /**
     * The constructor for the Level class takes a levelIndex and constructs the corresponding level.
     * @param levelIndex the level index that represents which level to create. For example, if
     *                   level index is 1, Level 1 is created.
     */
    public Level(int levelIndex) {

        this.levelIndex = levelIndex;

        InitializeDucks();
        ammoLeft = Duck.getDucksRemaining() * 3;
        InitializeSceneElements();
    }

    /**
     * Initializes the text that displays the level index.
     */
    public void initializeLevelText() {
        levelText = TextManager.generateText(WIDTH/2.15, 25, 6.5,
                "Level " + levelIndex);
    }

    /**
     * Initializes the text that displays the remaining ammo count.
     */
    public void initializeAmmoText() {
        ammoText = TextManager.generateText(WIDTH* 8.2/10, 25, 6.5,
                "Ammo Left: " + ammoLeft);
    }

    /**
     * Initializes the text displayed on game over.
     */
    public void initializeGameOverText() {
        Text gameOverText = TextManager.generateText(WIDTH / 3.25, HEIGHT / 2.2, 15.5,
                "GAME OVER!");

        getPane().getChildren().add(gameOverText);
    }

    /**
     * Initializes the flashing part of the game over text.
     */
    public void initializeGameOverFlashingText() {
        Text gameOverFlashingText = TextManager.generateText(WIDTH / 7, HEIGHT / 1.8, 15.5,
                "Press ENTER to play again\nPress ESC to exit");

        TextManager.assignFlashAnimationToText(gameOverFlashingText);

        getPane().getChildren().add(gameOverFlashingText);
    }

    /**
     * Initializes the text displayed on level completed.
     */
    public void initializeLevelCompletedText() {
        levelCompletedText = TextManager.generateText(WIDTH/2.75, HEIGHT/2.125, 15.5,
                "YOU WIN!");

        getPane().getChildren().add(levelCompletedText);
    }

    /**
     * Initializes the flashing part of the level completed text.
     */
    public void initializeLevelCompletedFlashingText() {
        levelCompletedFlashingText = TextManager.generateText(WIDTH/14, HEIGHT/1.8, 15.5,
                "Press ENTER to play next level");

        TextManager.assignFlashAnimationToText(levelCompletedFlashingText);

        getPane().getChildren().add(levelCompletedFlashingText);
    }

    /**
     * Sets the foreground of the scene.
     * @param foreground the foreground to be set.
     */
    public void setForeground(Image foreground) {
        this.foreground = foreground;
    }

    /**
     * Initializes an image view for the foreground image.
     */
    public void initializeForegroundView() {
        foregroundView = new ImageView(foreground);

        foregroundView.setDisable(true);
    }

    /**
     * Initializes the ducks depending on the level index and adds them to the "ducks" list.
     */
    private void InitializeDucks() {
        switch (levelIndex) {
            case 1:
                Duck.setDucksRemaining(1);
                ducks.add(new Duck(WIDTH/10, HEIGHT/5, FlyingDirection.RIGHT, DuckColor.BLUE));
                break;
            case 2:
                Duck.setDucksRemaining(2);
                ducks.add(new Duck(WIDTH * 9/10, HEIGHT/6, FlyingDirection.LEFT, DuckColor.RED));
                ducks.add(new Duck(WIDTH/10, HEIGHT/3, FlyingDirection.RIGHT, DuckColor.BLACK));
                break;
            case 3:
                Duck.setDucksRemaining(1);
                ducks.add(new Duck(WIDTH/5, HEIGHT * 9/10, FlyingDirection.UP_RIGHT, DuckColor.BLACK));
                break;
            case 4:
                Duck.setDucksRemaining(2);
                ducks.add(new Duck(WIDTH * 8/10, HEIGHT/4, FlyingDirection.UP_RIGHT, DuckColor.RED));
                ducks.add(new Duck(WIDTH/2, HEIGHT * 9/10, FlyingDirection.UP_LEFT, DuckColor.BLUE));
                break;
            case 5:
                Duck.setDucksRemaining(3);
                ducks.add(new Duck(WIDTH * 9/10, HEIGHT/10, FlyingDirection.DOWN_LEFT, DuckColor.BLACK));
                ducks.add(new Duck(WIDTH/3, HEIGHT/6, FlyingDirection.DOWN_RIGHT, DuckColor.RED));
                ducks.add(new Duck(WIDTH/8, HEIGHT/10, FlyingDirection.LEFT, DuckColor.BLUE));
                break;
            case 6:
                Duck.setDucksRemaining(4);
                ducks.add(new Duck(WIDTH/10, HEIGHT * 9/10, FlyingDirection.UP_RIGHT, DuckColor.RED));
                ducks.add(new Duck(WIDTH/4, HEIGHT/10, FlyingDirection.DOWN_RIGHT, DuckColor.BLUE));
                ducks.add(new Duck(WIDTH/7, HEIGHT/7, FlyingDirection.UP_LEFT, DuckColor.BLACK));
                ducks.add(new Duck(WIDTH * 9/10, HEIGHT/5, FlyingDirection.LEFT, DuckColor.RED));
                break;
        }
    }

    /**
     * Initializes all scene elements which are the selected background, the matching foreground, the selected crosshair
     * and all the text elements.
     */
    private void InitializeSceneElements() {

        setBackground(Sprites.getBackgrounds()[Sprites.getSelectedBackgroundIndex()]);
        setBackgroundView();

        setForeground(Sprites.getForegrounds()[Sprites.getSelectedBackgroundIndex()]);
        initializeForegroundView();

        initializeLevelText();
        initializeAmmoText();


        ImageCursor imageCursor = new ImageCursor(Sprites.getCrosshairs()[Sprites.getSelectedCrosshairIndex()]);
        getScene().setCursor(imageCursor);

        getPane().getChildren().add(getBackgroundView());
        for (Duck duck : ducks) {
            getPane().getChildren().add(duck.getDuck());
        }
        getPane().getChildren().addAll(foregroundView, levelText, ammoText);

        getScene().setOnMouseClicked(onShoot);
    }

    public static int getAmmoLeft() {
        return ammoLeft;
    }
}
