package com.digibank.models;

/**
 * Status of banking transactions
 */
public enum TransactionStatus {
    PENDING("Transaction is being processed"),
    COMPLETED("Transaction completed successfully"),
    FAILED("Transaction failed");

    private final String description;

    TransactionStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
