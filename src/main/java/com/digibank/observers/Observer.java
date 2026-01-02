package com.digibank.observers;

/**
 * Observer interface for Observer Pattern
 * Observers are notified of events in the Smart City system
 */
public interface Observer {
    /**
     * Called when an event occurs
     * @param event The event that occurred
     */
    void update(Event event);

    /**
     * Get unique identifier for this observer
     * @return Observer ID
     */
    String getObserverId();
}
