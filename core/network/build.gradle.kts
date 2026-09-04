import com.android.build.api.variant.BuildConfigField
import java.io.StringReader
import java.util.Properties

plugins {
    alias(libs.plugins.daric.android.library)
    alias(libs.plugins.daric.android.library.jacoco)
    alias(libs.plugins.daric.hilt)
    id("kotlinx-serialization")
}

android {
    buildFeatures {
        buildConfig = true
    }
    namespace = "com.aliayali.network"
    testOptions.unitTests.isIncludeAndroidResources = true
}

dependencies {
    implementation(libs.kotlinx.datetime)
    implementation(projects.core.common)
    implementation(projects.core.model)

    implementation(libs.coil.kt)
    implementation(libs.coil.kt.svg)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.okhttp.logging)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)

    testImplementation(libs.kotlinx.coroutines.test)
}

val newsApiKey = providers.fileContents(
    isolated.rootProject.projectDirectory.file("local.properties")
).asText.map { text ->
    val properties = Properties()
    properties.load(StringReader(text))
    properties["newsApiKey"]
}.orElse("")

val coinGeckoApiKey = providers.fileContents(
    isolated.rootProject.projectDirectory.file("local.properties")
).asText.map { text ->
    val properties = Properties()
    properties.load(StringReader(text))
    properties["coinGeckoApiKey"]
}.orElse("")

val brsApiKey = providers.fileContents(
    isolated.rootProject.projectDirectory.file("local.properties")
).asText.map { text ->
    val properties = Properties()
    properties.load(StringReader(text))
    properties["brsApiKey"]
}.orElse("")

androidComponents {
    onVariants {
        it.buildConfigFields!!.put(
            "NEWS_API_KEY",
            newsApiKey.map { value ->
                BuildConfigField(
                    type = "String",
                    value = "\"$value\"",
                    comment = null,
                )
            },
        )

        it.buildConfigFields!!.put(
            "COIN_GECKO_API_KEY",
            coinGeckoApiKey.map { value ->
                BuildConfigField(
                    type = "String",
                    value = "\"$value\"",
                    comment = null,
                )
            },
        )

        it.buildConfigFields!!.put(
            "BRS_API_KEY",
            brsApiKey.map { value ->
                BuildConfigField(
                    type = "String",
                    value = "\"$value\"",
                    comment = null,
                )
            },
        )
    }
}