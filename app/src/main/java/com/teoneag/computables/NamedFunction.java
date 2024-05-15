package com.teoneag.computables;

import java.util.List;
import java.util.function.Function;

public enum NamedFunction implements Computable {
    SQRT("sqrt", 1, args -> Math.sqrt(args.getFirst())),
    SUM("sum", 2, args -> args.stream().reduce(0.0, Double::sum)),
    POWER("pow", 2, args -> Math.pow(args.getFirst(), args.get(1))),
    LOG("log", 1, args -> Math.log(args.getFirst())),
    EXP("exp", 1, args -> Math.exp(args.getFirst())),
    SIN("sin", 1, args -> Math.sin(args.getFirst())),
    COS("cos", 1, args -> Math.cos(args.getFirst())),
    TAN("tan", 1, args -> Math.tan(args.getFirst()));

    private final String name;
    private final int argCount;
    private final Function<List<Double>, Double> operation;

    NamedFunction(String name, int argCount, Function<List<Double>, Double> operation) {
        this.name = name;
        this.argCount = argCount;
        this.operation = operation;
    }

    public static NamedFunction fromSymbol(String symbol) {
        for (NamedFunction function : values()) {
            if (function.name.equals(symbol)) return function;
        }
        throw new IllegalArgumentException("Unknown named function: " + symbol);
    }

    @Override
    public double compute(List<Double> args) {
        if (args.size() != argCount && !(this == SUM && args.size() >= argCount)) {
            throw new IllegalArgumentException(
                name + " requires " + argCount + "argument" + (argCount == 1 ? "." : "s."));
        }
        return operation.apply(args);
    }

    @Override
    public String getSymbol() {
        return name;
    }
}
