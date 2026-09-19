plugins {
    alias(libs.plugins.compose)
    alias(libs.plugins.daric.android.library)
    alias(libs.plugins.daric.hilt)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.aliayali.navigation"
}

dependencies {
    api(libs.androidx.navigation3.runtime)
    implementation(libs.androidx.savedstate.compose)
    implementation(libs.androidx.lifecycle.viewModel.navigation3)
}
