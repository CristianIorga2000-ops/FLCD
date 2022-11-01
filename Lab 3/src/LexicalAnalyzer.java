import util.ProgramInternalForm;
import util.SymbolTable;
import util.Truple;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LexicalAnalyzer {
    private final String project_path = "/home/hp/Development/School/FLCD";
    private final SymbolTable identifiers;
    private final SymbolTable constants;
    private final ProgramInternalForm pif;

    public LexicalAnalyzer() {
        this.identifiers = new SymbolTable();
        this.constants = new SymbolTable();
        this.pif = new ProgramInternalForm();
    }

    public SymbolTable getIdentifiers() {
        return identifiers;
    }

    public SymbolTable getConstants() {
        return constants;
    }

    public ProgramInternalForm getPif() {
        return pif;
    }

    private List<String> readTokens(String tokensFileName) throws FileNotFoundException {
        List<String> tokens = new ArrayList<>();
        Scanner sc = new Scanner(new File(project_path + "/Lab 1b/" + tokensFileName));

        while (sc.hasNextLine())
            tokens.add(sc.nextLine());

        return tokens;
    }

    public void scan(String filename, String tokensFileName) throws FileNotFoundException, Exception {
        List<String> tokens = readTokens(tokensFileName);

        File file = new File(project_path + "/Lab1/" + filename);
        Scanner sc = new Scanner(file);

        while (sc.hasNextLine()) {
            String redLine = sc.nextLine();
            for (String token : redLine.split(" ")) {
                if (tokens.contains(token)) {
                    pif.add(new Truple<>(token, tokens.size(), -1));
                } else if (isValidIdentifier(token)) {
                    identifiers.add(identifiers.size(), token);
                    pif.add(new Truple<>(token, 1, identifiers.size() - 1));
                } else if (isValidConstant(token)) {
                    constants.add(identifiers.size(), token);
                    pif.add(new Truple<>(token, 0, identifiers.size() - 1));
                } else if (token.length() != 0) {
                    System.out.println(token);
                    System.out.println(token.length());
                    throw new Exception("Token number " + pif.size() + " is  I N V A L I D  boy!!!!: " + token);
                }
            }
        }
    }


    private boolean isValidIdentifier(String token) {
        return token.matches("[a-zA-Z]+([a-zA-Z]|[0-9])*");
    }

    private boolean isValidConstant(String token) {
        return token.matches("[+-]?[1-9][0-9]*") || token.matches("'[a-zA-Z]'") || token.equals("0");
    }


}
