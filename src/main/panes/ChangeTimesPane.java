package main.panes;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import main.config.Config;

import java.io.IOException;
import java.util.Properties;

class ChangeTimesPane extends GridPane {
    private MainPane mainPane;

    ChangeTimesPane(MainPane mainPane) {
        this.mainPane = mainPane;
        createContent();
    }

    private void createContent() {
        Label toLabel = new Label(mainPane.getResourceBundle().getString("to"));
        Label nameLabel = new Label(mainPane.getResourceBundle().getString
                ("name"));
        Label fromLabel = new Label(mainPane.getResourceBundle().getString
                ("from"));
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(33);
        column1.setMinWidth(mainPane.getWidth() * 0.33);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(33);
        column2.setMinWidth(mainPane.getWidth() * 0.33);
        ColumnConstraints column3 = new ColumnConstraints();
        column3.setPercentWidth(33);
        column3.setMinWidth(mainPane.getWidth() * 0.33);
        getColumnConstraints().addAll(column1, column2, column3);
        setHgap(1);
        setVgap(1);
        setPadding(new Insets(0, 0, 0, 0));
        add(nameLabel, 0, 0);
        add(fromLabel, 1, 0);
        add(toLabel, 2, 0);
        add(new Separator(Orientation.HORIZONTAL), 0, 1, 3, 1);
        Properties properties = Config.getProperties();

        Label greenTeaLabel = new Label(mainPane.getResourceBundle()
                .getString("green"));
        Label greenTeaOldTime = new Label(properties.getProperty("greenTime"));
        ComboBox<String> greenTeaNewTime = new ComboBox<>();

        add(greenTeaLabel, 0, 2);
        add(greenTeaOldTime, 1, 2);
        add(greenTeaNewTime, 2, 2);

        Label blackTeaLabel = new Label(mainPane.getResourceBundle()
                .getString("black"));
        Label blackTeaOldTime = new Label(properties.getProperty("blackTime"));
        ComboBox<String> blackTeaNewTime = new ComboBox<>();

        add(blackTeaLabel, 0, 3);
        add(blackTeaOldTime, 1, 3);
        add(blackTeaNewTime, 2, 3);
        Label fruitsTeaLabel = new Label(mainPane.getResourceBundle()
                .getString("fruit"));
        Label fruitsTeaOldTime = new Label(properties.getProperty("fruitsTime"));
        ComboBox<String> fruitsTeaNewTime = new ComboBox<>();

        add(fruitsTeaLabel, 0, 4);
        add(fruitsTeaOldTime, 1, 4);
        add(fruitsTeaNewTime, 2, 4);

        Label oolongTeaLabel = new Label(mainPane.getResourceBundle()
                .getString("oolong"));
        Label oolongTeaOldTime = new Label(properties.getProperty("oolongTime"));
        ComboBox<String> oolongTeaNewTime = new ComboBox<>();

        add(oolongTeaLabel, 0, 5);
        add(oolongTeaOldTime, 1, 5);
        add(oolongTeaNewTime, 2, 5);
        Label detoxTeaLabel = new Label(mainPane.getResourceBundle()
                .getString("detox"));
        Label detoxTeaOldTime = new Label(properties.getProperty("detoxTime"));
        ComboBox<String> detoxTeaNewTime = new ComboBox<>();

        add(detoxTeaLabel, 0, 6);
        add(detoxTeaOldTime, 1, 6);
        add(detoxTeaNewTime, 2, 6);

        Label uSpecTeaLabel = new Label(mainPane.getResourceBundle()
                .getString("uSpec"));
        Label uSpecOldTime = new Label(properties.getProperty("uSpecTime"));
        ComboBox<String> uSpecNewTime = new ComboBox<>();

        add(uSpecTeaLabel, 0, 7);
        add(uSpecOldTime, 1, 7);
        add(uSpecNewTime, 2, 7);

        add(new Separator(Orientation.HORIZONTAL), 0, 8, 3, 1);

        Button backBtn = new Button(mainPane.getResourceBundle()
                .getString("back"));
        backBtn.setOnAction(event -> mainPane.changeToInputMode());
        Button saveBtn = new Button(mainPane.getResourceBundle()
                .getString("save"));
        saveBtn.setStyle("-fx-background-color: #54e74f");
        saveBtn.setOnAction(event -> {
            Properties newTimes = new Properties();
            if (greenTeaNewTime.getValue() != null) {
                newTimes.setProperty("greenTime", greenTeaNewTime.getValue());
            }
            if (blackTeaNewTime.getValue() != null) {
                newTimes.setProperty("blackTime", blackTeaNewTime.getValue());
            }
            if (detoxTeaNewTime.getValue() != null) {
                newTimes.setProperty("detoxTime", detoxTeaNewTime.getValue());
            }
            if (fruitsTeaNewTime.getValue() != null) {
                newTimes.setProperty("fruitsTime", fruitsTeaNewTime.getValue());
            }
            if (uSpecNewTime.getValue() != null) {
                newTimes.setProperty("uspecTime", uSpecNewTime.getValue());
            }
            if (oolongTeaNewTime.getValue() != null) {
                newTimes.setProperty("oolongTime", oolongTeaNewTime.getValue());
            }
            try {
                Config.updateProperties(newTimes);
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(-2);
            }
            mainPane.changeToInputMode();
        });

        add(saveBtn, 2, 9);
        add(backBtn, 0, 9);

        for (Node node : getChildren()) {
            GridPane.setHalignment(node, HPos.CENTER);
        }
        fillBox(greenTeaNewTime);
        fillBox(blackTeaNewTime);
        fillBox(fruitsTeaNewTime);
        fillBox(detoxTeaNewTime);
        fillBox(uSpecNewTime);
        fillBox(oolongTeaNewTime);
    }

    private void fillBox(ComboBox<String> box) {
        for (int i = 0; i <= 59; i++) {
            for (int j = 0; j < 2; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }
                box.getItems().add(((i < 10) ? "0" + i : "" + i) + ((j ==
                        0) ? ":00" : ":30"));
            }
        }
    }
}
