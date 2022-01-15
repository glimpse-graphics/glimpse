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

import graphics.glimpse.gradle.tasks.GenerateMeshData
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.AbstractCopyTask
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension

/**
 * Glimpse Gradle plugin for JVM projects.
 */
class GlimpseJvmGradlePlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.kotlinExtension.sourceSets.forEach { kotlinSourceSet ->
            val sourceSetName = kotlinSourceSet.name
            val capitalizedSourceSetName = sourceSetName.replaceFirstChar { it.uppercaseChar() }
            val taskGroup = if (sourceSetName == "main") "" else capitalizedSourceSetName

            val taskName = "generate${taskGroup}MeshData"
            val processResourcesTaskName = "process${taskGroup}Resources"
            val srcDir = project.projectDir.resolve(relative = "src/$sourceSetName/obj")
            val dstDir = project.buildDir.resolve(relative = "generated/glimpseResources/$sourceSetName")

            kotlinSourceSet.resources.srcDir(dstDir)

            val generateMeshDataTask = project.tasks.register(taskName, GenerateMeshData::class.java) {
                it.sourceDir.set(srcDir)
                it.destinationDir.set(dstDir)
            }

            val processResourcesTask = project.tasks.findByName(processResourcesTaskName)

            if (processResourcesTask != null) {
                processResourcesTask.dependsOn(generateMeshDataTask)
                (processResourcesTask as? AbstractCopyTask)?.from(dstDir)
            } else {
                project.logger.warn(
                    "Task ${project.path}:$processResourcesTaskName not found. Mesh data will not be generated."
                )
            }
        }
    }
}
