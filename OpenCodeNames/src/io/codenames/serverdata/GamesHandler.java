package io.codenames.serverdata;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.*;
import java.rmi.server.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import io.codenames.clientinterfaces.ClientCommandInvokerInterface;
import io.codenames.serverinterfaces.GamesHandlerInterface;



public class GamesHandler extends UnicastRemoteObject implements GamesHandlerInterface {

    private static LinkedHashMap<String, Game> gameList = new LinkedHashMap<String, Game>();
    private static LinkedHashMap<String,HashMap<String,String>> viewablegamewue = new LinkedHashMap<String,HashMap<String,String>>();
    private static LinkedHashMap<String, Game> runningGames =  new LinkedHashMap<String, Game>();
    
    private static int numGames = 0;
	private static float avgCardsReviled = 0;
    private static float avgCorrectReviles = 0;
    private static float avgIncorrectReviles = 0;
    private static float avgDeathCards = 0;
    private static float avgGamesWon = 0;
    private static int redWonByDeathCard = 0;
    private static int redWonByCompletion = 0;
    private static int blueWonByDeathCard = 0;
    private static int blueWonByCompletion = 0;
    private static int numberOfPlayers = 0;
    /**
     *
     */
    
    private static final long serialVersionUID = -6002464202108439172L;

    private GamesHandler() throws RemoteException {

    	try {
            File f = new File("ServerGameData.ser");
            if (f.isFile() && f.canRead()) {
                FileInputStream fis = new FileInputStream("ServerGameData.ser");
                ObjectInputStream ois = new ObjectInputStream(fis);
                @SuppressWarnings("unchecked")
				HashMap<String, String> retriveGameData =  (HashMap<String, String>) ois.readObject();
                this.numGames = Integer.parseInt(retriveGameData.get("avgNumGames"));
                this.numberOfPlayers = Integer.parseInt(retriveGameData.get("numberOfPlayers"));
            	this.avgCardsReviled = Float.parseFloat(retriveGameData.get("avgCardsReviled"));
                this.avgCorrectReviles = Float.parseFloat(retriveGameData.get("avgCorrectReviles"));
                this.avgIncorrectReviles = Float.parseFloat(retriveGameData.get("avgIncorrectReviles"));
                this.avgDeathCards = Float.parseFloat(retriveGameData.get("avgDeathCards"));
                this.avgGamesWon = Float.parseFloat(retriveGameData.get("avgGamesWon"));
                this.redWonByDeathCard = Integer.parseInt(retriveGameData.get("redWonByDeathCard"));
                this.redWonByCompletion = Integer.parseInt(retriveGameData.get("redWonByCompletion"));
                this.blueWonByDeathCard = Integer.parseInt(retriveGameData.get("blueWonByDeathCard"));
                this.blueWonByCompletion = Integer.parseInt(retriveGameData.get("blueWonByCompletion"));
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
    private static GamesHandler single_instance = null;

    public static GamesHandler getInstance() throws RemoteException  {
        if (single_instance == null)
            single_instance = new GamesHandler();
        return single_instance;
    }



    public String createGame(String gameName, String creatorName, int numPlayers) {
        Game game = new Game(gameName,creatorName,numPlayers);
        if(gameList.containsKey(game.getGameID())){
            System.out.println("createGame: Game Creation Failed");
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
            PlayerProxy tmpPlayer = new PlayerProxy(player);
            tmpPlayer.setClientCallBackInterface(client);
            if(player!=null && game.addPlayer(tmpPlayer)){
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
            PlayerProxy tmpPlayer = new PlayerProxy(player);
            if(player!=null){
                if(game.removePlayer(tmpPlayer)){
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

    public boolean cardSelected(String gameID, int turnCount, String code, String playerName) throws RemoteException{
        if(runningGames.containsKey(gameID)){
            Game game = runningGames.get(gameID);
            if(game.revealCard(turnCount,code,playerName)){
                return true;
            }else {
            	 System.out.println("cardSelected: "+gameID+" RevealCard Failed for "+ " "+ code);
            }
        }else {
        	 System.out.println("cardSelected: "+gameID+" Game not found or Malicious call Found");
        }
        return false;
    }


    public int getTypeOfCardInGame(String gameID, String code, String playerName) throws RemoteException {
        if(runningGames.containsKey(gameID)){
            Game game = runningGames.get(gameID);
            if(game.playerExists(playerName)){
                return game.getTypeOfCard(code);
            }
        }
        System.out.println("getCardsArray: "+gameID+" Game not found or Malicious call Found");
        return -1;
    }


    public void passTurnInGame(String gameID, String playerName, int turnCount) throws RemoteException {
        if(runningGames.containsKey(gameID)){
            Game game = runningGames.get(gameID);
            if(game.turnMatches(turnCount,playerName)){
                game.passTurn(true);
            }
        }
    }


    public String getCodeNameOfCard(String gameID, String playerName, int i) throws RemoteException {
        if(runningGames.containsKey(gameID)){
            Game game = runningGames.get(gameID);
            if(game.playerExists(playerName)){
                return game.getCard(i).getCodeName();
            }
        }
        return null;
    }


    public ArrayList<String> getCardsArray(String gameID, String playerName) throws RemoteException {
        if(runningGames.containsKey(gameID)){
            Game game = runningGames.get(gameID);
            if(game.playerExists(playerName)){
                return game.getCardsArray();
            }
        }
        System.out.println("getCardsArray: Game "+gameID+"Not Found");
        return null;
    }


    public boolean placeChatMessage(String gameID, String playerName, String message) throws RemoteException {
        if(runningGames.containsKey(gameID)){
            Game game = runningGames.get(gameID);
            if(game.playerExists(playerName)){
                return game.placeChatMessage(message,playerName);
            }
        }
        System.out.println("getCardsArray: Game "+gameID+"Not Found");
        return true;
    }



    public boolean placeHintMessage(String gameName, int turnCount, String playerName, String message) throws RemoteException {
        // TODO Auto-generated method stub
        return false;
    }

	public int getAvgNumGames() throws RemoteException {
		return (numGames/numberOfPlayers);
	}

	public int getAvgCardsReviled() throws RemoteException {
		return (int)avgCardsReviled;
	}

	public int getAvgCorrectReviles() throws RemoteException {
		return (int)avgCorrectReviles;
	}

	public int getAvgDeathCardReviles() throws RemoteException {
		return (int)avgDeathCards;
	}

	public int getAvgIncorrectReviles() throws RemoteException {
		return (int)avgIncorrectReviles;
	}

	public int getAvgGamesWon() throws RemoteException {
		return (int)avgGamesWon;
	}

    public int getRedWonByDeathCard() throws RemoteException {
        return redWonByDeathCard;
    }

    public int getRedWonByCompletion() throws RemoteException {
        return redWonByCompletion;
    }

    public int getBlueWonByDeathCard() throws RemoteException {
        return blueWonByDeathCard;
    }

    public int getBlueWonByCompletion() throws RemoteException {
        return blueWonByCompletion;
    }

    protected static void incrimentNumGames() {
        if (single_instance == null) {
            try {
                single_instance = new GamesHandler();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        numGames++;
    }

    protected static void addToAvgCardsReviled(int CardsReviledChange) {
        if (single_instance == null) {
            try {
                single_instance = new GamesHandler();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        avgCardsReviled += ((float)CardsReviledChange/numberOfPlayers);
    }

    protected static void addToAvgCorrectReviles(int CorrectRevilesdChange) {
        if (single_instance == null) {
            try {
                single_instance = new GamesHandler();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        avgCorrectReviles += ((float)CorrectRevilesdChange/numberOfPlayers);
    }

    protected static void addToAvgIncorrectReviles(int IncorrectRevilesChange) {
        if (single_instance == null) {
            try {
                single_instance = new GamesHandler();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        avgIncorrectReviles += ((float)IncorrectRevilesChange/numberOfPlayers);
    }

    protected static void addToAvgDeathCards(int DeathCardsChange) {
        if (single_instance == null) {
            try {
                single_instance = new GamesHandler();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        avgDeathCards += ((float)DeathCardsChange/numberOfPlayers);
    }

    /**
     * Dont use for stats
     * @param avgGamesWonChange
     */
    protected static void addToAvgGamesWon(int avgGamesWonChange) {
        if (single_instance == null) {
            try {
                single_instance = new GamesHandler();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        avgGamesWon += ((float)avgGamesWonChange/numberOfPlayers);
    }

    protected static void incrimentRedWonByDeathCard() {
        if (single_instance == null) {
            try {
                single_instance = new GamesHandler();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        redWonByDeathCard ++;
    }

    protected static void incrimentRedWonByCompletion() {
        if (single_instance == null) {
            try {
                single_instance = new GamesHandler();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        redWonByCompletion ++;
    }

    protected static void incrimentBlueWonByDeathCard() {
        if (single_instance == null) {
            try {
                single_instance = new GamesHandler();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        blueWonByDeathCard++;
    }

    protected static void incrimentBlueWonByCompletion() {
        if (single_instance == null) {
            try {
                single_instance = new GamesHandler();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        blueWonByCompletion++;
    }

    protected static void incrimentNumberOfPlayers() {
        if (single_instance == null) {
            try {
                single_instance = new GamesHandler();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        numberOfPlayers++;
    }
    public void saveGameData(){
        FileOutputStream f = null;
        try {
            FileOutputStream fs = new FileOutputStream(new File("ServerGameData.ser"));
            ObjectOutputStream os = new ObjectOutputStream(fs);
            HashMap<String, String> savableGameData = new HashMap<String, String>();
            savableGameData.put("numGames", String.valueOf(numGames));
            savableGameData.put("numberOfPlayers",String.valueOf(numberOfPlayers));
            savableGameData.put("avgCardsReviled", String.valueOf(avgCardsReviled));
            savableGameData.put("avgCorrectReviles", String.valueOf(avgCorrectReviles));
            savableGameData.put("avgIncorrectReviles", String.valueOf(avgIncorrectReviles));
            savableGameData.put("avgDeathCards", String.valueOf(avgDeathCards));
            savableGameData.put("avgGamesWon", String.valueOf(avgGamesWon));
            savableGameData.put("redWonByDeathCard", String.valueOf(redWonByDeathCard));
            savableGameData.put("redWonByCompletion", String.valueOf(redWonByCompletion));
            savableGameData.put("blueWonByDeathCard", String.valueOf(blueWonByDeathCard));
            savableGameData.put("blueWonByCompletion", String.valueOf(blueWonByCompletion));
            os.writeObject(savableGameData);
            os.close();
            fs.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public int getTeamOfPlayerInGame(String gameID, String playerName) throws RemoteException {
        if(runningGames.containsKey(gameID)){
            Game game = runningGames.get(gameID);
            if(game.playerExists(playerName)){
                return game.getTeamOfPlayerInGame(playerName);
            }
        }
        System.out.println("getTeamOfPlayerInGame: Game "+gameID+"Not Found");
        return -1;
    }

    public int getRoleOfPlayerInGame(String gameID, String playerName) throws RemoteException {
        if(runningGames.containsKey(gameID)){
            Game game = runningGames.get(gameID);
            if(game.playerExists(playerName)){
                return game.getRoleOfPlayerInGame(playerName);
            }
        }
        System.out.println("getTeamOfPlayerInGame: Game "+gameID+"Not Found");
        return -1;
    }


    public int getTurnOfGame(String gameID, String playerName) throws RemoteException {
        if(runningGames.containsKey(gameID)){
            Game game = runningGames.get(gameID);
            if(game.playerExists(playerName)){
                return game.getTurn();
            }
        }
        System.out.println("getTurnOfGame: Game "+gameID+"Not Found");
        return -1;
    }


    public int getTurnCountOfGame(String gameID, String playerName) throws RemoteException {
        if(runningGames.containsKey(gameID)){
            Game game = runningGames.get(gameID);
            if(game.playerExists(playerName)){
                return game.getTurnCount();
            }
        }
        System.out.println("Game "+gameID+"Not Found");
        return -1;
    }


}
