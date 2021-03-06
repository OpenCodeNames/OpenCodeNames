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

   
    public boolean startGame() throws RemoteException {
        if(this.loadScreen != null && loadScreen.loadGame()){
            return true;
        }else {
            return false;
        }

    }
    
    public boolean playerDropped() throws RemoteException{
        if(this.gameScreen != null && gameScreen.gameDrop()){
            return true;
        }else {
            return false;
        }
    }

    public void gameWinLossMessageReceive(int lastTurnBy, boolean wonByDeathCard) throws RemoteException {
        this.gameScreen.gameWon(lastTurnBy,wonByDeathCard);
    }


    public boolean reciveMessageBroadCast(String Message,String playerName,int team) throws RemoteException {
        this.gameScreen.addChatMessage(playerName+" :"+Message,team,0);
        return true;
    }

   
    public boolean cardOpened(String code, boolean turnChange) throws RemoteException {
        this.gameScreen.revealCard(code, turnChange);
        return true;
    }


    public boolean turnPassed() throws RemoteException {
        if(this.gameScreen != null) {
            gameScreen.turnChange();
            return true;
        }
        return false;
    }


    public boolean reciveGameMessage(String message) throws RemoteException {
        this.gameScreen.addChatMessage("Game Message :"+message,3,3);
        return true;
    }

   
    public boolean reciveHint(String hint,String playerName,int team) throws RemoteException {
        this.gameScreen.addChatMessage(playerName+" :"+hint,team,0);
        return false;
    }

}
