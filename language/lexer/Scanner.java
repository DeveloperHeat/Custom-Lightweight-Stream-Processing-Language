package language.lexer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Scanner {
    private final String source;
    private final List<Token> tokens = new ArrayList<>();
    private int start = 0;
    private int current = 0;
    private static final Map<String, TokenType> KEYWORDS = Map.of(
        "def", TokenType.DEF,
        "defun", TokenType.DEFUN,
        "if", TokenType.IF,
        "else", TokenType.ELSE
    );
    
    public Scanner(String source) {
        this.source = source;
    }
    
    public List<Token> scanTokens() {
        while (current < source.length()) {
            start = current;
            scanToken();
        }
        tokens.add(new Token(TokenType.EOF, ""));
        return tokens;
    }
    
    private void scanToken() {
        char c = advance();
        if (Character.isWhitespace(c)) return;
        
        switch(c) {
            case '|' -> {
                if (match('>')) tokens.add(new Token(TokenType.PIPE, "|>"));
            }
            case '+' -> addToken(TokenType.PLUS); 
            case '{' -> addToken(TokenType.LEFT_BRACE);
            case '}' -> addToken(TokenType.RIGHT_BRACE);
            case '#' -> { 
                while (peek() != '\n' && peek() != '\0') advance();
            }
            
            case '-' -> { 
                
                if (Character.isDigit(peek())) {
                    number(); 
                } else {
                    throw new RuntimeException("Unexpected character: -");
                }
            }
            default -> {
                if (Character.isDigit(c)) number();
                else if (Character.isLetter(c)) identifier();
                else throw new RuntimeException("Unexpected character: " + c);
            }
        }
    }

    private void addToken(TokenType type) {
        tokens.add(new Token(type, source.substring(start, current)));
    }

    private void identifier() {
        while (Character.isLetterOrDigit(peek())) advance();
        String text = source.substring(start, current);
        tokens.add(new Token(KEYWORDS.getOrDefault(text, TokenType.IDENTIFIER), text));
    }

    private void number() {
        while (Character.isDigit(peek())) advance();
        tokens.add(new Token(TokenType.NUMBER, source.substring(start, current)));
    }
        
    private char peek() {
        if (current >= source.length()) return '\0';
        return source.charAt(current);
    }

    private char advance() {
        return source.charAt(current++);
    }

    private boolean match(char expected) {
        if (current >= source.length() || source.charAt(current) != expected) return false;
        current++;
        return true;
    }
}