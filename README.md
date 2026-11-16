# SecureChat Tabasco — Prototipo de mensajería E2E sin número telefónico

Proyecto escolar: alternativa de comunicación para cuando se bloquean las
líneas telefónicas, inspirada en el funcionamiento de **Session**.

## Qué SÍ tiene este prototipo (real, no simulado)
- **Identidad sin número de teléfono**: tu "Session ID" es una llave pública
  generada en tu propio teléfono.
- **Cifrado de extremo a extremo real**, con Google Tink (ECIES sobre curva
  P-256). El servidor nunca ve el contenido de los mensajes, solo texto cifrado.
- **Transporte por internet/WiFi** en vez de red celular — así se evita el
  bloqueo de líneas.
- Mensajería en tiempo real vía WebSocket, con cola para cuando el
  destinatario está desconectado.

## Qué NO tiene (y debes explicar como limitación en tu reporte)
- **No hay onion routing** (enrutamiento por múltiples nodos anónimos) como
  la red real de Session. Aquí hay UN servidor relay central: no lee los
  mensajes, pero sí sabe quién le escribe a quién (metadata). Esto es la
  diferencia principal entre "cifrado E2E" (lo que tenemos) y "anonimato de
  red" (lo que Session añade encima). Es una limitación de tiempo, no técnica
  imposible — vale mencionar cómo se resolvería con más tiempo.
- No hay verificación de identidad fuera de banda (en Session real también
  confías en el ID compartido directamente, así que esto es comparable).
- Sin persistencia cifrada en disco (los mensajes viven en memoria mientras
  la app está abierta) — se resolvería con Room + SQLCipher.

## Cómo correrlo

### 1. Servidor relay
```bash
cd server
npm install
npm start
```
Esto levanta el servidor en `ws://localhost:8787`.

### 2. App Android
1. Abre la carpeta `android-app/` en Android Studio (Hedgehog o más reciente).
2. Deja que Gradle sincronice (descargará Tink, OkHttp, Compose).
3. Si usas el **emulador de Android Studio**, no cambies nada: `RelayClient`
   ya apunta a `10.0.2.2:8787`, que es como el emulador ve tu computadora.
4. Si usas un **teléfono físico**, conéctalo a la misma red WiFi que tu
   computadora, obtén la IP local de tu compu (`ipconfig` en Windows /
   `ifconfig` en Mac-Linux) y cambia en `MainActivity.kt`:
   ```kotlin
   relay = RelayClient(myId = myId, serverUrl = "ws://TU_IP_LOCAL:8787", ...)
   ```
5. Corre la app en dos dispositivos/emuladores distintos para probar el
   chat entre "dos personas".

### 3. Probar la conversación
1. Abre la app en el Dispositivo A → botón "Ver mi ID" → copia el ID.
2. Abre la app en el Dispositivo B → "Agregar contacto" → pega el ID de A,
   ponle un alias.
3. Repite al revés: comparte el ID de B con A.
4. Ya puedes escribirte — el mensaje sale cifrado desde el teléfono, pasa
   por el servidor como texto ilegible, y se descifra en el otro teléfono.

## Estructura del proyecto
```
securechat-tabasco/
├── server/                    # Servidor relay (Node.js + WebSocket)
│   ├── server.js
│   └── package.json
└── android-app/
    └── app/src/main/java/com/tabasco/securechat/
        ├── crypto/CryptoManager.kt     # Generación de llaves + cifrar/descifrar
        ├── network/RelayClient.kt      # Conexión WebSocket al servidor
        ├── data/AppState.kt            # Contactos y mensajes en memoria
        └── MainActivity.kt             # UI (Jetpack Compose)
```

## Para tu presentación/reporte
Vale la pena estructurar tu explicación así:
1. **El problema**: bloqueo de líneas telefónicas en Tabasco corta la
   comunicación por SMS/llamadas/datos de un operador específico.
2. **La idea de Session**: identidad sin teléfono + cifrado E2E + no depender
   de un solo operador — cualquier conexión a internet (WiFi de otro lugar,
   otro operador, etc.) sirve para comunicarte.
3. **Lo que construiste**: un prototipo funcional con cifrado E2E real que
   demuestra el concepto central.
4. **Lo que falta para ser "igual" a Session en producción**: red
   descentralizada de nodos (onion routing), persistencia cifrada,
   verificación de contactos, apps para más plataformas — y por qué eso
   requiere mucho más tiempo/equipo de desarrollo.
