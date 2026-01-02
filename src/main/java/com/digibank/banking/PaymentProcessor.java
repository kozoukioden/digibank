package com.digibank.banking;

/**
 * Payment Processor interface for Adapter Pattern
 * Provides unified interface for Fiat and Cryptocurrency payments
 */
public interface PaymentProcessor {
    /**
     * Process a transaction
     * @param txn Transaction to process
     * @return Transaction result
     */
    TransactionResult process(Transaction txn);

    /**
     * Validate a transaction before processing
     * @param txn Transaction to validate
     * @return true if valid, false otherwise
     */
    boolean validate(Transaction txn);

    /**
     * Get currency handled by this processor
     * @return Currency code
     */
    String getCurrency();
}
