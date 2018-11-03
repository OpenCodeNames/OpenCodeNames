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
		for(int i = 0; i<10; i++) {
			gameList.add(i, new Game("Game "+i, "Player "+i , i*2));
		}
	}
	

	public static GameHandler getInstance() {
		if(instance == null)
			instance = new GameHandler();
		return instance;
	}
	
	
}
