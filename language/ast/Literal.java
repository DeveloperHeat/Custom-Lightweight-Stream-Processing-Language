package language.ast;
import language.evaluator.Environment;

public record Literal(int value) implements Expression {
    public int evaluate(Environment env, int input) {
        return value;
    }
}