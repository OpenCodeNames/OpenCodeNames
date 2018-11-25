package io.codenames.serverdata;

import java.rmi.*;
import java.rmi.server.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

import io.codenames.clientinterfaces.ClientCommandInvokerInterface;
import io.codenames.serverinterfaces.GamesHandlerInterface;



public class GamesHandler extends UnicastRemoteObject implements GamesHandlerInterface {

    private static LinkedHashMap<String, Game> gameList = new LinkedHashMap<String, Game>();
    private static LinkedHashMap<String,HashMap<String,String>> viewablegamewue = new LinkedHashMap<String,HashMap<String,String>>();
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



    public String createGame(String gameName, String creatorName, int numPlayers) {
        Game game = new Game(gameName,creatorName,numPlayers);
        if(gameList.containsKey(game.getGameID())){
            System.out.println("Game Creation Failed");
            return null;
        }

        gameList.put(game.getGameID(), game);
        HashMap<String,String> gameHM = new HashMap<String,String>();
        gameHM.put("name",game.getName());
        gameHM.put("creator",game.getCreator());
        gameHM.put("seats",Integer.toString(game.getSeats()));
        gameHM.put("gameID",game.getGameID());
        viewablegamewue.put(game.getGameID(),gameHM);
        System.out.println("New Game Created" + game.getGameID());
        return game.getGameID();
    }


    public boolean joinGameQueue(String gameID, String playerName,  ClientCommandInvokerInterface client) throws RemoteException {
        if(gameList.containsKey(gameID)){
            Game game = gameList.get(gameID);
            PlayersHandler playersHandler= PlayersHandler.getInstance();
            Player player = playersHandler.getPlayer(playerName);
            player.setClientCallBackInterface(client);
            if(player!=null && game.addPlayer(player)){
                System.out.println("Player "+playerName +" joined");
                if(game.getSeatsAvailable()==0 && game.startGame()){
                    /**
                     * Start Game
                     */

                    System.out.println("Game "+gameID+" Started");
                    gameList.remove(gameID);
                    viewablegamewue.remove(gameID);
                    runningGames.put(gameID,game);

                }

                return true;
            }

        }
        System.out.println("Player "+playerName+" couldn't join game");
        return false;
    }


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


    public LinkedHashMap<String, HashMap<String,String>> getGames() throws RemoteException {
        return this.viewablegamewue;
    }


    public boolean cardSelected(String gameID, int cardID, String playerName) throws RemoteException{
        return false;
    }



	public String getCodeNameOfCard(String gameName, int i) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

    @Override
    public ArrayList<String> getCardsArray(String gameID, String playerName) throws RemoteException {
        if(runningGames.containsKey(gameID)){
            Game game = runningGames.get(gameID);
            PlayersHandler playersHandler= PlayersHandler.getInstance();
            Player player = playersHandler.getPlayer(playerName);
            if(game.playerExists(player)){
                return game.getCardsArray();
            }
        }
        System.out.println("Game "+gameID+"Not Found");
        return null;
    }


    public boolean placeChatMessage(String gameName, String platerName, String message) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}



	public boolean placeHintMessage(String gameName, String playerName, String message) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}


}
