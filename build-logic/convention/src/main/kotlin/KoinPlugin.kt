import dev.stukalo.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class KoinPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            dependencies {
                add("implementation", libs.findLibrary("koin.core").get())
            }

            pluginManager.withPlugin("com.android.base") {
                dependencies {
                    add("implementation", libs.findLibrary("koin.android").get())
                }
            }
        }
    }

}