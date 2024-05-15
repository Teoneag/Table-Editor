package com.teoneag.computables;

import java.util.List;
import java.util.function.Function;

public enum NamedFunction implements Computable {
    SQRT("sqrt", args -> {
        if (args.size() != 1) throw new IllegalArgumentException("sqrt requires exactly one argument");
        return Math.sqrt(args.getFirst());
    }),
    SUM("sum", args -> {
        if (args.size() < 2) throw new IllegalArgumentException("sum requires at least two arguments");
        return args.stream().reduce(0.0, Double::sum);
    });

    private final String name;
    private final Function<List<Double>, Double> operation;

    NamedFunction(String name, Function<List<Double>, Double> operation) {
        this.name = name;
        this.operation = operation;
    }

    public static NamedFunction fromSymbol(String symbol) {
        for (NamedFunction function : values()) {
            if (function.getSymbol().equals(symbol)) {
                return function;
            }
        }
        throw new IllegalArgumentException("Unknown named function: " + symbol);
    }

    @Override
    public double compute(List<Double> args) {
        return operation.apply(args);
    }

    @Override
    public String getSymbol() {
        return name;
    }
}
