plugins {
    alias(libs.plugins.daric.android.library)
    alias(libs.plugins.daric.android.library.jacoco)
    alias(libs.plugins.daric.hilt)
    id("kotlinx-serialization")
}

android {
    namespace = "com.aliayali.data"
    testOptions.unitTests.isIncludeAndroidResources = true
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.core.network)

    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.kotlinx.serialization.json)
    testImplementation(projects.core.testing)
}