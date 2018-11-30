package io.codenames.serverinterfaces;

import java.rmi.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import io.codenames.clientinterfaces.ClientCommandInvokerInterface;

public interface GamesHandlerInterface extends Remote{

    String createGame(String gameID, String creatorName, int numPlayers) throws RemoteException;

	boolean joinGameQueue(String gameID, String playerName, ClientCommandInvokerInterface client ) throws RemoteException;

    boolean leaveGameQueue(String gameID, String playerName) throws RemoteException;

    LinkedHashMap<String, HashMap<String,String>> getGames() throws RemoteException;

	String getCodeNameOfCard(String gameID, int i) throws RemoteException;

	ArrayList<String> getCardsArray(String gameID, String playerName) throws RemoteException ;

    boolean cardSelected(String gameID, int turnCount, String code, String playerName) throws RemoteException;

	boolean placeChatMessage(String gameID, String platerName, String message) throws RemoteException;

	boolean placeHintMessage(String gameID, int turnCount, String playerName, String message) throws RemoteException;

	int getTeamOfPlayerInGame(String gameID, String playerName) throws RemoteException;

	int getTurnOfGame(String gameID, String playerName) throws  RemoteException;

	int getTurnCountOfGame(String gameID, String playerName) throws RemoteException;
}
