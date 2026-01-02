package com.digibank.commands;

import com.digibank.models.TrafficSignal;

/**
 * Command to adjust traffic signal mode
 * Part of Command Pattern implementation
 */
public class AdjustTrafficCommand implements Command {
    private TrafficSignal trafficSignal;
    private TrafficSignal.TrafficMode newMode;
    private TrafficSignal.TrafficMode previousMode;

    public AdjustTrafficCommand(TrafficSignal trafficSignal, TrafficSignal.TrafficMode newMode) {
        this.trafficSignal = trafficSignal;
        this.newMode = newMode;
        this.previousMode = trafficSignal.getCurrentMode();
    }

    @Override
    public void execute() {
        previousMode = trafficSignal.getCurrentMode();
        trafficSignal.setMode(newMode);
    }

    @Override
    public void undo() {
        trafficSignal.setMode(previousMode);
    }

    @Override
    public String getDescription() {
        return "Adjust traffic signal " + trafficSignal.getSignalId() + " to " + newMode + " mode";
    }
}
