package com.teoneag.parser;

import com.teoneag.computables.BinaryOperator;
import com.teoneag.computables.Computable;
import com.teoneag.computables.NamedFunction;
import com.teoneag.computables.UnaryOperator;
import com.teoneag.tokenizer.Token;
import com.teoneag.tokenizer.TokenType;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Parser {
    public static Node parse(List<Token> tokens, List<List<String>> sheet) {
        Stack<Node> values = new Stack<>();
        Stack<Token> ops = new Stack<>();

        for (Token token : tokens) {
            switch (token.getType()) {
                case NUMBER -> values.push(new NumberNode(token));
                case CELL_REFERENCE -> values.push(new NumberNode(getCellValue(token.getValue(), sheet)));
                case NAMED_FUNCTION, LEFT_PAREN -> ops.push(token);
                case COMMA -> {
                    while (!ops.isEmpty() && ops.peek().getType() != TokenType.LEFT_PAREN) {
                        values.push(applyOp(ops.pop(), values));
                    }
                }
                case RIGHT_PAREN -> {
                    while (ops.peek().getType() != TokenType.LEFT_PAREN) {
                        values.push(applyOp(ops.pop(), values));
                    }
                    ops.pop();
                    if (!ops.isEmpty() && ops.peek().getType() == TokenType.NAMED_FUNCTION) {
                        values.push(applyOp(ops.pop(), values));
                    }
                }
                case UNARY_OPERATOR, BINARY_OPERATOR -> {
                    while (!ops.isEmpty() && hasPrecedence(token, ops.peek())) {
                        values.push(applyOp(ops.pop(), values));
                    }
                    ops.push(token);
                }
            }
        }

        while (!ops.isEmpty()) {
            values.push(applyOp(ops.pop(), values));
        }

        System.out.println("Parsed expression: " + values.peek());
        return values.pop();
    }

    private static Node applyOp(Token token, Stack<Node> values) {
        Computable computable;
        if (token.getType() == TokenType.UNARY_OPERATOR) {
            computable = UnaryOperator.fromSymbol(token.getValue());
            Node value = values.pop();
            return new Node(computable, value);
        } else if (token.getType() == TokenType.BINARY_OPERATOR) {
            computable = BinaryOperator.fromSymbol(token.getValue());
            Node right = values.pop();
            Node left = values.pop();
            return new Node(computable, left, right);
        } else if (token.getType() == TokenType.NAMED_FUNCTION) {
            computable = NamedFunction.fromSymbol(token.getValue());
            List<Node> args = new ArrayList<>();
            while (!values.isEmpty() && values.peek().getComputable() != computable) {
                args.addFirst(values.pop());
            }
            return new Node(computable, args);
        } else {
            throw new IllegalArgumentException("Invalid operator token: " + token);
        }
    }

    private static boolean hasPrecedence(Token op1, Token op2) {
        if (op2.getType() == TokenType.LEFT_PAREN || op2.getType() == TokenType.RIGHT_PAREN) {
            return false;
        }
        if (op1.getType() == TokenType.BINARY_OPERATOR && op2.getType() == TokenType.BINARY_OPERATOR) {
            BinaryOperator op1Operator = BinaryOperator.fromSymbol(op1.getValue());
            BinaryOperator op2Operator = BinaryOperator.fromSymbol(op2.getValue());
            return op1Operator.getPrecedence() <= op2Operator.getPrecedence();
        }
        return false;
    }

    private static double getCellValue(String cellReference, List<List<String>> sheet) {
        // AB37 -> (37, 28)
        String letters = cellReference.replaceAll("\\d", "");
        String numbers = cellReference.replaceAll("\\D", "");

        int row = Integer.parseInt(numbers) - 1;
        int column = 0;
        for (int i = 0; i < letters.length(); i++) {
            column = column * 26 + letters.charAt(i) - 'A' + 1;
        }
        column--;
        return Double.parseDouble(sheet.get(row).get(column));
    }
}



