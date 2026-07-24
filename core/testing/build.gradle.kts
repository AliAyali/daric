plugins {
    alias(libs.plugins.daric.android.library)
    alias(libs.plugins.daric.hilt)
}
android {
    namespace = "com.aliayali.daric.core.testing"
}
dependencies {
    api(libs.kotlinx.coroutines.test)
    api(projects.core.common)
    api(projects.core.data)
    api(projects.core.model)

    implementation(libs.androidx.test.rules)
    implementation(libs.hilt.android.testing)
    implementation(libs.kotlinx.datetime)
}