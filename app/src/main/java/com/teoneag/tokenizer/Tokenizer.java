package com.teoneag.tokenizer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class Tokenizer {
    public static List<Token> tokenize(String input) {
        input = input.replaceAll("\\s+", "");
        List<Token> tokens = new ArrayList<>();
        int position = 0;

        while (position < input.length()) {
            boolean matched = false;

            for (TokenType type : TokenType.values()) {
                Matcher matcher = type.getPattern().matcher(input);
                matcher.region(position, input.length());

                if (matcher.lookingAt()) {
                    String tokenValue = input.substring(position, matcher.end());
                    if (tokenValue.equals("-")) {
                        if (tokens.isEmpty() || isPreviousTokenOperatorOrLeftParen(tokens)) {
                            tokens.add(new Token(TokenType.UNARY_OPERATOR, tokenValue));
                        } else {
                            tokens.add(new Token(TokenType.BINARY_OPERATOR, tokenValue));
                        }
                    } else {
                        tokens.add(new Token(type, tokenValue));
                    }
                    position = matcher.end();
                    matched = true;
                    break;
                }
            }

            if (!matched) {
                throw new IllegalArgumentException("Unknown token: " + input.substring(position));
            }
        }
        return tokens;
    }

    private static boolean isPreviousTokenOperatorOrLeftParen(List<Token> tokens) {
        TokenType type = tokens.getLast().getType();
        return type == TokenType.BINARY_OPERATOR || type == TokenType.UNARY_OPERATOR || type == TokenType.LEFT_PAREN;
    }
}
