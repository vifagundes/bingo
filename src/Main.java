import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main{
    public static int bingoCardSize = 6;

    public static void main(String[] args) {

        int qntPlayers  = welcome();
        String[] players = getPlayers(qntPlayers);
        int[] manualOrAutomatic = new int[qntPlayers];
        int[][] bingoCardsList = new int[qntPlayers][bingoCardSize];
        System.out.println("PARTICIPANTES: " + Arrays.toString(players));

        for (int i = 0; i < players.length; i++) {
            System.out.printf("\n%s ESCOLHA SE A SUA CARTELA VAI SER AUTOMATICA" +
                    "\n1 - SIM\n2 - NAO\n", players[i]);
            manualOrAutomatic[i] = getManualOrAutomatic();
        }

        for (int i = 0; i < players.length; i++) {
            if (manualOrAutomatic[i] == 1) {
                bingoCardsList[i] = getBingoCards(manualOrAutomatic[i]);
            }
            if (manualOrAutomatic[i] == 2) {
                System.out.printf("DIGITE OS 6 NUMEROS DA CARTELA %s\n", players[i]);
                bingoCardsList[i] = getBingoCards(manualOrAutomatic[i]);
            }
        }
    }

    public static int welcome() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== BEM VINDO AO BINGO! ===\n\n");
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
        return scanner.nextInt();
    }

    public static int[] getBingoCards(int manualOrAutomatic) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        int[] bingoCard = new int[6];
        switch (manualOrAutomatic) {
            case 1:
                for (int i = 0; i < bingoCardSize; i++) {
                    bingoCard[i] = random.nextInt(61);
                }
                break;
            case 2:
                for (int i = 0; i < bingoCardSize; i++) {
                    bingoCard[i] = scanner.nextInt();
                }
                break;
            default:
                getManualOrAutomatic();
                break;
        }
        return bingoCard;
    }
}
