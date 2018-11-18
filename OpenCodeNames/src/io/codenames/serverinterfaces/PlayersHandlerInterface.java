package io.codenames.serverinterfaces;

import io.codenames.serverdata.Player;

import java.rmi.*;

public interface PlayersHandlerInterface extends Remote{

	public boolean register(String userName, String password) throws RemoteException;
	
	public boolean authenticate(String userName, String password) throws RemoteException;

	public Player getPlayer(String playerName) throws RemoteException;
}
