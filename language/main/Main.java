package language.main;

import language.ast.Expression;
import language.ast.DefunNode; 
import language.evaluator.Environment;
import language.lexer.Scanner;
import language.parser.Parser;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        Environment env = new Environment();
        java.util.Scanner console = new java.util.Scanner(System.in);

        System.out.println("MyLanguage REPL v1.0 - Type 'exit' to quit.");

        while (true) {
            System.out.print("> "); 
            String line = console.nextLine().trim();
            
            if (line.equalsIgnoreCase("exit")) break;
            if (line.isBlank()) continue;
            
            try {
                if (line.startsWith("run ")) {
                    String filename = line.substring(4).trim();
                    String code = Files.readString(Path.of(filename));
                    
                    var tokens = new Scanner(code).scanTokens();
                    var statements = new Parser(tokens).parse();
                    
                    Environment freshEnv = new Environment();
                    
                    for (Expression stmt : statements) {
                        stmt.evaluate(freshEnv, 0); 
                    }
                } else {
                    var tokens = new Scanner(line).scanTokens();
                    var statements = new Parser(tokens).parse();
                    
                    for (Expression stmt : statements) {
                        stmt.evaluate(env, 0);
                    }
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        console.close();
    }
}