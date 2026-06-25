package language.parser;

import language.ast.*;
import language.lexer.*;
import java.util.*;
import java.util.function.Supplier;

public class Parser {
    private final List<Token> tokens;
    private int current = 0;
    private final Map<TokenType, Supplier<Expression>> dispatchTable = new EnumMap<>(TokenType.class);

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        dispatchTable.put(TokenType.NUMBER, this::parseLiteral);
        dispatchTable.put(TokenType.IDENTIFIER, this::parseVariable);
    }

    public List<Expression> parse() {
        List<Expression> statements = new ArrayList<>();
        while (!isAtEnd()) {
            if (check(TokenType.EOF)) break;
            if (match(TokenType.DEFUN)) {
                statements.add(parseDefun());
            } else {
                statements.add(parsePipe());
            }
        }
        return statements;
    }

    private Expression parseDefun() {
        Token name = consume(TokenType.IDENTIFIER, "Expect function name.");
        consume(TokenType.LEFT_BRACE, "Expect '{' before function body.");
        List<Expression> body = new ArrayList<>();
        while (!check(TokenType.RIGHT_BRACE) && !isAtEnd()) {
            body.add(parsePipe());
        }
        consume(TokenType.RIGHT_BRACE, "Expect '}' after function body.");
        return new DefunNode(name.lexeme(), body);
    }

    private Expression parsePipe() {
        Expression expr = parsePrimary();
        
        // Only continue if the NEXT token is an operator.
        // IF IT IS A NUMBER or IDENTIFIER, it's a new statement! STOP!
        while (check(TokenType.PIPE) || check(TokenType.PLUS)) {
            Token operator = advance(); 
            Expression right = parsePrimary();
            
            if (operator.type() == TokenType.PIPE) {
                expr = new Pipe(expr, right);
            } else if (operator.type() == TokenType.PLUS) {
                expr = new Addition(expr, right);
            }
        }
        return expr;
    }

    private Expression parsePrimary() {
        if (match(TokenType.IF)) {
            List<Expression> thenBranch = parseBlock();
            List<Expression> elseBranch = match(TokenType.ELSE) ? parseBlock() : null;
            return new IfNode(thenBranch, elseBranch);
        }
        if (check(TokenType.NUMBER)) {
            Token numToken = advance();
            Expression val = new Literal(Integer.parseInt(numToken.lexeme()));
            if (check(TokenType.DEF)) {
                advance(); 
                Token name = consume(TokenType.IDENTIFIER, "Expect variable name.");
                return new Assignment(name.lexeme(), val);
            }
            return val;
        }
        Token token = advance();
        Supplier<Expression> handler = dispatchTable.get(token.type());
        return (handler != null) ? handler.get() : null;
    }

    private Expression parseVariable() {
        Token token = previous();
        String name = token.lexeme();
        // Detect built-ins
        if (name.equals("plus") || name.equals("double") || name.equals("log")) {
            // Check if there is an argument following (like 'plus 10')
            if (!isAtEnd() && (check(TokenType.NUMBER) || check(TokenType.IDENTIFIER))) {
                return new CommandNode(name, parsePrimary());
            }
            return new CommandNode(name, null);
        }
        return new Variable(name);
    }

    private List<Expression> parseBlock() {
        consume(TokenType.LEFT_BRACE, "Expect '{'");
        List<Expression> block = new ArrayList<>();
        while (!check(TokenType.RIGHT_BRACE) && !isAtEnd()) block.add(parsePipe());
        consume(TokenType.RIGHT_BRACE, "Expect '}'");
        return block;
    }

    private Expression parseLiteral() { return new Literal(Integer.parseInt(previous().lexeme())); }
    private boolean match(TokenType type) { if (check(type)) { advance(); return true; } return false; }
    private Token consume(TokenType type, String message) { if (check(type)) return advance(); throw new RuntimeException(message); }
    private boolean check(TokenType type) { return peek().type() == type; }
    private Token advance() { if (!isAtEnd()) current++; return previous(); }
    private boolean isAtEnd() { return peek().type() == TokenType.EOF; }
    private Token peek() { return tokens.get(current); }
    private Token previous() { return tokens.get(current - 1); }
}