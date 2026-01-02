package com.digibank.models;

/**
 * Types of events in the Smart City system
 * Used by Observer Pattern for notifications
 */
public enum EventType {
    SECURITY_BREACH,
    DDOS_ATTEMPT,
    SQL_INJECTION,
    TRANSACTION_COMPLETE,
    CITY_ALERT,
    MAINTENANCE_REQUIRED,
    LIGHT_STATUS_CHANGED,
    TRAFFIC_ADJUSTED
}
