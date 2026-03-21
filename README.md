# Ultimate S Setup

A JavaFX setup tool for Ultimate S (Super Smash Bros. Ultimate mod).

## Requirements

- JDK 21 or newer ([Temurin](https://adoptium.net/) recommended)
- Maven 3.6+ (or use the bundled `./mvnw`)

## Building

### Run locally (no installer)
```bash
mvn clean javafx:run
```

### Build a native installer for your current OS
```bash
# Linux → produces .deb and .rpm in target/dist/linux/
./build.sh linux

# Windows → produces .exe in target/dist/windows/
./build.sh windows

# macOS → produces .dmg in target/dist/mac/
./build.sh mac
```

> **Note:** `jpackage` (the JDK tool that creates native installers) can only build
> packages for the OS it is running on. To build all three platforms at once, push
> to GitHub — the included Actions workflow builds them in parallel automatically.

### Build all platforms via GitHub Actions

Push to `main` or `master` and GitHub Actions will run three parallel jobs —
one each on Linux, Windows, and macOS runners. When complete, download the
installers from the **Actions** tab → your run → **Artifacts** section.

You can also trigger a build manually from the Actions tab without pushing.

## Project structure

```
.
├── src/                   Java source code
├── resources/             Runtime assets (icons, layout.arc, logo)
├── .github/workflows/     GitHub Actions CI workflow
├── pom.xml                Maven build file
├── build.sh               Convenience build script
└── mvnw / mvnw.cmd        Bundled Maven wrapper
```

## Dependencies

- [JavaFX 21](https://openjfx.io/)
- [ControlsFX 11](https://controlsfx.org/)
