package com.teoneag.formula.computables;

import java.util.List;
import java.util.function.BiFunction;

public enum BinaryOperator implements Computable {
    ADD("+", 1, Double::sum),
    SUBTRACT("-", 1, (a, b) -> a - b),
    MULTIPLY("*", 2, (a, b) -> a * b),
    DIVIDE("/", 2, (a, b) -> {
        if (b == 0) throw new ArithmeticException("Division by zero");
        return a / b;
    }),
    POWER("^", 3, Math::pow);

    private final String symbol;
    private final int precedence;
    private final BiFunction<Double, Double, Double> operation;

    BinaryOperator(String symbol, int precedence, BiFunction<Double, Double, Double> operation) {
        this.symbol = symbol;
        this.precedence = precedence;
        this.operation = operation;
    }

    public static BinaryOperator fromSymbol(String symbol) {
        for (BinaryOperator operator : values()) {
            if (operator.getSymbol().equals(symbol)) return operator;
        }
        throw new IllegalArgumentException("Unknown binary operator: " + symbol);
    }

    @Override
    public double compute(List<Double> args) {
        if (args.size() != 2) throw new IllegalArgumentException("Binary operation requires exactly 2 arguments.");
        return operation.apply(args.get(0), args.get(1));
    }

    @Override
    public String getSymbol() {
        return symbol;
    }

    public int getPrecedence() {
        return precedence;
    }
}
