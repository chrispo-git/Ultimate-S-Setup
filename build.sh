#!/usr/bin/env bash
# ─────────────────────────────────────────────────────────────
# build.sh — Ultimate S Setup portable executable builder
#
# Usage:
#   ./build.sh           → Linux (default)
#   ./build.sh linux     → UltimateSSetup-linux.tar.gz
#   ./build.sh windows   → UltimateSSetup-windows.zip  (must run ON Windows)
#   ./build.sh mac       → UltimateSSetup-mac.zip      (must run ON macOS)
#
# Extract the archive anywhere, place your ultimate/ folder next to
# the executable, then run it directly — no installation needed.
# ─────────────────────────────────────────────────────────────
set -euo pipefail

MVN="./mvnw"
if ! [ -x "$MVN" ]; then MVN="mvn"; fi

TARGET="${1:-linux}"

case "$TARGET" in
    linux)
        $MVN clean verify -P linux -DskipTests
        echo "Output: target/dist/out/UltimateSSetup-linux.tar.gz"
        ;;
    windows)
        $MVN clean verify -P windows -DskipTests
        echo "Output: target/dist/out/UltimateSSetup-windows.zip"
        ;;
    mac)
        $MVN clean verify -P mac -DskipTests
        echo "Output: target/dist/out/UltimateSSetup-mac.zip"
        ;;
    *)
        echo "Usage: $0 [linux|windows|mac]"
        exit 1
        ;;
esac