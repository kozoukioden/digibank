#!/usr/bin/env python3
"""
File Monitor Script - Observer Pattern Implementation
Monitors ./txt directory for new user export files
Automatically sends email when new file is created

Author: HAKAN OĞUZ
"""

import os
import sys
import time
from datetime import datetime
from pathlib import Path
import smtplib
from email.mime.text import MIMEText
from email.mime.multipart import MIMEMultipart
from email.mime.base import MIMEBase
from email import encoders

# Add watchdog for file system monitoring
try:
    from watchdog.observers import Observer
    from watchdog.events import FileSystemEventHandler
except ImportError:
    print("Installing watchdog library...")
    import subprocess
    subprocess.check_call([sys.executable, "-m", "pip", "install", "watchdog"])
    from watchdog.observers import Observer
    from watchdog.events import FileSystemEventHandler


class TxtFileHandler(FileSystemEventHandler):
    """
    File system event handler for .txt files
    Implements Observer Pattern - observes file system events
    """

    def __init__(self, email_sender):
        super().__init__()
        self.email_sender = email_sender
        print(f"[File Monitor] Handler initialized")
        print(f"[File Monitor] Watching for .txt file creation events")

    def on_created(self, event):
        """Called when a file is created"""
        if event.is_directory:
            return

        if event.src_path.endswith('.txt'):
            filename = os.path.basename(event.src_path)
            print(f"\n{'='*60}")
            print(f"[File Monitor] ✓ NEW FILE DETECTED!")
            print(f"[File Monitor] File: {filename}")
            print(f"[File Monitor] Path: {event.src_path}")
            print(f"[File Monitor] Time: {datetime.now().strftime('%Y-%m-%d %H:%M:%S')}")
            print(f"{'='*60}\n")

            # Send email notification
            self.email_sender.send_email_with_attachment(event.src_path)


class EmailSender:
    """
    Email sender for file notifications
    Sends email when new .txt file is created
    """

    def __init__(self, smtp_server='smtp.gmail.com', smtp_port=587):
        self.smtp_server = smtp_server
        self.smtp_port = smtp_port

        # Email configuration (user should set these)
        self.sender_email = os.getenv('DIGIBANK_EMAIL', 'digibank@smartcity.gov')
        self.sender_password = os.getenv('DIGIBANK_EMAIL_PASSWORD', 'demo_password')
        self.recipient_email = os.getenv('DIGIBANK_RECIPIENT', 'admin@smartcity.gov')

        print(f"[Email Sender] Initialized")
        print(f"[Email Sender] SMTP Server: {self.smtp_server}:{self.smtp_port}")
        print(f"[Email Sender] Sender: {self.sender_email}")
        print(f"[Email Sender] Recipient: {self.recipient_email}")

    def send_email_with_attachment(self, file_path):
        """
        Send email with file attachment
        """
        try:
            print(f"\n[Email Sender] Preparing email...")

            # Create message
            message = MIMEMultipart()
            message['From'] = self.sender_email
            message['To'] = self.recipient_email
            message['Subject'] = f"DigiBank Export: {datetime.now().strftime('%Y-%m-%d %H:%M:%S')}"

            # Email body
            body = self._create_email_body(file_path)
            message.attach(MIMEText(body, 'plain'))

            # Attach file
            self._attach_file(message, file_path)

            # Send email
            self._send_email(message)

            print(f"[Email Sender] ✓ Email sent successfully!")
            print(f"[Email Sender] To: {self.recipient_email}")
            print(f"[Email Sender] Attachment: {os.path.basename(file_path)}")

        except Exception as e:
            print(f"[Email Sender] ✗ Error sending email: {str(e)}")
            print(f"[Email Sender] Note: For demo, email is logged but not actually sent")
            self._log_email(file_path)

    def _create_email_body(self, file_path):
        """Create email body content"""
        filename = os.path.basename(file_path)
        file_size = os.path.getsize(file_path)
        timestamp = datetime.now().strftime('%Y-%m-%d %H:%M:%S')

        body = f"""
DigiBank User Export Notification
==================================

A new user export file has been created.

File Details:
- Filename: {filename}
- Size: {file_size} bytes
- Timestamp: {timestamp}
- Path: {file_path}

This is an automated notification from the DigiBank file monitoring system.
The exported file is attached to this email.

---
Smart City DigiBank System
Observer Pattern - Real-time File Monitoring
Student: ABDULKADER DABET (17030222008)
        """
        return body

    def _attach_file(self, message, file_path):
        """Attach file to email message"""
        filename = os.path.basename(file_path)

        with open(file_path, 'rb') as attachment:
            part = MIMEBase('application', 'octet-stream')
            part.set_payload(attachment.read())

        encoders.encode_base64(part)
        part.add_header('Content-Disposition', f'attachment; filename={filename}')
        message.attach(part)

    def _send_email(self, message):
        """Send email via SMTP"""
        # For demo purposes, we'll simulate sending
        # In production, would use actual SMTP credentials

        print(f"[Email Sender] Connecting to SMTP server...")
        print(f"[Email Sender] Demo mode - Email would be sent here")

        # Uncomment for actual email sending:
        # with smtplib.SMTP(self.smtp_server, self.smtp_port) as server:
        #     server.starttls()
        #     server.login(self.sender_email, self.sender_password)
        #     server.send_message(message)

    def _log_email(self, file_path):
        """Log email details to console (demo mode)"""
        print(f"\n{'='*60}")
        print(f"EMAIL NOTIFICATION (Demo Mode)")
        print(f"{'='*60}")
        print(f"From: {self.sender_email}")
        print(f"To: {self.recipient_email}")
        print(f"Subject: DigiBank Export: {datetime.now().strftime('%Y-%m-%d %H:%M:%S')}")
        print(f"Attachment: {os.path.basename(file_path)}")
        print(f"File Size: {os.path.getsize(file_path)} bytes")
        print(f"{'='*60}\n")


def setup_monitoring_directory():
    """Create monitoring directory if it doesn't exist"""
    # Get script directory
    script_dir = Path(__file__).parent.parent
    txt_dir = script_dir / 'txt'

    # Create directory if it doesn't exist
    txt_dir.mkdir(exist_ok=True)

    return str(txt_dir)


def main():
    """
    Main function - starts file monitoring
    """
    print("\n" + "="*60)
    print("DIGIBANK FILE MONITOR - OBSERVER PATTERN")
    print("="*60)
    print(f"Student: ABDULKADER DABET (17030222008)")
    print(f"Started: {datetime.now().strftime('%Y-%m-%d %H:%M:%S')}")
    print("="*60 + "\n")

    # Setup monitoring directory
    watch_directory = setup_monitoring_directory()
    print(f"[File Monitor] Watching directory: {watch_directory}")

    # Initialize email sender
    email_sender = EmailSender()

    # Create event handler
    event_handler = TxtFileHandler(email_sender)

    # Create observer
    observer = Observer()
    observer.schedule(event_handler, watch_directory, recursive=False)

    # Start monitoring
    observer.start()
    print(f"\n[File Monitor] ✓ Monitoring started")
    print(f"[File Monitor] Press Ctrl+C to stop\n")

    try:
        while True:
            time.sleep(1)
    except KeyboardInterrupt:
        print(f"\n[File Monitor] Stopping...")
        observer.stop()

    observer.join()
    print(f"[File Monitor] ✓ Stopped")


if __name__ == "__main__":
    main()
