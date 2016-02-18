package main.panes;

import javafx.scene.layout.HBox;

public class InputPane extends HBox{

    public InputPane(MainPane mainPane) {
        getChildren().add(new NumberPane(mainPane));
        getChildren().add(new ButtonPane(mainPane));
    }
}
