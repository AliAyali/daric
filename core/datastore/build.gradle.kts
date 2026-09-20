plugins {
    alias(libs.plugins.daric.android.library)
    alias(libs.plugins.daric.android.library.jacoco)
    alias(libs.plugins.daric.hilt)
}

android {
    namespace = "com.aliayali.datastore"
}

dependencies {
    api(libs.androidx.dataStore)
    api(projects.core.datastoreProto)
    implementation(projects.core.model)
    implementation(projects.core.domain)

    implementation(projects.core.common)

    testImplementation(libs.kotlinx.coroutines.test)
}