import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class Grammar {
    List<String> nonterminals;
    List<String> terminals;
    String startingSymbol;
    List<Production> productions;


    public Grammar(String filename) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(System.getProperty("user.dir") + "/src/" + filename));
        List<String> nonterminals = Arrays.asList(sc.nextLine().split(" "));
        List<String> terminals = Arrays.asList(sc.nextLine().split(" "));
        String startingSymbol = sc.nextLine();
        List<String> productionsRows = new ArrayList<>();
        while(sc.hasNextLine()){
            productionsRows.add(sc.nextLine());
        }

        List<Production> productions = new ArrayList<>();
        for (String production : productionsRows){
            List<String> splitStr = Arrays.asList(production.split("->"));
            String nonterminal = splitStr.get(0).trim().strip();
            List<String> ends = List.of(splitStr.get(1).split("\\|"));
            for(String end : ends){
                productions.add(new Production(nonterminal, end));
            }
        }

        this.nonterminals = nonterminals;
        this.terminals = terminals;
        this.startingSymbol = startingSymbol;
        this. productions = productions;
    }

    public List<String> getNonterminals() {
        return nonterminals;
    }

    public List<String> getTerminals() {
        return terminals;
    }

    public String getStartingSymbol() {
        return startingSymbol;
    }

    public List<Production> getProductions() {
        return productions;
    }

    public List<Production> getProductionsForNonTerminal(String nonterm) {
        return productions.stream().filter(production -> Objects.equals(production.nonterminal, nonterm)).collect(Collectors.toList());
    }
}

