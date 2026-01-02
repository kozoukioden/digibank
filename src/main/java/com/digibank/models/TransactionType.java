package com.digibank.models;

/**
 * Types of banking transactions
 */
public enum TransactionType {
    PAYMENT("Payment for city services"),
    TRANSFER("Transfer between accounts"),
    DEPOSIT("Deposit to account"),
    WITHDRAWAL("Withdrawal from account");

    private final String description;

    TransactionType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
