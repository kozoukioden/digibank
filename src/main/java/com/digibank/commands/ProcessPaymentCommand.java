package com.digibank.commands;

import com.digibank.banking.BankingService;
import com.digibank.banking.Transaction;
import com.digibank.banking.TransactionResult;

/**
 * Command to process a payment through Banking Service
 * Part of Command Pattern implementation
 * Integrates with Adapter Pattern (via BankingService)
 */
public class ProcessPaymentCommand implements Command {
    private BankingService bankingService;
    private Transaction transaction;
    private TransactionResult result;

    public ProcessPaymentCommand(BankingService bankingService, Transaction transaction) {
        this.bankingService = bankingService;
        this.transaction = transaction;
    }

    @Override
    public void execute() {
        System.out.println("[Payment Command] Executing payment: " + transaction.getDetails());
        result = bankingService.processTransaction(transaction);

        if (result.isSuccess()) {
            System.out.println("[Payment Command] ✓ Payment processed successfully");
        } else {
            System.out.println("[Payment Command] ✗ Payment failed: " + result.getErrorMessage());
        }
    }

    @Override
    public void undo() {
        if (result != null && result.isSuccess()) {
            System.out.println("[Payment Command] ⚠ Undoing payment (refund)...");
            System.out.println("[Payment Command] Refund would be processed here");
            // In real implementation, would create reverse transaction
        } else {
            System.out.println("[Payment Command] No successful payment to undo");
        }
    }

    @Override
    public String getDescription() {
        return "Process payment: " + transaction.getCurrency() + " " +
               transaction.getAmount() + " (Transaction #" + transaction.getTransactionId() + ")";
    }

    public TransactionResult getResult() {
        return result;
    }
}
