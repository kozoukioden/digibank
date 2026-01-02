package com.digibank.security;

import com.digibank.models.EventType;
import com.digibank.models.Severity;
import com.digibank.observers.Event;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * DDoS Defense System
 * Implements multiple defense mechanisms:
 * - Rate limiting
 * - IP blacklisting
 * - Anomalous pattern detection
 * - Automated countermeasures
 */
public class DDoSDefense {
    // Rate limiting: max requests per IP per minute
    private static final int MAX_REQUESTS_PER_MINUTE = 100;
    private static final int ANOMALY_THRESHOLD_PERCENT = 70;

    // Blacklist duration in minutes
    private static final int BLACKLIST_DURATION = 15;

    // Track request counts per IP
    private Map<String, List<Long>> ipRequestTimestamps;

    // Blacklisted IPs
    private Map<String, Long> blacklistedIPs;

    public DDoSDefense() {
        this.ipRequestTimestamps = new ConcurrentHashMap<>();
        this.blacklistedIPs = new ConcurrentHashMap<>();

        System.out.println("[DDoS Defense] Initialized");
        System.out.println("[DDoS Defense] Rate limit: " + MAX_REQUESTS_PER_MINUTE + " requests/minute");
    }

    /**
     * Check if request is allowed
     * @param clientIP Client IP address
     * @return true if allowed, false if blocked
     */
    public boolean isAllowed(String clientIP) {
        // Check if IP is blacklisted
        if (isBlacklisted(clientIP)) {
            System.out.println("[DDoS Defense] ✗ Request BLOCKED - IP blacklisted: " + clientIP);
            return false;
        }

        // Rate limiting check
        if (!checkRateLimit(clientIP)) {
            System.out.println("[DDoS Defense] ⚠ Rate limit exceeded for IP: " + clientIP);
            blacklistIP(clientIP);
            activateCountermeasures(clientIP, "Rate limit exceeded");
            return false;
        }

        // Anomaly detection
        if (detectAnomalousPattern(clientIP)) {
            System.out.println("[DDoS Defense] ⚠ Anomalous pattern detected from IP: " + clientIP);
            activateCountermeasures(clientIP, "Anomalous pattern");
            return false;
        }

        // Record request timestamp
        recordRequest(clientIP);

        return true;
    }

    /**
     * Check if IP is blacklisted
     */
    private boolean isBlacklisted(String ip) {
        Long blacklistTime = blacklistedIPs.get(ip);

        if (blacklistTime == null) {
            return false;
        }

        // Check if blacklist has expired
        long currentTime = System.currentTimeMillis();
        long elapsedMinutes = (currentTime - blacklistTime) / (1000 * 60);

        if (elapsedMinutes >= BLACKLIST_DURATION) {
            // Remove from blacklist
            blacklistedIPs.remove(ip);
            System.out.println("[DDoS Defense] IP removed from blacklist (expired): " + ip);
            return false;
        }

        return true;
    }

    /**
     * Check rate limit for IP
     */
    private boolean checkRateLimit(String ip) {
        List<Long> timestamps = ipRequestTimestamps.get(ip);

        if (timestamps == null) {
            return true; // First request from this IP
        }

        // Remove timestamps older than 1 minute
        long currentTime = System.currentTimeMillis();
        long oneMinuteAgo = currentTime - (60 * 1000);

        timestamps.removeIf(timestamp -> timestamp < oneMinuteAgo);

        // Check if exceeded rate limit
        return timestamps.size() < MAX_REQUESTS_PER_MINUTE;
    }

    /**
     * Detect anomalous request patterns (simulated)
     */
    private boolean detectAnomalousPattern(String ip) {
        List<Long> timestamps = ipRequestTimestamps.get(ip);

        if (timestamps == null || timestamps.size() < 10) {
            return false; // Not enough data
        }

        // Simulate pattern analysis
        // In production, would use machine learning or statistical analysis

        // Check for burst pattern (many requests in short time)
        long currentTime = System.currentTimeMillis();
        long fiveSecondsAgo = currentTime - (5 * 1000);

        long recentRequests = timestamps.stream()
                                        .filter(t -> t > fiveSecondsAgo)
                                        .count();

        // If more than 50 requests in 5 seconds, flag as anomalous
        return recentRequests > 50;
    }

    /**
     * Record request timestamp for IP
     */
    private void recordRequest(String ip) {
        ipRequestTimestamps.computeIfAbsent(ip, k -> new ArrayList<>())
                          .add(System.currentTimeMillis());
    }

    /**
     * Blacklist IP address
     */
    private void blacklistIP(String ip) {
        blacklistedIPs.put(ip, System.currentTimeMillis());
        System.out.println("[DDoS Defense] IP BLACKLISTED for " + BLACKLIST_DURATION + " minutes: " + ip);
    }

    /**
     * Activate countermeasures
     */
    private void activateCountermeasures(String ip, String reason) {
        System.out.println("\n[DDoS Defense] ⚠⚠⚠ ACTIVATING COUNTERMEASURES ⚠⚠⚠");
        System.out.println("[DDoS Defense] Reason: " + reason);
        System.out.println("[DDoS Defense] Target IP: " + ip);
        System.out.println("[DDoS Defense] Actions:");
        System.out.println("  → Blacklisting IP");
        System.out.println("  → Sending CAPTCHA challenge");
        System.out.println("  → Alerting security team");
        System.out.println("  → Logging incident");

        // Create security event
        Event event = new Event(EventType.DDOS_ATTEMPT, Severity.CRITICAL,
                               "DDoS attack detected from IP: " + ip + " - " + reason);
        event.setSourceIP(ip);

        notifySecurityTeam(event);
    }

    /**
     * Notify security team (simulated)
     */
    private void notifySecurityTeam(Event event) {
        System.out.println("[DDoS Defense] 📧 Sending alert to security team:");
        System.out.println("  → Subject: CRITICAL - DDoS Attack Detected");
        System.out.println("  → Details: " + event.getDescription());
        System.out.println("  → Source IP: " + event.getSourceIP());
    }

    /**
     * Get blacklist status
     */
    public Map<String, Long> getBlacklistedIPs() {
        return new HashMap<>(blacklistedIPs);
    }

    /**
     * Manually remove IP from blacklist
     */
    public void removeFromBlacklist(String ip) {
        blacklistedIPs.remove(ip);
        System.out.println("[DDoS Defense] IP manually removed from blacklist: " + ip);
    }
}
