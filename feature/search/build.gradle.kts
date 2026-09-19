plugins {
    alias(libs.plugins.roborazzi)
    alias(libs.plugins.daric.android.feature.api)
    alias(libs.plugins.daric.android.feature.impl)
    alias(libs.plugins.daric.android.library.compose)
    alias(libs.plugins.daric.android.library.jacoco)
}

android {
    namespace = "com.aliayali.search"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.feature.marketdetail)
    implementation(projects.core.common)
    implementation(projects.core.model)
    testImplementation(projects.core.testing)

    androidTestImplementation(libs.bundles.androidx.compose.ui.test)
    androidTestImplementation(projects.core.testing)
}