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

import graphics.glimpse.gradle.internal.SourceSetName
import org.gradle.api.Project
import org.gradle.api.tasks.AbstractCopyTask
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension

/**
 * Glimpse Gradle plugin for JVM projects.
 */
class GlimpseJvmGradlePlugin : AbstractGlimpseGradlePlugin() {

    override fun apply(project: Project) {
        project.kotlinExtension.sourceSets.forEach { kotlinSourceSet ->
            val sourceSetName = SourceSetName(kotlinSourceSet.name)

            val generateMeshDataTask = registerGenerateMeshDataTask(project, sourceSetName)

            kotlinSourceSet.resources.srcDir(generateMeshDataTask.get().destinationDir)

            val processResourcesTaskName = "process${sourceSetName.taskQualifier}Resources"
            val processResourcesTask = project.tasks.findByName(processResourcesTaskName)

            if (processResourcesTask != null) {
                processResourcesTask.dependsOn(generateMeshDataTask)
                (processResourcesTask as? AbstractCopyTask)?.from(generateMeshDataTask.get().destinationDir)
            }
        }
    }
}
