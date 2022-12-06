import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main{
    public static int bingoCardSize = 6;
    public static int limitBingoCardNumbers = 61;

    public static void main(String[] args) {
        System.out.println("=== BEM VINDO AO BINGO! ===\n\n");
        int qntPlayers = getQntPlayers();
        String[] players = getPlayers(qntPlayers);
        System.out.println("PARTICIPANTES: " + Arrays.toString(players));
        int manualOrAutomatic = getManualOrAutomatic();
        int[][] bingoCardsList = new int[qntPlayers][bingoCardSize];

        for (int i = 0; i < players.length; i++) {
            switch (manualOrAutomatic) {
                case 1:
                    bingoCardsList[i] = getBingoCardsAutomatic();
                    break;
                case 2:
                    System.out.printf("DIGITE OS 6 NUMEROS DA CARTELA %s\n", players[i]);
                    bingoCardsList[i] = getBingoCardsManual();
                    break;
                default:
                    manualOrAutomatic = getManualOrAutomatic();
                    i--;
                    break;
            }
        }
        printPlayersNamesAndBingoCards(players,bingoCardsList);
    }

    public static int getQntPlayers() {
        Scanner scanner = new Scanner(System.in);
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
        return players;
    }

    public static int getManualOrAutomatic() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("ESCOLHA SE DESEJA JOGAR NO MODO AUTOMATUCO OU MANUAL");
        System.out.println("1- AUTOMATICO");
        System.out.println("2- MANUAL");
        return scanner.nextInt();
    }

    public static int[] getBingoCardsManual() {
        Scanner scanner = new Scanner(System.in);
        int[] bingoCard = new int[bingoCardSize];
        for (int i = 0; i < bingoCardSize; i++) {
            bingoCard[i] = scanner.nextInt();
        }
        return bingoCard;
    }

    public static int[] getBingoCardsAutomatic() {
        Random random = new Random();
        int[] bingoCard = new int[bingoCardSize];
        for (int i = 0; i < bingoCardSize; i++) {
            bingoCard[i] = random.nextInt(limitBingoCardNumbers);
            if (bingoCard[i] == 0) bingoCard[i] = 1;
        }
        return bingoCard;
    }

    public static void printPlayersNamesAndBingoCards(String[] players, int[][] bingoCards) {
        for (int i = 0; i < players.length; i++){
            System.out.printf("\n%s", players[i]);
            for (int j = 0; j < bingoCards[i].length; j++) {
                System.out.printf("\t%d",bingoCards[i][j]);
            }
        }
    }
}