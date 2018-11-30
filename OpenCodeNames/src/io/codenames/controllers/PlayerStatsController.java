package io.codenames.controllers;

import java.io.IOException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import io.codenames.data.Game;
import io.codenames.serverdata.Player;
import io.codenames.serverinterfaces.GamesHandlerInterface;
import io.codenames.serverinterfaces.PlayersHandlerInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Action listener for back button
 * @param event
 */

public class PlayerStatsController implements Initializable{
	
	
	private PlayersHandlerInterface playerhandler;
	private Preferences pref;
	
	@FXML
	private Label playerName;
	
	@FXML
	private Label correctCards;
	
	@FXML
	private Label wrongCards;
	
	@FXML
	private Label deathCards;
	
	@FXML
	private Label winRate;
	
	@FXML
	private Label avgCardReveals;
	
	@FXML
	private Label avgWCardReveals;
	
	@FXML
	private Label avgDCardReveals;
	
	@FXML
	private Label avgWinRate;
	
	@FXML
	protected void backAction(ActionEvent event) {
		try {
				ViewController viewcontroller = ViewController.getInstance();
				viewcontroller.addScreen("Back", FXMLLoader.load(getClass().getResource( "/fxml/MenuView.fxml" )));
				viewcontroller.activate("Back");
		}
		catch (IOException e) {
			e.printStackTrace();
		}catch (RuntimeException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		try{
			this.pref = Preferences.userNodeForPackage(io.codenames.Main.class);
			playerhandler = (PlayersHandlerInterface) Naming.lookup("rmi://"+pref.get("rmiUri", "localhost")+"/PlayersHandler");
		}catch (Exception e){
			System.out.println(e.getMessage());
		}

		playerName.setText(this.pref.get("userName",""));
		String playerName = this.pref.get("userName","");
		try {
			
			Player player = playerhandler.getPlayer(playerName);
			int cardReviled = player.getCardsReviled();
			correctCards.setText(player.getCorrectReviles()+"/"+cardReviled);
			wrongCards.setText(player.getIncorrectReviles()+"/"+cardReviled);
			deathCards.setText(player.getDeathCards()+"/"+cardReviled);
			winRate.setText(player.getGamesWon()+"/"+player.getNumGames());
			
			int avgCardReviled = 0;
			
			avgCardReveals.setText(player.getCorrectReviles()+"/"+avgCardReviled);
			avgWCardReveals.setText(player.getIncorrectReviles()+"/"+avgCardReviled);
			avgDCardReveals.setText(player.getDeathCards()+"/"+avgCardReviled);
			avgWinRate.setText(player.getGamesWon()+"/"+player.getNumGames());
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

