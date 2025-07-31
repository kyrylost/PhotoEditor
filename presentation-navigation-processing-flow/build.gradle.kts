plugins {
    alias(libs.plugins.photoeditor.android.presentation)
}

android {
    namespace = "dev.stukalo.presentation.navigation.processing.flow"
}

dependencies {
    implementation(project(":presentation-core-navigation"))
    implementation(project(":presentation-feature-compressor"))
    implementation(project(":presentation-feature-compare"))
    implementation(project(":presentation-feature-logs"))
}