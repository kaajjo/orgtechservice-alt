@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kapt)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.kaajjo.orgtechservice"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.kaajjo.orgtechservice"
        minSdk = 27
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.6"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)

    implementation(libs.retrofit2)
    implementation(libs.gson.converter)
    implementation(libs.logging.interceptor)

    implementation(libs.klock)

    implementation(libs.compose.destinations)
    ksp(libs.compose.destinations.ksp)

    implementation(libs.dagger.hilt)
    implementation(libs.dagger.hilt.navigation)
    kapt(libs.dagger.compiler)

    implementation(libs.material.icons.extended)

    implementation(libs.datastore)

    implementation(libs.paging.runtime)
    implementation(libs.paging.compose)

    implementation(libs.vico.compose)
    implementation(libs.vico.compose.m3)

    implementation(libs.accompanist.systemuicontroller)

    implementation(libs.coil)
    implementation(libs.coil.svg)
    implementation(libs.materialKolor)
}