package com.digibank.banking.adapters;

import com.digibank.banking.PaymentProcessor;
import com.digibank.banking.Transaction;
import com.digibank.banking.TransactionResult;

/**
 * Abstract Crypto Adapter - base class for cryptocurrency adapters
 * Part of Adapter Pattern - adapts different crypto APIs to unified interface
 */
public abstract class CryptoAdapter implements PaymentProcessor {
    protected String cryptoCurrency;

    public CryptoAdapter(String cryptoCurrency) {
        this.cryptoCurrency = cryptoCurrency;
    }

    /**
     * Convert DigiBank transaction to crypto-specific transaction
     * @param txn DigiBank transaction
     * @return Crypto-specific transaction object (simulated)
     */
    protected String convertToCryptoTransaction(Transaction txn) {
        return String.format("{from: '%d', to: '%d', amount: %.8f, currency: '%s'}",
                txn.getFromAccount(), txn.getToAccount(),
                txn.getAmount().doubleValue(), cryptoCurrency);
    }

    /**
     * Convert crypto API response to TransactionResult
     * @param cryptoResponse Response from crypto API (simulated)
     * @param txn Original transaction
     * @return Transaction result
     */
    protected TransactionResult convertToTransactionResult(String cryptoResponse, Transaction txn) {
        // Simulate parsing crypto API response
        if (cryptoResponse.contains("SUCCESS")) {
            return new TransactionResult(true, "CRYPTO-TXN-" + txn.getTransactionId());
        } else {
            return new TransactionResult(false, "CRYPTO-TXN-" + txn.getTransactionId(),
                                        "Crypto transaction failed");
        }
    }

    @Override
    public boolean validate(Transaction txn) {
        if (!txn.validate()) {
            return false;
        }

        if (!txn.getCurrency().equalsIgnoreCase(cryptoCurrency)) {
            System.out.println("[Crypto Adapter] Currency mismatch: expected " + cryptoCurrency +
                              " but got " + txn.getCurrency());
            return false;
        }

        return true;
    }

    @Override
    public String getCurrency() {
        return cryptoCurrency;
    }

    /**
     * Abstract method to process crypto transaction
     * Each cryptocurrency has its own implementation
     */
    @Override
    public abstract TransactionResult process(Transaction txn);
}
