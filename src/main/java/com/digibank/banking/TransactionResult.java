package com.digibank.banking;

import java.time.LocalDateTime;

/**
 * Result of a transaction processing
 * Returned by Payment Processors
 */
public class TransactionResult {
    private boolean success;
    private String transactionId;
    private String errorMessage;
    private LocalDateTime timestamp;

    public TransactionResult(boolean success, String transactionId) {
        this.success = success;
        this.transactionId = transactionId;
        this.timestamp = LocalDateTime.now();
    }

    public TransactionResult(boolean success, String transactionId, String errorMessage) {
        this(success, transactionId);
        this.errorMessage = errorMessage;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        if (success) {
            return "TransactionResult{success=true, transactionId='" + transactionId + "'}";
        } else {
            return "TransactionResult{success=false, error='" + errorMessage + "'}";
        }
    }
}
