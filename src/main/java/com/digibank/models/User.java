package com.digibank.models;

import java.time.LocalDateTime;

/**
 * User model for Smart City system
 * Supports Multi-Factor Authentication and Role-Based Access Control
 */
public class User {
    private int userId;
    private String username;
    private String email;
    private String passwordHash;
    private Role role;
    private boolean mfaEnabled;
    private LocalDateTime createdAt;

    public User(int userId, String username, String email, String passwordHash, Role role) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
        this.mfaEnabled = true; // MFA enabled by default
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isMfaEnabled() {
        return mfaEnabled;
    }

    public void setMfaEnabled(boolean mfaEnabled) {
        this.mfaEnabled = mfaEnabled;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", mfaEnabled=" + mfaEnabled +
                '}';
    }
}
