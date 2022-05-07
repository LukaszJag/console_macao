package functionality;

public class Computer extends Player{

    UI gameWindow = new UI();
    public Computer(boolean isComputer) {
        super(isComputer);
    }

    public Card actionOfComputer(Player computer, Card cardOnStack) {

        Card card = new Card(0, 'z', false);
        boolean hasComputerAGoodCard = false;
        int indexOfGoodCard = -1;
        for (int i = 0; i < computer.hand.cardsInDeck; i++) {

            // k-d k-c
            if (!card.getIsAction()) {
                if (card.getValue() == cardOnStack.getValue()) {
                    hasComputerAGoodCard = true;
                }

                if (card.getColor() == cardOnStack.getColor()) {
                    hasComputerAGoodCard = true;
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
                        hasComputerAGoodCard = true;
                    }

                    if ((cardOnStack.getValue() == 2 || cardOnStack.getValue() == 3 || cardOnStack.getValue() == 13) &&
                            (card.getValue() == 2 || card.getValue() == 3 || card.getValue() == 13)) {
                        hasComputerAGoodCard = true;
                    }

                } else if (cardOnStack.getValue() == card.getValue()) {
                    hasComputerAGoodCard = true;

                }

            }

            if (card.getValue() == 12) {
                hasComputerAGoodCard = true;
            }
            if (hasComputerAGoodCard == true) {
                indexOfGoodCard = i;
            }
            hasComputerAGoodCard = false;

        }

        if (hasComputerAGoodCard == true) {
            card = computer.hand.cards[indexOfGoodCard];
            computer.hand.cards[indexOfGoodCard] = computer.hand.cards[computer.hand.getCardsInDeck()-1];
            computer.hand.cardsInDeck--;
            return card;
        } else {
            return card;
        }
    }

    public Card computeResponseToActionCard(Computer computer, Action action, MainStack mainStack, int indexOfPlayer){
        Card card = new Card(0, 'z', false);

        if (action.typeOfAction == 1) {
            action.witchPlayerIsWaiting = indexOfPlayer;

            gameWindow.parseTextToManyLineWidnowText(new String[]{"Computer is waiting " + action.volumeOfAction + " turns"});

            action.volumeOfAction = 0;
            action.isActionCardActive = false;
            action.typeOfAction = 0;
        }

        if (action.typeOfAction == 2) {
            gameWindow.parseTextToManyLineWidnowText(new String[]{"Computer draw " + action.volumeOfAction + " cards"});

            for (int i = 0; i < action.volumeOfAction; i++) {
                computer.hand.addCardFromMainStackTop(mainStack);
            }

            action.volumeOfAction = 0;
            action.isActionCardActive = false;
            action.typeOfAction = 0;
        }

        if (action.typeOfAction == 3) {
            gameWindow.parseTextToManyLineWidnowText(new String[]{"Computer draw a card"});
            computer.hand.addCardFromMainStackTop(mainStack);

            action.volumeOfAction = 0;
            action.isActionCardActive = false;
            action.typeOfAction = 0;
        }

        if (action.typeOfAction == 4) {
            gameWindow.parseTextToManyLineWidnowText(new String[]{"Computer draw a card"});
            computer.hand.addCardFromMainStackTop(mainStack);

            action.volumeOfAction = 0;
            action.isActionCardActive = false;
            action.typeOfAction = 0;
        }

        return card;
    }

    public Card computerPutCardOnGameStack(Computer computer, Card gameCard){
        Card card = new Card(0, 'z', false);

        int indexOfCard = -1;

        for (int i = 0; i < computer.hand.cardsInDeck; i++) {
            if(computer.actionOfComputer(computer, computer.hand.cards[i]).getValue() != 0 ){
                card =  computer.hand.cards[i];
                indexOfCard = i;
            }
        }
        if(card.getIsAction() == true){
            card = new Card(0, 'z', false);
        }

        if (card.getValue() != 0){
            computer.fireCard(indexOfCard, computer);
        }

        return  card;
    }
}


