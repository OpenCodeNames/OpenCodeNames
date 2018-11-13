package io.codenames;

import java.util.prefs.Preferences;

import javax.swing.JOptionPane;

import io.codenames.controllers.ViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	Preferences pref;
    @Override
    public void start(Stage primaryStage) throws Exception{
    	this.pref = Preferences.userNodeForPackage(io.codenames.Main.class);
    	String rmiUri = JOptionPane.showInputDialog("Enter RMI Server IP",pref.get("rmiUri", ""));
        this.pref.put("rmiUri", rmiUri);
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
