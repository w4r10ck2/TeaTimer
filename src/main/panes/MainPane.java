package main.panes;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.Listener.FinishListener;
import main.language.Language;
import main.music.PlayAlarm;
import main.windows.Main;

import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainPane extends VBox implements FinishListener {
    InputPane inputPane;
    TimePane timePane;
    StartClearButtonPane startClearButtonPane;
    PlayAlarm playAlarm;
    Language language;
    EditUserInterfacePane editUserInterfacePane;
    Main main;
    Stage stage;

    public MainPane(Language language, Main main, Stage stage) {
        setAlignment(Pos.CENTER);
        this.stage = stage;
        this.main = main;
        setSpacing(25);
        this.language = language;
        editUserInterfacePane = new EditUserInterfacePane(this);
        timePane = new TimePane();
        timePane.addFinishListener(this);
        inputPane = new InputPane(this);
        startClearButtonPane = new StartClearButtonPane(this);
        playAlarm = new PlayAlarm();
        if (!playAlarm.isAvailable()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "couldn't load" +
                    " musicfiles file");
            alert.showAndWait().filter(response -> response == ButtonType
                    .OK || response == ButtonType.CLOSE);
        }
        getChildren().setAll(timePane, inputPane);
    }

    public void changeToInputMode() {
        getChildren().removeAll(getChildren());
        editUserInterfacePane.setBackBtnEvent(event -> changeToInputMode());
        getChildren().addAll(timePane, inputPane);
    }

    public void changeToStartMode() {
        getChildren().removeAll(getChildren());
        editUserInterfacePane.setBackBtnEvent(event -> changeToStartMode());
        getChildren().addAll(timePane, startClearButtonPane);
    }

    public void changeToEditUserInterface() {
        getChildren().removeAll(getChildren());
        getChildren().add(editUserInterfacePane);
    }

    public void changeToTimeEditInterface() {
        getChildren().removeAll(getChildren());
        getChildren().addAll(new ChangeTimesPane(this, main.getMenuBar()));
    }

    public TimePane getTimePane() {
        return timePane;
    }

    public void startMusic() {
        playAlarm.start();
    }

    public void stopMusic() {
        playAlarm.stop();
    }

    public Locale getLanguage() {
        return language.getLanguage();
    }

    public void setLanguage(Locale language) {
        Objects.requireNonNull(language, "language is null");
        this.language.setLanguage(language);
    }

    public ResourceBundle getResourceBundle() {
        return language.getResourceBundle();
    }

    public void disableTimes() {
        main.disableTimes();
    }

    public void activateTimes() {
        main.activateTimes();
    }

    public void changeLanguage() {
        main.changeLanguage();
        startClearButtonPane.changeLanguage();
        inputPane.changeLanguage();
    }

    public void changeMusicFile(String path) {
        playAlarm.changeMusicFile(path);
    }

    public void playAlarmShort(String path) {
        playAlarm.playShort(path);
    }

    @Override
    public void finish() {
        stage.toFront();
    }
}
