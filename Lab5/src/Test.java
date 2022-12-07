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

    private static void test_first() {
        Map<String, Set<String>> first = testGrammar.computeFirst();
        assert first.equals(Map.of(
                "S", Set.of("(", "a"),
                "A", Set.of("+", ""),
                "B", Set.of("(", "a"),
                "C", Set.of("*", ""),
                "D", Set.of("(", "a")
                )
        );
    }

    public static void main(String[] args) {

    }
}
