#!/bin/bash

###############################################################################
# SQL Injection Testing Script for DigiBank
# Tests PreparedStatement, input sanitization, and SQL injection protection
#
# Author: HAKAN OĞUZ
#
# WARNING: Only use on your own systems or with explicit permission!
###############################################################################

# Colors
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m'

# Configuration
TARGET_URL="${1:-http://localhost:8080/api/login}"

echo "=============================================="
echo "DigiBank SQL Injection Testing"
echo "=============================================="
echo "Target: $TARGET_URL"
echo "Date: $(date)"
echo "=============================================="
echo ""

echo -e "${YELLOW}[!] WARNING: This is for TESTING PURPOSES ONLY${NC}"
echo -e "${YELLOW}[!] Only run this against your own deployed application${NC}"
echo ""

read -p "Continue? (yes/no): " confirm
if [ "$confirm" != "yes" ]; then
    echo "Aborted."
    exit 0
fi

echo ""
echo "=============================================="
echo "Test 1: Classic SQL Injection"
echo "=============================================="
echo ""
echo -e "${GREEN}[*] Testing: admin' OR '1'='1${NC}"
curl -X POST $TARGET_URL \
  -d "username=admin' OR '1'='1&password=anything" \
  -v
echo -e "\n"

echo "=============================================="
echo "Test 2: Comment-Based Injection"
echo "=============================================="
echo ""
echo -e "${GREEN}[*] Testing: admin'--${NC}"
curl -X POST $TARGET_URL \
  -d "username=admin'--&password=anything" \
  -v
echo -e "\n"

echo "=============================================="
echo "Test 3: Union-Based Injection"
echo "=============================================="
echo ""
echo -e "${GREEN}[*] Testing: 1' UNION SELECT * FROM users--${NC}"
curl -X POST $TARGET_URL \
  -d "username=1' UNION SELECT * FROM users--&password=test" \
  -v
echo -e "\n"

echo "=============================================="
echo "Test 4: DROP TABLE Attack"
echo "=============================================="
echo ""
echo -e "${GREEN}[*] Testing: '; DROP TABLE users; --${NC}"
curl -X POST $TARGET_URL \
  -d "username='; DROP TABLE users; --&password=test" \
  -v
echo -e "\n"

echo "=============================================="
echo "Test 5: Boolean-Based Blind Injection"
echo "=============================================="
echo ""
echo -e "${GREEN}[*] Testing: admin' AND '1'='1${NC}"
curl -X POST $TARGET_URL \
  -d "username=admin' AND '1'='1&password=test" \
  -v
echo -e "\n"

# SQLMap automated testing
echo "=============================================="
echo "Test 6: Automated Testing with SQLMap"
echo "=============================================="
echo ""

# Check if sqlmap is installed
if ! command -v sqlmap &> /dev/null; then
    echo -e "${RED}[✗] sqlmap not found. Installing...${NC}"
    sudo apt-get update && sudo apt-get install -y sqlmap
fi

echo -e "${GREEN}[*] Running SQLMap comprehensive scan...${NC}"
echo ""

sqlmap -u "$TARGET_URL" \
       --data "username=test&password=test" \
       --batch \
       --level=3 \
       --risk=2 \
       --output-dir=sqlmap_results

echo ""
echo "=============================================="
echo "Test Summary"
echo "=============================================="
echo ""
echo "SQL Injection Payloads Tested:"
echo "  1. Classic: admin' OR '1'='1"
echo "  2. Comment-based: admin'--"
echo "  3. Union-based: UNION SELECT"
echo "  4. Destructive: DROP TABLE"
echo "  5. Boolean blind: AND '1'='1"
echo "  6. Automated: SQLMap full scan"
echo ""
echo -e "${YELLOW}Expected Defense Response:${NC}"
echo "  ✓ PreparedStatement blocks SQL injection"
echo "  ✓ Input sanitization removes quotes"
echo "  ✓ SQL keywords detected and blocked"
echo "  ✓ Dangerous patterns rejected"
echo "  ✓ Attack attempts logged"
echo ""
echo -e "${GREEN}[✓] SQL Injection testing complete!${NC}"
echo ""
echo "Next steps:"
echo "  1. Check DigiBank logs for blocked attempts"
echo "  2. Verify input sanitization"
echo "  3. Review SQLMap results in: sqlmap_results/"
echo "  4. Take screenshots for report"
echo ""
echo "=============================================="
