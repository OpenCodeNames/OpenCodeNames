package io.codenames.serverinterfaces;

import java.rmi.*;

public interface GamesHandlerInterface extends Remote{

	public boolean createGame(String gameName, String createrName, int playerCount) throws RemoteException;
	
	public boolean joinGame(String gameName, String playerName) throws RemoteException;
	
	public String getCodeNameOfCard(String gameName, int i) throws RemoteException;
	
	public boolean revealCard(String gameName, String playerName, int i) throws RemoteException;
	
	public boolean placeChatMessage(String gameName, String platerName, String message) throws RemoteException;
	
	public boolean placeHintMessage(String gameName, String playerName, String message) throws RemoteException;
}
