package com.digibank.observers;

import com.digibank.models.EventType;
import com.digibank.models.Severity;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Event class for Observer Pattern
 * Represents events in the Smart City system
 */
public class Event {
    private String eventId;
    private EventType eventType;
    private LocalDateTime timestamp;
    private Severity severity;
    private String description;
    private String sourceIP;
    private String recipient;
    private String message;

    public Event(EventType eventType, Severity severity, String description) {
        this.eventId = generateEventId();
        this.eventType = eventType;
        this.timestamp = LocalDateTime.now();
        this.severity = severity;
        this.description = description;
    }

    private String generateEventId() {
        return "EVT-" + System.currentTimeMillis();
    }

    // Getters and Setters
    public String getEventId() {
        return eventId;
    }

    public EventType getEventType() {
        return eventType;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public Severity getSeverity() {
        return severity;
    }

    public String getDescription() {
        return description;
    }

    public String getSourceIP() {
        return sourceIP;
    }

    public void setSourceIP(String sourceIP) {
        this.sourceIP = sourceIP;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return String.format("[%s] %s - %s: %s (Severity: %s)",
                timestamp.format(formatter),
                eventId,
                eventType,
                description,
                severity);
    }
}
