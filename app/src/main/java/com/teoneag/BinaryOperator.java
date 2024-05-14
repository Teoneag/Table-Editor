package com.teoneag;

import java.util.List;
import java.util.function.BiFunction;

public enum BinaryOperator implements Computable {
    ADD("+", Double::sum),
    SUBTRACT("-", (a, b) -> a - b),
    MULTIPLY("*", (a, b) -> a * b),
    DIVIDE("/", (a, b) -> {
        if (b == 0) throw new ArithmeticException("Division by zero");
        return a / b;
    }),
    POWER("^", Math::pow);

    private final String symbol;
    private final BiFunction<Double, Double, Double> operation;

    BinaryOperator(String symbol, BiFunction<Double, Double, Double> operation) {
        this.symbol = symbol;
        this.operation = operation;
    }

    @Override
    public double compute(List<Double> args) {
        if (args.size() != 2) throw new IllegalArgumentException("Binary operation requires exactly two arguments.");
        return operation.apply(args.get(0), args.get(1));
    }

    @Override
    public String getSymbol() {
        return symbol;
    }
}
