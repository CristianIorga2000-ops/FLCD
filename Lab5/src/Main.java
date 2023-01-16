import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
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
            case "1" -> parseGrammar(scanner, "g1.txt");
            case "2" -> parseGrammar(scanner, "g2.txt");
            default -> {
                System.out.println("Wrong choice bozo!");
                mainMenu(scanner);
            }
        }
    }

    public static void parseGrammar(Scanner scanner, String filename) throws FileNotFoundException {
        Grammar grammar = new Grammar(filename);
        System.out.println(filename + " parsed!");
        String outputFileName = "out" + filename.charAt(filename.length() - 5) + ".txt";

        while (true) {
            System.out.println("Choose what to print:");
            System.out.println("1. Non-terminals");
            System.out.println("2. Terminals");
            System.out.println("3. All productions");
            System.out.println("4. Productions for a given non-terminal");
            System.out.println("69. First of all nonterminals");
            System.out.println("21. Follow of all nonterminals");
            System.out.println("42. Parse a sequence and print parse table");
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
                case "42":
                    parseSequenceMenu(grammar, scanner, outputFileName);
                    break;
                case "69":
                    System.out.println(grammar.getFirst());
                    break;
                case "21":
                    System.out.println(grammar.getFollow());
                    break;
                default:
                    System.out.println("Socheres prala ai gresit comanda");
                    break;
            }
        }

    }

    public static void parseSequenceMenu(Grammar grammar, Scanner scanner, String outputFileName) {
        System.out.println("Give the file name please:");
        String inputFilename = scanner.nextLine();
        StringBuilder fileContent = new StringBuilder();
        try {
            File myObj = new File(inputFilename);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                fileContent.append(myReader.nextLine());
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred reading the file.");
            e.printStackTrace();
        }
        List<String> formattedContent = List.of(fileContent.toString().split(" "));
        ParseOutput.ParentSiblingTable parseResult = ParseOutput.formatOutputTable(grammar.getProductions(), grammar.parseSequence(formattedContent));

        try {
            File myObj = new File(outputFileName);
            if (myObj.createNewFile())
                System.out.println("File created: " + myObj.getName());
            FileWriter myWriter = new FileWriter(outputFileName);
            myWriter.write(String.valueOf(parseResult));
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}