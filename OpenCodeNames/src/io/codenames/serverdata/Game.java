package io.codenames.serverdata;

import java.io.Serializable;
import java.rmi.RemoteException;

import java.util.ArrayList;


import io.codenames.serverinterfaces.CardInterface;
import io.codenames.serverinterfaces.GameInterface;

public class Game  implements GameInterface, Serializable {
    private String name;
    private String creator;
    private int seats;
    private int seatsAvailable;

    private CardFactory cardfactory= new CardFactory();
    private static ArrayList<Player> playerMap = new ArrayList<Player>();

    
    public Game(String name, String creator, int seats) throws RemoteException {
        this.name = name;
        this.creator = creator;
        setSeats(seats);
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



}
