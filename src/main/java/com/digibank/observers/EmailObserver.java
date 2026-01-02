package com.digibank.observers;

import com.digibank.models.EventType;

/**
 * Email Observer - sends email notifications for events
 * Part of Observer Pattern implementation
 */
public class EmailObserver implements Observer {
    private String observerId;
    private String defaultRecipient;

    public EmailObserver(String observerId, String defaultRecipient) {
        this.observerId = observerId;
        this.defaultRecipient = defaultRecipient;
    }

    @Override
    public void update(Event event) {
        System.out.println("[Email Observer] Received event: " + event.getEventId());

        String recipient = event.getRecipient() != null ?
                          event.getRecipient() : defaultRecipient;

        sendEmail(recipient, event);
    }

    private void sendEmail(String recipient, Event event) {
        System.out.println("[Email Observer] Sending email:");
        System.out.println("  → To: " + recipient);
        System.out.println("  → Subject: " + getEmailSubject(event));
        System.out.println("  → Body: " + getEmailBody(event));
        System.out.println("  → Email sent successfully at " + event.getTimestamp());
    }

    private String getEmailSubject(Event event) {
        return "Smart City Alert: " + event.getEventType() + " [" + event.getSeverity() + "]";
    }

    private String getEmailBody(Event event) {
        StringBuilder body = new StringBuilder();
        body.append("Dear Resident,\n\n");
        body.append("An event has occurred in the Smart City system:\n\n");
        body.append("Event ID: ").append(event.getEventId()).append("\n");
        body.append("Type: ").append(event.getEventType()).append("\n");
        body.append("Severity: ").append(event.getSeverity()).append("\n");
        body.append("Time: ").append(event.getTimestamp()).append("\n");
        body.append("Description: ").append(event.getDescription()).append("\n");

        if (event.getEventType() == EventType.TRANSACTION_COMPLETE) {
            body.append("\nYour transaction has been completed successfully.");
        } else if (event.getEventType() == EventType.SECURITY_BREACH) {
            body.append("\n⚠ URGENT: Please review your account and change your password immediately.");
        }

        body.append("\n\nBest regards,\nSmart City Administration");
        return body.toString();
    }

    @Override
    public String getObserverId() {
        return observerId;
    }
}
