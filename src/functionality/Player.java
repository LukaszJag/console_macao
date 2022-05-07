package functionality;

import java.util.Scanner;

import cardsTypes.Functional;

public class Player {


    UI gameWindow = new UI();

    public boolean isComputer;

    public Deck hand = new Deck();

    public Player(boolean isComputer) {
        this.isComputer = isComputer;
    }

    public void showHand() {
        for (int i = 0; i < hand.cardsInDeck; i++) {
            System.out.println(hand.cards[i].introduceYourself());
        }
    }


    public boolean canPlayerPutCardOnStack(Card card, Card cardOnStack) {

        // k-d k-c
        if (!card.getIsAction()) {
            if (card.getValue() == cardOnStack.getValue()) {
                return true;
            }

            if (card.getColor() == cardOnStack.getColor()) {
                return true;
            }

        } else {
            // 2 3 K-h K-s
            // 4
            // j lub rządana wartość
            // q
            // A albo na asa albo żądany kolor

            // jh = 5d 4h/ js
            if (cardOnStack.getColor() == card.getColor()) {

                if (!cardOnStack.getIsAction()) {
                    return true;
                }

                if ((cardOnStack.getValue() == 2 || cardOnStack.getValue() == 3 || cardOnStack.getValue() == 13) &&
                        (card.getValue() == 2 || card.getValue() == 3 || card.getValue() == 13)) {
                    return true;
                }

            } else if (cardOnStack.getValue() == card.getValue()) {
                return true;

            }

        }

        if (card.getValue() == 12) {
            return true;
        }
        return false;
    }

    public void fireCard(int indexOfCard, Player player) {
        Card tmpCard = new Card(0, 'z', false);
        player.hand.cards[indexOfCard] = tmpCard;
        tmpCard = player.hand.cards[player.hand.getCardsInDeck() - 1];
        player.hand.cards[indexOfCard] = tmpCard;
        player.hand.cards[indexOfCard] = tmpCard;
        player.hand.cardsInDeck--;

    }

    public void addCardToHand(MainStack mainStack, Deck hand) {
        Card card = new Card(0, 'z', false);
        card = mainStack.stack.addCardFromMainStackTop(mainStack);
        hand.addCardToHand(card, hand);


    }

    public Card playerAction(Player currentPlayer, MainStack mainStack, Card gameCard, Action action, Player[] players) {
        Card card = new Card(0, 'z', false);

        Scanner input = new Scanner(System.in);

        int choice = 0;

        boolean endOfTurn = false;

        while (!endOfTurn) {

            gameWindow.playerDecisionWindow();
            choice = input.nextInt();

            if (choice == 1) {

                gameWindow.parseTextToOneLineWindowText("Which card ?");
                gameWindow.closeTheWindow("");
                gameWindow.showHandInConsole(currentPlayer.hand);

                int indexOfCardToPut = input.nextInt();

                //Catching Wrong index
                while (indexOfCardToPut > currentPlayer.hand.getCardsInDeck()) {
                    gameWindow.parseTextToLineConsoleText("Wrong index. Try again");
                    indexOfCardToPut = input.nextInt();
                }

                boolean isCardRight = false;


                card = currentPlayer.hand.cards[indexOfCardToPut - 1];

                //Checking is card right
                if (currentPlayer.canPlayerPutCardOnStack(card, gameCard)) {
                    isCardRight = true;
                    currentPlayer.fireCard(indexOfCardToPut - 1, currentPlayer);
                }

                if (isCardRight) {

                    System.out.println("You put " + card.introduceYourself() + " on game stack");
                    gameCard = card;

                    if (gameCard.isActionCard) {

                        action.isActionCardActive = true;

                        if (gameCard.value == 4) {
                            action.typeOfAction = 1;
                            action.volumeOfAction = 1;
                        }

                        if (gameCard.value == 3 || gameCard.value == 2 || (gameCard.value == 13 && (gameCard.color == 'h' || gameCard.color == 's'))) {

                            action.typeOfAction = 2;

                            if (gameCard.value == 13) {
                                action.volumeOfAction += 5;
                            } else {
                                action.volumeOfAction += gameCard.value;
                            }

                        }

                        if (gameCard.getValue() == 11) {
                            if(action.demandValue != 0){
                                gameWindow.parseTextToManyLineWidnowText(new String[]{"You can't [ut this card"});
                            }else {
                                gameWindow.parseTextToManyLineWidnowText(new String[]{"You can demand value of cards", "It must be no action card", "You also have to put this card"});
                                action.typeOfAction = 3;
                                action.demandValue = input.nextInt();

                                currentPlayer.showHand();
                                gameWindow.parseTextToManyLineWidnowText(new String[]{"Which card do you want to put"});

                                boolean goodCardWithDemandValue= false;
                                int demandCardResponse;
                                char wrongValueCardDecision;

                                while(!goodCardWithDemandValue){
                                    demandCardResponse = input.nextInt();

                                    if (currentPlayer.hand.cards[demandCardResponse - 1].getValue() == action.demandValue){
                                        currentPlayer.hand.putCardFromHand(hand,demandCardResponse);
                                        goodCardWithDemandValue = true;
                                    }else {
                                        gameWindow.parseTextToManyLineWidnowText(new String[]{"Wrong card", " Do you want choose again? y/n", "If no you will draw a card"});
                                        wrongValueCardDecision = input.next().charAt(0);

                                        if(wrongValueCardDecision =='y'){
                                            gameWindow.parseTextToManyLineWidnowText(new String[]{"Which card do you want to put"});
                                            currentPlayer.showHand();
                                        }else{
                                            currentPlayer.hand.addCardFromMainStackTop(mainStack);
                                            goodCardWithDemandValue = true;
                                        }
                                    }
                                }
                            }
                        }

                        if (gameCard.value == 14) {
                            gameWindow.parseTextToManyLineWidnowText(new String[]{"You can demand color of cards", "Chose from s, d, c or h. "});
                            action.typeOfAction = 4;
                            action.demandColor = input.next().charAt(0);

                        }
                    }
                    endOfTurn = true;
                } else {
                    gameWindow.parseTextToManyLineWidnowText(new String[]{"Wrong card", "Choose your action again"});
                }
            }

            if (choice == 2) {
                currentPlayer.addCardToHand(mainStack, currentPlayer.hand);
                gameWindow.parseTextToManyLineWidnowText(new String[]{"Card have been added to hand"});
                endOfTurn = true;

            }

            if (choice == 3) {
                currentPlayer.showHand();

            }

            if (choice == 4) {
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

            if (choice == 5) {
                for (int i = 0; i < players.length; i++) {
                    System.out.println("Player " + (i + 1) + " have " + players[i].hand.getCardsInDeck() + " cards.");
                }

            }

            if (choice == 6) {
                System.out.println(gameWindow.cardUI(gameCard));
            }

        }
        return card;
    }

    public Card responseToActionCard(Player currentPlayer, Action action,  int indexOfCurrentPlayer, MainStack mainStack) {
        Card card = new Card(0, 'z', false);
        Scanner input = new Scanner(System.in);
        char anwser = 'z';
        int chosenOption = -1;
        boolean isThereAResponse = false;

        while (!isThereAResponse) {

            //Waiting
            if (action.typeOfAction == 1) {

                gameWindow.parseTextToManyLineWidnowText(new String[]{"You have to wait " + action.volumeOfAction + " turn/s.", "Unless you put card with value 4"});

                gameWindow.parseTextToManyLineWidnowText(new String[]{"Do you want to put card? [y/n]"});
                anwser = input.next().charAt(0);

                if (anwser == 'y') {
                    gameWindow.parseTextToLineConsoleText("Which card?");
                    currentPlayer.showHand();
                    chosenOption = input.nextInt();

                    if (currentPlayer.hand.cards[chosenOption].getValue() == 4) {
                        gameWindow.parseTextToLineConsoleText("Good move. You are still in the game.");
                        card = currentPlayer.hand.cards[chosenOption];
                        currentPlayer.fireCard(chosenOption, currentPlayer);
                        action.volumeOfAction++;

                    } else {
                        gameWindow.parseTextToLineConsoleText("Wrong card, you are waiting.");
                        action.witchPlayerIsWaiting = indexOfCurrentPlayer;
                        action.howManyCycleOfWaiting = action.volumeOfAction;
                        action.volumeOfAction = 0;
                        action.isActionCardActive = false;
                    }

                }

                isThereAResponse = true;
            }

            if (action.typeOfAction == 2) {
                gameWindow.parseTextToManyLineWidnowText(new String[]{"You have to draw " + action.volumeOfAction + " card/s.",
                        "Unless you put card with value", "2, 3 , King s, King h"});

                gameWindow.parseTextToManyLineWidnowText(new String[]{"Do you want to put card? [y/n]"});
                anwser = input.next().charAt(0);

                if (anwser == 'y') {
                    gameWindow.parseTextToLineConsoleText("Which card?");
                    currentPlayer.showHand();
                    chosenOption = input.nextInt();

                    if (currentPlayer.hand.cards[chosenOption].getValue() == 2 || currentPlayer.hand.cards[chosenOption].getValue() == 3 ||
                            (currentPlayer.hand.cards[chosenOption].getValue() == 13 && (currentPlayer.hand.cards[chosenOption].getColor() == 's' ||
                                    currentPlayer.hand.cards[chosenOption].getColor() == 'h'))) {


                        gameWindow.parseTextToLineConsoleText("Good move. You don't draw any card.");
                        card = currentPlayer.hand.cards[chosenOption];
                        currentPlayer.fireCard(chosenOption, currentPlayer);

                        if (currentPlayer.hand.cards[chosenOption].getValue() == 13) {
                            action.volumeOfAction += 5;
                        } else {
                            action.volumeOfAction += currentPlayer.hand.cards[chosenOption].getValue();
                        }

                    } else {
                        gameWindow.parseTextToLineConsoleText("Wrong card, you draw." + action.volumeOfAction);
                        for (int i = 0; i < action.volumeOfAction; i++) {
                            currentPlayer.addCardToHand(mainStack, currentPlayer.hand);
                        }

                        action.isActionCardActive = false;
                        action.volumeOfAction = 0;
                    }
                }

                isThereAResponse = true;
            }

            if (action.typeOfAction == 3) {

                gameWindow.parseTextToManyLineWidnowText(new String[]{"Demand color is ", "" + action.demandColor,
                        "You have to put card with " + action.demandColor + " color.", "or you will draw a card"});

                gameWindow.parseTextToManyLineWidnowText(new String[]{"Do you want to put card? [y/n]"});
                anwser = input.next().charAt(0);

                if (anwser == 'y') {
                    gameWindow.parseTextToLineConsoleText("Which card?");
                    currentPlayer.showHand();
                    chosenOption = input.nextInt();

                    if (currentPlayer.hand.cards[chosenOption].getColor() == action.demandColor) {


                        gameWindow.parseTextToLineConsoleText("Good move. You put card.");

                        card = currentPlayer.hand.cards[chosenOption];
                        currentPlayer.fireCard(chosenOption, currentPlayer);
                        action.volumeOfAction += currentPlayer.hand.cards[chosenOption].getValue();

                        action.cycleOfDemandColor--;

                    } else {

                        gameWindow.parseTextToLineConsoleText("Wrong card, you are drawing a card.");
                        currentPlayer.addCardToHand(mainStack, currentPlayer.hand);
                        action.cycleOfDemandColor--;
                        if (action.cycleOfDemandColor <= 0) {
                            action.isActionCardActive = false;
                        }
                    }
                }

                isThereAResponse = true;
            }


            if (action.typeOfAction == 4) {
                gameWindow.parseTextToManyLineWidnowText(new String[]{"Demand value is ", "" + action.demandValue, "You must put card with " + action.demandValue + " value.",
                        "or you will draw a card"});
                gameWindow.parseTextToManyLineWidnowText(new String[]{"Do you want to put card? [y/n]"});
                anwser = input.next().charAt(0);

                if (anwser == 'y') {
                    gameWindow.parseTextToLineConsoleText("Which card?");
                    currentPlayer.showHand();
                    chosenOption = input.nextInt();
                    if (currentPlayer.hand.cards[chosenOption].getValue() == action.demandValue) {
                        gameWindow.parseTextToLineConsoleText("Good move. You put card.");

                        card = currentPlayer.hand.cards[chosenOption];
                        currentPlayer.fireCard(chosenOption, currentPlayer);
                        action.volumeOfAction += currentPlayer.hand.cards[chosenOption].getValue();

                        action.cycleOfDemandValue--;
                        if (action.cycleOfDemandValue <= 0) {
                            action.isActionCardActive = false;
                        }
                    }else{
                        gameWindow.parseTextToLineConsoleText("Wrong card, you are drawing a card.");
                        currentPlayer.addCardToHand(mainStack, currentPlayer.hand);
                        action.cycleOfDemandValue--;
                        if (action.cycleOfDemandValue <= 0) {
                            action.isActionCardActive = false;
                            action.demandValue = 0;
                        }
                    }
                }

                isThereAResponse = true;
            }

        }


        return card;
    }
}
