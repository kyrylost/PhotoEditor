pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "PhotoEditor"
include(":app")

include(":common-core")

include(":presentation-core-ui")

include(":data-network")
include(":data-network:impl")
include(":data-repository")
include(":data-repository:impl")

include(":domain-usecase")
include(":domain-usecase:impl")
include(":domain-usecase-android")
include(":domain-usecase-android:impl")

include(":presentation-core-model")
include(":presentation-feature-host")
include(":presentation-navigation-main-flow")
include(":presentation-navigation-processing-flow")
include(":presentation-feature-search")
include(":presentation-feature-downloads")
include(":presentation-feature-compressor")
include(":presentation-feature-logs")
include(":presentation-feature-compare")
include(":presentation-service")
include(":presentation-core-navigation")
include(":presentation-core-localization")
