package io.codenames.data;

public class Player {
	private String name;
	private int numGames;
	private int cardsRevield;
	private int correctReviels;
	private int incorrectReviels;

	/**
	 * Get player name
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set player name
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get Number of names played
	 * @return
	 */
	public int getNumGames() {
		return numGames;
	}

	/**
	 * set number of games
	 * @param numGames
	 */
	protected void setNumGames(int numGames) {
		this.numGames = numGames;
	}

	/**
	 * Increment number of games played
	 */
	public void playedGame() {
		this.numGames++;
	}

	/**
	 * Get number of cards Revield
	 * @return
	 */
	public int getCardsRevield() {
		return cardsRevield;
	}

	/**
	 * Set number of cards revield by a player
	 * @param cardsRevield
	 */
	protected void setCardsRevield(int cardsRevield) {
		this.cardsRevield = cardsRevield;
	}

	/**
	 * Get correct reveals by player
	 * @return
	 */
	public int getCorrectReviels() {
		return correctReviels;
	}

	/**
	 * Set correct cards revield
	 * @param correctReviels
	 */
	protected void setCorrectReviels(int correctReviels) {
		this.correctReviels = correctReviels;
	}

	/**
	 * Get incorrect card reviels
	 * @return
	 */
	public int getIncorrectReviels() {
		return incorrectReviels;
	}

	/**
	 * Set Incorrect Revels
	 * @param incorrectReviels
	 */
	protected void setIncorrectReviels(int incorrectReviels) {
		this.incorrectReviels = incorrectReviels;
	}


}
