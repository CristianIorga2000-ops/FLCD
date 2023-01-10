import java.util.*;

public class ParsingTable {
    private final Map<String, Map<String, Production>> table;

    public ParsingTable(Grammar grammar) throws IllegalArgumentException {
        table = new HashMap<>();
        for (String nonTerminal : grammar.getNonTerminals()) {
            table.put(nonTerminal, new HashMap<>());
        }

        int productionIndex = 1;
        for (Production production : grammar.getProductions()) {
            production.setIndex(productionIndex++);

            Map<String, Production> row = table.get(production.leftHandSide);
            Set<String> firstOfRightHandSide = grammar.computeFirstOfSequence(production.rightHandSide);
            for (String terminal : firstOfRightHandSide) {
                if (!terminal.isEmpty()) {
                    addToTable(row, terminal, production);
                } else {
                    addToTable(row, "$", production);
                    for(String terminalAfter : grammar.getFollow().get(production.leftHandSide)) {
                        addToTable(row, terminalAfter, production);
                    }
                }
            }
        }
    }

    public Production get(String nonTerminal, String terminal) {
        Map<String, Production> nonTerminalMap = table.get(nonTerminal);
        if (nonTerminalMap == null) {
            return null;
        }
        return nonTerminalMap.get(terminal);
    }

    private void addToTable(Map<String, Production> row, String key, Production production) {
        if (row.containsKey(key)) {
            throw new IllegalArgumentException("Grammar is not LL(1)");
        }
        row.put(key, production);
    }
}
