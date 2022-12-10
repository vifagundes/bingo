import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import static java.lang.System.exit;

public class Main {
    public static final int bingoCardSize  = 5;
    public static final int poolSize       = 61;
    public static final int numberOfRounds = poolSize - 1;

    public static void main(String[] args) {

        String[] players           = getPlayers();
        int      qntPlayers        = players.length;
        int      manualOrAutomatic = getManualOrAutomatic();

        int[][] bingoCardsList          = new int[qntPlayers][bingoCardSize];
        int[]   scoredPointsListEmpty   = new int[qntPlayers];
        int[][] updatedScoredPointsList = new int[1][qntPlayers];
        int[]   pool                    = new int[numberOfRounds];
        int[]   randomPool              = getRandomPool(pool);
        int[]   drawNumberList          = new int[numberOfRounds];

        for (int i = 0; i < players.length; i++) {  //MENU PARA PERGUNTAR SE VAI SER MODO MANUAL OU AUTOMATICO
            switch (manualOrAutomatic) {
                case 1:
                    bingoCardsList[i] = getBingoCardsAutomatic(pool);
                    break;
                case 2:
                    System.out.printf("DIGITE OS %d NUMEROS DA CARTELA %s (SEPARE CADA NUMERO POR , )\n", bingoCardSize, players[i]);
                    bingoCardsList[i] = getBingoCardsManual();
                    break;
                default:
                    manualOrAutomatic = getManualOrAutomatic();
                    i--;
                    break;
            }
        }

        for (int i = 0; i < numberOfRounds; i++) {  // MENU PRINCIPAL DO JOGO
            int nextRound = getNextRound();
            int round = i + 1;
            switch (nextRound) {
                case 1:
                    int drawNumber = getPrizeDraw(randomPool, i);
                    drawNumberList[i] = drawNumber;
                    System.out.printf("\nRODADA - %d\nNUMERO SORTEADO - %d\n", round, drawNumber);
                    updatedScoredPointsList[0] = getScoredPointsList(drawNumber, bingoCardsList, scoredPointsListEmpty);
                    boolean bingo = getBingo(updatedScoredPointsList);
                    if (bingo) {
                        for (int j = 0; j < updatedScoredPointsList[0].length; j++) {
                            if (updatedScoredPointsList[0][j] == bingoCardSize) {
                                System.out.printf("\n\nPARABENS %S, VOCE FEZ BINGO!!!\n", players[j]);
                                printStatistics(drawNumberList, round);
                                exit(0);
                            }
                        }
                    }
                    break;
                case 2:
                    printPlayersNamesAndBingoCards(players, bingoCardsList);
                    i--;
                    break;
                case 3:
                    printStatistics(drawNumberList, (round-1));
                    i--;
                    break;
                case 4:
                    exit(0);
                    break;
                default:
                    i--;
                    break;
            }
        }
    }

    public static String[] getPlayers() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("DIGITE A LISTA DE JOGADORES: (SEPARE O NOME DOS JGADORES POR -)");
        String entry = scanner.nextLine();
        return entry.split("-");
    }

    public static int getManualOrAutomatic() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nESCOLHA SE DESEJA JOGAR NO MODO AUTOMATUCO OU MANUAL");
        System.out.println("1- AUTOMATICO");
        System.out.println("2- MANUAL");
        return scanner.nextInt();
    }

    public static int[] getBingoCardsManual() {
        Scanner scanner = new Scanner(System.in);
        String entry = scanner.nextLine();
        String[] numbers = entry.split(",");
        int[] bingoCard = new int[bingoCardSize];
        for (int i = 0; i < bingoCardSize; i++) {
            bingoCard[i] = Integer.parseInt(numbers[i]);
        }
        return bingoCard;
    }

    public static int[] getBingoCardsAutomatic(int[] pool) {
        int[] bingoCard = new int[bingoCardSize];
        int[] randomPool = getRandomPool(pool);
       for (int i = 0; i < bingoCardSize;i++) {
           bingoCard[i] = getPrizeDraw(randomPool,i);
       }
       return bingoCard;
    }

    public static void printPlayersNamesAndBingoCards(String[] players, int[][] bingoCards) {
        String player = "jogadores(a)";
        String card = "cartelas";
        System.out.printf("\n%-25S%S", player, card);
        for (int i = 0; i < players.length; i++) {
            System.out.printf("\n%-25S", players[i]);
            for (int j = 0; j < bingoCards[i].length; j++) {
                System.out.printf("%d\t", bingoCards[i][j]);
            }
        }
    }

    private static void printInstructions() {
        System.out.println("\n\n========\tINSTRUCOES\t========");
        System.out.println("A TECLA 1 DA CONTINUAÇÃO PARA O JOGO");
        System.out.println("A TECLA 2 MOSTRA AS CARTELAS");
        System.out.println("A TECLA 3 MOSTRA AS ESTATISTICAS");
        System.out.println("A TECLA 4 FINALIZA O JOGO");
        System.out.println("========\tINSTRUCOES\t========");
    }

    public static int[] getRandomPool(int[] pool) {
        Random random   = new Random();
        int[] randomPool = new int[numberOfRounds];
        for (int i = 0; i < pool.length; i++) {
            pool[i] = i + 1;
        }
        for (int i = 0; i < pool.length; i++) {
            randomPool[i] = i + 1;
        }
        for (int i = 0; i < (pool.length - 1); i++) {
            int j = random.nextInt(pool.length);
            int temp = randomPool[i];
            randomPool[i] = randomPool[j];
            randomPool[j] = temp;
        }
        return randomPool;
    }

    public static int getPrizeDraw(int[] randomPool, int index) {
        return randomPool[index];
    }

    public static int getNextRound() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n\n1- GERAR SORTEIO");
        System.out.println("2- CARTELAS");
        System.out.println("3- ESTATISTICAS");
        System.out.println("4- FINALIZAR");
        return scanner.nextInt();
    }

    public static int[] getScoredPointsList(int drawNumber, int[][] bingoCardsNumbers, int[] scoredPointsList) {
        for (int i = 0; i < bingoCardsNumbers.length; i++) {
            for (int j = 0; j < bingoCardsNumbers[i].length; j++) {
                if (drawNumber == bingoCardsNumbers[i][j]) {
                    scoredPointsList[i] += 1;
                }
            }
        }
        return scoredPointsList;
    }

    public static boolean getBingo(int[][] updatedScoredPointsList) {
        for (int i = 0; i < updatedScoredPointsList.length; i++) {
            for (int j = 0; j < updatedScoredPointsList[i].length; j++) {
                if (updatedScoredPointsList[i][j] == bingoCardSize) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void printStatistics(int[] drawNumberList, int rounds) {
        System.out.printf("\nLISTAS DOS NUMEROS SORTEADOS %s\n", Arrays.toString(drawNumberList));
        System.out.printf("O JOGO TEVE %d ROUNDS", rounds);
    }
}