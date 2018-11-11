package io.codenames.serverinterfaces;

import java.rmi.*;

public interface PlayersHandlerInterface extends Remote{

	public boolean register(String userName, String password) throws RemoteException;
	
	public boolean authenticate (String userName, String password) throws RemoteException;
	
	public PlayerInterface playerStatistic(String playerName) throws RemoteException;
}
