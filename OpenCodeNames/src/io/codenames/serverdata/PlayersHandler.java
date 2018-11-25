package io.codenames.serverdata;

import java.io.*;
import java.rmi.*;
import java.rmi.server.*;

import java.util.HashMap;


import io.codenames.serverinterfaces.PlayersHandlerInterface;

public class PlayersHandler extends UnicastRemoteObject implements PlayersHandlerInterface{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -4825483869639346540L;

	private static transient HashMap<String, Player> playerList = new HashMap<String, Player>();

	public PlayersHandler() throws RemoteException {
        try {
            File f = new File("ServerData.ser");
            if (f.isFile() && f.canRead()) {
                FileInputStream fis = new FileInputStream("ServerData.ser");
                ObjectInputStream ois = new ObjectInputStream(fis);
                this.playerList = (HashMap<String, Player>) ois.readObject();
                ois.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
    /**
     * Implement Singleton
     */
	private static PlayersHandler single_instance = null;
	
    public static PlayersHandler getInstance() throws RemoteException {
    	if (single_instance == null) 
            single_instance = new PlayersHandler();
        return single_instance; 
    }


    public boolean register(String userName, String password) throws RemoteException{
        System.out.println("registering : "+userName);
        if(!playerList.containsKey(userName)){
            Player player = new Player(userName,password);
            playerList.put(userName,player);
            return true;
        }else{
            System.out.println("user "+userName+"already exists");
            return false;
        }

    }


    public boolean authenticate(String userName, String password) throws RemoteException{
        if(playerList.containsKey(userName)) {
            Player player = playerList.get(userName);
            return player.matchPassword(password);
        }else{
            return false;
        }
    }


    public Player getPlayer(String playerName) throws RemoteException{
        if(playerList.containsKey(playerName)) {
            return playerList.get(playerName);
        }else{
            return null;
        }
    }

    public void savePlayerList(){
        FileOutputStream f = null;
        try {
            FileOutputStream fs = new FileOutputStream(new File("ServerData.ser"));
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(playerList);
            os.close();
            fs.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
    
    
   