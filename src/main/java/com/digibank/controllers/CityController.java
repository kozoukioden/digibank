package com.digibank.controllers;

import com.digibank.commands.Command;
import com.digibank.commands.CommandInvoker;
import com.digibank.observers.Event;
import com.digibank.observers.Observer;
import com.digibank.observers.Subject;
import com.digibank.models.StreetLight;
import com.digibank.models.TrafficSignal;

import java.util.*;

/**
 * CityController - Singleton Pattern implementation
 * Central controller for the Smart City system
 * Manages all city infrastructure and coordinates between components
 */
public class CityController implements Subject {
    // Singleton instance
    private static CityController instance;
    private static final Object lock = new Object();

    // Observer Pattern
    private List<Observer> observers;

    // Command Pattern
    private CommandInvoker invoker;

    // City Infrastructure
    private Map<String, StreetLight> streetLights;
    private Map<String, TrafficSignal> trafficSignals;

    // System status
    private boolean systemActive;

    /**
     * Private constructor for Singleton pattern
     */
    private CityController() {
        this.observers = new ArrayList<>();
        this.invoker = new CommandInvoker();
        this.streetLights = new HashMap<>();
        this.trafficSignals = new HashMap<>();
        this.systemActive = true;

        System.out.println("[City Controller] Singleton instance created");
        initializeInfrastructure();
    }

    /**
     * Get the singleton instance (thread-safe)
     * @return CityController instance
     */
    public static CityController getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new CityController();
                }
            }
        }
        return instance;
    }

    /**
     * Initialize city infrastructure
     */
    private void initializeInfrastructure() {
        // Create street lights
        streetLights.put("SL001", new StreetLight("SL001", "Main Street"));
        streetLights.put("SL002", new StreetLight("SL002", "Park Avenue"));
        streetLights.put("SL003", new StreetLight("SL003", "City Center"));

        // Create traffic signals
        trafficSignals.put("TS001", new TrafficSignal("TS001", "Main Intersection"));
        trafficSignals.put("TS002", new TrafficSignal("TS002", "Highway Exit"));

        System.out.println("[City Controller] Infrastructure initialized:");
        System.out.println("  → " + streetLights.size() + " street lights");
        System.out.println("  → " + trafficSignals.size() + " traffic signals");
    }

    // ==================== Subject Interface (Observer Pattern) ====================

    @Override
    public void registerObserver(Observer observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
            System.out.println("[City Controller] Observer registered: " + observer.getObserverId());
        }
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
        System.out.println("[City Controller] Observer removed: " + observer.getObserverId());
    }

    @Override
    public void notifyObservers(Event event) {
        System.out.println("[City Controller] Notifying " + observers.size() + " observers about: " + event.getEventType());
        for (Observer observer : observers) {
            observer.update(event);
        }
    }

    // ==================== Command Execution ====================

    /**
     * Execute a command through the invoker
     * @param command Command to execute
     */
    public void executeCommand(Command command) {
        invoker.executeCommand(command);
    }

    /**
     * Queue a command for later execution
     * @param command Command to queue
     */
    public void queueCommand(Command command) {
        invoker.queueCommand(command);
    }

    /**
     * Execute all queued commands
     */
    public void executeQueuedCommands() {
        invoker.executeQueuedCommands();
    }

    /**
     * Undo the last command
     */
    public void undoLastCommand() {
        invoker.undoLastCommand();
    }

    // ==================== City Management ====================

    /**
     * Get street light by ID
     * @param lightId Light ID
     * @return StreetLight object
     */
    public StreetLight getStreetLight(String lightId) {
        return streetLights.get(lightId);
    }

    /**
     * Get traffic signal by ID
     * @param signalId Signal ID
     * @return TrafficSignal object
     */
    public TrafficSignal getTrafficSignal(String signalId) {
        return trafficSignals.get(signalId);
    }

    /**
     * Get all street lights
     * @return Map of all street lights
     */
    public Map<String, StreetLight> getAllStreetLights() {
        return new HashMap<>(streetLights);
    }

    /**
     * Get all traffic signals
     * @return Map of all traffic signals
     */
    public Map<String, TrafficSignal> getAllTrafficSignals() {
        return new HashMap<>(trafficSignals);
    }

    /**
     * Get city status
     * @return City status summary
     */
    public String getCityStatus() {
        StringBuilder status = new StringBuilder();
        status.append("===== SMART CITY STATUS =====\n");
        status.append("System Active: ").append(systemActive).append("\n");
        status.append("Registered Observers: ").append(observers.size()).append("\n");
        status.append("\nStreet Lights:\n");
        for (StreetLight light : streetLights.values()) {
            status.append("  - ").append(light.getStatus()).append("\n");
        }
        status.append("\nTraffic Signals:\n");
        for (TrafficSignal signal : trafficSignals.values()) {
            status.append("  - ").append(signal.getStatus()).append("\n");
        }
        status.append("============================\n");
        return status.toString();
    }

    /**
     * Shutdown the city controller
     */
    public void shutdown() {
        System.out.println("[City Controller] Shutting down...");
        systemActive = false;
        observers.clear();
        System.out.println("[City Controller] Shutdown complete");
    }

    public boolean isSystemActive() {
        return systemActive;
    }
}
