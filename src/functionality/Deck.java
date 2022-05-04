package functionality;

import java.util.Random;

public class Deck {

    public Card[] cards = new Card[52];
    int cardsInDeck = 0;
    public Card[] getCards() {
        return cards;
    }

    public Deck() {

        for (int i = 0; i < 52; i++) {
            Card card = new Card(0, 'z', false);
            this.cards[i] = card;
        }
        this.cardsInDeck = 0;

    }

    public void setCards(Card[] cards) {
        this.cards = cards;
    }

    public int getCardsInDeck() {
        return cardsInDeck;
    }

    public Card putCardFromHand(Deck hand, int index) {

        Card card = new Card(0, 'z', false);
        Card tmpCard = new Card(0, 'z', false);
        card = hand.cards[index];

        if (hand.getCardsInDeck() > 1) {

            if (index < hand.cardsInDeck) {

                tmpCard = hand.cards[hand.getCardsInDeck() - 1];
                hand.cards[index] = tmpCard;
            }
        }
        hand.cardsInDeck--;
        return card;
    }



    public void showCardsInDeck(Deck deck) {

        for (int i = 0; i < deck.cardsInDeck; i++) {
            System.out.println(cards[i].introduceYourself());
        }
    }

    public void addCardToHand(Card card) {
        this.cards[this.cardsInDeck] = card;
        this.cardsInDeck++;
    }

    public void fillHandByCards(Deck playersDeck, MainStack mainStack, int howManyCards){

        Card card = new Card(0, 'z',false);
        Card emptyCard = new Card(0, 'z',false);
        int cardsInDeck = mainStack.stack.getCardsInDeck();
        int cardsInHand = playersDeck.getCardsInDeck();

        for (int i = cardsInHand; i < howManyCards + cardsInHand; i++) {
            card = mainStack.stack.cards[cardsInDeck - 1];
            mainStack.stack.cards[cardsInDeck - 1] = emptyCard;
            mainStack.stack.cardsInDeck--;
            playersDeck.cards[playersDeck.cardsInDeck] = card;
            playersDeck.cardsInDeck++;
            cardsInDeck--;
        }


    }

    public Deck shuffleMainDeck() {

        FactoryOfMainDeck main = new FactoryOfMainDeck();
        Deck deck = new Deck();
        Deck resultDeck = new Deck();
        deck = main.deck;
        Random random = new Random();
        boolean[] isThereACard = new boolean[52];

        for (int i = 0; i < 52; i++) {
            isThereACard[i] = true;
        }

        int counter = 52;
        int rand;
        int i = 0;

        while (counter > 0) {

            rand = random.nextInt(52);

            if (isThereACard[rand] == true) {
                resultDeck.cards[i] = deck.cards[rand];
                i++;
                counter--;
            }
        }

        resultDeck.cardsInDeck = 52;
        return resultDeck;
    }

    public void setCardsInDeck(int cardsInDeck) {
        this.cardsInDeck = cardsInDeck;
    }

    public Card addCardFromMainStackTop(MainStack stack) {

        if (stack.stack.cardsInDeck == 0) {

            Card card = new Card(0, 'z', false);
            return card;

        } else {

            Card card = new Card();
            Card emptyCard = new Card(0, 'z', false);

            card = stack.stack.cards[stack.stack.cardsInDeck - 1];
            emptyCard = stack.stack.cards[stack.stack.cardsInDeck - 1];

            stack.stack.cardsInDeck--;

            return card;

        }
    }
}
