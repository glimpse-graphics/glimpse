/*
 * Copyright 2020-2022 Slawomir Czerwinski
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package graphics.glimpse.gradle

import com.android.build.gradle.BaseExtension
import graphics.glimpse.gradle.internal.SourceSetName
import graphics.glimpse.gradle.tasks.GenerateMeshData
import org.gradle.api.Project
import org.gradle.api.tasks.TaskContainer
import org.gradle.api.tasks.TaskProvider

/**
 * Glimpse Gradle plugin for Android projects.
 */
class GlimpseAndroidGradlePlugin : AbstractGlimpseGradlePlugin() {

    override fun apply(project: Project) {
        (project.extensions.findByName("android") as? BaseExtension)?.let { extension ->
            extension.sourceSets.forEach { androidSourceSet ->
                val sourceSetName = SourceSetName(androidSourceSet.name)

                val generateMeshDataTask = registerGenerateMeshDataTask(project, sourceSetName)

                androidSourceSet.assets.srcDir(generateMeshDataTask.get().destinationDir)

                addDependsOnTask(
                    projectTasks = project.tasks,
                    taskName = "generate${sourceSetName.taskQualifier}Assets",
                    dependsOnTask = generateMeshDataTask
                )
            }
            connectDependingBuildTypes(extension, project.tasks.filterIsInstance<GenerateMeshData>())
        }
    }

    private fun addDependsOnTask(projectTasks: TaskContainer, taskName: String, dependsOnTask: TaskProvider<*>?) {
        projectTasks.findByName(taskName)?.dependsOn(dependsOnTask)
        projectTasks.whenTaskAdded { task ->
            if (task.name == taskName) {
                task.dependsOn(dependsOnTask)
            }
        }
    }

    private fun connectDependingBuildTypes(extension: BaseExtension, tasks: List<GenerateMeshData>) {
        extension.buildTypes.forEach { buildType ->
            connectDependingBuildType(buildType.name, tasks)
        }
    }

    private fun connectDependingBuildType(buildTypeName: String, tasks: List<GenerateMeshData>) {
        val capitalizedBuildTypeName = buildTypeName.replaceFirstChar { it.uppercaseChar() }
        tasks.filter { capitalizedBuildTypeName in it.name }
            .forEach { task ->
                val dependsOnTaskName = task.name.replace(capitalizedBuildTypeName, "")
                val dependsOnTask = tasks.find { it.name == dependsOnTaskName }
                if (dependsOnTask != null) {
                    task.dependsOn(dependsOnTask)
                }
            }
    }
}
