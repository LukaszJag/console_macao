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

        Player[] players = new Player[humans];
        Computer[] computers = new Computer[playersNum - humans];

        // Create players and computers

            for (int j = 0; j < humans; j++) {
                Player hum = new Player(false);
                players[j] = hum;
                Deck hand = new Deck();
                players[j].hand = hand;
            }
            for (int j = 0; j < (playersNum - humans); j++) {
                Computer com = new Computer(true);
                computers[j] = com;
                Deck hand = new Deck();
                    computers[j].hand = hand;
            }

        gameWindow.parseTextToOneLineWindowText("Gameplay");


        String[] playerTable = new String[playersNum];

        playerTable[0] = "Chosen number of players:" + playersNum;
        int counterForPlayers = 1;
        int counter = 0;

        for (int j = 0; j < humans; j++) {

            playerTable[counter] = "Player " + counterForPlayers + ": " + " Human";
            counterForPlayers++;
            counter++;

        }
        for (int j = 0; j < (playersNum - humans); j++) {
            playerTable[counter] = "Player " + counterForPlayers + ": " + " Computer";
            counter++;
            counterForPlayers++;

        }



        gameWindow.parseTextToManyLineWidnowText(playerTable);

        MainStack mainStack = new MainStack();

        Card gameCard = new Card(0, 'z', true);

        gameWindow.parseTextToManyLineWidnowText(new String[]{"Gameplay", "Chosen number of players: " + playersNum, "Cycle 1", "First Card:"});


        while (gameCard.getIsAction()) {
            gameCard = mainStack.stack.addCardFromMainStackTop(mainStack);
        }

        System.out.println((gameWindow.cardUI(gameCard)));

        // Fill each players' hand with 5 cards
        for (int i = 0; i < humans; i++) {
            players[i].hand.fillHandByCards(players[i].hand, mainStack, 5);
        }

        for (int i = 0; i < computers.length; i++) {
            computers[i].hand.fillHandByCards(computers[i].hand, mainStack, 5);
        }

        boolean isThereALooser = false;
        boolean sillyComputers = true;
        boolean endOfPlayerTurn = false;
        boolean isComputerDone = false;
        int playerChosenOption = 0;
        char playerAnswer = 'z';
        int turns = 0;
        int currenthuman = 0;
        int currentComputer = 0;
        int cycle = 0;
        int winnerIndex = -1;
        Card blankCard = new Card(0, 'z', false);

        Card computerCard = new Card(0, 'z', false);
        Card humanCard = new Card(0, 'z', false);
        Computer computer = new Computer(true);

        Action action = new Action();

        // Game Loop
        while (!isThereALooser) {

            turns = 0;
            currenthuman = 0;
            currentComputer = 0;

            while (turns < playersNum) {

                //Player loop
                while (currenthuman < humans) {


                    gameWindow.parseTextToOneLineWindowText("Player " + (currenthuman + 1) + " Turn:");

                    if (action.isPlayerAWinner(players[currenthuman])) {
                        isThereALooser = true;
                        winnerIndex = currenthuman;
                        endOfPlayerTurn = true;
                        isComputerDone = true;
                    }

                    while (!endOfPlayerTurn) {
                        if (action.isThisPlayerWait(currenthuman, action)) {
                            gameWindow.parseTextToManyLineWidnowText(new String[]{"Player " + currenthuman + " is waiting"});
                            currenthuman++;
                            isComputerDone = true;
                        } else {

                            if (action.isActionCardActive) {
                                humanCard = players[currenthuman].responseToActionCard(players[currenthuman], action, currenthuman, mainStack);
                                if (humanCard.value != 0) {
                                    gameCard = humanCard;
                                    humanCard = blankCard;
                                }
                            } else {

                                //Choice of player action
                                humanCard = players[currenthuman].playerAction(players[currenthuman], mainStack, gameCard, action, players);
                                if (humanCard.value != 0) {
                                    gameCard = humanCard;
                                    humanCard = blankCard;
                                }
                            }
                        }

                        endOfPlayerTurn = false;
                        currenthuman++;
                        turns++;
                    }

                    if (action.isComputerAWinner(computers[currentComputer + currenthuman])) {
                        isThereALooser = true;
                        winnerIndex = currenthuman + currentComputer;
                        endOfPlayerTurn = true;
                        isComputerDone = true;
                    }
                    //Computer loop
                    while (currentComputer < (playersNum - humans)) {


                        while (!isComputerDone) {

                            if (action.isThisPlayerWait(currentComputer + humans, action)) {
                                gameWindow.parseTextToManyLineWidnowText(new String[]{"Computer " + currentComputer + " is waiting"});
                                currentComputer++;
                                isComputerDone = true;
                            } else {
                                if (action.isActionCardActive) {
                                    computers[currentComputer].computeResponseToActionCard(computers[currentComputer], action, mainStack, currentComputer);
                                } else {
                                    computerCard = computers[currentComputer].computerPutCardOnGameStack(computer, gameCard);
                                    if (computerCard.getValue() != 0) {
                                        gameCard = computerCard;
                                        computerCard = blankCard;
                                    }
                                }
                            }

                            isComputerDone = false;
                            currentComputer++;
                            turns++;
                        }

                    }


                }
            }

            gameWindow.parseTextToOneLineWindowText("Player " + winnerIndex + " is a winner");
            gameWindow.parseTextToOneLineWindowText("Do you like macao console ?(y/n)");
            playerAnswer = input.next().charAt(0);

            if (playerAnswer == 'y') {
                gameWindow.parseTextToOneLineWindowText("Glad to hear that!");
            } else {
                String whyNo;
                gameWindow.parseTextToOneLineWindowText("Please, tell us why not?");
                whyNo = input.next();
                gameWindow.parseTextToOneLineWindowText("Thank you for your opinion!");
            }

        }
    }
}
