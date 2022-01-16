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

class GlimpseJvmGradlePluginTestFunctionalTest : AbstractFunctionalTest() {

    @Before
    fun createProjectStructure() {
        createDirectories(projectDir)
        createDirectories(testSrcDir)
        createDirectories(testSrcSubDir)
        createDirectories(testResDir)

        writeFile(File(projectDir, "settings.gradle.kts"), EMPTY)
        writeFile(File(projectDir, "build.gradle.kts"), BUILD_JVM)
        writeFile(File(testSrcDir, "test.obj"), TRIANGLE.trimIndent())
        writeFile(File(testSrcSubDir, "test.obj"), TRIANGLE.trimIndent())
        writeFile(File(testResDir, "resource.txt"), TEXT)
    }

    @Test
    fun `GIVEN a JVM project, WHEN run generateTestMeshData task, THEN generate mesh data`() {
        generateTestMeshData()

        assertFileContents(expectedMeshDataBytes, path = "generated/glimpseResources/test/test.meshdata")
        assertFileContents(expectedMeshDataBytes, path = "generated/glimpseResources/test/subdir/test.meshdata")
    }

    @Test
    fun `GIVEN a JVM project, WHEN run processTestResources task, THEN generate and process mesh data`() {
        processTestResources()

        assertFileContents(expectedMeshDataBytes, path = "generated/glimpseResources/test/test.meshdata")
        assertFileContents(expectedMeshDataBytes, path = "generated/glimpseResources/test/subdir/test.meshdata")
        assertFileContents(expectedMeshDataBytes, path = "resources/test/test.meshdata")
        assertFileContents(expectedMeshDataBytes, path = "resources/test/subdir/test.meshdata")
        assertFileContents(TEXT.toByteArray(), path = "resources/test/resource.txt")
    }
}
