plugins {
    id(libs.plugins.photoeditor.kotlin.library.get().pluginId)
//    id(libs.plugins.koin.plugin.get().pluginId)
}

dependencies {
    implementation(libs.koin.core)
    implementation(libs.bundles.ktor)
    implementation(project(":data-network"))
    implementation(project(":common-core"))
}

