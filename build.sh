#!/bin/bash

# Define variables
PROJECT_NAME="f1database"
JAR_FILE="target/${PROJECT_NAME}-1.0-SNAPSHOT.jar"

echo "Building the project..."
mvn clean package

if [ $? -ne 0 ]; then
    echo "Build failed! Exiting..."
    exit 1
fi

echo "Running the project..."
java -jar "$JAR_FILE"
