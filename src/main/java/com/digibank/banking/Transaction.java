package com.digibank.banking;

import com.digibank.models.TransactionType;
import com.digibank.models.TransactionStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Transaction model for banking operations
 * Used by Banking Service and Payment Processors
 */
public class Transaction {
    private int transactionId;
    private int fromAccount;
    private int toAccount;
    private BigDecimal amount;
    private String currency;
    private TransactionType transactionType;
    private TransactionStatus status;
    private LocalDateTime timestamp;

    public Transaction(int fromAccount, int toAccount, BigDecimal amount, String currency, TransactionType type) {
        this.transactionId = generateTransactionId();
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
        this.currency = currency;
        this.transactionType = type;
        this.status = TransactionStatus.PENDING;
        this.timestamp = LocalDateTime.now();
    }

    private int generateTransactionId() {
        return (int) (System.currentTimeMillis() % 1000000);
    }

    /**
     * Validate transaction parameters
     * @return true if valid, false otherwise
     */
    public boolean validate() {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("[Transaction] Validation failed: Invalid amount");
            return false;
        }

        if (currency == null || currency.isEmpty()) {
            System.out.println("[Transaction] Validation failed: Invalid currency");
            return false;
        }

        if (fromAccount == toAccount) {
            System.out.println("[Transaction] Validation failed: Same account transfer");
            return false;
        }

        return true;
    }

    public String getDetails() {
        return String.format("Transaction #%d: %s %.2f from Account %d to Account %d (%s)",
                transactionId, currency, amount, fromAccount, toAccount, status);
    }

    // Getters and Setters
    public int getTransactionId() {
        return transactionId;
    }

    public int getFromAccount() {
        return fromAccount;
    }

    public int getToAccount() {
        return toAccount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return getDetails();
    }
}
