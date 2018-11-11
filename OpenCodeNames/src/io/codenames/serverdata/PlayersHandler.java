package io.codenames.serverdata;

import java.rmi.*;
import java.rmi.server.*;

import java.util.HashMap;


import io.codenames.serverinterfaces.PlayersHandlerInterface;

public class PlayersHandler extends UnicastRemoteObject implements PlayersHandlerInterface{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -4825483869639346540L;

	private static final HashMap<String, Player> playerList = new HashMap<String, Player>();

	public PlayersHandler() throws RemoteException {
    	
    }
    /**
     * Implement Singleton
     */
	private static PlayersHandler single_instance = null;
	
    public static PlayersHandler getInstance() throws RemoteException {
    	if (single_instance == null) 
            single_instance = new PlayersHandler();
        return single_instance; 
    }


    public boolean register(String userName, String password) throws RemoteException{
        System.out.println("registering : "+userName);
        if(!playerList.containsKey(userName)){
            Player player = new Player(userName,password);
            playerList.put(userName,player);
            return true;
        }else{
            System.out.println("user "+userName+"already exists");
            return false;
        }

    }


    public boolean authenticate(String userName, String password) throws RemoteException{
        if(playerList.containsKey(userName)) {
            Player player = playerList.get(userName);
            return player.matchPassword(password);
        }else{
            return false;
        }
    }


    public Player playerStatistic(String playerName) throws RemoteException{
        if(playerList.containsKey(playerName)) {
            return playerList.get(playerName);
        }else{
            return null;
        }
    }
}
    
    
   