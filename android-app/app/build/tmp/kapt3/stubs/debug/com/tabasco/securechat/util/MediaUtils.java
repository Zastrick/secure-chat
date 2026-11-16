package com.tabasco.securechat.util;

/**
 * Nota honesta: como cada archivo va incrustado en Base64 dentro de un
 * único mensaje JSON (sin partirlo en pedazos), este prototipo no está
 * pensado para archivos grandes. Las imágenes se redimensionan antes de
 * enviarse; para archivos genéricos se pone un límite razonable.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\bJ\u000e\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u000bJ\u0016\u0010\f\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010J\u0016\u0010\u0011\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010J\u0018\u0010\u0012\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010J(\u0010\u0013\u001a\u0004\u0018\u00010\b2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0016"}, d2 = {"Lcom/tabasco/securechat/util/MediaUtils;", "", "()V", "MAX_FILE_BYTES", "", "base64ToBitmap", "Landroid/graphics/Bitmap;", "base64", "", "bytesToBase64", "bytes", "", "fileNameFromUri", "context", "Landroid/content/Context;", "uri", "Landroid/net/Uri;", "mimeTypeFromUri", "readBytes", "resizedImageBase64", "maxDimPx", "quality", "app_debug"})
public final class MediaUtils {
    public static final int MAX_FILE_BYTES = 3145728;
    @org.jetbrains.annotations.NotNull()
    public static final com.tabasco.securechat.util.MediaUtils INSTANCE = null;
    
    private MediaUtils() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String fileNameFromUri(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    android.net.Uri uri) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String mimeTypeFromUri(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    android.net.Uri uri) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final byte[] readBytes(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    android.net.Uri uri) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String bytesToBase64(@org.jetbrains.annotations.NotNull()
    byte[] bytes) {
        return null;
    }
    
    /**
     * Redimensiona una imagen a un máximo de [maxDimPx] por lado y la comprime a JPEG.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String resizedImageBase64(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    android.net.Uri uri, int maxDimPx, int quality) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final android.graphics.Bitmap base64ToBitmap(@org.jetbrains.annotations.NotNull()
    java.lang.String base64) {
        return null;
    }
}