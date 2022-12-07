import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class Grammar {
    List<String> nonTerminals;
    Set<String> terminals;
    String startingSymbol;
    List<Production> productions;


    public Grammar(String filename) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(System.getProperty("user.dir") + "/Lab5/src/" + filename));
        List<String> nonTerminals = Arrays.asList(sc.nextLine().split(" "));
        Set<String> terminals = Set.of(sc.nextLine().split(" "));
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
                productions.add(new Production(nonTerminal, List.of("|")));
            }
            if(production.contains("\\\\")) {
                productions.add(new Production(nonTerminal, List.of("\\")));
            }
            for(String end : ends){
                productions.add(new Production(nonTerminal, List.of(end.replace('\\',' ').trim().strip())));
            }
        }

        this.nonTerminals = nonTerminals;
        this.terminals = terminals;
        this.startingSymbol = startingSymbol;
        this.productions = productions;
    }

    public Grammar(List<String> nonTerminals, Set<String> terminals, String startingSymbol, List<Production> productions) {
        this.nonTerminals = nonTerminals;
        this.terminals = terminals;
        this.startingSymbol = startingSymbol;
        this.productions = productions;
    }

    public List<String> getNonTerminals() {
        return nonTerminals;
    }

    public Set<String> getTerminals() {
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

    public Map<String, Set<String>> computeFirst() {
        // TODO: Work in progress
        boolean changed = true;
        Map<String, Set<String>> first = nonTerminals.stream().collect(
                Collectors.toMap(nonTerminal -> nonTerminal, nonTerminal -> new HashSet<>()));

        Set<Production> nonTerminalProductions = new HashSet<>();
        for (Production production : productions) {
            String firstSymbol = production.rightHandSide.get(0);
            if (firstSymbol.equals("") || nonTerminals.contains(firstSymbol)) {
                first.get(production.leftHandSide).add(firstSymbol);
            }
            else {
                nonTerminalProductions.add(production);
            }
        }

        while (changed) {
            changed = false;
            for (Production production : nonTerminalProductions) {
                int currentIndex = 0;
                Set<String> firstOfLeftHandSide = first.get(production.leftHandSide);
                while(true) {
                    String firstSymbol = production.rightHandSide.get(currentIndex);
                    Set<String> firstOfFirstSymbol = first.get(firstSymbol);

                    if (firstOfFirstSymbol.contains("") && currentIndex + 1 < production.rightHandSide.size()) {
                        for (String symbol : firstOfFirstSymbol) {
                            if (!Objects.equals(symbol, "")) {
                                if(firstOfLeftHandSide.add(symbol)) {
                                    changed = true;
                                }
                            }
                        }
                        currentIndex++;
                    } else {
                        if(firstOfLeftHandSide.addAll(firstOfFirstSymbol)) {
                            changed = true;
                        }
                        break;
                    }
                }
            }
        }

        return first;
    }

    public Map<String, Set<String>> computeFollow() {
        throw new UnsupportedOperationException();
    }
}

