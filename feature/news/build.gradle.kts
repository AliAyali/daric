plugins {
    alias(libs.plugins.roborazzi)
    alias(libs.plugins.daric.android.feature.api)
    alias(libs.plugins.daric.android.feature.impl)
    alias(libs.plugins.daric.android.library.compose)
    alias(libs.plugins.daric.android.library.jacoco)
}

android {
    namespace = "com.aliayali.news"
    testOptions.unitTests.isIncludeAndroidResources = true
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.navigation)
    implementation(projects.core.model)
    implementation(projects.core.common)
    implementation(libs.androidx.activity.compose)
    testImplementation(libs.hilt.android.testing)
    testImplementation(libs.robolectric)
    testImplementation(projects.core.testing)
    androidTestImplementation(projects.core.testing)
}