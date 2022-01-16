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

package graphics.glimpse.gradle.tasks

import graphics.glimpse.gradle.obj.ObjFile
import graphics.glimpse.gradle.obj.ObjFileVisitor
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.SkipWhenEmpty
import org.gradle.api.tasks.TaskAction
import java.io.DataOutputStream
import java.io.File

/**
 * Task generating mesh data files from Wavefront OBJ files.
 */
abstract class GenerateMeshData : DefaultTask() {

    /**
     * Source directory containing Wavefront OBJ files.
     */
    @get:InputDirectory
    @get:SkipWhenEmpty
    abstract val sourceDir: DirectoryProperty

    /**
     * Destination directory for mesh data files.
     */
    @get:OutputDirectory
    abstract val destinationDir: DirectoryProperty

    /**
     * Generates mesh data files.
     */
    @TaskAction
    fun generateMeshDataFiles() {
        val srcDir = sourceDir.asFile.get()
        val dstDir = destinationDir.asFile.get()
        val allSources = sourceDir.asFileTree
        val objFiles = allSources.matching { filterable -> filterable.include("**/*.obj") }

        objFiles.forEach { file ->
            val outputFilePath = file.relativeTo(srcDir).path.replaceAfterLast(EXT_DELIMITER, MESH_DATA_FILE_EXTENSION)
            val outputFile = File(dstDir, outputFilePath)

            outputFile.parentFile.mkdirs()

            val objFile = ObjFile(file)
            val visitor = GenerateMeshDataObjFileVisitor(outputFile)

            objFile.accept(visitor)
        }
    }

    private class GenerateMeshDataObjFileVisitor(private val outputFile: File) : ObjFileVisitor() {

        private val vertexPositions = mutableListOf<List<Float>>()
        private val textureCoordinates = mutableListOf<List<Float>>()
        private val vertexNormals = mutableListOf<List<Float>>()

        private val triangles = mutableListOf<Triangle>()

        override fun visitVertexPosition(coordinates: List<Float>) {
            require(coordinates.size == POSITION_SIZE)
            vertexPositions.add(coordinates)
        }

        override fun visitTextureCoordinates(coordinates: List<Float>) {
            require(coordinates.size == TEX_COORDS_SIZE)
            textureCoordinates.add(coordinates)
        }

        override fun visitVertexNormal(coordinates: List<Float>) {
            require(coordinates.size == NORMAL_SIZE)
            vertexNormals.add(coordinates)
        }

        override fun visitTriangle(indices: List<Triple<Int?, Int?, Int?>>) {
            require(indices.size == VERTICES_PER_TRIANGLE)
            val (v1, v2, v3) = indices.map { it.toCoordinates() }
            triangles.add(Triangle(v1, v2, v3))
        }

        private fun Triple<Int?, Int?, Int?>.toCoordinates(): Vertex =
            Vertex(
                position = first?.let { vertexPositions[it - 1] } ?: listOf(0f, 0f, 0f),
                texCoords = second?.let { textureCoordinates[it - 1] } ?: listOf(0f, 0f),
                normal = third?.let { vertexNormals[it - 1] } ?: listOf(0f, 0f, 0f)
            )

        override fun afterLast() {
            outputFile.outputStream().use { outputStream ->
                val out = DataOutputStream(outputStream)
                out.writeInt(triangles.size * VERTICES_PER_TRIANGLE)
                triangles.flatMap { it.v1.position + it.v2.position + it.v3.position }
                    .forEach { out.writeFloat(it) }
                triangles.flatMap { it.v1.texCoords + it.v2.texCoords + it.v3.texCoords }
                    .forEach { out.writeFloat(it) }
                triangles.flatMap { it.v1.normal + it.v2.normal + it.v3.normal }
                    .forEach { out.writeFloat(it) }
                triangles.flatMap { it.tangent + it.tangent + it.tangent }
                    .forEach { out.writeFloat(it) }
                triangles.flatMap { it.bitangent + it.bitangent + it.bitangent }
                    .forEach { out.writeFloat(it) }
                out.flush()
            }
        }

        private data class Vertex(val position: List<Float>, val texCoords: List<Float>, val normal: List<Float>)

        private data class Triangle(val v1: Vertex, val v2: Vertex, val v3: Vertex) {

            val tangent: List<Float>
            val bitangent: List<Float>

            init {
                val edge1 = (v2.position zip v1.position).map { (a, b) -> a - b }
                val edge2 = (v3.position zip v1.position).map { (a, b) -> a - b }

                val deltaUV1 = (v2.texCoords zip v1.texCoords).map { (a, b) -> a - b }
                val deltaUV2 = (v3.texCoords zip v1.texCoords).map { (a, b) -> a - b }

                val factor = 1f / (deltaUV1[X] * deltaUV2[Y] - deltaUV2[X] * deltaUV1[Y])

                tangent = (edge1 zip edge2).map { (a, b) -> (a * deltaUV2[Y] - b * deltaUV1[Y]) * factor }
                bitangent = (edge2 zip edge1).map { (a, b) -> (a * deltaUV1[X] - b * deltaUV2[X]) * factor }
            }
        }

        companion object {
            private const val X = 0
            private const val Y = 1

            private const val POSITION_SIZE = 3
            private const val TEX_COORDS_SIZE = 2
            private const val NORMAL_SIZE = 3

            private const val VERTICES_PER_TRIANGLE = 3
        }
    }

    companion object {
        private const val EXT_DELIMITER = '.'
        private const val MESH_DATA_FILE_EXTENSION = "meshdata"
    }
}
