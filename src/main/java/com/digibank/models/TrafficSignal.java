package com.digibank.models;

/**
 * Traffic Signal model for Smart City infrastructure
 * Controlled by Command Pattern
 */
public class TrafficSignal {
    private String signalId;
    private String location;
    private TrafficMode currentMode;

    public enum TrafficMode {
        NORMAL,
        RUSH_HOUR,
        NIGHT_MODE,
        EMERGENCY
    }

    public TrafficSignal(String signalId, String location) {
        this.signalId = signalId;
        this.location = location;
        this.currentMode = TrafficMode.NORMAL;
    }

    public void setMode(TrafficMode mode) {
        this.currentMode = mode;
        System.out.println("[Traffic Signal] " + signalId + " at " + location +
                          " set to " + mode + " mode");
    }

    public String getStatus() {
        return "Traffic Signal " + signalId + " at " + location + " is in " + currentMode + " mode";
    }

    public String getSignalId() {
        return signalId;
    }

    public String getLocation() {
        return location;
    }

    public TrafficMode getCurrentMode() {
        return currentMode;
    }
}
