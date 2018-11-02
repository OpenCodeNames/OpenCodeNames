package io.codenames;

import io.codenames.controllers.ViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/LoginView.fxml"));
        primaryStage.setTitle("CodeNames");
        Scene main = new Scene(root, 1000, 600);
        main.getStylesheets().add("/css/style.css");

    	ViewController viewcontroller = ViewController.getInstance(main);
    	primaryStage.setScene(main);
        primaryStage.setResizable(false);
        primaryStage.show();
    	
    	
    }


    public static void main(String[] args) {
        launch(args);
    }
}
