plugins {
    id(libs.plugins.photoeditor.kotlin.library.get().pluginId)
    alias(libs.plugins.kotlin.serialization)
    id(libs.plugins.koin.plugin.get().pluginId)
}

dependencies {
    implementation(libs.bundles.ktor)
    implementation(libs.androidx.paging.common)
    implementation(project(":common-core"))
}