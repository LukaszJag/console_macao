package functionality;

public class Computer {

    public Card actionOfComputer(Player computer, Card cardOnStack, int typeOfPreviousAction) {

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
}


