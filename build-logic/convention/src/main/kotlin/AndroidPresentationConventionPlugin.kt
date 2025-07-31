import com.android.build.api.dsl.LibraryExtension
import dev.stukalo.convention.configureAndroidCompose
import dev.stukalo.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidPresentationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("photoeditor.android.library")
                apply("photoeditor.koin")
                apply("kotlinx-serialization")
            }

            extensions.configure<LibraryExtension> {
                configureAndroidCompose(this)
            }

            dependencies {
                add("implementation", project(":presentation-core-ui"))
                add("implementation", project(":presentation-core-localization"))
                add("implementation", project(":presentation-core-navigation"))

                add("implementation", libs.findLibrary("koin.android.compose").get())
                add("implementation", libs.findLibrary("kotlinx.serialization.json").get())
//                add("implementation", libs.findLibrary("androidx.ui.test.manifest").get())
            }
        }
    }
}
