package language.ast;
import language.evaluator.Environment;

public record Variable(String name) implements VariableExpr {
	public int evaluate(Environment env, int input) {
	    if (env.isFunction(name)) {
	        return env.execute(name, input);
	    }
	    else if (env.isUserFunction(name)) {
	        int result = input;
	        for (Expression expr : env.getUserFunction(name)) {
	            result = expr.evaluate(env, result);
	        }
	        return result;
	    }
	    return env.get(name);
	}
}