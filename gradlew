#!/usr/bin/env sh

set -e

# Gradle wrapper script for Unix
cd "$(dirname "$0")"

GRADLE_DIST="gradle-9.5"
GRADLE_DIR="$HOME/.gradle/wrapper/dists/${GRADLE_DIST}-bin"

mkdir -p "$(dirname "$GRADLE_DIR")"

if [ ! -d "$GRADLE_DIR" ]; then
    curl -fsSL "https://services.gradle.org/distributions/${GRADLE_DIST}-bin.zip" -o /tmp/gradle.zip
    unzip -q /tmp/gradle.zip -d "$(dirname "$GRADLE_DIR")"
    mv "$(dirname "$GRADLE_DIR")/gradle-${GRADLE_DIST}" "$GRADLE_DIR" 2>/dev/null || true
fi

GRADLE_HOME="$GRADLE_DIR"
exec "$GRADLE_HOME/bin/gradle" "$@"