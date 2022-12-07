import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import static java.lang.System.exit;

public class Main{
    public static int bingoCardSize         = 5;
    public static int limitBingoCardNumbers = 61;
    public static int numberOfRounds        = limitBingoCardNumbers - 1;

    public static void main(String[] args) {

        int qntPlayers         = getQntPlayers();
        String[] players       = getPlayers(qntPlayers);
        int[][] bingoCardsList = new int[qntPlayers][bingoCardSize];
        int[] pool             = new int[numberOfRounds];
        int manualOrAutomatic  = getManualOrAutomatic();

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

        printPlayersNamesAndBingoCards(players,bingoCardsList);
        printInstructions();

        for (int i = 0; i < pool.length; i++) {
            int nextRound = getNextRound();
            int round = i+1;
            switch (nextRound) {
                case 1:
                    int drawNumber = getPrizeDraw(pool);
                    System.out.printf("\nRODADA - %d\nNUMERO SORTEADO - %d\n", round, drawNumber);
                    break;
                case 2:
                    exit(0);
                    break;
                case 3:
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
            System.out.printf("DIGITE O NOME DO JOGADOR %d: ", i+1);
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
        for (int i = 0; i < bingoCardSize; i++) {
            bingoCard[i] = random.nextInt(limitBingoCardNumbers);
            }
        return bingoCard;
    }

    public static void  printPlayersNamesAndBingoCards(String[] players, int[][] bingoCards) {
        System.out.printf("JOGADORES\t\tCARTELAS\n");
        for (int i = 0; i < players.length; i++){
            System.out.printf("\n%-15s", players[i]);
            for (int j = 0; j < bingoCards[i].length; j++) {
                System.out.printf("%d\t",bingoCards[i][j]);
            }
        }
    }

    private static void printInstructions() {
        System.out.println("\n\n========\tINSTRUCOES\t========");
        System.out.println("SIGA AS INSTRUCOES PARA CONTINUAR");
        System.out.println("A TECLA 1 DA CONTINUAÇÃO PARA O JOGO");
        System.out.println("A TECLA 2 FINALIZA O JOGO");
        System.out.println("A TECLA 3 MOSTRA AS INSTRUCOES");
        System.out.println("========\tBOA SORTE\t========");
    }

    public static int getPrizeDraw(int[] pool) {
        Random random = new Random();
        int drawNumber = 0;
        int prizeNumber = random.nextInt(limitBingoCardNumbers);

        for (int i = 0; i < pool.length; i++) {
            pool[i] = i+1;
        }

        for (int i = 0; i < pool.length; i++) {
            drawNumber = pool[i];
            if (drawNumber == prizeNumber) {
                drawNumber = pool[i];
                break;
            }
        }
        return drawNumber;
    }

    public static int getNextRound() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nPROXIMA RODADA?");
        System.out.println("1- SIM");
        System.out.println("2- NAO");
        System.out.println("3- INSTRUCOES");
        return scanner.nextInt();
    }
}