import java.util.Random;
import java.util.Scanner;

public class Minesweeper7 {

    static final int SIZE = 7;
    static final int MINES = 8;

    static char[][] board = new char[SIZE][SIZE];
    static boolean[][] revealed = new boolean[SIZE][SIZE];
    static boolean[][] mines = new boolean[SIZE][SIZE];

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        initBoard();
        placeMines();
        fillNumbers();

        System.out.println("════════════════════════════");
        System.out.println("      💣 MINESWEEPER 7x7     ");
        System.out.println("════════════════════════════");
        System.out.println("Enter row and column (0–6)");
        System.out.println("Example: 2 3");
        System.out.println("Goal: Reveal all safe cells\n");

        int safeCells = SIZE * SIZE - MINES;
        int revealedCount = 0;

        while (true) {

            printBoard();

            System.out.print("\nYour move (row col): ");
            int r = sc.nextInt();
            int c = sc.nextInt();

            if (r < 0 || r >= SIZE || c < 0 || c >= SIZE) {
                System.out.println("❌ Invalid input! Try again.");
                continue;
            }

            if (revealed[r][c]) {
                System.out.println("⚠️ Cell already revealed!");
                continue;
            }

            if (mines[r][c]) {
                revealAll();
                printBoard();
                System.out.println("\n💥 BOOM! You stepped on a mine!");
                break;
            }

            revealedCount += reveal(r, c);

            if (revealedCount == safeCells) {
                revealAll();
                printBoard();
                System.out.println("\n🎉 Congratulations! You cleared the board!");
                break;
            }
        }

        sc.close();
    }

    // Initialize board
    static void initBoard() {
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                board[i][j] = ' ';
    }

    // Place mines randomly
    static void placeMines() {
        Random rand = new Random();
        int placed = 0;

        while (placed < MINES) {
            int r = rand.nextInt(SIZE);
            int c = rand.nextInt(SIZE);

            if (!mines[r][c]) {
                mines[r][c] = true;
                placed++;
            }
        }
    }

    // Fill numbers
    static void fillNumbers() {
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {

                if (mines[r][c]) continue;

                int count = 0;

                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {

                        int nr = r + i;
                        int nc = c + j;

                        if (nr >= 0 && nr < SIZE && nc >= 0 && nc < SIZE && mines[nr][nc]) {
                            count++;
                        }
                    }
                }

                if (count > 0)
                    board[r][c] = (char) ('0' + count);
            }
        }
    }

    // Reveal cell (recursive)
    static int reveal(int r, int c) {
        if (revealed[r][c]) return 0;

        revealed[r][c] = true;
        int count = 1;

        if (board[r][c] == ' ') {
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {

                    int nr = r + i;
                    int nc = c + j;

                    if (nr >= 0 && nr < SIZE && nc >= 0 && nc < SIZE) {
                        if (!revealed[nr][nc] && !mines[nr][nc]) {
                            count += reveal(nr, nc);
                        }
                    }
                }
            }
        }

        return count;
    }

    // Reveal entire board
    static void revealAll() {
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                revealed[i][j] = true;
    }

    // Print board with better visuals
    static void printBoard() {

        System.out.print("\n    ");
        for (int c = 0; c < SIZE; c++) System.out.print(c + "   ");
        System.out.println();

        System.out.print("  +");
        for (int i = 0; i < SIZE; i++) System.out.print("---+");
        System.out.println();

        for (int r = 0; r < SIZE; r++) {
            System.out.print(r + " |");

            for (int c = 0; c < SIZE; c++) {

                if (!revealed[r][c]) {
                    System.out.print(" □ |");
                }
                else if (mines[r][c]) {
                    System.out.print(" * |");
                }
                else if (board[r][c] == ' ') {
                    System.out.print("   |");
                }
                else {
                    System.out.print(" " + board[r][c] + " |");
                }
            }
            System.out.println();

            System.out.print("  +");
            for (int i = 0; i < SIZE; i++) System.out.print("---+");
            System.out.println();
        }
    }
}
