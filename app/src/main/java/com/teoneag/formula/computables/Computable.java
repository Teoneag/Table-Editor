package com.teoneag.formula.computables;

import java.util.List;

public interface Computable {
    double compute(List<Double> args);
    String getSymbol();
}
