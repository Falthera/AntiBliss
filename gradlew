#!/usr/bin/env sh

set -e

# Download and extract Gradle if needed
GRADLE_VERSION="8.14"
GRADLE_DIR="$HOME/.gradle/wrapper/dists/gradle-$GRADLE_VERSION-bin"

if [ ! -d "$GRADLE_DIR" ]; then
  mkdir -p "$(dirname "$GRADLE_DIR")"
  curl -fsSL "https://services.gradle.org/distributions/gradle-$GRADLE_VERSION-bin.zip" -o /tmp/gradle.zip
  unzip -q /tmp/gradle.zip -d "$(dirname "$GRADLE_DIR")"
fi

GRADLE_HOME=$(find "$GRADLE_DIR" -name "gradle-$GRADLE_VERSION" -type d | head -n1)
exec "$GRADLE_HOME/bin/gradle" "$@"