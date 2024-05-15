package com.teoneag.tokenizer;

import com.teoneag.computables.BinaryOperator;
import com.teoneag.computables.Computable;
import com.teoneag.computables.NamedFunction;
import com.teoneag.computables.UnaryOperator;

import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public enum TokenType {
    NUMBER("[0-9]+(\\.[0-9]+)?"),
    CELL_REFERENCE("[A-Z]+[0-9]+"),
    UNARY_OPERATOR(getRegex(UnaryOperator.class)),
    BINARY_OPERATOR(getRegex(BinaryOperator.class)),
    NAMED_FUNCTION(getRegex(NamedFunction.class)),
    COMMA(","),
    LEFT_PAREN("\\("),
    RIGHT_PAREN("\\)");

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
