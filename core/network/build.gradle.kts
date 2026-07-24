plugins {
    alias(libs.plugins.daric.android.library)
    alias(libs.plugins.daric.android.library.jacoco)
    alias(libs.plugins.daric.hilt)
    id("kotlinx-serialization")
}

android {
    buildFeatures {
        buildConfig = true
    }
    namespace = "com.aliayali.network"
    testOptions.unitTests.isIncludeAndroidResources = true
}

dependencies {
    implementation(libs.kotlinx.datetime)
    implementation(projects.core.common)
    implementation(projects.core.model)

    implementation(libs.coil.kt)
    implementation(libs.coil.kt.svg)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.okhttp.logging)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)

    testImplementation(libs.kotlinx.coroutines.test)
}