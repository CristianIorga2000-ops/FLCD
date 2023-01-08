import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class Grammar {
    List<String> nonTerminals;
    Set<String> terminals;
    String startingSymbol;
    List<Production> productions;
    Map<String, Set<String>> first;
    Map<String, Set<String>> follow;

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

    public Map<String, Set<String>> getFirst() {
        if (first == null) {
            first = computeFirst();
        }
        return first;
    }

    public Set<String> computeFirstOfSequence(List<String> sequence) {
        Set<String> result = new HashSet<>();

        if (sequence.isEmpty() || "".equals(sequence.get(0))) {
            result.add("");
            return result;
        }

        String firstSymbol = sequence.get(0);
        if (terminals.contains(firstSymbol)) {
            result.add(firstSymbol);
            return result;
        }

        result.addAll(first.get(firstSymbol));
        if (result.contains("") && sequence.size() > 1) {
            result.remove("");
            result.addAll(computeFirstOfSequence(sequence.subList(1, sequence.size())));
        }
        return result;
    }

    private Map<String, Set<String>> computeFirst() {
        // Build a map from nonTerminals to their First (initially empty) set:
        Map<String, Set<String>> first = nonTerminals.stream().collect(
                Collectors.toMap(nonTerminal -> nonTerminal, nonTerminal -> new HashSet<>()));
        first.putAll(terminals.stream().collect(Collectors.toMap(terminal -> terminal, Set::of)));

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
                                changed = firstOfLeftHandSide.add(symbol) || changed;
                            }
                        }
                        currentIndex++;
                    } else {
                        changed = firstOfLeftHandSide.addAll(firstOfFirstSymbol) || changed;
                        break;
                    }
                }
            }
        }

        return first;
    }

    public Map<String, Set<String>> getFollow() {
        if (follow == null) {
            follow = computeFollow();
        }
        return follow;
    }

    private Map<String, Set<String>> computeFollow() {
        Map<String, Set<String>> follow = nonTerminals.stream().collect(
                Collectors.toMap(nonTerminal -> nonTerminal, nonTerminal -> new HashSet<>()));
        follow.putAll(terminals.stream().collect(
                Collectors.toMap(terminal -> terminal, nonTerminal -> new HashSet<>())));

        follow.get(startingSymbol).add("");

        boolean changed = true;
        while(changed) {
            changed = false;
            for (Production production : productions) {
                List<String> rightHandSide = production.rightHandSide;
                String leftHandSide = production.leftHandSide;
                for(int i = 0; i < rightHandSide.size(); i++) {
                    String currentSymbol = rightHandSide.get(i);

                    if ("".equals(currentSymbol)) {
                        break;
                    }

                    List<String> suffix = rightHandSide.subList(i + 1, rightHandSide.size());

                    Set<String> firstOfWhatFollows = computeFirstOfSequence(suffix);

                    if (firstOfWhatFollows.contains("")) {
                        firstOfWhatFollows.remove("");
                        changed = follow.get(currentSymbol).addAll(follow.get(leftHandSide)) || changed;
                    }
                    changed = follow.get(currentSymbol).addAll(firstOfWhatFollows) || changed;
                }
            }
        }

        terminals.forEach(follow::remove);
        return follow;
    }

//    private Map<String, Map<String, Integer>> computeParsingTable() {
//
//        nonTerminals.
//    }
}

