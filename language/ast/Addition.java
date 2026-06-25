package language.ast;
import language.evaluator.Environment;

public record Addition(Expression left, Expression right) implements MathExpr {
    public int evaluate(Environment env, int input) {
        return left.evaluate(env, input) + right.evaluate(env, input);
    }
}