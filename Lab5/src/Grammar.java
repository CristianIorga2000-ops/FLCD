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
        Scanner sc = new Scanner(new File(System.getProperty("user.dir") + "/src/" + filename));
        List<String> nonTerminals = Arrays.asList(sc.nextLine().split(" "));
        Set<String> terminals = Set.of(sc.nextLine().split(" "));
        String startingSymbol = sc.nextLine();
        List<String> productionsRows = new ArrayList<>();
        while (sc.hasNextLine()) {
            productionsRows.add(sc.nextLine());
        }

        List<Production> productions = new ArrayList<>();
        for (String production : productionsRows) {
            // The following implementation does not detect \\ and \| so we do that tractorist style
            List<String> splitStr = Arrays.asList(production.split("->"));
            String nonTerminal = splitStr.get(0).trim().strip();
            List<String> ends = List.of(splitStr.get(1).split("\\|"));
            if (production.contains("\\\\|")) {
                productions.add(new Production(nonTerminal, List.of("|")));
            }
            if (production.contains("\\\\")) {
                productions.add(new Production(nonTerminal, List.of("\\")));
            }
            for (String end : ends) {
                productions.add(new Production(nonTerminal, List.of(end.trim().strip().split(" "))));
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
        // Build a map from nonTerminals to their First (initially empty) set:
        Map<String, Set<String>> first = nonTerminals.stream().collect(
                Collectors.toMap(nonTerminal -> nonTerminal, nonTerminal -> new HashSet<>()));

        Set<Production> nonTerminalProductions = new HashSet<>();
        for (Production production : productions) {
            String firstSymbol = production.rightHandSide.get(0);
            // If the first symbol on the right hand side is either epsilon or a terminal:
            if (firstSymbol.equals("") || terminals.contains(firstSymbol)) {
                first.get(production.leftHandSide).add(firstSymbol); // symbol added to First
            } else {
                // save the productions that start with a non-terminal on the right hand side:
                nonTerminalProductions.add(production);
            }
        }

        boolean changed = true;
        while (changed) { // While there are still iterations to perform:
            changed = false;
            for (Production production : nonTerminalProductions) {
                int currentIndex = 0;
                Set<String> firstOfLeftHandSide = first.get(production.leftHandSide);
                while (true) {
                    String firstSymbol = production.rightHandSide.get(currentIndex);
                    Set<String> firstOfFirstSymbol = first.get(firstSymbol);

                    if (firstOfFirstSymbol.contains("") && currentIndex + 1 < production.rightHandSide.size()) {
                        for (String symbol : firstOfFirstSymbol) {
                            if (!Objects.equals(symbol, "")) {
                                if (firstOfLeftHandSide.add(symbol)) {
                                    changed = true;
                                }
                            }
                        }
                        currentIndex++;
                    } else {
                        if (firstOfLeftHandSide.addAll(firstOfFirstSymbol)) {
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
        Map<String, Set<String>> result = new HashMap<>();
        Map<String, Set<String>> firstResult = new HashMap<>();
        for (String nonTerminal : this.nonTerminals) {
            result.put(nonTerminal, computeFollowForOne(nonTerminal, firstResult));
        }
        return result;
    }

    //Computing follow for one non-terminal
    private Set<String> computeFollowForOne(String nonterminal, Map<String, Set<String>> firstResult) {
        Set<String> result = new HashSet<>();
        //Search all productions
        for (Production production : this.productions) {
            //For a right hand side that contains that nonterminal
            //When such a right hand side is found
            if (production.rightHandSide.contains(nonterminal)) {
                int index = production.rightHandSide.indexOf(nonterminal);
                //And our nonterminal is not the last element
                if (index < production.rightHandSide.size() - 1) {
                    String next = production.rightHandSide.get(index + 1);
                    //If it is a non-terminal
                    if (firstResult.containsKey(next)) {
                        //Add FIRST of it to the set
                        result.addAll(firstResult.get(next));
                        //If it is a terminal, simply add it
                    } else {
                        result.add(next);
                    }
                }
            }
        }
        return result;
    }
}

