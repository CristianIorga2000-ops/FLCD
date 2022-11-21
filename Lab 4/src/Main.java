import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        chooseAutomaton();
    }

    public static void chooseAutomaton(){
        Scanner myObj = new Scanner(System.in);
        System.out.println("Choose your pokemon\n1)constant\n2)identifier\n");
        String input = myObj.nextLine();
        switch (input){
            case "1":
                parseAutomaton("FA_constant.in");
                break;
            case "2":
                parseAutomaton("FA_identifier.in");
                break;
            default:
                System.out.println("Bro pick 1 or 2 not " + input + " is it that hard??");
                chooseAutomaton();
                break;
        }
    }

    public static void parseAutomaton(String filename){
        Automaton automaton = new Automaton();
        System.out.println("Hello world!");
        try {
            automaton = FAParser.constructAutomatonFromFile("/home/hp/Development/School/FLCD/Lab 4/src/Automata/FA_identifier.in");
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
        while (true)
            automatonMenu(automaton);
    }

    public static void automatonMenu(Automaton automaton){
        Scanner myObj = new Scanner(System.in);
        System.out.println("Choose one of the following stuff");
        System.out.println("1. Print set of all states");
        System.out.println("2. Print the alphabet");
        System.out.println("3. Print the transitions");
        System.out.println("4. Print initial and final states");
        System.out.println("100. Check a string against your automaton\n");
        String command = myObj.nextLine();
        switch (command) {
            case "1":
                System.out.println(automaton.getStates());
                break;
            case "2":
                System.out.println(automaton.getAlphabet());
                break;
            case "3":
                System.out.println(automaton.getTransitions());
                break;
            case "4":
                System.out.println(automaton.getInitial());
                System.out.println(automaton.getEnd());
                break;
            case "100":
                checkMenu(automaton);
                break;
            default:
                System.out.println(command + "?? Wtf is that?");
        }
    }

    public static void checkMenu (Automaton automaton){
        Scanner myObj = new Scanner(System.in);
        System.out.println("Give input string: \n");
        String command = myObj.nextLine();

    }
}