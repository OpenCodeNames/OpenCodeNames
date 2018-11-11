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
        Card card;
        do {
            String code =  WordList.getWord();
            card = generateCard(code);
        }while(card != null);

    }


    public Card generateCard(String code) {
        Card card = null;
        if(!cardNameMap.contains(code)){
            int type = this.randType();
            if(type == 0){
                return null;
            }else {
                try {
					card = new Card(type, code);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
                cardMap.put(code, card);
            }
        }
        return card;

    }

    protected Card getCard(int i){
        return cardMap.get(cardNameMap.get(i));
    }


    public LinkedHashMap<String, Card> getCardMap() {
        return cardMap;
    }

    private int randType(){
        Random rn = new Random();
        int type = rn.nextInt(4)+1;

        if((blueCount+blackCount+redCount+neutralCount) >= 25){
            return 0;
        }else if(type==1){
            if(blueCount >= 8 ){
                return randType();
            }else {
                blueCount++;
            }
        }else if(type == 2){
            if(blackCount >= 1 ){
                return randType();
            }else {
                blackCount++;
            }
        }else if(type == 3){
            if(redCount >= 8 ){
                return randType();
            }else {
                redCount++;
            }
        }else if(type == 4){
            if(neutralCount >= 8 ){
                return randType();
            }else {
                neutralCount++;
            }
        }

        return type;
    }
}
