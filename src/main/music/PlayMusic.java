package main.music;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;

public class PlayMusic {
    private MediaPlayer mediaPlayer;
    private boolean available;
    private URL resource;
    Media media;

    public PlayMusic() {
        resource = getClass().getResource
                ("/musicfiles/Ticktac.mp3");
        if (resource == null) {
            return;
        }
        available = true;
        media = new Media(resource.toString());
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

    public void changeMusicFile(String path) {
        resource = getClass().getResource(path);
        if (resource == null) {
            return;
        }
        available = true;
        media = new Media(resource.toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    }

    public boolean isAvailable() {
        return available;
    }
}
