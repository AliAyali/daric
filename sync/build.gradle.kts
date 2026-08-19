plugins {
    alias(libs.plugins.daric.android.library)
    alias(libs.plugins.daric.hilt)
    alias(libs.plugins.daric.android.library.jacoco)
}

android {
    namespace = "com.aliayali.sync"
}

dependencies {
    ksp(libs.hilt.ext.compiler)

    implementation(libs.hilt.android.testing)
    implementation(libs.androidx.tracing.ktx)
    implementation(libs.androidx.work.ktx)
    implementation(libs.hilt.ext.work)
    implementation(projects.core.model)
    implementation(projects.core.domain)

    androidTestImplementation(libs.androidx.work.testing)
    androidTestImplementation(libs.hilt.android.testing)
    androidTestImplementation(libs.kotlinx.coroutines.guava)
    androidTestImplementation(projects.core.testing)
}