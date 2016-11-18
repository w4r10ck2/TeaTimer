package main.panes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
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
    private InputPane inputPane;
    private TimePane timePane;
    private StartClearButtonPane startClearButtonPane;
    private PlayAlarm playAlarm;
    private Language language;
    private EditUserInterfacePane editUserInterfacePane;
    private Main main;
    private Stage stage;

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
            Alert alert = new Alert(Alert.AlertType.WARNING, "couldn't load" + " resources.musicfiles file");
            alert.showAndWait().filter(response -> response == ButtonType
                    .OK || response == ButtonType.CLOSE);
        }
        getChildren().setAll(timePane, inputPane);
    }

    void changeToInputMode() {
        getChildren().removeAll(getChildren());
        editUserInterfacePane.setBackBtnEvent(event -> changeToInputMode());
        getChildren().addAll(timePane, inputPane);
    }

    void changeToStartMode() {
        getChildren().removeAll(getChildren());
        editUserInterfacePane.setBackBtnEvent(event -> changeToStartMode());
        getChildren().addAll(timePane, startClearButtonPane);
    }

    void changeToEditUserInterface() {
        getChildren().removeAll(getChildren());
        getChildren().add(editUserInterfacePane);
    }

    void changeToTimeEditInterface() {
        getChildren().removeAll(getChildren());
        ScrollPane sp = new ScrollPane();
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        sp.setContent(new ChangeTimesPane(this, main.getMenuBar()));
        sp.setStyle("-fx-background-color:transparent;");
        sp.setPadding(new Insets(0, 0, 0, 0));
        getChildren().addAll(sp);
    }

    TimePane getTimePane() {
        return timePane;
    }

    void startMusic() {
        playAlarm.start();
    }

    void stopMusic() {
        playAlarm.stop();
    }

    Locale getLanguage() {
        return language.getLanguage();
    }

    void setLanguage(Locale language) {
        Objects.requireNonNull(language, "language is null");
        this.language.setLanguage(language);
    }

    ResourceBundle getResourceBundle() {
        return language.getResourceBundle();
    }

    void disableTimes() {
        main.disableTimes();
    }

    void activateTimes() {
        main.activateTimes();
    }

    void changeLanguage() {
        main.changeLanguage();
        startClearButtonPane.changeLanguage();
        inputPane.changeLanguage();
    }

    void changeMusicFile(String path) {
        playAlarm.changeMusicFile(path);
    }

    void playAlarmShort(String path) {
        playAlarm.playShort(path);
    }

    void setFullscreen(boolean isFullscreen) {
        stage.setFullScreen(isFullscreen);
    }

    void setAlarmCount(Integer alarmCount) {
        playAlarm.setAlarmCount(alarmCount);
    }

    @Override
    public void finish() {
        stage.toFront();
    }
}
