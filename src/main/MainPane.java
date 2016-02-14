package main;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

public class MainPane extends VBox{
    InputPane inputPane;
    TimePane timePane;
    StartClearButtonPane startClearButtonPane;
    PlayMusic playMusic;

    public MainPane() {
        setAlignment(Pos.CENTER);
        setSpacing(25);
        timePane = new TimePane();
        inputPane = new InputPane(this);
        startClearButtonPane = new StartClearButtonPane(this);
        playMusic = new PlayMusic();
        getChildren().setAll(timePane, inputPane);
    }

    public void changeToInputMode() {
        getChildren().remove(startClearButtonPane);
        getChildren().add(inputPane);
    }

    public void changeToStartMode() {
        getChildren().remove(inputPane);
        getChildren().add(startClearButtonPane);
    }

    public TimePane getTimePane() {
        return timePane;
    }

    public void startMusic() {
        playMusic.start();
    }

    public void stopMusic() {
        playMusic.stop();
    }

}
