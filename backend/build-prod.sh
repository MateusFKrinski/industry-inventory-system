#!/usr/bin/env bash
set -e

echo "Building application for production..."
./mvnw clean package -DskipTests
echo "Build finished successfully."
echo "Artifact: target/quarkus-app/quarkus-app.jar"
