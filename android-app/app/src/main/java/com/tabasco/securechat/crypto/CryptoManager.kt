package com.tabasco.securechat.crypto

import android.content.Context
import android.util.Base64
import com.google.crypto.tink.BinaryKeysetReader
import com.google.crypto.tink.BinaryKeysetWriter
import com.google.crypto.tink.CleartextKeysetHandle
import com.google.crypto.tink.HybridDecrypt
import com.google.crypto.tink.HybridEncrypt
import com.google.crypto.tink.KeysetHandle
import com.google.crypto.tink.hybrid.HybridConfig
import com.google.crypto.tink.hybrid.HybridKeyTemplates
import java.io.ByteArrayOutputStream

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
class CryptoManager(private val context: Context) {

    private val prefsName = "securechat_keys"
    private val privateKeysetPrefKey = "private_keyset_b64"

    init {
        HybridConfig.register()
    }

    private fun prefs() =
        context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)

    /** Devuelve el keyset privado existente, o genera uno nuevo la primera vez. */
    fun getOrCreateMyKeysetHandle(): KeysetHandle {
        val saved = prefs().getString(privateKeysetPrefKey, null)
        if (saved != null) {
            try {
                val bytes = Base64.decode(saved, Base64.NO_WRAP)
                return CleartextKeysetHandle.read(BinaryKeysetReader.withBytes(bytes))
            } catch (e: Exception) {
                // Llave guardada con un formato viejo (de una versión anterior de la app).
                // La reemplazamos por una nueva en vez de tronar la app.
                prefs().edit().remove(privateKeysetPrefKey).apply()
            }
        }
        val handle = KeysetHandle.generateNew(HybridKeyTemplates.ECIES_P256_HKDF_HMAC_SHA256_AES128_GCM)
        val out = ByteArrayOutputStream()
        CleartextKeysetHandle.write(handle, BinaryKeysetWriter.withOutputStream(out))
        prefs().edit()
            .putString(privateKeysetPrefKey, Base64.encodeToString(out.toByteArray(), Base64.NO_WRAP))
            .apply()
        return handle
    }

    /** Mi "Session ID": la llave pública, codificada de forma compacta para compartir. */
    fun getMyPublicIdBase64(): String {
        val handle = getOrCreateMyKeysetHandle()
        val publicHandle = handle.publicKeysetHandle
        val out = ByteArrayOutputStream()
        CleartextKeysetHandle.write(publicHandle, BinaryKeysetWriter.withOutputStream(out))
        return Base64.encodeToString(out.toByteArray(), Base64.NO_WRAP)
    }

    /** Cifra un mensaje de texto usando la llave pública del DESTINATARIO. */
    fun encryptFor(recipientPublicIdBase64: String, plaintext: String): String {
        val cleanId = recipientPublicIdBase64.trim()
        val pubKeysetBytes = Base64.decode(cleanId, Base64.NO_WRAP)
        val recipientPublicHandle = KeysetHandle.readNoSecret(pubKeysetBytes)
        val hybridEncrypt = recipientPublicHandle.getPrimitive(HybridEncrypt::class.java)
        val ciphertext = hybridEncrypt.encrypt(plaintext.toByteArray(Charsets.UTF_8), null)
        return Base64.encodeToString(ciphertext, Base64.NO_WRAP)
    }

    /** Descifra un mensaje recibido usando MI llave privada. */
    fun decrypt(ciphertextBase64: String): String {
        val handle = getOrCreateMyKeysetHandle()
        val hybridDecrypt = handle.getPrimitive(HybridDecrypt::class.java)
        val ciphertext = Base64.decode(ciphertextBase64, Base64.NO_WRAP)
        val plaintext = hybridDecrypt.decrypt(ciphertext, null)
        return String(plaintext, Charsets.UTF_8)
    }
}
