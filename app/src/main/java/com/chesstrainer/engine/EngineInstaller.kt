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
    data class EngineDownloadSpec(
        val url: String,
        val sha256: String,
        val fileName: String
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
        val statusMessage: String
    )

    private val baseDir = File(context.filesDir, "engines")

    fun getStatus(engineType: EngineType): EngineStatus {
        val abi = findSupportedAbi(engineType)
        val engineFile = abi?.let { resolveEngineBinary(engineType, it) }
        val engineAvailable = engineFile?.let { it.exists() && it.canExecute() } == true
        val weightsFile = if (engineType == EngineType.LEELA_CHESS_ZERO) {
            resolveWeightsFile()
        } else {
            null
        }
        val weightsAvailable = weightsFile?.exists() != false
        val statusMessage = when {
            engineAvailable && weightsAvailable -> "Installed"
            !engineAvailable -> "Engine binary missing"
            engineType == EngineType.LEELA_CHESS_ZERO && !weightsAvailable -> "Network weights missing"
            else -> "Not installed"
        }
        return EngineStatus(
            engineType = engineType,
            engineAvailable = engineAvailable,
            weightsAvailable = weightsAvailable,
            enginePath = engineFile?.absolutePath,
            weightsPath = weightsFile?.absolutePath,
            statusMessage = statusMessage
        )
    }

    suspend fun ensureInstalled(engineType: EngineType): Result<InstalledAssets> = withContext(Dispatchers.IO) {
        try {
            baseDir.mkdirs()
            val abi = findSupportedAbi(engineType)
                ?: return@withContext Result.failure(Exception("No supported ABI download available."))
            val engineSpec = engineBinarySpec(engineType, abi)
                ?: return@withContext Result.failure(Exception("No download spec configured for $engineType/$abi."))
            val engineBinary = resolveEngineBinary(engineType, abi)

            if (!engineBinary.exists() || !engineBinary.canExecute()) {
                downloadAndVerify(engineSpec, engineBinary)
                if (!engineBinary.setExecutable(true)) {
                    return@withContext Result.failure(Exception("Failed to mark engine binary executable."))
                }
            }

            val weightsFile = if (engineType == EngineType.LEELA_CHESS_ZERO) {
                val weightsSpec = lc0WeightsSpec()
                    ?: return@withContext Result.failure(Exception("No LC0 weights download spec configured."))
                val targetWeights = resolveWeightsFile()
                if (!targetWeights.exists()) {
                    downloadAndVerify(weightsSpec, targetWeights)
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

    private fun findSupportedAbi(engineType: EngineType): String? {
        val candidates = when (engineType) {
            EngineType.STOCKFISH -> stockfishDownloadSpecs().keys
            EngineType.LEELA_CHESS_ZERO -> lc0DownloadSpecs().keys
        }
        return Build.SUPPORTED_ABIS.firstOrNull { abi -> abi in candidates }
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
        }
    }

    private fun engineBinarySpec(engineType: EngineType, abi: String): EngineDownloadSpec? {
        return when (engineType) {
            EngineType.STOCKFISH -> stockfishDownloadSpecs()[abi]
            EngineType.LEELA_CHESS_ZERO -> lc0DownloadSpecs()[abi]
        }
    }

    private fun downloadAndVerify(spec: EngineDownloadSpec, targetFile: File) {
        targetFile.parentFile?.mkdirs()
        val tempFile = File(targetFile.parentFile, "${targetFile.name}.download")
        URL(spec.url).openStream().use { input ->
            FileOutputStream(tempFile).use { output ->
                copyWithDigest(input, output)
            }
        }
        val actualHash = sha256(tempFile)
        if (!actualHash.equals(spec.sha256, ignoreCase = true)) {
            tempFile.delete()
            throw IllegalStateException("Checksum mismatch for ${spec.url}")
        }
        if (targetFile.exists()) {
            targetFile.delete()
        }
        if (!tempFile.renameTo(targetFile)) {
            throw IllegalStateException("Failed to move downloaded file into place.")
        }
    }

    private fun copyWithDigest(input: InputStream, output: FileOutputStream) {
        val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
        var read = input.read(buffer)
        while (read >= 0) {
            if (read > 0) {
                output.write(buffer, 0, read)
            }
            read = input.read(buffer)
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

    private fun stockfishDownloadSpecs(): Map<String, EngineDownloadSpec> {
        return mapOf(
            "arm64-v8a" to EngineDownloadSpec(
                url = "https://example.com/engines/stockfish/android/arm64/stockfish",
                sha256 = "REPLACE_WITH_SHA256",
                fileName = "stockfish"
            ),
            "armeabi-v7a" to EngineDownloadSpec(
                url = "https://example.com/engines/stockfish/android/armeabi-v7a/stockfish",
                sha256 = "REPLACE_WITH_SHA256",
                fileName = "stockfish"
            )
        )
    }

    private fun lc0DownloadSpecs(): Map<String, EngineDownloadSpec> {
        return mapOf(
            "arm64-v8a" to EngineDownloadSpec(
                url = "https://example.com/engines/lc0/android/arm64/lc0",
                sha256 = "REPLACE_WITH_SHA256",
                fileName = "lc0"
            ),
            "armeabi-v7a" to EngineDownloadSpec(
                url = "https://example.com/engines/lc0/android/armeabi-v7a/lc0",
                sha256 = "REPLACE_WITH_SHA256",
                fileName = "lc0"
            )
        )
    }

    private fun lc0WeightsSpec(): EngineDownloadSpec? {
        return EngineDownloadSpec(
            url = "https://example.com/engines/lc0/networks/network.pb.gz",
            sha256 = "REPLACE_WITH_SHA256",
            fileName = "lc0-network.pb.gz"
        )
    }
}
