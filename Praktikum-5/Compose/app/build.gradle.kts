plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.0" // Sesuaikan dengan versi Kotlin Anda
    id("com.google.devtools.ksp") version "2.0.0-1.0.21" // Sesuaikan dengan versi Kotlin Anda
}

android {
    namespace = "com.pemrogramanmobile.apicompose"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.pemrogramanmobile.apicompose"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // KotlinX Serialization
    implementation(libs.kotlinx.serialization.json) // Cek versi terbaru

    // Retrofit & KotlinX Serialization Converter
    implementation(libs.retrofit) // Cek versi terbaru
    implementation(libs.retrofit2.kotlinx.serialization.converter) // Cek versi terbaru
    implementation(libs.logging.interceptor) // Opsional, untuk logging

    // Flow
    implementation(libs.kotlinx.coroutines.core) // Cek versi terbaru
    implementation(libs.kotlinx.coroutines.android) // Cek versi terbaru

    // Coil
    implementation(libs.coil.compose) // Cek versi terbaru

    // Room
    implementation(libs.androidx.room.runtime) // Cek versi terbaru
    ksp(libs.androidx.room.compiler) // Gunakan ksp, bukan kapt
    implementation(libs.androidx.room.ktx) // Untuk Coroutines & Flow support

    //Navigation
    implementation(libs.androidx.navigation.compose.v277)

    // Jetpack Compose
    implementation(platform(libs.androidx.compose.bom.v20240100)) // Cek BOM terbaru
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    implementation(libs.androidx.lifecycle.viewmodel.compose) // ViewModel untuk Compose
    implementation(libs.androidx.navigation.compose) // Untuk navigasi antar layar
}