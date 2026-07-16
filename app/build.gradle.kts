import java.util.Properties

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlinCompose)
    alias(libs.plugins.kotlinKapt)
    alias(libs.plugins.hilt)
    alias(libs.plugins.googleServices)
}

val localProperties = Properties().apply {
    val localPropertiesFile = rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        localPropertiesFile.inputStream().use(::load)
    }
}

fun localProperty(name: String, defaultValue: String = ""): String {
    return localProperties.getProperty(name)
        ?.trim()
        ?.takeIf(String::isNotEmpty)
        ?: defaultValue
}

fun String.asBuildConfigString(): String {
    return replace("\\", "\\\\").replace("\"", "\\\"")
}

android {
    namespace = "com.example.pmuprojekat"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.pmuprojekat"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        /*
         * Prototype-only configuration: a key exposed through BuildConfig is bundled
         * inside the APK and can be extracted. For production, use a backend proxy.
         */
        buildConfigField(
            "String",
            "OPENAI_API_KEY",
            "\"${localProperty("OPENAI_API_KEY").asBuildConfigString()}\""
        )
        buildConfigField(
            "String",
            "OPENAI_API_BASE_URL",
            "\"${localProperty("OPENAI_API_BASE_URL", "https://api.openai.com/v1/chat/completions").asBuildConfigString()}\""
        )
        buildConfigField(
            "String",
            "OPENAI_MODEL",
            "\"${localProperty("OPENAI_MODEL", "gpt-5.6-luna").asBuildConfigString()}\""
        )
        buildConfigField(
            "String",
            "OPENAI_VISION_MODEL",
            "\"${localProperty("OPENAI_VISION_MODEL", localProperty("OPENAI_MODEL", "gpt-5.6-luna")).asBuildConfigString()}\""
        )
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)

    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)

    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    kapt(libs.androidx.room.compiler)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.storage)
    implementation(libs.coil.compose)

    testImplementation(libs.junit)
    testImplementation("org.json:json:20240303")

    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)

    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)
}
