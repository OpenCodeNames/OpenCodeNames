package io.codenames.controllers;

import java.io.IOException;
import java.net.URL;
import java.rmi.Naming;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import io.codenames.serverinterfaces.GamesHandlerInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import javax.swing.*;

public class CreateGameViewController implements Initializable{

	private GamesHandlerInterface gamehandler;
    private Preferences pref;

    @FXML
    TextField numPlayers;

    @FXML
    TextField gameName;

	/**
	 * Action listener for Create game button
	 * @param event
	 */
	@FXML 
	protected void gameAction(ActionEvent event) {
//		int numberofplayers = Integer.parseInt(numPlayers.getText());
//		if(numberofplayers % 2==0 && numberofplayers >= 4 ) {
		try {
				
		        String creatorName = this.pref.get("userName", null);
                String gameID = gamehandler.createGame(gameName.getText(),creatorName, Integer.parseInt(numPlayers.getText()));
                if(gameID != null ){
                    this.pref.put("gameID",gameID);
                    ViewController viewcontroller = ViewController.getInstance();
                    viewcontroller.addScreen("GameView", FXMLLoader.load(getClass().getResource( "/fxml/LoadingView.fxml" )));
                    viewcontroller.activate("GameView");
                }else{
                    JOptionPane.showMessageDialog(new JFrame(), "GameName Already Exists", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
               
		}
		
		catch (IOException e) {
			e.printStackTrace();
		}catch (RuntimeException e) {
			e.printStackTrace();
		}
//	}else{
//	    JOptionPane.showMessageDialog(new JFrame(), "Number of players can not be below 4 or an Odd number", "Error",
//	            JOptionPane.ERROR_MESSAGE);
//	}
}
	
	/**
	 * Action listener for back button
	 * @param event
	 */
	
	@FXML 
	protected void backAction(ActionEvent event) {
				ViewController viewcontroller = ViewController.getInstance();
				viewcontroller.activate("Menu");
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
        try{
            this.pref = Preferences.userNodeForPackage(io.codenames.Main.class);
            gamehandler = (GamesHandlerInterface) Naming.lookup("rmi://"+pref.get("rmiUri", "localhost")+"/GamesHandler");
        }catch (Exception e){
            System.out.println(e.getMessage());

        }
    }
}
