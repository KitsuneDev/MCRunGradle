package me.gabrieltk.mcrun

import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.TaskAction
import java.io.InputStream
import java.net.URL
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import kotlin.reflect.KProperty


open class MCRunPluginExtension(project: Project) {
    var pluginFiles: List<String> = listOf()

    var mcVersion: String? = "1.15.2"
    var paperBuild: String? = "latest"


    //var attributes by GradleProperty(project, MutableMap::class.java, mutableMapOf<String, String>())

    //fun attribute(attribute: String, value: String) {
    //    val map = attributes as? MutableMap<String, String>
    //    map?.put(attribute, value)
    //}


}

class MCRunPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.extensions.create(
            "runMinecraft",
            MCRunPluginExtension::class.java,
            project)

        project.tasks.create<RunPaperTask>("paperPrepare", RunPaperTask::class.java).run {
            // Set description and group for the task
            description = "Prepares a Paper Server with the specified plugins"
            group = "run"
        }
    }

}
internal fun Project.getConfig(): MCRunPluginExtension =
    extensions.getByName("mcConfig") as? MCRunPluginExtension
        ?: throw IllegalStateException("mcConfig is not of the correct type")

open class RunPaperTask: DefaultTask() {
    @TaskAction
    fun preparePaper() {
        if(project.getConfig().pluginFiles.isEmpty()) println("[!!] No pluginFiles defined at mcRun Configuration.");
        val paperRoot = project.file("\$buildDir/paper/")
            paperRoot.mkdirs()
        val paper = project.file("\$buildDir/paper/paper.jar")
        if(!paper.exists()) {
            println("Setting Up Paper...")
            val inputStream: InputStream = URL("https://papermc.io/api/v1/paper/${project.getConfig().mcVersion}/${project.getConfig().paperBuild}/download").openStream()
            Files.copy(
                inputStream,
                paper.toPath(),
                StandardCopyOption.REPLACE_EXISTING
            )
        }
        val plugins = project.file("\$buildDir/paper/plugins/")
            plugins.mkdirs()
        for(plugin in (project).getConfig().pluginFiles){
            var pluginFile = project.file(plugin)
            pluginFile.copyTo(plugins, true)
        }
    }
}

/*nternal class GradleProperty<T, V>(
    project: Project,
    type: Class<V>,
    default: V? = null
) {
    val property = project.objects.property(type).apply {
        set(default)
    }

    operator fun getValue(thisRef: T, property: KProperty<*>): V =
        this.property.get()

    operator fun setValue(thisRef: T, property: KProperty<*>, value: V) =
        this.property.set(value)
}
internal class GradleIntProperty<T>(
    project: Project,
    default: Int? = null
) {
    val property = project.objects.property(Integer::class.java).apply {
        set(default as? Integer)
    }

    operator fun getValue(thisRef: T, property: KProperty<*>): Int =
        this.property.get().toInt()

    operator fun setValue(thisRef: T, property: KProperty<*>, value: Int) =
        this.property.set(value as? Integer)
}*/