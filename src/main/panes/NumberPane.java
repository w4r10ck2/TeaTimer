package main.panes;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class NumberPane extends GridPane{
    private TimePane timePane;

    public NumberPane(MainPane mainPane) {
        this.timePane = mainPane.getTimePane();
        createContent();
    }

    private void createContent() {
        setHgap(10);
        setVgap(10);
        setPadding(new Insets(0, 10, 0, 10));
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 5; j++) {
                Button button = new Button(((Integer)(i == 1 ? j : 5 + j))
                        .toString());
                button.setOnAction(event ->
                    timePane.add(Integer.parseInt(button.getText()))
                );
                button.setMaxWidth(Double.MAX_VALUE);
                button.setMinWidth(30);
                button.setStyle("-fx-base: #54e74f;");
                add(button, j, i);
            }
        }
    }
}
