plugins {
    alias(libs.plugins.daric.jvm.library)
    alias(libs.plugins.daric.hilt)
}
dependencies {
    implementation(libs.kotlinx.coroutines.core)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.turbine)
}