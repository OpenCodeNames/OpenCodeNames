package io.codenames.serverdata;

import java.rmi.RemoteException;
import java.util.Map;
import java.util.TimerTask;

public class MessageRecivedCallbackTask extends TimerTask {
    String message;
    boolean isSpyMaster;
    String playerName;
    int team;
    Map<String, PlayerProxy> players;


    public MessageRecivedCallbackTask(String message, boolean isSpyMaster, String playerName, int team,Map<String, PlayerProxy> players) {
        this.message = message;
        this.isSpyMaster = isSpyMaster;
        this.playerName = playerName;
        this.team = team;
        this.players = players;
    }

    @Override
    public void run() {
        for (Map.Entry<String,PlayerProxy> entry : players.entrySet() ) {
            try {
                PlayerProxy player = entry.getValue();
                if(isSpyMaster)
                    player.getClientCallBackInterface().reciveHint(message, playerName,team);
                else
                    player.getClientCallBackInterface().reciveMessageBroadCast(message, playerName,team);
                System.out.println(player.getName()+ " updated on message:" + message + "by"+playerName+" with isSpyMaster : "+ isSpyMaster);
            } catch (RemoteException e) {
            	new java.util.Timer().schedule(new PlayerDroppedCallbackTask(players),20);
            	break;
            }
        }
    }
}
