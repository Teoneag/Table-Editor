package com.teoneag.tokenizer;

import com.teoneag.BinaryOperator;
import com.teoneag.Computable;
import com.teoneag.NamedFunction;
import com.teoneag.UnaryOperator;

import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

enum TokenType {
    NUMBER("[0-9]+(\\.[0-9]+)?"),
    CELL_REFERENCE("[A-Z]+[0-9]+"),
    OPERATOR(String.join(getRegex(UnaryOperator.class), getRegex(BinaryOperator.class))),
    NAMED_FUNCTION(getRegex(NamedFunction.class)),
    COMMA(","),
    LEFT_PARENTHESIS("\\("),
    RIGHT_PARENTHESIS("\\)");

    private final Pattern pattern;

    TokenType(String regex) {
        this.pattern = Pattern.compile("^" + regex);
    }

    public Pattern getPattern() {
        return pattern;
    }

    public static <E extends Enum<E> & Computable> String getRegex(Class<E> enumClass) {
        return Arrays.stream(enumClass.getEnumConstants())
            .map(e -> escapeRegex(e.getSymbol()))
            .collect(Collectors.joining("|"));
    }

    private static String escapeRegex(String symbol) {
        return symbol.replaceAll("([\\Q\\*+?[](){}|.^$-\\E])", "\\\\$1");
    }
}
