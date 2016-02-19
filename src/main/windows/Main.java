package main.windows;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.language.Language;
import main.panes.MainPane;

import java.net.URL;
import java.util.ResourceBundle;

public class Main extends Application {
    Language language;
    MainPane mainPane;
    Stage primaryStage;
    VBox root;

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("TeaTimer");
        this.primaryStage = primaryStage;
        root = new VBox();
        primaryStage.setScene(new Scene(root, 350, 250));
        final URL resource = getClass().getResource
                ("../pictures/icon.png");
        if (resource != null) {
            primaryStage.getIcons().add(new Image(resource.toString()));
        }
        language = new Language();
        primaryStage.setResizable(false);
        createMenu(primaryStage, root);
        root.setSpacing(10);
        mainPane = new MainPane(language, this);
        root.getChildren().addAll(mainPane);
        primaryStage.show();
    }

    private void createMenu(Stage primaryStage, VBox root) {
        MenuBar menuBar = new MenuBar();
        menuBar.prefWidthProperty().bind(primaryStage.widthProperty());
        root.getChildren().add(0, menuBar);
        ResourceBundle resourceBundle = language.getResourceBundle();
        Menu fileMenu = new Menu(resourceBundle.getString
                ("file"));
        Menu settingsMenu = new Menu(resourceBundle.getString
                ("settings"));
        Menu preDefTimes = new Menu(resourceBundle.getString
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
    }

    public void changeLanguage() {
        root.getChildren().remove(0);
        createMenu(primaryStage, root);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
