package io.codenames.serverinterfaces;

import io.codenames.serverdata.Game;

import java.rmi.*;
import java.util.HashMap;
import java.util.LinkedHashMap;

public interface GamesHandlerInterface extends Remote{

	String createGame(String gameName,String creatorName, int numPlayers) throws RemoteException;
    boolean joinGameQueue(String gameID, String playerName) throws RemoteException;
    boolean leaveGameQueue(String gameID, String playerName) throws RemoteException;
    LinkedHashMap<String, HashMap<String,String>> getGames() throws RemoteException ;
    boolean cardSelected(String gameID, int cardID, String playerName) throws RemoteException;
}
