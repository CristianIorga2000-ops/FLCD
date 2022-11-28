public class Production {
    public String nonterminal;
    public String end;

    public Production(String nonterminal, String end) {
        this.nonterminal = nonterminal;
        this.end = end;
    }
    @Override
    public String toString(){
        return nonterminal + " -> " + end;
    }
}
