plugins {
    alias(libs.plugins.daric.android.library)
    alias(libs.plugins.daric.android.library.jacoco)
    id("com.google.devtools.ksp")
}
android {
    namespace = "com.aliayali.daric.core.domain"
}
dependencies {
    implementation(projects.core.data)
    implementation(projects.core.model)

    implementation(libs.javax.inject)

    testImplementation(projects.core.testing)
}
