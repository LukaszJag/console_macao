package functionality;

public class FactoryOfMainDeck {

    public Deck deck = new Deck();

    public FactoryOfMainDeck() {

        int indexInDeck = 0;

        for (int i = 0; i < 4; i++) {

            char color = 'z';

            if (i == 0) {
                color = 's'; // spades
            }
            if (i == 1) {
                //
                color = 'c'; // clubs
            }
            if (i == 2) {
                color = 'h'; // heart
            }
            if (i == 3) {
                color = 'd'; // diamonds
            }

            for (int j = 2; j <= 14; j++) {

                if (j >= 5 && j <= 10) {

                    Card card = new Card(j, color, false);
                    this.deck.cards[indexInDeck] = card;
                    indexInDeck++;

                } else {

                    if(j == 13 && (i == 1 || i ==3)){
                        Card card = new Card(j, color, false);
                        this.deck.cards[indexInDeck] = card;
                        indexInDeck++;
                    }else{
                        Card card = new Card(j, color, true);
                        this.deck.cards[indexInDeck] = card;
                        indexInDeck++;
                    }

                }

            }

        }
        this.deck.cardsInDeck = 52;
    }
}
