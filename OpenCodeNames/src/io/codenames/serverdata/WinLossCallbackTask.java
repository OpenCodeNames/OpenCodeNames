package io.codenames.serverdata;

import java.rmi.RemoteException;
import java.util.Map;
import java.util.TimerTask;

public class WinLossCallbackTask extends TimerTask {
    Map<String, PlayerProxy> players;
    boolean wonByDeathCard;
    int winningTeam;

    public WinLossCallbackTask(int winningTeam, boolean wonByDeathCard, Map<String, PlayerProxy> players) {
        this.players = players;
        this.winningTeam = winningTeam;
        this.wonByDeathCard = wonByDeathCard;
    }

    @Override
    public void run() {
        for (Map.Entry<String,PlayerProxy> entry : players.entrySet() ) {
            try {
                PlayerProxy player = entry.getValue();
                player.getClientCallBackInterface().gameWinLossMessageReceive(winningTeam,wonByDeathCard);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
