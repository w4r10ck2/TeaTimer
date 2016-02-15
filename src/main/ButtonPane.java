package main;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class ButtonPane extends VBox {
    private TimePane timePane;
    private MainPane mainPane;

    public ButtonPane(MainPane mainPane) {
        setSpacing(10);
        setPrefWidth(200);
        setPadding(new Insets(0, 20, 10, 20));
        this.timePane = mainPane.getTimePane();
        this.mainPane = mainPane;
        createContent();
    }

    private void createContent() {
        Button setButton = new Button("Set");
        setButton.setOnAction(event -> {
            timePane.calculateTime();
            mainPane.changeToStartMode();
        });
        setButton.setStyle("-fx-base: #54e74f;");
        setButton.setMaxWidth(Double.MAX_VALUE);

        Button clearButton = new Button("Clear");
        clearButton.setOnAction(event -> timePane.clear());
        clearButton.setStyle("-fx-base: #dadee3;");
        clearButton.setMaxWidth(Double.MAX_VALUE);

        getChildren().addAll(setButton, clearButton);
    }
}