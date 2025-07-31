import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

group = "dev.stukalo.photoeditor.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_21
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
}

gradlePlugin {
    // register the convention plugin
    plugins {
        register("androidApplication") {
            id = "photoeditor.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }

        register("androidApplicationCompose") {
            id = "photoeditor.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }

        register("androidLibrary") {
            id = "photoeditor.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }

        register("androidData") {
            id = "photoeditor.android.data"
            implementationClass = "AndroidDataConventionPlugin"
        }

        register("androidPresentation") {
            id = "photoeditor.android.presentation"
            implementationClass = "AndroidPresentationConventionPlugin"
        }

        register("kotlinLibrary") {
            id = "photoeditor.kotlin.library"
            implementationClass = "KotlinLibraryConventionPlugin"
        }

        register("kotlinDomain") {
            id = "photoeditor.kotlin.domain"
            implementationClass = "KotlinDomainConventionPlugin"
        }

        register("koinPlugin") {
            id = "photoeditor.koin"
            implementationClass = "KoinPlugin"
        }
    }
}