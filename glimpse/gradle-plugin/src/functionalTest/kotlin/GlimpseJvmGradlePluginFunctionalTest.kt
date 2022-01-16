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

import org.junit.Before
import org.junit.Test
import java.io.File

class GlimpseJvmGradlePluginFunctionalTest : AbstractFunctionalTest() {

    @Before
    fun createProjectStructure() {
        createDirectories(projectDir)
        createDirectories(srcDir)
        createDirectories(srcSubDir)
        createDirectories(resDir)

        writeFile(File(projectDir, "settings.gradle.kts"), EMPTY)
        writeFile(File(projectDir, "build.gradle.kts"), BUILD_JVM)
        writeFile(File(srcDir, "test.obj"), TRIANGLE.trimIndent())
        writeFile(File(srcSubDir, "test.obj"), TRIANGLE.trimIndent())
        writeFile(File(resDir, "resource.txt"), TEXT)
    }

    @Test
    fun `GIVEN a JVM project, WHEN run generateMeshData task, THEN generate mesh data`() {
        generateMeshData()

        assertFileContents(expectedMeshDataBytes, path = "generated/glimpseResources/main/test.meshdata")
        assertFileContents(expectedMeshDataBytes, path = "generated/glimpseResources/main/subdir/test.meshdata")
    }

    @Test
    fun `GIVEN a JVM project, WHEN run processResources task, THEN generate and process mesh data`() {
        processResources()

        assertFileContents(expectedMeshDataBytes, path = "generated/glimpseResources/main/test.meshdata")
        assertFileContents(expectedMeshDataBytes, path = "generated/glimpseResources/main/subdir/test.meshdata")
        assertFileContents(expectedMeshDataBytes, path = "resources/main/test.meshdata")
        assertFileContents(expectedMeshDataBytes, path = "resources/main/subdir/test.meshdata")
        assertFileContents(TEXT.toByteArray(), path = "resources/main/resource.txt")
    }
}
