package com.digibank.security;

import com.digibank.models.User;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

/**
 * Multi-Factor Authentication Implementation
 * Provides 3-factor authentication:
 * 1. Password verification
 * 2. OTP (One-Time Password)
 * 3. Biometric (optional)
 */
public class MFAuthentication {
    private Map<String, String> otpStore;
    private SecureRandom random;

    public MFAuthentication() {
        this.otpStore = new HashMap<>();
        this.random = new SecureRandom();
    }

    /**
     * Authenticate user with multi-factor authentication
     * @param user User to authenticate
     * @param creds Credentials provided
     * @return true if authenticated, false otherwise
     */
    public boolean authenticate(User user, Credentials creds) {
        System.out.println("\n[MFA] Starting Multi-Factor Authentication for: " + user.getUsername());

        // Step 1: Password verification
        System.out.println("[MFA] Step 1: Verifying password...");
        if (!verifyPassword(user, creds.getPassword())) {
            System.out.println("[MFA] ✗ Password verification FAILED");
            return false;
        }
        System.out.println("[MFA] ✓ Password verified");

        // Step 2: OTP verification (if MFA enabled)
        if (user.isMfaEnabled()) {
            System.out.println("[MFA] Step 2: OTP verification required");

            // Generate and send OTP
            String otp = generateOTP(user);
            sendOTP(user.getEmail(), otp);

            // Verify OTP
            String userOTP = creds.getOTP();
            if (!verifyOTP(user.getUsername(), userOTP)) {
                System.out.println("[MFA] ✗ OTP verification FAILED");
                return false;
            }
            System.out.println("[MFA] ✓ OTP verified");
        }

        // Step 3: Biometric verification (optional)
        if (creds.hasBiometric()) {
            System.out.println("[MFA] Step 3: Biometric verification");
            if (!verifyBiometric(user, creds.getBiometric())) {
                System.out.println("[MFA] ✗ Biometric verification FAILED");
                return false;
            }
            System.out.println("[MFA] ✓ Biometric verified");
        }

        System.out.println("[MFA] ✓✓✓ Multi-Factor Authentication SUCCESSFUL\n");
        return true;
    }

    /**
     * Verify password hash
     */
    private boolean verifyPassword(User user, String password) {
        // In production, would use bcrypt or similar
        String passwordHash = hashPassword(password);
        return user.getPasswordHash().equals(passwordHash);
    }

    /**
     * Generate OTP for user
     */
    private String generateOTP(User user) {
        // Generate 6-digit OTP
        int otpValue = 100000 + random.nextInt(900000);
        String otp = String.valueOf(otpValue);

        // Store OTP (in production, would have expiration)
        otpStore.put(user.getUsername(), otp);

        System.out.println("[MFA] Generated OTP: " + otp + " (for demo purposes)");
        return otp;
    }

    /**
     * Send OTP via email (simulated)
     */
    private void sendOTP(String email, String otp) {
        System.out.println("[MFA] Sending OTP to email: " + email);
        System.out.println("[MFA] Email content: Your OTP is: " + otp);
        System.out.println("[MFA] OTP valid for 5 minutes");
    }

    /**
     * Verify OTP provided by user
     */
    private boolean verifyOTP(String username, String userOTP) {
        String storedOTP = otpStore.get(username);

        if (storedOTP == null) {
            System.out.println("[MFA] No OTP found for user: " + username);
            return false;
        }

        boolean valid = storedOTP.equals(userOTP);

        // Remove OTP after verification (one-time use)
        if (valid) {
            otpStore.remove(username);
        }

        return valid;
    }

    /**
     * Verify biometric data (simulated)
     */
    private boolean verifyBiometric(User user, String biometric) {
        // In production, would compare with stored biometric template
        System.out.println("[MFA] Comparing biometric data...");
        System.out.println("[MFA] Biometric match confidence: 98%");

        // Simulate biometric verification (always succeeds in demo)
        return biometric != null && !biometric.isEmpty();
    }

    /**
     * Hash password (simplified for demo)
     */
    private String hashPassword(String password) {
        // In production, use bcrypt or Argon2
        return "HASH_" + password + "_SALT";
    }
}
