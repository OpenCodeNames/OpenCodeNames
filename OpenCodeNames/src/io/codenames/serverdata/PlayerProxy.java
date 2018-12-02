package io.codenames.serverdata;

import io.codenames.clientinterfaces.ClientCommandInvokerInterface;

public class PlayerProxy {
    private Player player;
    private int cardsReviled = 0;
    private int correctReviles = 0;
    private int incorrectReviles = 0;
    private int deathCards = 0;
    private int gamesWon = 0;
    private int team;
    private int role;

    private ClientCommandInvokerInterface clientCallBackInterface;

    public PlayerProxy(Player player) {
        this.player = player;
    }

    protected Player getPlayer() {
        return player;
    }

    public String getName() {
        return player.getName();
    }

    public int getCardsReviled() {
        return cardsReviled+player.getCardsReviled();
    }

    public int getCorrectReviles() {
        return correctReviles+player.getCorrectReviles();
    }

    public int getIncorrectReviles() {
        return incorrectReviles+player.getIncorrectReviles();
    }

    public int getDeathCards() {
        return deathCards+player.getDeathCards();
    }

    public int getGamesWon() {
        return gamesWon+player.getGamesWon();
    }


    public int getCardsReviledChange() {
        return cardsReviled;
    }

    public int getCorrectRevilesChange() {
        return correctReviles;
    }

    public int getIncorrectRevilesChange() {
        return incorrectReviles;
    }

    public int getDeathCardsChange() {
        return deathCards;
    }


    public int getTeam() {
        return team;
    }

    public int getRole() {
        return role;
    }

    protected ClientCommandInvokerInterface getClientCallBackInterface() {
        return clientCallBackInterface;
    }

    protected void setTeam(int team) {
        this.team = team;
    }

    protected void setRole(int role) {
        this.role = role;
    }

    protected void clicked(int type){
        cardsReviled++;
        if(type == team){
            correctReviles++;
        }else if(type == 3){
            incorrectReviles++;
            deathCards++;
        }else{
            incorrectReviles++;
        }
    }

    protected void gameOver(int lastTeamPlayed ,boolean byDeathCard){
        if(team==lastTeamPlayed){
            if(!byDeathCard){
                gamesWon++;
            }
        }else{
            if(byDeathCard){
                gamesWon++;
            }
        }
        updateRealPlayer();
    }

    protected void updateRealPlayer(){
        player.setCardsReviled(getCardsReviled());
        player.setCorrectReviles(getCorrectReviles());
        player.setDeathCards(getDeathCards());
        player.setGamesWon(getGamesWon());
        player.setIncorrectReviles(getIncorrectReviles());
        player.setNumGames(player.getNumGames()+1);
    }

    protected void setClientCallBackInterface(ClientCommandInvokerInterface clientCallBackInterface) {
        this.clientCallBackInterface = clientCallBackInterface;
    }

}
