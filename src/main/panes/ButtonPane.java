package main.panes;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class ButtonPane extends VBox {
    private TimePane timePane;
    private MainPane mainPane;
    private Button setButton;
    private Button clearButton;

    public ButtonPane(MainPane mainPane) {
        setSpacing(10);
        setPrefWidth(200);
        setPadding(new Insets(0, 20, 10, 20));
        this.timePane = mainPane.getTimePane();
        this.mainPane = mainPane;
        createContent();
    }

    private void createContent() {
        setButton = new Button(mainPane.getResourceBundle()
                .getString("set"));
        setButton.setOnAction(event -> {
            if (timePane.isAdded()) {
                timePane.calculateTime();
                mainPane.changeToStartMode();
            }
        });
        setButton.setStyle("-fx-base: #54e74f;");
        setButton.setMaxWidth(Double.MAX_VALUE);

        clearButton = new Button(mainPane.getResourceBundle()
                .getString("clear"));
        clearButton.setOnAction(event -> timePane.clear());
        clearButton.setStyle("-fx-base: #dadee3;");
        clearButton.setMaxWidth(Double.MAX_VALUE);

        getChildren().addAll(setButton, clearButton);
    }

    public void changeLanguage() {
        clearButton.setText(mainPane.getResourceBundle()
                .getString("clear"));
        setButton.setText(mainPane.getResourceBundle()
                .getString("set"));
    }
}
