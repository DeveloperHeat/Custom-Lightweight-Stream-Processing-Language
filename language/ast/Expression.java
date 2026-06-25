package language.ast;
import language.evaluator.Environment;

public sealed interface Expression permits MathExpr, VariableExpr, KeywordExpr, Literal, Assignment, Log, DefunNode, IfNode, CommandNode {
	int evaluate(Environment env, int input);
}