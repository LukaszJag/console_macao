package cardsTypes;

import functionality.Card;
import functionality.Player;

public class Functional {

    Card card = new Card(0,'z',false);
    Card[] atackCards = new Card[10];



    public void invokeAction(Card card, Player nextPlayer){

        if(2 == card.getValue() || 3 == card.getValue()){
            nextPlayer.addCardToHand();
        }

    }

}
