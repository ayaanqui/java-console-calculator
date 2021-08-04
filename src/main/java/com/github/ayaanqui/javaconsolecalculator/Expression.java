package com.github.ayaanqui.javaconsolecalculator;

public class Expression {
    public String expression;
    public double result;
    public boolean success;
    public String[] errors = new String[0];

    public Expression(String expression, double result) {
        this.expression = expression;
        this.result = result;
        this.success = true;
    }

    public Expression(String expression, double result, String... errors) {
        this.expression = expression;
        this.result = result;
        this.errors = errors;
        this.success = false;
    }
}
