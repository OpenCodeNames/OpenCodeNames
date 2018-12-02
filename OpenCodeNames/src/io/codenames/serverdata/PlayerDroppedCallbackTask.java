package io.codenames.serverdata;

import java.rmi.RemoteException;
import java.util.Map;
import java.util.TimerTask;

public class PlayerDroppedCallbackTask extends TimerTask {
    Map<String, PlayerProxy> players;


    public PlayerDroppedCallbackTask( Map<String, PlayerProxy> players) {
        this.players = players;
    }

    @Override
    public void run() {
        for (Map.Entry<String,PlayerProxy> entry : players.entrySet() ) {
            try {
                PlayerProxy player = entry.getValue();
                player.getClientCallBackInterface().playerDropped();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
