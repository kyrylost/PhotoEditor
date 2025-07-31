plugins {
    id(libs.plugins.photoeditor.kotlin.library.get().pluginId)
    id(libs.plugins.koin.plugin.get().pluginId)
}

dependencies {
    implementation(project(":data-repository"))
    implementation(project(":domain-usecase"))
    implementation(project(":common-core"))
    implementation(libs.androidx.paging.common)
}