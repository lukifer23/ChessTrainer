package com.chesstrainer.engine

import android.content.Context
import com.chesstrainer.chess.GameState
import com.chesstrainer.chess.Move
import com.chesstrainer.utils.Settings
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.io.*
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * Manages chess engine processes and UCI communication.
 * Handles engine lifecycle, binary extraction, and thread-safe communication.
 */
class EngineManager(
    private val context: Context,
    private val settings: Settings
) {
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val mutex = Mutex()

    private var process: java.lang.Process? = null
    private var writer: BufferedWriter? = null
    private var reader: BufferedReader? = null

    private var isEngineReady = false
    private var engineName = "Unknown Engine"
    private var lc0WeightsFile: File? = null
    private val uciOptions = mutableMapOf<String, UCIParser.UCIOption>()

    private val installer = EngineInstaller(context)

    private val _responses = MutableSharedFlow<UCIParser.UCIResponse>()
    val responses: Flow<UCIParser.UCIResponse> = _responses.asSharedFlow()

    private var outputJob: Job? = null
    private var activeSearchJob: Job? = null

    /**
     * Check if a process has terminated (compatible with API 24+)
     */
    private fun isProcessTerminated(process: java.lang.Process): Boolean {
        return try {
            process.exitValue()
            true
        } catch (e: IllegalThreadStateException) {
            // Process is still running
            false
        }
    }

    /**
     * Start the chess engine process
     */
    suspend fun startEngine(
        onStatusUpdate: (String) -> Unit = {}
    ): Result<Unit> = mutex.withLock {
        try {
            android.util.Log.d("EngineManager", "Starting engine: ${settings.engineType}")
            val currentProcess = process
            if (currentProcess != null && !isProcessTerminated(currentProcess)) {
                android.util.Log.d("EngineManager", "Engine already running")
                return Result.success(Unit)
            }

            uciOptions.clear()

            // Ensure engine binary (and weights, if needed) are installed
            android.util.Log.d("EngineManager", "Ensuring engine is installed")
            val engineAssets = installer.ensureInstalled(settings.engineType, onStatusUpdate)
            engineAssets.fold(
                onSuccess = { assets ->
                    android.util.Log.d("EngineManager", "Engine assets: ${assets.engineBinary.absolutePath}")
                    lc0WeightsFile = assets.weightsFile

                    val processBuilder = java.lang.ProcessBuilder(assets.engineBinary.absolutePath)
                        .directory(assets.engineBinary.parentFile)
                        .redirectErrorStream(true)

                    android.util.Log.d("EngineManager", "Starting process")
                    process = processBuilder.start().also { proc: java.lang.Process ->
                        android.util.Log.d("EngineManager", "Process started successfully")
                        writer = BufferedWriter(OutputStreamWriter(proc.outputStream))
                        reader = BufferedReader(InputStreamReader(proc.inputStream))

                        // Start output monitoring
                        startOutputMonitoring()

                        // Initialize UCI protocol
                        initializeEngine()
                        waitForReadyOk()
                    }

                    Result.success(Unit)
                },
                onFailure = { error ->
                    android.util.Log.e("EngineManager", "Failed to install engine", error)
                    Result.failure(error)
                }
            )
        } catch (e: Exception) {
            android.util.Log.e("EngineManager", "Failed to start engine", e)
            Result.failure(Exception("Failed to start engine: ${e.message}", e))
        }
    }

    /**
     * Start monitoring engine output in background
     */
    private fun startOutputMonitoring() {
        outputJob = scope.launch {
            try {
                reader?.let { reader ->
                    while (isActive && process?.let { !isProcessTerminated(it as java.lang.Process) } == true) {
                        val line = withTimeoutOrNull(100) {
                            reader.readLine()
                        }

                        line?.let { processLine(it) }
                    }
                }
            } catch (e: Exception) {
                // Handle read errors
                _responses.emit(UCIParser.UCIResponse.ErrorResponse("Output monitoring error: ${e.message}"))
            }
        }
    }

    /**
     * Process a line of engine output
     */
    private suspend fun processLine(line: String) {
        try {
            val response = UCIParser.parseLine(line)
            _responses.emit(response)

            // Update engine state based on responses
            when (response) {
                is UCIParser.UCIResponse.IdResponse -> {
                    if (response.name.isNotEmpty()) {
                        engineName = response.name
                    }
                }
                is UCIParser.UCIResponse.ReadyOkResponse -> {
                    // Engine is ready for commands
                    isEngineReady = true
                }
                is UCIParser.UCIResponse.OptionResponse -> {
                    val optionName = response.option.name.trim()
                    if (optionName.isNotBlank()) {
                        uciOptions[optionName.lowercase()] = response.option
                        if (optionName.equals("Backend", ignoreCase = true) &&
                            response.option.options.isNotEmpty()
                        ) {
                            settings.lc0BackendOptions = response.option.options.toSet()
                        }
                    }
                }
                is UCIParser.UCIResponse.ErrorResponse -> {
                    // Log error
                }
                else -> {
                    // Other responses
                }
            }
        } catch (e: Exception) {
            _responses.emit(UCIParser.UCIResponse.ErrorResponse("Failed to parse line '$line': ${e.message}"))
        }
    }

    /**
     * Initialize UCI protocol with the engine
     */
    private suspend fun initializeEngine() {
        try {
            sendCommand(UCIParser.uciCommand())

            // Wait for uciok response
            withTimeout(5000) {
                responses.collect { response ->
                    if (response is UCIParser.UCIResponse.UciOkResponse) {
                        throw InitializationCompleteException()
                    }
                }
            }
        } catch (e: TimeoutCancellationException) {
            throw Exception("Engine initialization timeout")
        } catch (e: InitializationCompleteException) {
            // Expected - initialization complete
        } catch (e: Exception) {
            throw Exception("Engine initialization failed: ${e.message}", e)
        }
    }

    private suspend fun waitForReadyOk() {
        try {
            sendCommand(UCIParser.isReadyCommand())
            withTimeout(5000) {
                responses.collect { response ->
                    if (response is UCIParser.UCIResponse.ReadyOkResponse) {
                        throw ReadyCompleteException()
                    }
                }
            }
        } catch (e: TimeoutCancellationException) {
            throw Exception("Engine readiness timeout")
        } catch (e: ReadyCompleteException) {
            // Expected - readiness complete
        } catch (e: Exception) {
            throw Exception("Engine readiness failed: ${e.message}", e)
        }
    }

    /**
     * Send a command to the engine
     */
    private suspend fun sendCommand(command: String): Result<Unit> {
        return try {
            writer?.let { writer ->
                writer.write(command)
                writer.newLine()
                writer.flush()
                Result.success(Unit)
            } ?: Result.failure(Exception("Engine writer not available"))
        } catch (e: Exception) {
            Result.failure(Exception("Failed to send command '$command': ${e.message}", e))
        }
    }

    /**
     * Check if engine is ready for commands
     */
    fun isReady(): Boolean = isEngineReady && process?.let { !isProcessTerminated(it as java.lang.Process) } == true

    fun getLc0WeightsFile(): File? = lc0WeightsFile

    /**
     * Start a new game
     */
    suspend fun newGame(): Result<Unit> {
        if (!isReady()) return Result.failure(Exception("Engine not ready"))
        return sendCommand(UCIParser.newGameCommand())
    }

    /**
     * Set the current position
     */
    suspend fun setPosition(gameState: GameState): Result<Unit> {
        if (!isReady()) return Result.failure(Exception("Engine not ready"))

        val fen = gameState.toFen()
        val moves = gameState.moveHistory
        val command = UCIParser.positionCommand(fen, moves)
        return sendCommand(command)
    }

    /**
     * Start engine search for the best move
     */
    suspend fun startSearch(
        gameState: GameState,
        onBestMove: (Move) -> Unit,
        onInfo: (UCIParser.EngineInfo) -> Unit = {},
        searchParams: SearchParams = SearchParams()
    ): Result<Unit> {
        if (!isReady()) return Result.failure(Exception("Engine not ready"))
        cancelActiveSearch()

        // Set position first
        setPosition(gameState).getOrElse {
            return Result.failure(it)
        }

        // Start search
        val goCommand = UCIParser.goCommand(
            depth = searchParams.depth,
            moveTime = searchParams.moveTime,
            nodes = searchParams.nodes,
            infinite = searchParams.infinite
        )

        sendCommand(goCommand).getOrElse {
            return Result.failure(it)
        }

        // Monitor for responses
        activeSearchJob = scope.launch {
            try {
                val bestMoveResponse = withTimeout(searchParams.timeoutMs) {
                    searchResponses()
                        .onEach { response ->
                            if (response is UCIParser.UCIResponse.InfoResponse) {
                                onInfo(response.info)
                            }
                        }
                        .first { response -> response is UCIParser.UCIResponse.BestMoveResponse }
                } as UCIParser.UCIResponse.BestMoveResponse
                onBestMove(bestMoveResponse.move)
            } catch (e: TimeoutCancellationException) {
                // Search timeout - stop the search
                stopSearch()
            } catch (e: CancellationException) {
                // Search cancelled
            }
        }.also { job ->
            job.invokeOnCompletion { activeSearchJob = null }
        }

        return Result.success(Unit)
    }

    private fun searchResponses(): Flow<UCIParser.UCIResponse> = channelFlow {
        val job = scope.launch {
            responses.collect { response ->
                send(response)
            }
        }
        awaitClose { job.cancel() }
    }

    suspend fun cancelActiveSearch() {
        activeSearchJob?.cancelAndJoin()
        activeSearchJob = null
        if (isReady()) {
            stopSearch()
        }
    }

    /**
     * Stop the current search
     */
    suspend fun stopSearch(): Result<Unit> {
        if (!isReady()) return Result.failure(Exception("Engine not ready"))
        return sendCommand(UCIParser.stopCommand())
    }

    /**
     * Set an engine option
     */
    suspend fun setOption(name: String, value: Any): Result<Unit> {
        if (!isReady()) return Result.failure(Exception("Engine not ready"))
        return sendCommand(UCIParser.setOptionCommand(name, value))
    }

    /**
     * Configure engine for the current settings
     */
    suspend fun configureEngine(): Result<Unit> {
        if (!isReady()) return Result.failure(Exception("Engine not ready"))

        val results = mutableListOf<Result<Unit>>()

        // Configure based on engine type and settings
        when (settings.engineType) {
            com.chesstrainer.utils.EngineType.LEELA_CHESS_ZERO -> {
                // Leela-specific options
                settings.leelaNodes.takeIf { it > 0 }?.let { nodes ->
                    if (supportsOption("MaxNodes")) {
                        results.add(setOption("MaxNodes", nodes))
                    }
                }
                val availableThreads = Runtime.getRuntime().availableProcessors().coerceAtLeast(1)
                val lc0Threads = settings.lc0Threads.coerceIn(1, availableThreads)
                if (supportsOption("Threads")) {
                    results.add(setOption("Threads", lc0Threads))
                }
                settings.lc0Backend.trim().takeIf { it.isNotEmpty() }?.let { backend ->
                    if (supportsOption("Backend", backend)) {
                        results.add(setOption("Backend", backend))
                    }
                }
                lc0WeightsFile?.let { weights ->
                    if (supportsOption("WeightsFile")) {
                        results.add(setOption("WeightsFile", weights.absolutePath))
                    }
                } ?: return Result.failure(Exception("LC0 weights file missing."))
            }
            com.chesstrainer.utils.EngineType.STOCKFISH -> {
                // Stockfish-specific options
                settings.stockfishDepth.takeIf { it > 0 }?.let { depth ->
                    if (supportsOption("Skill Level")) {
                        results.add(setOption("Skill Level", depth))
                    }
                }
                if (supportsOption("Threads")) {
                    results.add(setOption("Threads", Runtime.getRuntime().availableProcessors()))
                }
            }
        }

        // Return failure if any option failed to set
        val failures = results.filter { it.isFailure }
        return if (failures.isEmpty()) {
            Result.success(Unit)
        } else {
            Result.failure(Exception("Failed to configure engine: ${failures.joinToString { it.exceptionOrNull()?.message ?: "Unknown error" }}"))
        }
    }

    /**
     * Get engine information
     */
    fun getEngineName(): String = engineName

    private fun supportsOption(name: String, value: Any? = null): Boolean {
        val option = uciOptions[name.lowercase()] ?: return false
        if (value != null && option.type == UCIParser.OptionType.COMBO && option.options.isNotEmpty()) {
            val valueString = value.toString()
            return option.options.any { it.equals(valueString, ignoreCase = true) }
        }
        return true
    }

    /**
     * Clean up resources
     */
    fun cleanup() {
        scope.launch {
            mutex.withLock {
                try {
                    cancelActiveSearch()
                    // Send quit command
                    if (isReady()) {
                        sendCommand(UCIParser.quitCommand())
                        delay(100) // Give engine time to quit gracefully
                    }
                } catch (e: Exception) {
                    // Ignore errors during cleanup
                } finally {
                    // Force kill process if still alive
                    process?.let { proc: java.lang.Process ->
                        if (!isProcessTerminated(proc)) {
                            try {
                                proc.destroy()
                                // Wait for process to terminate
                                proc.waitFor()
                            } catch (e: Exception) {
                                // Process might not terminate cleanly
                            }
                        }
                    }

                    // Close streams
                    try {
                        writer?.close()
                        reader?.close()
                    } catch (e: Exception) {
                        // Ignore stream close errors
                    }

                    // Cancel background jobs
                    outputJob?.cancel()
                    scope.cancel()

                    // Reset state
                    process = null
                    writer = null
                    reader = null
                    isEngineReady = false
                }
            }
        }
    }

    /**
     * Search parameters for engine analysis
     */
    data class SearchParams(
        val depth: Int? = null,
        val moveTime: Long? = null,
        val nodes: Long? = null,
        val infinite: Boolean = false,
        val timeoutMs: Long = 10000 // Default 10 second timeout
    )

    // Exception classes for control flow
    private class InitializationCompleteException : Exception()
    private class ReadyCompleteException : Exception()
}
