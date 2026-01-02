-- DigiBank Database Initialization Script
-- Creates all necessary tables for Smart City system
--
-- Author: HAKAN OĞUZ

-- Users Table
CREATE TABLE IF NOT EXISTS users (
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(20) DEFAULT 'RESIDENT',
    mfa_enabled BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_role CHECK (role IN ('ADMIN', 'CITY_MANAGER', 'RESIDENT', 'PUBLIC_SAFETY', 'UTILITY_WORKER'))
);

-- Accounts Table
CREATE TABLE IF NOT EXISTS accounts (
    account_id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES users(user_id) ON DELETE CASCADE,
    account_type VARCHAR(10) NOT NULL,
    currency VARCHAR(10) NOT NULL,
    balance DECIMAL(18, 8) DEFAULT 0.00,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_account_type CHECK (account_type IN ('FIAT', 'CRYPTO')),
    CONSTRAINT chk_balance CHECK (balance >= 0)
);

-- Transactions Table
CREATE TABLE IF NOT EXISTS transactions (
    transaction_id SERIAL PRIMARY KEY,
    from_account INTEGER REFERENCES accounts(account_id),
    to_account INTEGER REFERENCES accounts(account_id),
    amount DECIMAL(18, 8) NOT NULL,
    currency VARCHAR(10) NOT NULL,
    transaction_type VARCHAR(20) NOT NULL,
    status VARCHAR(20) DEFAULT 'PENDING',
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_transaction_type CHECK (transaction_type IN ('PAYMENT', 'TRANSFER', 'DEPOSIT', 'WITHDRAWAL')),
    CONSTRAINT chk_status CHECK (status IN ('PENDING', 'COMPLETED', 'FAILED')),
    CONSTRAINT chk_amount CHECK (amount > 0)
);

-- IoT Sensor Data Table
CREATE TABLE IF NOT EXISTS sensor_data (
    sensor_id SERIAL PRIMARY KEY,
    sensor_type VARCHAR(20) NOT NULL,
    location VARCHAR(100),
    reading_value DECIMAL(10, 2),
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_sensor_type CHECK (sensor_type IN ('TRAFFIC', 'ENVIRONMENTAL', 'SECURITY'))
);

-- Security Events Table
CREATE TABLE IF NOT EXISTS security_events (
    event_id SERIAL PRIMARY KEY,
    event_type VARCHAR(30) NOT NULL,
    severity VARCHAR(10) NOT NULL,
    description TEXT,
    source_ip VARCHAR(45),
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_event_type CHECK (event_type IN ('BREACH', 'ALERT', 'DDOS_ATTEMPT', 'SQL_INJECTION')),
    CONSTRAINT chk_severity CHECK (severity IN ('LOW', 'MEDIUM', 'HIGH', 'CRITICAL'))
);

-- City Devices Table (Street Lights, Traffic Signals)
CREATE TABLE IF NOT EXISTS city_devices (
    device_id VARCHAR(20) PRIMARY KEY,
    device_type VARCHAR(20) NOT NULL,
    location VARCHAR(100),
    status VARCHAR(20) DEFAULT 'ACTIVE',
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_device_type CHECK (device_type IN ('STREET_LIGHT', 'TRAFFIC_SIGNAL', 'CAMERA', 'SENSOR'))
);

-- Create indexes for better performance
CREATE INDEX idx_users_username ON users(username);
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_transactions_from_account ON transactions(from_account);
CREATE INDEX idx_transactions_to_account ON transactions(to_account);
CREATE INDEX idx_transactions_timestamp ON transactions(timestamp);
CREATE INDEX idx_security_events_timestamp ON security_events(timestamp);
CREATE INDEX idx_security_events_severity ON security_events(severity);
CREATE INDEX idx_sensor_data_timestamp ON sensor_data(timestamp);

-- Insert sample data

-- Sample users
INSERT INTO users (username, email, password_hash, role, mfa_enabled) VALUES
('admin', 'admin@smartcity.gov', 'HASH_admin123_SALT', 'ADMIN', TRUE),
('john_doe', 'john@email.com', 'HASH_john123_SALT', 'RESIDENT', TRUE),
('city_manager', 'manager@smartcity.gov', 'HASH_manager123_SALT', 'CITY_MANAGER', TRUE)
ON CONFLICT (username) DO NOTHING;

-- Sample accounts
INSERT INTO accounts (user_id, account_type, currency, balance) VALUES
(1, 'FIAT', 'USD', 10000.00),
(1, 'CRYPTO', 'BTC', 0.5),
(2, 'FIAT', 'USD', 5000.00),
(2, 'CRYPTO', 'ETH', 2.0),
(3, 'FIAT', 'USD', 7500.00)
ON CONFLICT DO NOTHING;

-- Sample city devices
INSERT INTO city_devices (device_id, device_type, location, status) VALUES
('SL001', 'STREET_LIGHT', 'Main Street', 'ACTIVE'),
('SL002', 'STREET_LIGHT', 'Park Avenue', 'ACTIVE'),
('SL003', 'STREET_LIGHT', 'City Center', 'ACTIVE'),
('TS001', 'TRAFFIC_SIGNAL', 'Main Intersection', 'ACTIVE'),
('TS002', 'TRAFFIC_SIGNAL', 'Highway Exit', 'ACTIVE')
ON CONFLICT (device_id) DO NOTHING;

-- Grant permissions
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO digibank;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO digibank;

-- Success message
DO $$
BEGIN
    RAISE NOTICE 'DigiBank database initialized successfully!';
    RAISE NOTICE 'Sample users created: admin, john_doe, city_manager';
    RAISE NOTICE 'Default password format: HASH_<username>123_SALT';
END $$;
