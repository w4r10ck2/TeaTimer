package main.music;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;

public class PlayMusic {
    private MediaPlayer mediaPlayer;
    private boolean available;

    public PlayMusic() {
        final URL resource = getClass().getResource
                ("/musicfiles/Ticktac.mp3");
        if (resource == null) {
            return;
        }
        available = true;
        final Media media = new Media(resource.toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    }

    public void start() {
        if (available) {
            mediaPlayer.play();
        }
    }

    public void stop() {
        if (available) {
            mediaPlayer.stop();
        }
    }

    public boolean isAvailable() {
        return available;
    }
}
