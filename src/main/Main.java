package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Timer");

        StackPane root = new StackPane();
        root.getChildren().addAll(new MainPane());
        primaryStage.setScene(new Scene(root, 350, 250));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
