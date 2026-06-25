package language.ast;
import language.evaluator.Environment;

public record Log(Expression target) implements Expression {
    public int evaluate(Environment env, int input) {
        // Pass the input along to the target
        int val = target.evaluate(env, input);
        System.out.println("LOG: " + val);
        return val;
    }
}