plugins {
    alias(libs.plugins.photoeditor.android.presentation)
}

android {
    namespace = "dev.stukalo.presentation.navigation.main.flow"
}

dependencies {
    implementation(project(":presentation-core-navigation"))
    implementation(project(":presentation-feature-search"))
    implementation(project(":presentation-feature-downloads"))
}