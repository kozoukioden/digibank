package com.digibank.commands;

import com.digibank.models.StreetLight;

/**
 * Command to turn ON a street light
 * Part of Command Pattern implementation
 */
public class LightOnCommand implements Command {
    private StreetLight light;

    public LightOnCommand(StreetLight light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.turnOn();
    }

    @Override
    public void undo() {
        light.turnOff();
    }

    @Override
    public String getDescription() {
        return "Turn ON street light: " + light.getLightId();
    }
}
