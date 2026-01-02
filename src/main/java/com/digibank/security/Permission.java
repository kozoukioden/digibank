package com.digibank.security;

/**
 * Permission enum for Role-Based Access Control
 */
public enum Permission {
    // City Management
    CONTROL_LIGHTS,
    CONTROL_TRAFFIC,
    VIEW_SENSORS,
    EXECUTE_ROUTINES,

    // Banking
    PROCESS_PAYMENT,
    VIEW_TRANSACTIONS,
    MANAGE_ACCOUNTS,

    // Security
    VIEW_SECURITY_LOGS,
    MANAGE_USERS,
    CONFIGURE_SECURITY,

    // System
    SYSTEM_ADMIN,
    VIEW_REPORTS,
    DEPLOY_UPDATES;

    public static Permission forResource(String resource) {
        try {
            return Permission.valueOf(resource.toUpperCase());
        } catch (IllegalArgumentException e) {
            // Default permission if resource not found
            return null;
        }
    }
}
