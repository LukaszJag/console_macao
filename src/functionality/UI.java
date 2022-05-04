package functionality;

public class UI {

    public int maxTextinformationLength = 70;
    public int gapBeforeText = 6;
    public String[] actionToChoice = {"1 - Put card to game stack", "2 - Draw card from main stack", "3 - Have look at cards in your hand", "4 - Check function of action card",
            "5 - Get amount of card in another players hand", "6 - Show card on top of game stack"};


    public void parseTextToOneLineWindowText(String text){
        String line = "";

        int gapAfterText;

        for (int i = 0; i < maxTextinformationLength; i++) {
            System.out.print("-");
        }

        System.out.println();
        line = line + "|";

        for (int i = 0; i < gapBeforeText; i++) {
            line = line + " ";
        }

        line = line + text;

        gapAfterText = maxTextinformationLength - line.length() - 1;
        for (int i = 0; i < gapAfterText; i++) {
            line = line + " ";
        }
        line = line + "|";

        System.out.println(line);
    }

    public String closeTheWindow (String text){
        for (int i = 0; i < maxTextinformationLength; i++) {
            System.out.print("-");
        }
        System.out.println();
        return text;
    }


    public String parseTextToLineConsoleText(String text){
        String line = "";
        int gapAfterText;

        line = line + "|";

        for (int i = 0; i < gapBeforeText; i++) {
            line = line + " ";
        }

        line = line + text;

        gapAfterText = maxTextinformationLength - line.length() - 1;
        for (int i = 0; i < gapAfterText; i++) {
            line = line + " ";
        }
        line = line + "|";

        return line;
    }
    public void showHandInConsole(Deck hand){
        for (int i = 1; i < hand.getCardsInDeck()+1; i++) {
            System.out.println( i + " - " + hand.cards[i-1].introduceYourself());
        }
    }

    public void cardUI(Card card){

        String line = "       | ";

        if (card.getValue() < 11){
            line = line + card.getValue();
        }

        if (card.getValue() == 11){
            line = line + " Jack";
        }

        if(card.getValue() == 12){
            line = line + " Queen";
        }

        if (card.getValue() == 13){
            line = line + " King";
        }

        if (card.getValue() == 14){
            line = line + " Ace";
        }

        if (card.getColor() == 's'){
            line = line + " ♠";
        }

        if (card.getColor() == 'h'){
            line = line + " ♥";
        }

        if (card.getColor() == 'c'){
            line = line + " ♣";
        }

        if (card.getColor() == 'd'){
            line = line + " ♦";
        }
        line = line +" |   ";
        System.out.println(line);

    }

    public void playerDecisionWindow() {
        UI mainUI = new UI();
        int beforeText;
        String gap = "";

        for (int i = 0; i < maxTextinformationLength; i++) {
            System.out.print("-");
        }
        System.out.println();

        int lengthOfQuestion = ("|" + "What do you want to do?" +  "|").length();
        beforeText = maxTextinformationLength - lengthOfQuestion - gapBeforeText;

        // print question before action to choice
        System.out.print("|");
        for (int i = 0; i < gapBeforeText; i++) {
            System.out.print(" ");
        }

        System.out.print("What do you want to do?");

        for (int i = 0; i < beforeText; i++) {
            System.out.print(" ");
        }
        System.out.print("|");

        System.out.println();
        for (int i = 0; i < maxTextinformationLength; i++) {
            System.out.print("-");
        }

        System.out.println();

        // print action to choice
        for (int i = 0; i < actionToChoice.length; i++) {
            System.out.println(mainUI.parseTextToLineConsoleText(actionToChoice[i]));
        }

        for (int i = 0; i < maxTextinformationLength; i++) {
            System.out.print("-");
        }
        System.out.println();
    }
}
