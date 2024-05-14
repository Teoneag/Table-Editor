package com.teoneag;

import java.util.List;
import java.util.function.Function;

public enum UnaryOperator implements Computable {
    NEGATE("-", a -> -a);

    private final String symbol;
    private final Function<Double, Double> operation;

    UnaryOperator(String symbol, Function<Double, Double> operation) {
        this.symbol = symbol;
        this.operation = operation;
    }

    @Override
    public double compute(List<Double> args) {
        if (args.size() != 1) throw new IllegalArgumentException("Unary operation requires exactly one argument.");
        return operation.apply(args.getFirst());
    }

    @Override
    public String getSymbol() {
        return symbol;
    }
}
