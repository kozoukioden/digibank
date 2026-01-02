package com.digibank.automation;

/**
 * Morning Routine - Concrete implementation of Template Method Pattern
 * Automated tasks executed every morning in the Smart City
 */
public class MorningRoutine extends CityRoutine {

    @Override
    protected void performMainTask() {
        System.out.println("[Morning Routine] Starting morning operations...\n");

        turnOffStreetLights();
        adjustTrafficSignals();
        checkPublicTransport();
        sendDailyReport();

        System.out.println("\n[Morning Routine] Morning operations completed");
    }

    private void turnOffStreetLights() {
        System.out.println("→ Turning OFF street lights (sunrise detected)");
        System.out.println("  • Main Street lights: OFF");
        System.out.println("  • Park Avenue lights: OFF");
        System.out.println("  • City Center lights: OFF");
    }

    private void adjustTrafficSignals() {
        System.out.println("\n→ Adjusting traffic signals to RUSH HOUR mode");
        System.out.println("  • Main Intersection: RUSH_HOUR mode");
        System.out.println("  • Highway Exit: RUSH_HOUR mode");
    }

    private void checkPublicTransport() {
        System.out.println("\n→ Checking public transport systems");
        System.out.println("  • Bus network: OPERATIONAL");
        System.out.println("  • Metro system: OPERATIONAL");
        System.out.println("  • All systems running on schedule");
    }

    private void sendDailyReport() {
        System.out.println("\n→ Generating and sending daily report");
        System.out.println("  • Energy consumption: NORMAL");
        System.out.println("  • Security status: ALL CLEAR");
        System.out.println("  • Report sent to city authorities");
    }

    @Override
    protected String getRoutineName() {
        return "Morning City Activation Routine";
    }
}
