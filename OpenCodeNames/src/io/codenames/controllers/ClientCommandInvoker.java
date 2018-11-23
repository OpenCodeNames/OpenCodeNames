package io.codenames.controllers;

import io.codenames.clientinterfaces.ClientCommandInvokerInterface;
import io.codenames.serverinterfaces.GamesHandlerInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientCommandInvoker  extends UnicastRemoteObject implements ClientCommandInvokerInterface {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static ClientCommandInvoker single_instance = null;

    private LoadingViewController loadScreen;

    private GameViewController gameScreen;


    private ClientCommandInvoker() throws RemoteException{

    }

    public static ClientCommandInvoker getInstance() throws RemoteException {
        if (single_instance == null)
            single_instance = new ClientCommandInvoker();
        return single_instance;
    }

    protected void setLoadScreen(LoadingViewController loadScreen) {
        this.loadScreen = loadScreen;
    }

    protected void setGameScreen(GameViewController gameScreen) {
        this.gameScreen = gameScreen;
    }

    @Override
    public boolean startGame() throws RemoteException {
        if(this.loadScreen == null)
            return false;
        if(loadScreen.loadGame()){
            return true;
        }else {
            return false;
        }

    }

    @Override
    public boolean reciveMessageBroadCast(String Message) throws RemoteException {
        return false;
    }

    @Override
    public boolean cardOpened(int i, int type, boolean turnChange) throws RemoteException {
        return false;
    }

    @Override
    public boolean reciveGameMessage(String message) throws RemoteException {
        return false;
    }

    @Override
    public boolean reciveHint(String hint) throws RemoteException {
        return false;
    }

}
