#!/usr/bin/env bash
# ─────────────────────────────────────────────────────────────
# build.sh — Ultimate S Setup native package builder
#
# Usage:
#   ./build.sh           → Linux packages (.deb + .rpm)
#   ./build.sh linux     → Linux packages (.deb + .rpm)
#   ./build.sh windows   → Windows installer (.exe) — must run ON Windows
#   ./build.sh mac       → macOS disk image (.dmg)   — must run ON macOS
#   ./build.sh all       → build all three profiles at once
#
# Prerequisites:
#   • JDK 21+ (jpackage is included with the JDK)
#   • Maven 3.6+ (or use ./mvnw which is bundled)
#   • For .deb: fakeroot + dpkg packages
#   • For .rpm: rpm-build package
#   • For .exe: WiX Toolset (https://wixtoolset.org/) on Windows
# ─────────────────────────────────────────────────────────────
set -euo pipefail

MVN="./mvnw"
if ! [ -x "$MVN" ]; then
    MVN="mvn"
fi

TARGET="${1:-linux}"

case "$TARGET" in
    linux)
        echo ">>> Building Linux packages (.deb + .rpm)..."
        $MVN clean package -P linux -DskipTests
        echo ""
        echo "Output in: target/dist/linux/"
        ;;
    windows)
        echo ">>> Building Windows installer (.exe)..."
        echo "NOTE: jpackage can only build Windows packages ON Windows."
        $MVN clean package -P windows -DskipTests
        echo ""
        echo "Output in: target/dist/windows/"
        ;;
    mac)
        echo ">>> Building macOS disk image (.dmg)..."
        echo "NOTE: jpackage can only build macOS packages ON macOS."
        $MVN clean package -P mac -DskipTests
        echo ""
        echo "Output in: target/dist/mac/"
        ;;
    all)
        echo ">>> Building all platforms..."
        $MVN clean package -P linux,windows,mac -DskipTests
        echo ""
        echo "Output in: target/dist/"
        ;;
    *)
        echo "Unknown target: $TARGET"
        echo "Usage: $0 [linux|windows|mac|all]"
        exit 1
        ;;
esac

echo "Done."
