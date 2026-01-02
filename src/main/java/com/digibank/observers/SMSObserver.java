package com.digibank.observers;

import com.digibank.models.Severity;

/**
 * SMS Observer - sends SMS notifications for critical events
 * Part of Observer Pattern implementation
 */
public class SMSObserver implements Observer {
    private String observerId;
    private String defaultPhoneNumber;

    public SMSObserver(String observerId, String defaultPhoneNumber) {
        this.observerId = observerId;
        this.defaultPhoneNumber = defaultPhoneNumber;
    }

    @Override
    public void update(Event event) {
        // Only send SMS for high severity events
        if (event.getSeverity().isHigherThan(Severity.MEDIUM)) {
            System.out.println("[SMS Observer] Received CRITICAL event: " + event.getEventId());
            sendSMS(defaultPhoneNumber, event);
        }
    }

    private void sendSMS(String phoneNumber, Event event) {
        String message = String.format("ALERT: %s - %s. Time: %s",
                event.getEventType(),
                event.getDescription(),
                event.getTimestamp());

        System.out.println("[SMS Observer] Sending SMS:");
        System.out.println("  → To: " + phoneNumber);
        System.out.println("  → Message: " + message);
        System.out.println("  → SMS sent successfully");
    }

    @Override
    public String getObserverId() {
        return observerId;
    }
}
