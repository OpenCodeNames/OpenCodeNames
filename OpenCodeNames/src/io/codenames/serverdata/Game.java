package io.codenames.serverdata;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.security.MessageDigest;

import io.codenames.serverinterfaces.GameInterface;

public class Game  implements GameInterface, Serializable {
    private String gameID;
    private String name;
    private String creator;
    private int seats;
    private int seatsAvailable;


    private CardFactory cardfactory= new CardFactory();
    private ArrayList<Player> playerMap = new ArrayList<Player>();

    
    public Game(String name, String creator, int seats) {
        try {
            this.name = name;
            this.creator = creator;
            MessageDigest messageDigest = null;

            messageDigest = MessageDigest.getInstance("MD5");
            String gameIDToHash = name+creator+ Long.toString(System.currentTimeMillis());
            messageDigest.update(gameIDToHash.getBytes());
            this.gameID = new String(messageDigest.digest());
            setSeats(seats);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
    
    
	public String getName(){
        return name;
    }


    public Card getCard(int i) {
        return cardfactory.getCard(i);
    }


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

    protected boolean addPlayer(Player player){
        int seats = getSeatsAvailable();
        if(seats>0){
            this.playerMap.add(player);
            setSeatsAvailable(seats-1);
            return true;
        }else{
            return false;
        }

    }

    protected boolean removePlayer(Player player){
        int seats = getSeatsAvailable();
        if(seats>0){
            this.playerMap.remove(player);
            setSeatsAvailable(seats-1);
            return true;
        }else{
            return false;
        }

    }
    
    protected boolean startGame(){
    	for (Player player: playerMap ) {
    		System.out.println(player.getName());
    	   try {
			if(!player.getClientCallBackInterface().startGame() ) {
				   return false;
			   }
		} catch (RemoteException e) {
			e.printStackTrace();
			return false;
		}
    	}
    	return true;
    	
    }
    
    

}
