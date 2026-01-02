#!/usr/bin/env python3
"""
Email Sender Script - SMTP Email Automation
Standalone email sending utility for DigiBank notifications

Author: HAKAN OĞUZ
"""

import smtplib
import os
from email.mime.text import MIMEText
from email.mime.multipart import MIMEMultipart
from datetime import datetime


class DigiEmailSender:
    """
    Email sender for DigiBank notifications
    Supports multiple notification types
    """

    def __init__(self, smtp_server='smtp.gmail.com', smtp_port=587):
        self.smtp_server = smtp_server
        self.smtp_port = smtp_port

        # Load from environment variables (more secure)
        self.sender_email = os.getenv('DIGIBANK_EMAIL', 'digibank@smartcity.gov')
        self.sender_password = os.getenv('DIGIBANK_EMAIL_PASSWORD', 'demo_password')

        print(f"[DigiEmail] Email sender initialized")
        print(f"[DigiEmail] SMTP: {self.smtp_server}:{self.smtp_port}")

    def send_transaction_notification(self, recipient, transaction_id, amount, currency):
        """
        Send transaction notification email
        """
        subject = f"Transaction Completed - #{transaction_id}"

        body = f"""
Dear User,

Your transaction has been completed successfully.

Transaction Details:
- Transaction ID: {transaction_id}
- Amount: {amount} {currency}
- Status: COMPLETED
- Timestamp: {datetime.now().strftime('%Y-%m-%d %H:%M:%S')}

Thank you for using DigiBank!

Best regards,
Smart City DigiBank Team
        """

        return self._send_email(recipient, subject, body, "transaction")

    def send_security_alert(self, recipient, alert_type, description, source_ip):
        """
        Send security alert email
        """
        subject = f"SECURITY ALERT - {alert_type}"

        body = f"""
SECURITY ALERT

A security event has been detected in the Smart City system.

Alert Details:
- Type: {alert_type}
- Description: {description}
- Source IP: {source_ip}
- Timestamp: {datetime.now().strftime('%Y-%m-%d %H:%M:%S')}

This is an automated security notification.
Please review and take appropriate action if necessary.

Smart City Security Team
        """

        return self._send_email(recipient, subject, body, "security")

    def send_daily_report(self, recipient, metrics):
        """
        Send daily city report
        """
        subject = f"Daily City Report - {datetime.now().strftime('%Y-%m-%d')}"

        body = f"""
Smart City Daily Report
=======================

Date: {datetime.now().strftime('%Y-%m-%d')}

System Metrics:
- Active Users: {metrics.get('users', 0)}
- Transactions: {metrics.get('transactions', 0)}
- Security Events: {metrics.get('security_events', 0)}
- Street Lights Active: {metrics.get('lights', 0)}
- System Status: {metrics.get('status', 'OPERATIONAL')}

All systems operating normally.

Smart City Administration
        """

        return self._send_email(recipient, subject, body, "report")

    def _send_email(self, recipient, subject, body, email_type):
        """
        Internal method to send email
        """
        try:
            print(f"\n[DigiEmail] Sending {email_type} email...")

            message = MIMEMultipart()
            message['From'] = self.sender_email
            message['To'] = recipient
            message['Subject'] = subject

            message.attach(MIMEText(body, 'plain'))

            # Demo mode - log instead of actually sending
            print(f"[DigiEmail] ✓ Email prepared")
            print(f"[DigiEmail] To: {recipient}")
            print(f"[DigiEmail] Subject: {subject}")
            print(f"[DigiEmail] Type: {email_type}")

            # In production, uncomment to actually send:
            # with smtplib.SMTP(self.smtp_server, self.smtp_port) as server:
            #     server.starttls()
            #     server.login(self.sender_email, self.sender_password)
            #     server.send_message(message)

            print(f"[DigiEmail] Demo mode - Email logged but not sent")
            return True

        except Exception as e:
            print(f"[DigiEmail] ✗ Error: {str(e)}")
            return False


def demo():
    """
    Demonstrate email sending functionality
    """
    print("\n" + "="*60)
    print("DIGIBANK EMAIL SENDER - DEMO")
    print("="*60 + "\n")

    sender = DigiEmailSender()

    # Test transaction notification
    print("\n1. Transaction Notification:")
    sender.send_transaction_notification(
        recipient="user@example.com",
        transaction_id="TXN-123456",
        amount="0.005",
        currency="BTC"
    )

    # Test security alert
    print("\n2. Security Alert:")
    sender.send_security_alert(
        recipient="security@smartcity.gov",
        alert_type="DDoS ATTEMPT",
        description="Multiple failed login attempts detected",
        source_ip="192.168.1.100"
    )

    # Test daily report
    print("\n3. Daily Report:")
    metrics = {
        'users': 1250,
        'transactions': 3420,
        'security_events': 5,
        'lights': 245,
        'status': 'OPERATIONAL'
    }
    sender.send_daily_report(
        recipient="admin@smartcity.gov",
        metrics=metrics
    )

    print("\n" + "="*60)
    print("DEMO COMPLETE")
    print("="*60 + "\n")


if __name__ == "__main__":
    demo()
