plugins {
    id(libs.plugins.photoeditor.android.library.get().pluginId)
    id(libs.plugins.koin.plugin.get().pluginId)
}

android {
    namespace = "dev.stukalo.domain.usecase.android"
}
dependencies {
    implementation(project(":presentation-service"))
}

