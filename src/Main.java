import org.codehaus.groovy.transform.SourceURIASTTransformation;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import static java.lang.System.exit;

public class Main {
    public static int bingoCardSize = 5;
    public static int poolSize = 21;
    public static int numberOfRounds = poolSize - 1;

    public static void main(String[] args) {

        String[] players           = getPlayers();
        int      qntPlayers        = players.length;
        int      manualOrAutomatic = getManualOrAutomatic();

        int[][] bingoCardsList          = new int[qntPlayers][bingoCardSize];
        int[]   scoredPointsListEmpty   = new int[qntPlayers];
        int[][] updatedScoredPointsList = new int[1][qntPlayers];
        int[]   pool                    = new int[numberOfRounds];
        int[]   randomPool              = getRandomPool(pool);

        for (int i = 0; i < players.length; i++) {  //MENU PARA PERGUNTAR SE VAI SER MODO MANUAL OU AUTOMATICO
            switch (manualOrAutomatic) {
                case 1:
                    bingoCardsList[i] = getBingoCardsAutomatic(pool);
                    break;
                case 2:
                    System.out.printf("DIGITE OS %d NUMEROS DA CARTELA %S\n", bingoCardSize, players[i]);
                    bingoCardsList[i] = getBingoCardsManual();
                    break;
                default:
                    manualOrAutomatic = getManualOrAutomatic();
                    i--;
                    break;
            }
        }

        for (int index = 0; index < numberOfRounds; index++) {  // MENU PRINCIPAL DO JOGO
            int nextRound = getNextRound();
            int round = index + 1;
            switch (nextRound) {
                case 1:
                    int drawNumber = getPrizeDraw(randomPool, index);
                    System.out.printf("\nRODADA - %d\nNUMERO SORTEADO - %d\n", round, drawNumber);
                    updatedScoredPointsList[0] = getScoredPointsList(drawNumber, bingoCardsList,players, scoredPointsListEmpty);
                    boolean bingo = getBingo(updatedScoredPointsList);
                    if (bingo) {
                        for (int x = 0; x < updatedScoredPointsList[0].length; x++) {
                            if (updatedScoredPointsList[0][x] == bingoCardSize) {
                                System.out.printf("\n\nPARABENS %S, VOCE FEZ BINGO!!!\n", players[x]);
                                System.out.printf("\nO JOGO DUROU %d ROUNDS\n", round);
                                exit(0);
                            }
                        }
                    }
                    break;
                case 2:
                    exit(0);
                    break;
                case 3:
                    printPlayersNamesAndBingoCards(players, bingoCardsList);
                    index--;
                    break;
                case 4:
                    printInstructions();
                    index--;
                    break;
                default:
                    index--;
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
        String player = "jogadores";
        String card = "cartela";
        System.out.printf("\n%-25S%S", player, card);
        for (int i = 0; i < players.length; i++) {
            System.out.printf("\n%-25S", players[i]);
            for (int j = 0; j < bingoCards[i].length; j++) {
                System.out.printf("%d\t", bingoCards[i][j]);
            }
        }
    }

    public static int[] getRandomPool(int[] pool) {
        Random random   = new Random();
        for (int i = 0; i < pool.length; i++) {
            pool[i] = i + 1;
        }

        for (int i = 0; i < (pool.length - 1); i++) {
            int j = random.nextInt(pool.length);
            int temp = pool[i];
            pool[i] = pool[j];
            pool[j] = temp;
        }
        return pool;
    }

    private static void printInstructions() {
        System.out.println("\n\n========\tINSTRUCOES\t========");
        System.out.println("SIGA AS INSTRUCOES PARA CONTINUAR");
        System.out.println("A TECLA 1 DA CONTINUAÇÃO PARA O JOGO");
        System.out.println("A TECLA 2 FINALIZA O JOGO");
        System.out.println("A TECLA 3 MOSTRA AS CARTELAS");
        System.out.println("A TECLA 4 MOSTRA AS INSTRUCOES");
        System.out.println("========\tINSTRUCOES\t========");
    }

    public static int getPrizeDraw(int[] randomPool, int index) {
        return randomPool[index];
    }

    public static int getNextRound() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n\n1- GERAR SORTEIO");
        System.out.println("2- FINALIZAR");
        System.out.println("3- CARTELAS");
        System.out.println("4- INSTRUCOES");
        return scanner.nextInt();
    }

    public static int[] getScoredPointsList(int drawNumber, int[][] bingoCardsNumbers, String[] players, int[] scoredPointsList) {
        for (int i = 0; i < bingoCardsNumbers.length; i++) {
            for (int j = 0; j < bingoCardsNumbers[i].length; j++) {
                if (drawNumber == bingoCardsNumbers[i][j]) {
                    scoredPointsList[i] += 1;
                }
            }
            if (scoredPointsList[i] > 0) {
                System.out.printf("\nO JOGADOR %S JA TEM %d PONTOS", players[i], scoredPointsList[i]);
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
}