package com.digibank.security;

import com.digibank.models.User;

/**
 * Security Manager - Coordinates all security modules
 * Provides unified interface for security operations
 */
public class SecurityManager {
    private MFAuthentication mfAuth;
    private RoleBasedAccess rbac;
    private QuantumResistantEncryption encryption;
    private DDoSDefense ddosDefense;
    private SQLInjectionProtection sqlProtection;

    public SecurityManager() {
        this.mfAuth = new MFAuthentication();
        this.rbac = new RoleBasedAccess();
        this.encryption = new QuantumResistantEncryption();
        this.ddosDefense = new DDoSDefense();
        this.sqlProtection = new SQLInjectionProtection();

        System.out.println("\n[Security Manager] All security modules initialized");
        System.out.println("[Security Manager] System ready for secure operations\n");
    }

    // ==================== Authentication & Authorization ====================

    /**
     * Authenticate user with multi-factor authentication
     * @param user User to authenticate
     * @param creds Credentials
     * @return true if authenticated
     */
    public boolean authenticateUser(User user, Credentials creds) {
        return mfAuth.authenticate(user, creds);
    }

    /**
     * Authorize user access to resource
     * @param user User requesting access
     * @param resource Resource to access
     * @return true if authorized
     */
    public boolean authorizeAccess(User user, String resource) {
        try {
            rbac.checkAccess(user, resource);
            return true;
        } catch (UnauthorizedException e) {
            System.out.println("[Security Manager] Authorization failed: " + e.getMessage());
            return false;
        }
    }

    // ==================== DDoS Protection ====================

    /**
     * Validate incoming request for DDoS protection
     * @param clientIP Client IP address
     * @return true if request is allowed
     */
    public boolean validateRequest(String clientIP) {
        return ddosDefense.isAllowed(clientIP);
    }

    // ==================== SQL Injection Protection ====================

    /**
     * Validate user input for SQL injection
     * @param input User input
     * @return true if safe
     */
    public boolean validateInput(String input) {
        return sqlProtection.validateInput(input);
    }

    /**
     * Sanitize user input
     * @param input Input to sanitize
     * @return Sanitized input
     */
    public String sanitizeInput(String input) {
        return (String) sqlProtection.sanitize(input);
    }

    // ==================== Encryption ====================

    /**
     * Encrypt data using quantum-resistant encryption
     * @param data Data to encrypt
     * @return Encrypted data
     */
    public String encryptData(String data) {
        String publicKey = "PUBLIC_KEY_123"; // Would be actual key in production
        return encryption.encrypt(data, publicKey);
    }

    /**
     * Decrypt data using quantum-resistant encryption
     * @param encryptedData Encrypted data
     * @return Decrypted data
     */
    public String decryptData(String encryptedData) {
        String privateKey = "PRIVATE_KEY_456"; // Would be actual key in production
        return encryption.decrypt(encryptedData, privateKey);
    }

    /**
     * Sign data for integrity verification
     * @param data Data to sign
     * @return Digital signature
     */
    public String signData(String data) {
        String privateKey = "PRIVATE_KEY_456";
        return encryption.sign(data, privateKey);
    }

    /**
     * Verify data signature
     * @param data Original data
     * @param signature Signature to verify
     * @return true if valid
     */
    public boolean verifySignature(String data, String signature) {
        String publicKey = "PUBLIC_KEY_123";
        return encryption.verify(data, signature, publicKey);
    }

    // ==================== Security Demonstrations ====================

    /**
     * Demonstrate all security features
     */
    public void demonstrateSecurityFeatures() {
        System.out.println("\n========================================");
        System.out.println("SECURITY FEATURES DEMONSTRATION");
        System.out.println("========================================\n");

        // 1. SQL Injection Protection
        System.out.println("1. SQL Injection Protection:");
        sqlProtection.demonstrateProtection();

        // 2. DDoS Protection
        System.out.println("2. DDoS Protection:");
        demonstrateDDoSProtection();

        // 3. Encryption
        System.out.println("3. Quantum-Resistant Encryption:");
        demonstrateEncryption();

        System.out.println("\n========================================");
        System.out.println("SECURITY DEMONSTRATION COMPLETE");
        System.out.println("========================================\n");
    }

    private void demonstrateDDoSProtection() {
        System.out.println("\n→ Simulating request flood from IP: 192.168.1.100");

        for (int i = 0; i < 150; i++) {
            if (i == 100) {
                System.out.println("\n  [After 100 requests]");
            }
            boolean allowed = ddosDefense.isAllowed("192.168.1.100");
            if (!allowed) {
                System.out.println("  Request #" + (i+1) + ": BLOCKED");
                break;
            }
        }
        System.out.println();
    }

    private void demonstrateEncryption() {
        System.out.println("\n→ Encrypting sensitive data");
        String sensitiveData = "User SSN: 123-45-6789";
        System.out.println("  Original: " + sensitiveData);

        String encrypted = encryptData(sensitiveData);
        System.out.println("  Encrypted: " + encrypted.substring(0, Math.min(50, encrypted.length())) + "...");

        String decrypted = decryptData(encrypted);
        System.out.println("  Decrypted: " + decrypted);
        System.out.println("  Match: " + (sensitiveData.equals(decrypted) ? "✓" : "✗"));
        System.out.println();
    }

    // ==================== Getters for individual modules ====================

    public MFAuthentication getMfAuth() {
        return mfAuth;
    }

    public RoleBasedAccess getRbac() {
        return rbac;
    }

    public QuantumResistantEncryption getEncryption() {
        return encryption;
    }

    public DDoSDefense getDdosDefense() {
        return ddosDefense;
    }

    public SQLInjectionProtection getSqlProtection() {
        return sqlProtection;
    }
}
