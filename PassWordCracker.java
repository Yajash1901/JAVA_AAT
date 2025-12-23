
import java.util.*;

// Interface for game structure
interface Game {
    void start();
    void play();
    void end();
}

// Base game class
abstract class BaseGame implements Game {
    protected Scanner sc = new Scanner(System.in);
    protected String generatedPassword;
    protected int score = 0;
    protected String[] hints;

    public BaseGame(String password, String[] hints) {
        this.generatedPassword = password;
        this.hints = hints;
    }

    public void start() {
        System.out.println("====== PASSWORD CRACKER GAME ======\n");
        System.out.println("Game Rules:");
        System.out.println("1. You will guess the password character by character.");
        System.out.println("2. Each character has a unique hint.");
        System.out.println("3. Score +1 for each correct character.");
        System.out.println("------------------------------------\n");
    }
}

// Password game class (inheritance)
class PasswordGame extends BaseGame {

    public PasswordGame(String password, String[] hints) {
        super(password, hints);
    }

    @Override
    public void play() {
        StringBuilder userPassword = new StringBuilder();

        for (int i = 0; i < generatedPassword.length(); i++) {
            System.out.println("Hint for character " + (i + 1) + ": " + hints[i]);
            System.out.print("Your guess: ");
            char userChar = sc.next().toUpperCase().charAt(0);
            userPassword.append(userChar);

            if (userChar == generatedPassword.charAt(i)) {
                score++;
            }
            System.out.println();
        }

        // Show summary
        System.out.println("Your entered password : " + userPassword);
        System.out.println("Actual password        : " + generatedPassword);
    }

    @Override
    public void end() {
        System.out.println("\n====== GAME RESULT ======");
        System.out.println("Your Score: " + score + "/" + generatedPassword.length());

        if (score == generatedPassword.length()) {
            System.out.println("🎉 Congratulations! You cracked the password!");
        } else {
            System.out.println("❌ Incorrect password. Better luck next time!");
        }
    }
}

public class PassWordCracker{

    public static void main(String[] args) {

        // Updated password sets with better hints
        String[][] passwordSets = {

                // PASSWORD SET 1 – JAVA
                {
                        "JAVA",
                        "This language runs on a virtual machine and follows the WORA principle.",
                        "This letter is also the chemical symbol of a radioactive gas used in lighting.",
                        "The first letter of the name of a widely used version-control system.",
                        "This letter starts the name of the world’s second-largest ocean."
                },

                // PASSWORD SET 2 – HTML
                {
                        "HTML",
                        "Foundation of all web pages — it structures content using tags.",
                        "This letter represents a musical note & also starts the word ‘Hyper’.",
                        "This letter is the Roman numeral for 50.",
                        "This letter begins the unit of measurement equal to 1000 meters."
                },

                // PASSWORD SET 3 – DBMS
                {
                        "DBMS",
                        "Software that stores, modifies, and retrieves large amounts of structured data.",
                        "The starting letter of the number system used by computers.",
                        "This letter begins the word describing logical connections between tables.",
                        "This letter ends the word 'Process'."
                },

                // PASSWORD SET 4 – CODE
                {
                        "CODE",
                        "What programmers write to instruct computers.",
                        "The letter that begins all object-oriented terms like Class, Constructor, Compiler.",
                        "First letter of a structured loop that repeats until a condition becomes false.",
                        "This letter begins the word ‘Execution’."
                }
        };

        // Randomly choose one password set
        Random random = new Random();
        int index = random.nextInt(passwordSets.length);

        String selectedPassword = passwordSets[index][0];
        String[] selectedHints = {
                passwordSets[index][1],
                passwordSets[index][2],
                passwordSets[index][3],
                passwordSets[index][4]
        };

        PasswordGame game = new PasswordGame(selectedPassword, selectedHints);
        game.start();
        game.play();
        game.end();
    }
}
