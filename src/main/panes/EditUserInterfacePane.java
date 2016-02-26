package main.panes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;

import java.util.Locale;


public class EditUserInterfacePane extends VBox{
    private MainPane mainPane;
    private Button backBtn;

    public EditUserInterfacePane(MainPane mainPane) {
        this.mainPane = mainPane;
        setSpacing(2);
        setPadding(new Insets(0, 0, 0, 0));
        createContent();
    }

    private void createContent() {
        ObservableList<String> boxText =
                FXCollections.observableArrayList(
                        "Deutsch",
                        "English"
                );
        ComboBox<String> languageBox = new ComboBox<>(boxText);
        if (mainPane.getLanguage().getLanguage().equals("de")) {
            languageBox.setValue("Deutsch");
        } else if (mainPane.getLanguage().getLanguage().equals("en")) {
            languageBox.setValue("English");
        }
        languageBox.valueProperty().addListener((observable, oldValue,
                                                 newValue) -> {
            changeLanguage(newValue);
        });
        CheckBox fullscreenBtn = new CheckBox(mainPane.getResourceBundle()
                .getString("fullscreen"));
        fullscreenBtn.setOnAction(event -> mainPane.setFullscreen
                (fullscreenBtn.isSelected()));

        backBtn = new Button(mainPane.getResourceBundle().getString
                ("back"));
        backBtn.setOnAction(event -> mainPane.changeToInputMode());
        getChildren().addAll(languageBox, fullscreenBtn, backBtn);
    }

    public void setBackBtnEvent(EventHandler<ActionEvent> eventHandler) {
        backBtn.setOnAction(eventHandler);
    }

    private void changeLanguage(String language) {
        switch (language) {
            case "Deutsch":
                mainPane.setLanguage(new Locale("de"));
                break;
            case "English":
                mainPane.setLanguage(new Locale("en"));
                break;
            default:
                throw new IllegalArgumentException();
        }
        backBtn.setText(mainPane.getResourceBundle().getString("back"));
        mainPane.changeLanguage();
    }
}
