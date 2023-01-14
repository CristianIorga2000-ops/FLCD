import java.util.*;

public class ParsingTable {
    private final Map<String, Map<String, Production>> table;
    private final Map<List<String>, Set<Production>> conflicts;

    public ParsingTable(Grammar grammar) throws IllegalArgumentException {
        conflicts = new HashMap<>();

        table = new HashMap<>();
        for (String nonTerminal : grammar.getNonTerminals()) {
            table.put(nonTerminal, new HashMap<>());
        }

        for (Production production : grammar.getProductions()) {
            Set<String> firstOfRightHandSide = grammar.computeFirstOfSequence(production.rightHandSide());
            for (String terminal : firstOfRightHandSide) {
                if (!terminal.isEmpty()) {
                    addToTable(production.leftHandSide(), terminal, production);
                } else {
                    addToTable(production.leftHandSide(), "$", production);
                    for(String terminalAfter : grammar.getFollow().get(production.leftHandSide())) {
                        addToTable(production.leftHandSide(), terminalAfter, production);
                    }
                }
            }
        }

        if (!conflicts.isEmpty()) {
            throw new IllegalArgumentException("Grammar is not LL(1) because of conflicts: " + conflicts);
        }
    }

    public Production get(String nonTerminal, String terminal) {
        Map<String, Production> nonTerminalMap = table.get(nonTerminal);
        if (nonTerminalMap == null) {
            return null;
        }
        return nonTerminalMap.get(terminal);
    }

    private void addToTable(String nonTerminal, String terminal, Production production) {
        Map<String, Production> row = table.get(nonTerminal);
        if (row.containsKey(terminal)) {
            List<String> pair = List.of(nonTerminal, terminal);
            if (conflicts.containsKey(pair)) {
                conflicts.get(pair).add(production);
            } else {
                conflicts.put(pair, new HashSet<>(List.of(row.get(terminal), production)));
            }
        }
        else {
            row.put(terminal, production);
        }
    }
}
