plugins {
    id(libs.plugins.photoeditor.android.library.get().pluginId)
    id(libs.plugins.koin.plugin.get().pluginId)
}

android {
    namespace = "dev.stukalo.presentation.service"
}

dependencies {
    implementation(project(":presentation-core-localization"))
}