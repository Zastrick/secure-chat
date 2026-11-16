package com.tabasco.securechat.ui

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

/**
 * Pantalla de Ajustes de Conexión.
 *
 * Permite configurar el servidor relay para que SecureChat Tabasco funcione:
 * - A través de INTERNET GLOBAL (sin importar la red, WiFi, 4G o 5G en cualquier lugar)
 *   usando wss:// con hostings en la nube (Render, Railway, Fly.io, etc.) o túneles públicos.
 * - O en red local para pruebas escolares (ws://).
 */

private const val PREFS_SETTINGS = "securechat_settings"
private const val KEY_SERVER_URL = "server_url"

/**
 * URL por defecto para conexión global.
 * Por defecto apunta al relay en la nube con protocolo seguro WSS.
 * Si se corre en local se puede cambiar en 1 toque con los botones de abajo.
 */
const val DEFAULT_SERVER_URL = "wss://securechat-tabasco.onrender.com"

/**
 * Normaliza la dirección ingresada para evitar errores comunes de tipeo:
 * - Si el usuario escribe "mi-app.onrender.com", se convierte en "wss://mi-app.onrender.com"
 * - Si escribe "https://...", se convierte en "wss://..."
 * - Si escribe "http://...", se convierte en "ws://..."
 * - Si es una IP local o localhost, usa "ws://"
 */
fun normalizeServerUrl(rawUrl: String): String {
    val trimmed = rawUrl.trim()
    if (trimmed.isEmpty()) return DEFAULT_SERVER_URL
    return when {
        trimmed.startsWith("wss://", ignoreCase = true) -> trimmed
        trimmed.startsWith("ws://", ignoreCase = true) -> trimmed
        trimmed.startsWith("https://", ignoreCase = true) -> "wss://" + trimmed.removePrefix("https://").removePrefix("HTTPS://")
        trimmed.startsWith("http://", ignoreCase = true) -> "ws://" + trimmed.removePrefix("http://").removePrefix("HTTP://")
        trimmed.contains("10.0.2.2") || trimmed.contains("localhost") || trimmed.startsWith("192.168.") || trimmed.startsWith("127.") -> "ws://$trimmed"
        else -> "wss://$trimmed"
    }
}

/** Lee la URL guardada en SharedPreferences o devuelve la predeterminada. */
fun savedServerUrl(context: Context): String {
    val raw = context.getSharedPreferences(PREFS_SETTINGS, Context.MODE_PRIVATE)
        .getString(KEY_SERVER_URL, DEFAULT_SERVER_URL) ?: DEFAULT_SERVER_URL
    return normalizeServerUrl(raw)
}

@Composable
fun SettingsScreen(
    connectionStatus: String = "Desconectado",
    onClose: () -> Unit,
    onServerUrlChanged: (newUrl: String) -> Unit = {}
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    var urlDraft by remember {
        mutableStateOf(savedServerUrl(context))
    }
    var savedOk by remember { mutableStateOf(false) }

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(24.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ajustes de Conexión", style = MaterialTheme.typography.headlineSmall, modifier = Modifier.weight(1f))
            IconButton(onClick = onClose) {
                Icon(Icons.Default.Close, contentDescription = "Cerrar")
            }
        }

        Spacer(Modifier.height(16.dp))

        // Estado actual de la conexión
        val statusColor = when {
            connectionStatus.contains("Conectado", ignoreCase = true) -> Color(0xFF00C853)
            connectionStatus.contains("Conectando", ignoreCase = true) -> Color(0xFFFFB300)
            else -> MaterialTheme.colorScheme.error
        }

        Surface(
            color = MaterialTheme.colorScheme.surfaceVariant,
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = statusColor,
                    shape = MaterialTheme.shapes.small,
                    modifier = Modifier.size(12.dp)
                ) {}
                Spacer(Modifier.width(10.dp))
                Column {
                    Text("Estado del Relay:", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text(connectionStatus, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = statusColor)
                }
            }
        }

        Spacer(Modifier.height(20.dp))

        Text(
            "URL del Servidor Relay (Internet Global)",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(Modifier.height(6.dp))

        OutlinedTextField(
            value = urlDraft,
            onValueChange = { urlDraft = it; savedOk = false },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("URL WebSocket (ej. wss://tu-servidor.com)") },
            placeholder = { Text("wss://tu-relay.onrender.com") },
            singleLine = true
        )

        Spacer(Modifier.height(10.dp))

        Text(
            "Configuraciones rápidas:",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.height(6.dp))

        // Botones de presets
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SuggestionChip(
                onClick = {
                    urlDraft = "wss://securechat-tabasco.onrender.com"
                    savedOk = false
                },
                label = { Text("Nube (Render)", style = MaterialTheme.typography.bodySmall) },
                icon = { Icon(Icons.Default.Cloud, contentDescription = null, modifier = Modifier.size(16.dp)) },
                modifier = Modifier.weight(1f)
            )

            SuggestionChip(
                onClick = {
                    urlDraft = "ws://10.0.2.2:8787"
                    savedOk = false
                },
                label = { Text("Emulador PC", style = MaterialTheme.typography.bodySmall) },
                icon = { Icon(Icons.Default.Computer, contentDescription = null, modifier = Modifier.size(16.dp)) },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(Modifier.height(12.dp))

        Text(
            "• Internet Global: Ingresa tu dominio público con wss:// (ej. Render, Railway o túnel Cloudflare/Localtunnel). Permite comunicar teléfonos en cualquier red, datos móviles o WiFi.\n" +
            "• Red Local: Ingresa la IP de tu PC con ws:// (ej. ws://192.168.1.X:8787) solo si ambos están en la misma red.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(Modifier.height(20.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Button(
                onClick = {
                    val finalUrl = normalizeServerUrl(urlDraft)
                    urlDraft = finalUrl
                    context.getSharedPreferences(PREFS_SETTINGS, Context.MODE_PRIVATE)
                        .edit()
                        .putString(KEY_SERVER_URL, finalUrl)
                        .apply()
                    savedOk = true
                    // Reconexión en caliente inmediata
                    onServerUrlChanged(finalUrl)
                },
                modifier = Modifier.weight(1f)
            ) {
                Icon(Icons.Default.Save, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(6.dp))
                Text("Guardar y Conectar")
            }

            OutlinedButton(onClick = onClose) {
                Text("Listo")
            }
        }

        if (savedOk) {
            Spacer(Modifier.height(12.dp))
            Surface(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = MaterialTheme.shapes.small,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        "URL actualizada. Reconectando al servidor relay...",
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }

        Spacer(Modifier.height(28.dp))
        HorizontalDivider()
        Spacer(Modifier.height(16.dp))

        // Sección de arquitectura y privacidad
        Text(
            "Seguridad y Privacidad del Prototipo",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(Modifier.height(8.dp))
        Text(
            "• Cifrado E2E Real (ECIES P-256 Tink): El contenido viaja cifrado en todo momento a través de Internet. El relay nunca puede leer los mensajes ni los archivos.\n" +
            "• Identidad Criptográfica: Sin números telefónicos ni cuentas de correo. Tu identidad es tu Session ID (clave pública).\n" +
            "• Transporte WSS (TLS): Sobre internet público los datos viajan con cifrado de transporte además del cifrado E2E.\n" +
            "• Mensajes Offline: El relay retiene en cola temporal los mensajes cifrados hasta que el destinatario se conecta.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
