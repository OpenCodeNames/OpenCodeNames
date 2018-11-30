package io.codenames.serverdata;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.security.MessageDigest;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import io.codenames.serverinterfaces.GameInterface;

public class Game  implements GameInterface, Serializable {
    private String gameID;
    private String name;
    private String creator;
    private int seats;
    private int seatsAvailable;
    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();
    private int team1;
    private int team2;
    private int team1SpyMasterIndex;
    private int team2SpyMasterIndex;
    private int turnCount=1;
    private int turn = 0;
    private boolean gameOver;
    private boolean gameStarted;

    private CardFactory cardfactory= new CardFactory();
    private Map<String, PlayerProxy> players= new LinkedHashMap<String, PlayerProxy>();


    public Game(String name, String creator, int seats) {
        try {
            this.name = name;
            this.creator = creator;
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            String gameIDToHash = name+creator+ Long.toString(System.currentTimeMillis());
            messageDigest.update(gameIDToHash.getBytes());
            this.gameID = bytesToHex(messageDigest.digest());
            setSeats(seats);
            chooseTeamOrder();
            genarateRole();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public String getName(){
        return name;
    }


    public Card getCard(int i) {
        return cardfactory.getCard(i);
    }

    public Card getCard(String code) {
        return cardfactory.getCard(code);
    }

    public ArrayList<String> getCardsArray(){ return cardfactory.getCardsArray(); }

    public void setName(String name) {
        this.name = name;
    }


    public String getCreator() {
        return creator;
    }


    public void setCreator(String creator) {
        this.creator = creator;
    }


    public int getSeats() {
        return seats;
    }


    public void setSeats(int seats) {
        this.seats = seats;
        setSeatsAvailable(seats);
    }


    public String getGameID() {
        return gameID;
    }

    public int getSeatsAvailable() {
        return seatsAvailable;
    }

    /**
     *
     * @param seatsAvailable
     */
    private void setSeatsAvailable(int seatsAvailable) {
        this.seatsAvailable = seatsAvailable;
    }

    public int getTurnCount() {
        return turnCount;
    }

    public int getTurn() {
        return turn;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    protected void gameOver() {
        this.gameOver = false;
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    protected void gameStarted() {
        this.gameStarted = true;
    }

    protected boolean addPlayer(PlayerProxy player){
        String name = player.getName();
        if(seatsAvailable>0 && !playerExists(name)){
            this.players.put(name,player);
            setSeatsAvailable(seatsAvailable-1);
            return true;
        }else{
            return false;
        }

    }

    protected boolean removePlayer(PlayerProxy player){
        String name = player.getName();
        if(seatsAvailable>0 && playerExists(name)){
            this.players.remove(name,player);
            setSeatsAvailable(seatsAvailable+1);
            return true;
        }else{
            return false;
        }

    }

    protected boolean playerExists(String playerName){
        return players.containsKey(playerName);
    }

    protected boolean startGame(){
        int i=0;
        int t1i=0;
        int t2i=0;
        for (Map.Entry<String,PlayerProxy> entry : players.entrySet() ) {
            PlayerProxy player = entry.getValue();
            System.out.println("Starting game for"+ player.getName());
            if(i % 2 == 0){
                player.setTeam(team1);
                if(t1i==team1SpyMasterIndex){
                    player.setRole(1);
                }else{
                    player.setRole(0);
                }
                t1i++;
            }else{
                player.setTeam(team1=2);
                if(t2i==team2SpyMasterIndex){
                    player.setRole(1);
                }else{
                    player.setRole(0);
                }
                t2i++;
            }try{
                if(!player.getClientCallBackInterface().startGame() ) {
                    return false;
                }
            } catch (RemoteException e) {
                e.printStackTrace();
                return false;
            }
            i++;
        }
        gameStarted();
        return true;

    }

    protected void incrimentTurnCount(){
        turnCount++;
    }

    protected void passTurn(){
        if(turn==0){
            turn = 1;
        }else{
            turn =0;
        }
    }

    protected boolean revealCard(int turnCount, String code, String playerName){
        if(this.playerExists(playerName) && this.turnMatches(turnCount, playerName) && cardfactory.revealCard(code)){
            Card card = this.getCard(code);
            int type = card.getType();
            if(type == this.turn){
                incrimentTurnCount();
                new java.util.Timer().schedule(new CardRevealedCallbackTask(code,false,players),20);
            }else if(type == 3 ){
                gameOver();
                // TODO Auto-generated DeathCard Revealed Callback
            }else{
                incrimentTurnCount();
                passTurn();
                new java.util.Timer().schedule(new CardRevealedCallbackTask(code,true,players),20);
            }

            return true;
        }
        return false;
    }

    protected int getTeamOfPlayerInGame(String playerName){
        return this.players.get(playerName).getTeam();
    }


    protected int getRoleOfPlayerInGame(String playerName){
        return this.players.get(playerName).getRole();
    }

    public boolean turnMatches(int turnCount, String playerName){
        if(turnCount==this.turnCount && playerExists(playerName)){
            return (this.getTeamOfPlayerInGame(playerName) == this.turn);
        }
        return false;
    }
    protected void chooseTeamOrder(){
        Random rn = new Random();
        int team1 = rn.nextInt(2);
        if(team1==0)
            team2=1;
        else
            team2=0;
    }

    protected void genarateRole(){
        Random rn = new Random();
        team1SpyMasterIndex = rn.nextInt(seats/2);
        team2SpyMasterIndex = rn.nextInt(seats/2);
    }

}
