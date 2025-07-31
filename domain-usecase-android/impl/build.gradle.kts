plugins {
    id(libs.plugins.photoeditor.android.library.get().pluginId)
    id(libs.plugins.koin.plugin.get().pluginId)
}

android {
    namespace = "dev.stukalo.domain.usecase.android.impl"
}

dependencies {
    implementation(project(":presentation-core-localization"))
    implementation(project(":presentation-service"))
    implementation(project(":domain-usecase-android"))
}
