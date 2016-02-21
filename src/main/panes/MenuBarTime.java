package main.panes;


import javafx.application.Platform;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import main.windows.Main;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Iterator;
import java.util.ResourceBundle;

public class MenuBarTime extends javafx.scene.control.MenuBar {
    MainPane mainPane;
    MenuBar menuBar;
    Menu preDefTimes;
    Menu changeAlarmItem;

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
        changeAlarmItem = new Menu(resourceBundle.getString
                ("cAlarm"));
        createChangeAlarmTone();
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
        MenuItem detoxTeaTime = new MenuItem(mainPane.getResourceBundle()
                .getString("detox"));
        detoxTeaTime.setOnAction(event -> changeToStartMode("6:00"));
        MenuItem userSpecifiedTime = new MenuItem(resourceBundle.getString
                ("uSpec"));
        userSpecifiedTime.setOnAction(event -> changeToStartMode("3:00"));
        preDefTimes.getItems().addAll(greenTeaTime, blackTeaTime,
                fruitTeaTime, oolongTeaTime, detoxTeaTime, new
                        SeparatorMenuItem(), userSpecifiedTime);
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

    private void createChangeAlarmTone() {
        String music = loadGameObjects();
        String[] musicTitle = music.split("=");
        URI uri = null;
        try {
            uri = Main.class.getResource("/musicfiles/").toURI();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        for (String tmp : musicTitle) {
            String s;
            if (uri != null && uri.getScheme().equals("jar")) {
                s = tmp.substring(tmp.lastIndexOf("/") + 1);
            } else {
                s = tmp.substring(tmp.lastIndexOf(File.separator) + 1);
                tmp = tmp.substring(tmp.indexOf(File.separator + "musicfiles"));
                tmp = tmp.replace("\\", "/");
            }
            String path = tmp;
            MenuItem item = new MenuItem(s);
            item.setOnAction(event -> mainPane.changeMusicFile(path));
            changeAlarmItem.getItems().add(item);
        }
    }

    public String loadGameObjects() {
        URI uri = null;
        try {
            uri = Main.class.getResource("/musicfiles/").toURI();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        Path myPath;
        if (uri != null && uri.getScheme().equals("jar")) {
            FileSystem fileSystem = null;
            try {
                fileSystem = FileSystems.newFileSystem(uri, Collections
                        .<String, Object>emptyMap());
            } catch (IOException e) {
                e.printStackTrace();
            }
            assert fileSystem != null;
            myPath = fileSystem.getPath("/musicfiles/");
        } else {
            assert uri != null;
            myPath = Paths.get(uri);
        }
        String filenames = "";
        try {
            StringBuilder stringBuilder = new StringBuilder();
            Iterator iterator = Files.list(myPath).iterator();
            while (iterator.hasNext()) {
                stringBuilder.append(iterator.next()).append("=");
            }
            filenames = stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filenames;
    }
}
