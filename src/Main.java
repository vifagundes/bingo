import org.codehaus.groovy.transform.SourceURIASTTransformation;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import static java.lang.System.exit;

public class Main {
    public static final int bingoCardSize  = 5;
    public static final int poolSize       = 61;
    public static final int numberOfRounds = poolSize - 1;

    public static void main(String[] args) {

        welcome(bingoCardSize,numberOfRounds);

        String[] players           = getPlayers();
        int      qntPlayers        = players.length;
        int      manualOrAutomatic = getManualOrAutomatic();

        int[][] bingoCardsNumbers       = new int[qntPlayers][bingoCardSize];
        int[]   scoredPointsListEmpty   = new int[qntPlayers];
        int[]   pool                    = new int[numberOfRounds];
        int[]   randomPool              = getRandomPool(pool);
        int[]   drawNumberList          = new int[numberOfRounds];

        for (int i = 0; i < players.length; i++) {  //MENU PARA PERGUNTAR SE VAI SER MODO MANUAL OU AUTOMATICO
            switch (manualOrAutomatic) {
                case 1:
                    bingoCardsNumbers[i] = getBingoCardsAutomatic(pool);
                    break;
                case 2:
                    System.out.printf("DIGITE OS %d NUMEROS DA CARTELA %s (SEPARE CADA NUMERO POR , )\n", bingoCardSize, players[i]);
                    bingoCardsNumbers[i] = getBingoCardsManual();
                    break;
                default:
                    manualOrAutomatic = getManualOrAutomatic();
                    i--;
                    break;
            }
        }

        for (int i = 0; i < numberOfRounds; i++) {  // MENU PRINCIPAL DO JOGO
            int   nextRound;
            int   round;
            int[] updatedScoredPointsList;

            nextRound = getNextRound();
            round     = i + 1;

            switch (nextRound) {
                case 1:
                    int drawNumber;
                    boolean bingo;

                    drawNumber        = getPrizeDraw(randomPool, i);
                    drawNumberList[i] = drawNumber;

                    System.out.printf("\nRODADA - %d\nNUMERO SORTEADO - %d\n", round, drawNumber);

                    updatedScoredPointsList = getScoredPointsList(drawNumber, players,bingoCardsNumbers, scoredPointsListEmpty);
                    bingo                   = getBingo(updatedScoredPointsList);

                    if (bingo) {
                        for (int j = 0; j < updatedScoredPointsList.length; j++) {
                            if (updatedScoredPointsList[j] == bingoCardSize) {
                                System.out.printf("\n\nPARABENS %S, VOCE FEZ BINGO!!!\n", players[j]);
                                printStatistics(drawNumberList,round, bingoCardsNumbers, updatedScoredPointsList, players);
                                exit(0);
                            }
                        }
                    }
                    break;
                case 2:
                    printPlayersNamesAndBingoCards(players, bingoCardsNumbers);
                    i--;
                    break;
                case 3:
                    printInstructions();
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

    public static void welcome(int bingoCardSize, int poolSize) {
        System.out.println("===\tBEM VINDO AO BINGO\t===");
        System.out.printf("CADA CARTELA TERA %d NUMEROS\n", bingoCardSize);
        System.out.printf("SERAO SORTEADOS NUMEROS DE 1 ATE %d\n", poolSize);
        System.out.println("O VENCEDOR SERA QUEM COMPLETAR A CARTELA PRIMEIRO");
        System.out.println("BOA SORTE");
    }

    public static String[] getPlayers() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("DIGITE A LISTA DE JOGADORES: (SEPARE O NOME DOS JOGADORES POR -)");
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
        Scanner  scanner  = new Scanner(System.in);
        String   entry    = scanner.nextLine();
        String[] numbers  = entry.split(",");
        int[] bingoCard   = new int[bingoCardSize];
        for (int i = 0; i < bingoCardSize; i++) {
            bingoCard[i] = Integer.parseInt(numbers[i]);
        }
        return bingoCard;
    }

    public static int[] getBingoCardsAutomatic(int[] pool) {
        int[] bingoCard  = new int[bingoCardSize];
        int[] randomPool = getRandomPool(pool);
       for (int i = 0; i < bingoCardSize;i++) {
           bingoCard[i] = getPrizeDraw(randomPool,i);
       }
       return bingoCard;
    }

    public static void printPlayersNamesAndBingoCards(String[] players, int[][] bingoCards) {
        String player = "jogadores(a)";
        String card   = "cartelas";
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
        Random random     = new Random();
        int[]  randomPool = new int[numberOfRounds];
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
        System.out.println("3- INSTRUCOES");
        System.out.println("4- FINALIZAR");
        return scanner.nextInt();
    }

    public static int[] getScoredPointsList(int drawNumber,String[] players,int[][] bingoCardsNumbers, int[] scoredPointsList) {
        for (int i = 0; i < bingoCardsNumbers.length; i++) {
            for (int j = 0; j < bingoCardsNumbers[i].length; j++) {
                if (drawNumber == bingoCardsNumbers[i][j]) {
                    scoredPointsList[i] += 1;
                }
            }
            if (scoredPointsList[i] < (bingoCardSize -1) && (scoredPointsList[i] > 0)) {
                System.out.printf("\nO JOGADOR %-30S TEM %d PONTOS", players[i], scoredPointsList[i]);
            } else if (scoredPointsList[i] == 0) {
                System.out.printf("\nO JOGADOR %-30S NAO TEM PONTOS", players[i]);
            } else if (scoredPointsList[i] == (bingoCardSize -1)) {
                System.out.printf("\nO JOGADOR %-30S ESTA QUASE FAZENDO BINGO", players[i]);
            }
        }
        return scoredPointsList;
    }

    public static boolean getBingo(int[] updatedScoredPointsList) {
            for (int j = 0; j < updatedScoredPointsList.length; j++) {
                if (updatedScoredPointsList[j] == bingoCardSize) {
                    return true;
            }
        }
        return false;
    }

    public static void printStatistics(int[] drawNumberList, int rounds, int[][] bingoCardNumbers, int[] scoredPointsList, String[] players) {
        int[] newDrawNumberList = new int[rounds];
        for (int i = 0; i < newDrawNumberList.length;i++) {
            if (drawNumberList[i] != 0) {
                newDrawNumberList[i] = drawNumberList[i];
            }
        }

        for (int i = 0; i < scoredPointsList.length;i++) {
            if (scoredPointsList[i] == 5) {
                Arrays.sort(bingoCardNumbers[i]);
                System.out.printf("\nOS NUMEROS DA CARTELA PREMIADA DO VENCEDOR(A) %S: %s\n",players[i], Arrays.toString(bingoCardNumbers[i]));
            }
        }

        Arrays.sort(newDrawNumberList);
        System.out.printf("\nLISTAS DOS NUMEROS SORTEADOS %s\n", Arrays.toString(newDrawNumberList));
        System.out.printf("O JOGO TEVE %d ROUNDS", rounds);
    }
}