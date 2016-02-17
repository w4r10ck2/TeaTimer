package main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Timer");
        VBox root = new VBox();
        primaryStage.setScene(new Scene(root, 350, 250));
        primaryStage.getIcons().add(new Image(getClass().getResource
                ("Pictures/icon.png").toString()));
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
    }


    public static void main(String[] args) {
        launch(args);
    }
}
