import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import static java.lang.System.exit;

public class Main {
    public static int bingoCardSize = 5;
    public static int limitBingoCardNumbers = 91;
    public static int numberOfRounds = limitBingoCardNumbers - 1;

    public static void main(String[] args) {
        Random random = new Random();

        int qntPlayers = getQntPlayers();
        String[] players = getPlayers(qntPlayers);
        int manualOrAutomatic = getManualOrAutomatic();

        int[][] bingoCardsList = new int[qntPlayers][bingoCardSize];
        int[] scoredPointsListEmpty = new int[qntPlayers];
        int[][] updatedScoredPointsList = new int[1][qntPlayers];
        int[] pool = new int[numberOfRounds];


        for (int i = 0; i < pool.length; i++) {
            pool[i] = i + 1;
        }


        for (int i=0; i < (pool.length - 1); i++) {
            int j = random.nextInt(pool.length);
            int temp = pool[i];
            pool[i] = pool[j];
            pool[j] = temp;
        }

        for (int i = 0; i < players.length; i++) {
            switch (manualOrAutomatic) {
                case 1:
                    bingoCardsList[i] = getBingoCardsAutomatic();
                    break;
                case 2:
                    System.out.printf("DIGITE OS %d NUMEROS DA CARTELA %s\n", bingoCardSize, players[i]);
                    bingoCardsList[i] = getBingoCardsManual();
                    break;
                default:
                    manualOrAutomatic = getManualOrAutomatic();
                    i--;
                    break;
            }
        }

        for (int i = 0; i < numberOfRounds; i++) {
            int nextRound = getNextRound();
            int round = i + 1;
            switch (nextRound) {
                case 1:

                    int drawNumber = getPrizeDraw(pool, i);
                    System.out.printf("\nRODADA - %d\nNUMERO SORTEADO - %d\n", round, drawNumber);
                    updatedScoredPointsList[0] = getScoredPointsList(drawNumber, players, bingoCardsList, scoredPointsListEmpty);
                    boolean bingo = getBingo(updatedScoredPointsList);

                    if (bingo) {
                        for (int x = 0; x < updatedScoredPointsList[0].length; x++) {
                            if (updatedScoredPointsList[0][x] == bingoCardSize) {
                                System.out.printf("\nPARABENS %s, VOCE FEZ BINGO!!!", players[x]);
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
                    i--;
                    break;
                case 4:
                    printInstructions();
                    i--;
                    break;
                default:
                    i--;
                    break;
            }
        }
    }

    public static int getQntPlayers() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("========\tBEM VINDO AO BINGO\t========\n");
        System.out.println("DIGITE QUANTOS JOGADORES PARTICIPARAO DO JOGO");
        return scanner.nextInt();
    }

    public static String[] getPlayers(int qntPlayers) {
        Scanner scanner = new Scanner(System.in);
        String[] players = new String[qntPlayers];
        for (int i = 0; i < qntPlayers; i++) {
            System.out.printf("DIGITE O NOME DO JOGADOR %d: ", i + 1);
            players[i] = scanner.nextLine().toUpperCase();
        }
        System.out.printf("\nPARTICIPANTES: %s\n", Arrays.toString(players));
        return players;
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

    public static int[] getBingoCardsAutomatic() {
        Random random = new Random();
        int[] bingoCard = new int[bingoCardSize];
//        int[] bingoCard2 = new int[bingoCardSize];
//        int[] bingoCard3 = new int[bingoCardSize];
//
//        for (int i = 0; i < bingoCardSize; i++) {
//            bingoCard1[i] = random.nextInt(limitBingoCardNumbers);
//            bingoCard2[i] = random.nextInt(limitBingoCardNumbers);
//            if (bingoCard1[i] != bingoCard2[i]) {
//                bingoCard3[i] = bingoCard1[i];
//            }
//        }
//        return bingoCard3;
        int[] pool = new int[numberOfRounds];


        for (int i = 0; i < pool.length; i++) {
            pool[i] = i + 1;
        }


        for (int i = 0; i < (pool.length - 1); i++) {
            int j = random.nextInt(pool.length);
            int temp = pool[i];
            pool[i] = pool[j];
            pool[j] = temp;
        }
       for (int i = 0; i < bingoCardSize;i++) {
           bingoCard[i] = getPrizeDraw(pool,i);
       }
       return bingoCard;
    }

    public static void printPlayersNamesAndBingoCards(String[] players, int[][] bingoCards) {
        String player = "jogadores";
        String card = "cartela";
        System.out.printf("\n%-15S%S", player, card);
        for (int i = 0; i < players.length; i++) {
            System.out.printf("\n%-15s", players[i]);
            for (int j = 0; j < bingoCards[i].length; j++) {
                System.out.printf("%d\t", bingoCards[i][j]);
            }
        }
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

    public static int[] getScoredPointsList(int drawNumber, String[] players, int[][] bingoCardsNumbers, int[] scoredPointsList) {
        for (int i = 0; i < bingoCardsNumbers.length; i++) {
            for (int j = 0; j < bingoCardsNumbers[i].length; j++) {
                if (drawNumber == bingoCardsNumbers[i][j]) {
                    scoredPointsList[i] += 1;
                }
            }
            if (scoredPointsList[i] > 0) {
                System.out.printf("\nO JOGADOR %s JA TEM %d PONTOS", players[i], scoredPointsList[i]);
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