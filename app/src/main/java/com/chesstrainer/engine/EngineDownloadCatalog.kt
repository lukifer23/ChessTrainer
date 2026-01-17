package com.chesstrainer.engine

object EngineDownloadCatalog {
    const val STOCKFISH_VERSION = "16.1"
    const val LC0_VERSION = "0.32.1"
    const val LC0_WEIGHTS_ID = "9149"

    // Stockfish publishes Android binaries for ARM ABIs only; x86 devices are currently unsupported.
    val stockfishDownloads: Map<String, EngineInstaller.EngineDownloadSpec> = mapOf(
        "arm64-v8a" to EngineInstaller.EngineDownloadSpec(
            url = "https://github.com/official-stockfish/Stockfish/releases/download/sf_16.1/stockfish-android-armv8.tar",
            sha256 = "ed413bd912824fc5fda3a0eee9f748ee5b34324dd78aae5c32952d9234e8d60a",
            fileName = "stockfish",
            archiveType = EngineInstaller.ArchiveType.TAR,
            archiveEntryPath = "stockfish/stockfish-android-armv8",
            label = "Stockfish 16.1 (arm64-v8a)"
        ),
        "armeabi-v7a" to EngineInstaller.EngineDownloadSpec(
            url = "https://github.com/official-stockfish/Stockfish/releases/download/sf_16.1/stockfish-android-armv7.tar",
            sha256 = "492fd57fa91a2a556b2e82553a489098745217b27df823951661f8c91f46721f",
            fileName = "stockfish",
            archiveType = EngineInstaller.ArchiveType.TAR,
            archiveEntryPath = "stockfish/stockfish-android-armv7",
            label = "Stockfish 16.1 (armeabi-v7a)"
        )
    )

    // LC0 Android releases ship a PIE CLI binary named liblc0.so inside the APK container.
    val lc0Downloads: Map<String, EngineInstaller.EngineDownloadSpec> = mapOf(
        "arm64-v8a" to EngineInstaller.EngineDownloadSpec(
            url = "https://github.com/LeelaChessZero/lc0/releases/download/v0.32.1/lc0-v0.32.1-android.apk",
            sha256 = "1ceb6ac5dd454fd41417debebc1ecd40172f0a83d451b67a9fd1fa1e30470a34",
            fileName = "lc0",
            archiveType = EngineInstaller.ArchiveType.ZIP,
            archiveEntryPath = "lib/arm64-v8a/liblc0.so",
            label = "LC0 0.32.1 CLI (arm64-v8a)"
        ),
        "armeabi-v7a" to EngineInstaller.EngineDownloadSpec(
            url = "https://github.com/LeelaChessZero/lc0/releases/download/v0.32.1/lc0-v0.32.1-android.apk",
            sha256 = "1ceb6ac5dd454fd41417debebc1ecd40172f0a83d451b67a9fd1fa1e30470a34",
            fileName = "lc0",
            archiveType = EngineInstaller.ArchiveType.ZIP,
            archiveEntryPath = "lib/armeabi-v7a/liblc0.so",
            label = "LC0 0.32.1 CLI (armeabi-v7a)"
        ),
        "x86" to EngineInstaller.EngineDownloadSpec(
            url = "https://github.com/LeelaChessZero/lc0/releases/download/v0.32.1/lc0-v0.32.1-android.apk",
            sha256 = "1ceb6ac5dd454fd41417debebc1ecd40172f0a83d451b67a9fd1fa1e30470a34",
            fileName = "lc0",
            archiveType = EngineInstaller.ArchiveType.ZIP,
            archiveEntryPath = "lib/x86/liblc0.so",
            label = "LC0 0.32.1 CLI (x86)"
        ),
        "x86_64" to EngineInstaller.EngineDownloadSpec(
            url = "https://github.com/LeelaChessZero/lc0/releases/download/v0.32.1/lc0-v0.32.1-android.apk",
            sha256 = "1ceb6ac5dd454fd41417debebc1ecd40172f0a83d451b67a9fd1fa1e30470a34",
            fileName = "lc0",
            archiveType = EngineInstaller.ArchiveType.ZIP,
            archiveEntryPath = "lib/x86_64/liblc0.so",
            label = "LC0 0.32.1 CLI (x86_64)"
        )
    )

    val lc0Weights: EngineInstaller.EngineDownloadSpec = EngineInstaller.EngineDownloadSpec(
        url = "https://github.com/dkappe/leela-chess-weights/files/4773441/weights_9149.pb.gz",
        sha256 = "0319fc216a271c2e8c1acdb0ff0ad7e2a53748379cd102d42b376b2e2fcc0056",
        fileName = "weights_9149.pb.gz",
        archiveType = EngineInstaller.ArchiveType.NONE,
        archiveEntryPath = null,
        label = "LC0 network weights 9149"
    )
}
