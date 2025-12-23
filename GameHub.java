import java.util.*;

/* =========================
   GAME HUB (MAIN MENU)
   ========================= */
public class GameHub {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n==========================");
            System.out.println(" 🎮 JAVA GAME HUB ");
            System.out.println("==========================");
            System.out.println("1. Password Cracker (Hangman)");
            System.out.println("2. Minesweeper 7x7");
            System.out.println("3. Exit");
            System.out.print("Choose a game: ");

            int choice = sc.nextInt();
            sc.nextLine(); // clear buffer

            switch (choice) {
                case 1:
                    HangmanMain.play();
                    break;
                case 2:
                    Minesweeper7.play();
                    break;
                case 3:
                    System.out.println("👋 Thanks for playing!");
                    sc.close();
                    return;
                default:
                    System.out.println("❌ Invalid choice!");
            }
        }
    }
}

/* =========================
   HANGMAN GAME (OOP)
   ========================= */

// Word provider
class WordProvider {
    private static final String[] WORDS = {
            "JAVA", "PROGRAMMING", "COMPUTER", "ALGORITHM",
            "COMPILER", "OBJECT", "CLASS", "INHERITANCE"
    };

    public String getRandomWord() {
        return WORDS[new Random().nextInt(WORDS.length)];
    }
}

// Hangman game logic
class HangmanGame {
    private String secretWord;
    private StringBuilder currentGuess;
    private int attempts = 6;
    private Set<Character> guessed = new HashSet<>();

    public HangmanGame(String word) {
        secretWord = word;
        currentGuess = new StringBuilder("_".repeat(word.length()));
    }

    public int guess(char ch) {
        ch = Character.toUpperCase(ch);
        if (guessed.contains(ch)) return 0;

        guessed.add(ch);
        boolean found = false;

        for (int i = 0; i < secretWord.length(); i++) {
            if (secretWord.charAt(i) == ch) {
                currentGuess.setCharAt(i, ch);
                found = true;
            }
        }

        if (!found) attempts--;
        return found ? 1 : 2;
    }

    public boolean won() {
        return currentGuess.toString().equals(secretWord);
    }

    public boolean lost() {
        return attempts <= 0;
    }

    public String display() {
        return currentGuess.toString().replace("", " ").trim();
    }

    public int attemptsLeft() {
        return attempts;
    }

    public String getWord() {
        return secretWord;
    }
}

// Hangman renderer
class HangmanRenderer {
    private static final String[] PIC = {
            " +---+\n |   |\n     |\n     |\n     |\n     |\n=======",
            " +---+\n |   |\n O   |\n     |\n     |\n     |\n=======",
            " +---+\n |   |\n O   |\n |   |\n     |\n     |\n=======",
            " +---+\n |   |\n O   |\n/|   |\n     |\n     |\n=======",
            " +---+\n |   |\n O   |\n/|\\  |\n     |\n     |\n=======",
            " +---+\n |   |\n O   |\n/|\\  |\n/    |\n=======",
            " +---+\n |   |\n O   |\n/|\\  |\n/ \\  |\n======="
    };

    public void show(HangmanGame game) {
        System.out.println(PIC[6 - game.attemptsLeft()]);
        System.out.println("Word: " + game.display());
        System.out.println("Attempts left: " + game.attemptsLeft());
    }
}

// Hangman main controller
class HangmanMain {
    public static void play() {
        Scanner sc = new Scanner(System.in);
        WordProvider wp = new WordProvider();
        HangmanGame game = new HangmanGame(wp.getRandomWord());
        HangmanRenderer renderer = new HangmanRenderer();

        System.out.println("\n🔐 PASSWORD CRACKER - HANGMAN 🔐");

        while (!game.won() && !game.lost()) {
            renderer.show(game);
            System.out.print("Enter a letter: ");
            char ch = sc.nextLine().charAt(0);

            int res = game.guess(ch);
            if (res == 0) System.out.println("⚠ Already guessed!");
            else if (res == 1) System.out.println("✅ Correct!");
            else System.out.println("❌ Wrong!");
        }

        renderer.show(game);
        System.out.println(game.won()
                ? "🎉 You cracked the password: " + game.getWord()
                : "💀 Game Over! Word was: " + game.getWord());
                sc.close();
    }
}

/* =========================
   MINESWEEPER GAME
   ========================= */

class Minesweeper7 {
    static final int SIZE = 7;
    static final int MINES = 8;
    static char[][] board = new char[SIZE][SIZE];
    static boolean[][] revealed = new boolean[SIZE][SIZE];
    static boolean[][] mines = new boolean[SIZE][SIZE];

    public static void play() {
        Scanner sc = new Scanner(System.in);
        initBoard();
        placeMines();
        fillNumbers();

        int safeCells = SIZE * SIZE - MINES;
        int revealedCount = 0;

        System.out.println("\n💣 MINESWEEPER 7x7 💣");

        while (true) {
            printBoard();
            System.out.print("Enter row and column: ");
            int r = sc.nextInt();
            int c = sc.nextInt();

            if (r < 0 || r >= SIZE || c < 0 || c >= SIZE) {
                System.out.println("❌ Invalid move!");
                continue;
            }

            if (mines[r][c]) {
                revealAll();
                printBoard();
                System.out.println("💥 BOOM! Game Over!");
                return;
            }

            revealedCount += reveal(r, c);
            if (revealedCount == safeCells) {
                revealAll();
                printBoard();
                System.out.println("🎉 You cleared the board!");
                return;
            }
            sc.close();
        }
    }

    static void initBoard() {
        for (int i = 0; i < SIZE; i++)
            Arrays.fill(board[i], ' ');
    }

    static void placeMines() {
        Random r = new Random();
        int count = 0;
        while (count < MINES) {
            int x = r.nextInt(SIZE);
            int y = r.nextInt(SIZE);
            if (!mines[x][y]) {
                mines[x][y] = true;
                count++;
            }
        }
    }

    static void fillNumbers() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (!mines[i][j]) {
                    int count = 0;
                    for (int dx = -1; dx <= 1; dx++)
                        for (int dy = -1; dy <= 1; dy++) {
                            int ni = i + dx, nj = j + dy;
                            if (ni >= 0 && nj >= 0 && ni < SIZE && nj < SIZE && mines[ni][nj])
                                count++;
                        }
                    if (count > 0) board[i][j] = (char) ('0' + count);
                }
            }
        }
    }

    static int reveal(int i, int j) {
        if (revealed[i][j]) return 0;
        revealed[i][j] = true;
        int count = 1;

        if (board[i][j] == ' ') {
            for (int dx = -1; dx <= 1; dx++)
                for (int dy = -1; dy <= 1; dy++) {
                    int ni = i + dx, nj = j + dy;
                    if (ni >= 0 && nj >= 0 && ni < SIZE && nj < SIZE && !mines[ni][nj])
                        count += reveal(ni, nj);
                }
        }
        return count;
    }

    static void revealAll() {
        for (int i = 0; i < SIZE; i++)
            Arrays.fill(revealed[i], true);
    }

    static void printBoard() {
        System.out.print("\n   ");
        for (int i = 0; i < SIZE; i++) System.out.print(i + "  ");
        System.out.println();

        for (int i = 0; i < SIZE; i++) {
            System.out.print(i + " |");
            for (int j = 0; j < SIZE; j++) {
                if (!revealed[i][j]) System.out.print("□ |");
                else if (mines[i][j]) System.out.print("* |");
                else System.out.print(board[i][j] + " |");
            }
            System.out.println();
        }
    }
}
