package io.codenames.serverdata;

import java.rmi.RemoteException;
import java.util.Map;
import java.util.TimerTask;

public class GameStartCallbackTask extends TimerTask {

    Map<String, PlayerProxy> players;


    public GameStartCallbackTask(Map<String, PlayerProxy> players) {
        this.players = players;
    }

    @Override
    public void run() {
        for (Map.Entry<String,PlayerProxy> entry : players.entrySet() ) {
            try {
                PlayerProxy player = entry.getValue();
                player.getClientCallBackInterface().startGame();
            } catch (RemoteException e) {
            	new java.util.Timer().schedule(new PlayerDroppedCallbackTask(players),20);
            	break;
            }
        }
    }
}
