import java.util.List;

public class Automaton {
    private List<String> alphabet;
    private List<String> states;
    private List<String> transitions;
    private String initial;
    private List<String> end;

    public List<String> getAlphabet() {
        return alphabet;
    }

    public List<String> getStates() {
        return states;
    }

    public List<String> getTransitions() {
        return transitions;
    }

    public String getInitial() {
        return initial;
    }

    public List<String> getEnd() {
        return end;
    }

    public void setAlphabet(List<String> alphabet) {
        this.alphabet = alphabet;
    }

    public void setStates(List<String> states) {
        this.states = states;
    }

    public void setTransitions(List<String> transitions) {
        this.transitions = transitions;
    }

    public void setInitial(String initial) {
        this.initial = initial;
    }

    public void setEnd(List<String> end) {
        this.end = end;
    }

}
