import javafx.scene.media.AudioClip;

/**
 * Acts as a storage for sound effects and musics for the game.
 */
public class Effects {

    private static final AudioClip titleMusic = new AudioClip(
            Effects.class.getResource("/assets/effects/Title.mp3").toExternalForm());
    private static final AudioClip introMusic = new AudioClip(
            Effects.class.getResource("/assets/effects/Intro.mp3").toExternalForm());
    private static final AudioClip gunshotMusic = new AudioClip(
            Effects.class.getResource("/assets/effects/Gunshot.mp3").toExternalForm());
    private static final AudioClip gameOverMusic = new AudioClip(
            Effects.class.getResource("/assets/effects/GameOver.mp3").toExternalForm());
    private static final AudioClip levelCompletedMusic = new AudioClip(
            Effects.class.getResource("/assets/effects/LevelCompleted.mp3").toExternalForm());
    private static final AudioClip gameCompletedMusic = new AudioClip(
            Effects.class.getResource("/assets/effects/GameCompleted.mp3").toExternalForm());
    private static final AudioClip duckFallingMusic = new AudioClip(
            Effects.class.getResource("/assets/effects/DuckFalls.mp3").toExternalForm());
    private static final AudioClip[] musics = {titleMusic, introMusic, gunshotMusic,
            gameOverMusic, levelCompletedMusic, gameCompletedMusic, duckFallingMusic};

    // adjusts the audio volume statically
    static {
        for (AudioClip music : musics) {
            music.setVolume(DuckHunt.VOLUME);
        }
    }

    public static AudioClip getDuckFallingMusic() {
        return duckFallingMusic;
    }

    public static AudioClip getGameCompletedMusic() {
        return gameCompletedMusic;
    }

    public static AudioClip getGameOverMusic() {
        return gameOverMusic;
    }

    public static AudioClip getGunshotMusic() {
        return gunshotMusic;
    }

    public static AudioClip getIntroMusic() {
        return introMusic;
    }

    public static AudioClip getLevelCompletedMusic() {
        return levelCompletedMusic;
    }

    public static AudioClip getTitleMusic() {
        return titleMusic;
    }

}
