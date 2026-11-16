package com.tabasco.securechat.network

import android.os.Handler
import android.os.Looper
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okhttp3.Response
import org.json.JSONObject
import okio.ByteString
import java.util.concurrent.TimeUnit

/**
 * RelayClient
 *
 * Habla con el servidor Node.js por WebSocket. Todo lo que manda por aquí
 * ya viene cifrado desde CryptoManager — este cliente NUNCA ve texto plano
 * saliendo hacia la red, solo blobs cifrados en base64.
 *
 * Incluye reconexión automática: si la conexión se cae (red inestable,
 * el teléfono bloquea la app en segundo plano, etc.), reintenta solo cada
 * pocos segundos en vez de quedarse desconectado para siempre.
 *
 * Cambia SERVER_URL por la IP/dominio donde corras server.js.
 * En emulador Android, "10.0.2.2" apunta al localhost de tu computadora.
 */
class RelayClient(
    private var serverUrl: String = "ws://10.0.2.2:8787",
    private val myId: String,
    private val onMessageReceived: (from: String, ciphertext: String) -> Unit,
    private val onConnected: () -> Unit = {},
    private val onDisconnected: () -> Unit = {},
    private val onConnecting: () -> Unit = {}
) {
    // Ping cada 20s para mantener viva la conexión (evita que redes móviles o routers
    // cierren conexiones idle en silencio si no hay tráfico).
    private val client = OkHttpClient.Builder()
        .pingInterval(20, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(0, TimeUnit.MILLISECONDS)
        .build()

    private var webSocket: WebSocket? = null
    private val mainHandler = Handler(Looper.getMainLooper())
    private var shouldReconnect = true
    private val reconnectDelayMs = 3000L

    fun connect() {
        shouldReconnect = true
        openSocket()
    }

    /**
     * Actualiza la URL del servidor en caliente (ej. de local a internet wss://...)
     * y reconecta inmediatamente sin reiniciar la app.
     */
    fun updateServerUrl(newUrl: String) {
        serverUrl = newUrl
        shouldReconnect = true
        mainHandler.removeCallbacksAndMessages(null)
        try {
            webSocket?.close(1000, "Cambiando servidor relay")
        } catch (_: Exception) {}
        openSocket()
    }

    private fun openSocket() {
        onConnecting()
        val request = try {
            Request.Builder().url(serverUrl).build()
        } catch (e: Exception) {
            onDisconnected()
            scheduleReconnect()
            return
        }

        webSocket = client.newWebSocket(request, object : WebSocketListener() {

            override fun onOpen(ws: WebSocket, response: Response) {
                val register = JSONObject().apply {
                    put("type", "register")
                    put("id", myId)
                }
                ws.send(register.toString())
            }

            override fun onMessage(ws: WebSocket, text: String) {
                val json = JSONObject(text)
                when (json.optString("type")) {
                    "registered" -> onConnected()
                    "message" -> {
                        val from = json.getString("from")
                        val ciphertext = json.getString("ciphertext")
                        onMessageReceived(from, ciphertext)
                    }
                }
            }

            override fun onMessage(ws: WebSocket, bytes: ByteString) {
                // No usamos frames binarios, solo JSON en texto
            }

            override fun onClosed(ws: WebSocket, code: Int, reason: String) {
                onDisconnected()
                scheduleReconnect()
            }

            override fun onFailure(ws: WebSocket, t: Throwable, response: Response?) {
                t.printStackTrace()
                onDisconnected()
                scheduleReconnect()
            }
        })
    }

    private fun scheduleReconnect() {
        if (!shouldReconnect) return
        mainHandler.postDelayed({
            if (shouldReconnect) openSocket()
        }, reconnectDelayMs)
    }

    fun sendCiphertext(toId: String, ciphertextBase64: String) {
        val payload = JSONObject().apply {
            put("type", "message")
            put("to", toId)
            put("ciphertext", ciphertextBase64)
        }
        webSocket?.send(payload.toString())
    }

    fun disconnect() {
        shouldReconnect = false
        webSocket?.close(1000, "Cerrando app")
    }
}
