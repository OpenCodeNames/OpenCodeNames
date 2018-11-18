package io.codenames.data;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class GameHandler {
	
	private static ObservableList <Game> gameList = FXCollections.observableArrayList();
	
	private static GameHandler instance = null;
	
	
	public ObservableList<Game> getGamesList() {
		return gameList;
	}


	public GameHandler() {

	}
	

	public static GameHandler getInstance() {
		if(instance == null)
			instance = new GameHandler();
		return instance;
	}
	
	
}
