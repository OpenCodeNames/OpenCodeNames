package io.codenames.serverdata;

import java.rmi.*;
import java.rmi.server.*;

import io.codenames.serverinterfaces.GamesHandlerInterface;

public class GamesHandler extends UnicastRemoteObject implements GamesHandlerInterface {

    /**
	 * 
	 */
	private static final long serialVersionUID = -6002464202108439172L;

	public GamesHandler() throws RemoteException {
    	
    }
    /**
     * Implement Singleton
     */
	private static GamesHandler single_instance = null;
	
    public static GamesHandler getInstance() throws RemoteException  {
    	if (single_instance == null) 
            single_instance = new GamesHandler();
        return single_instance; 
    }

	public boolean createGame(String gameName, String createrName, int playerCount) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean joinGame(String gameName, String playerName) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	public String getCodeNameOfCard(String gameName, int i) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean revealCard(String gameName, String playerName, int i) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean placeChatMessage(String gameName, String platerName, String message) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean placeHintMessage(String gameName, String playerName, String message) throws RemoteException{
		// TODO Auto-generated method stub
		return false;
	}

}
