package main.panes;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.Listener.FinishListener;

public class StartClearButtonPane extends VBox implements FinishListener {
    private TimePane timePane;
    private MainPane mainPane;
    private Button startBtn;
    private Button clearBtn;
    private Button backBtn;
    private HBox hbox;
    private boolean isPaused;

    public StartClearButtonPane(MainPane mainPane) {
        this.timePane = mainPane.getTimePane();
        this.mainPane = mainPane;
        this.hbox = new HBox();
        hbox.setSpacing(130);
        hbox.setPrefWidth(300);
        setPadding(new Insets(0, 20, 10, 20));
        setSpacing(10);
        timePane.addFinishListener(this);
        createContent();
    }

    private void createContent() {
        startBtn = new Button(mainPane.getResourceBundle().getString("start"));
        startBtn.setStyle("-fx-base: #54e74f;");
        startBtn.setMaxWidth(Double.MAX_VALUE);
        startBtn.setOnAction(event -> {
            if (!timePane.isRunning()) {
                if (isPaused) {
                    timePane.restart();
                    isPaused = false;
                    startBtn.setText(mainPane.getResourceBundle().getString
                             ("pause"));
                    startBtn.setStyle("-fx-base: #2751f3;");
                } else {
                    timePane.start();
                    startBtn.setText(mainPane.getResourceBundle().getString
                             ("pause"));
                    startBtn.setStyle("-fx-base: #2751f3;");
                }
            } else {
                startBtn.setText(mainPane.getResourceBundle().getString
                         ("continue"));
                startBtn.setStyle("-fx-base: #54e74f;");
                timePane.pause();
                isPaused = true;
            }
        });
        startBtn.setMinWidth(100);
        clearBtn = new Button(mainPane.getResourceBundle().getString("clear"));
        clearBtn.setStyle("-fx-base: #550d18");
        clearBtn.setOnAction(event -> {
                timePane.stop();
                startBtn.setText(mainPane.getResourceBundle().getString
                        ("start"));
                startBtn.setVisible(true);
                mainPane.stopMusic();
                timePane.stopChangingColor();
        });
        clearBtn.setMinWidth(100);
        backBtn = new Button(mainPane.getResourceBundle().getString("back"));
        backBtn.setOnAction(event -> {
            timePane.stop();
            mainPane.changeToInputMode();
            startBtn.setText(mainPane.getResourceBundle().getString("start"));
            startBtn.setVisible(true);
            mainPane.stopMusic();
            timePane.stopChangingColor();
            isPaused = false;
            timePane.revert();
        });
        backBtn.setStyle("-fx-base: #dadee3;");
        backBtn.setMinWidth(100);
        hbox.getChildren().addAll(startBtn, clearBtn);
        getChildren().addAll(hbox, backBtn);
    }

    public void changeLanguage() {
        backBtn.setText(mainPane.getResourceBundle().getString("back"));
        clearBtn.setText(mainPane.getResourceBundle().getString("clear"));
        if (timePane.isRunning()) {
            startBtn.setText(mainPane.getResourceBundle().getString("pause"));
        } else if (!timePane.isRunning() && isPaused) {
            startBtn.setText(mainPane.getResourceBundle().getString
                    ("continue"));
        } else {
            startBtn.setText(mainPane.getResourceBundle().getString
                    ("start"));
        }
    }

    @Override
    public void finish() {
        startBtn.setVisible(false);
        mainPane.startMusic();
    }
}
