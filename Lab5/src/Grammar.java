import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class Grammar {
    List<String> nonTerminals;
    List<String> terminals;
    String startingSymbol;
    List<Production> productions;


    public Grammar(String filename) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(System.getProperty("user.dir") + "/Lab5/src/" + filename));
        List<String> nonTerminals = Arrays.asList(sc.nextLine().split(" "));
        List<String> terminals = Arrays.asList(sc.nextLine().split(" "));
        String startingSymbol = sc.nextLine();
        List<String> productionsRows = new ArrayList<>();
        while(sc.hasNextLine()) {
            productionsRows.add(sc.nextLine());
        }

        List<Production> productions = new ArrayList<>();
        for (String production : productionsRows){
            // The following implementation does not detect \\ and \| so we do that tractorist style
            List<String> splitStr = Arrays.asList(production.split("->"));
            String nonTerminal = splitStr.get(0).trim().strip();
            List<String> ends = List.of(splitStr.get(1).split("\\|"));
            if(production.contains("\\\\|")) {
                productions.add(new Production(nonTerminal, "|"));
            }
            if(production.contains("\\\\")) {
                productions.add(new Production(nonTerminal, "\\"));
            }
            for(String end : ends){
                productions.add(new Production(nonTerminal, end.replace('\\',' ').trim().strip()));
            }
        }

        this.nonTerminals = nonTerminals;
        this.terminals = terminals;
        this.startingSymbol = startingSymbol;
        this.productions = productions;
    }

    public List<String> getNonTerminals() {
        return nonTerminals;
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

    public List<Production> getProductionsForNonTerminal(String nonTerminal) {
        return productions.stream().filter(production -> Objects.equals(production.leftHandSide, nonTerminal)).collect(Collectors.toList());
    }

    public Map<String, List<String>> computeFirst() {
        throw new UnsupportedOperationException();
    }

    public Map<String, List<String>> computeFollow() {
        throw new UnsupportedOperationException();
    }
}

