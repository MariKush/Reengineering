package com.trustmenet.repositories.dto;


public enum ConditionOperatorDto {
    GREATER(">"),
    EQUALS("="),
    LESS("<");

    private final String operatorSymbol;

    ConditionOperatorDto(String operatorSymbol) {
        this.operatorSymbol = operatorSymbol;
    }

    public String getOperatorSymbol() {
        return operatorSymbol;
    }

}
