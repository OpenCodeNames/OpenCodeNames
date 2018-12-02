package io.codenames.serverdata;

import io.codenames.serverdata.PlayerProxy;

import java.rmi.RemoteException;
import java.util.Map;
import java.util.TimerTask;

public class CardRevealedCallbackTask extends TimerTask {
    String code;
    boolean turnChanged;
    Map<String, PlayerProxy> players;


    public CardRevealedCallbackTask(String code, boolean turnChanged, Map<String, PlayerProxy> players) {
        this.code = code;
        this.turnChanged = turnChanged;
        this.players = players;
    }

    @Override
    public void run() {
        for (Map.Entry<String,PlayerProxy> entry : players.entrySet() ) {
            try {
                PlayerProxy player = entry.getValue();
                player.getClientCallBackInterface().cardOpened(code, turnChanged);
                System.out.println(player.getName()+ " updated on :" + code + " click with turnChanged : "+ turnChanged);
            } catch (RemoteException e) {
            	new java.util.Timer().schedule(new PlayerDroppedCallbackTask(players),20);
            	break;
            }
        }
    }
}
