package language.ast;
import language.evaluator.Environment;
import java.util.List;

public record DefunNode(String name, List<Expression> body) implements Expression {
    public int evaluate(Environment env, int input) {
        env.defineFunction(name, body); 
        return 0; 
    }
}