package main.windows;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.config.Config;
import main.language.Language;
import main.panes.MainPane;
import main.panes.MenuBarTime;

import java.io.File;
import java.io.InputStream;

public class Main extends Application {
    private Language language;
    private MainPane mainPane;
    private Stage primaryStage;
    private VBox root;
    private MenuBarTime menuBar;

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("TeaTimer");
        this.primaryStage = primaryStage;
        root = new VBox();
        primaryStage.setScene(new Scene(root, 375, 250));
        InputStream resource = getClass().getResourceAsStream
                ("/pictures/icon.png");
        if (resource != null) {
            primaryStage.getIcons().add(new Image(resource));
        }
        language = new Language();
        primaryStage.setResizable(false);
        mainPane = new MainPane(language, this, primaryStage);
        createMenu(primaryStage, root);
        root.setSpacing(10);
        root.getChildren().add(1, mainPane);
        loadConfig();
        primaryStage.show();
    }

    private void createMenu(Stage primaryStage, VBox root) {
        menuBar = new MenuBarTime(mainPane);
        menuBar.prefWidthProperty().bind(primaryStage.widthProperty());
        root.getChildren().add(0, menuBar.getMenuBar());
    }


    public void changeLanguage() {
        root.getChildren().remove(0);
        createMenu(primaryStage, root);
    }

    public void disableTimes() {
        menuBar.disableTimes();
    }

    public void activateTimes() {
        menuBar.activateTimes();
    }

    public MenuBarTime getMenuBar() {
        return menuBar;
    }

    private void loadConfig() {
        Config.getProperties();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
