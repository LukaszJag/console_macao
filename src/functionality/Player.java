package functionality;

import cardsTypes.Functional;

public class Player {

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

    // - Undone
    public void chooseAction() {

    }

    // - Undone
    public boolean putCardOnStack(Card card, Card cardOnStack, Player nextPlayer) {

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


    public void addCardToHand(MainStack mainStack) {
        Card card = new Card(0,'z',false);
        card = mainStack.stack.addCardFromMainStackTop(mainStack);
        hand.addCardToHand(card);


    }

    // - Undone
    public void waitingTurn() {

    }

}
