plugins {
    id(libs.plugins.photoeditor.android.library.get().pluginId)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "dev.stukalo.presentation.core.navigation"
}

dependencies {
    implementation(project(":common-core"))
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.navigation.compose)
}