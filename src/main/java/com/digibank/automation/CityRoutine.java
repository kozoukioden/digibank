package com.digibank.automation;

/**
 * Abstract class for City Routine - Template Method Pattern
 * Defines the skeleton of automated city operations
 */
public abstract class CityRoutine {
    /**
     * Template method - defines the algorithm structure
     * Subclasses cannot override this method
     */
    public final void execute() {
        System.out.println("\n========== CITY ROUTINE EXECUTION ==========");
        System.out.println("Routine: " + getRoutineName());
        System.out.println("===========================================\n");

        prepareSystem();
        performMainTask();
        cleanupAndLog();
        notifyCompletion();

        System.out.println("\n========== ROUTINE COMPLETE ==========\n");
    }

    /**
     * Prepare system before executing routine
     * Common preparation logic
     */
    protected void prepareSystem() {
        System.out.println("[Routine] Preparing system...");
        System.out.println("[Routine] Checking system health");
        System.out.println("[Routine] Verifying permissions");
        System.out.println("[Routine] System ready\n");
    }

    /**
     * Main task to perform - must be implemented by subclasses
     * This is the varying part of the algorithm
     */
    protected abstract void performMainTask();

    /**
     * Cleanup and logging after task execution
     * Common cleanup logic
     */
    protected void cleanupAndLog() {
        System.out.println("\n[Routine] Cleaning up...");
        System.out.println("[Routine] Logging execution details");
        System.out.println("[Routine] Updating system metrics");
        System.out.println("[Routine] Cleanup complete");
    }

    /**
     * Notify completion to observers
     * Common notification logic
     */
    protected void notifyCompletion() {
        System.out.println("[Routine] Notifying observers of completion");
        System.out.println("[Routine] Routine '" + getRoutineName() + "' completed successfully");
    }

    /**
     * Get routine name - can be overridden by subclasses
     * @return Routine name
     */
    protected String getRoutineName() {
        return this.getClass().getSimpleName();
    }
}
