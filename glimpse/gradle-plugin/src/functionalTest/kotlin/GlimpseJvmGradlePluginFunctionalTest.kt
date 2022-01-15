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

import org.gradle.testkit.runner.GradleRunner
import org.junit.After
import org.junit.Assert.assertArrayEquals
import org.junit.Before
import org.junit.Test
import java.io.File

class GlimpseJvmGradlePluginFunctionalTest {

    private val projectDir = File("build/functionalTest")
    private val srcDir = File(projectDir, "src/main/obj")
    private val resDir = File(projectDir, "src/main/resources")
    private val subdir = File(srcDir, "subdir")
    private val buildDir = File(projectDir, "build")

    @Before
    fun `set up project`() {
        createDirectories(projectDir)
        createDirectories(srcDir)
        createDirectories(subdir)
        createDirectories(resDir)

        writeFile(File(projectDir, "settings.gradle.kts"), EMPTY)
        writeFile(File(projectDir, "build.gradle.kts"), PLUGINS)
        writeFile(File(srcDir, "test.obj"), TRIANGLE.trimIndent())
        writeFile(File(subdir, "test.obj"), TRIANGLE.trimIndent())
        writeFile(File(resDir, "resource.txt"), TEXT)
    }

    @After
    fun `clean project`() {
        deleteDirectory(buildDir)
    }

    @Test
    fun `GIVEN a JVM project, WHEN run generateMeshData task, THEN generate mesh data`() {
        generateMeshData()

        assertArrayEquals(
            expectedMeshDataFileContents,
            File(buildDir, "generated/glimpseResources/main/test.meshdata").readBytes()
        )
        assertArrayEquals(
            expectedMeshDataFileContents,
            File(buildDir, "generated/glimpseResources/main/subdir/test.meshdata").readBytes()
        )
    }

    private fun generateMeshData() {
        GradleRunner.create()
            .forwardOutput()
            .withPluginClasspath()
            .withArguments("generateMeshData")
            .withProjectDir(projectDir)
            .build()
    }

    @Test
    fun `GIVEN a JVM project, WHEN run processResources task, THEN generate and process mesh data`() {
        processResources()

        assertArrayEquals(
            expectedMeshDataFileContents,
            File(buildDir, "generated/glimpseResources/main/test.meshdata").readBytes()
        )
        assertArrayEquals(
            expectedMeshDataFileContents,
            File(buildDir, "generated/glimpseResources/main/subdir/test.meshdata").readBytes()
        )
        assertArrayEquals(
            expectedMeshDataFileContents,
            File(buildDir, "resources/main/test.meshdata").readBytes()
        )
        assertArrayEquals(
            expectedMeshDataFileContents,
            File(buildDir, "resources/main/subdir/test.meshdata").readBytes()
        )
        assertArrayEquals(TEXT.toByteArray(), File(buildDir, "resources/main/resource.txt").readBytes())
    }

    private fun processResources() {
        GradleRunner.create()
            .forwardOutput()
            .withPluginClasspath()
            .withArguments("processResources")
            .withProjectDir(projectDir)
            .build()
    }
}
