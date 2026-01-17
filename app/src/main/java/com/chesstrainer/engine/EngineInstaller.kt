package com.chesstrainer.engine

import android.content.Context
import android.os.Build
import com.chesstrainer.utils.EngineType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.net.URL
import java.security.MessageDigest

class EngineInstaller(private val context: Context) {
    enum class ArchiveType {
        NONE,
        TAR,
        ZIP
    }

    data class EngineDownloadSpec(
        val url: String,
        val sha256: String,
        val fileName: String,
        val archiveType: ArchiveType = ArchiveType.NONE,
        val archiveEntryPath: String? = null,
        val label: String
    )

    data class InstalledAssets(
        val engineBinary: File,
        val weightsFile: File?
    )

    data class EngineStatus(
        val engineType: EngineType,
        val engineAvailable: Boolean,
        val weightsAvailable: Boolean,
        val enginePath: String?,
        val weightsPath: String?,
        val statusMessage: String,
        val unsupportedAbiMessage: String?
    )

    private val baseDir = File(context.filesDir, "engines")

    fun getStatus(engineType: EngineType): EngineStatus {
        if (engineType == EngineType.GGUF) {
            val settings = com.chesstrainer.utils.Settings(context)
            val path = settings.ggufModelPath
            val exists = path?.let { File(it).exists() } == true
            return EngineStatus(
                engineType,
                engineAvailable = true, // Assumed available or not needed
                weightsAvailable = exists,
                enginePath = null,
                weightsPath = path,
                statusMessage = if (exists) "Model Ready" else "Model Path Missing",
                unsupportedAbiMessage = null
            )
        }
        val abi = findSupportedAbi(engineType)
        val unsupportedMessage = unsupportedAbiMessage(engineType)
        val engineFile = abi?.let { resolveEngineBinary(engineType, it) }
        val engineAvailable = engineFile?.let { isExecutableBinary(it) } == true
        val weightsFile = if (engineType == EngineType.LEELA_CHESS_ZERO) {
            resolveWeightsFile()
        } else {
            null
        }
        val weightsAvailable = weightsFile?.exists() != false
        val statusMessage = when {
            abi == null -> "Unsupported ABI"
            engineAvailable && weightsAvailable -> "Installed"
            !engineAvailable -> "Engine binary missing"
            engineType == EngineType.LEELA_CHESS_ZERO && !weightsAvailable -> "Network weights missing"
            else -> "Not installed"
        }
        android.util.Log.d("EngineInstaller", "Status for $engineType: $statusMessage (Engine: $engineAvailable, Weights: $weightsAvailable, ABI: $abi)")
        return EngineStatus(
            engineType = engineType,
            engineAvailable = engineAvailable,
            weightsAvailable = weightsAvailable,
            enginePath = engineFile?.absolutePath,
            weightsPath = weightsFile?.absolutePath,
            statusMessage = statusMessage,
            unsupportedAbiMessage = unsupportedMessage
        )
    }

    suspend fun ensureInstalled(
        engineType: EngineType,
        onStatusUpdate: (String) -> Unit = {}
    ): Result<InstalledAssets> = withContext(Dispatchers.IO) {
        try {
            if (engineType == EngineType.GGUF) {
                val settings = com.chesstrainer.utils.Settings(context)
                val path = settings.ggufModelPath
                return@withContext if (!path.isNullOrBlank() && File(path).exists()) {
                    // For GGUF, we currently rely on the model path as the primary asset.
                    // The "binary" is a placeholder until we have a bundled executor or user setting.
                    Result.success(InstalledAssets(File("/dev/null"), File(path)))
                } else {
                    Result.failure(Exception("GGUF Model path not set or invalid."))
                }
            }

            baseDir.mkdirs()
            val abi = findSupportedAbi(engineType)
                ?: return@withContext Result.failure(
                    Exception(unsupportedAbiMessage(engineType) ?: "No supported ABI download available.")
                )
            val engineSpec = engineBinarySpec(engineType, abi)
                ?: return@withContext Result.failure(Exception("No download spec configured for $engineType/$abi."))
            val engineBinary = resolveEngineBinary(engineType, abi)

            if (!engineBinary.exists() || !engineBinary.canExecute() || !isExecutableBinary(engineBinary)) {
                onStatusUpdate("Downloading ${engineSpec.label}...")
                downloadAndVerify(engineSpec, engineBinary, onStatusUpdate)
                ensureExecutable(engineBinary)

                if (!isExecutableBinary(engineBinary)) {
                     // Check one last time if it's executable via file system
                     if (!engineBinary.canExecute()) {
                        return@withContext Result.failure(Exception("Downloaded engine binary is not executable (chmod failed)."))
                     }
                }
            } else {
                // Ensure executable permissions even for existing files
                ensureExecutable(engineBinary)
            }

            val weightsFile = if (engineType == EngineType.LEELA_CHESS_ZERO) {
                val weightsSpec = lc0WeightsSpec()
                    ?: return@withContext Result.failure(Exception("No LC0 weights download spec configured."))
                val targetWeights = resolveWeightsFile()
                if (!targetWeights.exists()) {
                    onStatusUpdate("Downloading ${weightsSpec.label}...")
                    downloadAndVerify(weightsSpec, targetWeights, onStatusUpdate)
                }
                targetWeights
            } else {
                null
            }

            Result.success(InstalledAssets(engineBinary, weightsFile))
        } catch (e: Exception) {
            Result.failure(Exception("Engine install failed: ${e.message}", e))
        }
    }

    private fun ensureExecutable(file: File) {
        try {
            android.util.Log.d("EngineInstaller", "Attempting to make executable: ${file.absolutePath}")
            
            // 1. Java API
            if (file.setExecutable(true, false)) { // owner only = false (all)
                android.util.Log.d("EngineInstaller", "Java setExecutable(true) returned true")
            } else {
                android.util.Log.w("EngineInstaller", "Java setExecutable(true) returned false")
            }

            // 2. Shell chmod 755
            val process = ProcessBuilder("chmod", "755", file.absolutePath)
                .redirectErrorStream(true)
                .start()
            
            val output = process.inputStream.bufferedReader().readText()
            val exitCode = process.waitFor()
            
            if (exitCode == 0) {
                android.util.Log.d("EngineInstaller", "chmod 755 success")
            } else {
                android.util.Log.e("EngineInstaller", "chmod 755 failed (exit $exitCode): $output")
                if (output.contains("Operation not permitted") || output.contains("Permission denied")) {
                     android.util.Log.e("EngineInstaller", "System appears to block chmod (W^X violation likely).")
                }
            }
        } catch (e: Exception) {
            android.util.Log.e("EngineInstaller", "Failed to set executable permissions", e)
        }
    }

    private fun findSupportedAbi(engineType: EngineType): String? {
        val candidates = supportedAbis(engineType)
        return Build.SUPPORTED_ABIS.firstOrNull { abi -> abi in candidates }
    }

    private fun supportedAbis(engineType: EngineType): Set<String> {
        return when (engineType) {
            EngineType.STOCKFISH -> stockfishDownloadSpecs().keys
            EngineType.LEELA_CHESS_ZERO -> lc0DownloadSpecs().keys
            EngineType.GGUF -> emptySet()
        }.toSet()
    }

    private fun unsupportedAbiMessage(engineType: EngineType): String? {
        val candidates = supportedAbis(engineType)
        if (Build.SUPPORTED_ABIS.any { it in candidates }) {
            return null
        }
        val deviceAbi = Build.SUPPORTED_ABIS.firstOrNull() ?: "unknown ABI"
        val suggestedAbi = candidates.firstOrNull { it.startsWith("arm64") } ?: candidates.firstOrNull() ?: "arm64"
        return "No engine available for $deviceAbi; please use $suggestedAbi device."
    }

    private fun resolveEngineBinary(engineType: EngineType, abi: String): File {
        val engineDir = File(baseDir, engineType.name.lowercase())
        val abiDir = File(engineDir, abi)
        abiDir.mkdirs()
        return File(abiDir, engineBinaryName(engineType))
    }

    private fun resolveWeightsFile(): File {
        val engineDir = File(baseDir, EngineType.LEELA_CHESS_ZERO.name.lowercase())
        engineDir.mkdirs()
        return File(engineDir, lc0WeightsSpec()?.fileName ?: "lc0-network.pb.gz")
    }

    private fun engineBinaryName(engineType: EngineType): String {
        return when (engineType) {
            EngineType.STOCKFISH -> "stockfish"
            EngineType.LEELA_CHESS_ZERO -> "lc0"
            EngineType.GGUF -> "gguf-engine"
        }
    }

    private fun engineBinarySpec(engineType: EngineType, abi: String): EngineDownloadSpec? {
        return when (engineType) {
            EngineType.STOCKFISH -> stockfishDownloadSpecs()[abi]
            EngineType.LEELA_CHESS_ZERO -> lc0DownloadSpecs()[abi]
            EngineType.GGUF -> null
        }
    }

    private fun downloadAndVerify(
        spec: EngineDownloadSpec,
        targetFile: File,
        onStatusUpdate: (String) -> Unit
    ) {
        targetFile.parentFile?.mkdirs()
        val tempFile = File(targetFile.parentFile, "${targetFile.name}.download")
        val connection = URL(spec.url).openConnection()
        val contentLength = connection.contentLengthLong
        var bytesRead = 0L
        var lastProgress = -1
        connection.getInputStream().use { input ->
            FileOutputStream(tempFile).use { output ->
                val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
                var read = input.read(buffer)
                while (read >= 0) {
                    if (read > 0) {
                        output.write(buffer, 0, read)
                        bytesRead += read
                        if (contentLength > 0) {
                            val progress = ((bytesRead * 100) / contentLength).toInt()
                            if (progress != lastProgress && progress % 5 == 0) {
                                onStatusUpdate("Downloading ${spec.label}... $progress%")
                                lastProgress = progress
                            }
                        }
                    }
                    read = input.read(buffer)
                }
            }
        }
        onStatusUpdate("Verifying ${spec.label}...")
        val actualHash = sha256(tempFile)
        if (!actualHash.equals(spec.sha256, ignoreCase = true)) {
            tempFile.delete()
            throw IllegalStateException("Checksum mismatch for ${spec.url}")
        }
        if (spec.archiveType == ArchiveType.NONE) {
            if (targetFile.exists()) {
                targetFile.delete()
            }
            if (!tempFile.renameTo(targetFile)) {
                throw IllegalStateException("Failed to move downloaded file into place.")
            }
            return
        }
        try {
            extractArchiveEntry(spec, tempFile, targetFile)
        } finally {
            tempFile.delete()
        }
    }

    private fun extractArchiveEntry(spec: EngineDownloadSpec, archiveFile: File, targetFile: File) {
        val entryPath = spec.archiveEntryPath
            ?: throw IllegalArgumentException("Missing archive entry path for ${spec.label}.")
        when (spec.archiveType) {
            ArchiveType.TAR -> extractTarEntry(archiveFile, entryPath, targetFile)
            ArchiveType.ZIP -> extractZipEntry(archiveFile, entryPath, targetFile)
            ArchiveType.NONE -> Unit
        }
    }

    private fun extractZipEntry(archiveFile: File, entryPath: String, targetFile: File) {
        java.util.zip.ZipInputStream(archiveFile.inputStream()).use { zipInput ->
            var entry = zipInput.nextEntry
            while (entry != null) {
                if (!entry.isDirectory && entry.name == entryPath) {
                    targetFile.outputStream().use { output ->
                        copyStream(zipInput, output)
                    }
                    return
                }
                entry = zipInput.nextEntry
            }
        }
        throw IllegalStateException("Archive entry not found: $entryPath")
    }

    private fun extractTarEntry(archiveFile: File, entryPath: String, targetFile: File) {
        archiveFile.inputStream().use { input ->
            val header = ByteArray(512)
            while (true) {
                if (!readFully(input, header)) {
                    break
                }
                if (header.all { it == 0.toByte() }) {
                    break
                }
                val name = header.copyOfRange(0, 100).toString(Charsets.US_ASCII).trimEnd('\u0000')
                val sizeText = header.copyOfRange(124, 136)
                    .toString(Charsets.US_ASCII)
                    .trim()
                    .trim('\u0000')
                val size = sizeText.toLongOrNull(8) ?: 0L
                if (name == entryPath) {
                    targetFile.outputStream().use { output ->
                        copyExact(input, output, size)
                    }
                    val padding = (512 - (size % 512)) % 512
                    if (padding > 0) {
                        skipFully(input, padding)
                    }
                    return
                } else {
                    skipFully(input, size)
                    val padding = (512 - (size % 512)) % 512
                    if (padding > 0) {
                        skipFully(input, padding)
                    }
                }
            }
        }
        throw IllegalStateException("Archive entry not found: $entryPath")
    }

    private fun copyStream(input: InputStream, output: FileOutputStream) {
        val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
        var read = input.read(buffer)
        while (read >= 0) {
            if (read > 0) {
                output.write(buffer, 0, read)
            }
            read = input.read(buffer)
        }
    }

    private fun readFully(input: InputStream, buffer: ByteArray): Boolean {
        var offset = 0
        while (offset < buffer.size) {
            val read = input.read(buffer, offset, buffer.size - offset)
            if (read == -1) {
                return false
            }
            offset += read
        }
        return true
    }

    private fun copyExact(input: InputStream, output: FileOutputStream, length: Long) {
        val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
        var remaining = length
        while (remaining > 0) {
            val read = input.read(buffer, 0, minOf(buffer.size.toLong(), remaining).toInt())
            if (read == -1) {
                throw IllegalStateException("Unexpected end of archive while extracting.")
            }
            output.write(buffer, 0, read)
            remaining -= read
        }
    }

    private fun skipFully(input: InputStream, bytes: Long) {
        var remaining = bytes
        while (remaining > 0) {
            val skipped = input.skip(remaining)
            if (skipped <= 0) {
                if (input.read() == -1) {
                    break
                }
                remaining -= 1
            } else {
                remaining -= skipped
            }
        }
    }

    private fun sha256(file: File): String {
        val digest = MessageDigest.getInstance("SHA-256")
        file.inputStream().use { input ->
            val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
            var read = input.read(buffer)
            while (read >= 0) {
                if (read > 0) {
                    digest.update(buffer, 0, read)
                }
                read = input.read(buffer)
            }
        }
        return digest.digest().joinToString("") { byte -> "%02x".format(byte) }
    }

    private fun isExecutableBinary(file: File): Boolean {
        if (!file.exists() || !file.canExecute()) {
            return false
        }
        return try {
            file.inputStream().use { input ->
                val header = ByteArray(20)
                val read = input.read(header)
                if (read < header.size) {
                    return false
                }
                val isElf = header[0] == 0x7f.toByte() &&
                    header[1] == 'E'.code.toByte() &&
                    header[2] == 'L'.code.toByte() &&
                    header[3] == 'F'.code.toByte()
                if (!isElf) {
                    return false
                }

                val type = (header[16].toInt() and 0xff) or ((header[17].toInt() and 0xff) shl 8)
                val isExe = type == 2 || type == 3
                if (!isExe) android.util.Log.e("EngineInstaller", "Binary ${file.name} is not executable (ELF type $type)")
                isExe
            }
        } catch (e: Exception) {
            android.util.Log.e("EngineInstaller", "Failed to check executable status for ${file.name}", e)
            false
        }
    }

    private fun stockfishDownloadSpecs(): Map<String, EngineDownloadSpec> {
        return EngineDownloadCatalog.stockfishDownloads
    }

    private fun lc0DownloadSpecs(): Map<String, EngineDownloadSpec> {
        return EngineDownloadCatalog.lc0Downloads
    }

    private fun lc0WeightsSpec(): EngineDownloadSpec? {
        return EngineDownloadCatalog.lc0Weights
    }
}
