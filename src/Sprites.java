import javafx.scene.image.Image;
import java.io.File;

/**
 * Sprites class acts as a decoder and storage for all sprites.
 */
public class Sprites {
    public static final Image[] backgrounds = loadSpritesFromPath("assets/background");
    private static final Image[] crosshairs = loadSpritesFromPath("assets/crosshair");
    private static final Image[] blackDucks = loadSpritesFromPath("assets/duck_black");
    private static final Image[] blueDucks = loadSpritesFromPath("assets/duck_blue");
    private static final Image[] redDucks = loadSpritesFromPath("assets/duck_red");
    private static final Image[] foregrounds = loadSpritesFromPath("assets/foreground");
    private static final Image icon = loadSpritesFromPath("assets/favicon")[0];
    private static final Image titleScreen = loadSpritesFromPath("assets/welcome")[0];
    //the selected index instance variables are used to mark the selected background and crosshair in the background selection screen.
    private static int selectedBackgroundIndex = 0;
    private static int selectedCrosshairIndex = 0;

    /**
     * Returns sprites under the specified folder as an Image array.
     * @param spriteFolderName the path of the sprite folder.
     * @return an image array containing every sprite under the folder.
     */
    public static Image[] loadSpritesFromPath(String spriteFolderName) {

        File spriteFolder = new File(spriteFolderName);
        File[] spriteFiles = spriteFolder.listFiles();
        Image[] sprites = null;

        if (spriteFiles != null) {

            sprites = new Image[spriteFiles.length];

            for (int i = 0; i < spriteFiles.length; i++) {
                String path = spriteFiles[i].getPath();
                Image image = new Image(path);
                sprites[i] = new Image(path, image.getWidth()*DuckHunt.SCALE, image.getHeight()*DuckHunt.SCALE, true, true);
            }

        }

        return sprites;
    }

    public static Image[] getBackgrounds() {
        return backgrounds;
    }

    public static Image[] getCrosshairs() {
        return crosshairs;
    }

    public static Image[] getBlackDucks() {
        return blackDucks;
    }

    public static Image[] getBlueDucks() {
        return blueDucks;
    }

    public static Image[] getRedDucks() {
        return redDucks;
    }
    public static Image[] getForegrounds() {
        return foregrounds;
    }
    public static Image getIcon() {
        return icon;
    }
    public static Image getTitleScreen() {
        return titleScreen;
    }
    public static void setSelectedBackgroundIndex(int selectedBackgroundIndex) {
        Sprites.selectedBackgroundIndex = selectedBackgroundIndex;
    }

    public static int getSelectedBackgroundIndex() {
        return selectedBackgroundIndex;
    }

    public static void setSelectedCrosshairIndex(int selectedCrosshairIndex) {
        Sprites.selectedCrosshairIndex = selectedCrosshairIndex;
    }

    public static int getSelectedCrosshairIndex() {
        return selectedCrosshairIndex;
    }
}
