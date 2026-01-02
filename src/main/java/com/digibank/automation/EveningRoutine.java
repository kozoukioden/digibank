package com.digibank.automation;

/**
 * Evening Routine - Concrete implementation of Template Method Pattern
 * Automated tasks executed every evening in the Smart City
 */
public class EveningRoutine extends CityRoutine {

    @Override
    protected void performMainTask() {
        System.out.println("[Evening Routine] Starting evening operations...\n");

        turnOnStreetLights();
        increaseSecurity();
        activateNightMode();
        generateEveningReport();

        System.out.println("\n[Evening Routine] Evening operations completed");
    }

    private void turnOnStreetLights() {
        System.out.println("→ Turning ON street lights (sunset detected)");
        System.out.println("  • Main Street lights: ON");
        System.out.println("  • Park Avenue lights: ON");
        System.out.println("  • City Center lights: ON");
    }

    private void increaseSecurity() {
        System.out.println("\n→ Increasing security measures");
        System.out.println("  • Activating surveillance cameras");
        System.out.println("  • Enabling motion detectors");
        System.out.println("  • Notifying security personnel");
    }

    private void activateNightMode() {
        System.out.println("\n→ Activating night mode for city systems");
        System.out.println("  • Traffic signals: NIGHT mode");
        System.out.println("  • Reducing non-essential services");
        System.out.println("  • Energy-saving mode ENABLED");
    }

    private void generateEveningReport() {
        System.out.println("\n→ Generating evening summary");
        System.out.println("  • Daily traffic analysis complete");
        System.out.println("  • Utility usage logged");
        System.out.println("  • Security incidents: 0");
    }

    @Override
    protected String getRoutineName() {
        return "Evening City Night Mode Routine";
    }
}
