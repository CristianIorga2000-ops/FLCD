import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class FAParser {

    public static Automaton constructAutomatonFromFile(String filename) throws FileNotFoundException {
        Automaton automaton = new Automaton();
        Map<String, List<String>> headers = parse(filename);
        automaton.setAlphabet(List.of(headers.get("ALPHABET").get(0).split(" ")));
        automaton.setEnd(headers.get("END"));
        automaton.setInitial(headers.get("INITIAL").get(0));
        automaton.setStates(List.of(headers.get("STATES").get(0).split(" ")));
        automaton.setTransitions(headers.get("TRANSITIONS"));

        return automaton;
    }
    
    private static Map<String, List<String>> parse(String filename) throws FileNotFoundException {
        Map<String, List<String>> headers;
        String currentHeader;
        currentHeader = "";
        headers = new HashMap<>();
        headers.put("ALPHABET", new ArrayList<>());
        headers.put("STATES", new ArrayList<>());
        headers.put("TRANSITIONS", new ArrayList<>());
        headers.put("INITIAL", new ArrayList<>());
        headers.put("END", new ArrayList<>());
        for (String line : readFile(filename)){
            if (headers.containsKey(line)){
                currentHeader = line;
            }
            else {
                headers.get(currentHeader).add(line);
            }
        }

        return headers;
    }

    private static List<String> readFile(String filename) throws FileNotFoundException {
        List<String> tokens = new ArrayList<>();
        Scanner sc = new Scanner(new File(filename));

        while (sc.hasNextLine())
            tokens.add(sc.nextLine());

        return tokens;
    }
}
