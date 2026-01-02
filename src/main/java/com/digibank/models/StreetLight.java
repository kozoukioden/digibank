package com.digibank.models;

/**
 * Street Light model for Smart City infrastructure
 * Controlled by Command Pattern
 */
public class StreetLight {
    private String lightId;
    private String location;
    private boolean isOn;

    public StreetLight(String lightId, String location) {
        this.lightId = lightId;
        this.location = location;
        this.isOn = false; // Lights off by default
    }

    public void turnOn() {
        isOn = true;
        System.out.println("[Street Light] " + lightId + " at " + location + " turned ON");
    }

    public void turnOff() {
        isOn = false;
        System.out.println("[Street Light] " + lightId + " at " + location + " turned OFF");
    }

    public String getStatus() {
        return "Street Light " + lightId + " at " + location + " is " + (isOn ? "ON" : "OFF");
    }

    public String getLightId() {
        return lightId;
    }

    public String getLocation() {
        return location;
    }

    public boolean isOn() {
        return isOn;
    }
}
