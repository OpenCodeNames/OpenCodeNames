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
        GamesHandler.incrimentNumGames();
        for (Map.Entry<String,PlayerProxy> entry : players.entrySet() ) {
            try {
                PlayerProxy player = entry.getValue();
                if(player.getPlayer().getNumGames()==0)
                    GamesHandler.incrimentNumberOfPlayers();
                player.gameOver(winningTeam,wonByDeathCard);
                GamesHandler.addToAvgCardsReviled(player.getCardsReviledChange());
                GamesHandler.addToAvgCorrectReviles(player.getIncorrectRevilesChange());
                GamesHandler.addToAvgDeathCards(player.getDeathCardsChange());
                GamesHandler.addToAvgIncorrectReviles(player.getIncorrectRevilesChange());
                player.getClientCallBackInterface().gameWinLossMessageReceive(winningTeam,wonByDeathCard);

            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        if(winningTeam==0){
            if(wonByDeathCard){
                GamesHandler.incrimentBlueWonByDeathCard();
            }else{
                GamesHandler.incrimentRedWonByCompletion();
            }
        }else{
            if(wonByDeathCard){
                GamesHandler.incrimentRedWonByDeathCard();
            }else{
                GamesHandler.incrimentBlueWonByCompletion();
            }
        }
    }
}
