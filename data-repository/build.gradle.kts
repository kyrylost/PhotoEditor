plugins {
    id(libs.plugins.photoeditor.kotlin.library.get().pluginId)
}

dependencies{
    implementation(libs.androidx.paging.common)
    implementation(project(":data-network"))
    implementation(project(":common-core"))
}