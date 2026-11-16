# Guía: Conectar SecureChat Tabasco a través de Internet Global 🌐

Esta guía explica cómo poner a funcionar el servidor relay para que **cualquier persona se comunique desde cualquier lugar** (datos móviles 4G/5G, WiFi de casa, escuela, otra ciudad) sin estar en la misma red local.

---

## Opción 1: Servidor 100% Permanente en la Nube (Gratis y Recomendado) 🚀

Servicios como **Render.com** o **Railway** permiten alojar el servidor Node.js de forma gratuita, 24/7 y con protocolo seguro `wss://` (TLS) automático.

### Pasos en Render.com:
1. Sube la carpeta `server/` a un repositorio en GitHub (o todo el repo `securechat-tabasco`).
2. Entra a [render.com](https://render.com) y crea una cuenta gratuita.
3. Haz clic en **New +** > **Web Service**.
4. Conecta tu repositorio de GitHub.
5. Configura los campos:
   - **Root Directory:** `server` (o déjalo en blanco si el repo es solo la carpeta del server).
   - **Environment:** `Node`
   - **Build Command:** `npm install`
   - **Start Command:** `node server.js`
   - **Plan:** Free
6. Haz clic en **Create Web Service**.
7. En unos segundos Render te dará una URL pública, por ejemplo:
   `https://mi-chat-tabasco.onrender.com`
8. En la app Android, ve a **Ajustes (⚙️)** y escribe:
   `wss://mi-chat-tabasco.onrender.com`
   *(Si escribes https:// o el dominio solo, la app lo convertirá automáticamente a wss://)*.

¡Listo! A partir de ese momento cualquier celular con la app instalada podrá chatear y enviar archivos cifrados desde cualquier parte del mundo.

---

## Opción 2: Probar desde tu Computadora con un Túnel Público (En 10 segundos) ⚡

Si quieres probar de inmediato sin subir nada a la nube, puedes exponer el servidor de tu PC a Internet con **Localtunnel** o **Cloudflare**:

### Con Localtunnel:
1. En tu PC, abre una terminal en la carpeta `server/`:
   ```bash
   node server.js
   ```
2. En otra terminal, ejecuta:
   ```bash
   npm run tunnel
   ```
   O directamente:
   ```bash
   npx localtunnel --port 8787
   ```
3. Te dará una URL pública como:
   `https://clean-dogs-jump.loca.lt`
4. En la app Android en Ajustes (⚙️), pon:
   `wss://clean-dogs-jump.loca.lt`

### Con Cloudflare Tunnel (Muy estable):
Si tienes instalado `cloudflared`:
```bash
cloudflared tunnel --url http://localhost:8787
```
Te dará una URL `https://xxxx.trycloudflare.com` que puedes usar directamente con `wss://xxxx.trycloudflare.com`.

---

## Verificación en el Navegador

Puedes abrir la URL en cualquier navegador (desde tu celular con datos o PC):
- Ejemplo: `https://mi-chat-tabasco.onrender.com/health`
- Respuesta esperada:
  ```json
  {
    "status": "online",
    "app": "SecureChat Tabasco Relay",
    "connectedClients": 2,
    "queuedMessages": 0
  }
  ```
Esto te confirma que el servidor está 100% activo en Internet.
