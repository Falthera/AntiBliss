#!/usr/bin/env sh

set -e

# Gradle wrapper script for Unix

GRADLE_DIST="gradle-9.6.1-bin"
GRADLE_DIR="$HOME/.gradle/wrapper/dists/$GRADLE_DIST"

if [ ! -d "$GRADLE_DIR" ]; then
    mkdir -p "$(dirname "$GRADLE_DIR")"
    curl -fsSL "https://services.gradle.org/distributions/$GRADLE_DIST.zip" -o /tmp/gradle.zip
    unzip -q /tmp/gradle.zip -d "$(dirname "$GRADLE_DIR")"
fi

GRADLE_HOME=$(find "$GRADLE_DIR" -name "$GRADLE_DIST" -type d | head -n1)
exec "$GRADLE_HOME/bin/gradle" "$@"