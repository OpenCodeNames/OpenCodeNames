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

	@Override
	public boolean createGame(String gameName, String createrName, int playerCount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean joinGame(String gameName, String playerName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getCodeNameOfCard(String gameName, int i) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean revealCard(String gameName, String playerName, int i) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean placeChatMessage(String gameName, String platerName, String message) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean placeHintMessage(String gameName, String playerName, String message) {
		// TODO Auto-generated method stub
		return false;
	}

}
