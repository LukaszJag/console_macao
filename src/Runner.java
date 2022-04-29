import functionality.*;
import functionality.MainStack;

import java.util.*;
public class Runner {

    public static void main(String[] args) {

        int playersNum;
        int humans;

        Scanner input = new Scanner(System.in);

        System.out.println("---------------------------------------");
        System.out.println("Welcome in Macao Card Game!");
        System.out.println("---------------------------------------");
        System.out.println("Set number of players (min 2 - max 4):");

        playersNum = input.nextInt();

        if(playersNum < 2 || playersNum > 4){
            System.out.println("Wrong input (min 2 - max 4). Enter again:");
            playersNum = input.nextInt();
        }

        System.out.println("Choose number of human players:");

        humans = input.nextInt();

        if(!(playersNum >= 2 && playersNum <= 4)){
            System.out.println("Wrong input (min 2 - max 4). Enter again:");
            humans = input.nextInt();
        }

        Player[] players = new Player[playersNum];

        // Create players
        for (int i = 0; i < playersNum; i++) {
            int counter = 0;
            for (int j = 0; j < humans; j++) {
                Player hum = new Player(false);
                players[i] = hum;
                Deck hand = new Deck();
                players[i].hand = hand;
                counter++;
            }
            for (int j = counter; j < playersNum; j++) {
                Player hum = new Player(true);
                players[i] = hum;
                Deck hand = new Deck();
                players[i].hand = hand;
                counter++;
            }
        }

        System.out.println("------ Gameplay ------");
        System.out.println("Chosen number of players: " + playersNum);

        // print all the players with exeption if it's computer or human


        MainStack mainStack = new MainStack();

        for (int i = 0; i < 52; i++) {
            mainStack.stack.cards[i].introduceYourself();
            //System.out.println(mainStack.stack.cards[i].introduceYourself());
            // don't print whole deck
        }
        int cycle = 1;

        Card gameCard = new Card(0,'z', true);

        System.out.println("--- Cycle " + 1 + " ---" );

        while (gameCard.getIsAction()){
            gameCard = mainStack.stack.addCardFromMainStackTop(mainStack);
        }

        System.out.println("First Card:");
        System.out.println("-------------");

        System.out.println(gameCard.introduceYourself());
        System.out.println("-----------");

        // Fill each players' hand with 5 cards
        for (int i = 0; i < playersNum; i++) {
            for (int j = 0; j < 5; j++) {
                Card playerRadomCard = new Card();
                playerRadomCard = mainStack.stack.addCardFromMainStackTop(mainStack);
                players[i].hand.addCardToHand(playerRadomCard);
            }
        }

                                    /// coding done ///

        int action = 0;
        boolean isThereALooser = false;
        int currentPlayer = 0;
        cycle = 0 ;
        UI gameWindow = new UI();
        // Game Loop
        while(!isThereALooser) {

            cycle++;
            while (currentPlayer < playersNum) {

                int volumeOfAction = 0;

                boolean correctDecision = false;
                gameWindow.playerDecisionWindow();
                while(!(correctDecision)) {
                    action = input.nextInt();
                    if (action == 1) {
                        gameWindow.whichCardFromHand(players[currentPlayer].hand);

                        int cardToPut = input.nextInt();
                        while (cardToPut > players[currentPlayer].hand.getCardsInDeck()) {
                            gameWindow.parseTextToLineConsoleText("Wrong input. Try again");
                            cardToPut = input.nextInt();
                        }
                        Card card = new Card(0, 'z', false);
                        card = players[currentPlayer].hand.putCardFromHand(players[currentPlayer].hand, cardToPut);
                        players[currentPlayer].putCardOnStack(card, gameCard, players[currentPlayer + 1]);

                    }
                    if (action == 2) {
                        players[currentPlayer].addCardToHand(mainStack);
                    }
                    if (action == 3) {
                        players[currentPlayer].showHand();
                    }

                    if (action == 4) {

                    }

                    if (action == 5) {

                    }
                }
            }
        }
    }
}
