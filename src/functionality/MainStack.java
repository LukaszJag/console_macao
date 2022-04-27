package functionality;

public class MainStack {

    public Deck stack = new Deck();
    public boolean isMainStack;

    public MainStack(){
        Deck deck = new Deck();
        this.stack = deck.shuffleMainDeck();

    }

}
