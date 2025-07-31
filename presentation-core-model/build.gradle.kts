plugins {
    id(libs.plugins.photoeditor.kotlin.library.get().pluginId)
}
dependencies {
    implementation(project(":common-core"))
}
