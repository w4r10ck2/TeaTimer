package main;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.Listener.FinishListener;

public class StartClearButtonPane extends VBox implements FinishListener {
    private TimePane timePane;
    private MainPane mainPane;
    private Button startBtn;
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
        startBtn = new Button("Start");
        startBtn.setStyle("-fx-base: #54e74f;");
        startBtn.setMaxWidth(Double.MAX_VALUE);
        startBtn.setOnAction(event -> {
            if (!timePane.isRunning()) {
                if (isPaused) {
                    timePane.restart();
                    isPaused = false;
                    startBtn.setText("Pause");
                    startBtn.setStyle("-fx-base: #2751f3;");
                } else {
                    timePane.start();
                    startBtn.setText("Pause");
                    startBtn.setStyle("-fx-base: #2751f3;");
                }
            } else {
                startBtn.setText("Continue");
                startBtn.setStyle("-fx-base: #54e74f;");
                timePane.pause();
                isPaused = true;
            }
        });
        startBtn.setMinWidth(100);
        Button clearBtn = new Button("Clear");
        clearBtn.setStyle("-fx-base: #550d18");
        clearBtn.setOnAction(event -> {
                timePane.stop();
                startBtn.setText("Start");
                startBtn.setVisible(true);
                mainPane.stopMusic();
                timePane.stopChangingColor();
        });
        clearBtn.setMinWidth(100);
        Button backBtn = new Button("Back");
        backBtn.setOnAction(event -> {
            timePane.stop();
            mainPane.changeToInputMode();
            startBtn.setText("Start");
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

    @Override
    public void finish() {
        startBtn.setVisible(false);
        mainPane.startMusic();
    }
}
