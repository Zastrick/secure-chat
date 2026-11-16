plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    // kapt necesario para que Room genere el código de la base de datos
    id("org.jetbrains.kotlin.kapt")
}

android {
    namespace = "com.tabasco.securechat"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.tabasco.securechat"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    // Criptografía real (Google Tink) — ECIES sobre P-256 para cifrado E2E
    implementation("com.google.crypto.tink:tink-android:1.20.0")

    // Generación de códigos QR (para compartir el Session ID)
    implementation("com.google.zxing:core:3.5.3")

    // Cliente WebSocket para hablar con el servidor relay
    implementation("com.squareup.okhttp3:okhttp:4.12.0")

    // Guardar la llave privada de forma segura en el dispositivo
    implementation("androidx.security:security-crypto:1.1.0-alpha06")

    // UI con Jetpack Compose
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.activity:activity-compose:1.9.0")
    implementation(platform("androidx.compose:compose-bom:2024.06.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material-icons-extended")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.2")

    // ── Persistencia local con Room ──────────────────────────────────────────
    // Nota: no usamos SQLCipher para no añadir complejidad al prototipo escolar.
    // En producción se reemplazaría el driver por SQLCipher para cifrar la BD.
    val roomVersion = "2.6.1"
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")

    // ── Escáner de QR (CameraX + ML Kit) ────────────────────────────────────
    // Versión 1.4.1+ incluye alineación de bibliotecas nativas a 16 KB (requerido por Android 15+)
    val cameraVersion = "1.4.1"
    implementation("androidx.camera:camera-core:$cameraVersion")
    implementation("androidx.camera:camera-camera2:$cameraVersion")
    implementation("androidx.camera:camera-lifecycle:$cameraVersion")
    implementation("androidx.camera:camera-view:$cameraVersion")
    // ML Kit Barcode detecta QR sin necesidad de conexión a internet
    implementation("com.google.mlkit:barcode-scanning:17.3.0")
    // Interop Compose → View para embeber PreviewView en un Composable
    implementation("androidx.compose.ui:ui-viewbinding")
}
