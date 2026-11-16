package com.tabasco.securechat.ui;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000*\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\u001aE\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00050\b2#\b\u0002\u0010\t\u001a\u001d\u0012\u0013\u0012\u00110\u0001\u00a2\u0006\f\b\u000b\u0012\b\b\f\u0012\u0004\b\b(\r\u0012\u0004\u0012\u00020\u00050\nH\u0007\u001a\u000e\u0010\u000e\u001a\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u0001\u001a\u000e\u0010\u0010\u001a\u00020\u00012\u0006\u0010\u0011\u001a\u00020\u0012\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0086T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0082T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0003\u001a\u00020\u0001X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0013"}, d2 = {"DEFAULT_SERVER_URL", "", "KEY_SERVER_URL", "PREFS_SETTINGS", "SettingsScreen", "", "connectionStatus", "onClose", "Lkotlin/Function0;", "onServerUrlChanged", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "newUrl", "normalizeServerUrl", "rawUrl", "savedServerUrl", "context", "Landroid/content/Context;", "app_debug"})
public final class SettingsScreenKt {
    
    /**
     * Pantalla de Ajustes de Conexión.
     *
     * Permite configurar el servidor relay para que SecureChat Tabasco funcione:
     * - A través de INTERNET GLOBAL (sin importar la red, WiFi, 4G o 5G en cualquier lugar)
     *  usando wss:// con hostings en la nube (Render, Railway, Fly.io, etc.) o túneles públicos.
     * - O en red local para pruebas escolares (ws://).
     */
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String PREFS_SETTINGS = "securechat_settings";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_SERVER_URL = "server_url";
    
    /**
     * URL por defecto para conexión global.
     * Por defecto apunta al relay en la nube con protocolo seguro WSS.
     * Si se corre en local se puede cambiar en 1 toque con los botones de abajo.
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String DEFAULT_SERVER_URL = "wss://securechat-tabasco.onrender.com";
    
    /**
     * Normaliza la dirección ingresada para evitar errores comunes de tipeo:
     * - Si el usuario escribe "mi-app.onrender.com", se convierte en "wss://mi-app.onrender.com"
     * - Si escribe "https://...", se convierte en "wss://..."
     * - Si escribe "http://...", se convierte en "ws://..."
     * - Si es una IP local o localhost, usa "ws://"
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String normalizeServerUrl(@org.jetbrains.annotations.NotNull()
    java.lang.String rawUrl) {
        return null;
    }
    
    /**
     * Lee la URL guardada en SharedPreferences o devuelve la predeterminada.
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String savedServerUrl(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
    
    @androidx.compose.runtime.Composable()
    public static final void SettingsScreen(@org.jetbrains.annotations.NotNull()
    java.lang.String connectionStatus, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClose, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onServerUrlChanged) {
    }
}