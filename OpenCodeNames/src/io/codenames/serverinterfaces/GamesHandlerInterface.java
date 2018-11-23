package io.codenames.serverinterfaces;

import java.rmi.*;
import java.util.HashMap;
import java.util.LinkedHashMap;

import io.codenames.clientinterfaces.ClientCommandInvokerInterface;

public interface GamesHandlerInterface extends Remote{

    String createGame(String gameName, String creatorName, int numPlayers) throws RemoteException;

	boolean joinGameQueue(String gameName, String playerName, ClientCommandInvokerInterface client ) throws RemoteException;

    boolean leaveGameQueue(String gameID, String playerName) throws RemoteException;

    LinkedHashMap<String, HashMap<String,String>> getGames() throws RemoteException ;

	String getCodeNameOfCard(String gameName, int i) throws RemoteException;

    boolean cardSelected(String gameID, int cardID, String playerName) throws RemoteException;

	boolean placeChatMessage(String gameName, String platerName, String message) throws RemoteException;

	boolean placeHintMessage(String gameName, String playerName, String message) throws RemoteException;
}
