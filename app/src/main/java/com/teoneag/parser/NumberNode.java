package com.teoneag.parser;

import com.teoneag.computables.Computable;
import com.teoneag.tokenizer.Token;

import java.util.List;

public class NumberNode extends Node {
    public NumberNode(Token token) {
        super(new NumberComputable(Double.parseDouble(token.getValue())), List.of());
    }

    public NumberNode(double value) {
        super(new NumberComputable(value), List.of());
    }
}

class NumberComputable implements Computable {
    private final double value;

    public NumberComputable(double value) {
        this.value = value;
    }

    @Override
    public double compute(List<Double> args) {
        return value;
    }

    @Override
    public String getSymbol() {
        return Double.toString(value);
    }
}
