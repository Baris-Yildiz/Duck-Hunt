import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;
import javafx.util.Duration;

/**
 * The Duck class is responsible for initializing a certain duck. It also contains a static instance variable
 * ducksRemaining to keep track of the remaining ducks in the level.
 */
public class Duck {

    private Image duckImage;
    private ImageView duckImageView;
    private Rectangle hitbox;
    private StackPane duck;
    private Image[] duckSprites;
    private Image[] flyingDuckSprites;


    private final Scale rotationScale = new Scale(1, 1);
    private double xSpeed;
    private double ySpeed;
    private double offsetMultiplier = 0;

    private static final double screenHeight = Screen.HEIGHT;
    private static final double screenWidth = Screen.WIDTH;

    private static int ducksRemaining;

    /**
     * Responsible for the movement animation of the duck and calling the "moveDuck" method to
     * move the duck.
     */
    private final Timeline flyingAnimation = new Timeline(
            new KeyFrame(Duration.seconds(0.3), event -> {
                duckImageView.setImage(flyingDuckSprites[0]);
                moveDuck(xSpeed, ySpeed);
            }),
            new KeyFrame(Duration.seconds(0.6), event -> {
                duckImageView.setImage(flyingDuckSprites[1]);
                moveDuck(xSpeed, ySpeed - 5 * DuckHunt.SCALE * offsetMultiplier);
            }),
            new KeyFrame(Duration.seconds(0.9), event -> {
                duckImageView.setImage(flyingDuckSprites[2]);
                moveDuck(xSpeed, ySpeed + 5 * DuckHunt.SCALE * offsetMultiplier);
            })
    );
    /**
     * Gets played when the duck gets hit.
     */
    private final Timeline dyingAnimation = new Timeline(
            new KeyFrame(Duration.seconds(0), event -> duckImageView.setImage(duckSprites[6])),
            new KeyFrame(Duration.seconds(0.4), event -> duckImageView.setImage(duckSprites[7]))
    );

    /**
     * Gets played right after the duck gets hit. In other words, plays right after dyingAnimation.
     */
    private final Timeline fallingAnimation = new Timeline(
            new KeyFrame(Duration.seconds(0.4), event -> {
                if (duck.getTranslateY() < screenHeight) {
                    duck.setTranslateY(duck.getTranslateY() + 100);
                }
            })
    );
    /**
     * Handles the events that occur when a duck has been hit such as playing the death animations
     * for the duck, playing the "DuckFall" music and decrementing the remaining duck count by 1.
     */
    private final EventHandler<MouseEvent> onDuckHit = event -> {

        if (Level.getAmmoLeft() != 0) {
            Effects.getDuckFallingMusic().play();

            ducksRemaining--;

            flyingAnimation.stop();

            dyingAnimation.play();

            fallingAnimation.play();

            duck.setOnMouseClicked(null);
        }

    };


    /**
     * The constructor for Duck objects initializes a duck with its hitbox as a StackPane in the screen.
     * It also decides on the motion of the duck depending on its flying direction.
     * @param x the initial x position of the duck
     * @param y the initial y position of the duck
     * @param flyingDirection the initial flying direction of the duck
     * @param duckColor the color of the duck
     */
    public Duck(double x, double y, FlyingDirection flyingDirection, DuckColor duckColor) {

        xSpeed = 25 * DuckHunt.SCALE * Math.cos(Math.toRadians(-flyingDirection.getAngleValue()));
        ySpeed = 25 * DuckHunt.SCALE * Math.sin(Math.toRadians(-flyingDirection.getAngleValue()));

        if (ySpeed == 0) {
            offsetMultiplier = 1;
        }

        setDuckSprites(duckColor, flyingDirection);

        initializeDuck();

        duck.setTranslateX(x);
        duck.setTranslateY(y);

        duck.setOnMouseClicked(onDuckHit);

        duck.getTransforms().add(rotationScale);

        flyingAnimation.setCycleCount(Animation.INDEFINITE);
        flyingAnimation.playFromStart();

        fallingAnimation.setCycleCount(Animation.INDEFINITE);
    }

    /**
     * Determines which sprite sheet/sprite array to use on animations based on the given color of the duck.
     * @param duckColor color of the duck
     */
    public void setDuckSprites(DuckColor duckColor, FlyingDirection flyingDirection) {
        switch (duckColor) {
            case BLACK:
                duckSprites = Sprites.getBlackDucks();
                break;
            case BLUE:
                duckSprites = Sprites.getBlueDucks();
                break;
            case RED:
                duckSprites = Sprites.getRedDucks();
                break;
        }

        if (flyingDirection.getAngleValue() == 0 || flyingDirection.getAngleValue() == 180) {
            flyingDuckSprites = new Image[]{
                    duckSprites[3],
                    duckSprites[4],
                    duckSprites[5]
            };
        } else {
            flyingDuckSprites = new Image[]{
                    duckSprites[0],
                    duckSprites[1],
                    duckSprites[2]
            };
        }


        rotationScale.setX( (xSpeed >= 0) ? 1 : -1 );
        rotationScale.setY( (ySpeed <= 0) ? 1 : -1 );

    }

    /**
     * Sets the duck image to the first sprite of the flying animation.
     */
    public void initializeDuckImage() {
        duckImage = flyingDuckSprites[0];
    }

    /**
     * Initializes an image view for the duck.
     */
    public void initializeDuckImageView() {
        duckImageView = new ImageView(duckImage);
    }

    /**
     * Initializes a hitbox for the duck
     */
    public void initializeHitbox() {
        hitbox = new Rectangle(duckImage.getWidth(),duckImage.getHeight());
        hitbox.setFill(Color.TRANSPARENT);
    }

    /**
     * Initializes the "duck" which is a combination of the image view and the hitbox
     * placed in a StackPane.
     */
    public void initializeDuck() {
        initializeDuckImage();
        initializeDuckImageView();
        initializeHitbox();
        duck = new StackPane(duckImageView, hitbox);
    }

    public StackPane getDuck() {
        return duck;
    }

    public static int getDucksRemaining() {
        return ducksRemaining;
    }

    public static void setDucksRemaining(int ducksRemaining) {
        Duck.ducksRemaining = ducksRemaining;
    }

    /**
     * Responsible for the movement of the duck, collision check and reflection of the duck according to the collider.
     * @param xIncrement amount of pixels to move along the x-axis.
     * @param yIncrement amount of pixels to move along the y-axis.
     */
    private void moveDuck(double xIncrement, double yIncrement) {
        duck.setTranslateX(duck.getTranslateX() + xIncrement);
        duck.setTranslateY(duck.getTranslateY() + yIncrement);

        if (duck.getTranslateX() + hitbox.getWidth()/2 > screenWidth * 19/20
                || duck.getTranslateX() - hitbox.getWidth()/2 < screenWidth/20) {

            rotationScale.setX(-rotationScale.getX());
            xSpeed = -xSpeed;
        }

        if (duck.getTranslateY() + hitbox.getHeight()/2 > screenHeight
                || duck.getTranslateY() - hitbox.getHeight()/2 < 0) {


            rotationScale.setY(-rotationScale.getY());
            ySpeed = -ySpeed;

            duck.setTranslateY(duck.getTranslateY() -yIncrement * 1.5);
        }

    }


}
