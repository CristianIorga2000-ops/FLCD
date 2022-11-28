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

        String command = scanner.nextLine();

        switch (command) {
            case "1":
                parseGrammar(scanner, "g1.txt");
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
            System.out.println("1. Nonterminals");
            System.out.println("2. Terminals");
            System.out.println("3. All productions");
            System.out.println("4. Productions for a given nonterminal");

            String command = scanner.nextLine();

            switch (command) {
                case "1":
                    System.out.println(grammar.getNonterminals());
                    break;
                case "2":
                    System.out.println(grammar.getTerminals());
                    break;
                case "3":
                    System.out.println(grammar.getProductions());
                    break;
                case "4":
                    System.out.println("Give nonterminal name");
                    String nonterm = scanner.nextLine();
                    System.out.println(grammar.getProductionsForNonTerminal(nonterm));
                    break;
                default:
                    System.out.println("Socheres prala ai gresit comanda");
                    break;
            }
        }

    }
}