package com.digibank.security;

/**
 * Exception thrown when user is unauthorized to access a resource
 */
public class UnauthorizedException extends Exception {
    public UnauthorizedException(String message) {
        super(message);
    }
}
