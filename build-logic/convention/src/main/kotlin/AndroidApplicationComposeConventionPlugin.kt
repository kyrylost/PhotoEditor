import com.android.build.api.dsl.ApplicationExtension
import dev.stukalo.convention.configureAndroidCompose
import dev.stukalo.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidApplicationComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("photoeditor.application")
            }

            extensions.configure<ApplicationExtension> {
                configureAndroidCompose(this)
            }

            dependencies {
                add("implementation", libs.findLibrary("androidx.ui.test.manifest").get())
            }
        }
    }

}