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
import main.panes.MainPane;

import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("TeaTimer");
        VBox root = new VBox();
        primaryStage.setScene(new Scene(root, 350, 250));
        final URL resource = getClass().getResource
                ("../pictures/icon.png");
        if (resource != null) {
            primaryStage.getIcons().add(new Image(resource.toString()));
        }
        primaryStage.setResizable(false);
        createMenu(primaryStage, root);
        root.setSpacing(10);
        root.getChildren().addAll(new MainPane());
        primaryStage.show();
    }

    private void createMenu(Stage primaryStage, VBox root) {
        MenuBar menuBar = new MenuBar();
        menuBar.prefWidthProperty().bind(primaryStage.widthProperty());
        root.getChildren().add(menuBar);
        Menu fileMenu = new Menu("File");
        Menu settingsMenu = new Menu("Settings");
        Menu helpMenu = new Menu("Help");
        menuBar.getMenus().addAll(fileMenu, settingsMenu, helpMenu);

        MenuItem closeItem = new MenuItem("Exit");
        closeItem.setOnAction(event -> Platform.exit());
        fileMenu.getItems().add(closeItem);

        MenuItem aboutItem = new MenuItem("About TeaTimer");
        MenuItem reportItem = new MenuItem("Report Bug");
        MenuItem supportItem = new MenuItem("Support");
        helpMenu.getItems().addAll(reportItem, supportItem, new
                SeparatorMenuItem(), aboutItem);

        MenuItem importMusicItem = new MenuItem("Import music");
        MenuItem changeAlarmItem = new MenuItem("Change Alarm");
        MenuItem changePreTimeItem = new MenuItem("Change Times");
        MenuItem uiItem = new MenuItem("User Interface");
        settingsMenu.getItems().addAll(importMusicItem, changeAlarmItem, new
                SeparatorMenuItem(), changePreTimeItem, new SeparatorMenuItem
                (), uiItem);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
