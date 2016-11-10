package main.music;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.net.URL;

public class PlayAlarm {
    private MediaPlayer mediaPlayer;
    private boolean available;
    private URL resource;
    private Media media;

    public PlayAlarm() {
        if (!checkResource("/musicfiles/Ticktac.mp3")) {
            return;
        }
        media = new Media(resource.toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(3);
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

    public void playShort(String path) {
        URL resourceShort = getClass().getResource(path);
        if (resourceShort == null) {
            return;
        }
        Media mediaShort = new Media(resourceShort.toString());
        MediaPlayer shortPlayer = new MediaPlayer(mediaShort);
        shortPlayer.setCycleCount(1);
        shortPlayer.stopTimeProperty().setValue(new Duration(2000));
        shortPlayer.play();
    }

    public void changeMusicFile(String path) {
        if (!checkResource(path)) {
            return;
        }
        media = new Media(resource.toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(3);
    }

    private boolean checkResource(String path) {
        resource = getClass().getResource(path);
        available = resource != null;
        return available;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAlarmCount(Integer alarmCount) {
        if (available) {
            if (alarmCount == 0) {
                mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            } else {
                mediaPlayer.setCycleCount(alarmCount);
            }
        }
    }
}
