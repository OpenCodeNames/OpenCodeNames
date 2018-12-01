package io.codenames.serverinterfaces;

import java.rmi.*;

public interface CardInterface extends Remote{
 
	    /**
	     * Get type of card
	     *
	     * @return
	     */
	   int getType();

	    /**
	     * Set type of cards
	     *
	     * @param type
	     */
	   void setType(int type);

	    /**
	     * get codeName of Card
	     *
	     * @return
	     */
	   String getCodeName();

	    /**
	     * Set codeName of card
	     *
	     * @param codeName
	     */
	    void setCodeName(String codeName);

	    /**
	     * check if card is hidden
	     *
	     * @return
	     */
	    boolean isHidden();

	    /*
	     * reveal card andSet hidden state to false
	     */
	    boolean revealCard();

}
