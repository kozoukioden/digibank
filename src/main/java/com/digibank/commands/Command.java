package com.digibank.commands;

/**
 * Command interface for Command Pattern
 * Encapsulates actions as objects
 */
public interface Command {
    /**
     * Execute the command
     */
    void execute();

    /**
     * Undo the command
     */
    void undo();

    /**
     * Get description of what this command does
     * @return Command description
     */
    String getDescription();
}
