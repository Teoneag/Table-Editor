// Inspired from https://www.geeksforgeeks.org/expression-evaluation/

package com.teoneag.formula;

import com.teoneag.table.FormulaTableModel;
import com.teoneag.formula.computables.BinaryOperator;
import com.teoneag.formula.computables.Computable;
import com.teoneag.formula.computables.NamedFunction;
import com.teoneag.formula.computables.UnaryOperator;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

class Parser {
    public static Node parse(List<Token> tokens, FormulaTableModel model) {
        Stack<Node> values = new Stack<>();
        Stack<Token> ops = new Stack<>();

        for (Token token : tokens) {
            switch (token.type()) {
                case NUMBER -> values.push(new Node.NumberNode(Double.parseDouble(token.value())));
                case CELL_REFERENCE -> values.push(new Node.NumberNode(model.getCellValue(token.value())));
                case NAMED_FUNCTION, LEFT_PAREN -> ops.push(token);
                case COMMA -> {
                    while (!ops.isEmpty() && ops.peek().type() != TokenType.LEFT_PAREN) {
                        values.push(applyOp(ops.pop(), values));
                    }
                }
                case RIGHT_PAREN -> {
                    while (ops.peek().type() != TokenType.LEFT_PAREN) {
                        values.push(applyOp(ops.pop(), values));
                    }
                    ops.pop();
                    if (!ops.isEmpty() && ops.peek().type() == TokenType.NAMED_FUNCTION) {
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

        while (!ops.isEmpty()) values.push(applyOp(ops.pop(), values));

        if (values.size() != 1) throw new IllegalArgumentException("Expression is empty.");

        return values.pop();
    }

    private static Node applyOp(Token token, Stack<Node> values) {
        Computable computable;
        if (token.type() == TokenType.UNARY_OPERATOR) {
            computable = UnaryOperator.fromSymbol(token.value());
            Node value = values.pop();
            return new Node(computable, value);
        } else if (token.type() == TokenType.BINARY_OPERATOR) {
            computable = BinaryOperator.fromSymbol(token.value());
            Node right = values.pop();
            Node left = values.pop();
            return new Node(computable, left, right);
        } else if (token.type() == TokenType.NAMED_FUNCTION) {
            computable = NamedFunction.fromSymbol(token.value());
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
        if (op2.type() == TokenType.LEFT_PAREN || op2.type() == TokenType.RIGHT_PAREN) {
            return false;
        }
        if (op1.type() == TokenType.BINARY_OPERATOR && op2.type() == TokenType.BINARY_OPERATOR) {
            BinaryOperator op1Operator = BinaryOperator.fromSymbol(op1.value());
            BinaryOperator op2Operator = BinaryOperator.fromSymbol(op2.value());
            return op1Operator.getPrecedence() <= op2Operator.getPrecedence();
        }
        return false;
    }
}




