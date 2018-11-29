package io.codenames.controllers;

import java.io.IOException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import com.jfoenix.controls.JFXButton;

import io.codenames.data.Game;
import io.codenames.serverinterfaces.GamesHandlerInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class PlayerConnectViewController implements Initializable {

	private GamesHandlerInterface gamehandler;
	private Preferences pref;
	private ObservableList<Game> gameList = FXCollections.observableArrayList();

	@FXML
	private TableView<Game> gamesTable;
	
	@FXML
	private TableColumn<Game, String> creatorCol;
	
	@FXML
	private TableColumn<Game, String> gameNameCol;
	
	@FXML
	private TableColumn<Game, String> seatAvailableCol;
	
	
	
	@FXML
	private JFXButton joinbtn;
	
	@FXML
	private JFXButton resetBtn;
	
	@FXML 
	private Label creatorLabel;
	
	@FXML 
	private Label creatorName;
	

	@FXML 
	private Label gameLabel;
	
	@FXML 
	private Label gameName;
	
	private String gameID;
	
	/**
	 * action listener for table row select
	 * @param newSelection
	 */
	public void handleGameSelected(Game newSelection) {
		if(joinbtn.isVisible() == false) {
			joinbtn.setVisible(true);
			creatorLabel.setVisible(true);
			creatorName.setVisible(true);
			gameLabel.setVisible(true);
			gameName.setVisible(true);
			resetBtn.setVisible(true);
		}
		
		creatorName.setText(newSelection.getCreator());
		gameName.setText(newSelection.getName());
		gameID = newSelection.getGameID();
	}
	
	/**
	 * Action listener for back button
	 * @param event
	 */
	@FXML 
	public void backAction(ActionEvent event) {
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
	
	/**
	 * Action listener for join button
	 * @param event
	 */
	
	@FXML 
	protected void joinGame(ActionEvent event) {
		try {
			this.pref.put("gameID",gameID);
			ViewController viewcontroller = ViewController.getInstance();
			viewcontroller.addScreen("GameLoader", FXMLLoader.load(getClass().getResource( "/fxml/LoadingView.fxml" )));
			viewcontroller.activate("GameLoader");
		}
		catch (IOException e) {
			e.printStackTrace();
		}catch (RuntimeException e) {
			e.printStackTrace();
		}
	}

	private void loadGameList(){
		try {
			LinkedHashMap<String, HashMap<String,String>> gameMap = gamehandler.getGames();
			Iterator<HashMap<String,String>> it = gameMap.values().iterator();
			gameList.clear();
			while (it.hasNext())
			{
                HashMap<String,String> itGame = it.next();

				gameList.add(new Game( itGame.get("gameID"), itGame.get("name"),itGame.get("creator"), Integer.parseInt(itGame.get("seats"))));
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	@FXML 
	protected void reloadGameList(ActionEvent event) {
		this.loadGameList();
	}

	/**
	 * Initialize Player Connection View 
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		joinbtn.setVisible(false);
		creatorLabel.setVisible(false);
		creatorName.setVisible(false);
		gameLabel.setVisible(false);
		gameName.setVisible(false);
		resetBtn.setVisible(false);

		try{
			this.pref = Preferences.userNodeForPackage(io.codenames.Main.class);
			gamehandler = (GamesHandlerInterface) Naming.lookup("rmi://"+pref.get("rmiUri", "localhost")+"/GamesHandler");
		}catch (Exception e){
			System.out.println(e.getMessage());
		}

		gameNameCol.setCellValueFactory(new PropertyValueFactory<Game, String>("name"));
		creatorCol.setCellValueFactory(new PropertyValueFactory<Game, String>("creator"));
		seatAvailableCol.setCellValueFactory(new PropertyValueFactory<Game, String>("seats"));
		
		gamesTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> handleGameSelected((Game)newSelection));
        loadGameList();
		gamesTable.setItems(gameList);
	}
}
