# Chess Trainer Setup Guide

Complete installation and configuration guide for the Chess Trainer Android application.

## Prerequisites

### System Requirements
- **Operating System**: macOS, Windows, or Linux
- **Android Studio**: Arctic Fox (2020.3.1) or later
- **Android SDK**: API 24+ (Android 7.0+)
- **Java**: JDK 11 or later
- **RAM**: 8GB minimum, 16GB recommended
- **Storage**: 5GB free space for Android SDK and project

### Required Tools
```bash
# Verify installations
java -version          # JDK 11+
./gradlew --version    # Gradle 8.2+
adb devices           # Android Debug Bridge
```

## Installation Steps

### 1. Project Setup

```bash
# Clone or download the project
cd ~/Projects
git clone <repository-url>
cd ChessTrainer

# Verify project structure
ls -la
# Should show: app/, build.gradle.kts, settings.gradle.kts, etc.
```

### 2. Android Studio Configuration

#### Import Project
1. Open Android Studio
2. Select "Open an existing Android Studio project"
3. Navigate to and select the `ChessTrainer` directory
4. Wait for Gradle sync to complete

#### SDK Setup
1. **File** → **Settings** → **Appearance & Behavior** → **System Settings** → **Android SDK**
2. Install SDK Platforms:
   - Android API 34 (Android 14)
   - Android API 24 (minimum)
3. Install SDK Tools:
   - Android SDK Build-Tools 34.0.0
   - Android SDK Command-line Tools
   - Android Emulator (optional)

#### Device Setup
1. Enable **Developer Options** on your Android device:
   - Settings → About Phone → Tap Build Number 7 times
2. Enable **USB Debugging**:
   - Settings → Developer Options → USB Debugging
3. Connect device via USB and accept debugging authorization

### 3. Chess Engine Setup

#### Download Engine Binaries

**LeelaChess0 (lc0)**:
```bash
# Visit: https://github.com/LeelaChessZero/lc0/releases
# Download: lc0-v0.28.2-android-arm64.zip (or latest ARM64 build)

# Extract and prepare
unzip lc0-v0.28.2-android-arm64.zip
chmod +x lc0  # Make executable
cp lc0 app/src/main/assets/engines/lc0
```

**Stockfish**:
```bash
# Visit: https://github.com/official-stockfish/Stockfish/releases
# Download: stockfish-android-arm64.zip (or latest ARM64 build)

# Extract and prepare
unzip stockfish-android-arm64.zip
chmod +x stockfish  # Make executable
cp stockfish app/src/main/assets/engines/stockfish
```

#### Engine Verification
```bash
# Verify binaries are executable and in correct location
ls -la app/src/main/assets/engines/
# Should show: lc0 and stockfish with execute permissions (-rwxr-xr-x)

# Test engines (optional)
./app/src/main/assets/engines/lc0 --help
./app/src/main/assets/engines/stockfish --help
```

#### Alternative: Build from Source

**Building lc0**:
```bash
# Requires Android NDK
git clone https://github.com/LeelaChessZero/lc0.git
cd lc0
git submodule update --init --recursive

# Configure for Android ARM64
mkdir build && cd build
cmake .. \
  -DCMAKE_TOOLCHAIN_FILE=$ANDROID_NDK/build/cmake/android.toolchain.cmake \
  -DANDROID_ABI=arm64-v8a \
  -DANDROID_PLATFORM=android-24

make -j$(nproc)
cp lczero app/src/main/assets/engines/lc0
```

**Building Stockfish**:
```bash
git clone https://github.com/official-stockfish/Stockfish.git
cd Stockfish/src

# Configure for Android
make build ARCH=arm64 COMP=clang
cp stockfish app/src/main/assets/engines/stockfish
```

### 4. Build Configuration

#### Gradle Properties
```properties
# gradle.properties
org.gradle.jvmargs=-Xmx4096m -XX:MaxMetaspaceSize=1024m
org.gradle.daemon=true
org.gradle.parallel=true
kotlin.code.style=official
android.useAndroidX=true
android.enableJetifier=false
```

#### Local Properties (Optional)
```properties
# local.properties
sdk.dir=/Users/YOUR_USERNAME/Library/Android/sdk
ndk.dir=/Users/YOUR_USERNAME/Library/Android/sdk/ndk/25.1.8937393
```

### 5. Build the Application

#### Debug Build
```bash
# Clean and build
./gradlew clean
./gradlew assembleDebug

# Verify APK creation
ls -la app/build/outputs/apk/debug/
# Should contain: app-debug.apk
```

#### Release Build (Production)
```bash
# Build signed release APK
./gradlew assembleRelease

# Or use Android Studio: Build → Generate Signed Bundle/APK
# Configure signing with your keystore
```

#### Build Verification
```bash
# Check for build issues
./gradlew build --info

# Run linting
./gradlew lintDebug

# Run tests
./gradlew testDebugUnitTest
```

## Device Installation

### Via USB (Recommended)

```bash
# List connected devices
adb devices
# Should show: emulator-5554 device or YOUR_DEVICE_ID device

# Install debug APK
./gradlew installDebug

# Or manual installation
adb install app/build/outputs/apk/debug/app-debug.apk

# Launch app
adb shell am start -n com.chesstrainer/.MainActivity
```

### Via Android Studio
1. **Run** → **Run 'app'**
2. Select connected device or create emulator
3. Wait for installation and launch

### Via Wireless ADB (Alternative)
```bash
# Enable wireless debugging on device
# Settings → Developer Options → Wireless debugging

# Connect wirelessly
adb connect 192.168.1.100:5555  # Replace with device IP

# Install and run
./gradlew installDebug
```

## Configuration

### First Launch Setup
1. **Grant Permissions**: Allow storage and internet access when prompted
2. **Engine Extraction**: App will automatically extract engines to device storage
3. **Initial Configuration**: Set preferred engine and difficulty in Settings

### App Configuration
```kotlin
// Access settings programmatically
val settings = Settings(context)

// Configure engine
settings.engineType = EngineType.LEELA_CHESS_ZERO
settings.maxNodes = 10000
settings.searchTimeMs = 2000

// Configure UI
settings.boardOrientation = Color.WHITE
settings.soundEnabled = true
settings.animationEnabled = true
```

## Testing

### Manual Testing Checklist

#### Core Functionality
- [ ] App launches without crashes
- [ ] Chess board displays correctly
- [ ] Piece movement works (touch and drag)
- [ ] Move validation prevents illegal moves
- [ ] Check/checkmate detection
- [ ] Special moves (castling, en passant, promotion)

#### Game Modes
- [ ] Human vs Engine gameplay
- [ ] Engine vs Engine matches
- [ ] Free play (two human players)
- [ ] Move history navigation
- [ ] Game export (PGN format)

#### Engine Integration
- [ ] Engine selection (LeelaChess0/Stockfish)
- [ ] Difficulty adjustment
- [ ] Engine response times
- [ ] Position analysis
- [ ] Best move suggestions

#### User Interface
- [ ] Settings persistence
- [ ] Theme switching (light/dark)
- [ ] Responsive layouts (portrait/landscape)
- [ ] Foldable device support
- [ ] Touch feedback and animations

### Automated Testing
```bash
# Unit tests
./gradlew testDebugUnitTest

# Integration tests (requires device/emulator)
./gradlew connectedAndroidTest

# Generate test coverage report
./gradlew createDebugCoverageReport
```

## Troubleshooting

### Common Issues

#### Build Failures
```bash
# Clean build cache
./gradlew cleanBuildCache
./gradlew clean

# Invalidate Android Studio cache
# File → Invalidate Caches / Restart

# Check Gradle version compatibility
./gradlew --version
```

#### Engine Issues
```bash
# Check engine binary permissions
ls -la app/src/main/assets/engines/
chmod +x app/src/main/assets/engines/*

# Test engine manually
adb push app/src/main/assets/engines/lc0 /data/local/tmp/
adb shell /data/local/tmp/lc0 --help
```

#### Device Connection Issues
```bash
# Restart ADB server
adb kill-server
adb start-server

# Check device authorization
adb devices  # Should show "device" not "unauthorized"

# USB debugging troubleshooting
# 1. Try different USB cable
# 2. Try different USB port
# 3. Disable/re-enable USB debugging
# 4. Revoke USB debugging authorizations
```

#### Performance Issues
```bash
# Enable ProGuard for release builds
# Modify build.gradle.kts:
buildTypes {
    release {
        isMinifyEnabled = true
        proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"))
    }
}

# Optimize Gradle settings
org.gradle.jvmargs=-Xmx4096m -XX:+HeapDumpOnOutOfMemoryError
org.gradle.daemon=true
org.gradle.parallel=true
org.gradle.caching=true
```

### Log Analysis
```bash
# View device logs
adb logcat | grep -i chesstrainer

# Filter by log level
adb logcat *:W | grep -i chess

# Save logs to file
adb logcat -d > chess_app_logs.txt
```

## Performance Optimization

### Build Optimization
```gradle
// app/build.gradle.kts
android {
    buildFeatures {
        buildConfig = false  // Disable if not needed
    }

    dexOptions {
        preDexLibraries true
        maxProcessCount 8
    }
}
```

### Runtime Optimization
- Engine analysis runs on background threads
- UI uses GPU-accelerated rendering
- Memory-efficient board representations
- Lazy loading of lesson content

## Deployment

### Play Store Preparation
1. **Generate Signed APK/AAB**
   ```bash
   ./gradlew bundleRelease  # For AAB (recommended)
   ./gradlew assembleRelease  # For APK
   ```

2. **Create Signing Config**
   ```kotlin
   android {
       signingConfigs {
           create("release") {
               storeFile = file("path/to/keystore.jks")
               storePassword = "store_password"
               keyAlias = "key_alias"
               keyPassword = "key_password"
           }
       }
   }
   ```

3. **Play Store Assets**
   - Screenshots (phone/tablet/foldable)
   - Feature graphic (1024x500)
   - Icon (512x512)
   - Description and changelog

### Distribution
- **Internal Testing**: Share APK with beta testers
- **Open Testing**: Use Play Store open testing track
- **Production**: Full Play Store release

## Support and Maintenance

### Monitoring
```bash
# Monitor crashes with Play Console
# Enable crash reporting in Firebase Crashlytics

# Performance monitoring
# Add Firebase Performance Monitoring
```

### Updates
- Regular engine binary updates
- Feature enhancements based on user feedback
- Bug fixes and stability improvements

This setup guide provides comprehensive instructions for building, configuring, and deploying the Chess Trainer application across different development environments.