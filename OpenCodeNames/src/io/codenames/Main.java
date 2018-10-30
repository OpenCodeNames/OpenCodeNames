package io.codenames;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/sample.fxml"));
        primaryStage.setTitle("CodeName");
        Scene main = new Scene(root, 1000, 600);
        main.getStylesheets().add("/css/style.css");
        primaryStage.setScene(main);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
