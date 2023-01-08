import java.util.*;

public class ParsingTable {
    private final Map<String, Map<String, Production>> table;

    public ParsingTable(Grammar grammar) {
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
                    row.put(terminal, production);
                } else {
                    for(String terminalAfter : grammar.getFollow().get(production.leftHandSide)) {
                        row.put(terminalAfter, production);
                    }
                }
            }
        }
    }

    public Optional<Production> get(String nonTerminal, String terminal) {
        Map<String, Production> nonTerminalMap = table.get(nonTerminal);
        if (nonTerminalMap == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(nonTerminalMap.get(terminal));
    }
}
