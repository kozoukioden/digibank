package com.digibank.banking.adapters;

import com.digibank.banking.Transaction;
import com.digibank.banking.TransactionResult;
import com.digibank.models.TransactionStatus;

/**
 * Bitcoin Adapter - adapts Bitcoin API to PaymentProcessor interface
 * Part of Adapter Pattern implementation
 */
public class BitcoinAdapter extends CryptoAdapter {

    public BitcoinAdapter() {
        super("BTC");
    }

    @Override
    public TransactionResult process(Transaction txn) {
        System.out.println("[Bitcoin Adapter] Processing Bitcoin transaction #" + txn.getTransactionId());

        if (!validate(txn)) {
            return new TransactionResult(false, "BTC-TXN-" + txn.getTransactionId(),
                                        "Bitcoin validation failed");
        }

        // Convert to Bitcoin transaction format
        String btcTransaction = convertToBitcoinTransaction(txn);
        System.out.println("[Bitcoin Adapter] Bitcoin transaction: " + btcTransaction);

        // Simulate Bitcoin network processing
        System.out.println("[Bitcoin Adapter] Broadcasting to Bitcoin network...");
        System.out.println("[Bitcoin Adapter] Waiting for blockchain confirmation...");

        // Simulate blockchain confirmation
        String blockchainResponse = simulateBitcoinAPI(btcTransaction);
        System.out.println("[Bitcoin Adapter] Blockchain response: " + blockchainResponse);

        // Update transaction status
        txn.setStatus(TransactionStatus.COMPLETED);
        System.out.println("[Bitcoin Adapter] ✓ Bitcoin transaction confirmed");

        return convertToTransactionResult(blockchainResponse, txn);
    }

    /**
     * Convert DigiBank transaction to Bitcoin-specific format
     */
    private String convertToBitcoinTransaction(Transaction txn) {
        // Simulating Bitcoin transaction structure
        return String.format("BTC_TX{wallet_from: 'W%d', wallet_to: 'W%d', amount_btc: %.8f}",
                txn.getFromAccount(), txn.getToAccount(), txn.getAmount().doubleValue());
    }

    /**
     * Simulate Bitcoin API call
     */
    private String simulateBitcoinAPI(String btcTransaction) {
        // Simulate Bitcoin network response
        String txHash = "0x" + Long.toHexString(System.currentTimeMillis());
        return "SUCCESS - Transaction Hash: " + txHash + " - Confirmations: 6";
    }
}
