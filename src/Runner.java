import functionality.*;

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


        while (playersNum < 2 || playersNum > 4) {
            gameWindow.parseTextToOneLineWindowText("Wrong input (min 2 - max 4). Enter again:");
            gameWindow.closeTheWindow("");
            playersNum = input.nextInt();
        }
        while (!(playersNum >= 2 && playersNum <= 4)) {
            gameWindow.parseTextToOneLineWindowText("Wrong input (min 2 - max 4). Enter again:");

            playersNum = input.nextInt();
        }

        gameWindow.parseTextToOneLineWindowText("Choose number of human players:");

        gameWindow.closeTheWindow("");

        humans = input.nextInt();

        while (!(humans >= 1 && humans <= 4 && humans <= playersNum) || humans == 0) {
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
                players[j] = hum;
                Deck hand = new Deck();
                players[j].hand = hand;
                counter++;
            }
        }

        gameWindow.parseTextToOneLineWindowText("Gameplay");


        String[] playerTable = new String[playersNum];
        playerTable[0] = "Chosen number of players:" + playersNum;
        int counterForPlayers = 1;
        for (int i = 0; i < playersNum; i++) {


            if (players[i].isComputer) {
                playerTable[i] = "Player " + counterForPlayers + ": " + " Computer";
                counterForPlayers++;

            } else {
                playerTable[i] = "Player " + counterForPlayers + ": " + " Human";
                counterForPlayers++;
            }

        }
        gameWindow.parseTextToManyLineWidnowText(playerTable);


        MainStack mainStack = new MainStack();

        int cycle = 1;

        Card gameCard = new Card(0, 'z', true);

        gameWindow.parseTextToManyLineWidnowText(new String[]{"Gameplay", "Chosen number of players: " + playersNum, "Cycle 1", "First Card:"});


        while (gameCard.getIsAction()) {
            gameCard = mainStack.stack.addCardFromMainStackTop(mainStack);
        }

        System.out.println((gameWindow.cardUI(gameCard)));

        // Fill each players' hand with 5 cards
        for (int i = 0; i < playersNum; i++) {

            players[i].hand.fillHandByCards(players[i].hand, mainStack, 5);

        }

        int action = 0;
        int chosenOption = 0;
        boolean isThereALooser = false;
        int currentPlayer = 0;
        cycle = 0;
        Card blankCard = new Card(0, 'z', false);

        boolean isActionCardActive = false;
        int witchPlayerIsWaiting = -1;
        int howManyCycleOfWaiting = 0;
        int volumeOfAction = 0;
        int typeOfAction = 0;
        char demandColor = 'z';
        int demandValue = 0;
        int cycleOfDemandValue = 0;
        int cycleOfDemandColor = 0;
        Card computerCard = new Card(0, 'z', false);
        Computer computer = new Computer();
        char anwser = 'z';

        // Game Loop
        while (!isThereALooser) {

            cycle++;
            currentPlayer = 0;

            while (currentPlayer < playersNum) {

                if (witchPlayerIsWaiting == currentPlayer) {
                    gameWindow.parseTextToManyLineWidnowText(new String[]{"Player " + currentPlayer + " wait" });
                    howManyCycleOfWaiting--;

                    if (howManyCycleOfWaiting <= 0) {
                        witchPlayerIsWaiting = -1;
                    }
                    currentPlayer++;

                } else {

                    if (players[currentPlayer].isComputer == true) {

                        computerCard = computer.actionOfComputer(players[currentPlayer], gameCard, typeOfAction);
                        if (computerCard.getValue() == 0 && typeOfAction == 0) {

                            gameWindow.parseTextToManyLineWidnowText(new String[]{"Computer draw a card"});
                            currentPlayer++;

                        } else if (computerCard.getValue() != 0 && typeOfAction == 0) {


                            if (gameCard.isActionCard) {

                                gameWindow.parseTextToManyLineWidnowText(new String[]{"Computer put a action card on game stack"});

                                if (computerCard.getValue() == 4) {
                                    typeOfAction = 1;
                                    volumeOfAction = 1;
                                    computerCard = blankCard;
                                } else if ((computerCard.getValue() == 2 || computerCard.getValue() == 3 || computerCard.getValue() == 13) &&
                                        (computerCard.getValue() == 2 || computerCard.getValue() == 3 || computerCard.getValue() == 13)) {

                                    typeOfAction = 2;
                                    if (computerCard.getValue() == 13) {
                                        volumeOfAction = 5;
                                    } else {
                                        volumeOfAction = computerCard.getValue();
                                    }
                                    computerCard = blankCard;
                                } else if (computerCard.getValue() == 14) {
                                    demandColor = 'h';
                                    cycleOfDemandColor = 0;
                                } else if (computerCard.getValue() == 11) {
                                    cycleOfDemandValue = 0;
                                    demandValue = 7;
                                }

                            } else {
                                gameWindow.parseTextToManyLineWidnowText(new String[]{"Computer put a no action card on game stack"});
                            }
                            currentPlayer++;

                        } else if (computerCard.getValue() != 0 && typeOfAction == 1) {
                            witchPlayerIsWaiting = currentPlayer;
                            gameWindow.parseTextToManyLineWidnowText(new String[]{"Computer is waiting" + howManyCycleOfWaiting + "turns"});
                            howManyCycleOfWaiting--;
                            currentPlayer++;
                        } else if (computerCard.getValue() != 0 && typeOfAction == 2) {

                            for (int i = 0; i < volumeOfAction; i++) {
                                players[currentPlayer].addCardToHand(mainStack, players[currentPlayer].hand);
                            }

                            volumeOfAction = 0;
                            typeOfAction = 0;
                            currentPlayer++;
                        } else if (computerCard.getValue() != 0 && typeOfAction == 3) {
                            gameWindow.parseTextToManyLineWidnowText(new String[]{"Computer draw a card from stack"});
                            players[currentPlayer].addCardToHand(mainStack, players[currentPlayer].hand);
                            currentPlayer++;
                        } else if (computerCard.getValue() != 0 && typeOfAction == 4) {
                            gameWindow.parseTextToManyLineWidnowText(new String[]{"Computer draw a card from stack"});
                            players[currentPlayer].addCardToHand(mainStack, players[currentPlayer].hand);
                            currentPlayer++;
                        }
                    } else {
                        gameWindow.parseTextToOneLineWindowText("Player " + (currentPlayer + 1) + " Turn:");

                        boolean correctDecision = false;

                        while (!(correctDecision)) {


                            //If action card is active current player must handle with action:
                            if (isActionCardActive) {

                                if (typeOfAction == 1) {

                                    gameWindow.parseTextToManyLineWidnowText(new String[]{"You have to wait " + volumeOfAction + " turn/s.", "Unless you put card with value 4"});

                                    gameWindow.parseTextToManyLineWidnowText(new String[]{"Do you want to put card? [y/n]"});
                                    anwser = input.next().charAt(0);

                                    if (anwser == 'y') {
                                        gameWindow.parseTextToLineConsoleText("Which card?");
                                        players[currentPlayer].showHand();
                                        chosenOption = input.nextInt();

                                        if (players[currentPlayer].hand.cards[chosenOption].getValue() == 4) {
                                            gameWindow.parseTextToLineConsoleText("Good move. You are still in the game.");
                                            gameCard = players[currentPlayer].hand.cards[chosenOption];
                                            players[currentPlayer].giveAwayCard(chosenOption, players[currentPlayer]);
                                            volumeOfAction++;
                                            currentPlayer++;
                                            correctDecision = true;
                                            isActionCardActive = true;
                                        } else {
                                            gameWindow.parseTextToLineConsoleText("Wrong card, you are waiting.");
                                            witchPlayerIsWaiting = currentPlayer;
                                            howManyCycleOfWaiting = volumeOfAction;
                                            volumeOfAction = 0;
                                            isActionCardActive = false;
                                        }
                                    }
                                }

                                if (typeOfAction == 2) {
                                    gameWindow.parseTextToManyLineWidnowText(new String[]{"You have to draw " + volumeOfAction + " card/s.",
                                            "Unless you put card with value", "2, 3 , King s, King h"});

                                    gameWindow.parseTextToManyLineWidnowText(new String[]{"Do you want to put card? [y/n]"});
                                    anwser = input.next().charAt(0);

                                    if (anwser == 'y') {
                                        gameWindow.parseTextToLineConsoleText("Which card?");
                                        players[currentPlayer].showHand();
                                        chosenOption = input.nextInt();

                                        if (players[currentPlayer].hand.cards[chosenOption].getValue() == 2 || players[currentPlayer].hand.cards[chosenOption].getValue() == 3 ||
                                                (players[currentPlayer].hand.cards[chosenOption].getValue() == 13 && (players[currentPlayer].hand.cards[chosenOption].getColor() == 's' ||
                                                        players[currentPlayer].hand.cards[chosenOption].getColor() == 'h'))) {


                                            gameWindow.parseTextToLineConsoleText("Good move. You don't draw any card.");
                                            gameCard = players[currentPlayer].hand.cards[chosenOption];
                                            players[currentPlayer].giveAwayCard(chosenOption, players[currentPlayer]);
                                            if (players[currentPlayer].hand.cards[chosenOption].getValue() == 13) {
                                                volumeOfAction = volumeOfAction + 5;
                                            } else {
                                                volumeOfAction = volumeOfAction + players[currentPlayer].hand.cards[chosenOption].getValue();
                                            }

                                            currentPlayer++;
                                            correctDecision = true;
                                            isActionCardActive = true;

                                        } else {
                                            gameWindow.parseTextToLineConsoleText("Wrong card, you draw." + volumeOfAction);
                                            for (int i = 0; i < volumeOfAction; i++) {
                                                players[currentPlayer].addCardToHand(mainStack, players[currentPlayer].hand);
                                            }
                                            currentPlayer++;
                                            isActionCardActive = false;
                                            volumeOfAction = 0;
                                        }
                                    }

                                }

                                if (typeOfAction == 3) {
                                    gameWindow.parseTextToManyLineWidnowText(new String[]{"Demand color is ", "" + demandColor,
                                            "You have to put card with " + demandColor + " color.", "or you will draw a card"});

                                    gameWindow.parseTextToManyLineWidnowText(new String[]{"Do you want to put card? [y/n]"});
                                    anwser = input.next().charAt(0);

                                    if (anwser == 'y') {
                                        gameWindow.parseTextToLineConsoleText("Which card?");
                                        players[currentPlayer].showHand();
                                        chosenOption = input.nextInt();

                                        if (players[currentPlayer].hand.cards[chosenOption].getColor() == demandColor) {


                                            gameWindow.parseTextToLineConsoleText("Good move. You put card.");

                                            gameCard = players[currentPlayer].hand.cards[chosenOption];
                                            players[currentPlayer].giveAwayCard(chosenOption, players[currentPlayer]);
                                            volumeOfAction = volumeOfAction + players[currentPlayer].hand.cards[chosenOption].getValue();

                                            cycleOfDemandColor--;
                                            currentPlayer++;
                                            correctDecision = true;
                                            isActionCardActive = true;
                                        } else {
                                            gameWindow.parseTextToLineConsoleText("Wrong card, you are drawing a card.");
                                            players[currentPlayer].addCardToHand(mainStack, players[currentPlayer].hand);
                                            cycleOfDemandColor--;
                                            if (cycleOfDemandColor <= 0) {
                                                isActionCardActive = false;
                                            }
                                        }
                                    }

                                }

                                if (typeOfAction == 4) {
                                    gameWindow.parseTextToManyLineWidnowText(new String[]{"Demand value is ", "" + demandColor, "You must put card with " + demandValue + " color.",
                                            "or you will draw a card"});
                                    gameWindow.parseTextToManyLineWidnowText(new String[]{"Do you want to put card? [y/n]"});
                                    anwser = input.next().charAt(0);

                                    if (anwser == 'y') {
                                        gameWindow.parseTextToLineConsoleText("Which card?");
                                        players[currentPlayer].showHand();
                                        chosenOption = input.nextInt();
                                        if (players[currentPlayer].hand.cards[chosenOption].getValue() == demandValue) {
                                            gameWindow.parseTextToLineConsoleText("Good move. You put card.");

                                            gameCard = players[currentPlayer].hand.cards[chosenOption];
                                            players[currentPlayer].giveAwayCard(chosenOption, players[currentPlayer]);
                                            volumeOfAction = volumeOfAction + players[currentPlayer].hand.cards[chosenOption].getValue();

                                            cycleOfDemandValue--;
                                            currentPlayer++;
                                            correctDecision = true;
                                            if (cycleOfDemandValue <= 0) {
                                                isActionCardActive = false;
                                            }
                                        }
                                    }

                                }

                            } else {

                                gameWindow.playerDecisionWindow();
                                action = input.nextInt();

                                if (action == 1) {

                                    gameWindow.parseTextToOneLineWindowText("Which card ?");
                                    gameWindow.closeTheWindow("");
                                    gameWindow.showHandInConsole(players[currentPlayer].hand);

                                    int indexOfCardToPut = input.nextInt();

                                    //Catching Wrong index
                                    while (indexOfCardToPut > players[currentPlayer].hand.getCardsInDeck()) {
                                        gameWindow.parseTextToLineConsoleText("Wrong index. Try again");
                                        indexOfCardToPut = input.nextInt();
                                    }

                                    boolean isCardRight = false;
                                    Card card = new Card(0, 'z', false);

                                    card = players[currentPlayer].hand.cards[indexOfCardToPut - 1];

                                    //Checking is card right
                                    if (players[currentPlayer].putCardOnStack(card, gameCard, players[currentPlayer])) {
                                        isCardRight = true;
                                        players[currentPlayer].giveAwayCard(indexOfCardToPut - 1, players[currentPlayer]);
                                    }

                                    if (isCardRight) {

                                        System.out.println("You put " + card.introduceYourself() + " on game stack");
                                        gameCard = card;

                                        if (gameCard.isActionCard) {

                                            isActionCardActive = true;

                                            if (gameCard.value == 4) {
                                                typeOfAction = 1;
                                                volumeOfAction = 1;
                                            }

                                            if (gameCard.value == 3 || gameCard.value == 2 || (gameCard.value == 13 && (gameCard.color == 'h' || gameCard.color == 's'))) {

                                                typeOfAction = 2;

                                                if (gameCard.value == 13) {
                                                    volumeOfAction = volumeOfAction + 5;
                                                } else {
                                                    volumeOfAction = volumeOfAction + gameCard.getValue();
                                                }

                                            }

                                            if (gameCard.getValue() == 11) {
                                                gameWindow.parseTextToManyLineWidnowText(new String[]{"You can demand value of cards", "It must be no action card", "You also have to put this card"});
                                                typeOfAction = 3;
                                                demandValue = input.nextInt();
                                                // Putting card write latter
                                            }

                                            if (gameCard.value == 14) {
                                                gameWindow.parseTextToManyLineWidnowText(new String[]{"You can demand color of cards", "Chose from s, d, c or h. "});
                                                typeOfAction = 4;
                                                demandColor = input.next().charAt(0);

                                            }
                                        }
                                        correctDecision = true;
                                    } else {
                                        gameWindow.parseTextToManyLineWidnowText(new String[]{"Wrong card", "Choose your action again"});
                                    }
                                }

                                if (action == 2) {
                                    players[currentPlayer].addCardToHand(mainStack, players[currentPlayer].hand);

                                    gameWindow.parseTextToManyLineWidnowText(new String[]{"Card have been added"});


                                    correctDecision = true;
                                }

                                if (action == 3) {
                                    players[currentPlayer].showHand();

                                }

                                if (action == 4) {
                                    gameWindow.parseTextToOneLineWindowText("which card do you want to check?");
                                    System.out.println("|      Choose card number form 2-14                                  |");
                                    gameWindow.closeTheWindow("");

                                    int answer = input.nextInt();

                                    if (!(answer >= 2 && answer <= 14)) {
                                        gameWindow.closeTheWindow("");
                                        System.out.println("Wrong input! Choose card number from 2-14 and enter again:");
                                        gameWindow.closeTheWindow("");
                                        answer = input.nextInt();
                                    }

                                    if (answer <= 10 && answer > 4 || answer == 12) {
                                        gameWindow.closeTheWindow("");
                                        System.out.println("| " + answer + " ♠/♣/♥/♦ |");
                                        System.out.println("This card is a non-functional card.");
                                        gameWindow.closeTheWindow("");
                                    }

                                    if (answer == 2 || answer == 3) {
                                        gameWindow.closeTheWindow("");
                                        System.out.println("| " + answer + " ♠/♣/♥/♦ |");
                                        System.out.println("This card is a functional card.");
                                        System.out.println("If you put this card, it will add " + answer + " extra cards to the next player.");
                                        System.out.println("Cards add up to each other and could be used on another attacking card.");
                                        gameWindow.closeTheWindow("");
                                    }

                                    if (answer == 4) {
                                        gameWindow.closeTheWindow("");
                                        System.out.println("| " + answer + " ♠/♣/♥/♦ |");
                                        System.out.println("This card is a functional card.");
                                        System.out.println("If you put this card, it will force the next player to skip their next turn.");
                                        System.out.println("Cards add up to each other.");
                                        gameWindow.closeTheWindow("");
                                    }

                                    if (answer == 11) {
                                        gameWindow.closeTheWindow("");
                                        System.out.println("| " + answer + " ♠/♣/♥/♦ |");
                                        System.out.println("This card is a functional card.");
                                        System.out.println("If you put this card you could demand any value of a non-functional card.");
                                        System.out.println("Each player (including you) have to put demanded value.");
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
                                        System.out.println("| " + answer + " ♠/♣/♥/♦ |");
                                        System.out.println("This card is a functional card.");
                                        System.out.println("If you put this card you could demand new color.");
                                        System.out.println("Next player has to put his card in demanded color.");
                                        gameWindow.closeTheWindow("");
                                    }

                                }

                                if (action == 5) {
                                    for (int i = 0; i < playersNum; i++) {
                                        System.out.println("Player " + (i + 1) + " have " + players[i].hand.getCardsInDeck() + " cards.");
                                    }


                                }


                                if (action == 6) {
                                    System.out.println(gameWindow.cardUI(gameCard));
                                }

                                if (correctDecision == true) {

                                    currentPlayer++;

                                }
                            }
                        }
                    }
                }
            }
        }
        gameWindow.parseTextToOneLineWindowText("Player " + currentPlayer + " is a winner");
        gameWindow.parseTextToOneLineWindowText("Do you like macao console ?(y/n)");
        anwser = input.next().charAt(0);

        if (anwser == 'y') {
            gameWindow.parseTextToOneLineWindowText("Glad to hear that!");
        } else {
            String whyNo;
            gameWindow.parseTextToOneLineWindowText("Please, tell us why not?");
            whyNo = input.next();
            gameWindow.parseTextToOneLineWindowText("Thank you for your opinion!");
        }

    }
}
