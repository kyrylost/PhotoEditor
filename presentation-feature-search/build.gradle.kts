plugins {
    alias(libs.plugins.photoeditor.android.presentation)
}

android {
    namespace = "dev.stukalo.presentation.feature.search"
}

dependencies {
    implementation(project(":domain-usecase"))
    implementation(project(":common-core"))
    implementation(project(":presentation-core-navigation"))
    implementation(project(":presentation-core-model"))
    implementation(libs.androidx.paging.compose)
    implementation(libs.coil.compose)
    implementation(libs.coil.gif)
}