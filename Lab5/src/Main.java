import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        mainMenu(scanner);
    }

    public static void mainMenu(Scanner scanner) throws FileNotFoundException {

        System.out.println("Choose file");
        System.out.println("1. g1.txt");
        System.out.println("2. g2.txt");

        String command = scanner.nextLine();

        switch (command) {
            case "1":
                parseGrammar(scanner, "g1.txt");
                break;
            case "2":
                parseGrammar(scanner, "g2.txt");
                break;
            default:
                System.out.println("Wrong choice bozo!");
                mainMenu(scanner);
                break;
        }
    }

    public static void parseGrammar(Scanner scanner, String filename) throws FileNotFoundException {
        Grammar grammar = new Grammar(filename);
        System.out.println(filename + " parsed!");

        while (true) {
            System.out.println("Choose what to print:");
            System.out.println("1. Non-terminals");
            System.out.println("2. Terminals");
            System.out.println("3. All productions");
            System.out.println("4. Productions for a given non-terminal");
            System.out.println("5. Exit");

            String command = scanner.nextLine();

            switch (command) {
                case "1":
                    System.out.println(grammar.getNonTerminals());
                    break;
                case "2":
                    System.out.println(grammar.getTerminals());
                    break;
                case "3":
                    System.out.println(grammar.getProductions());
                    break;
                case "4":
                    System.out.println("Give non-terminal name");
                    String nonTerminal = scanner.nextLine();
                    System.out.println(grammar.getProductionsForNonTerminal(nonTerminal));
                    break;
                case "5":
                    return;
                default:
                    System.out.println("Socheres prala ai gresit comanda");
                    break;
            }
        }

    }
}