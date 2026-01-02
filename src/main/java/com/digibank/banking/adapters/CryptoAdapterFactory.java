package com.digibank.banking.adapters;

import com.digibank.banking.FiatPaymentProcessor;
import com.digibank.banking.PaymentProcessor;
import com.digibank.models.CryptoType;

/**
 * Crypto Adapter Factory - creates appropriate payment processor
 * Part of Factory Pattern (works with Adapter Pattern)
 */
public class CryptoAdapterFactory {

    /**
     * Get payment processor for specified crypto type
     * @param type Crypto type
     * @return Appropriate payment processor
     */
    public static PaymentProcessor getAdapter(CryptoType type) {
        System.out.println("[Crypto Factory] Creating adapter for: " + type);

        switch (type) {
            case BITCOIN:
                return new BitcoinAdapter();

            case ETHEREUM:
                return new EthereumAdapter();

            case FIAT:
                return new FiatPaymentProcessor("USD");

            case LITECOIN:
                // Could add LitecoinAdapter here
                System.out.println("[Crypto Factory] Litecoin not yet implemented, using Bitcoin adapter");
                return new BitcoinAdapter();

            default:
                throw new IllegalArgumentException("Unsupported crypto type: " + type);
        }
    }

    /**
     * Get payment processor by currency string
     * @param currency Currency code (USD, BTC, ETH, etc.)
     * @return Appropriate payment processor
     */
    public static PaymentProcessor getAdapterByCurrency(String currency) {
        currency = currency.toUpperCase();

        switch (currency) {
            case "BTC":
            case "BITCOIN":
                return new BitcoinAdapter();

            case "ETH":
            case "ETHEREUM":
                return new EthereumAdapter();

            case "USD":
            case "EUR":
            case "TRY":
                return new FiatPaymentProcessor(currency);

            default:
                throw new IllegalArgumentException("Unsupported currency: " + currency);
        }
    }
}
