package main.panes;


import javafx.application.Platform;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

import java.util.ResourceBundle;

public class MenuBarTime extends javafx.scene.control.MenuBar {
    MainPane mainPane;
    MenuBar menuBar;
    Menu preDefTimes;

    public MenuBarTime(MainPane mainPane) {
        this.mainPane = mainPane;
        menuBar = new MenuBar();
        createContent();
    }

    private void createContent() {
        ResourceBundle resourceBundle = mainPane.getResourceBundle();
        Menu fileMenu = new Menu(resourceBundle.getString
                ("file"));
        Menu settingsMenu = new Menu(resourceBundle.getString
                ("settings"));
        preDefTimes = new Menu(resourceBundle.getString
                ("preDefTimes"));
        Menu helpMenu = new Menu(resourceBundle.getString
                ("help"));
        menuBar.getMenus().addAll(fileMenu, preDefTimes, settingsMenu,
                helpMenu);

        MenuItem closeItem = new MenuItem(resourceBundle.getString
                ("exit"));
        closeItem.setOnAction(event -> Platform.exit());
        fileMenu.getItems().add(closeItem);

        MenuItem aboutItem = new MenuItem(resourceBundle.getString
                ("about"));
        MenuItem reportItem = new MenuItem(resourceBundle.getString
                ("report"));
        MenuItem supportItem = new MenuItem(resourceBundle.getString
                ("support"));
        helpMenu.getItems().addAll(reportItem, supportItem, new
                SeparatorMenuItem(), aboutItem);

        MenuItem importMusicItem = new MenuItem(resourceBundle.getString
                ("importMusic"));
        MenuItem changeAlarmItem = new MenuItem(resourceBundle.getString
                ("cAlarm"));
        MenuItem changePreTimeItem = new MenuItem(resourceBundle.getString
                ("cTimes"));
        MenuItem uiItem = new MenuItem(resourceBundle.getString
                ("ui"));
        uiItem.setOnAction(event -> mainPane.changeToEditUserInterface());
        settingsMenu.getItems().addAll(importMusicItem, changeAlarmItem, new
                SeparatorMenuItem(), changePreTimeItem, new SeparatorMenuItem
                (), uiItem);
        createPreDefTimesSettings();
    }

    public MenuBar getMenuBar() {
        return menuBar;
    }

    public void disableTimes() {
        for (MenuItem menuItem : preDefTimes.getItems()) {
            menuItem.setDisable(true);
        }
    }

    public void activateTimes() {
        for (MenuItem menuItem : preDefTimes.getItems()) {
            menuItem.setDisable(false);
        }
    }

    private void createPreDefTimesSettings() {
        ResourceBundle resourceBundle = mainPane.getResourceBundle();
        MenuItem greenTeaTime = new MenuItem(resourceBundle.getString("green"));
        greenTeaTime.setOnAction(event -> changeToStartMode("2:00"));
        MenuItem fruitTeaTime = new MenuItem(resourceBundle.getString("fruit"));
        fruitTeaTime.setOnAction(event -> changeToStartMode("10:00"));
        MenuItem blackTeaTime = new MenuItem(resourceBundle.getString("black"));
        blackTeaTime.setOnAction(event -> changeToStartMode("4:00"));
        MenuItem oolongTeaTime = new MenuItem(resourceBundle.getString
                ("oolong"));
        oolongTeaTime.setOnAction(event -> changeToStartMode("2:00"));
        MenuItem userSpecifiedTime = new MenuItem(resourceBundle.getString
                ("uSpec"));
        userSpecifiedTime.setOnAction(event -> changeToStartMode("3:00"));
        preDefTimes.getItems().addAll(greenTeaTime, blackTeaTime,
                fruitTeaTime, oolongTeaTime, new SeparatorMenuItem(),
                userSpecifiedTime);
    }

    private void changeToStartMode(String time) {
        TimePane timePane = mainPane.getTimePane();
        timePane.clear();
        char[] minutes = time.substring(0, time.indexOf(":")).toCharArray();
        char[] seconds = time.substring(time.indexOf(":") + 1).toCharArray();
        for (char c : minutes) {
            addTimes(c, timePane);
        }
        for (char c : seconds) {
            addTimes(c, timePane);
        }
        mainPane.getTimePane().calculateTime();
        mainPane.changeToStartMode();
    }

    private void addTimes(char time, TimePane timePane) {
        switch (time) {
            case '0':
                timePane.add(0);
                break;
            case '1':
                timePane.add(1);
                break;
            case '2':
                timePane.add(2);
                break;
            case '3':
                timePane.add(3);
                break;
            case '4':
                timePane.add(4);
                break;
            case '5':
                timePane.add(5);
                break;
            case '6':
                timePane.add(6);
                break;
            case '7':
                timePane.add(7);
                break;
            case '8':
                timePane.add(8);
                break;
            case '9':
                timePane.add(9);
                break;
            default:
                throw new IllegalArgumentException("You shouldn't have " +
                        "done that");

        }
    }
}
