package Module;
import java.util.*;
import javax.management.RuntimeErrorException;
import static java.util.Map.entry;

public class Tokenizer{
	static String allchar="abcedfghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_1234567890";
	static String allnum="1234567890";
//    public static void tokenize(Scanner sc, ArrayList<Token> tokens){
//        while (sc.hasNext()){
//			String line = sc.nextLine();
//			tokenize(line, tokens);
//			System.out.println("done");
//			System.out.println(tokens.toString());
//		}
//	}
	// public static void tokenize(ArrayList<Token> tokens, ExpParser p){
		
	// }
    
	public static void tokenize(String str, ArrayList<Token> tokens){
		int i = 0;
		int e;                                            // ending index of a token
		int n = str.length();
        
		for (;;){
			if (i >= n){
				return;
			}
			switch (str.charAt(i)){
				case '0':
					tokens.add(new Token(Token.IL, "0"));
					if (i < n-1 && Character.isDigit(str.charAt(i+1))) {
						error("invalid literal");
					}
					i++;
					break;
				case '1':
				case '2':
				case '3':
				case '4':
				case '5':
				case '6':
				case '7':
				case '8':
				case '9':
					e = extractLiteral(str, i+1);
					tokens.add(new Token(Token.IL, str.substring(i, e)));
					i = e;
					break;

				case 'A':
		    	case 'B':
				case 'C':
				case 'D':
				case 'E':
				case 'F':
		    	case 'G':
				case 'H':
				case 'I':
				case 'J':
				case 'K':
				case 'L':
		    	case 'M':
				case 'N':
				case 'O':
				case 'P':
				case 'Q':
		    	case 'R':
				case 'S':
				case 'T':
				case 'U':
				case 'V':
		    	case 'W':
				case 'X':
				case 'Y':
				case 'Z':
				case 'a':
		    	case 'b':
				case 'c':
				case 'd':
				case 'e':
				case 'f':
				case 'g':
		    	case 'h':
				case 'i':
				case 'j':
				case 'k':
				case 'l':
		    	case 'm':
				case 'n':
				case 'o':
				case 'p':
				case 'q':
				case 'r':
		    	case 's':
				case 't':
				case 'u':
				case 'v':
				case 'w':
		    	case 'x':
				case 'y':
				case 'z':
					e = extractIdentifier(str, i+1);
					tokens.add(new Token(Token.ID, str.substring(i, e)));
					i = e;
					break;

				case '=':
				case '+':
				case '-':
				case '*':
					tokens.add(new Token(Token.OP, Character.toString(str.charAt(i))));
					i++;
					break;
				case '(':
					tokens.add(new Token(Token.LP, "("));
					i++;
					break;
				case ')':
					tokens.add(new Token(Token.RP, ")"));
					i++;
					break;
				case ';':
					tokens.add(new Token(Token.PM, ";"));
					i++;
					break;
				case ' ':
					i++;
					break;
				default:
					error("unrecognized symbol:" + str);
			}
		}
	}
	
	static int extractLiteral(String str, int i){
		if(i > str.length()){
			return i;
		}
		HashSet set = new HashSet<Integer>();
		for(int j=0;j<allnum.length(); j++){
			set.add(allnum.charAt(j));
		}
		for(int k=i; k<str.length(); k++){
			if(!set.contains(str.charAt(k))){
				return k;
			}
		}
		return str.length();
	}

	static int extractIdentifier(String str, int i){
		if(i> str.length()){
			return i;
		}
		// add all char to set 
		HashSet set = new HashSet<Character>();
		for(int j=0; j<allchar.length(); j++){
			set.add(allchar.charAt(j));
		}

		// check if str has char in set 
		for(int k=i; k<str.length(); k++){
			if(!set.contains(str.charAt(k))){
				return k;
			}
		}
		return str.length();
	}

	static void error(String str){
		throw new RuntimeException(str);
	}

//	public static void main(String []args){
//		// if (args.length != 1){
//		// 	System.out.println("Usage: java Tokenizer file");
//		// 	System.exit(0);
//		// }
//		ArrayList<Token> tokens = new ArrayList<>();
//	//	Scanner sc = new Scanner(args[0]);
//	    System.out.println("input:");
//	    Scanner sc = new Scanner(System.in);
//		tokenize(sc, tokens);
//		System.out.println("outside");
//		System.out.println(tokens.toString());
//
//		ExpParser.tokens = tokens;
//		ExpParser.Assignment();
//
//
//	}
}
//
//class Token {
//	public static int
//		ID = 0,     // Identifier
//		IL = 1,     // Integer literal
//		OP = 2,     // operator
//		PM = 3,     // punctuation mark
//		LP = 4,     // left parenthesis
//		RP = 5;     // right parenthesis
//
//	public int type;
//	public String lexeme;
//	public Map<Integer,String> map = Map.ofEntries(
//		entry(0,"Indentifier"),
//		entry(1,"literal"),
//		entry(2,"operator"),
//		entry(3,"punctuation"),
//		entry(4,"left parenthesis"),
//		entry(5,"right parenthesis"),
//		entry(-1, "ending")
//	);
//
//	// public HashMap<String, Integer> map = new HashMap<>(){
//	// 	"indentifier": 0,
//	// 	"literal": 1
//	// };
//	public Token(int type, String lexeme){
//		this.type = type;
//		this.lexeme = lexeme;
//	}
//
//	public String toString(){
//		return map.get(type) +":"+ lexeme+"\n";
//	}
//}
//
//class ExpParser{
//	//static String str;
//	public static ArrayList<Token> tokens;
//	static int curToken;
//
//    //check each token
//	static Token currentToken(){
//		//if finishing check all token, then return '$'
//		if(curToken>=tokens.size()){
//			// check last token is ";"
//			Token lastToken = tokens.get(curToken-1);
//			if(lastToken.type != Token.PM || !lastToken.lexeme.equals(";")){
//				error();
//			}
//			return new Token(-1, "$");
//		}
//		//otherwise return the current token, send error message
//		return tokens.get(curToken);
//	}
//
//	static void readNextToken(){
//		curToken++;
//	}
//
//	static void Assignment(){
//		if (currentToken().type != Token.ID) {
//			error();
//		}
//		readNextToken();
//		if (currentToken().type != Token.OP || !currentToken().lexeme.equals("=")){
//			error();
//		}
//		readNextToken();
//		exp();
//	}
//	static void exp(){
//		term();
//		// + -
//		expPrime();
//	}
//	static void term(){
//		//( digit + - )
//		factor();
//		// * /
//		termPrime();
//	}
//	static void expPrime(){
//		Token token = currentToken();
//		switch(token.lexeme){
//			case "+":
//			case "-":
//			     readNextToken();
//				 //check if it digit after + -
//				 term();
//				 expPrime();
//		}
//	}
//
//	static void termPrime(){
//		Token token = currentToken();
//		switch(token.lexeme){
//			case "/":
//			case "*":
//			     readNextToken();
//				 //check if it digit after / *
//				 factor();
//				 termPrime();
//		}
//	}
//
//	static void factor(){
//		Token token = currentToken();
//		switch(token.type){
//			// 4 -> left parenthesis
//			// 5 -> right parenthesis
//			case 4 :
//			    readNextToken();
//				exp();
//				//after read next token again and again
//				if( currentToken().type == Token.RP){
//					readNextToken();
//				}
//				else{
//					error();
//				}
//				break;
//
//			default:
//				// Token.IL Integer Literal
//			    if(token.type == Token.IL || token.type == Token.ID){
//					readNextToken();
//				}
//				else{
//					error();
//				}
//		}
//	}
//
//    static void error(){
//		throw new RuntimeErrorException(null, "syntac error");
//	}
//
//	/*
//	statements = []
//	for line : files:
//		tokens = tokenize(line)
//		statements.push(tokens)
//
//
//	statements = [[Indentifier:x, operator:=, literal:10, punctuation:;], [Indentifier:y, operator:=, literal:9, punctuation:;]]
//	for statement : statments:
//		stetement.assignemnt()
//	 */
//}
