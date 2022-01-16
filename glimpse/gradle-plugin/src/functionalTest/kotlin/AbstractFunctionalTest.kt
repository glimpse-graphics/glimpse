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
import java.io.File

abstract class AbstractFunctionalTest {

    protected val projectDir: File = File("build/functionalTest")

    protected val srcDir by lazy { File(projectDir, "src/main/obj") }
    protected val srcSubDir by lazy { File(srcDir, "subdir") }
    protected val resDir = File(projectDir, "src/main/resources")
    protected val testSrcDir by lazy { File(projectDir, "src/test/obj") }
    protected val testSrcSubDir by lazy { File(testSrcDir, "subdir") }
    protected val testResDir = File(projectDir, "src/test/resources")
    protected val assetsDir by lazy { File(projectDir, "src/main/assets") }
    protected val buildDir by lazy { File(projectDir, "build") }

    @After
    fun deleteDirectories() {
        deleteDirectory(projectDir)
    }

    protected fun generateMeshData() {
        GradleRunner.create()
            .forwardOutput()
            .withPluginClasspath()
            .withArguments("generateMeshData")
            .withProjectDir(projectDir)
            .build()
    }

    protected fun generateTestMeshData() {
        GradleRunner.create()
            .forwardOutput()
            .withPluginClasspath()
            .withArguments("generateTestMeshData")
            .withProjectDir(projectDir)
            .build()
    }

    protected fun processResources() {
        GradleRunner.create()
            .forwardOutput()
            .withPluginClasspath()
            .withArguments("processResources")
            .withProjectDir(projectDir)
            .build()
    }

    protected fun processTestResources() {
        GradleRunner.create()
            .forwardOutput()
            .withPluginClasspath()
            .withArguments("processTestResources")
            .withProjectDir(projectDir)
            .build()
    }

    protected fun mergeAssets() {
        GradleRunner.create()
            .forwardOutput()
            .withPluginClasspath()
            .withArguments("mergeDebugAssets")
            .withProjectDir(projectDir)
            .build()
        GradleRunner.create()
            .forwardOutput()
            .withPluginClasspath()
            .withArguments("mergeReleaseAssets")
            .withProjectDir(projectDir)
            .build()
    }

    protected fun assertFileContents(expectedBytes: ByteArray, directory: File = buildDir, path: String) {
        assertArrayEquals(
            "File <${directory.path}/$path> contents",
            expectedBytes,
            File(directory, path).readBytes()
        )
    }
}
