package io.codenames.serverinterfaces;

import java.rmi.*;

public interface GamesHandlerInterface extends Remote{

	public boolean createGame(String gameName, String createrName, int playerCount);
	
	public boolean joinGame(String gameName, String playerName);
	
	public String getCodeNameOfCard(String gameName, int i);
	
	public boolean revealCard(String gameName, String playerName, int i);
	
	public boolean placeChatMessage(String gameName, String platerName, String message);
	
	public boolean placeHintMessage(String gameName, String playerName, String message);
}
