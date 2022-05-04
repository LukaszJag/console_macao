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
        gameWindow.closeTheWindow("");

        playersNum = input.nextInt();

        // do a loop? doesn't work for more than 1 false input
        if (playersNum < 2 || playersNum > 4) {
            gameWindow.parseTextToOneLineWindowText("Wrong input (min 2 - max 4). Enter again:");
            gameWindow.closeTheWindow("");
            playersNum = input.nextInt();
        }

        gameWindow.parseTextToOneLineWindowText("Choose number of human players:");
        gameWindow.closeTheWindow("");

        humans = input.nextInt();

        if (!(humans >= 1 && humans <= 4) || humans == 0) {
            gameWindow.parseTextToOneLineWindowText("Wrong input (min 1 - max 4). Enter again:");
            gameWindow.closeTheWindow("");
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
        for (int i = 1; i <= playersNum; i++) {
            if (i <= humans) {
                System.out.println("|      Player " + i + ": Human                                               |");
            } else {
                System.out.println("|      Player " + i + ": Computer                                            |");
            }
        }
        gameWindow.closeTheWindow("");


        MainStack mainStack = new MainStack();

        int cycle = 1;

        Card gameCard = new Card(0, 'z', true);

        gameWindow.parseTextToLineConsoleText("Cycle " + 1);

        while (gameCard.getIsAction()) {
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
        cycle = 0;

        // Game Loop
        while (!isThereALooser) {

            cycle++;
            currentPlayer = 0;
            while (currentPlayer < playersNum) {

                gameWindow.parseTextToOneLineWindowText("Player " + (currentPlayer + 1) + " Turn:");

                int volumeOfAction = 0;

                boolean correctDecision = false;
                gameWindow.playerDecisionWindow();

                while (!(correctDecision)) {

                    action = input.nextInt();

                    if (action == 1) {

                        gameWindow.parseTextToOneLineWindowText("Which card ?");
                        gameWindow.showHandInConsole(players[currentPlayer].hand);

                        int cardToPut = input.nextInt();

                        while (cardToPut > players[currentPlayer].hand.getCardsInDeck()) {
                            gameWindow.parseTextToLineConsoleText("Wrong input. Try again");
                            cardToPut = input.nextInt();
                        }

                        Card card = new Card(0, 'z', false);
                        card = players[currentPlayer].hand.putCardFromHand(players[currentPlayer].hand, cardToPut);
                        if (players[currentPlayer].putCardOnStack(card, gameCard, players[currentPlayer + 1])) {
                            System.out.println("You put " + card.introduceYourself() + " on game stack");
                            correctDecision = true;
                        } else {
                            System.out.println("This card can't be put on game stack");
                            System.out.println("Choose again:");
                        }

                    }

                    if (action == 2) {
                        players[currentPlayer].addCardToHand(mainStack);
                        // print to the player added card
                        gameWindow.parseTextToOneLineWindowText("Added card: ");
                        gameWindow.closeTheWindow("");
                        correctDecision = true;
                    }

                    if (action == 3) {
                        players[currentPlayer].showHand();
                        gameWindow.playerDecisionWindow();
                    }

                    if (action == 4) {
                        gameWindow.parseTextToOneLineWindowText("which card do you want to check?");
                        System.out.println("|      Choose card number form 2-14                                  |");
                        gameWindow.closeTheWindow("");

                        int answer = input.nextInt();
                        String answerCard = "| " + answer + " ♠/♣/♥/♦ |";

                        if (!(answer >= 2 && answer <= 14)) {
                            gameWindow.closeTheWindow("");
                            System.out.println("Wrong input! Choose card number from 2-14 and enter again:");
                            gameWindow.closeTheWindow("");
                            answer = input.nextInt();
                        }

                        if (answer <= 10 && answer > 4 || answer == 12) {
                            gameWindow.closeTheWindow("");
                            System.out.println(answerCard);
                            System.out.println("This card is a non-functional card.");
                            gameWindow.closeTheWindow("");
                        }

                        if (answer == 2 || answer == 3) {
                            gameWindow.closeTheWindow("");
                            System.out.println(answerCard);
                            System.out.println("This card is a functional card.");
                            System.out.println("If you put this card, it will add " + answer + " extra cards to the next player.");
                            System.out.println("Cards add up to each other and could be used on another attacking card.");
                            gameWindow.closeTheWindow("");
                        }

                        if (answer == 4) {
                            gameWindow.closeTheWindow("");
                            System.out.println(answerCard);
                            System.out.println("This card is a functional card.");
                            System.out.println("If you put this card, it will force the next player to miss their next turn.");
                            System.out.println("Cards add up to each other.");
                            gameWindow.closeTheWindow("");
                        }

                        if (answer == 11) {
                            gameWindow.closeTheWindow("");
                            System.out.println(answerCard);
                            System.out.println("This card is a functional card.");
                            System.out.println("If you put this card you could claim any value of a non-functional card.");
                            System.out.println("Each player (including you) have to put claimed value.");
                            gameWindow.closeTheWindow("");
                        }

                        if (answer == 13) {
                            gameWindow.closeTheWindow("");
                            String answerCard1 = "| " + answer + " ♣/♦ |";
                            System.out.println(answerCard1);
                            System.out.println("This card is a non-functional card.");
                            System.out.println("");
                            String answerCard2 = "| " + answer + " ♥/♠ |";
                            System.out.println(answerCard2);
                            System.out.println("This card is a functional card.");
                            System.out.println("If you put King ♥, it will add 5 cards to the next player.");
                            System.out.println("If you put King ♠, it will add 5 cards to the previous player.");
                            System.out.println("Cards add up to each other. You could also put 2/3 cards to defend yourself.");
                            gameWindow.closeTheWindow("");
                        }

                        if (answer == 14) {
                            gameWindow.closeTheWindow("");
                            System.out.println(answerCard);
                            System.out.println("This card is a functional card.");
                            System.out.println("If you put this card you could claim new color.");
                            System.out.println("Next player has to put his card in claimed color.");
                            gameWindow.closeTheWindow("");
                        }

                        gameWindow.playerDecisionWindow();
                    }

                    if (action == 5) {
                        for (int i = 0; i < playersNum; i++) {
                            System.out.println("Player " + (i + 1) + " have " + players[i].hand.getCardsInDeck() + " cards.");
                        }

                        gameWindow.playerDecisionWindow();
                    }

                    if (action == 6) {
                        gameWindow.cardUI(gameCard);
                        gameWindow.playerDecisionWindow();
                    }

                    if (correctDecision == true) {
                        currentPlayer++;
                    }

                }
            }
        }
    }
}
