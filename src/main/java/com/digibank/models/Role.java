package com.digibank.models;

/**
 * Enum for User Roles in Smart City System
 * Used in Role-Based Access Control (RBAC)
 */
public enum Role {
    ADMIN("Administrator - Full system access"),
    CITY_MANAGER("City Manager - Infrastructure control"),
    RESIDENT("Resident - Basic services access"),
    PUBLIC_SAFETY("Public Safety Officer - Security access"),
    UTILITY_WORKER("Utility Worker - Maintenance access");

    private final String description;

    Role(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
