package com.digibank;

import com.digibank.automation.*;
import com.digibank.banking.*;
import com.digibank.banking.adapters.CryptoAdapterFactory;
import com.digibank.commands.*;
import com.digibank.controllers.CityController;
import com.digibank.models.*;
import com.digibank.observers.*;
import com.digibank.security.*;

import java.math.BigDecimal;

/**
 * Main Demo Application for Smart City DigiBank System
 * Demonstrates all Design Patterns and Security Features
 *
 * @author HAKAN OĞUZ
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║   SMART CITY AUTOMATION WITH DIGITAL BANKING INTEGRATION    ║");
        System.out.println("║                                                              ║");
        System.out.println("║   Student: ABDULKADER DABET (17030222008)                   ║");
        System.out.println("║   Course: Bil 401 - Siber Güvenlik ve Büyük Veri            ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        Main demo = new Main();
        demo.runCompleteDemo();
    }

    public void runCompleteDemo() {
        // Initialize system components
        System.out.println("\n" + "=".repeat(60));
        System.out.println("INITIALIZING SYSTEM COMPONENTS");
        System.out.println("=".repeat(60));

        // 1. Get CityController (Singleton Pattern)
        CityController cityController = CityController.getInstance();

        // 2. Initialize Banking Service
        BankingService bankingService = new BankingService();

        // 3. Initialize Security Manager
        SecurityManager securityManager = new SecurityManager();

        System.out.println("\n✓ All system components initialized successfully\n");

        // Run demonstrations
        demonstrateObserverPattern(cityController, bankingService);
        demonstrateCommandPattern(cityController);
        demonstrateTemplateMethodPattern();
        demonstrateAdapterPattern(bankingService);
        demonstrateSecurityFeatures(securityManager);
        demonstrateBankingTransactions(bankingService, cityController);

        // Show final system status
        System.out.println("\n" + "=".repeat(60));
        System.out.println(cityController.getCityStatus());
        System.out.println("=".repeat(60));

        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                    DEMO COMPLETE                             ║");
        System.out.println("║                                                              ║");
        System.out.println("║  All Design Patterns successfully demonstrated:             ║");
        System.out.println("║   ✓ Singleton Pattern (CityController)                      ║");
        System.out.println("║   ✓ Command Pattern (Street Lights, Traffic, Payments)      ║");
        System.out.println("║   ✓ Observer Pattern (Security, Email, SMS Notifications)   ║");
        System.out.println("║   ✓ Adapter Pattern (Bitcoin, Ethereum, Fiat Payments)      ║");
        System.out.println("║   ✓ Template Method Pattern (City Automation Routines)      ║");
        System.out.println("║                                                              ║");
        System.out.println("║  All Security Features implemented:                          ║");
        System.out.println("║   ✓ Multi-Factor Authentication                             ║");
        System.out.println("║   ✓ Role-Based Access Control                               ║");
        System.out.println("║   ✓ Quantum-Resistant Encryption                            ║");
        System.out.println("║   ✓ DDoS Defense                                            ║");
        System.out.println("║   ✓ SQL Injection Protection                                ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");
    }

    // ==================== OBSERVER PATTERN DEMO ====================

    private void demonstrateObserverPattern(CityController cityController, BankingService bankingService) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("DEMONSTRATION 1: OBSERVER PATTERN");
        System.out.println("=".repeat(60) + "\n");

        // Create observers
        SecurityObserver securityObserver = new SecurityObserver("SEC-OBS-001");
        EmailObserver emailObserver = new EmailObserver("EMAIL-OBS-001", "admin@smartcity.gov");
        SMSObserver smsObserver = new SMSObserver("SMS-OBS-001", "+1-555-0100");

        // Register observers with CityController
        cityController.registerObserver(securityObserver);
        cityController.registerObserver(emailObserver);
        cityController.registerObserver(smsObserver);

        // Register observer with BankingService
        bankingService.registerObserver(emailObserver);

        System.out.println("\n→ Simulating security breach event...\n");

        // Create and trigger security event
        Event securityEvent = new Event(EventType.SECURITY_BREACH,
                                       Severity.CRITICAL,
                                       "Unauthorized access attempt detected");
        securityEvent.setSourceIP("192.168.1.50");

        cityController.notifyObservers(securityEvent);

        System.out.println("\n✓ Observer Pattern demonstration complete\n");
    }

    // ==================== COMMAND PATTERN DEMO ====================

    private void demonstrateCommandPattern(CityController cityController) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("DEMONSTRATION 2: COMMAND PATTERN");
        System.out.println("=".repeat(60) + "\n");

        // Get city infrastructure
        StreetLight mainStreetLight = cityController.getStreetLight("SL001");
        TrafficSignal mainIntersection = cityController.getTrafficSignal("TS001");

        // Create commands
        Command lightOn = new LightOnCommand(mainStreetLight);
        Command lightOff = new LightOffCommand(mainStreetLight);
        Command adjustTraffic = new AdjustTrafficCommand(mainIntersection,
                                                         TrafficSignal.TrafficMode.RUSH_HOUR);

        // Execute commands
        System.out.println("→ Executing commands...\n");
        cityController.executeCommand(lightOn);
        cityController.executeCommand(adjustTraffic);

        System.out.println("\n→ Undoing last command...\n");
        cityController.undoLastCommand();

        System.out.println("\n→ Executing light off command...\n");
        cityController.executeCommand(lightOff);

        System.out.println("\n✓ Command Pattern demonstration complete\n");
    }

    // ==================== TEMPLATE METHOD PATTERN DEMO ====================

    private void demonstrateTemplateMethodPattern() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("DEMONSTRATION 3: TEMPLATE METHOD PATTERN");
        System.out.println("=".repeat(60) + "\n");

        // Create and execute routines
        CityRoutine morningRoutine = new MorningRoutine();
        CityRoutine eveningRoutine = new EveningRoutine();
        CityRoutine securityCheck = new SecurityCheckRoutine();

        System.out.println("→ Executing Morning Routine...\n");
        morningRoutine.execute();

        System.out.println("\n→ Executing Evening Routine...\n");
        eveningRoutine.execute();

        System.out.println("\n→ Executing Security Check Routine...\n");
        securityCheck.execute();

        System.out.println("\n✓ Template Method Pattern demonstration complete\n");
    }

    // ==================== ADAPTER PATTERN DEMO ====================

    private void demonstrateAdapterPattern(BankingService bankingService) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("DEMONSTRATION 4: ADAPTER PATTERN");
        System.out.println("=".repeat(60) + "\n");

        System.out.println("→ Demonstrating cryptocurrency adapters...\n");

        // Test Bitcoin adapter
        System.out.println("1. Bitcoin Payment:");
        Transaction btcTransaction = new Transaction(1001, 2001,
                                                     new BigDecimal("0.005"),
                                                     "BTC",
                                                     TransactionType.PAYMENT);
        bankingService.processTransaction(btcTransaction);

        System.out.println("\n2. Ethereum Payment:");
        Transaction ethTransaction = new Transaction(1001, 2002,
                                                     new BigDecimal("0.1"),
                                                     "ETH",
                                                     TransactionType.PAYMENT);
        bankingService.processTransaction(ethTransaction);

        System.out.println("\n3. Fiat Currency Payment:");
        Transaction usdTransaction = new Transaction(1001, 2003,
                                                     new BigDecimal("100.00"),
                                                     "USD",
                                                     TransactionType.PAYMENT);
        bankingService.processTransaction(usdTransaction);

        System.out.println("\n✓ Adapter Pattern demonstration complete\n");
    }

    // ==================== SECURITY FEATURES DEMO ====================

    private void demonstrateSecurityFeatures(SecurityManager securityManager) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("DEMONSTRATION 5: SECURITY FEATURES");
        System.out.println("=".repeat(60) + "\n");

        // 1. Multi-Factor Authentication
        System.out.println("1. MULTI-FACTOR AUTHENTICATION:");
        demonstrateMFA(securityManager);

        // 2. Role-Based Access Control
        System.out.println("\n2. ROLE-BASED ACCESS CONTROL:");
        demonstrateRBAC(securityManager);

        // 3. Full security demonstration
        securityManager.demonstrateSecurityFeatures();

        System.out.println("\n✓ Security Features demonstration complete\n");
    }

    private void demonstrateMFA(SecurityManager securityManager) {
        // Create test user
        User testUser = new User(1, "admin", "admin@smartcity.gov",
                                "HASH_password123_SALT", Role.ADMIN);

        // Create credentials
        Credentials credentials = new Credentials("admin", "password123");
        credentials.setOTP("123456"); // Would be sent via email in production

        System.out.println("\n→ Authenticating user with MFA...");
        boolean authenticated = securityManager.authenticateUser(testUser, credentials);

        System.out.println("\nAuthentication result: " + (authenticated ? "✓ SUCCESS" : "✗ FAILED"));
    }

    private void demonstrateRBAC(SecurityManager securityManager) {
        User admin = new User(1, "admin", "admin@smartcity.gov",
                             "HASH_admin123_SALT", Role.ADMIN);

        User resident = new User(2, "john_doe", "john@email.com",
                                "HASH_john123_SALT", Role.RESIDENT);

        System.out.println("\n→ Testing access control...");

        // Admin should have access to system admin
        System.out.println("\n  Admin accessing SYSTEM_ADMIN:");
        securityManager.authorizeAccess(admin, "SYSTEM_ADMIN");

        // Resident should NOT have access to system admin
        System.out.println("\n  Resident accessing SYSTEM_ADMIN:");
        securityManager.authorizeAccess(resident, "SYSTEM_ADMIN");

        // Resident should have access to payments
        System.out.println("\n  Resident accessing PROCESS_PAYMENT:");
        securityManager.authorizeAccess(resident, "PROCESS_PAYMENT");
    }

    // ==================== INTEGRATED BANKING DEMO ====================

    private void demonstrateBankingTransactions(BankingService bankingService,
                                                CityController cityController) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("DEMONSTRATION 6: INTEGRATED BANKING WITH COMMAND PATTERN");
        System.out.println("=".repeat(60) + "\n");

        // Create transaction
        Transaction parkingPayment = new Transaction(1001, 9999,
                                                     new BigDecimal("5.50"),
                                                     "USD",
                                                     TransactionType.PAYMENT);

        // Create payment command
        ProcessPaymentCommand paymentCommand = new ProcessPaymentCommand(bankingService, parkingPayment);

        System.out.println("→ Executing parking payment via Command Pattern...\n");
        cityController.executeCommand(paymentCommand);

        System.out.println("\n✓ Integrated Banking demonstration complete\n");
    }
}
