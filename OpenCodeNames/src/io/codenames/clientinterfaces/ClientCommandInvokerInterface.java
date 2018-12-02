package io.codenames.clientinterfaces;

import io.codenames.serverdata.Game;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientCommandInvokerInterface extends Remote {
    boolean startGame() throws RemoteException;
    boolean reciveMessageBroadCast(String Message) throws RemoteException;
    boolean cardOpened(String code, boolean turnChange) throws RemoteException;
    boolean turnPassed() throws RemoteException;
    boolean reciveGameMessage(String message) throws RemoteException;
    boolean reciveHint(String hint) throws RemoteException;
    boolean playerDropped()throws RemoteException;
    void gameWinLossMessageReceive(int winningTeam, boolean wonByDeathCard) throws RemoteException;
}
