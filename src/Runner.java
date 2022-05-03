import functionality.*;
import functionality.MainStack;

import java.util.*;
public class Runner {

    public static void main(String[] args) {

        int playersNum;
        int humans;
        UI gameWindow = new UI();
        Scanner input = new Scanner(System.in);

        gameWindow.parseTextToOneLineWindowText("Welcome in Macao Card Game!");
        gameWindow.parseTextToOneLineWindowText("Set number of players (min 2 - max 4):");

        playersNum = input.nextInt();

        if(playersNum < 2 || playersNum > 4){
            gameWindow.parseTextToOneLineWindowText("Wrong input (min 2 - max 4). Enter again:");
            playersNum = input.nextInt();
        }

        gameWindow.parseTextToOneLineWindowText("Choose number of human players:");

        humans = input.nextInt();

        if(!(playersNum >= 2 && playersNum <= 4)){
            gameWindow.parseTextToOneLineWindowText("Wrong input (min 2 - max 4). Enter again:");
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

        gameWindow.parseTextToOneLineWindowText("Gameplay");
        gameWindow.parseTextToOneLineWindowText("Chosen number of players: " + playersNum);

        // print all the players with exeption if it's computer or human

        MainStack mainStack = new MainStack();

        int cycle = 1;

        Card gameCard = new Card(0,'z', true);

        gameWindow.parseTextToOneLineWindowText("Cycle " + 1);

        while (gameCard.getIsAction()){
            gameCard = mainStack.stack.addCardFromMainStackTop(mainStack);
        }

        gameWindow.parseTextToOneLineWindowText("First Card:");

        gameWindow.cardUI(gameCard);

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

        // Game Loop
        while(!isThereALooser) {

            cycle++;
            currentPlayer = 0;
            while (currentPlayer < playersNum) {

                gameWindow.parseTextToOneLineWindowText("Player " + (currentPlayer+1) +  " Turn:");

                int volumeOfAction = 0;

                boolean correctDecision = false;
                gameWindow.playerDecisionWindow();

                while(!(correctDecision)) {

                    action = input.nextInt();

                    if (action == 1) {

                        gameWindow.parseTextToOneLineWindowText("Witch card ?");
                        gameWindow.showHandInConsole(players[currentPlayer].hand);

                        int cardToPut = input.nextInt();

                        while (cardToPut > players[currentPlayer].hand.getCardsInDeck()) {
                            gameWindow.parseTextToLineConsoleText("Wrong input. Try again");
                            cardToPut = input.nextInt();
                        }

                        Card card = new Card(0, 'z', false);
                        card = players[currentPlayer].hand.putCardFromHand(players[currentPlayer].hand, cardToPut);
                        if (players[currentPlayer].putCardOnStack(card, gameCard, players[currentPlayer + 1])){
                            System.out.println("You put " + card.introduceYourself() + " on game stack");
                            correctDecision = true;
                        }else{
                            System.out.println("This card can't be put on game stack");
                            System.out.println("Choose again:");
                        }

                    }

                    if (action == 2) {
                        players[currentPlayer].addCardToHand(mainStack);
                        correctDecision = true;
                    }

                    if (action == 3) {
                        players[currentPlayer].showHand();
                        gameWindow.playerDecisionWindow();;
                    }

                    if (action == 4) {

                    }

                    if (action == 5) {
                        for (int i = 0; i < playersNum; i++) {
                            System.out.println("Player " + i + " have " + players[i].hand.getCardsInDeck() + " cards.");
                        }
                    }

                    if (action == 6){
                        gameWindow.cardUI(gameCard);
                        gameWindow.playerDecisionWindow();
                    }

                    if (correctDecision == true){
                        currentPlayer++;
                    }

                }
            }
        }
    }
}
