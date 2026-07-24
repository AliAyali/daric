plugins {
    alias(libs.plugins.daric.android.feature.impl)
    alias(libs.plugins.daric.android.feature.api)
    alias(libs.plugins.daric.android.library.compose)
    alias(libs.plugins.roborazzi)
}

android {
    namespace = "com.aliayali.setting"
    testOptions.unitTests.isIncludeAndroidResources = true
}

dependencies {
    implementation(libs.accompanist.permissions)
    implementation(projects.core.domain)
    implementation(projects.core.navigation)
    implementation(libs.androidx.activity.compose)
    testImplementation(libs.hilt.android.testing)
    testImplementation(libs.robolectric)
    testImplementation(projects.core.testing)
    androidTestImplementation(projects.core.testing)
}