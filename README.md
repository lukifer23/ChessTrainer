# ChessTrainer

**Offline Android chess training with local Stockfish/LC0 analysis, interactive lessons, and foldable-first layouts.**

ChessTrainer is a native Android chess application built around local analysis and device-first play. It supports human-vs-engine, engine-vs-engine, free play, position analysis, lessons, PGN export, and responsive layouts for phones, tablets, and foldable devices.

The project is designed to work without a remote chess service: supported engine analysis runs locally on the device and user game data remains local unless the user explicitly exports it.

## Highlights

- **Local chess engines** — Stockfish and Leela Chess Zero through UCI integration.
- **Multiple play modes** — human vs. engine, engine vs. engine, and local two-player play.
- **Training tools** — interactive lessons, position analysis, best-move suggestions, and move-history navigation.
- **Responsive Android UI** — Jetpack Compose layouts for phones, tablets, and foldables.
- **PGN workflows** — export games with standard metadata for use in other chess tools.
- **Offline-first operation** — no remote engine dependency or account requirement.

## Technology

- **Language:** Kotlin
- **UI:** Jetpack Compose / Material 3
- **Concurrency:** Kotlin Coroutines + Flow
- **Architecture:** MVVM
- **Chess engine protocol:** UCI
- **Minimum Android version:** API 24 / Android 7.0
- **Primary engine target:** ARM64 Android devices

## Architecture

```text
app/src/main/java/com/chesstrainer/
├── MainActivity.kt
├── chess/       # Board state, rules, legal moves, FEN and game state
├── engine/      # UCI process management and Stockfish/LC0 integration
├── ui/          # Compose screens and chessboard interaction
├── lessons/     # Training content and lesson progression
├── export/      # PGN export
└── utils/       # Settings and supporting utilities
```

The chess layer handles standard rules including castling, en passant, promotion, check, checkmate, and move validation. Engine integration manages local UCI processes and their lifecycle independently from the UI.

## Build

Requirements:

- Android Studio or Android command-line tooling
- Android SDK
- JDK compatible with the current Gradle configuration
- ARM64 Android device or emulator for bundled native-engine testing

```bash
./gradlew assembleDebug
```

Install a debug build:

```bash
./gradlew installDebug
```

Run unit tests:

```bash
./gradlew testDebugUnitTest
```

Run connected Android tests:

```bash
./gradlew connectedAndroidTest
```

## Engine support

The application integrates local UCI engines, including Stockfish and Leela Chess Zero. Engine strength can be bounded through search depth, nodes, and time controls. There is no silent fallback to an online engine.

Because native engine binaries are architecture-specific, verify the bundled engine artifacts on the target Android ABI before treating a build as release-qualified.

## User data and privacy

- Game and application state are stored locally.
- Chess analysis does not require a remote service.
- Users explicitly control PGN export and sharing.
- The project does not depend on an account or hosted chess backend.

## Current status

ChessTrainer is a working Android project and has been built and exercised on physical hardware. It remains a portfolio/development project rather than a Play Store release, so device compatibility, engine packaging, accessibility, and release signing should be re-qualified before distribution.

## Related work

For a newer game-to-mastery training workflow with bounded Stockfish analysis, persistent review, and optional local-model coaching, see [GemmaFischer](https://github.com/lukifer23/GemmaFischer).

## License

MIT. See `LICENSE`.
