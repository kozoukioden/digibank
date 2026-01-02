package com.digibank.security;

/**
 * Credentials class for authentication
 */
public class Credentials {
    private String username;
    private String password;
    private String otp;
    private String biometric;

    public Credentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getOTP() {
        return otp;
    }

    public void setOTP(String otp) {
        this.otp = otp;
    }

    public String getBiometric() {
        return biometric;
    }

    public void setBiometric(String biometric) {
        this.biometric = biometric;
    }

    public boolean hasBiometric() {
        return biometric != null && !biometric.isEmpty();
    }
}
