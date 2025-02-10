/**
 * Enum used to store flying directions with needed rotation angle for the duck relative to the positive x-axis.
 */
public enum FlyingDirection {
    RIGHT(0),
    LEFT(180),
    UP_RIGHT(45),
    UP_LEFT(135),
    DOWN_RIGHT(-45),
    DOWN_LEFT(-135);

    private final int angleValue;

    FlyingDirection(int angleValue) {
        this.angleValue = angleValue;
    }

    public int getAngleValue() {
        return angleValue;
    }
}
