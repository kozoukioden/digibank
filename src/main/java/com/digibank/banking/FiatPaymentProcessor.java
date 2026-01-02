package com.digibank.banking;

import com.digibank.models.TransactionStatus;

/**
 * Fiat Payment Processor - handles traditional currency payments
 * Part of Adapter Pattern (no actual adaptation needed for fiat)
 */
public class FiatPaymentProcessor implements PaymentProcessor {
    private String currency;

    public FiatPaymentProcessor(String currency) {
        this.currency = currency; // USD, EUR, TRY, etc.
    }

    @Override
    public TransactionResult process(Transaction txn) {
        System.out.println("[Fiat Processor] Processing " + currency + " transaction #" + txn.getTransactionId());

        if (!validate(txn)) {
            return new TransactionResult(false, "TXN-" + txn.getTransactionId(), "Validation failed");
        }

        // Simulate bank API processing
        System.out.println("[Fiat Processor] Contacting bank API...");
        System.out.println("[Fiat Processor] Debiting " + txn.getAmount() + " " + currency +
                          " from account " + txn.getFromAccount());
        System.out.println("[Fiat Processor] Crediting " + txn.getAmount() + " " + currency +
                          " to account " + txn.getToAccount());

        // Simulate successful processing
        txn.setStatus(TransactionStatus.COMPLETED);
        System.out.println("[Fiat Processor] ✓ Transaction completed successfully");

        return new TransactionResult(true, "TXN-" + txn.getTransactionId());
    }

    @Override
    public boolean validate(Transaction txn) {
        if (!txn.validate()) {
            return false;
        }

        if (!txn.getCurrency().equals(currency)) {
            System.out.println("[Fiat Processor] Currency mismatch: expected " + currency +
                              " but got " + txn.getCurrency());
            return false;
        }

        return true;
    }

    @Override
    public String getCurrency() {
        return currency;
    }
}
