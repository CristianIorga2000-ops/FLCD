import java.util.List;
import java.util.Map;
import java.util.Set;

public class Test {
    private static final Grammar testGrammar = new Grammar(
            List.of("S", "A", "B", "C", "D"),
            Set.of("+", "*", "(", ")", "a"),
            "S",
            List.of(
                    new Production("S", List.of("B", "A"), 1),
                    new Production("A", List.of("+", "B", "A"), 2),
                    new Production("A", List.of(""), 3),
                    new Production("B", List.of("D", "C"), 4),
                    new Production("C", List.of("*", "D", "C"), 5),
                    new Production("C", List.of(""), 6),
                    new Production("D", List.of("(", "S", ")"), 7),
                    new Production("D", List.of("a"), 8)
            )
    );
    private static final Grammar testGrammar2 = new Grammar(
            List.of("E", "T"),
            Set.of("+", "id", "const", "(", ")"),
            "E",
            List.of(
                    new Production("E", List.of("E", "+", "T"), 0),
                    new Production("T", List.of("id"), 1),
                    new Production("T", List.of("const"), 2),
                    new Production("T", List.of("(", "E", ")"), 3)
            )
    );

    private static void test() {
        Map<String, Set<String>> first = testGrammar.getFirst();
        System.out.println("First: " + first);
        System.out.println("First is correct: " + first.equals(Map.of(
                "S", Set.of("(", "a"),
                "A", Set.of("+", ""),
                "B", Set.of("(", "a"),
                "C", Set.of("*", ""),
                "D", Set.of("(", "a"),
                "+", Set.of("+"),
                "*", Set.of("*"),
                "(", Set.of("("),
                ")", Set.of(")"),
                "a", Set.of("a")
        )));
        Map<String, Set<String>> follow = testGrammar.getFollow();
        System.out.println("Follow: " + follow);
        System.out.println("Follow is correct: " + follow.equals(Map.of(
                "S", Set.of("", ")"),
                "A", Set.of("", ")"),
                "B", Set.of("", "+", ")"),
                "C", Set.of("+", "", ")"),
                "D", Set.of("*", "+", "", ")")
        )));
        ParsingTable parsingTable = new ParsingTable(testGrammar);
        List<Production> productions = testGrammar.getProductions();

        List<Boolean> matches = List.of(
                productions.get(0).equals(parsingTable.get("S", "a")),
                productions.get(0).equals(parsingTable.get("S", "(")),
                productions.get(1).equals(parsingTable.get("A", "+")),
                productions.get(2).equals(parsingTable.get("A", ")")),
                productions.get(2).equals(parsingTable.get("A", "$")),
                productions.get(3).equals(parsingTable.get("B", "a")),
                productions.get(3).equals(parsingTable.get("B", "(")),
                productions.get(5).equals(parsingTable.get("C", "+")),
                productions.get(4).equals(parsingTable.get("C", "*")),
                productions.get(5).equals(parsingTable.get("C", ")")),
                productions.get(5).equals(parsingTable.get("C", "$")),
                productions.get(7).equals(parsingTable.get("D", "a")),
                productions.get(6).equals(parsingTable.get("D", "("))
        );
        System.out.println("Parsing table matches: " + matches);

        System.out.println("Parsing succeeded: " + testGrammar.parseSequence(List.of("a", "*", "(", "a", "+", "a", ")"))
                .equals(List.of(1, 4, 8, 5, 7, 1, 4, 8, 6, 2, 4, 8, 6, 3, 6, 3)));
        try {
            testGrammar.parseSequence(null);
        } catch (IllegalArgumentException illegalArgumentException) {
            System.out.println("Null parse sequence was caught successfully.");
        }
        try {
            testGrammar.parseSequence(List.of("a", "*", "(", "a", "+", "a", ")", "(", ")"));
        } catch (IllegalArgumentException illegalArgumentException) {
            System.out.println("Incorrect parse sequence was caught successfully.");
        }
    }

    public static void main(String[] args) {
        test();
    }
}
