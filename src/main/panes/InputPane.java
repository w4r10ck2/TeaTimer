package main.panes;

import javafx.scene.layout.HBox;

public class InputPane extends HBox{
    private ButtonPane buttonPane;

    public InputPane(MainPane mainPane) {
        buttonPane = new ButtonPane(mainPane);
        getChildren().add(new NumberPane(mainPane));
        getChildren().add(buttonPane);
    }

    public void changeLanguage() {
        buttonPane.changeLanguage();
    }
}
