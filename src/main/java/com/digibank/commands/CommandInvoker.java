package com.digibank.commands;

import java.util.*;

/**
 * Command Invoker for Command Pattern
 * Manages execution of commands and maintains history
 */
public class CommandInvoker {
    private Queue<Command> commandQueue;
    private Stack<Command> history;

    public CommandInvoker() {
        this.commandQueue = new LinkedList<>();
        this.history = new Stack<>();
    }

    /**
     * Execute a command immediately
     * @param command Command to execute
     */
    public void executeCommand(Command command) {
        System.out.println("[Command Invoker] Executing: " + command.getDescription());
        command.execute();
        history.push(command);
        System.out.println("[Command Invoker] Command executed successfully");
    }

    /**
     * Add command to queue for later execution
     * @param command Command to queue
     */
    public void queueCommand(Command command) {
        commandQueue.offer(command);
        System.out.println("[Command Invoker] Command queued: " + command.getDescription());
    }

    /**
     * Execute all queued commands
     */
    public void executeQueuedCommands() {
        System.out.println("[Command Invoker] Executing " + commandQueue.size() + " queued commands");
        while (!commandQueue.isEmpty()) {
            Command command = commandQueue.poll();
            executeCommand(command);
        }
    }

    /**
     * Undo the last executed command
     */
    public void undoLastCommand() {
        if (!history.isEmpty()) {
            Command lastCommand = history.pop();
            System.out.println("[Command Invoker] Undoing: " + lastCommand.getDescription());
            lastCommand.undo();
        } else {
            System.out.println("[Command Invoker] No commands to undo");
        }
    }

    /**
     * Get command execution history
     * @return List of executed commands
     */
    public List<Command> getHistory() {
        return new ArrayList<>(history);
    }

    /**
     * Clear command history
     */
    public void clearHistory() {
        history.clear();
        System.out.println("[Command Invoker] History cleared");
    }
}
