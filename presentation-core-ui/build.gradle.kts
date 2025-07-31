import com.android.build.api.dsl.LibraryExtension
import dev.stukalo.convention.configureAndroidCompose

plugins {
    id(libs.plugins.photoeditor.android.library.get().pluginId)
}

extensions.configure<LibraryExtension> {
    configureAndroidCompose(this)
}

android {
    namespace = "dev.stukalo.presentation.core.ui"
}

dependencies {
    implementation(libs.androidx.appcompat)
}