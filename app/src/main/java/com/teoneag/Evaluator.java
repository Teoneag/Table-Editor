package com.teoneag;

import com.teoneag.parser.Node;
import com.teoneag.parser.Parser;
import com.teoneag.tokenizer.Tokenizer;

import java.util.List;
import java.util.ArrayList;

public class Evaluator {
    public static String evaluate(String expression, List<List<String>> sheet) {
        return evaluate(Parser.parse(Tokenizer.tokenize(expression), sheet));
    }

    private static String evaluate(Node node) {
        return Double.toString(evaluate(node, new ArrayList<>()));
    }

    private static double evaluate(Node node, List<Double> args) {
        List<Double> childArgs = new ArrayList<>();
        for (Node child : node.getChildren()) {
            childArgs.add(evaluate(child, args));
        }
        return node.getComputable().compute(childArgs);
    }
}