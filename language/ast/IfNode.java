package language.ast;
import language.evaluator.Environment;
import java.util.List;

public record IfNode(List<Expression> thenBranch, List<Expression> elseBranch) implements Expression {
    public int evaluate(Environment env, int input) {
        if (input > 0) {
            int result = input;
            for(Expression e : thenBranch) result = e.evaluate(env, result);
            return result;
        } else if (elseBranch != null) {
            int result = input;
            for(Expression e : elseBranch) result = e.evaluate(env, result);
            return result;
        }
        return 0; 
    }
}