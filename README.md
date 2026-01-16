# Chess Trainer Android App

## ✅ **BUILD SUCCESSFUL** - App Running on Device!

A complete, modern, and feature-rich chess training application for Android devices, optimized for phones, tablets, and foldable devices. Features local chess engines (LeelaChess0 and Stockfish), interactive lessons, position analysis, and professional game export.

**Current Status**: 🟢 **Successfully built and deployed to test device (Samsung Galaxy Fold 5)**

## 🎯 Features

### Core Chess Engine
- **Complete Chess Logic**: Full implementation of chess rules including castling, en passant, pawn promotion, check/checkmate detection
- **Local Engines**: LeelaChess0 and Stockfish run entirely on-device with no internet required
- **UCI Protocol**: Professional chess engine communication supporting all UCI features

### Game Modes
- **Human vs Engine**: Play against configurable AI opponents
- **Engine vs Engine**: Watch AI battles between different engines
- **Free Play**: Two-player mode on the same device

### Training Tools
- **Interactive Lessons**: Step-by-step tutorials covering basics, openings, tactics, and endgames
- **Position Analysis**: Real-time engine evaluation with best move suggestions
- **Move History**: Navigate through game moves with visual indicators
- **Difficulty Scaling**: Adjustable engine strength (nodes, search time, depth)

### User Interface
- **Touch Optimized**: Drag-and-drop piece movement with smooth animations
- **Foldable Support**: Dynamic scaling for all screen sizes and form factors
- **Material Design 3**: Modern, accessible interface with dark/light themes
- **Visual Feedback**: Move highlighting, check indicators, and game state displays

### Export & Sharing
- **PGN Export**: Professional Portable Game Notation with complete metadata
- **Game Sharing**: Export games via email, messaging, or cloud storage
- **Analysis Reports**: Detailed position analysis with engine evaluations

## 🏗️ Architecture

### Technology Stack
- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Async Programming**: Kotlin Coroutines + Flow
- **Architecture**: MVVM with clean separation of concerns
- **Minimum SDK**: API 24 (Android 7.0)

### Project Structure
```
app/src/main/java/com/chesstrainer/
├── MainActivity.kt                 # Application entry point
├── chess/                          # Core chess logic
│   ├── Board.kt                   # 8x8 board representation
│   ├── Piece.kt                   # Piece definitions and utilities
│   ├── Move.kt                    # Move representation and validation
│   ├── MoveValidator.kt           # Complete move generation and validation
│   └── GameState.kt               # Game state management with FEN support
├── engine/                        # Chess engine integration
│   ├── EngineManager.kt           # Process management and UCI communication
│   ├── UCIParser.kt               # UCI protocol implementation
│   ├── LeelaEngine.kt             # LeelaChess0 wrapper
│   └── StockfishEngine.kt         # Stockfish wrapper
├── ui/                           # User interface components
│   ├── ChessBoard.kt             # Custom Compose chess board with touch handling
│   ├── GameScreen.kt             # Main game interface
│   ├── SettingsScreen.kt         # Configuration and preferences
│   ├── AnalysisScreen.kt         # Position analysis tool
│   └── LessonsScreen.kt          # Interactive tutorials
├── utils/                        # Utilities and helpers
│   └── Settings.kt               # SharedPreferences wrapper
├── export/                       # Export functionality
│   └── PGNExporter.kt            # PGN format export with metadata
└── lessons/                      # Lesson system
    └── LessonManager.kt          # Interactive tutorials and content
```

### Key Components

#### Chess Engine (`chess/` package)
- **Board**: Bitboard-based representation for efficient move generation
- **MoveValidator**: Complete implementation of chess rules with legal move generation
- **GameState**: FEN notation support, move history, and game result tracking

#### Engine Integration (`engine/` package)
- **UCI Protocol**: Full UCI specification support for professional engines
- **Process Management**: Safe engine lifecycle management with cleanup
- **Binary Extraction**: Automatic extraction of engine binaries from assets

#### User Interface (`ui/` package)
- **ChessBoard**: Custom Canvas-based board with GPU acceleration
- **Touch Handling**: Multi-touch support with drag-and-drop animations
- **Responsive Design**: Adaptive layouts for phones, tablets, and foldables

## 📋 Prerequisites

- **Android Studio**: Arctic Fox or later (recommended: latest stable)
- **Android SDK**: API 24+ (Android 7.0+)
- **Kotlin**: 1.9.0+
- **Gradle**: 8.2+

### Engine Binaries

The app requires chess engine binaries in `app/src/main/assets/engines/`:

#### LeelaChess0 (lc0)
```bash
# Download ARM64 binary for Android
# Place as: app/src/main/assets/engines/lc0
# Make executable: chmod +x app/src/main/assets/engines/lc0
```

#### Stockfish
```bash
# Download ARM64 binary for Android
# Place as: app/src/main/assets/engines/stockfish
# Make executable: chmod +x app/src/main/assets/engines/stockfish
```

**Note**: Engine binaries must be ARM64 architecture for Android compatibility.

## 🚀 Getting Started

### 1. Clone and Setup
```bash
git clone <repository-url>
cd ChessTrainer
```

### 2. Engine Setup
```bash
# Create engines directory
mkdir -p app/src/main/assets/engines

# Download and place engine binaries
# lc0 and stockfish ARM64 binaries required
```

### 3. Build the App
```bash
# Generate Gradle wrapper (if needed)
gradle wrapper --gradle-version 8.2

# Build debug APK
./gradlew assembleDebug

# Build release APK
./gradlew assembleRelease
```

### 4. Run on Device
```bash
# Install debug build
./gradlew installDebug

# Or use Android Studio: Run > Run 'app'
```

## 🔧 Configuration

### Engine Settings
- **Engine Type**: LeelaChess0 or Stockfish
- **Difficulty**: Configurable nodes (100-50,000), search time (500-10,000ms), depth (1-20)
- **Threads**: Automatic detection of CPU cores

### UI Preferences
- **Board Orientation**: White or black at bottom
- **Theme**: Light/dark mode (follows system)
- **Sound/Vibration**: Optional audio feedback
- **Animations**: Smooth move transitions

## 📖 Usage Guide

### Playing a Game
1. **Select Game Mode**: Human vs Engine, Engine vs Engine, or Free Play
2. **Configure Difficulty**: Adjust engine strength in Settings
3. **Make Moves**: Touch pieces to select, drag to move, or tap destination
4. **View Analysis**: Use Analysis mode for position evaluation
5. **Export Games**: Save games in PGN format for sharing

### Learning with Lessons
1. **Browse Categories**: Basics, Openings, Tactics, Endgames, Strategy
2. **Interactive Tutorials**: Step-by-step lessons with hints
3. **Practice Positions**: Try moves and get feedback
4. **Track Progress**: Complete lessons and view achievements

### Analyzing Positions
1. **Load Position**: Set up any chess position
2. **Engine Evaluation**: Real-time centipawn evaluation
3. **Best Moves**: See engine recommendations
4. **Variations**: Explore different lines

## 🧪 Testing

### Unit Tests
```bash
./gradlew testDebugUnitTest
```

### Instrumented Tests
```bash
./gradlew connectedAndroidTest
```

### Manual Testing Checklist
- [ ] Basic piece movement and capture
- [ ] Special moves (castling, en passant, promotion)
- [ ] Check and checkmate detection
- [ ] Engine vs human gameplay
- [ ] Engine vs engine matches
- [ ] PGN export functionality
- [ ] Settings persistence
- [ ] Lesson progression
- [ ] Analysis mode operation
- [ ] Touch interactions on different screen sizes

## 📦 Build Variants

### Debug Build
- Development features enabled
- Logging and debugging tools
- Test engines with reduced strength

### Release Build
- Optimized for performance
- ProGuard/R8 minification
- Production-ready configuration

## 🔒 Security & Permissions

### Required Permissions
```xml
<!-- Internet access for future features -->
<uses-permission android:name="android.permission.INTERNET" />

<!-- External storage for game export -->
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
```

### Data Handling
- **Local Storage**: Game data stored locally on device
- **No Data Collection**: No user data sent to external servers
- **Export Only**: Users control all data export

## 🚨 Known Limitations

### Engine Dependencies
- Requires ARM64 chess engine binaries
- Engines must support UCI protocol
- No fallback to online engines

### Performance Considerations
- Engine analysis may be CPU intensive
- Large PGN exports on low-end devices
- GPU acceleration requires compatible hardware

### Platform Limitations
- Android only (iOS not supported)
- Minimum Android 7.0 required
- Root access not required

## 🤝 Contributing

### Development Setup
1. Fork the repository
2. Create a feature branch
3. Make changes with comprehensive tests
4. Submit a pull request

### Code Standards
- **Kotlin**: Follow official Kotlin coding conventions
- **Architecture**: MVVM pattern with clean architecture principles
- **Testing**: Unit tests for all business logic, integration tests for UI
- **Documentation**: KDoc for all public APIs

### Testing Requirements
- Unit test coverage > 80%
- All UI interactions tested
- Chess logic validation comprehensive
- Performance benchmarks maintained

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🙏 Acknowledgments

- **Chess Engines**: LeelaChess0 and Stockfish development communities
- **Android Team**: For Jetpack Compose and modern Android development
- **Chess Programming**: Community resources and algorithm implementations

## 📞 Support

### Issues
- GitHub Issues for bug reports and feature requests
- Include device information, Android version, and reproduction steps

### Documentation
- Comprehensive in-code documentation
- Architecture decision records in `/docs`
- User guide and API reference

---

**Built with ❤️ for chess enthusiasts and learners**