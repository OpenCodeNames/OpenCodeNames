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

}
