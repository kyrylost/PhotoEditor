plugins {
    alias(libs.plugins.photoeditor.android.presentation)
}

android {
    namespace = "dev.stukalo.presentation.feature.host"
}

dependencies {
    implementation(project(":presentation-core-navigation"))
    implementation(project(":presentation-navigation-main-flow"))
    implementation(project(":presentation-navigation-processing-flow"))
}