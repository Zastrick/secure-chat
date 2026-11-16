// server.js — Servidor relay para SecureChat Tabasco
//
// Funciona en red local O en cualquier servidor cloud / hosting de internet
// (Render, Railway, Fly.io, VPS, o mediante tunel público Cloudflare/Localtunnel).
//
// IMPORTANTE PARA TU REPORTE: este servidor SOLO ve:
//   - el ID (llave pública) del destinatario
//   - un blob de texto cifrado (ciphertext)
// Nunca ve el mensaje en claro, ni contraseñas, ni número de teléfono
// (porque no existe registro por teléfono, cada usuario ES su llave pública).
//
// Limitación honesta: este servidor SABE quién habla con quién (metadata),
// aunque no sabe QUÉ dicen. Session real resuelve esto con onion routing
// (varios nodos intermedios, ninguno ve origen+destino+contenido a la vez).
// Para el proyecto escolar, documenta esta diferencia como trabajo futuro.

const http = require('http');
const WebSocket = require('ws');

const PORT = process.env.PORT || 8787;

// id (clave pública en base64) -> socket conectado
const clients = new Map();
// Cola de mensajes para usuarios desconectados (en memoria; para producción usar Redis/DB)
const offlineQueue = new Map();

// Servidor HTTP base: permite responder a health-checks de hostings en la nube (Render, Railway, etc.)
// y permite verificar desde cualquier navegador si el servidor está activo.
const server = http.createServer((req, res) => {
  res.setHeader('Access-Control-Allow-Origin', '*');
  res.setHeader('Content-Type', 'application/json; charset=utf-8');

  if (req.url === '/health' || req.url === '/') {
    res.writeHead(200);
    res.end(JSON.stringify({
      status: 'online',
      app: 'SecureChat Tabasco Relay',
      connectedClients: clients.size,
      queuedMessages: offlineQueue.size,
      time: new Date().toISOString()
    }));
    return;
  }

  res.writeHead(404);
  res.end(JSON.stringify({ error: 'Ruta no encontrada' }));
});

const wss = new WebSocket.Server({ server });

function send(ws, obj) {
  if (ws && ws.readyState === WebSocket.OPEN) {
    ws.send(JSON.stringify(obj));
  }
}

wss.on('connection', (ws) => {
  let myId = null;

  ws.on('message', (raw) => {
    let msg;
    try {
      msg = JSON.parse(raw.toString());
    } catch (e) {
      return; // ignorar mensajes mal formados
    }

    if (msg.type === 'register') {
      myId = msg.id;
      clients.set(myId, ws);
      console.log(`[+] Cliente registrado: ${myId.slice(0, 12)}... (Total conectados: ${clients.size})`);

      // Entregar mensajes que llegaron mientras estaba desconectado
      const pending = offlineQueue.get(myId) || [];
      pending.forEach((env) => send(ws, { type: 'message', ...env }));
      offlineQueue.delete(myId);

      send(ws, { type: 'registered', id: myId });
      return;
    }

    if (msg.type === 'message' && myId) {
      const envelope = {
        type: 'message',
        from: myId,
        ciphertext: msg.ciphertext,
        ts: Date.now(),
      };
      const targetSocket = clients.get(msg.to);
      if (targetSocket) {
        send(targetSocket, envelope);
        console.log(`[→] Mensaje entregado inmediatamente a ${msg.to.slice(0, 12)}...`);
      } else {
        const queue = offlineQueue.get(msg.to) || [];
        queue.push(envelope);
        offlineQueue.set(msg.to, queue);
        console.log(`[i] Destinatario offline, mensaje en cola para ${msg.to.slice(0, 12)}...`);
      }
    }
  });

  ws.on('close', () => {
    if (myId) {
      clients.delete(myId);
      console.log(`[-] Cliente desconectado: ${myId.slice(0, 12)}... (Total conectados: ${clients.size})`);
    }
  });
});

server.listen(PORT, '0.0.0.0', () => {
  console.log(`====================================================`);
  console.log(`  SecureChat Tabasco — Servidor Relay Activo`);
  console.log(`  Puerto: ${PORT}`);
  console.log(`  Protocolos soportados: ws:// y wss://`);
  console.log(`  Health check HTTP: http://localhost:${PORT}/health`);
  console.log(`====================================================`);
});
