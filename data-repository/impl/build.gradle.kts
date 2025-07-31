plugins {
    id(libs.plugins.photoeditor.kotlin.library.get().pluginId)
}

dependencies{
    implementation(libs.androidx.paging.common)
    implementation(libs.koin.core)
    implementation(project(":data-repository"))
    implementation(project(":data-network"))
    implementation(project(":common-core"))
}