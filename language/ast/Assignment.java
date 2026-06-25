package language.ast;
import language.evaluator.Environment;

public record Assignment(String name, Expression value) implements Expression {
    public int evaluate(Environment env, int input) {
        int val = value.evaluate(env, input);
        env.define(name, val); // Assuming your env has a define method
        return val;
    }
}