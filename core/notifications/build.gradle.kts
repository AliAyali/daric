plugins {
    alias(libs.plugins.daric.android.library)
    alias(libs.plugins.daric.hilt)
}

android {
    namespace = "com.aliayali.notifications"
}

dependencies {
    api(projects.core.model)
    implementation(projects.core.common)
    compileOnly(platform(libs.androidx.compose.bom))
}