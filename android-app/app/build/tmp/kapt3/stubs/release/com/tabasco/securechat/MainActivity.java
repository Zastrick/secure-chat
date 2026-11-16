package com.tabasco.securechat;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000x\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u001c\u0010&\u001a\u0004\u0018\u00010\'2\u0006\u0010(\u001a\u00020\u00122\b\b\u0002\u0010)\u001a\u00020*H\u0002J\u0018\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020\u00122\u0006\u0010.\u001a\u00020/H\u0002J\b\u00100\u001a\u00020\bH\u0002J\u0012\u00101\u001a\u00020,2\b\u00102\u001a\u0004\u0018\u000103H\u0014J\b\u00104\u001a\u00020,H\u0014J\u0010\u00105\u001a\u00020,2\u0006\u00106\u001a\u00020\u0012H\u0002J\u0010\u00107\u001a\n 9*\u0004\u0018\u00010808H\u0002J\u001a\u0010:\u001a\u00020,2\u0006\u0010;\u001a\u00020\u00122\b\u0010<\u001a\u0004\u0018\u00010\u0012H\u0002J\"\u0010=\u001a\u00020,2\u0006\u0010>\u001a\u00020\u00122\b\u0010?\u001a\u0004\u0018\u00010\u00122\u0006\u00106\u001a\u00020\u0012H\u0002J\"\u0010@\u001a\u00020,2\u0006\u0010-\u001a\u00020\u00122\u0006\u0010A\u001a\u00020\u00122\b\u0010B\u001a\u0004\u0018\u00010CH\u0002J\b\u0010D\u001a\u00020,H\u0002J\u0010\u0010E\u001a\u00020,2\u0006\u0010-\u001a\u00020\u0012H\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R+\u0010\t\u001a\u00020\b2\u0006\u0010\u0007\u001a\u00020\b8B@BX\u0082\u008e\u0002\u00a2\u0006\u0012\n\u0004\b\r\u0010\u000e\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u0012X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00120\u0015X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00120\u0015X\u0082\u0004\u00a2\u0006\u0002\n\u0000R+\u0010\u0017\u001a\u00020\u00122\u0006\u0010\u0007\u001a\u00020\u00128B@BX\u0082\u008e\u0002\u00a2\u0006\u0012\n\u0004\b\u001c\u0010\u000e\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR/\u0010\u001d\u001a\u0004\u0018\u00010\u00122\b\u0010\u0007\u001a\u0004\u0018\u00010\u00128B@BX\u0082\u008e\u0002\u00a2\u0006\u0012\n\u0004\b \u0010\u000e\u001a\u0004\b\u001e\u0010\u0019\"\u0004\b\u001f\u0010\u001bR\u0014\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00120\u0015X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020#X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020%X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006F"}, d2 = {"Lcom/tabasco/securechat/MainActivity;", "Landroidx/activity/ComponentActivity;", "()V", "audioFile", "Ljava/io/File;", "crypto", "Lcom/tabasco/securechat/crypto/CryptoManager;", "<set-?>", "", "isRecording", "()Z", "setRecording", "(Z)V", "isRecording$delegate", "Landroidx/compose/runtime/MutableState;", "mediaRecorder", "Landroid/media/MediaRecorder;", "myId", "", "pendingAttachTargetId", "pickAttachmentLauncher", "Landroidx/activity/result/ActivityResultLauncher;", "pickProfilePhotoLauncher", "profileName", "getProfileName", "()Ljava/lang/String;", "setProfileName", "(Ljava/lang/String;)V", "profileName$delegate", "profilePhotoBase64", "getProfilePhotoBase64", "setProfilePhotoBase64", "profilePhotoBase64$delegate", "recordAudioPermissionLauncher", "recordStartMs", "", "relay", "Lcom/tabasco/securechat/network/RelayClient;", "generateQrBitmap", "Landroid/graphics/Bitmap;", "content", "size", "", "handleAttachmentPicked", "", "toId", "uri", "Landroid/net/Uri;", "hasRecordPermission", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "playAudioBase64", "base64", "profilePrefs", "Landroid/content/SharedPreferences;", "kotlin.jvm.PlatformType", "saveProfile", "name", "photoBase64", "saveReceivedFile", "fileName", "mimeType", "sendPayload", "plaintext", "localEcho", "Lcom/tabasco/securechat/data/ChatMessage;", "startRecording", "stopRecordingAndSend", "app_release"})
public final class MainActivity extends androidx.activity.ComponentActivity {
    private com.tabasco.securechat.crypto.CryptoManager crypto;
    private com.tabasco.securechat.network.RelayClient relay;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String myId = "";
    @org.jetbrains.annotations.NotNull()
    private final androidx.compose.runtime.MutableState profileName$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.compose.runtime.MutableState profilePhotoBase64$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.activity.result.ActivityResultLauncher<java.lang.String> pickProfilePhotoLauncher = null;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String pendingAttachTargetId;
    @org.jetbrains.annotations.NotNull()
    private final androidx.activity.result.ActivityResultLauncher<java.lang.String> pickAttachmentLauncher = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.activity.result.ActivityResultLauncher<java.lang.String> recordAudioPermissionLauncher = null;
    @org.jetbrains.annotations.Nullable()
    private android.media.MediaRecorder mediaRecorder;
    @org.jetbrains.annotations.Nullable()
    private java.io.File audioFile;
    private long recordStartMs = 0L;
    @org.jetbrains.annotations.NotNull()
    private final androidx.compose.runtime.MutableState isRecording$delegate = null;
    
    public MainActivity() {
        super(0);
    }
    
    private final android.content.SharedPreferences profilePrefs() {
        return null;
    }
    
    private final java.lang.String getProfileName() {
        return null;
    }
    
    private final void setProfileName(java.lang.String p0) {
    }
    
    private final java.lang.String getProfilePhotoBase64() {
        return null;
    }
    
    private final void setProfilePhotoBase64(java.lang.String p0) {
    }
    
    private final void saveProfile(java.lang.String name, java.lang.String photoBase64) {
    }
    
    private final boolean isRecording() {
        return false;
    }
    
    private final void setRecording(boolean p0) {
    }
    
    private final boolean hasRecordPermission() {
        return false;
    }
    
    private final void startRecording() {
    }
    
    private final void stopRecordingAndSend(java.lang.String toId) {
    }
    
    private final void playAudioBase64(java.lang.String base64) {
    }
    
    private final void handleAttachmentPicked(java.lang.String toId, android.net.Uri uri) {
    }
    
    private final void saveReceivedFile(java.lang.String fileName, java.lang.String mimeType, java.lang.String base64) {
    }
    
    private final void sendPayload(java.lang.String toId, java.lang.String plaintext, com.tabasco.securechat.data.ChatMessage localEcho) {
    }
    
    private final android.graphics.Bitmap generateQrBitmap(java.lang.String content, int size) {
        return null;
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override()
    protected void onDestroy() {
    }
}