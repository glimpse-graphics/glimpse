/*
 * Copyright 2020 Slawomir Czerwinski
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

package graphics.glimpse

import com.jogamp.opengl.GL2ES2
import graphics.glimpse.buffers.BufferType
import graphics.glimpse.buffers.BufferUsage
import graphics.glimpse.buffers.FloatBufferData
import graphics.glimpse.buffers.IntBufferData
import graphics.glimpse.logging.GlimpseLogger
import graphics.glimpse.shaders.ShaderType
import graphics.glimpse.textures.TextureMagFilter
import graphics.glimpse.textures.TextureMinFilter
import graphics.glimpse.textures.TextureType
import graphics.glimpse.textures.TextureWrap
import graphics.glimpse.types.Vec3
import graphics.glimpse.types.Vec4

/**
 * Glimpse OpenGL adapter for the given [GL ES 2.0][gles].
 */
@Suppress("TooManyFunctions")
actual class GlimpseAdapter(internal val gles: GL2ES2) {

    /**
     * Glimpse logger.
     */
    actual val logger: GlimpseLogger = GlimpseLogger.create(this)

    /**
     * Returns a boolean value for the given integer [value].
     */
    actual fun booleanOf(value: Int): Boolean = value != GL2ES2.GL_FALSE

    /**
     * Sets clear values for color buffers to [color] with alpha channel set to fully opaque.
     */
    actual fun glClearColor(color: Vec3) {
        gles.glClearColor(color.r, color.g, color.b, 1f)
    }

    /**
     * Sets clear values for color buffers to [color].
     */
    actual fun glClearColor(color: Vec4) {
        gles.glClearColor(color.r, color.g, color.b, color.a)
    }

    /**
     * Sets clear value for the depth buffer to [depth].
     */
    actual fun glClearDepth(depth: Float) {
        gles.glClearDepthf(depth)
    }

    /**
     * Sets the given [depthTestFunction].
     */
    actual fun glDepthTest(depthTestFunction: DepthTestFunction) {
        if (depthTestFunction.isDepthTestEnabled) {
            gles.glEnable(GL2ES2.GL_DEPTH_TEST)
        } else {
            gles.glDisable(GL2ES2.GL_DEPTH_TEST)
        }
        depthTestFunction.toInt()?.let { depthFunc -> gles.glDepthFunc(depthFunc) }
    }

    private fun DepthTestFunction.toInt(): Int? = when (this) {
        DepthTestFunction.DISABLED -> null
        DepthTestFunction.NEVER -> GL2ES2.GL_NEVER
        DepthTestFunction.LESS -> GL2ES2.GL_LESS
        DepthTestFunction.EQUAL -> GL2ES2.GL_EQUAL
        DepthTestFunction.LESS_OR_EQUAL -> GL2ES2.GL_LEQUAL
        DepthTestFunction.GREATER -> GL2ES2.GL_GREATER
        DepthTestFunction.NOT_EQUAL -> GL2ES2.GL_NOTEQUAL
        DepthTestFunction.GREATER_OR_EQUAL -> GL2ES2.GL_GEQUAL
        DepthTestFunction.ALWAYS -> GL2ES2.GL_ALWAYS
    }

    /**
     * Sets facets to be culled to [faceCullingMode].
     */
    actual fun glCullFace(faceCullingMode: FaceCullingMode) {
        if (faceCullingMode.isFaceCullingEnabled) {
            gles.glEnable(GL2ES2.GL_CULL_FACE)
        } else {
            gles.glDisable(GL2ES2.GL_CULL_FACE)
        }
        faceCullingMode.toInt()?.let { depthFunc -> gles.glDepthFunc(depthFunc) }
    }

    private fun FaceCullingMode.toInt(): Int? = when (this) {
        FaceCullingMode.DISABLED -> null
        FaceCullingMode.FRONT -> GL2ES2.GL_FRONT
        FaceCullingMode.BACK -> GL2ES2.GL_BACK
        FaceCullingMode.FRONT_AND_BACK -> GL2ES2.GL_FRONT_AND_BACK
    }

    /**
     * Sets the viewport to have its bottom-left corner at ([x], [y]),
     * and a given [width] and [height].
     */
    actual fun glViewport(x: Int, y: Int, width: Int, height: Int) {
        gles.glViewport(x, y, width, height)
    }

    /**
     * Clears given buffers to the predefined clear values.
     */
    actual fun glClear(vararg buffers: ClearableBufferType) {
        if (buffers.isEmpty()) return
        gles.glClear(
            buffers.map { clearTarget -> clearTarget.toInt() }
                .reduce { a, b -> a or b }
        )
    }

    private fun ClearableBufferType.toInt(): Int = when (this) {
        ClearableBufferType.COLOR_BUFFER -> GL2ES2.GL_COLOR_BUFFER_BIT
        ClearableBufferType.DEPTH_BUFFER -> GL2ES2.GL_DEPTH_BUFFER_BIT
        ClearableBufferType.STENCIL_BUFFER -> GL2ES2.GL_STENCIL_BUFFER_BIT
    }

    /**
     * Generates buffer handles and writes them to a given [bufferHandles] array.
     *
     * The number of generated buffer handles is equal to the size of the given
     * [bufferHandles] array.
     */
    actual fun glGenBuffers(bufferHandles: IntArray) {
        gles.glGenBuffers(bufferHandles.size, bufferHandles, 0)
    }

    /**
     * Binds a given [bufferHandle] to a given buffer [type].
     */
    actual fun glBindBuffer(type: BufferType, bufferHandle: Int) {
        gles.glBindBuffer(type.toInt(), bufferHandle)
    }

    private fun BufferType.toInt(): Int = when (this) {
        BufferType.ARRAY_BUFFER -> GL2ES2.GL_ARRAY_BUFFER
        BufferType.ELEMENT_ARRAY_BUFFER -> GL2ES2.GL_ELEMENT_ARRAY_BUFFER
    }

    /**
     * Creates a buffer of integer values and fills it with data.
     */
    actual fun glBufferData(type: BufferType, data: IntBufferData, usage: BufferUsage) {
        gles.glBufferData(
            type.toInt(),
            data.sizeInBytes.toLong(),
            data.nioBuffer,
            usage.toInt()
        )
    }

    private fun BufferUsage.toInt(): Int = when (this) {
        BufferUsage.STREAM_DRAW -> GL2ES2.GL_STREAM_DRAW
        BufferUsage.STATIC_DRAW -> GL2ES2.GL_STATIC_DRAW
        BufferUsage.DYNAMIC_DRAW -> GL2ES2.GL_DYNAMIC_DRAW
    }

    /**
     * Creates a buffer of floating point values and fills it with data.
     */
    actual fun glBufferData(type: BufferType, data: FloatBufferData, usage: BufferUsage) {
        gles.glBufferData(
            type.toInt(),
            data.sizeInBytes.toLong(),
            data.nioBuffer,
            usage.toInt()
        )
    }

    /**
     * Deletes buffers represented by given [bufferHandles].
     */
    actual fun glDeleteBuffers(bufferHandles: IntArray) {
        gles.glDeleteBuffers(bufferHandles.size, bufferHandles, 0)
    }

    /**
     * Generates texture handles and writes them to a given [textureHandles] array.
     *
     * The number of generated texture handles is equal to the the size of the given
     * [textureHandles] array.
     */
    actual fun glGenTextures(textureHandles: IntArray) {
        gles.glGenTextures(textureHandles.size, textureHandles, 0)
    }

    /**
     * Binds a given [textureHandle] to a given texture [type].
     */
    actual fun glBindTexture(type: TextureType, textureHandle: Int) {
        gles.glBindTexture(type.toInt(), textureHandle)
    }

    private fun TextureType.toInt(): Int = when (this) {
        TextureType.TEXTURE_2D -> GL2ES2.GL_TEXTURE_2D
        TextureType.TEXTURE_CUBE_MAP -> GL2ES2.GL_TEXTURE_CUBE_MAP
    }

    /**
     * Gets maximum size of a texture of a given [type].
     */
    actual fun glMaxTextureSize(type: TextureType): Int = when (type) {
        TextureType.TEXTURE_2D -> glGetInteger(GL2ES2.GL_MAX_TEXTURE_SIZE)
        TextureType.TEXTURE_CUBE_MAP -> glGetInteger(GL2ES2.GL_MAX_CUBE_MAP_TEXTURE_SIZE)
    }

    private fun glGetInteger(parameter: Int): Int {
        val result = IntArray(size = 1)
        gles.glGetIntegerv(parameter, result, 0)
        return result.first()
    }

    /**
     * Generates mipmaps for the currently selected texture of a given [type].
     */
    actual fun glGenerateMipmap(type: TextureType) {
        gles.glGenerateMipmap(type.toInt())
    }

    /**
     * Sets texture [minifying][minFilter] and [magnifying][magFilter] filters for the currently
     * selected texture of a given [type].
     */
    actual fun glTexParameterFilter(
        type: TextureType,
        minFilter: TextureMinFilter,
        magFilter: TextureMagFilter
    ) {
        gles.glTexParameteri(type.toInt(), GL2ES2.GL_TEXTURE_MIN_FILTER, minFilter.toInt())
        gles.glTexParameteri(type.toInt(), GL2ES2.GL_TEXTURE_MAG_FILTER, magFilter.toInt())
    }

    private fun TextureMinFilter.toInt(): Int = when (this) {
        TextureMinFilter.NEAREST -> GL2ES2.GL_NEAREST
        TextureMinFilter.LINEAR -> GL2ES2.GL_LINEAR
        TextureMinFilter.NEAREST_MIPMAP_NEAREST -> GL2ES2.GL_NEAREST_MIPMAP_NEAREST
        TextureMinFilter.LINEAR_MIPMAP_NEAREST -> GL2ES2.GL_LINEAR_MIPMAP_NEAREST
        TextureMinFilter.NEAREST_MIPMAP_LINEAR -> GL2ES2.GL_NEAREST_MIPMAP_LINEAR
        TextureMinFilter.LINEAR_MIPMAP_LINEAR -> GL2ES2.GL_LINEAR_MIPMAP_LINEAR
    }

    private fun TextureMagFilter.toInt(): Int = when (this) {
        TextureMagFilter.NEAREST -> GL2ES2.GL_NEAREST
        TextureMagFilter.LINEAR -> GL2ES2.GL_LINEAR
    }

    /**
     * Sets wrap parameter for texture coordinates [S][wrapS] and [T][wrapT] for the currently
     * selected texture of a given [type].
     */
    actual fun glTexParameterWrap(
        type: TextureType,
        wrapS: TextureWrap,
        wrapT: TextureWrap
    ) {
        gles.glTexParameteri(type.toInt(), GL2ES2.GL_TEXTURE_WRAP_S, wrapS.toInt())
        gles.glTexParameteri(type.toInt(), GL2ES2.GL_TEXTURE_WRAP_T, wrapT.toInt())
    }

    private fun TextureWrap.toInt(): Int = when (this) {
        TextureWrap.CLAMP_TO_EDGE -> GL2ES2.GL_CLAMP_TO_EDGE
        TextureWrap.REPEAT -> GL2ES2.GL_REPEAT
        TextureWrap.MIRRORED_REPEAT -> GL2ES2.GL_MIRRORED_REPEAT
    }

    /**
     * Deletes textures represented by given [textureHandles].
     */
    actual fun glDeleteTextures(textureHandles: IntArray) {
        gles.glDeleteTextures(textureHandles.size, textureHandles, 0)
    }

    /**
     * Selects active [textureIndex] starting with 0.
     */
    actual fun glActiveTexture(textureIndex: Int) {
        gles.glActiveTexture(GL2ES2.GL_TEXTURE0 + textureIndex)
    }

    /**
     * Returns a range of indices supported by [glActiveTexture].
     */
    actual fun glTextureIndices(): IntRange =
        0 until glGetInteger(GL2ES2.GL_MAX_COMBINED_TEXTURE_IMAGE_UNITS)

    /**
     * Creates a shader of a given [shaderType] and returns its handle.
     */
    actual fun glCreateShader(shaderType: ShaderType): Int =
        gles.glCreateShader(shaderType.toInt())

    private fun ShaderType.toInt(): Int = when (this) {
        ShaderType.VERTEX_SHADER -> GL2ES2.GL_VERTEX_SHADER
        ShaderType.FRAGMENT_SHADER -> GL2ES2.GL_FRAGMENT_SHADER
    }

    /**
     * Sets [source] of the shader identified by a given [shaderHandle].
     */
    actual fun glShaderSource(shaderHandle: Int, source: String) {
        val sources = arrayOf(source)
        gles.glShaderSource(shaderHandle, 1, sources, sources.map { it.length }.toIntArray(), 0)
    }

    /**
     * Compiles a shader identified by a given [shaderHandle].
     */
    actual fun glCompileShader(shaderHandle: Int) {
        gles.glCompileShader(shaderHandle)
    }

    /**
     * Returns `true` if shader identified by a given [shaderHandle] has been successfully compiled.
     */
    actual fun glGetShaderCompileStatus(shaderHandle: Int): Boolean {
        val output = IntArray(size = 1)
        gles.glGetShaderiv(shaderHandle, GL2ES2.GL_COMPILE_STATUS, output, 0)
        return booleanOf(output.first())
    }

    /**
     * Returns information log for the shader identified by a given [shaderHandle].
     */
    actual fun glGetShaderInfoLog(shaderHandle: Int): String {
        val logLength = IntArray(size = 1)
        gles.glGetShaderiv(shaderHandle, GL2ES2.GL_INFO_LOG_LENGTH, logLength, 0)
        val log = ByteArray(size = logLength.first())
        gles.glGetShaderInfoLog(shaderHandle, logLength.first(), logLength, 0, log, 0)
        return log.decodeToString()
    }

    /**
     * Deletes a shader identified by a given [shaderHandle].
     */
    actual fun glDeleteShader(shaderHandle: Int) {
        gles.glDeleteShader(shaderHandle)
    }

    /**
     * Returns `true` if shader identified by a given [shaderHandle] has been marked for deletion.
     */
    actual fun glGetShaderDeleteStatus(shaderHandle: Int): Boolean {
        val output = IntArray(size = 1)
        gles.glGetShaderiv(shaderHandle, GL2ES2.GL_DELETE_STATUS, output, 0)
        return booleanOf(output.first())
    }

    /**
     * Creates a program and returns its handle.
     */
    actual fun glCreateProgram(): Int =
        gles.glCreateProgram()

    /**
     * Attaches shader identified by a given [shaderHandle] to program identified by
     * a given [programHandle].
     */
    actual fun glAttachShader(programHandle: Int, shaderHandle: Int) {
        gles.glAttachShader(programHandle, shaderHandle)
    }

    /**
     * Links program identified by a given [programHandle].
     */
    actual fun glLinkProgram(programHandle: Int) {
        gles.glLinkProgram(programHandle)
    }

    /**
     * Returns `true` if program identified by a given [programHandle] has been successfully linked.
     */
    actual fun glGetProgramLinkStatus(programHandle: Int): Boolean {
        val output = IntArray(size = 1)
        gles.glGetProgramiv(programHandle, GL2ES2.GL_LINK_STATUS, output, 0)
        return booleanOf(output.first())
    }

    /**
     * Returns information log for the program identified by a given [programHandle].
     */
    actual fun glGetProgramInfoLog(programHandle: Int): String {
        val logLength = IntArray(size = 1)
        gles.glGetProgramiv(programHandle, GL2ES2.GL_INFO_LOG_LENGTH, logLength, 0)
        val log = ByteArray(size = logLength.first())
        gles.glGetProgramInfoLog(programHandle, logLength.first(), logLength, 0, log, 0)
        return log.decodeToString()
    }

    /**
     * Validates program identified by a given [programHandle].
     */
    actual fun glValidateProgram(programHandle: Int) {
        gles.glValidateProgram(programHandle)
    }

    /**
     * Returns `true` if program identified by a given [programHandle] has been successfully
     * validated.
     */
    actual fun glGetProgramValidateStatus(programHandle: Int): Boolean {
        val output = IntArray(size = 1)
        gles.glGetProgramiv(programHandle, GL2ES2.GL_VALIDATE_STATUS, output, 0)
        return booleanOf(output.first())
    }

    /**
     * Uses a program identified by a given [programHandle].
     */
    actual fun glUseProgram(programHandle: Int) {
        gles.glUseProgram(programHandle)
    }

    /**
     * Deletes a program identified by a given [programHandle].
     */
    actual fun glDeleteProgram(programHandle: Int) {
        gles.glDeleteProgram(programHandle)
    }

    /**
     * Returns `true` if program identified by a given [programHandle] has been marked for deletion.
     */
    actual fun glGetProgramDeleteStatus(programHandle: Int): Boolean {
        val output = IntArray(size = 1)
        gles.glGetProgramiv(programHandle, GL2ES2.GL_DELETE_STATUS, output, 0)
        return booleanOf(output.first())
    }
}
