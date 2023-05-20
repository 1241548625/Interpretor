package Module;

import java.util.Map;

import static java.util.Map.entry;

public class Token {
    public static int
            ID = 0,     // Identifier
            IL = 1,     // Integer literal
            OP = 2,     // operator
            PM = 3,     // punctuation mark
            LP = 4,     // left parenthesis
            RP = 5;     // right parenthesis

    public int type;
    public String lexeme;
    public Map<Integer,String> map = Map.ofEntries(
            entry(0,"Indentifier"),
            entry(1,"literal"),
            entry(2,"operator"),
            entry(3,"punctuation"),
            entry(4,"left parenthesis"),
            entry(5,"right parenthesis"),
            entry(-1, "ending")
    );
    public Token(int type, String lexeme){
        this.type = type;
        this.lexeme = lexeme;
    }

    public String toString(){
        return map.get(type) +":"+ lexeme+"\n";
    }
}
