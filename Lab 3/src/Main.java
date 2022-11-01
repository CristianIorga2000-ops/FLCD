import util.ProgramInternalForm;
import util.SymbolTable;

import java.io.FileNotFoundException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        analyze("p1.ghs");
        analyze("p1err.ghs");
        analyze("p2.ghs");
        analyze("p3.ghs");




    }

    public static void analyze(String filename){
        LexicalAnalyzer lexicusMaximus = new LexicalAnalyzer();
        try {
            lexicusMaximus.scan(filename,"tokens.in");
            System.out.println("Scan over");
            System.out.println(lexicusMaximus.getIdentifiers());
            System.out.println(lexicusMaximus.getConstants());
            System.out.println(lexicusMaximus.getPif());
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("DONE for " + filename + "!  \n\n\n");
    }

}