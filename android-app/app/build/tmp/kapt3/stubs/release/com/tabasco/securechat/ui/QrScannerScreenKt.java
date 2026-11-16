package com.tabasco.securechat.ui;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000\u001e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u001a9\u0010\u0000\u001a\u00020\u00012!\u0010\u0002\u001a\u001d\u0012\u0013\u0012\u00110\u0004\u00a2\u0006\f\b\u0005\u0012\b\b\u0006\u0012\u0004\b\b(\u0007\u0012\u0004\u0012\u00020\u00010\u00032\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00010\tH\u0007\u00a8\u0006\n"}, d2 = {"QrScannerScreen", "", "onScanned", "Lkotlin/Function1;", "", "Lkotlin/ParameterName;", "name", "qrValue", "onCancel", "Lkotlin/Function0;", "app_release"})
public final class QrScannerScreenKt {
    
    /**
     * Pantalla de escáner QR.
     *
     * Flujo:
     * 1. Si no hay permiso de cámara, muestra botón para pedirlo.
     * 2. Una vez concedido, abre la cámara trasera con CameraX.
     * 3. ML Kit analiza cada fotograma buscando un código QR.
     * 4. Al encontrarlo llama a [onScanned] con el texto del QR y cierra.
     *
     * El caller (AppRoot) es quien decide qué hacer con el resultado
     * (normalmente agregar el Session ID como nuevo contacto).
     */
    @androidx.annotation.OptIn(markerClass = {androidx.camera.core.ExperimentalGetImage.class})
    @androidx.compose.runtime.Composable()
    public static final void QrScannerScreen(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onScanned, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onCancel) {
    }
}