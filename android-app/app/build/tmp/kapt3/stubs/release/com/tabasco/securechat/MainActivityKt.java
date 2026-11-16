package com.tabasco.securechat;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000\\\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0015\n\u0002\u0018\u0002\n\u0000\u001a\u009e\u0004\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\t2\u0006\u0010\u000e\u001a\u00020\t2\b\u0010\u000f\u001a\u0004\u0018\u00010\t2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00112\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\f0\u00142!\u0010\u0015\u001a\u001d\u0012\u0013\u0012\u00110\t\u00a2\u0006\f\b\u0017\u0012\b\b\u0018\u0012\u0004\b\b(\u0018\u0012\u0004\u0012\u00020\f0\u00162\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\f0\u00142\u0014\u0010\u001a\u001a\u0010\u0012\u0004\u0012\u00020\t\u0012\u0006\u0012\u0004\u0018\u00010\u001b0\u001626\u0010\u001c\u001a2\u0012\u0013\u0012\u00110\t\u00a2\u0006\f\b\u0017\u0012\b\b\u0018\u0012\u0004\b\b(\u001e\u0012\u0013\u0012\u00110\t\u00a2\u0006\f\b\u0017\u0012\b\b\u0018\u0012\u0004\b\b(\u001f\u0012\u0004\u0012\u00020\f0\u001d2!\u0010 \u001a\u001d\u0012\u0013\u0012\u00110\t\u00a2\u0006\f\b\u0017\u0012\b\b\u0018\u0012\u0004\b\b(\u001e\u0012\u0004\u0012\u00020\f0\u00162\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\f0\u00142!\u0010\"\u001a\u001d\u0012\u0013\u0012\u00110\t\u00a2\u0006\f\b\u0017\u0012\b\b\u0018\u0012\u0004\b\b(\u001e\u0012\u0004\u0012\u00020\f0\u00162\u0012\u0010#\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\f0\u00162M\u0010$\u001aI\u0012\u0013\u0012\u00110\t\u00a2\u0006\f\b\u0017\u0012\b\b\u0018\u0012\u0004\b\b(&\u0012\u0015\u0012\u0013\u0018\u00010\t\u00a2\u0006\f\b\u0017\u0012\b\b\u0018\u0012\u0004\b\b(\'\u0012\u0013\u0012\u00110\t\u00a2\u0006\f\b\u0017\u0012\b\b\u0018\u0012\u0004\b\b((\u0012\u0004\u0012\u00020\f0%2K\u0010)\u001aG\u0012\u0013\u0012\u00110\t\u00a2\u0006\f\b\u0017\u0012\b\b\u0018\u0012\u0004\b\b(\u001e\u0012\u0013\u0012\u00110\t\u00a2\u0006\f\b\u0017\u0012\b\b\u0018\u0012\u0004\b\b(*\u0012\u0013\u0012\u00110\t\u00a2\u0006\f\b\u0017\u0012\b\b\u0018\u0012\u0004\b\b(+\u0012\u0004\u0012\u00020\f0%2!\u0010,\u001a\u001d\u0012\u0013\u0012\u00110\t\u00a2\u0006\f\b\u0017\u0012\b\b\u0018\u0012\u0004\b\b(\u001e\u0012\u0004\u0012\u00020\f0\u001626\u0010-\u001a2\u0012\u0013\u0012\u00110\t\u00a2\u0006\f\b\u0017\u0012\b\b\u0018\u0012\u0004\b\b(.\u0012\u0013\u0012\u00110\t\u00a2\u0006\f\b\u0017\u0012\b\b\u0018\u0012\u0004\b\b(/\u0012\u0004\u0012\u00020\f0\u001dH\u0007\u001a8\u00100\u001a\u00020\f2\u0012\u00101\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\f0\u00162\f\u00102\u001a\b\u0012\u0004\u0012\u00020\f0\u00142\f\u00103\u001a\b\u0012\u0004\u0012\u00020\f0\u0014H\u0007\u001a\u008d\u0002\u00104\u001a\u00020\f2\u0006\u00105\u001a\u00020\t2\u0006\u0010\u0012\u001a\u00020\u00112\f\u00106\u001a\b\u0012\u0004\u0012\u00020\f0\u00142\u0012\u00107\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\f0\u00162\f\u00108\u001a\b\u0012\u0004\u0012\u00020\f0\u00142\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\f0\u00142\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\f0\u00142\u0012\u0010#\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\f0\u00162M\u0010$\u001aI\u0012\u0013\u0012\u00110\t\u00a2\u0006\f\b\u0017\u0012\b\b\u0018\u0012\u0004\b\b(&\u0012\u0015\u0012\u0013\u0018\u00010\t\u00a2\u0006\f\b\u0017\u0012\b\b\u0018\u0012\u0004\b\b(\'\u0012\u0013\u0012\u00110\t\u00a2\u0006\f\b\u0017\u0012\b\b\u0018\u0012\u0004\b\b((\u0012\u0004\u0012\u00020\f0%26\u0010)\u001a2\u0012\u0013\u0012\u00110\t\u00a2\u0006\f\b\u0017\u0012\b\b\u0018\u0012\u0004\b\b(*\u0012\u0013\u0012\u00110\t\u00a2\u0006\f\b\u0017\u0012\b\b\u0018\u0012\u0004\b\b(+\u0012\u0004\u0012\u00020\f0\u001d2\f\u0010,\u001a\b\u0012\u0004\u0012\u00020\f0\u0014H\u0007\u001a\u0096\u0001\u00109\u001a\u00020\f2\u0006\u0010:\u001a\u00020;2\u0012\u0010#\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\f0\u00162M\u0010$\u001aI\u0012\u0013\u0012\u00110\t\u00a2\u0006\f\b\u0017\u0012\b\b\u0018\u0012\u0004\b\b(&\u0012\u0015\u0012\u0013\u0018\u00010\t\u00a2\u0006\f\b\u0017\u0012\b\b\u0018\u0012\u0004\b\b(\'\u0012\u0013\u0012\u00110\t\u00a2\u0006\f\b\u0017\u0012\b\b\u0018\u0012\u0004\b\b((\u0012\u0004\u0012\u00020\f0%2!\u0010)\u001a\u001d\u0012\u0013\u0012\u00110\t\u00a2\u0006\f\b\u0017\u0012\b\b\u0018\u0012\u0004\b\b(+\u0012\u0004\u0012\u00020\f0\u0016H\u0003\"\u0010\u0010\u0000\u001a\u00020\u0001X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u0002\"\u0010\u0010\u0003\u001a\u00020\u0001X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u0002\"\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0006\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\"\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\n\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006<"}, d2 = {"BrandPrimaryDark", "Landroidx/compose/ui/graphics/Color;", "J", "BrandPrimaryLight", "DarkColors", "Landroidx/compose/material3/ColorScheme;", "LightColors", "REACTION_EMOJIS", "", "", "scannedPubId", "AppRoot", "", "myId", "profileName", "profilePhotoBase64", "isDarkTheme", "", "isRecording", "onToggleTheme", "Lkotlin/Function0;", "onSaveProfile", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "onPickProfilePhoto", "onGenerateQr", "Landroid/graphics/Bitmap;", "onSendText", "Lkotlin/Function2;", "toId", "text", "onAttachClick", "onStartRecording", "onStopRecording", "onPlayAudio", "onSaveFile", "Lkotlin/Function3;", "fileName", "mimeType", "base64", "onReact", "messageId", "emoji", "onShareProfile", "onAddContactManual", "alias", "pubId", "ChatListScreen", "onOpenChat", "onShowMyId", "onAddContact", "ChatScreen", "contactId", "onBack", "onSend", "onAttach", "MessageBubble", "msg", "Lcom/tabasco/securechat/data/ChatMessage;", "app_release"})
@kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class, androidx.compose.foundation.ExperimentalFoundationApi.class})
public final class MainActivityKt {
    private static final long BrandPrimaryDark = 0L;
    private static final long BrandPrimaryLight = 0L;
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.material3.ColorScheme DarkColors = null;
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.material3.ColorScheme LightColors = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.util.List<java.lang.String> REACTION_EMOJIS = null;
    
    /**
     * Variable de estado a nivel de composición para pasar el Session ID
     * escaneado del QrScannerScreen al diálogo de "Agregar contacto".
     * Se limpia con LaunchedEffect al abrir el diálogo.
     */
    @org.jetbrains.annotations.NotNull()
    private static java.lang.String scannedPubId = "";
    
    @androidx.compose.runtime.Composable()
    public static final void AppRoot(@org.jetbrains.annotations.NotNull()
    java.lang.String myId, @org.jetbrains.annotations.NotNull()
    java.lang.String profileName, @org.jetbrains.annotations.Nullable()
    java.lang.String profilePhotoBase64, boolean isDarkTheme, boolean isRecording, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onToggleTheme, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onSaveProfile, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onPickProfilePhoto, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, android.graphics.Bitmap> onGenerateQr, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super java.lang.String, ? super java.lang.String, kotlin.Unit> onSendText, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onAttachClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onStartRecording, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onStopRecording, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onPlayAudio, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function3<? super java.lang.String, ? super java.lang.String, ? super java.lang.String, kotlin.Unit> onSaveFile, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function3<? super java.lang.String, ? super java.lang.String, ? super java.lang.String, kotlin.Unit> onReact, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onShareProfile, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super java.lang.String, ? super java.lang.String, kotlin.Unit> onAddContactManual) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void ChatListScreen(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onOpenChat, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onShowMyId, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onAddContact) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void ChatScreen(@org.jetbrains.annotations.NotNull()
    java.lang.String contactId, boolean isRecording, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onSend, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onAttach, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onStartRecording, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onStopRecording, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onPlayAudio, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function3<? super java.lang.String, ? super java.lang.String, ? super java.lang.String, kotlin.Unit> onSaveFile, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super java.lang.String, ? super java.lang.String, kotlin.Unit> onReact, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onShareProfile) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void MessageBubble(com.tabasco.securechat.data.ChatMessage msg, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onPlayAudio, kotlin.jvm.functions.Function3<? super java.lang.String, ? super java.lang.String, ? super java.lang.String, kotlin.Unit> onSaveFile, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onReact) {
    }
}