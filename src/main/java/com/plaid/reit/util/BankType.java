package com.plaid.reit.util;

public enum BankType {
    CHECKING("checking"),
    SAVING("saving");

    private String value;

    BankType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
