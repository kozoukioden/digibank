package com.digibank.models;

/**
 * Types of cryptocurrencies supported
 * Used by Adapter Pattern
 */
public enum CryptoType {
    FIAT("Fiat Currency (USD, EUR, TRY)"),
    BITCOIN("Bitcoin (BTC)"),
    ETHEREUM("Ethereum (ETH)"),
    LITECOIN("Litecoin (LTC)");

    private final String description;

    CryptoType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
