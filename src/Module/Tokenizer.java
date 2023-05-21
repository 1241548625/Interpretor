package Module;
import java.util.*;
import javax.management.RuntimeErrorException;
import static java.util.Map.entry;

public class Tokenizer {
	static String allchar = "abcedfghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_1234567890";
	static String allnum = "1234567890";
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

	public static void tokenize(String str, ArrayList<Token> tokens) {
		int i = 0;
		int e;                                            // ending index of a token
		int n = str.length();

		for (; ; ) {
			if (i >= n) {
				return;
			}
			switch (str.charAt(i)) {
				case '0':
					tokens.add(new Token(Token.IL, "0"));
					if (i < n - 1 && Character.isDigit(str.charAt(i + 1))) {
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
					e = extractLiteral(str, i + 1);
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
					e = extractIdentifier(str, i + 1);
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

	static int extractLiteral(String str, int i) {
		if (i > str.length()) {
			return i;
		}
		HashSet<Character> set = new HashSet<>();
		for (int j = 0; j < allnum.length(); j++) {
			set.add(allnum.charAt(j));
		}
		for (int k = i; k < str.length(); k++) {
			if (!set.contains(str.charAt(k))) {
				return k;
			}
		}
		return str.length();
	}

	static int extractIdentifier(String str, int i) {
		if (i > str.length()) {
			return i;
		}
		// add all char to set 
		HashSet<Character> set = new HashSet<>();
		for (int j = 0; j < allchar.length(); j++) {
			set.add(allchar.charAt(j));
		}

		// check if str has char in set 
		for (int k = i; k < str.length(); k++) {
			if (!set.contains(str.charAt(k))) {
				return k;
			}
		}
		return str.length();
	}

	static void error(String str) {
		throw new RuntimeException(str);
	}
}