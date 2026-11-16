@file:OptIn(
    androidx.compose.material3.ExperimentalMaterial3Api::class,
    androidx.compose.foundation.ExperimentalFoundationApi::class
)

package com.tabasco.securechat

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Bitmap
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.FiberManualRecord
import androidx.compose.material.icons.filled.InsertDriveFile
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.tabasco.securechat.crypto.CryptoManager
import com.tabasco.securechat.data.AppState
import com.tabasco.securechat.data.ChatMessage
import com.tabasco.securechat.data.ChatRepository
import com.tabasco.securechat.data.MessagePayload
import com.tabasco.securechat.data.ParsedPayload
import com.tabasco.securechat.data.parsePayload
import com.tabasco.securechat.network.RelayClient
import com.tabasco.securechat.ui.QrScannerScreen
import com.tabasco.securechat.ui.SettingsScreen
import com.tabasco.securechat.ui.savedServerUrl
import com.tabasco.securechat.util.MediaUtils
import java.io.File
import java.util.UUID

// ---------------- Temas (claro y oscuro) ----------------
// Un solo lugar para los colores: botones, superficies y contenedores
// siempre combinan, en cualquiera de los dos modos.
private val BrandPrimaryDark = Color(0xFF00D9A3)
private val BrandPrimaryLight = Color(0xFF00896B)

private val DarkColors = darkColorScheme(
    primary = BrandPrimaryDark,
    onPrimary = Color(0xFF00201A),
    primaryContainer = Color(0xFF00493B),
    onPrimaryContainer = Color(0xFF6FFFD9),
    secondary = Color(0xFF4FD8EB),
    onSecondary = Color(0xFF00363D),
    background = Color(0xFF0D1117),
    onBackground = Color(0xFFE6E8EB),
    surface = Color(0xFF151B22),
    onSurface = Color(0xFFE6E8EB),
    surfaceVariant = Color(0xFF212A33),
    onSurfaceVariant = Color(0xFFAAB4BF),
    error = Color(0xFFFF6B6B),
)

private val LightColors = lightColorScheme(
    primary = BrandPrimaryLight,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFB6FFEA),
    onPrimaryContainer = Color(0xFF00201A),
    secondary = Color(0xFF0097A7),
    onSecondary = Color.White,
    background = Color(0xFFF7F9FA),
    onBackground = Color(0xFF1A1C1E),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF1A1C1E),
    surfaceVariant = Color(0xFFE3E8E6),
    onSurfaceVariant = Color(0xFF444B49),
    error = Color(0xFFBA1A1A),
)

private val REACTION_EMOJIS = listOf("👍", "❤️", "😂", "😮", "😢", "🙏")

class MainActivity : ComponentActivity() {

    private lateinit var crypto: CryptoManager
    private lateinit var relay: RelayClient
    private var myId: String = ""
    private var connectionStatus by mutableStateOf("Conectando...")

    // ---- Perfil local (nombre + foto), persistido en SharedPreferences ----
    private fun profilePrefs() = getSharedPreferences("securechat_profile", Context.MODE_PRIVATE)
    private var profileName by mutableStateOf("")
    private var profilePhotoBase64 by mutableStateOf<String?>(null)

    private fun saveProfile(name: String, photoBase64: String?) {
        profilePrefs().edit()
            .putString("name", name)
            .apply { if (photoBase64 != null) putString("photo_b64", photoBase64) }
            .apply()
        profileName = name
        if (photoBase64 != null) profilePhotoBase64 = photoBase64
    }

    // ---- Selector de foto de perfil ----
    private val pickProfilePhotoLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                val b64 = MediaUtils.resizedImageBase64(this, uri, 256, 80)
                if (b64 != null) saveProfile(profileName, b64)
            }
        }

    // ---- Selector de archivo/imagen para adjuntar en un chat ----
    private var pendingAttachTargetId: String? = null
    private val pickAttachmentLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            val toId = pendingAttachTargetId
            if (uri != null && toId != null) handleAttachmentPicked(toId, uri)
        }

    // ---- Permiso de micrófono ----
    private val recordAudioPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (!granted) {
                Toast.makeText(this, "Se necesita permiso de micrófono para enviar audios", Toast.LENGTH_SHORT).show()
            }
        }

    // ---- Grabación de audio ----
    private var mediaRecorder: MediaRecorder? = null
    private var audioFile: File? = null
    private var recordStartMs: Long = 0L
    private var isRecording by mutableStateOf(false)

    private fun hasRecordPermission(): Boolean =
        androidx.core.content.ContextCompat.checkSelfPermission(
            this, android.Manifest.permission.RECORD_AUDIO
        ) == android.content.pm.PackageManager.PERMISSION_GRANTED

    private fun startRecording() {
        if (!hasRecordPermission()) {
            recordAudioPermissionLauncher.launch(android.Manifest.permission.RECORD_AUDIO)
            return
        }
        try {
            val file = File(cacheDir, "rec_${System.currentTimeMillis()}.m4a")
            audioFile = file
            mediaRecorder = MediaRecorder().apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                setOutputFile(file.absolutePath)
                prepare()
                start()
            }
            recordStartMs = System.currentTimeMillis()
            isRecording = true
        } catch (e: Exception) {
            Toast.makeText(this, "No se pudo grabar: ${e.message}", Toast.LENGTH_SHORT).show()
            isRecording = false
        }
    }

    private fun stopRecordingAndSend(toId: String) {
        val recorder = mediaRecorder
        val file = audioFile
        isRecording = false
        if (recorder == null || file == null) return
        try {
            recorder.stop()
            recorder.release()
        } catch (e: Exception) {
            // grabación demasiado corta u otro problema — la ignoramos con cuidado
        }
        mediaRecorder = null
        val durationMs = (System.currentTimeMillis() - recordStartMs).toInt()
        val bytes = try { file.readBytes() } catch (e: Exception) { null }
        if (bytes == null || durationMs < 300) {
            Toast.makeText(this, "Grabación muy corta, intenta de nuevo", Toast.LENGTH_SHORT).show()
            return
        }
        val base64 = Base64.encodeToString(bytes, Base64.NO_WRAP)
        val msg = ChatMessage(fromMe = true, kind = "audio", fileBase64 = base64, durationMs = durationMs)
        sendPayload(
            toId = toId,
            plaintext = MessagePayload.audio(msg.id, base64, durationMs),
            localEcho = msg
        )
    }

    private fun playAudioBase64(base64: String) {
        try {
            val bytes = Base64.decode(base64, Base64.NO_WRAP)
            val file = File(cacheDir, "play_${System.currentTimeMillis()}.m4a")
            file.writeBytes(bytes)
            val player = MediaPlayer()
            player.setDataSource(file.absolutePath)
            player.setOnPreparedListener { it.start() }
            player.setOnCompletionListener { it.release() }
            player.prepareAsync()
        } catch (e: Exception) {
            Toast.makeText(this, "No se pudo reproducir: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    // ---- Manejo de archivos/imágenes adjuntos ----
    private fun handleAttachmentPicked(toId: String, uri: Uri) {
        val mimeType = MediaUtils.mimeTypeFromUri(this, uri)
        val fileName = MediaUtils.fileNameFromUri(this, uri)
        val id = UUID.randomUUID().toString()

        if (mimeType.startsWith("image/")) {
            val base64 = MediaUtils.resizedImageBase64(this, uri, 800, 70)
            if (base64 == null) {
                Toast.makeText(this, "No se pudo leer la imagen", Toast.LENGTH_SHORT).show()
                return
            }
            sendPayload(
                toId = toId,
                plaintext = MessagePayload.image(id, fileName, "image/jpeg", base64),
                localEcho = ChatMessage(id = id, fromMe = true, kind = "image", fileName = fileName, mimeType = "image/jpeg", fileBase64 = base64)
            )
        } else {
            val bytes = MediaUtils.readBytes(this, uri)
            if (bytes == null) {
                Toast.makeText(this, "No se pudo leer el archivo", Toast.LENGTH_SHORT).show()
                return
            }
            if (bytes.size > MediaUtils.MAX_FILE_BYTES) {
                Toast.makeText(this, "Archivo muy grande para este prototipo (máx 3 MB)", Toast.LENGTH_LONG).show()
                return
            }
            val base64 = MediaUtils.bytesToBase64(bytes)
            sendPayload(
                toId = toId,
                plaintext = MessagePayload.file(id, fileName, mimeType, base64),
                localEcho = ChatMessage(id = id, fromMe = true, kind = "file", fileName = fileName, mimeType = mimeType, fileBase64 = base64)
            )
        }
    }

    private fun saveReceivedFile(fileName: String, mimeType: String?, base64: String) {
        try {
            val bytes = Base64.decode(base64, Base64.NO_WRAP)
            val dir = File(getExternalFilesDir(null), "SecureChatTabasco")
            if (!dir.exists()) dir.mkdirs()
            val outFile = File(dir, fileName)
            outFile.writeBytes(bytes)
            Toast.makeText(this, "Guardado en: ${outFile.absolutePath}", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Toast.makeText(this, "No se pudo guardar: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    // ---- Envío genérico: cifra, manda por el relay, y agrega el eco local ----
    // localEcho == null se usa para reacciones y perfil (no crean burbuja nueva).
    private fun sendPayload(toId: String, plaintext: String, localEcho: ChatMessage?) {
        try {
            val ciphertext = crypto.encryptFor(toId, plaintext)
            relay.sendCiphertext(toId, ciphertext)
            if (localEcho != null) {
                AppState.addMessage(toId, localEcho)
                ChatRepository.saveMessage(this, toId, localEcho)
            }
        } catch (e: Exception) {
            val errMsg = ChatMessage(
                fromMe = false,
                kind = "system",
                text = "[Error al cifrar/enviar: ${e.message ?: "desconocido"}. Revisa el Session ID.]"
            )
            AppState.addMessage(toId, errMsg)
        }
    }

    private fun generateQrBitmap(content: String, size: Int = 512): Bitmap? {
        return try {
            val writer = QRCodeWriter()
            val matrix = writer.encode(content, BarcodeFormat.QR_CODE, size, size)
            val bmp = Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565)
            for (x in 0 until size) {
                for (y in 0 until size) {
                    bmp.setPixel(x, y, if (matrix[x, y]) android.graphics.Color.BLACK else android.graphics.Color.WHITE)
                }
            }
            bmp
        } catch (e: Exception) {
            null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        crypto = CryptoManager(applicationContext)
        myId = crypto.getMyPublicIdBase64()

        profileName = profilePrefs().getString("name", "") ?: ""
        profilePhotoBase64 = profilePrefs().getString("photo_b64", null)

        // ── Cargar contactos y mensajes guardados en Room ─────────────────────
        ChatRepository.loadAll(this)

        // ── Leer la URL del servidor desde Ajustes (o usar la predeterminada) ──
        val serverUrl = savedServerUrl(this)

        relay = RelayClient(
            serverUrl = serverUrl,
            myId = myId,
            onMessageReceived = { from, ciphertext ->
                val plaintext = try {
                    crypto.decrypt(ciphertext)
                } catch (e: Exception) {
                    null
                }
                runOnUiThread {
                    if (plaintext == null) {
                        AppState.addMessage(from, ChatMessage(fromMe = false, kind = "system", text = "[No se pudo descifrar un mensaje entrante]"))
                        return@runOnUiThread
                    }
                    when (val parsed = parsePayload(plaintext, fromMe = false)) {
                        is ParsedPayload.Chat -> {
                            AppState.addMessage(from, parsed.message)
                            ChatRepository.saveMessage(this, from, parsed.message)
                        }
                        is ParsedPayload.Profile -> {
                            AppState.updateContactProfile(from, parsed.name, parsed.photoBase64)
                            // Actualizar la foto/nombre guardados en Room
                            val contact = AppState.contacts.find { it.publicId == from }
                            if (contact != null) {
                                ChatRepository.saveContact(this, from, contact.alias, parsed.photoBase64)
                            }
                        }
                        is ParsedPayload.Reaction -> {
                            AppState.addReaction(from, parsed.targetId, parsed.emoji)
                            val msg = AppState.conversations[from]?.find { it.id == parsed.targetId }
                            if (msg != null) {
                                ChatRepository.saveReaction(this, parsed.targetId, msg.reactions.toList())
                            }
                        }
                    }
                }
            },
            onConnected = {
                runOnUiThread { connectionStatus = "Conectado" }
            },
            onDisconnected = {
                runOnUiThread { connectionStatus = "Desconectado" }
            },
            onConnecting = {
                runOnUiThread { connectionStatus = "Conectando..." }
            }
        )
        relay.connect()

        setContent {
            var isDarkTheme by remember { mutableStateOf(true) }
            MaterialTheme(colorScheme = if (isDarkTheme) DarkColors else LightColors) {
                Surface(color = MaterialTheme.colorScheme.background) {
                    AppRoot(
                        myId = myId,
                        profileName = profileName,
                        profilePhotoBase64 = profilePhotoBase64,
                        isDarkTheme = isDarkTheme,
                        isRecording = isRecording,
                        connectionStatus = connectionStatus,
                        onToggleTheme = { isDarkTheme = !isDarkTheme },
                        onUpdateServerUrl = { newUrl ->
                            relay.updateServerUrl(newUrl)
                        },
                        // onSaveProfile ya no recibe photoRequested: ese parámetro se eliminó
                        onSaveProfile = { name -> saveProfile(name, null) },
                        onPickProfilePhoto = { pickProfilePhotoLauncher.launch("image/*") },
                        onGenerateQr = { content -> generateQrBitmap(content) },
                        onSendText = { toId, text ->
                            val id = UUID.randomUUID().toString()
                            sendPayload(
                                toId = toId,
                                plaintext = MessagePayload.text(id, text),
                                localEcho = ChatMessage(id = id, fromMe = true, kind = "text", text = text)
                            )
                        },
                        onAttachClick = { toId ->
                            pendingAttachTargetId = toId
                            pickAttachmentLauncher.launch("*/*")
                        },
                        onStartRecording = { startRecording() },
                        onStopRecording = { toId -> stopRecordingAndSend(toId) },
                        onPlayAudio = { base64 -> playAudioBase64(base64) },
                        onSaveFile = { fileName, mimeType, base64 -> saveReceivedFile(fileName, mimeType, base64) },
                        onReact = { toId, messageId, emoji ->
                            AppState.addReaction(toId, messageId, emoji)
                            sendPayload(toId, MessagePayload.reaction(messageId, emoji), localEcho = null)
                            val msg = AppState.conversations[toId]?.find { it.id == messageId }
                            if (msg != null) ChatRepository.saveReaction(this, messageId, msg.reactions.toList())
                        },
                        onShareProfile = { toId ->
                            sendPayload(toId, MessagePayload.profile(profileName, profilePhotoBase64), localEcho = null)
                            Toast.makeText(this@MainActivity, "Perfil compartido", Toast.LENGTH_SHORT).show()
                        },
                        onAddContactManual = { alias, pubId ->
                            AppState.addContact(alias.trim(), pubId.trim())
                            ChatRepository.saveContact(this, pubId.trim(), alias.trim())
                        }
                    )
                }
            }
        }
    }

    override fun onDestroy() {
        relay.disconnect()
        super.onDestroy()
    }
}

@Composable
fun AppRoot(
    myId: String,
    profileName: String,
    profilePhotoBase64: String?,
    isDarkTheme: Boolean,
    isRecording: Boolean,
    connectionStatus: String,
    onToggleTheme: () -> Unit,
    onUpdateServerUrl: (String) -> Unit,
    // Parámetro photoRequested eliminado: la foto se pide por onPickProfilePhoto
    onSaveProfile: (name: String) -> Unit,
    onPickProfilePhoto: () -> Unit,
    onGenerateQr: (String) -> Bitmap?,
    onSendText: (toId: String, text: String) -> Unit,
    onAttachClick: (toId: String) -> Unit,
    onStartRecording: () -> Unit,
    onStopRecording: (toId: String) -> Unit,
    onPlayAudio: (String) -> Unit,
    onSaveFile: (fileName: String, mimeType: String?, base64: String) -> Unit,
    onReact: (toId: String, messageId: String, emoji: String) -> Unit,
    onShareProfile: (toId: String) -> Unit,
    onAddContactManual: (alias: String, pubId: String) -> Unit
) {
    var openChatId by remember { mutableStateOf<String?>(null) }
    var showMyId by remember { mutableStateOf(false) }
    var showAddContact by remember { mutableStateOf(false) }
    var showProfile by remember { mutableStateOf(false) }
    var showSettings by remember { mutableStateOf(false) }
    /** Si es true, dentro de "Agregar contacto" se muestra el escáner QR. */
    var showQrScanner by remember { mutableStateOf(false) }

    // Pantalla de ajustes (cubre toda la pantalla)
    if (showSettings) {
        SettingsScreen(
            connectionStatus = connectionStatus,
            onClose = { showSettings = false },
            onServerUrlChanged = onUpdateServerUrl
        )
        return
    }

    // Escáner QR (cubre toda la pantalla)
    if (showQrScanner) {
        QrScannerScreen(
            onScanned = { value ->
                // El valor escaneado es el Session ID (llave pública Base64).
                // Abrimos el diálogo de "Agregar contacto" con el ID ya rellenado.
                showQrScanner = false
                showAddContact = true
                scannedPubId = value          // variable de estado compartida abajo
            },
            onCancel = { showQrScanner = false }
        )
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(if (openChatId == null) "SecureChat Tabasco" else "Chat")
                        if (openChatId == null) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                val dotColor = when {
                                    connectionStatus.contains("Conectado", ignoreCase = true) -> Color(0xFF00C853)
                                    connectionStatus.contains("Conectando", ignoreCase = true) -> Color(0xFFFFB300)
                                    else -> MaterialTheme.colorScheme.error
                                }
                                Surface(
                                    color = dotColor,
                                    shape = CircleShape,
                                    modifier = Modifier.size(8.dp)
                                ) {}
                                Spacer(Modifier.width(6.dp))
                                Text(
                                    text = if (connectionStatus.contains("Conectado", ignoreCase = true)) "En línea (Internet)" else connectionStatus,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                ),
                actions = {
                    IconButton(onClick = onToggleTheme) {
                        Icon(
                            if (isDarkTheme) Icons.Default.LightMode else Icons.Default.DarkMode,
                            contentDescription = "Cambiar tema"
                        )
                    }
                    IconButton(onClick = { showProfile = true }) {
                        Icon(Icons.Default.AccountCircle, contentDescription = "Mi perfil")
                    }
                    IconButton(onClick = { showSettings = true }) {
                        Icon(Icons.Default.Settings, contentDescription = "Ajustes")
                    }
                }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding).fillMaxSize()) {
            when {
                openChatId != null -> ChatScreen(
                    contactId = openChatId!!,
                    isRecording = isRecording,
                    onBack = { openChatId = null },
                    onSend = { text -> onSendText(openChatId!!, text) },
                    onAttach = { onAttachClick(openChatId!!) },
                    onStartRecording = onStartRecording,
                    onStopRecording = { onStopRecording(openChatId!!) },
                    onPlayAudio = onPlayAudio,
                    onSaveFile = onSaveFile,
                    onReact = { messageId, emoji -> onReact(openChatId!!, messageId, emoji) },
                    onShareProfile = { onShareProfile(openChatId!!) }
                )
                else -> ChatListScreen(
                    onOpenChat = { openChatId = it },
                    onShowMyId = { showMyId = true },
                    onAddContact = { showAddContact = true }
                )
            }
        }
    }

    // ── Diálogo: Mi Session ID con QR ────────────────────────────────────────
    if (showMyId) {
        val context = LocalContext.current
        val qrBitmap = remember(myId) { onGenerateQr(myId) }
        AlertDialog(
            onDismissRequest = { showMyId = false },
            confirmButton = {
                Row {
                    TextButton(onClick = {
                        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        clipboard.setPrimaryClip(ClipData.newPlainText("Session ID", myId))
                        Toast.makeText(context, "ID copiado", Toast.LENGTH_SHORT).show()
                    }) {
                        Icon(Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(6.dp))
                        Text("Copiar")
                    }
                    TextButton(onClick = { showMyId = false }) { Text("Cerrar") }
                }
            },
            title = { Text("Mi Session ID") },
            text = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        "Comparte esto (o el código QR) con quien quieras que te escriba:",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(Modifier.height(8.dp))
                    if (qrBitmap != null) {
                        Image(
                            bitmap = qrBitmap.asImageBitmap(),
                            contentDescription = "Código QR de mi Session ID",
                            modifier = Modifier.size(180.dp)
                        )
                        Spacer(Modifier.height(8.dp))
                    }
                    Surface(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = MaterialTheme.shapes.small
                    ) {
                        Text(
                            myId,
                            modifier = Modifier.padding(8.dp),
                            fontFamily = FontFamily.Monospace,
                            fontSize = 11.sp
                        )
                    }
                }
            }
        )
    }

    // ── Diálogo: Agregar contacto (manual + botón de escáner QR) ─────────────
    if (showAddContact) {
        var alias by remember { mutableStateOf("") }
        // Si venimos del escáner, scannedPubId ya tiene el valor
        var pubId by remember { mutableStateOf(scannedPubId) }
        // Limpiar scannedPubId para la próxima vez
        LaunchedEffect(Unit) { scannedPubId = "" }

        AlertDialog(
            onDismissRequest = { showAddContact = false },
            confirmButton = {
                TextButton(onClick = {
                    if (alias.isNotBlank() && pubId.isNotBlank()) {
                        onAddContactManual(alias.trim(), pubId.trim())
                    }
                    showAddContact = false
                }) { Text("Agregar") }
            },
            dismissButton = { TextButton(onClick = { showAddContact = false }) { Text("Cancelar") } },
            title = { Text("Agregar contacto") },
            text = {
                Column {
                    OutlinedTextField(value = alias, onValueChange = { alias = it }, label = { Text("Nombre") })
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = pubId,
                        onValueChange = { pubId = it },
                        label = { Text("Session ID (pegar o escanear)") }
                    )
                    Spacer(Modifier.height(12.dp))
                    // Botón para abrir el escáner QR con icono moderno
                    FilledTonalButton(
                        onClick = {
                            showAddContact = false
                            showQrScanner = true
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.QrCodeScanner, contentDescription = null, modifier = Modifier.size(20.dp))
                        Spacer(Modifier.width(8.dp))
                        Text("Escanear código QR")
                    }
                }
            }
        )
    }

    // ── Diálogo: Mi perfil ────────────────────────────────────────────────────
    if (showProfile) {
        var name by remember(profileName) { mutableStateOf(profileName) }
        val photoBmp = remember(profilePhotoBase64) {
            profilePhotoBase64?.let { com.tabasco.securechat.util.MediaUtils.base64ToBitmap(it) }
        }
        AlertDialog(
            onDismissRequest = { showProfile = false },
            confirmButton = {
                TextButton(onClick = {
                    onSaveProfile(name)
                    showProfile = false
                }) { Text("Guardar") }
            },
            dismissButton = { TextButton(onClick = { showProfile = false }) { Text("Cerrar") } },
            title = { Text("Mi perfil") },
            text = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .clickable(onClick = onPickProfilePhoto),
                        contentAlignment = Alignment.Center
                    ) {
                        if (photoBmp != null) {
                            Image(
                                bitmap = photoBmp.asImageBitmap(),
                                contentDescription = "Foto de perfil",
                                modifier = Modifier.size(72.dp).clip(CircleShape)
                            )
                        } else {
                            Icon(
                                Icons.Default.AddAPhoto,
                                contentDescription = "Subir foto",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }
                    Spacer(Modifier.height(4.dp))
                    Text("Toca para cambiar la foto", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp)
                    Spacer(Modifier.height(12.dp))
                    OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Nombre / tag") })
                }
            }
        )
    }
}

/**
 * Variable de estado a nivel de composición para pasar el Session ID
 * escaneado del QrScannerScreen al diálogo de "Agregar contacto".
 * Se limpia con LaunchedEffect al abrir el diálogo.
 */
private var scannedPubId: String = ""

// (Modifier.background y Modifier.clickable ya están importados arriba
// desde androidx.compose.foundation; no se necesitan helpers extra.)

@Composable
fun ChatListScreen(
    onOpenChat: (String) -> Unit,
    onShowMyId: () -> Unit,
    onAddContact: () -> Unit
) {
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = onShowMyId) {
                Icon(Icons.Default.QrCode, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(6.dp))
                Text("Ver mi ID")
            }
            Button(onClick = onAddContact) {
                Icon(Icons.Default.PersonAdd, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(6.dp))
                Text("Agregar contacto")
            }
        }
        Spacer(Modifier.height(16.dp))
        Text("Contactos", style = MaterialTheme.typography.titleMedium)
        LazyColumn {
            items(AppState.contacts) { contact ->
                val photoBmp = remember(contact.photoBase64) {
                    contact.photoBase64?.let { com.tabasco.securechat.util.MediaUtils.base64ToBitmap(it) }
                }
                ListItem(
                    leadingContent = {
                        Box(
                            modifier = Modifier.size(40.dp).clip(CircleShape).background(MaterialTheme.colorScheme.surfaceVariant),
                            contentAlignment = Alignment.Center
                        ) {
                            if (photoBmp != null) {
                                Image(bitmap = photoBmp.asImageBitmap(), contentDescription = null, modifier = Modifier.size(40.dp).clip(CircleShape))
                            } else {
                                Text(contact.alias.take(1).uppercase())
                            }
                        }
                    },
                    headlineContent = { Text(contact.alias) },
                    supportingContent = {
                        Text(
                            contact.publicId.take(24) + "…",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    },
                    colors = ListItemDefaults.colors(containerColor = MaterialTheme.colorScheme.background),
                    modifier = Modifier.clickable { onOpenChat(contact.publicId) }
                )
                HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)
            }
        }
        if (AppState.contacts.isEmpty()) {
            Spacer(Modifier.height(24.dp))
            Text(
                "Aún no tienes contactos. Pide a alguien su Session ID (o código QR) y agrégalo.",
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun ChatScreen(
    contactId: String,
    isRecording: Boolean,
    onBack: () -> Unit,
    onSend: (String) -> Unit,
    onAttach: () -> Unit,
    onStartRecording: () -> Unit,
    onStopRecording: () -> Unit,
    onPlayAudio: (String) -> Unit,
    onSaveFile: (fileName: String, mimeType: String?, base64: String) -> Unit,
    onReact: (messageId: String, emoji: String) -> Unit,
    onShareProfile: () -> Unit
) {
    val messages = AppState.conversations[contactId] ?: emptyList()
    var draft by remember { mutableStateOf("") }
    val contact = AppState.contacts.find { it.publicId == contactId }

    Column(Modifier.fillMaxSize()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
            }
            Text(
                contact?.alias ?: "Desconocido",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Spacer(Modifier.weight(1f))
            TextButton(onClick = onShareProfile) {
                Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(Modifier.width(4.dp))
                Text("Compartir mi perfil")
            }
        }
        HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)
        LazyColumn(Modifier.weight(1f).padding(8.dp)) {
            items(messages) { msg ->
                MessageBubble(msg = msg, onPlayAudio = onPlayAudio, onSaveFile = onSaveFile, onReact = { emoji -> onReact(msg.id, emoji) })
            }
        }
        if (isRecording) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Icon(
                    Icons.Default.FiberManualRecord,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(12.dp)
                )
                Spacer(Modifier.width(6.dp))
                Text(
                    "Grabando audio… toca detener para enviar",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
        Row(
            Modifier.fillMaxWidth().padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onAttach) {
                Icon(Icons.Default.AttachFile, contentDescription = "Adjuntar archivo")
            }
            IconButton(onClick = {
                if (isRecording) onStopRecording() else onStartRecording()
            }) {
                Icon(
                    if (isRecording) Icons.Default.Stop else Icons.Default.Mic,
                    contentDescription = if (isRecording) "Detener grabación" else "Grabar audio",
                    tint = if (isRecording) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                )
            }
            OutlinedTextField(
                value = draft,
                onValueChange = { draft = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Escribe un mensaje cifrado…") },
                maxLines = 4
            )
            Spacer(Modifier.width(8.dp))
            FilledIconButton(
                onClick = {
                    if (draft.isNotBlank()) {
                        onSend(draft)
                        draft = ""
                    }
                },
                enabled = draft.isNotBlank()
            ) {
                Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Enviar")
            }
        }
    }
}

@Composable
private fun MessageBubble(
    msg: ChatMessage,
    onPlayAudio: (String) -> Unit,
    onSaveFile: (fileName: String, mimeType: String?, base64: String) -> Unit,
    onReact: (emoji: String) -> Unit
) {
    var showReactions by remember { mutableStateOf(false) }
    val bubbleColor = if (msg.fromMe) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant
    val contentColor = if (msg.fromMe) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant

    Row(
        Modifier.fillMaxWidth().padding(4.dp),
        horizontalArrangement = if (msg.fromMe) Arrangement.End else Arrangement.Start
    ) {
        Column(horizontalAlignment = if (msg.fromMe) Alignment.End else Alignment.Start) {
            Box {
                Surface(
                    color = bubbleColor,
                    contentColor = contentColor,
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier.combinedClickable(
                        onClick = {},
                        onLongClick = { showReactions = true }
                    )
                ) {
                    Box(Modifier.padding(8.dp)) {
                        when (msg.kind) {
                            "image" -> {
                                val bmp = remember(msg.fileBase64) {
                                    msg.fileBase64?.let { com.tabasco.securechat.util.MediaUtils.base64ToBitmap(it) }
                                }
                                if (bmp != null) {
                                    Image(bitmap = bmp.asImageBitmap(), contentDescription = msg.fileName, modifier = Modifier.size(200.dp))
                                } else {
                                    Text("[imagen no disponible]")
                                }
                            }
                            "file" -> {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        Icons.Default.InsertDriveFile,
                                        contentDescription = null,
                                        modifier = Modifier.size(28.dp)
                                    )
                                    Spacer(Modifier.width(8.dp))
                                    Column {
                                        Text(msg.fileName ?: "archivo", style = MaterialTheme.typography.bodyMedium)
                                        TextButton(
                                            onClick = {
                                                if (msg.fileBase64 != null) onSaveFile(msg.fileName ?: "archivo", msg.mimeType, msg.fileBase64)
                                            },
                                            contentPadding = PaddingValues(0.dp)
                                        ) {
                                            Icon(Icons.Default.Download, contentDescription = null, modifier = Modifier.size(16.dp))
                                            Spacer(Modifier.width(4.dp))
                                            Text("Guardar archivo")
                                        }
                                    }
                                }
                            }
                            "audio" -> {
                                FilledTonalButton(
                                    onClick = { msg.fileBase64?.let(onPlayAudio) },
                                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                                ) {
                                    Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(20.dp))
                                    Spacer(Modifier.width(6.dp))
                                    Text("Audio (${(msg.durationMs ?: 0) / 1000}s)")
                                }
                            }
                            "system" -> Text(msg.text ?: "", fontSize = 12.sp)
                            else -> Text(msg.text ?: "")
                        }
                    }
                }
                DropdownMenu(expanded = showReactions, onDismissRequest = { showReactions = false }) {
                    Row(Modifier.padding(horizontal = 8.dp)) {
                        REACTION_EMOJIS.forEach { emoji ->
                            TextButton(onClick = { onReact(emoji); showReactions = false }) { Text(emoji) }
                        }
                    }
                }
            }
            if (msg.reactions.isNotEmpty()) {
                Row(Modifier.padding(top = 2.dp)) {
                    msg.reactions.forEach { emoji ->
                        Text(emoji, fontSize = 14.sp, modifier = Modifier.padding(end = 2.dp))
                    }
                }
            }
        }
    }
}
