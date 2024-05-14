package com.teoneag.tokenizer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
                    tokens.add(new Token(type, tokenValue));
                    position = matcher.end();
                    matched = true;
                    break;
                }
            }

            if (!matched) {
                throw new IllegalArgumentException("Invalid input at position: " + position);
            }
        }

        return tokens;
    }
}
