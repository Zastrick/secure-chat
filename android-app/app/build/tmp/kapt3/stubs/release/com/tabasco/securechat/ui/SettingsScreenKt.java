package com.tabasco.securechat.ui;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000\u001e\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u0016\u0010\u0004\u001a\u00020\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0007H\u0007\u001a\u000e\u0010\b\u001a\u00020\u00012\u0006\u0010\t\u001a\u00020\n\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0086T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0082T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0003\u001a\u00020\u0001X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2 = {"DEFAULT_SERVER_URL", "", "KEY_SERVER_URL", "PREFS_SETTINGS", "SettingsScreen", "", "onClose", "Lkotlin/Function0;", "savedServerUrl", "context", "Landroid/content/Context;", "app_release"})
public final class SettingsScreenKt {
    
    /**
     * Pantalla de Ajustes.
     *
     * Por ahora solo expone la URL del servidor relay.
     * Se guarda en SharedPreferences para sobrevivir a reinicios de la app.
     *
     * Cambiar la URL requiere reiniciar la app (o reconectar el RelayClient).
     * Se muestra un aviso al guardar para que el usuario lo sepa.
     */
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String PREFS_SETTINGS = "securechat_settings";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_SERVER_URL = "server_url";
    
    /**
     * URL por defecto: emulador Android → localhost de la PC.
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String DEFAULT_SERVER_URL = "ws://10.0.2.2:8787";
    
    /**
     * Lee la URL guardada en prefs, o devuelve la URL por defecto.
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String savedServerUrl(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
    
    @androidx.compose.runtime.Composable()
    public static final void SettingsScreen(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClose) {
    }
}