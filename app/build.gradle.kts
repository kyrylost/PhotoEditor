plugins {
    alias(libs.plugins.photoeditor.application.compose)
}

android {
    namespace = "dev.stukalo.photoeditor"

    defaultConfig {
        applicationId = "dev.stukalo.photoeditor"
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    implementation(project(":common-core"))

    implementation(project(":data-network"))
    implementation(project(":data-network:impl"))
    implementation(project(":data-repository"))
    implementation(project(":data-repository:impl"))

    implementation(project(":domain-usecase"))
    implementation(project(":domain-usecase:impl"))
    implementation(project(":domain-usecase-android"))
    implementation(project(":domain-usecase-android:impl"))

    implementation(project(":presentation-core-model"))
    implementation(project(":presentation-core-localization"))
    implementation(project(":presentation-core-navigation"))
    implementation(project(":presentation-core-ui"))
    implementation(project(":presentation-feature-search"))
    implementation(project(":presentation-feature-downloads"))
    implementation(project(":presentation-feature-compressor"))
    implementation(project(":presentation-feature-logs"))
    implementation(project(":presentation-feature-compare"))
    implementation(project(":presentation-feature-host"))
    implementation(project(":presentation-navigation-main-flow"))
    implementation(project(":presentation-navigation-processing-flow"))
    implementation(project(":presentation-service"))
}