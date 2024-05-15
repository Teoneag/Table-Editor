package com.teoneag.tokenizer;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class TokenizerTest {

    @Test
    public void testTokenizeExample() {
        String input = "sum(-2, A3) * 42 - -69";

        List<Token> expectedTokens = List.of(
            new Token(TokenType.NAMED_FUNCTION, "sum"),
            new Token(TokenType.LEFT_PAREN, "("),
            new Token(TokenType.UNARY_OPERATOR, "-"),
            new Token(TokenType.NUMBER, "2"),
            new Token(TokenType.COMMA, ","),
            new Token(TokenType.CELL_REFERENCE, "A3"),
            new Token(TokenType.RIGHT_PAREN, ")"),
            new Token(TokenType.BINARY_OPERATOR, "*"),
            new Token(TokenType.NUMBER, "42"),
            new Token(TokenType.BINARY_OPERATOR, "-"),
            new Token(TokenType.UNARY_OPERATOR, "-"),
            new Token(TokenType.NUMBER, "69")
        );

        List<Token> actualTokens = Tokenizer.tokenize(input);

        assertEquals(expectedTokens, actualTokens);
    }

}