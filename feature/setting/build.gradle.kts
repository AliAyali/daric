plugins {
    alias(libs.plugins.daric.android.feature.impl)
    alias(libs.plugins.daric.android.feature.api)
    alias(libs.plugins.daric.android.library.compose)
}

android {
    namespace = "com.aliayali.setting"
    testOptions.unitTests.isIncludeAndroidResources = true
}

dependencies {
    implementation(libs.androidx.appcompat)
    implementation(projects.core.model)
    implementation(projects.core.domain)
    implementation(projects.core.datastore)

    testImplementation(projects.core.testing)

    androidTestImplementation(libs.bundles.androidx.compose.ui.test)
}