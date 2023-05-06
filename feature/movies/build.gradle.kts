plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.example.movies"
    compileSdk = 33

    defaultConfig {
        minSdk = 24
        targetSdk = 33

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.5"
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(project(":core:model"))
    implementation(project(":core:data"))
    implementation(project(":core:domain"))

    implementation(libs.viewmodel.lifcycle)
    implementation(libs.androidx.core)
    implementation(libs.androidx.lifecycle)
    implementation(libs.androidx.activity)
    implementation(libs.compose.lifecycle)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.material3)
    implementation(libs.compose.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test)
    androidTestImplementation(libs.androidx.espresso)
    androidTestImplementation(libs.androidx.compose.ui.junit)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test)
    // coroutine test:
    testImplementation(libs.kotlinx.coroutines.test)
    // mockk
    testImplementation(libs.mockk.android)
    testImplementation(libs.mockk.agent)
    // kotlin.test
    implementation(kotlin("test-common"))
    implementation(kotlin("test"))

    // hilt
    implementation(libs.hilt)
    kapt(libs.hilt.compiler)
    // hilt navigation
    implementation(libs.hilt.navigation.compose)
    // coil
    implementation(libs.coil)
}