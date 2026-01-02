package com.digibank.security;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Pattern;

/**
 * SQL Injection Protection System
 * Implements multiple defense mechanisms:
 * - PreparedStatement usage (primary defense)
 * - Input sanitization
 * - SQL keyword detection
 * - Pattern validation
 */
public class SQLInjectionProtection {
    // Dangerous SQL keywords and patterns
    private static final Pattern[] DANGEROUS_PATTERNS = {
        Pattern.compile("('|(\\-\\-)|(;)|(<)|(>))", Pattern.CASE_INSENSITIVE),
        Pattern.compile("(\\b(SELECT|INSERT|UPDATE|DELETE|DROP|CREATE|ALTER|EXEC|EXECUTE)\\b)", Pattern.CASE_INSENSITIVE),
        Pattern.compile("(\\bOR\\b.*=.*)", Pattern.CASE_INSENSITIVE),
        Pattern.compile("(\\bAND\\b.*=.*)", Pattern.CASE_INSENSITIVE),
        Pattern.compile("(\\bUNION\\b)", Pattern.CASE_INSENSITIVE)
    };

    public SQLInjectionProtection() {
        System.out.println("[SQL Protection] Initialized");
    }

    /**
     * Create safe SQL query using PreparedStatement
     * This is the PRIMARY defense against SQL injection
     *
     * @param conn Database connection
     * @param query SQL query with ? placeholders
     * @param params Parameters to bind
     * @return Prepared statement with bound parameters
     */
    public PreparedStatement createSafeQuery(Connection conn, String query, Object... params)
            throws SQLException {

        System.out.println("[SQL Protection] Creating safe query with PreparedStatement");

        // Always use PreparedStatement to prevent SQL injection
        PreparedStatement stmt = conn.prepareStatement(query);

        // Bind parameters safely
        for (int i = 0; i < params.length; i++) {
            Object param = sanitize(params[i]);
            stmt.setObject(i + 1, param);
        }

        System.out.println("[SQL Protection] ✓ Safe query created");
        return stmt;
    }

    /**
     * Sanitize input parameter (additional layer of defense)
     * @param param Parameter to sanitize
     * @return Sanitized parameter
     */
    public Object sanitize(Object param) {
        if (param instanceof String) {
            String str = (String) param;

            // Remove potentially dangerous characters
            str = str.replaceAll("[';\"\\-\\-]", "");

            // Check for SQL keywords
            if (detectSQLKeywords(str)) {
                System.out.println("[SQL Protection] ⚠ SQL keywords detected in input: " + str);
                // Escape or reject
                str = escapeSQLKeywords(str);
            }

            return str;
        }

        return param;
    }

    /**
     * Validate user input against SQL injection patterns
     * @param input User input to validate
     * @return true if safe, false if potentially malicious
     */
    public boolean validateInput(String input) {
        if (input == null || input.isEmpty()) {
            return true;
        }

        System.out.println("[SQL Protection] Validating input: " + input);

        // Check against dangerous patterns
        for (Pattern pattern : DANGEROUS_PATTERNS) {
            if (pattern.matcher(input).find()) {
                System.out.println("[SQL Protection] ✗ DANGEROUS pattern detected!");
                System.out.println("[SQL Protection] Pattern: " + pattern.pattern());
                System.out.println("[SQL Protection] Input: " + input);
                return false;
            }
        }

        System.out.println("[SQL Protection] ✓ Input validated");
        return true;
    }

    /**
     * Detect SQL keywords in input
     */
    private boolean detectSQLKeywords(String input) {
        String upperInput = input.toUpperCase();

        String[] keywords = {"SELECT", "INSERT", "UPDATE", "DELETE", "DROP",
                            "CREATE", "ALTER", "EXEC", "EXECUTE", "UNION",
                            "OR 1=1", "AND 1=1", "--", "/*", "*/"};

        for (String keyword : keywords) {
            if (upperInput.contains(keyword)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Escape SQL keywords (if they must be allowed)
     */
    private String escapeSQLKeywords(String input) {
        // Escape common SQL injection patterns
        return input.replaceAll("(')", "''")  // Escape single quotes
                   .replaceAll("(--)", "")    // Remove SQL comments
                   .replaceAll("(;)", "");    // Remove statement terminators
    }

    /**
     * Demonstrate SQL Injection prevention
     */
    public void demonstrateProtection() {
        System.out.println("\n========== SQL INJECTION PROTECTION DEMO ==========");

        String[] testInputs = {
            "john_doe",                                   // Safe input
            "admin' OR '1'='1",                          // Classic SQL injection
            "'; DROP TABLE users; --",                   // Destructive attack
            "1' UNION SELECT * FROM passwords --",       // Union-based attack
            "admin'--"                                    // Comment-based attack
        };

        for (String input : testInputs) {
            System.out.println("\n→ Testing input: \"" + input + "\"");
            boolean safe = validateInput(input);
            System.out.println("  Result: " + (safe ? "✓ SAFE" : "✗ BLOCKED"));
        }

        System.out.println("\n==================================================\n");
    }
}
