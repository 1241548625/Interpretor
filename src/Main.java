import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import Module.*;
import Module.ExpParser;


// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String []args) {
         if (args.length != 1) {
             System.out.println("Usage: java Tokenizer file");
             System.exit(0);
         }
         File file = new File(args[0]);
         try {
             Scanner sc = new Scanner(file);
             ArrayList<ArrayList<Token>> statements = new ArrayList<>();
             while(sc.hasNext()) {
                 String line = sc.nextLine();
                 if(line.length() > 0) {
                     ArrayList<Token> tokens = new ArrayList<>();
                     Tokenizer.tokenize(line, tokens);
//                 System.out.println(tokens.toString());
                     ExpParser.tokens = tokens;
                     ExpParser.curToken = 0;
                     ExpParser.parse();

                     statements.add(tokens);
                 }
             }

             for (Map.Entry<String, Integer> entry : ExpParser.Vars.entrySet()) {
                System.out.println(String.format("%s = %d", entry.getKey(), entry.getValue()));
             }

         }
         catch (FileNotFoundException e) {
             e.printStackTrace();
         }
    }
}