package com.tabasco.securechat.crypto;

/**
 * CryptoManager
 *
 * Cada usuario genera su propio par de llaves EN SU TELÉFONO. La llave
 * privada nunca sale del dispositivo. La llave pública ES tu "Session ID".
 *
 * Usamos ECIES sobre P-256 vía Tink (biblioteca de Google, auditada).
 *
 * Nota de formato: serializamos los keysets en formato BINARIO (no JSON)
 * antes de codificarlos en Base64. Esto hace que el Session ID que compartes
 * sea considerablemente más corto que si usáramos JSON, sin perder nada de
 * seguridad — es el mismo contenido, solo más compacto.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\b\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\u0006J\u0016\u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\u0006J\u0006\u0010\r\u001a\u00020\u0006J\u0006\u0010\u000e\u001a\u00020\u000fJ\u0010\u0010\u0010\u001a\n \u0012*\u0004\u0018\u00010\u00110\u0011H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082D\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0013"}, d2 = {"Lcom/tabasco/securechat/crypto/CryptoManager;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "prefsName", "", "privateKeysetPrefKey", "decrypt", "ciphertextBase64", "encryptFor", "recipientPublicIdBase64", "plaintext", "getMyPublicIdBase64", "getOrCreateMyKeysetHandle", "Lcom/google/crypto/tink/KeysetHandle;", "prefs", "Landroid/content/SharedPreferences;", "kotlin.jvm.PlatformType", "app_debug"})
public final class CryptoManager {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String prefsName = "securechat_keys";
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String privateKeysetPrefKey = "private_keyset_b64";
    
    public CryptoManager(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    private final android.content.SharedPreferences prefs() {
        return null;
    }
    
    /**
     * Devuelve el keyset privado existente, o genera uno nuevo la primera vez.
     */
    @org.jetbrains.annotations.NotNull()
    public final com.google.crypto.tink.KeysetHandle getOrCreateMyKeysetHandle() {
        return null;
    }
    
    /**
     * Mi "Session ID": la llave pública, codificada de forma compacta para compartir.
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getMyPublicIdBase64() {
        return null;
    }
    
    /**
     * Cifra un mensaje de texto usando la llave pública del DESTINATARIO.
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String encryptFor(@org.jetbrains.annotations.NotNull()
    java.lang.String recipientPublicIdBase64, @org.jetbrains.annotations.NotNull()
    java.lang.String plaintext) {
        return null;
    }
    
    /**
     * Descifra un mensaje recibido usando MI llave privada.
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String decrypt(@org.jetbrains.annotations.NotNull()
    java.lang.String ciphertextBase64) {
        return null;
    }
}