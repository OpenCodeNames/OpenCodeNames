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
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;

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
	private NumberAxis xAxis;
	@FXML
	private CategoryAxis yAxis;
    
    @FXML
    private BarChart<String, Integer> barChart;


	
	@FXML
	protected void backAction(ActionEvent event) {
				ViewController viewcontroller = ViewController.getInstance();
				viewcontroller.activate("Menu");
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
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
			
			xAxis.setLabel("Number of Games");
			xAxis.setTickLabelFill(Color.web("#FFFFFF"));
//			xAxis.setTickLabelRotation(90);
			yAxis.setLabel("Performance");
			
			XYChart.Series<String, Integer> series1 = new XYChart.Series<String, Integer>();
			series1.getData().add(new XYChart.Data("Won by Completion",50));
			series1.getData().add(new XYChart.Data("Won Death Card Reveal",20));
			
			
			XYChart.Series<String, Integer> series2 = new XYChart.Series<String, Integer>();
			series2.getData().add(new XYChart.Data("Won by Completion",60));
			series2.getData().add(new XYChart.Data("Won Death Card Reveal",30));
			barChart.getData().addAll(series1, series2);
			
			
			
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

