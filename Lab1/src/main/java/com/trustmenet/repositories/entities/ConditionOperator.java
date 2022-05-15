package com.trustmenet.repositories.entities;


public enum ConditionOperator {
    GREATER(">"),
    EQUALS("="),
    LESS("<");

    private final String operatorSymbol;

    ConditionOperator(String operatorSymbol) {
        this.operatorSymbol = operatorSymbol;
    }

    public String getOperatorSymbol() {
        return operatorSymbol;
    }

}
