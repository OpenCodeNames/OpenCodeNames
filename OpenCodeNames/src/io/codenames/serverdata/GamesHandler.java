package io.codenames.serverdata;

import java.rmi.*;
import java.rmi.server.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

import io.codenames.serverinterfaces.GamesHandlerInterface;



public class GamesHandler extends UnicastRemoteObject implements GamesHandlerInterface {

    private static LinkedHashMap<String, Game> gameList = new LinkedHashMap<String, Game>();
    private static LinkedHashMap<String, Game> runningGames =  new LinkedHashMap<String, Game>();
     /**
	 * 
	 */
	private static final long serialVersionUID = -6002464202108439172L;

	private GamesHandler() throws RemoteException {
    	
    }
    /**
     * Implement Singleton
     */
	private static GamesHandler single_instance = null;
	
    public static GamesHandler getInstance() throws RemoteException  {
    	if (single_instance == null) 
            single_instance = new GamesHandler();
        return single_instance; 
    }


    @Override
    public String createGame(String gameName, String creatorName, int numPlayers) {
        Game game = new Game(gameName,creatorName,numPlayers);
        if(gameList.containsKey(game.getGameID())){
            System.out.println("Game Creation Failed");
            return null;
        }
        gameList.put(game.getGameID(), game);
        System.out.println("New Game Created" + game.getGameID());
        return game.getGameID();
    }

    @Override
    public boolean joinGameQueue(String gameID, String playerName) throws RemoteException {
        if(gameList.containsKey(gameID)){
            Game game = gameList.get(gameID);
            PlayersHandler playersHandler= PlayersHandler.getInstance();
            Player player = playersHandler.getPlayer(playerName);
            if(player!=null && game.addPlayer(player)){
                System.out.println("Player "+playerName +" joined");
                if(game.getSeatsAvailable()==0){
                    /**
                     * Start Game
                     */
                    System.out.println("Game "+gameID+" Started");
                    gameList.remove(gameID);
                    runningGames.put(gameID,game);

                }
                return true;
            }

        }
        System.out.println("Player "+playerName+" couldn't join game");
        return false;
    }

    @Override
    public boolean leaveGameQueue(String gameID, String playerName) throws RemoteException {
        if(gameList.containsKey(gameID)){
            Game game = gameList.get(gameID);
            PlayersHandler playersHandler= PlayersHandler.getInstance();
            Player player = playersHandler.getPlayer(playerName);
            if(player!=null){
                if(game.removePlayer(player)){
                    System.out.println("Player "+playerName+" left game");
                    return true;
                }
            }

        }
        System.out.println("Player "+playerName+" couldnt leave game");
        return false;
    }

    @Override
    public LinkedHashMap<String, HashMap<String,String>> getGames() throws RemoteException {
        LinkedHashMap<String,HashMap<String,String>> games = new LinkedHashMap<String,HashMap<String,String>>();
        Iterator<Game> listIt = gameList.values().iterator();
        while (listIt.hasNext())
        {
            Game itGame = listIt.next();
            HashMap<String,String> game = new HashMap<String,String>();
            game.put("name",itGame.getName());
            game.put("creator",itGame.getCreator());
            game.put("seats",Integer.toString(itGame.getSeats()));
            game.put("gameID",itGame.getGameID());
            games.put(itGame.getGameID(),game);
        }
        return games;
    }

    @Override
    public boolean cardSelected(String gameID, int cardID, String playerName) throws RemoteException{
        return false;
    }


}
