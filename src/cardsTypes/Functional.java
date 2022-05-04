package cardsTypes;

import functionality.Card;
import functionality.MainStack;
import functionality.Player;

public class Functional {

    Card card = new Card(0,'z',false);
    Card[] atackCards = new Card[10];



    public void invokeAction(Player nextPlayer, int actionNum, int volumeOfAction, MainStack mainStack) {

        /*
        actionNum:
            1 = draw a card
            2 = stop next player
            3 = call value of card only normal card
            4 = block action of previous cards
            5 = call color of card
         */

        if (actionNum == 1){
            for (int i = 0; i <volumeOfAction; i++) {
                nextPlayer.addCardToHand(mainStack);
            }
        }

        if (actionNum == 2){
            nextPlayer.waitingTurn();
        }

        if (actionNum == 3){

        }
        if (actionNum == 4){

        }
        if (actionNum == 5){

        }

    }

}
