package io.codenames.serverdata;

import java.rmi.RemoteException;
import java.util.Map;
import java.util.TimerTask;

public class TurnPassedCallbackTask extends TimerTask {
    Map<String, PlayerProxy> players;


    public TurnPassedCallbackTask( Map<String, PlayerProxy> players) {
        this.players = players;
    }

    @Override
    public void run() {
        for (Map.Entry<String,PlayerProxy> entry : players.entrySet() ) {
            try {
                PlayerProxy player = entry.getValue();
                player.getClientCallBackInterface().turnPassed();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
