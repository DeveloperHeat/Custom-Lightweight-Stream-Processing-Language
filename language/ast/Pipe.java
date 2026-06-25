package language.ast;
import language.evaluator.Environment;

public record Pipe(Expression left, Expression right) implements KeywordExpr {
    public int evaluate(Environment env, int input) {
        int leftResult = left.evaluate(env, input);
        return right.evaluate(env, leftResult);
    }
}