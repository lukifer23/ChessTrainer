package com.chesstrainer.engine;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000~\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010$\n\u0002\b\u0003\n\u0002\u0010\u0012\n\u0002\b\u0007\n\u0002\u0010\"\n\u0002\b\u0006\u0018\u00002\u00020\u0001:\u0004<=>?B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J \u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u0018\u0010\u000f\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002J,\u0010\u0010\u001a\u00020\b2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00062\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020\b0\u0015H\u0002J\u0010\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J\u001a\u0010\u001a\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001b\u001a\u00020\u0016H\u0002J\u0010\u0010\u001c\u001a\u00020\b2\u0006\u0010\u001d\u001a\u00020\u0006H\u0002J:\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020 0\u001f2\u0006\u0010\u0018\u001a\u00020\u00192\u0014\b\u0002\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020\b0\u0015H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b!\u0010\"J \u0010#\u001a\u00020\b2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010$\u001a\u00020\u00062\u0006\u0010\u0013\u001a\u00020\u0006H\u0002J \u0010%\u001a\u00020\b2\u0006\u0010$\u001a\u00020\u00062\u0006\u0010&\u001a\u00020\u00162\u0006\u0010\u0013\u001a\u00020\u0006H\u0002J \u0010\'\u001a\u00020\b2\u0006\u0010$\u001a\u00020\u00062\u0006\u0010&\u001a\u00020\u00162\u0006\u0010\u0013\u001a\u00020\u0006H\u0002J\u0012\u0010(\u001a\u0004\u0018\u00010\u00162\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J\u000e\u0010)\u001a\u00020*2\u0006\u0010\u0018\u001a\u00020\u0019J\u0010\u0010+\u001a\u00020,2\u0006\u0010\u001d\u001a\u00020\u0006H\u0002J\u0014\u0010-\u001a\u000e\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020\u00120.H\u0002J\n\u0010/\u001a\u0004\u0018\u00010\u0012H\u0002J\u0018\u00100\u001a\u00020,2\u0006\u0010\t\u001a\u00020\n2\u0006\u00101\u001a\u000202H\u0002J\u0018\u00103\u001a\u00020\u00062\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001b\u001a\u00020\u0016H\u0002J\b\u00104\u001a\u00020\u0006H\u0002J\u0010\u00105\u001a\u00020\u00162\u0006\u0010\u001d\u001a\u00020\u0006H\u0002J\u0018\u00106\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u00107\u001a\u00020\u000eH\u0002J\u0014\u00108\u001a\u000e\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020\u00120.H\u0002J\u0016\u00109\u001a\b\u0012\u0004\u0012\u00020\u00160:2\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J\u0012\u0010;\u001a\u0004\u0018\u00010\u00162\u0006\u0010\u0018\u001a\u00020\u0019H\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006@"}, d2 = {"Lcom/chesstrainer/engine/EngineInstaller;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "baseDir", "Ljava/io/File;", "copyExact", "", "input", "Ljava/io/InputStream;", "output", "Ljava/io/FileOutputStream;", "length", "", "copyStream", "downloadAndVerify", "spec", "Lcom/chesstrainer/engine/EngineInstaller$EngineDownloadSpec;", "targetFile", "onStatusUpdate", "Lkotlin/Function1;", "", "engineBinaryName", "engineType", "Lcom/chesstrainer/utils/EngineType;", "engineBinarySpec", "abi", "ensureExecutable", "file", "ensureInstalled", "Lkotlin/Result;", "Lcom/chesstrainer/engine/EngineInstaller$InstalledAssets;", "ensureInstalled-0E7RQCE", "(Lcom/chesstrainer/utils/EngineType;Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "extractArchiveEntry", "archiveFile", "extractTarEntry", "entryPath", "extractZipEntry", "findSupportedAbi", "getStatus", "Lcom/chesstrainer/engine/EngineInstaller$EngineStatus;", "isExecutableBinary", "", "lc0DownloadSpecs", "", "lc0WeightsSpec", "readFully", "buffer", "", "resolveEngineBinary", "resolveWeightsFile", "sha256", "skipFully", "bytes", "stockfishDownloadSpecs", "supportedAbis", "", "unsupportedAbiMessage", "ArchiveType", "EngineDownloadSpec", "EngineStatus", "InstalledAssets", "app_debug"})
public final class EngineInstaller {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final java.io.File baseDir = null;
    
    public EngineInstaller(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.chesstrainer.engine.EngineInstaller.EngineStatus getStatus(@org.jetbrains.annotations.NotNull()
    com.chesstrainer.utils.EngineType engineType) {
        return null;
    }
    
    private final void ensureExecutable(java.io.File file) {
    }
    
    private final java.lang.String findSupportedAbi(com.chesstrainer.utils.EngineType engineType) {
        return null;
    }
    
    private final java.util.Set<java.lang.String> supportedAbis(com.chesstrainer.utils.EngineType engineType) {
        return null;
    }
    
    private final java.lang.String unsupportedAbiMessage(com.chesstrainer.utils.EngineType engineType) {
        return null;
    }
    
    private final java.io.File resolveEngineBinary(com.chesstrainer.utils.EngineType engineType, java.lang.String abi) {
        return null;
    }
    
    private final java.io.File resolveWeightsFile() {
        return null;
    }
    
    private final java.lang.String engineBinaryName(com.chesstrainer.utils.EngineType engineType) {
        return null;
    }
    
    private final com.chesstrainer.engine.EngineInstaller.EngineDownloadSpec engineBinarySpec(com.chesstrainer.utils.EngineType engineType, java.lang.String abi) {
        return null;
    }
    
    private final void downloadAndVerify(com.chesstrainer.engine.EngineInstaller.EngineDownloadSpec spec, java.io.File targetFile, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onStatusUpdate) {
    }
    
    private final void extractArchiveEntry(com.chesstrainer.engine.EngineInstaller.EngineDownloadSpec spec, java.io.File archiveFile, java.io.File targetFile) {
    }
    
    private final void extractZipEntry(java.io.File archiveFile, java.lang.String entryPath, java.io.File targetFile) {
    }
    
    private final void extractTarEntry(java.io.File archiveFile, java.lang.String entryPath, java.io.File targetFile) {
    }
    
    private final void copyStream(java.io.InputStream input, java.io.FileOutputStream output) {
    }
    
    private final boolean readFully(java.io.InputStream input, byte[] buffer) {
        return false;
    }
    
    private final void copyExact(java.io.InputStream input, java.io.FileOutputStream output, long length) {
    }
    
    private final void skipFully(java.io.InputStream input, long bytes) {
    }
    
    private final java.lang.String sha256(java.io.File file) {
        return null;
    }
    
    private final boolean isExecutableBinary(java.io.File file) {
        return false;
    }
    
    private final java.util.Map<java.lang.String, com.chesstrainer.engine.EngineInstaller.EngineDownloadSpec> stockfishDownloadSpecs() {
        return null;
    }
    
    private final java.util.Map<java.lang.String, com.chesstrainer.engine.EngineInstaller.EngineDownloadSpec> lc0DownloadSpecs() {
        return null;
    }
    
    private final com.chesstrainer.engine.EngineInstaller.EngineDownloadSpec lc0WeightsSpec() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005\u00a8\u0006\u0006"}, d2 = {"Lcom/chesstrainer/engine/EngineInstaller$ArchiveType;", "", "(Ljava/lang/String;I)V", "NONE", "TAR", "ZIP", "app_debug"})
    public static enum ArchiveType {
        /*public static final*/ NONE /* = new NONE() */,
        /*public static final*/ TAR /* = new TAR() */,
        /*public static final*/ ZIP /* = new ZIP() */;
        
        ArchiveType() {
        }
        
        @org.jetbrains.annotations.NotNull()
        public static kotlin.enums.EnumEntries<com.chesstrainer.engine.EngineInstaller.ArchiveType> getEntries() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0013\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B;\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\t\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\nJ\t\u0010\u0013\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0014\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0015\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0016\u001a\u00020\u0007H\u00c6\u0003J\u000b\u0010\u0017\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u0010\u0018\u001a\u00020\u0003H\u00c6\u0003JG\u0010\u0019\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00072\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\t\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\u001a\u001a\u00020\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001d\u001a\u00020\u001eH\u00d6\u0001J\t\u0010\u001f\u001a\u00020\u0003H\u00d6\u0001R\u0013\u0010\b\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\fR\u0011\u0010\t\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\f\u00a8\u0006 "}, d2 = {"Lcom/chesstrainer/engine/EngineInstaller$EngineDownloadSpec;", "", "url", "", "sha256", "fileName", "archiveType", "Lcom/chesstrainer/engine/EngineInstaller$ArchiveType;", "archiveEntryPath", "label", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lcom/chesstrainer/engine/EngineInstaller$ArchiveType;Ljava/lang/String;Ljava/lang/String;)V", "getArchiveEntryPath", "()Ljava/lang/String;", "getArchiveType", "()Lcom/chesstrainer/engine/EngineInstaller$ArchiveType;", "getFileName", "getLabel", "getSha256", "getUrl", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
    public static final class EngineDownloadSpec {
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String url = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String sha256 = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String fileName = null;
        @org.jetbrains.annotations.NotNull()
        private final com.chesstrainer.engine.EngineInstaller.ArchiveType archiveType = null;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.String archiveEntryPath = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String label = null;
        
        public EngineDownloadSpec(@org.jetbrains.annotations.NotNull()
        java.lang.String url, @org.jetbrains.annotations.NotNull()
        java.lang.String sha256, @org.jetbrains.annotations.NotNull()
        java.lang.String fileName, @org.jetbrains.annotations.NotNull()
        com.chesstrainer.engine.EngineInstaller.ArchiveType archiveType, @org.jetbrains.annotations.Nullable()
        java.lang.String archiveEntryPath, @org.jetbrains.annotations.NotNull()
        java.lang.String label) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getUrl() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getSha256() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getFileName() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.chesstrainer.engine.EngineInstaller.ArchiveType getArchiveType() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String getArchiveEntryPath() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getLabel() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component3() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.chesstrainer.engine.EngineInstaller.ArchiveType component4() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String component5() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component6() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.chesstrainer.engine.EngineInstaller.EngineDownloadSpec copy(@org.jetbrains.annotations.NotNull()
        java.lang.String url, @org.jetbrains.annotations.NotNull()
        java.lang.String sha256, @org.jetbrains.annotations.NotNull()
        java.lang.String fileName, @org.jetbrains.annotations.NotNull()
        com.chesstrainer.engine.EngineInstaller.ArchiveType archiveType, @org.jetbrains.annotations.Nullable()
        java.lang.String archiveEntryPath, @org.jetbrains.annotations.NotNull()
        java.lang.String label) {
            return null;
        }
        
        @java.lang.Override()
        public boolean equals(@org.jetbrains.annotations.Nullable()
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override()
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String toString() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0019\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001BC\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\b\u0010\t\u001a\u0004\u0018\u00010\b\u0012\u0006\u0010\n\u001a\u00020\b\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\b\u00a2\u0006\u0002\u0010\fJ\t\u0010\u0017\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0018\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0019\u001a\u00020\u0005H\u00c6\u0003J\u000b\u0010\u001a\u001a\u0004\u0018\u00010\bH\u00c6\u0003J\u000b\u0010\u001b\u001a\u0004\u0018\u00010\bH\u00c6\u0003J\t\u0010\u001c\u001a\u00020\bH\u00c6\u0003J\u000b\u0010\u001d\u001a\u0004\u0018\u00010\bH\u00c6\u0003JU\u0010\u001e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b2\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\b2\b\b\u0002\u0010\n\u001a\u00020\b2\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\bH\u00c6\u0001J\u0013\u0010\u001f\u001a\u00020\u00052\b\u0010 \u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010!\u001a\u00020\"H\u00d6\u0001J\t\u0010#\u001a\u00020\bH\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0013\u0010\u0007\u001a\u0004\u0018\u00010\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\n\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0010R\u0013\u0010\u000b\u001a\u0004\u0018\u00010\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0010R\u0011\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u000eR\u0013\u0010\t\u001a\u0004\u0018\u00010\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0010\u00a8\u0006$"}, d2 = {"Lcom/chesstrainer/engine/EngineInstaller$EngineStatus;", "", "engineType", "Lcom/chesstrainer/utils/EngineType;", "engineAvailable", "", "weightsAvailable", "enginePath", "", "weightsPath", "statusMessage", "unsupportedAbiMessage", "(Lcom/chesstrainer/utils/EngineType;ZZLjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getEngineAvailable", "()Z", "getEnginePath", "()Ljava/lang/String;", "getEngineType", "()Lcom/chesstrainer/utils/EngineType;", "getStatusMessage", "getUnsupportedAbiMessage", "getWeightsAvailable", "getWeightsPath", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "equals", "other", "hashCode", "", "toString", "app_debug"})
    public static final class EngineStatus {
        @org.jetbrains.annotations.NotNull()
        private final com.chesstrainer.utils.EngineType engineType = null;
        private final boolean engineAvailable = false;
        private final boolean weightsAvailable = false;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.String enginePath = null;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.String weightsPath = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String statusMessage = null;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.String unsupportedAbiMessage = null;
        
        public EngineStatus(@org.jetbrains.annotations.NotNull()
        com.chesstrainer.utils.EngineType engineType, boolean engineAvailable, boolean weightsAvailable, @org.jetbrains.annotations.Nullable()
        java.lang.String enginePath, @org.jetbrains.annotations.Nullable()
        java.lang.String weightsPath, @org.jetbrains.annotations.NotNull()
        java.lang.String statusMessage, @org.jetbrains.annotations.Nullable()
        java.lang.String unsupportedAbiMessage) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.chesstrainer.utils.EngineType getEngineType() {
            return null;
        }
        
        public final boolean getEngineAvailable() {
            return false;
        }
        
        public final boolean getWeightsAvailable() {
            return false;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String getEnginePath() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String getWeightsPath() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getStatusMessage() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String getUnsupportedAbiMessage() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.chesstrainer.utils.EngineType component1() {
            return null;
        }
        
        public final boolean component2() {
            return false;
        }
        
        public final boolean component3() {
            return false;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String component4() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String component5() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component6() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String component7() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.chesstrainer.engine.EngineInstaller.EngineStatus copy(@org.jetbrains.annotations.NotNull()
        com.chesstrainer.utils.EngineType engineType, boolean engineAvailable, boolean weightsAvailable, @org.jetbrains.annotations.Nullable()
        java.lang.String enginePath, @org.jetbrains.annotations.Nullable()
        java.lang.String weightsPath, @org.jetbrains.annotations.NotNull()
        java.lang.String statusMessage, @org.jetbrains.annotations.Nullable()
        java.lang.String unsupportedAbiMessage) {
            return null;
        }
        
        @java.lang.Override()
        public boolean equals(@org.jetbrains.annotations.Nullable()
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override()
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String toString() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u0005J\t\u0010\t\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010\n\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u001f\u0010\u000b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003H\u00c6\u0001J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u000f\u001a\u00020\u0010H\u00d6\u0001J\t\u0010\u0011\u001a\u00020\u0012H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007\u00a8\u0006\u0013"}, d2 = {"Lcom/chesstrainer/engine/EngineInstaller$InstalledAssets;", "", "engineBinary", "Ljava/io/File;", "weightsFile", "(Ljava/io/File;Ljava/io/File;)V", "getEngineBinary", "()Ljava/io/File;", "getWeightsFile", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "", "app_debug"})
    public static final class InstalledAssets {
        @org.jetbrains.annotations.NotNull()
        private final java.io.File engineBinary = null;
        @org.jetbrains.annotations.Nullable()
        private final java.io.File weightsFile = null;
        
        public InstalledAssets(@org.jetbrains.annotations.NotNull()
        java.io.File engineBinary, @org.jetbrains.annotations.Nullable()
        java.io.File weightsFile) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.io.File getEngineBinary() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.io.File getWeightsFile() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.io.File component1() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.io.File component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.chesstrainer.engine.EngineInstaller.InstalledAssets copy(@org.jetbrains.annotations.NotNull()
        java.io.File engineBinary, @org.jetbrains.annotations.Nullable()
        java.io.File weightsFile) {
            return null;
        }
        
        @java.lang.Override()
        public boolean equals(@org.jetbrains.annotations.Nullable()
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override()
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String toString() {
            return null;
        }
    }
}