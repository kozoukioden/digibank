package com.digibank.banking;

import com.digibank.banking.adapters.CryptoAdapterFactory;
import com.digibank.observers.Event;
import com.digibank.observers.Observer;
import com.digibank.observers.Subject;
import com.digibank.models.EventType;
import com.digibank.models.Severity;
import com.digibank.models.TransactionStatus;

import java.util.*;

/**
 * Banking Service - handles all banking operations
 * Integrates with Observer Pattern for transaction notifications
 * Uses Adapter Pattern for multiple payment processors
 */
public class BankingService implements Subject {
    private List<Observer> observers;
    private Map<String, PaymentProcessor> paymentProcessors;

    public BankingService() {
        this.observers = new ArrayList<>();
        this.paymentProcessors = new HashMap<>();

        // Initialize payment processors
        paymentProcessors.put("USD", CryptoAdapterFactory.getAdapterByCurrency("USD"));
        paymentProcessors.put("BTC", CryptoAdapterFactory.getAdapterByCurrency("BTC"));
        paymentProcessors.put("ETH", CryptoAdapterFactory.getAdapterByCurrency("ETH"));

        System.out.println("[Banking Service] Initialized with " +
                          paymentProcessors.size() + " payment processors");
    }

    /**
     * Process a transaction
     * @param txn Transaction to process
     * @return Transaction result
     */
    public TransactionResult processTransaction(Transaction txn) {
        System.out.println("\n[Banking Service] Processing transaction: " + txn.getDetails());

        // Step 1: Validate transaction
        if (!validateTransaction(txn)) {
            txn.setStatus(TransactionStatus.FAILED);
            return new TransactionResult(false, "TXN-" + txn.getTransactionId(),
                                        "Transaction validation failed");
        }

        // Step 2: Select payment processor based on currency
        PaymentProcessor processor = selectPaymentProcessor(txn.getCurrency());
        if (processor == null) {
            txn.setStatus(TransactionStatus.FAILED);
            return new TransactionResult(false, "TXN-" + txn.getTransactionId(),
                                        "Unsupported currency: " + txn.getCurrency());
        }

        // Step 3: Process payment through adapter
        TransactionResult result = processor.process(txn);

        // Step 4: Notify observers if successful
        if (result.isSuccess()) {
            txn.setStatus(TransactionStatus.COMPLETED);
            notifyTransactionComplete(txn);
        } else {
            txn.setStatus(TransactionStatus.FAILED);
        }

        System.out.println("[Banking Service] Transaction result: " + result);
        return result;
    }

    /**
     * Validate transaction before processing
     */
    private boolean validateTransaction(Transaction txn) {
        System.out.println("[Banking Service] Validating transaction...");

        if (!txn.validate()) {
            return false;
        }

        // Additional business logic validation
        System.out.println("[Banking Service] ✓ Transaction validated");
        return true;
    }

    /**
     * Select appropriate payment processor for currency
     */
    private PaymentProcessor selectPaymentProcessor(String currency) {
        System.out.println("[Banking Service] Selecting payment processor for: " + currency);

        PaymentProcessor processor = paymentProcessors.get(currency.toUpperCase());

        if (processor == null) {
            // Try to create processor dynamically
            try {
                processor = CryptoAdapterFactory.getAdapterByCurrency(currency);
                paymentProcessors.put(currency.toUpperCase(), processor);
            } catch (IllegalArgumentException e) {
                System.out.println("[Banking Service] No processor found for: " + currency);
                return null;
            }
        }

        System.out.println("[Banking Service] Using processor: " + processor.getClass().getSimpleName());
        return processor;
    }

    /**
     * Notify observers about completed transaction
     */
    private void notifyTransactionComplete(Transaction txn) {
        Event event = new Event(EventType.TRANSACTION_COMPLETE,
                               Severity.LOW,
                               "Transaction #" + txn.getTransactionId() + " completed");

        event.setMessage("Your " + txn.getCurrency() + " transaction has been processed successfully.");
        event.setRecipient("user@example.com"); // Would be actual user email

        notifyObservers(event);
    }

    // ==================== Observer Pattern Implementation ====================

    @Override
    public void registerObserver(Observer observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
            System.out.println("[Banking Service] Observer registered: " + observer.getObserverId());
        }
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
        System.out.println("[Banking Service] Observer removed: " + observer.getObserverId());
    }

    @Override
    public void notifyObservers(Event event) {
        System.out.println("[Banking Service] Notifying " + observers.size() + " observers");
        for (Observer observer : observers) {
            observer.update(event);
        }
    }

    /**
     * Get supported currencies
     */
    public Set<String> getSupportedCurrencies() {
        return new HashSet<>(paymentProcessors.keySet());
    }
}
