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
        this.cardsReviled = player.getCardsReviled();
        this.correctReviles = player.getCorrectReviles();
        this.incorrectReviles = player.getIncorrectReviles();
        this.deathCards = player.getDeathCards();
        this.gamesWon = player.getGamesWon();
    }

    protected Player getPlayer() {
        return player;
    }

    public String getName() {
        return player.getName();
    }

    public int getCardsReviled() {
        return cardsReviled;
    }

    public int getCorrectReviles() {
        return correctReviles;
    }

    public int getIncorrectReviles() {
        return incorrectReviles;
    }

    public int getDeathCards() {
        return deathCards;
    }

    public int getGamesWon() {
        return gamesWon;
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

    public void setTeam(int team) {
        this.team = team;
    }

    public void setRole(int role) {
        this.role = role;
    }

    protected void setClientCallBackInterface(ClientCommandInvokerInterface clientCallBackInterface) {
        this.clientCallBackInterface = clientCallBackInterface;
    }

}
