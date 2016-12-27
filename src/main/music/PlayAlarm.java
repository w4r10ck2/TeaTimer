package main.music;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import main.config.Config;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class PlayAlarm {
    private MediaPlayer mediaPlayer;
    private boolean available;
    private URL resource;
    private Media media;

    public PlayAlarm() {
        Properties prop = Config.getProperties();
        if (!checkResource(prop.getProperty("musicFile"))) {
            return;
        }
        media = new Media(resource.toString());
        mediaPlayer = new MediaPlayer(media);
        Properties properties = Config.getProperties();
        if (properties.getProperty("repetitionCount").equals("indefinite")) {
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        } else {
            mediaPlayer.setCycleCount(Integer.valueOf(properties.getProperty("repetitionCount")));
        }
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

    public void changeMusicFile(String path) throws IOException {
        if (!checkResource(path)) {
            return;
        }
        Properties newProp = new Properties();
        newProp.setProperty("musicFile", path);
        Config.updateProperties(newProp);
        media = new Media(resource.toString());
        mediaPlayer = new MediaPlayer(media);
        Properties properties = Config.getProperties();
        if (properties.getProperty("repetitionCount").equals("indefinite")) {
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        } else {
            mediaPlayer.setCycleCount(Integer.valueOf(properties.getProperty("repetitionCount")));
        }
    }

    private boolean checkResource(String path) {
        resource = getClass().getResource(path);
        available = resource != null;
        return available;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAlarmCount(Integer alarmCount) throws IOException {
        if (available) {
            Properties properties = new Properties();
            if (alarmCount == 0) {
                mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
                properties.setProperty("repetitionCount", "indefinite");
            } else {
                mediaPlayer.setCycleCount(alarmCount);
                properties.setProperty("repetitionCount", alarmCount.toString());
            }
            Config.updateProperties(properties);
        }
    }
}
