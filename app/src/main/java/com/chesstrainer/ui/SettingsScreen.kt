package com.chesstrainer.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.chesstrainer.engine.EngineInstaller
import com.chesstrainer.chess.Color as ChessColor
import com.chesstrainer.utils.EngineType
import com.chesstrainer.utils.Settings
import java.io.File
import androidx.compose.ui.res.stringResource
import com.chesstrainer.R
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

    var customWeightsPath by remember { mutableStateOf(settings.customLc0WeightsPath ?: "") }
    var stockfishDepth by remember { mutableStateOf(settings.stockfishDepth.toString()) }
    var leelaStatus by remember { mutableStateOf(installer.getStatus(EngineType.LEELA_CHESS_ZERO)) }
    var stockfishStatus by remember { mutableStateOf(installer.getStatus(EngineType.STOCKFISH)) }
    var installMessage by remember { mutableStateOf<String?>(null) }
    var installError by remember { mutableStateOf(false) }
    var isInstalling by remember { mutableStateOf(false) }
    var backendValidationMessage by remember { mutableStateOf<String?>(null) }
    var backendExpanded by remember { mutableStateOf(false) }
    var ggufModelPath by remember { mutableStateOf(settings.ggufModelPath ?: "") }
    var ggufStatus by remember { mutableStateOf(installer.getStatus(EngineType.GGUF)) }
    val supportedBackends = settings.lc0BackendOptions
    val defaultBackendChoices = listOf("cpu", "gpu", "opencl", "metal")
    val backendChoices = remember(supportedBackends) {
        (if (supportedBackends.isEmpty()) {
            defaultBackendChoices
        } else {
            supportedBackends.toList()
        }).distinct()
    }

    LaunchedEffect(Unit) {
        leelaStatus = installer.getStatus(EngineType.LEELA_CHESS_ZERO)
        stockfishStatus = installer.getStatus(EngineType.STOCKFISH)
        ggufStatus = installer.getStatus(EngineType.GGUF)
    }

    // Save settings when changed
    LaunchedEffect(
        selectedEngine,
        selectedBoardOrientation,
        leelaNodes,
        leelaThreads,
        leelaBackend,
        ggufModelPath,

        customWeightsPath,
        stockfishDepth,
        supportedBackends
    ) {
        val trimmedBackend = leelaBackend.trim().ifEmpty { "cpu" }
        val backendSupported = supportedBackends.isEmpty() ||
            supportedBackends.any { it.equals(trimmedBackend, ignoreCase = true) }
        val validatedBackend = if (backendSupported) trimmedBackend else "cpu"
        backendValidationMessage = if (!backendSupported) {
            "Backend not supported by this engine build. Falling back to CPU."
        } else {
            null
        }
        if (validatedBackend != leelaBackend) {
            leelaBackend = validatedBackend
        }
        settings.engineType = selectedEngine
        settings.boardOrientation = selectedBoardOrientation
        settings.leelaNodes = leelaNodes.toIntOrNull() ?: 1000
        settings.lc0Threads = leelaThreads.toIntOrNull() ?: 2
        settings.lc0Backend = validatedBackend
        settings.ggufModelPath = ggufModelPath.ifBlank { null }
        settings.stockfishDepth = stockfishDepth.toIntOrNull() ?: 15
    }
    
    // Save custom weights separately to handle potentially empty strings
    LaunchedEffect(customWeightsPath) {
        val path = customWeightsPath.trim()
        settings.customLc0WeightsPath = if (path.isEmpty()) null else path
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // Header
        TopAppBar(
            title = { Text(stringResource(R.string.settings_title)) },
            navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = stringResource(R.string.back))
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
                        text = stringResource(R.string.chess_engine),
                        style = MaterialTheme.typography.h6
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = stringResource(R.string.choose_engine_desc),
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        EngineOption(
                            title = stringResource(R.string.lc0_title),
                            description = stringResource(R.string.lc0_desc),
                            selected = selectedEngine == EngineType.LEELA_CHESS_ZERO,
                            onSelect = { selectedEngine = EngineType.LEELA_CHESS_ZERO }
                        )

                        EngineOption(
                            title = stringResource(R.string.stockfish_title),
                            description = stringResource(R.string.stockfish_desc),
                            selected = selectedEngine == EngineType.STOCKFISH,
                            onSelect = { selectedEngine = EngineType.STOCKFISH }
                        )

                        EngineOption(
                            title = stringResource(R.string.gguf_title),
                            description = stringResource(R.string.gguf_desc),
                            selected = selectedEngine == EngineType.GGUF,
                            onSelect = { selectedEngine = EngineType.GGUF }
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
                        text = stringResource(R.string.engine_parameters),
                        style = MaterialTheme.typography.h6
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    when (selectedEngine) {
                        EngineType.LEELA_CHESS_ZERO -> {
                            Text(
                                text = stringResource(R.string.lc0_info),
                                style = MaterialTheme.typography.body2,
                                color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
                            )
                            Spacer(modifier = Modifier.height(16.dp))

                            OutlinedTextField(
                                value = leelaNodes,
                                onValueChange = {
                                    leelaNodes = it.filter { char -> char.isDigit() }
                                },
                                label = { Text(stringResource(R.string.nodes_per_move)) },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true
                            )
                            Text(
                                text = stringResource(R.string.nodes_desc),
                                style = MaterialTheme.typography.caption,
                                color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            OutlinedTextField(
                                value = leelaThreads,
                                onValueChange = {
                                    leelaThreads = it.filter { char -> char.isDigit() }
                                },
                                label = { Text(stringResource(R.string.threads)) },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true
                            )
                            Text(
                                text = stringResource(R.string.threads_desc),
                                style = MaterialTheme.typography.caption,
                                color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Box(modifier = Modifier.fillMaxWidth()) {
                                OutlinedTextField(
                                    value = leelaBackend,
                                    onValueChange = { leelaBackend = it },
                                    label = { Text(stringResource(R.string.nn_backend)) },
                                    modifier = Modifier.fillMaxWidth(),
                                    singleLine = true,
                                    trailingIcon = {
                                        IconButton(onClick = { backendExpanded = !backendExpanded }) {
                                            Icon(
                                                imageVector = Icons.Default.ArrowDropDown,
                                                contentDescription = stringResource(R.string.backend_options)
                                            )
                                        }
                                    }
                                )
                                DropdownMenu(
                                    expanded = backendExpanded,
                                    onDismissRequest = { backendExpanded = false },
                                    modifier = Modifier
                                        .width((LocalConfiguration.current.screenWidthDp - 32).dp)
                                ) {
                                    backendChoices.forEach { backend ->
                                        DropdownMenuItem(
                                            onClick = {
                                                leelaBackend = backend
                                                backendExpanded = false
                                            }
                                        ) {
                                            Text(backend)
                                        }
                                    }
                                }
                            }
                            Text(
                                text = stringResource(R.string.backend_desc),
                                style = MaterialTheme.typography.caption,
                                color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
                            )
                            backendValidationMessage?.let { message ->
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = message,
                                    style = MaterialTheme.typography.caption,
                                    color = MaterialTheme.colors.error
                                )
                            }
                            if (supportedBackends.isNotEmpty()) {
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = stringResource(R.string.supported_backends, supportedBackends.joinToString(", ")),
                                    style = MaterialTheme.typography.caption,
                                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
                                )
                            }

                            
                            Spacer(modifier = Modifier.height(12.dp))
                            OutlinedTextField(
                                value = customWeightsPath,
                                onValueChange = { customWeightsPath = it },
                                label = { Text(stringResource(R.string.custom_weights_path)) },
                                placeholder = { Text(stringResource(R.string.weights_placeholder)) },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true
                            )
                            Text(
                                text = stringResource(R.string.weights_desc),
                                style = MaterialTheme.typography.caption,
                                color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
                            )
                        }

                        EngineType.STOCKFISH -> {
                            Text(
                                text = stringResource(R.string.stockfish_info),
                                style = MaterialTheme.typography.body2,
                                color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
                            )
                            Spacer(modifier = Modifier.height(16.dp))

                            OutlinedTextField(
                                value = stockfishDepth,
                                onValueChange = {
                                    stockfishDepth = it.filter { char -> char.isDigit() }
                                },
                                label = { Text(stringResource(R.string.search_depth)) },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true
                            )
                            Text(
                                text = stringResource(R.string.depth_desc),
                                style = MaterialTheme.typography.caption,
                                color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
                            )
                        }
                        EngineType.GGUF -> {
                            Text(
                                text = stringResource(R.string.gguf_info),
                                style = MaterialTheme.typography.body2,
                                color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
                            )
                            Spacer(modifier = Modifier.height(16.dp))

                            OutlinedTextField(
                                value = ggufModelPath,
                                onValueChange = { ggufModelPath = it },
                                label = { Text(stringResource(R.string.model_path)) },
                                placeholder = { Text(stringResource(R.string.model_placeholder)) },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true
                            )
                            Text(
                                text = stringResource(R.string.model_desc),
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
                        text = stringResource(R.string.engine_setup),
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
                    leelaStatus.unsupportedAbiMessage?.let { message ->
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = message,
                            style = MaterialTheme.typography.caption,
                            color = MaterialTheme.colors.error
                        )
                    }
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
                    stockfishStatus.unsupportedAbiMessage?.let { message ->
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = message,
                            style = MaterialTheme.typography.caption,
                            color = MaterialTheme.colors.error
                        )
                    }
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
                    Spacer(modifier = Modifier.height(12.dp))
                    EngineStatusRow(
                        title = "GGUF Model",
                        status = ggufStatus.statusMessage,
                        details = ggufStatus.enginePath
                    )
                    Text(
                        text = "For GGUF, please manually provide the model path above.",
                        style = MaterialTheme.typography.caption,
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
                    )
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
                        leelaBackend = leelaBackend,
                        stockfishDepth = stockfishDepth.toIntOrNull() ?: 15,
                        ggufModelPath = ggufModelPath
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
    leelaBackend: String,
    stockfishDepth: Int,
    ggufModelPath: String
) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
            text = "Engine: ${
                when (engine) {
                    EngineType.LEELA_CHESS_ZERO -> "LeelaChess0"
                    EngineType.STOCKFISH -> "Stockfish"
                    EngineType.GGUF -> "GGUF"
                }
            }",
            style = MaterialTheme.typography.body2
        )
        Text(
            text = when (engine) {
                EngineType.LEELA_CHESS_ZERO -> "Nodes: $leelaNodes • Backend: ${leelaBackend.ifBlank { "cpu" }}"
                EngineType.STOCKFISH -> "Depth: $stockfishDepth"
                EngineType.GGUF -> "Model: ${File(ggufModelPath).name}"
            },
            style = MaterialTheme.typography.body2
        )
        Text(
            text = "Board: ${if (boardOrientation == ChessColor.WHITE) "White" else "Black"} at bottom",
            style = MaterialTheme.typography.body2
        )
    }
}
