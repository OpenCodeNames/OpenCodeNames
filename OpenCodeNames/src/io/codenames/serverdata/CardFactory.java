package io.codenames.serverdata;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Random;

public class CardFactory {
    private LinkedHashMap<String, Card> cardMap = new LinkedHashMap<String, Card>();
    private ArrayList<String> cardNameMap = new ArrayList<String>();
    int blueCount = 0;
    int blackCount = 0;
    int redCount = 0;
    int neutralCount = 0;


    public CardFactory() {
        Card card = null;
        do {
            String code =  WordList.getWord();
            if(cardNameMap.contains(code))
            	continue;
            card = generateCard(code);
        }while(card != null);

    }


    public Card generateCard(String code) {
        Card card = null;
        int type = this.randType();
        if(type == -1){
            return null;
        }else {
            try {
				card = new Card(type, code);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			cardNameMap.add(code);
            cardMap.put(code, card);
        }
        return card;

    }

    protected Card getCard(int i){
        if(i<0 || i>24){
            return null;
        }
        return cardMap.get(cardNameMap.get(i));
    }

    protected Card getCard(String code){
        if(cardNameMap.contains(code))
            return cardMap.get(code);
        return null;
    }

    public LinkedHashMap<String, Card> getCardMap() {
        return cardMap;
    }

    protected int getTypeOfCard(String code){
        if(cardMap.containsKey(code)){
            Card card = cardMap.get(code);
            if(card.isHidden()){
                System.out.println("getTypeOfCard: "+code+" card is still hidden Malicious call");
            }else{
                return card.getType();
            }
        }else {
            System.out.println("getTypeOfCard: " + code + " card not found");
        }
        return -1;
    }

    private int randType(){
        Random rn = new Random();
        int type = rn.nextInt(4);
        if((blueCount+blackCount+redCount+neutralCount) >= 25){
            return -1;
        }else if(type==0){
            if(redCount >= 8 ){
                return randType();
            }else {
                redCount++;
            }
        }else if(type == 1){
            if(blueCount >= 8 ){
                return randType();
            }else {
                blueCount++;
            }
        }else if(type == 2){
            if(neutralCount >= 8 ){
                return randType();
            }else {
                neutralCount++;
            }
        }else if(type == 3){
            if(blackCount >= 1 ){
                return randType();
            }else {
                blackCount++;
            }
        }

        return type;
    }


    public ArrayList<String> getCardsArray() {
        return cardNameMap;
    }

    protected boolean revealCard(String code){
        if(cardMap.containsKey(code)){
            cardMap.get(code).revealCard();
            return true;
        }
        System.out.println("revealCard: "+code+" Card Not Found");
        return false;
    }
}
