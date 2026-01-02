package com.digibank.observers;

/**
 * Subject interface for Observer Pattern
 * Subjects maintain a list of observers and notify them of changes
 */
public interface Subject {
    /**
     * Register an observer
     * @param observer Observer to register
     */
    void registerObserver(Observer observer);

    /**
     * Remove an observer
     * @param observer Observer to remove
     */
    void removeObserver(Observer observer);

    /**
     * Notify all observers of an event
     * @param event Event to notify about
     */
    void notifyObservers(Event event);
}
