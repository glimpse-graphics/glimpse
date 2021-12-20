/*
 * Copyright 2020-2021 Slawomir Czerwinski
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
 *
 */

import org.gradle.api.Project
import org.gradle.kotlin.dsl.invoke
import org.jetbrains.dokka.gradle.AbstractDokkaLeafTask
import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.dokka.gradle.DokkaTaskPartial

private fun AbstractDokkaLeafTask.setUpDokkaLeafTask(project: Project) {
    moduleName.set("${project.parent?.name}-${project.name}")
    dokkaSourceSets {
        configureEach {
            includes.from(project.files("module.md", "packages.md"))
        }
    }
}

fun DokkaTask.setUpDokkaTask(project: Project) {
    setUpDokkaLeafTask(project)
    outputDirectory.set(project.buildDir.resolve("javadoc"))
}

fun DokkaTaskPartial.setUpDokkaTask(project: Project) {
    setUpDokkaLeafTask(project)
}
