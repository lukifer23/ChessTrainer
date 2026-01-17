package com.chesstrainer.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.chesstrainer.engine.EngineInstaller
import com.chesstrainer.chess.Color as ChessColor
import com.chesstrainer.utils.EngineType
import com.chesstrainer.utils.Settings
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(onNavigateBack: () -> Unit) {
    val context = LocalContext.current
    val settings = remember { Settings(context) }
    val installer = remember { EngineInstaller(context) }
    val coroutineScope = rememberCoroutineScope()

    var selectedEngine by remember { mutableStateOf(settings.engineType) }
    var selectedBoardOrientation by remember { mutableStateOf(settings.boardOrientation) }
    var leelaNodes by remember { mutableStateOf(settings.leelaNodes.toString()) }
    var leelaThreads by remember { mutableStateOf(settings.lc0Threads.toString()) }
    var leelaBackend by remember { mutableStateOf(settings.lc0Backend) }
    var stockfishDepth by remember { mutableStateOf(settings.stockfishDepth.toString()) }
    var leelaStatus by remember { mutableStateOf(installer.getStatus(EngineType.LEELA_CHESS_ZERO)) }
    var stockfishStatus by remember { mutableStateOf(installer.getStatus(EngineType.STOCKFISH)) }
    var installMessage by remember { mutableStateOf<String?>(null) }
    var installError by remember { mutableStateOf(false) }
    var isInstalling by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        leelaStatus = installer.getStatus(EngineType.LEELA_CHESS_ZERO)
        stockfishStatus = installer.getStatus(EngineType.STOCKFISH)
    }

    // Save settings when changed
    LaunchedEffect(selectedEngine, selectedBoardOrientation, leelaNodes, leelaThreads, leelaBackend, stockfishDepth) {
        settings.engineType = selectedEngine
        settings.boardOrientation = selectedBoardOrientation
        settings.leelaNodes = leelaNodes.toIntOrNull() ?: 1000
        settings.lc0Threads = leelaThreads.toIntOrNull() ?: 2
        settings.lc0Backend = leelaBackend
        settings.stockfishDepth = stockfishDepth.toIntOrNull() ?: 15
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // Header
        TopAppBar(
            title = { Text("Settings") },
            navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            }
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Engine Selection
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = 4.dp
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Chess Engine",
                        style = MaterialTheme.typography.h6
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Choose which chess engine to use for analysis and gameplay",
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        EngineOption(
                            title = "LeelaChess0 (Neural Network)",
                            description = "Uses deep learning for position evaluation. Best for strategic play.",
                            selected = selectedEngine == EngineType.LEELA_CHESS_ZERO,
                            onSelect = { selectedEngine = EngineType.LEELA_CHESS_ZERO }
                        )

                        EngineOption(
                            title = "Stockfish (Traditional Engine)",
                            description = "Classical chess engine with brute-force search. Excellent tactical analysis.",
                            selected = selectedEngine == EngineType.STOCKFISH,
                            onSelect = { selectedEngine = EngineType.STOCKFISH }
                        )
                    }
                }
            }

            // Engine Parameters
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = 4.dp
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Engine Parameters",
                        style = MaterialTheme.typography.h6
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    when (selectedEngine) {
                        EngineType.LEELA_CHESS_ZERO -> {
                            Text(
                                text = "LeelaChess0 analyzes positions by simulating thousands of games using neural networks.",
                                style = MaterialTheme.typography.body2,
                                color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
                            )
                            Spacer(modifier = Modifier.height(16.dp))

                            OutlinedTextField(
                                value = leelaNodes,
                                onValueChange = {
                                    leelaNodes = it.filter { char -> char.isDigit() }
                                },
                                label = { Text("Nodes per move") },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true
                            )
                            Text(
                                text = "Higher values = stronger play but slower moves (100-10000)",
                                style = MaterialTheme.typography.caption,
                                color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            OutlinedTextField(
                                value = leelaThreads,
                                onValueChange = {
                                    leelaThreads = it.filter { char -> char.isDigit() }
                                },
                                label = { Text("Threads") },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true
                            )
                            Text(
                                text = "Uses up to the available CPU cores.",
                                style = MaterialTheme.typography.caption,
                                color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            OutlinedTextField(
                                value = leelaBackend,
                                onValueChange = { leelaBackend = it },
                                label = { Text("NN backend (e.g. cpu)") },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true
                            )
                            Text(
                                text = "Set to cpu unless you bundle a GPU backend.",
                                style = MaterialTheme.typography.caption,
                                color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
                            )
                        }

                        EngineType.STOCKFISH -> {
                            Text(
                                text = "Stockfish evaluates positions by searching millions of possible moves.",
                                style = MaterialTheme.typography.body2,
                                color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
                            )
                            Spacer(modifier = Modifier.height(16.dp))

                            OutlinedTextField(
                                value = stockfishDepth,
                                onValueChange = {
                                    stockfishDepth = it.filter { char -> char.isDigit() }
                                },
                                label = { Text("Search depth") },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true
                            )
                            Text(
                                text = "Higher values = stronger play but slower moves (10-25)",
                                style = MaterialTheme.typography.caption,
                                color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
                            )
                        }
                    }
                }
            }

            // Engine Setup
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = 4.dp
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Engine Setup",
                        style = MaterialTheme.typography.h6
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Download engine binaries and (for LeelaChess0) network weights.",
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    EngineStatusRow(
                        title = "LeelaChess0",
                        status = leelaStatus.statusMessage,
                        details = leelaStatus.weightsPath
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        OutlinedButton(
                            onClick = {
                                installMessage = null
                                installError = false
                                isInstalling = true
                                coroutineScope.launch {
                                    val result = installer.ensureInstalled(EngineType.LEELA_CHESS_ZERO) { message ->
                                        installMessage = message
                                    }
                                    leelaStatus = installer.getStatus(EngineType.LEELA_CHESS_ZERO)
                                    installError = result.isFailure
                                    installMessage = result.exceptionOrNull()?.message ?: "LeelaChess0 ready."
                                    isInstalling = false
                                }
                            },
                            enabled = !isInstalling
                        ) {
                            Text("Install LeelaChess0")
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    EngineStatusRow(
                        title = "Stockfish",
                        status = stockfishStatus.statusMessage,
                        details = stockfishStatus.enginePath
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        OutlinedButton(
                            onClick = {
                                installMessage = null
                                installError = false
                                isInstalling = true
                                coroutineScope.launch {
                                    val result = installer.ensureInstalled(EngineType.STOCKFISH) { message ->
                                        installMessage = message
                                    }
                                    stockfishStatus = installer.getStatus(EngineType.STOCKFISH)
                                    installError = result.isFailure
                                    installMessage = result.exceptionOrNull()?.message ?: "Stockfish ready."
                                    isInstalling = false
                                }
                            },
                            enabled = !isInstalling
                        ) {
                            Text("Install Stockfish")
                        }
                    }
                    installMessage?.let { message ->
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = message,
                            style = MaterialTheme.typography.body2,
                            color = if (!installError) {
                                MaterialTheme.colors.primary
                            } else {
                                MaterialTheme.colors.error
                            }
                        )
                    }
                }
            }

            // Board Settings
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = 4.dp
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Board Settings",
                        style = MaterialTheme.typography.h6
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Customize the appearance and behavior of the chess board",
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Board Orientation",
                        style = MaterialTheme.typography.subtitle1
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        OrientationOption(
                            title = "White at Bottom",
                            description = "White pieces start at bottom",
                            selected = selectedBoardOrientation == ChessColor.WHITE,
                            onSelect = { selectedBoardOrientation = ChessColor.WHITE },
                            modifier = Modifier.weight(1f)
                        )

                        OrientationOption(
                            title = "Black at Bottom",
                            description = "Black pieces start at bottom",
                            selected = selectedBoardOrientation == ChessColor.BLACK,
                            onSelect = { selectedBoardOrientation = ChessColor.BLACK },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            // Current Settings Display
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = 4.dp
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Current Settings",
                        style = MaterialTheme.typography.h6
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    SettingsSummary(
                        engine = selectedEngine,
                        boardOrientation = selectedBoardOrientation,
                        leelaNodes = leelaNodes.toIntOrNull() ?: 1000,
                        stockfishDepth = stockfishDepth.toIntOrNull() ?: 15
                    )
                }
            }

            // App Info
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = 4.dp
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "About Chess Trainer",
                        style = MaterialTheme.typography.h6
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Professional chess training app with local engines. Download engines once for offline play.",
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Version 1.0.0",
                        style = MaterialTheme.typography.caption
                    )
                }
            }
        }
    }
}

@Composable
private fun EngineOption(
    title: String,
    description: String,
    selected: Boolean,
    onSelect: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = onSelect
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.subtitle1
            )
            Text(
                text = description,
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}

@Composable
private fun OrientationOption(
    title: String,
    description: String,
    selected: Boolean,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedButton(
            onClick = onSelect,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.outlinedButtonColors(
                backgroundColor = if (selected) {
                    MaterialTheme.colors.primary.copy(alpha = 0.1f)
                } else {
                    MaterialTheme.colors.surface
                }
            )
        ) {
            Text(title)
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = description,
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
        )
    }
}

@Composable
private fun EngineStatusRow(
    title: String,
    status: String,
    details: String?
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "$title: $status",
            style = MaterialTheme.typography.body2
        )
        details?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}

@Composable
private fun SettingsSummary(
    engine: EngineType,
    boardOrientation: ChessColor,
    leelaNodes: Int,
    stockfishDepth: Int
) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
            text = "Engine: ${if (engine == EngineType.LEELA_CHESS_ZERO) "LeelaChess0" else "Stockfish"}",
            style = MaterialTheme.typography.body2
        )
        Text(
            text = if (engine == EngineType.LEELA_CHESS_ZERO)
                "Nodes: $leelaNodes"
            else
                "Depth: $stockfishDepth",
            style = MaterialTheme.typography.body2
        )
        Text(
            text = "Board: ${if (boardOrientation == ChessColor.WHITE) "White" else "Black"} at bottom",
            style = MaterialTheme.typography.body2
        )
    }
}
