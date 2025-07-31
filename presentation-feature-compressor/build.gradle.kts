plugins {
    alias(libs.plugins.photoeditor.android.presentation)
}

android {
    namespace = "dev.stukalo.presentation.feature.compressor"
}

dependencies {
    implementation(project(":domain-usecase-android"))
}