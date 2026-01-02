#!/bin/bash

###############################################################################
# Google Cloud Platform Deployment Script for DigiBank
# Deploys Smart City DigiBank application to GCP
#
# Author: HAKAN OĞUZ
###############################################################################

set -e  # Exit on error

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Configuration
PROJECT_ID="digibank-smart-city"
REGION="us-central1"
APP_NAME="digibank-app"
DB_INSTANCE="digibank-db"
DB_VERSION="POSTGRES_15"
DB_TIER="db-f1-micro"

echo "=========================================="
echo "DigiBank GCP Deployment Script"
echo "=========================================="
echo "Project: $PROJECT_ID"
echo "Region: $REGION"
echo "=========================================="
echo ""

# Function to print colored output
print_status() {
    echo -e "${GREEN}[✓]${NC} $1"
}

print_error() {
    echo -e "${RED}[✗]${NC} $1"
}

print_warning() {
    echo -e "${YELLOW}[!]${NC} $1"
}

# Step 1: Check if gcloud is installed
echo "Step 1: Checking gcloud installation..."
if ! command -v gcloud &> /dev/null; then
    print_error "gcloud CLI not found. Please install Google Cloud SDK first."
    exit 1
fi
print_status "gcloud CLI found"

# Step 2: Authenticate
echo ""
echo "Step 2: Authenticating with Google Cloud..."
print_warning "Please login with your Google account"
gcloud auth login

print_status "Authentication successful"

# Step 3: Set project
echo ""
echo "Step 3: Setting up GCP project..."
gcloud config set project $PROJECT_ID 2>/dev/null || {
    print_warning "Project $PROJECT_ID not found. Creating new project..."
    gcloud projects create $PROJECT_ID --name="DigiBank Smart City"
    gcloud config set project $PROJECT_ID
}
print_status "Project set to: $PROJECT_ID"

# Step 4: Enable required APIs
echo ""
echo "Step 4: Enabling required APIs..."
gcloud services enable cloudbuild.googleapis.com
gcloud services enable run.googleapis.com
gcloud services enable sqladmin.googleapis.com
print_status "Required APIs enabled"

# Step 5: Build Docker image
echo ""
echo "Step 5: Building Docker image..."
cd "$(dirname "$0")/.."  # Go to project root

if [ ! -f "Dockerfile" ]; then
    print_error "Dockerfile not found in project root"
    exit 1
fi

print_status "Building image with Cloud Build..."
gcloud builds submit --tag gcr.io/$PROJECT_ID/$APP_NAME

print_status "Docker image built successfully"

# Step 6: Create Cloud SQL instance
echo ""
echo "Step 6: Setting up Cloud SQL database..."

# Check if instance exists
if gcloud sql instances describe $DB_INSTANCE &> /dev/null; then
    print_warning "Database instance $DB_INSTANCE already exists, skipping creation"
else
    print_status "Creating Cloud SQL instance..."
    gcloud sql instances create $DB_INSTANCE \
        --database-version=$DB_VERSION \
        --tier=$DB_TIER \
        --region=$REGION \
        --root-password="DigiBank2024!"

    print_status "Creating database..."
    gcloud sql databases create digibank_db --instance=$DB_INSTANCE

    print_status "Database created successfully"
fi

# Get database connection info
DB_CONNECTION=$(gcloud sql instances describe $DB_INSTANCE --format="value(connectionName)")
print_status "Database connection: $DB_CONNECTION"

# Step 7: Deploy to Cloud Run
echo ""
echo "Step 7: Deploying to Cloud Run..."

gcloud run deploy $APP_NAME \
    --image gcr.io/$PROJECT_ID/$APP_NAME \
    --platform managed \
    --region $REGION \
    --allow-unauthenticated \
    --set-env-vars DB_CONNECTION=$DB_CONNECTION \
    --add-cloudsql-instances $DB_CONNECTION \
    --memory 512Mi \
    --cpu 1

print_status "Application deployed successfully"

# Step 8: Get service URL
echo ""
echo "Step 8: Retrieving service information..."
SERVICE_URL=$(gcloud run services describe $APP_NAME --region $REGION --format="value(status.url)")

print_status "Deployment complete!"
echo ""
echo "=========================================="
echo "Deployment Summary"
echo "=========================================="
echo "Service URL: $SERVICE_URL"
echo "Database: $DB_INSTANCE ($DB_CONNECTION)"
echo "Region: $REGION"
echo "=========================================="
echo ""
echo "Next steps:"
echo "1. Visit your application: $SERVICE_URL"
echo "2. Configure database schema"
echo "3. Set up monitoring in GCP Console"
echo ""
print_warning "Don't forget to update your environment variables!"
print_warning "To avoid charges, remember to shut down resources when not in use:"
echo "  gcloud run services delete $APP_NAME --region $REGION"
echo "  gcloud sql instances delete $DB_INSTANCE"
echo ""
