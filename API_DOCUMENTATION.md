# Chess Trainer API Documentation

Comprehensive API reference for the Chess Trainer Android application.

## Table of Contents
- [Chess Engine API](#chess-engine-api)
- [UI Components](#ui-components)
- [Data Models](#data-models)
- [Utilities](#utilities)
- [Export Features](#export-features)
- [Lesson System](#lesson-system)

## Chess Engine API

### Core Classes

#### `Board`
Represents the 8x8 chess board with piece positions.

```kotlin
class Board {
    // Create from FEN string
    companion object {
        fun fromFen(fen: String): Board
        fun initialPosition(): Board
    }

    // Piece management
    fun getPiece(square: Square): Piece?
    fun setPiece(square: Square, piece: Piece?)
    fun isEmpty(square: Square): Boolean
    fun isOccupied(square: Square): Boolean

    // Board queries
    fun getPieces(color: Color): List<Pair<Square, Piece>>
    fun getAllPieces(): List<Pair<Square, Piece>>

    // Serialization
    fun toFen(): String
    override fun toString(): String
}
```

#### `GameState`
Manages complete game state including position, turn, castling rights, and history.

```kotlin
data class GameState(
    val board: Board = Board.initialPosition(),
    val currentPlayer: Color = Color.WHITE,
    val whiteCanCastleKingside: Boolean = true,
    val whiteCanCastleQueenside: Boolean = true,
    val blackCanCastleKingside: Boolean = true,
    val blackCanCastleQueenside: Boolean = true,
    val enPassantTarget: Square? = null,
    val halfMoveClock: Int = 0,
    val fullMoveNumber: Int = 1,
    val moveHistory: List<Move> = emptyList(),
    val gameResult: GameResult = GameResult.ONGOING
) {
    companion object {
        fun fromFen(fen: String): GameState
    }

    fun toFen(): String
    fun makeMove(move: Move): GameState
    fun isGameOver(): Boolean
    fun getWinner(): Color?
}
```

#### `MoveValidator`
Complete move validation and legal move generation.

```kotlin
class MoveValidator {
    companion object {
        // Move validation
        fun isValidMove(board: Board, move: Move, gameState: GameState): Boolean
        fun generateLegalMoves(board: Board, gameState: GameState): List<Move>

        // Special move validation
        fun isKingInCheck(board: Board, color: Color): Boolean
        fun isSquareAttacked(board: Board, square: Square, byColor: Color): Boolean
    }
}
```

### Engine Integration

#### `EngineManager`
Manages chess engine processes and UCI communication.

```kotlin
class EngineManager(
    context: Context,
    settings: Settings
) {
    val responses: Flow<UCIParser.UCIResponse>

    // Engine control
    suspend fun startEngine()
    fun newGame()
    fun setPosition(gameState: GameState)
    fun startSearch(
        gameState: GameState,
        onBestMove: (Move) -> Unit,
        onInfo: (UCIParser.EngineInfo) -> Unit = {}
    )
    fun stopSearch()
    fun quit()

    // Status
    fun isReady(): Boolean
    fun cleanup()
}
```

#### `UCIParser`
Handles UCI protocol parsing and command generation.

```kotlin
class UCIParser {
    data class UCIResponse(val type: ResponseType, val data: Any? = null)
    data class EngineInfo(/* ... */)

    companion object {
        // Parsing
        fun parseLine(line: String): UCIResponse

        // Command builders
        fun uciCommand(): String
        fun isReadyCommand(): String
        fun newGameCommand(): String
        fun positionCommand(fen: String, moves: List<Move> = emptyList()): String
        fun goCommand(/* parameters */): String
        fun stopCommand(): String
        fun quitCommand(): String
        fun setOptionCommand(name: String, value: Any): String
    }
}
```

## UI Components

### ChessBoard
Custom Compose component for chess board rendering and interaction.

```kotlin
@Composable
fun ChessBoard(
    gameState: GameState,
    selectedSquare: Square? = null,
    availableMoves: List<Move> = emptyList(),
    lastMove: Move? = null,
    draggedPiece: Square? = null,
    dragOffset: Offset = Offset.Zero,
    boardOrientation: Color = Color.WHITE,
    onSquareClick: (Square) -> Unit = {},
    onDragStart: (Square) -> Unit = {},
    onDragEnd: (Square?) -> Unit = {},
    onAnimateMove: (Move) -> Unit = {},
    modifier: Modifier = Modifier
)
```

### GameScreen
Main game interface with navigation and game management.

```kotlin
@Composable
fun GameScreen(
    onNavigateToSettings: () -> Unit,
    onNavigateToAnalysis: () -> Unit,
    onNavigateToLessons: () -> Unit
)
```

### SettingsScreen
Configuration interface for app preferences.

```kotlin
@Composable
fun SettingsScreen(onNavigateBack: () -> Unit)
```

### AnalysisScreen
Position analysis tool with engine evaluation.

```kotlin
@Composable
fun AnalysisScreen(onNavigateBack: () -> Unit)
```

### LessonsScreen
Interactive tutorial system.

```kotlin
@Composable
fun LessonsScreen(onNavigateBack: () -> Unit)
```

## Data Models

### Enums

```kotlin
enum class Color { WHITE, BLACK }
enum class PieceType { PAWN, ROOK, KNIGHT, BISHOP, QUEEN, KING }
enum class GameResult { ONGOING, WHITE_WINS, BLACK_WINS, DRAW }
enum class Difficulty { BEGINNER, INTERMEDIATE, ADVANCED, EXPERT }
enum class Category { BASICS, OPENINGS, TACTICS, ENDGAMES, STRATEGY }
enum class EngineType { LEELA_CHESS_ZERO, STOCKFISH }
```

### Classes

#### `Square`
Represents a chess square with file/rank coordinates.

```kotlin
data class Square(val file: Int, val rank: Int) {
    companion object {
        fun fromAlgebraic(algebraic: String): Square?
        fun fromIndex(index: Int): Square
    }

    val index: Int
    val algebraic: String
}
```

#### `Move`
Represents a chess move with optional promotion.

```kotlin
data class Move(
    val from: Square,
    val to: Square,
    val promotion: PieceType? = null,
    val isEnPassant: Boolean = false,
    val isCastling: Boolean = false
) {
    companion object {
        fun fromUci(uci: String): Move?
    }

    val uci: String
}
```

#### `Piece`
Represents a chess piece with type and color.

```kotlin
data class Piece(val type: PieceType, val color: Color) {
    companion object {
        fun getValue(pieceType: PieceType): Int
        fun getSymbol(piece: Piece): String
        fun getFenChar(piece: Piece): Char
        fun fromFenChar(char: Char): Piece?
    }

    val value: Int
    val symbol: String
    val fenChar: Char
}
```

## Utilities

### Settings
SharedPreferences wrapper for app configuration.

```kotlin
class Settings(context: Context) {
    // Engine settings
    var engineType: EngineType
    var maxNodes: Int
    var searchTimeMs: Int
    var depth: Int

    // UI settings
    var boardOrientation: Color
    var soundEnabled: Boolean
    var vibrationEnabled: Boolean
    var animationEnabled: Boolean
}
```

## Export Features

### PGNExporter
Professional PGN export with algebraic notation and metadata.

```kotlin
class PGNExporter(private val context: Context) {
    data class GameMetadata(
        val event: String = "Chess Trainer Game",
        val site: String = "Chess Trainer App",
        val date: String = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault()).format(Date()),
        val round: String = "-",
        val white: String = "Human",
        val black: String = "Engine",
        val result: String = "*",
        val timeControl: String = "-",
        val termination: String = "unterminated"
    )

    fun exportGame(gameState: GameState, metadata: GameMetadata = GameMetadata()): String
    fun exportGameToFile(gameState: GameState, metadata: GameMetadata = GameMetadata()): File
    fun shareGame(gameState: GameState, metadata: GameMetadata = GameMetadata())

    companion object {
        fun createMetadataForGame(
            whitePlayer: String = "Human",
            blackPlayer: String = "Engine",
            gameState: GameState
        ): GameMetadata
    }
}
```

## Lesson System

### LessonManager
Manages interactive chess lessons and tutorials.

```kotlin
class LessonManager {
    fun getAllLessons(): List<Lesson>
    fun getLessonsByCategory(category: Category): List<Lesson>
    fun getLessonsByDifficulty(difficulty: Difficulty): List<Lesson>
    fun getLessonById(id: String): Lesson?

    companion object {
        fun validateMove(gameState: GameState, move: Move, expectedMoves: List<Move>): Boolean
    }
}

data class Lesson(
    val id: String,
    val title: String,
    val description: String,
    val difficulty: Difficulty,
    val category: Category,
    val steps: List<LessonStep>
)

data class LessonStep(
    val title: String,
    val instruction: String,
    val position: GameState,
    val expectedMoves: List<Move>,
    val hints: List<String>,
    val explanation: String,
    val isInteractive: Boolean = true
)
```

## Error Handling

### Exceptions

```kotlin
class ChessEngineException(message: String, cause: Throwable? = null) : Exception(message, cause)
class InvalidMoveException(message: String) : Exception(message)
class EngineNotReadyException(message: String) : Exception(message)
class PGNExportException(message: String) : Exception(message)
```

### Result Types

The API uses Kotlin's `Result<T>` type for operations that may fail:

```kotlin
sealed class ChessResult<T> {
    data class Success<T>(val data: T) : ChessResult<T>()
    data class Error<T>(val exception: Exception) : ChessResult<T>()
}
```

## Performance Considerations

### Memory Management
- Board representations use efficient data structures
- Engine processes are properly cleaned up
- Bitboards used for move generation optimization

### Threading
- Engine communication on background threads
- UI updates on main thread
- Coroutine-based async operations

### Caching
- Position evaluation results cached
- Lesson progress persisted
- Settings stored in SharedPreferences

## Testing

### Unit Tests
```kotlin
// Chess logic tests
class ChessLogicTest {
    @Test fun testPawnMovement() { /* ... */ }
    @Test fun testCheckDetection() { /* ... */ }
    @Test fun testCastlingRules() { /* ... */ }
}

// Engine integration tests
class EngineManagerTest {
    @Test fun testUCIProtocol() { /* ... */ }
    @Test fun testEngineCommunication() { /* ... */ }
}
```

### Integration Tests
```kotlin
// UI interaction tests
class GameScreenTest {
    @Test fun testHumanVsEngineGameplay() { /* ... */ }
    @Test fun testMoveValidation() { /* ... */ }
}
```

### Performance Benchmarks
```kotlin
class PerformanceTest {
    @Test fun testMoveGenerationSpeed() { /* ... */ }
    @Test fun testEngineResponseTime() { /* ... */ }
}
```

This API documentation provides comprehensive coverage of all public interfaces and implementation details for the Chess Trainer application.