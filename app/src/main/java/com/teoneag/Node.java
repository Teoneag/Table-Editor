package com.teoneag;

import java.util.List;

public class Node {
    private Computable computable;
    private List<Node> children;

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

    public String toString() {
        return "Node: " + computable.getSymbol() + " " + children;
    }
}