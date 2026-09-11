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

    prodImplementation(platform(libs.firebase.bom))
    prodImplementation(libs.firebase.analytics)
}