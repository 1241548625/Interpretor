package Module;

import javax.management.RuntimeErrorException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ExpParser {
    //static String str;
    public static ArrayList<Token> tokens;
    public static int curToken;

    public static Map<String, Integer> Vars = new LinkedHashMap<>();
    //check each token
    static Token currentToken(){
        //if finishing check all token, then return '$'
        if(curToken>=tokens.size()){
            // check last token is ";"
            Token lastToken = tokens.get(curToken-1);
            if(lastToken.type != Token.PM || !lastToken.lexeme.equals(";")){
                error("Syntax Error: \";\" is expected");
            }
            return new Token(-1, "$");
        }
        //otherwise return the current token, send error message
        return tokens.get(curToken);
    }

    static void readNextToken(){
        curToken++;
    }

    static void eval_stack(ArrayList<Token> stack){
        int result = 0;
        while(true) {
            if (stack.size() == 0) {
                stack.add(new Token(Token.IL, String.valueOf(result)));
                break;
            }
            Token token = stack.get(stack.size()-1);
            stack.remove(stack.size()-1);
            if (token.lexeme == "("){
                stack.add(new Token(Token.IL, String.valueOf(result)));
                break;
            }

            if(token.type == Token.IL){
                result += Integer.parseInt(token.lexeme);
            }
            else if(token.type == Token.ID){
                if(Vars.containsKey(token.lexeme)) {
                    result += Vars.get(token.lexeme);
                }
                else {
                    error(String.format("Variable \"%s\" is reference before assignment", token.lexeme));
                }
            }
            else if(token.type == Token.OP) {
                switch (token.lexeme) {
                    case "-":
                        result *= -1;
                        break;
                    case "*":
                        result *= Integer.parseInt(stack.get(stack.size() - 1).lexeme);
                        stack.remove(stack.size() - 1);
                        break;
                }
            }
        }
    }
    static int eval_exp(int index){
        ArrayList<Token> stack = new ArrayList<>();
        stack.add(new Token(Token.IL, String.valueOf(0)));
        for(int i=index; i<tokens.size(); i++){
            Token token = tokens.get(i);
            if(token.lexeme == ";" || token.lexeme == ")") {
                eval_stack(stack);
            }
            else {
                stack.add(token);
            }
        }
        return Integer.parseInt(stack.get(0).lexeme);
    }
    public static void parse(){
        // Starting with Assignment
        if (currentToken().type != Token.ID) {
            error("Syntax Error");
        }
        readNextToken();
        if (currentToken().type != Token.OP || !currentToken().lexeme.equals("=")) {
            error("Syntax Error: Assignment operator \"=\" is expect");
        }
        readNextToken();
        exp();
        int result = eval_exp(2);
        Vars.put(tokens.get(0).lexeme, result);
    }
    static void exp(){
        term();
        // + -
        expPrime();
    }
    static void term(){
        //( digit + - )
        factor();
        // * /
        termPrime();
    }
    static void expPrime(){
        Token token = currentToken();
        switch(token.lexeme){
            case "+":
            case "-":
                readNextToken();
                //check if it digit after + -
                term();
                expPrime();
        }
    }

    static void termPrime(){
        Token token = currentToken();
        switch(token.lexeme){
            case "/":
            case "*":
                readNextToken();
                //check if it digit after / *
                factor();
                termPrime();
        }
    }

    static void factor(){
        Token token = currentToken();
        switch(token.lexeme){
            case "-":
            case "+":
                readNextToken();
                factor();
                break;
            // 4 -> left parenthesis
            // 5 -> right parenthesis
            case "(" :
                readNextToken();
                exp();
                //after read next token again and again
                if( currentToken().type == Token.RP && currentToken().lexeme == ")"){
                    readNextToken();
                }
                else{
                    error("Syntax Error: right parenthesis is expected");
                }
                break;
            default:
                // Token.IL Integer Literal
                if(token.type == Token.IL || token.type == Token.ID){
                    readNextToken();
                }
                else{
                    error("Syntax Error");
                }
        }
    }

    static void error(String msg){
        throw new RuntimeErrorException(null, msg);
    }

	/*
	statements = []
	for line : files:
		tokens = tokenize(line)
		statements.push(tokens)


	statements = [[Indentifier:x, operator:=, literal:10, punctuation:;], [Indentifier:y, operator:=, literal:9, punctuation:;]]
	for statement : statments:
		stetement.assignemnt()
	 */
}
