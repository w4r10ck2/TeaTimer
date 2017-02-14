package main.panes;


import javafx.application.Platform;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import main.config.Config;
import main.windows.Main;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.*;
import java.nio.file.FileSystem;
import java.nio.file.FileSystemAlreadyExistsException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Iterator;
import java.util.Properties;
import java.util.ResourceBundle;

public class MenuBarTime extends javafx.scene.control.MenuBar {
    private MainPane mainPane;
    private MenuBar menuBar;
    private Menu preDefTimes;
    private Menu changeAlarmItem;
    private Menu changeAlarmCount;

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
        aboutItem.setOnAction(event -> {
            try {
                URI uri = new URI("https://github.com/w4r10ck2/TeaTimer");
                Desktop.getDesktop().browse(uri);
            } catch (URISyntaxException | IOException e) {
                aboutItem.setDisable(true);
            }
        });

        MenuItem reportItem = new MenuItem(resourceBundle.getString
                ("report"));
        reportItem.setOnAction(event -> {
            Desktop desktop;
            if (Desktop.isDesktopSupported()
                    && (desktop = Desktop.getDesktop()).isSupported(Desktop.Action.MAIL)) {
                try {
                    URI mailTo = new URI("mailto:andreas.vogt3@gmail.com?subject=TeaTimer-Bug");
                    desktop.mail(mailTo);
                } catch (URISyntaxException | IOException e) {
                    reportItem.setDisable(true);
                }
            }
        });

        helpMenu.getItems().addAll(reportItem, new
                SeparatorMenuItem(), aboutItem);
        changeAlarmItem = new Menu(resourceBundle.getString
                ("cAlarm"));
        createChangeAlarmTone();
        MenuItem changePreTimeItem = new MenuItem(resourceBundle.getString
                ("cTimes"));
        changePreTimeItem.setOnAction(event -> mainPane
                .changeToTimeEditInterface());
        changeAlarmCount = new Menu(mainPane.getResourceBundle()
                .getString("changeAlarmCount"));
        createChangeAlarmCount();
        MenuItem uiItem = new MenuItem(resourceBundle.getString
                ("ui"));
        uiItem.setOnAction(event -> mainPane.changeToEditUserInterface());
        settingsMenu.getItems().addAll(changeAlarmItem, changeAlarmCount, new
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
        Properties properties = Config.getProperties();
        MenuItem greenTeaTime = new MenuItem(resourceBundle.getString("green"));
        greenTeaTime.setOnAction(event -> changeToStartMode(properties.getProperty("greenTime")));
        MenuItem fruitTeaTime = new MenuItem(resourceBundle.getString("fruit"));
        fruitTeaTime.setOnAction(event -> changeToStartMode(properties.getProperty("fruitsTime")));
        MenuItem blackTeaTime = new MenuItem(resourceBundle.getString("black"));
        blackTeaTime.setOnAction(event -> changeToStartMode(properties.getProperty("blackTime")));
        MenuItem oolongTeaTime = new MenuItem(resourceBundle.getString
                ("oolong"));
        oolongTeaTime.setOnAction(event -> changeToStartMode(properties.getProperty("oolongTime")));
        MenuItem detoxTeaTime = new MenuItem(mainPane.getResourceBundle()
                .getString("detox"));
        detoxTeaTime.setOnAction(event -> changeToStartMode(properties.getProperty("detoxTime")));
        MenuItem userSpecifiedTime = new MenuItem(resourceBundle.getString
                ("uSpec"));
        userSpecifiedTime.setOnAction(event -> changeToStartMode(properties.getProperty("uSpecTime")));
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
        String music = loadObjects();
        String[] musicTitle = music.split("=");
        URI uri = null;
        try {
            uri = Main.class.getResource("/resources/musicfiles/").toURI();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        for (String tmp : musicTitle) {
            String s;
            if (uri != null && uri.getScheme().equals("jar")) {
                s = tmp.substring(tmp.lastIndexOf("/") + 1);
            } else {
                s = tmp.substring(tmp.lastIndexOf(File.separator) + 1);
                tmp = tmp.substring(tmp.indexOf(File.separator + "resources" + File.separator + "musicfiles"));
                tmp = tmp.replace("\\", "/");
            }
            String path = tmp;
            s = s.substring(0, s.indexOf("."));
            Menu menu = new Menu(s);
            MenuItem selectItem = new MenuItem(mainPane.getResourceBundle()
                    .getString("use"));
            MenuItem playShort = new MenuItem(mainPane.getResourceBundle()
                    .getString("playShort"));
            selectItem.setOnAction(event -> {
                        try {
                            mainPane.changeMusicFile(path);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
            );
            playShort.setOnAction(event -> mainPane.playAlarmShort(path));
            menu.getItems().addAll(playShort, selectItem);
            changeAlarmItem.getItems().add(menu);
        }
    }

    private String loadObjects() {
        URI uri = null;
        try {
            uri = Main.class.getResource("/resources/musicfiles/").toURI();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        Path myPath;
        if (uri != null && uri.getScheme().equals("jar")) {
            FileSystem fileSystem = null;
            try {
                fileSystem = FileSystems.newFileSystem(uri, Collections
                        .emptyMap());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (FileSystemAlreadyExistsException f) {
                fileSystem = FileSystems.getFileSystem(uri);
            }
            assert fileSystem != null;
            myPath = fileSystem.getPath("/resources/musicfiles/");
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

    private void createChangeAlarmCount() {
        for (int i = 0; i < 10; i++) {
            final int counter = i + 1;
            MenuItem item = new MenuItem("" + counter);
            changeAlarmCount.getItems().add(item);
            item.setOnAction(event -> {
                try {
                    mainPane.setAlarmCount(counter);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        String indefinite = mainPane
                .getResourceBundle().getString("indefinite");
        MenuItem item = new MenuItem(indefinite);
        item.setOnAction(event -> {
            try {
                mainPane.setAlarmCount(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        changeAlarmCount.getItems().add(item);
    }
}
