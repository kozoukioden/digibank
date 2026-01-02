# DigiBank Smart City System

A secure digital banking platform designed for smart cities, featuring advanced security mechanisms, design patterns, and cryptocurrency payment support.

## Features

### Security Features
- **Multi-Factor Authentication (MFA)**: 3-factor authentication (password, OTP, biometric)
- **Role-Based Access Control (RBAC)**: Granular permission management
- **DDoS Defense**: Rate limiting and IP blacklisting
- **SQL Injection Protection**: PreparedStatement-based queries with input sanitization
- **Quantum-Resistant Encryption**: Lattice-based cryptographic algorithms
- **Security Event Monitoring**: Real-time threat detection and logging

### Design Patterns
- **Singleton Pattern**: Thread-safe city controller with double-checked locking
- **Command Pattern**: Undo/redo support for city automation tasks
- **Observer Pattern**: Real-time event notification system
- **Adapter Pattern**: Cryptocurrency payment integration (Bitcoin, Ethereum)
- **Template Method Pattern**: Standardized city automation routines

### Banking Features
- Multi-currency transaction support
- Cryptocurrency payments (Bitcoin, Ethereum)
- Fiat payment processing
- Transaction history and audit trail
- Real-time balance updates

### Smart City Integration
- Automated traffic signal control
- Street light management
- City infrastructure monitoring
- Resident portal for city services
- Admin dashboard for city management

## Technology Stack

- **Backend**: Java 17
- **Build Tool**: Maven
- **Database**: PostgreSQL
- **Deployment**: Docker, Docker Compose, Google Cloud Platform
- **Web**: HTML5, CSS3, JavaScript
- **Monitoring**: Python 3 scripts
- **Security Testing**: Kali Linux tools (nmap, nikto, hping3, sqlmap)
- **Documentation**: LaTeX (IEEE format)

## Project Structure

```
digibank/
├── src/main/java/com/digibank/     # Java source code
│   ├── automation/                  # City automation routines
│   ├── banking/                     # Banking services
│   ├── commands/                    # Command pattern implementation
│   ├── controllers/                 # Singleton controller
│   ├── models/                      # Data models
│   ├── observers/                   # Observer pattern implementation
│   └── security/                    # Security modules
├── gui/                             # Web interface
├── cloud/                           # Docker and deployment files
├── kali/                            # Security testing scripts
├── diagrams/                        # UML diagrams (PlantUML)
├── scripts/                         # Python monitoring scripts
└── paper/                           # Research paper (LaTeX)
```

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6+
- Docker and Docker Compose (for deployment)
- PostgreSQL 15+ (or use Docker)

### Running Locally with Docker

```bash
# Clone the repository
git clone https://github.com/kozoukioden/digibank.git
cd digibank

# Build and run with Docker Compose
cd cloud
docker-compose up -d

# Access the application
# Web interface: http://localhost:8080
# Admin dashboard: http://localhost:8080/gui/admin_dashboard.html
```

### Running with Maven

```bash
# Build the project
mvn clean package

# Run the application
java -jar target/digibank-1.0.jar

# Access at http://localhost:8080
```

## Web Interfaces

- **Main Page**: `http://localhost:8080`
- **Banking Interface**: `http://localhost:8080/gui/banking.html`
- **Admin Dashboard**: `http://localhost:8080/gui/admin_dashboard.html`
- **Resident Portal**: `http://localhost:8080/gui/resident_portal.html`

## Security Testing

Security testing scripts are located in the `kali/` directory. These scripts should only be used on your own systems or with explicit permission.

```bash
cd kali

# DDoS attack simulation
./ddos_attack.sh

# SQL injection testing
./sql_injection_test.sh

# Comprehensive vulnerability scan
./vulnerability_scan.sh
```

## Cloud Deployment

Deploy to Google Cloud Platform:

```bash
cd scripts
./gcloud_deploy.sh
```

Make sure to configure your GCP project ID and credentials before deployment.

## Documentation

- **UML Diagrams**: See `diagrams/` directory
- **Research Paper**: See `paper/digibank_security_analysis.tex`
- **API Documentation**: JavaDoc comments in source code
- **Security Testing Guide**: See `kali/attack_scenarios.md`

## Design Patterns Implementation

### Singleton Pattern
The `CityController` class uses thread-safe double-checked locking to ensure only one instance manages the entire smart city infrastructure.

### Command Pattern
City automation tasks (traffic control, lighting) are implemented as commands with full undo/redo support through the `CommandInvoker`.

### Observer Pattern
The security event system uses observers to notify multiple channels (email, SMS, security logs) when threats are detected.

### Adapter Pattern
Cryptocurrency payment processors (Bitcoin, Ethereum) are integrated through adapters, allowing seamless switching between payment methods.

### Template Method Pattern
City automation routines (`MorningRoutine`, `EveningRoutine`, `SecurityCheckRoutine`) follow a standard template for consistent execution.

## Security Architecture

1. **Authentication Layer**: MFA with password, OTP, and biometric verification
2. **Authorization Layer**: RBAC with fine-grained permissions
3. **Network Layer**: DDoS defense with rate limiting and IP blacklisting
4. **Application Layer**: SQL injection protection and input sanitization
5. **Data Layer**: Quantum-resistant encryption for sensitive data
6. **Monitoring Layer**: Real-time security event tracking and alerts

## License

This project is licensed under the MIT License.

## Author

**Hakan OĞUZ**
- Email: oguzhakan94@gmail.com
- GitHub: [@kozoukioden](https://github.com/kozoukioden)

## Acknowledgments

Built with [Claude Code](https://claude.com/claude-code)
