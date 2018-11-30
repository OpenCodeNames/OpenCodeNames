package io.codenames.serverinterfaces;
import java.rmi.*;
public interface PlayerInterface extends Remote{

    /**
     * Get player name
     * @return String
     */
    String getName();

    /**
     * Set player name
     * @param name Player Name
     */
    void setName(String name);

    /**
     * Get Number of names played
     * @return int
     */
    int getNumGames();


    /**
     * Increment number of games played
     */
    void playedGame();

    /**
     * Get number of cards Reviled
     * @return int
     */
    int getCardsReviled();


    /**
     * Get correct reveals by player
     * @return int
     */
    int getCorrectReviles();



    /**
     * Get incorrect card reviles
     * @return int
     */
    int getIncorrectReviles();

    /**
     * Get Number of games won
     * @return
     */
    int getGamesWon();
    
}
