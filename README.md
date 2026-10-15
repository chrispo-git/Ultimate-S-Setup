# Ultimate S Setup

A JavaFX setup tool for Ultimate S.


## Building locally
### Requirements
- JDK 21 or newer
- Maven 3.6+
### Running
Build the jar, then run it from the repo root (the app reads `resources/`, `ultimate/` and `atmosphere/` relative to where it's launched):
```bash
mvn clean package
java -jar target/ult-s-setup-1.0-SNAPSHOT.jar
```

### Packaging a portable executable
`build.sh` uses jpackage to build a self-contained app for the current OS:
```bash
./build.sh           # Linux (default) -> target/dist/out/UltimateSSetup-linux.tar.gz
./build.sh windows   # must be run on Windows -> UltimateSSetup-windows.zip
./build.sh mac       # must be run on macOS -> UltimateSSetup-mac.zip
```
Extract the archive anywhere and put your `ultimate/` folder next to the executable.

## Dependencies

- [JavaFX 21](https://openjfx.io/)
- [ControlsFX 11](https://controlsfx.org/)
- [AtlantaFX 2](https://github.com/mkpaz/atlantafx)
