package com.digibank.observers;

import com.digibank.models.EventType;
import com.digibank.models.Severity;
import java.util.ArrayList;
import java.util.List;

/**
 * Security Observer - observes security-related events
 * Part of Observer Pattern implementation
 */
public class SecurityObserver implements Observer {
    private String observerId;
    private List<String> authorities;
    private List<Event> incidentLog;

    public SecurityObserver(String observerId) {
        this.observerId = observerId;
        this.authorities = new ArrayList<>();
        this.incidentLog = new ArrayList<>();

        // Default authorities
        authorities.add("security@smartcity.gov");
        authorities.add("police@smartcity.gov");
    }

    @Override
    public void update(Event event) {
        System.out.println("[Security Observer] Received event: " + event);

        // Handle security-related events
        if (event.getEventType() == EventType.SECURITY_BREACH ||
            event.getEventType() == EventType.DDOS_ATTEMPT ||
            event.getEventType() == EventType.SQL_INJECTION) {

            logIncident(event);
            alertAuthorities(event);

            if (event.getSeverity().isHigherThan(Severity.HIGH)) {
                activateCountermeasures(event);
            }
        }
    }

    private void alertAuthorities(Event event) {
        System.out.println("[Security Observer] ALERT - Notifying authorities:");
        for (String authority : authorities) {
            System.out.println("  → Sending alert to: " + authority);
            System.out.println("  → Subject: " + event.getEventType() + " - " + event.getSeverity());
            System.out.println("  → Details: " + event.getDescription());
        }
    }

    private void logIncident(Event event) {
        incidentLog.add(event);
        System.out.println("[Security Observer] Incident logged: " + event.getEventId());
    }

    private void activateCountermeasures(Event event) {
        System.out.println("[Security Observer] ⚠ CRITICAL - Activating countermeasures:");

        switch (event.getEventType()) {
            case DDOS_ATTEMPT:
                System.out.println("  → Blacklisting IP: " + event.getSourceIP());
                System.out.println("  → Activating rate limiter");
                System.out.println("  → Sending CAPTCHA challenge");
                break;

            case SQL_INJECTION:
                System.out.println("  → Blocking malicious query");
                System.out.println("  → Sanitizing input parameters");
                System.out.println("  → Logging attack attempt");
                break;

            case SECURITY_BREACH:
                System.out.println("  → Initiating lockdown protocol");
                System.out.println("  → Requiring re-authentication");
                break;

            default:
                System.out.println("  → Generic countermeasure activated");
        }
    }

    @Override
    public String getObserverId() {
        return observerId;
    }

    public List<Event> getIncidentLog() {
        return new ArrayList<>(incidentLog);
    }
}
