import java.util.List;
import java.util.Map;
import java.util.Set;

public class Test {
    private static final Grammar testGrammar = new Grammar(
            List.of("S", "A", "B", "C", "D"),
            Set.of("+", "*", "(", ")", "a"),
            "S",
            List.of(
                    new Production("S", List.of("B", "A")),
                    new Production("A", List.of("+", "B", "A")),
                    new Production("A", List.of("")),
                    new Production("B", List.of("D", "C")),
                    new Production("C", List.of("*", "D", "C")),
                    new Production("C", List.of("")),
                    new Production("D", List.of("(", "S", ")")),
                    new Production("D", List.of("a"))
            )
    );
    private static final Grammar testGrammar2 = new Grammar(
            List.of("E", "T"),
            Set.of("+", "id", "const", "(", ")"),
            "E",
            List.of(
                    new Production("E", List.of("E", "+", "T")),
                    new Production("T", List.of("id")),
                    new Production("T", List.of("const")),
                    new Production("T", List.of("(", "E", ")"))
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
    }

    public static void main(String[] args) {
        test();
    }
}
