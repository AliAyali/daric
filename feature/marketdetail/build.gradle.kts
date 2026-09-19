plugins {
    alias(libs.plugins.daric.android.feature.impl)
    alias(libs.plugins.daric.android.feature.api)
    alias(libs.plugins.daric.android.library.compose)
    alias(libs.plugins.roborazzi)
}

android {
    namespace = "com.aliayali.marketdetail"
    testOptions.unitTests.isIncludeAndroidResources = true
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.core.designsystem)
    implementation(projects.core.domain)
    implementation(projects.core.model)
    implementation(libs.vico.compose)
    implementation(libs.vico.compose.m3)
    implementation(libs.androidx.activity.compose)
    testImplementation(libs.hilt.android.testing)
    testImplementation(libs.robolectric)
    testImplementation(projects.core.testing)
    androidTestImplementation(projects.core.testing)
}