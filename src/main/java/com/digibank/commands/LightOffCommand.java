package com.digibank.commands;

import com.digibank.models.StreetLight;

/**
 * Command to turn OFF a street light
 * Part of Command Pattern implementation
 */
public class LightOffCommand implements Command {
    private StreetLight light;

    public LightOffCommand(StreetLight light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.turnOff();
    }

    @Override
    public void undo() {
        light.turnOn();
    }

    @Override
    public String getDescription() {
        return "Turn OFF street light: " + light.getLightId();
    }
}
