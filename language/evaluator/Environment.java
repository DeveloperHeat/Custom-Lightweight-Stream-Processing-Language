package language.evaluator;
import java.util.*;
import java.util.function.Function;

import language.ast.Expression;

public class Environment {
	
    private final Map<String, Integer> variables = new HashMap<>();
    private final Map<String, Function<Integer, Integer>> functions = new HashMap<>();
    private final List<String> todoList = new ArrayList<>();
    private final Map<String, List<Expression>> userFunctions = new HashMap<>();

    // Update your Variable/Function evaluation logic to check this map!
    // (As we discussed previously, this is where you execute the body)

    public Environment() {
        functions.put("log", x -> { System.out.println("LOG: " + x); return x; });
        functions.put("square", x -> x * x);
        functions.put("double", x -> x * 2);
        
        functions.put("if", x -> {
        	return (x > 0) ? x : 0; 
        });
        
        functions.put("save", x -> {
            try {
                java.nio.file.Files.writeString(
                    java.nio.file.Path.of("output.txt"), 
                    String.valueOf(x)
                );
            } catch (Exception e) { e.printStackTrace(); }
            return x;
        });
        
        functions.put("run", fileName -> { /* your run logic from before */ return 0; });

        functions.put("add", x -> { 
            todoList.add(String.valueOf(x)); 
            System.out.println("Added: " + x);
            return x; 
        });
        
        functions.put("list", x -> { 
            System.out.println("TODO List: " + todoList);
            return todoList.size(); 
        });
    }

    public void define(String name, int value) { variables.put(name, value); }
    
    public int get(String name) {
        if (!variables.containsKey(name)) throw new RuntimeException("Undefined: " + name);
        return variables.get(name);
    }

    public boolean isFunction(String name) { return functions.containsKey(name); }
    
    public int execute(String name, int input) {
        return functions.get(name).apply(input);
    }
    
    public void defineFunction(String name, List<Expression> body) {
    	System.out.println("Defining function: " + name);
        userFunctions.put(name, body);
    }

    public boolean isUserFunction(String name) {
        return userFunctions.containsKey(name);
    }

    public List<Expression> getUserFunction(String name) {
        return userFunctions.get(name);
    }
}