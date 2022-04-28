import functionality.*;
import functionality.MainStack;

import java.util.*;
public class Runner {

    public static void main(String[] args) {

        int playersNum;
        int humans;

        Scanner input = new Scanner(System.in);

        System.out.println("---------------------------------------");
        System.out.println("Welcome in Macao Card Game!");
        System.out.println("---------------------------------------");
        System.out.println("Set number of players (min 2 - max 4):");

        playersNum = input.nextInt();

        if(!(playersNum >= 2 && playersNum <= 4)){
            System.out.println("Wrong input (min 2 - max 4). Enter again:");
            playersNum = input.nextInt();
        }

        System.out.println("How many humans players:");

        humans = input.nextInt();

        if(!(humans < playersNum && playersNum > 1)){
            System.out.println("Wrong input. Enter again:");
            humans = input.nextInt();
        }

        Player[] players = new Player[playersNum];

        // Create players
        for (int i = 0; i < playersNum; i++) {
            int counter = 0;
            for (int j = 0; j < humans; j++) {
                Player hum = new Player(false);

                players[i] = hum;
                counter++;
            }
            for (int j = counter; j < playersNum; j++) {
                Player hum = new Player(true);
                players[i] = hum;
                counter++;
            }

        }

        MainStack mainStack = new MainStack();

        int cycle = 1;

        Card gameCard = new Card(0,'z', true);

        System.out.println("Start Cycle: " + cycle );

        while (gameCard.getIsAction()){
            gameCard = mainStack.stack.addCardFromMainStackTop(mainStack);
        }

        System.out.println("First Card:");
        System.out.println(gameCard.prettyName());

        // Fill each players' hand with 5 cards
        for (int i = 0; i < playersNum; i++) {
            for (int j = 0; j < 5; j++) {
                Card playerRadomCard = new Card();
                playerRadomCard = mainStack.stack.addCardFromMainStackTop(mainStack);
                players[i].hand.addCardToHand(playerRadomCard);
            }
        }

        int action;
        boolean isThereALooser = false;

        // Game Loop
        while(!isThereALooser) {

            action = input.nextInt();

        }
    }
}
