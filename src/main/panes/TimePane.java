package main.panes;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import main.Listener.FinishListener;

import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

class TimePane extends GridPane implements FinishListener {
    private char[] timeCharacters;
    private int counter;
    private Text timeText;
    private Text timeSecondText;
    private Text timeMinuteText;
    private Text timeHourText;
    private Text timeMillisecondText;
    private boolean isAdded;
    private Integer timeSeconds;
    private Integer timeMinutes;
    private Integer timeHours;
    private Integer timeMilliseconds;
    private Timeline timeline;
    private Timeline timeline2;
    private Rectangle r;
    private CopyOnWriteArrayList<FinishListener> finishListeners;

    private boolean isRunning;

    TimePane() {
        timeText = new Text();
        timeMillisecondText = new Text("000");
        clear();
        createContent();
        timeSecondText = new Text();
        timeMinuteText = new Text();
        timeHourText = new Text();
        finishListeners = new CopyOnWriteArrayList<>();
        finishListeners.add(this);
    }


    private void createContent() {
        timeText = new Text(convertTimeCharactersToString());
        timeText.setFont(Font.font(50));

        r = new Rectangle();
        r.setWidth(300);
        r.setHeight(100);
        r.setArcWidth(20);
        r.setArcHeight(20);
        r.setFill(Color.LIGHTGRAY);
        r.setStroke(Color.BLACK);
        r.setStyle("-fx-base: #dadee3;");
        setHgap(1);
        setVgap(1);
        setPadding(new Insets(0, 0, 0, 0));
        add(r, 0, 0, 4, 4);
        add(timeText, 0, 0, 4, 1);
        add(timeMillisecondText, 2, 1);

        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(25);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(25);
        ColumnConstraints column3 = new ColumnConstraints();
        column3.setPercentWidth(25);
        ColumnConstraints column4 = new ColumnConstraints();
        column4.setPercentWidth(25);

        getColumnConstraints().addAll(column1, column2, column3, column4);

        GridPane.setHalignment(timeMillisecondText, HPos.RIGHT);
        GridPane.setHalignment(timeText, HPos.CENTER);
        GridPane.setHalignment(r, HPos.CENTER);
    }

    void clear() {
        timeCharacters = new char[]{'0', '0', '0', '0', '0', '0', '0'};
        counter = 0;
        timeHours = 0;
        isAdded = false;
        timeMillisecondText.setText("000");
        timeMilliseconds = 0;
        timeMinutes = 0;
        timeSeconds = 0;
        changeTimeText();
    }

    void add(Integer timeValue) {
        if (timeValue < 0 || timeValue > 9) {
            throw new IllegalArgumentException("timeValue is greater than 9" +
                    " or lower than 0");
        }
        switch (counter) {
            case 0:
                if (timeValue == 0) {
                    return;
                }
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                counter++;
                charLeftShift(timeValue.toString());
                isAdded = true;
                break;
            case 7:
                clear();
                counter = 0;
                break;
            default:
                throw new IllegalArgumentException("You shouldn't have done " +
                        "that");
        }
        changeTimeText();
    }

    private void charLeftShift(String timeValue) {
        if (timeValue.length() < 1) {
            throw new IllegalArgumentException("timeValue is not a char");
        }
        System.arraycopy(timeCharacters, 1, timeCharacters, 0, timeCharacters.length - 1);
        timeCharacters[timeCharacters.length - 1] = timeValue.charAt(0);
    }

    private String convertTimeCharactersToString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (char timeCharacter : timeCharacters) {
            stringBuilder.append(timeCharacter);
        }
        stringBuilder.insert(3, ":");
        stringBuilder.insert(6, ":");
        if (counter != 7) {
            stringBuilder.deleteCharAt(0);
        }
        return stringBuilder.toString();
    }

    private void changeTimeText() {
        timeText.setText(convertTimeCharactersToString());
    }

    private void updateRunningTimeText() {
        timeText.setText(timeHourText.getText() + ":" + timeMinuteText
                .getText() + ":" + timeSecondText.getText());
    }

    void calculateTime() {
        parseTime(timeText.getText());

        String tmp = timeText.getText();
        timeCharacters = tmp.toCharArray();
    }

    boolean isRunning() {
        return isRunning;
    }

    void start() {
        isRunning = true;
        timeline = new Timeline();
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.millis(1),
                        ae -> decrease()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    void stop() {
        isRunning = false;
        if (timeline != null) {
            timeline.stop();
        }
        timeMilliseconds = 0;
        timeMillisecondText.setText("000");
        StringBuilder stringBuilder = new StringBuilder();
        for (char timeCharacter : timeCharacters) {
            stringBuilder.append(timeCharacter);
        }
        String tmp = stringBuilder.toString();
        parseTime(tmp);
    }

    void revert() {
        isRunning = false;
        timeline = null;
        timeline2 = null;
        clear();
    }

    void pause() {
        isRunning = false;
        timeline.pause();
    }

    private void decrease() {
        timeMilliseconds -= 1;
        if (timeMilliseconds < 0) {
            timeMilliseconds = 999;
            timeSeconds -= 1;
        }
        if (timeSeconds < 0) {
            timeSeconds = 59;
            timeMinutes -= 1;
        }
        if (timeMinutes < 0) {
            timeMinutes = 59;
            timeHours -= 1;
        }
        if (timeHours < 0) {
            timeHours = 0;
            timeMinutes = 0;
            timeSeconds = 0;
            timeMilliseconds = 0;
            notifyFinishListener();
        }
        setText(timeMillisecondText, timeMilliseconds, true);
        setText(timeSecondText, timeSeconds, false);
        setText(timeMinuteText, timeMinutes, false);
        if (timeHours < 100) {
            setText(timeHourText, timeHours, false);
        } else {
            setText(timeHourText, timeHours, true);
        }
        updateRunningTimeText();
    }

    public void finish() {
        timeline.stop();
        timeline2 = new Timeline();
        timeline2.getKeyFrames().add(
                new KeyFrame(Duration.millis(500),
                        ae -> changeColor()));
        timeline2.setCycleCount(Timeline.INDEFINITE);
        timeline2.play();
    }

    private void notifyFinishListener() {
        finishListeners.forEach(FinishListener::finish);
    }

    void addFinishListener(FinishListener f) {
        Objects.requireNonNull(f, "finishListener is null");
        finishListeners.add(f);
    }

    public void removeFinishListener(FinishListener f) {
        Objects.requireNonNull(f, "finishListener is null");
        finishListeners.remove(f);
    }

    private void changeColor() {
        if (r.getFill().equals(Color.RED)) {
            r.setFill(Color.LIGHTGRAY);
        } else {
            r.setFill(Color.RED);
        }
    }

    void stopChangingColor() {
        if (timeline2 == null) {
            return;
        }
        timeline2.stop();
        r.setFill(Color.LIGHTGRAY);
    }

    void restart() {
        timeline.play();
        isRunning = true;
    }

    private void setText(Text specificTimeText, Integer
            specificTimeProperty, boolean threeDecimals) {
        if (specificTimeProperty < 100) {
            if (threeDecimals) {
                if (specificTimeProperty < 10) {
                    specificTimeText.setText("00" + specificTimeProperty);
                } else {
                    specificTimeText.setText("0" + specificTimeProperty);
                }
            } else if (specificTimeProperty < 10){
                specificTimeText.setText("0" + specificTimeProperty);
            } else {
                specificTimeText.setText(specificTimeProperty.toString());
            }
        } else {
            specificTimeText.setText(specificTimeProperty.toString());
        }
    }

    boolean isAdded() {
        return isAdded;
    }

    private void parseTime(String parseText) {
        int sec;
        int min = 0;
        int hour = 0;
        int separatorIndexTwo = parseText.lastIndexOf(':');
        int separatorIndexOne = parseText.indexOf(':');
        sec = Integer.parseInt(parseText.substring(separatorIndexTwo + 1));
        if (sec >= 60) {
            min += sec / 60;
            sec %= 60;
        }
        min = min + Integer.parseInt(parseText.substring(separatorIndexOne + 1,
                separatorIndexTwo));
        if (min >= 60) {
            hour += min / 60;
            min %= 60;
        }
        hour = hour + Integer.parseInt(parseText.substring(0,
                 separatorIndexOne));
        if (hour > 999) {
            hour = 999;
        }
        timeSecondText.setText(sec < 10 ? "0" + sec : "" + sec);
        timeMinuteText.setText(min < 10 ? "0" + min : "" + min);
        timeHourText.setText(hour < 100 ? "0" + hour : "" + hour);
        timeSeconds = sec;
        timeMinutes = min;
        timeHours = hour;
        timeText.setText(timeHourText.getText() + ":" + timeMinuteText
                .getText() + ":" + timeSecondText.getText());
    }
}
