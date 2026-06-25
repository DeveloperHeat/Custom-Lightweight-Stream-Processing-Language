package language.ast;
import language.evaluator.Environment;

public record CommandNode(String commandName, Expression argument) implements Expression {
    public int evaluate(Environment env, int input) {
        int argValue = (argument != null) ? argument.evaluate(env, input) : 0;
        return switch (commandName) {
            case "plus"   -> input + argValue;
            case "double" -> input * 2;
            case "log"    -> { System.out.println("LOG: " + input); yield input; }
            default -> throw new RuntimeException("Unknown command: " + commandName);
        };
    }
}