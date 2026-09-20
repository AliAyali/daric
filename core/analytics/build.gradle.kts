plugins {
    alias(libs.plugins.daric.android.library)
    alias(libs.plugins.daric.android.library.compose)
    alias(libs.plugins.daric.hilt)
}

android {
    namespace = "com.aliayali.analytics"
}

dependencies {
    implementation(libs.androidx.compose.runtime)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
}