plugins {
    alias(libs.plugins.photoeditor.android.presentation)
}

android {
    namespace = "dev.stukalo.presentation.feature.logs"
}

dependencies {
    implementation(project(":domain-usecase-android"))
    implementation ("io.github.ehsannarmani:compose-charts:0.1.7")
}