package main.panes;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

public class InputPane extends GridPane {
    private ButtonPane buttonPane;

    public InputPane(MainPane mainPane) {
        buttonPane = new ButtonPane(mainPane);
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(60);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(40);
        getColumnConstraints().addAll(column1, column2);
        add(new NumberPane(mainPane), 0, 0);
        add(buttonPane, 1, 0);
    }

    public void changeLanguage() {
        buttonPane.changeLanguage();
    }
}
