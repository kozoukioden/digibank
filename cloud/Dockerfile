# DigiBank Smart City Application - Dockerfile
# Multi-stage build for optimized image size
#
# Author: HAKAN OĞUZ

# Stage 1: Build Java application
FROM maven:3.9-eclipse-temurin-17 AS builder

WORKDIR /build

# Copy source code
COPY src/ /build/src/
COPY pom.xml /build/

# Build application (skip tests for faster builds)
RUN mvn clean package -DskipTests

# Stage 2: Runtime image
FROM eclipse-temurin:17-jre-alpine

LABEL maintainer="ABDULKADER DABET <17030222008>"
LABEL description="Smart City DigiBank Application"
LABEL version="1.0"

WORKDIR /app

# Install Python for monitoring scripts
RUN apk add --no-cache python3 py3-pip bash

# Copy Java application from builder
COPY --from=builder /build/target/digibank-1.0.jar /app/digibank.jar

# Copy Python scripts
COPY scripts/ /app/scripts/
RUN pip3 install --no-cache-dir -r /app/scripts/requirements.txt

# Copy GUI files
COPY gui/ /app/gui/

# Create directories
RUN mkdir -p /app/txt /app/logs

# Expose port
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8080/health || exit 1

# Environment variables
ENV JAVA_OPTS="-Xms256m -Xmx512m"
ENV DB_HOST="localhost"
ENV DB_PORT="5432"
ENV DB_NAME="digibank_db"

# Run application
CMD ["sh", "-c", "java $JAVA_OPTS -jar digibank.jar"]
