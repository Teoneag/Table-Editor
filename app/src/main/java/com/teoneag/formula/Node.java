package com.teoneag.formula;

import com.teoneag.formula.computables.Computable;

import java.util.List;

class Node {
    private final Computable computable;
    private final List<Node> children;

    public Node(Computable computable, Node... children) {
        this(computable, List.of(children));
    }

    public Node(Computable computable, List<Node> children) {
        this.computable = computable;
        this.children = children;
    }

    public Computable getComputable() {
        return computable;
    }

    public List<Node> getChildren() {
        return children;
    }

    static class NumberNode extends Node {
        public NumberNode(double value) {
            super(new NumberComputable(value), List.of());
        }
    }

    static class NumberComputable implements Computable {
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
}