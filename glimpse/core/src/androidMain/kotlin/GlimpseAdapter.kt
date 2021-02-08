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

import android.opengl.GLES20
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
import graphics.glimpse.types.Mat2
import graphics.glimpse.types.Mat3
import graphics.glimpse.types.Mat4
import graphics.glimpse.types.Vec2
import graphics.glimpse.types.Vec3
import graphics.glimpse.types.Vec4
import java.nio.ByteBuffer

/**
 * Glimpse OpenGL adapter.
 */
@Suppress("TooManyFunctions")
actual class GlimpseAdapter {

    private val logger: GlimpseLogger = GlimpseLogger.create(this)

    /**
     * Returns a boolean value for the given integer [value].
     */
    actual fun booleanOf(value: Int): Boolean = value != GLES20.GL_FALSE

    /**
     * Sets clear values for color buffers to [color] with alpha channel set to fully opaque.
     */
    actual fun glClearColor(color: Vec3) {
        logger.debug(message = "Setting clear color: $color")
        GLES20.glClearColor(color.r, color.g, color.b, 1f)
    }

    /**
     * Sets clear values for color buffers to [color].
     */
    actual fun glClearColor(color: Vec4) {
        logger.debug(message = "Setting clear color: $color")
        GLES20.glClearColor(color.r, color.g, color.b, color.a)
    }

    /**
     * Sets clear value for the depth buffer to [depth].
     */
    actual fun glClearDepth(depth: Float) {
        logger.debug(message = "Setting clear depth: $depth")
        GLES20.glClearDepthf(depth)
    }

    /**
     * Sets the given [depthTestFunction].
     */
    actual fun glDepthTest(depthTestFunction: DepthTestFunction) {
        logger.debug(message = "Setting depth test function: $depthTestFunction")
        if (depthTestFunction.isDepthTestEnabled) {
            GLES20.glEnable(GLES20.GL_DEPTH_TEST)
        } else {
            GLES20.glDisable(GLES20.GL_DEPTH_TEST)
        }
        depthTestFunction.toInt()?.let { depthFunc -> GLES20.glDepthFunc(depthFunc) }
    }

    private fun DepthTestFunction.toInt(): Int? = when (this) {
        DepthTestFunction.DISABLED -> null
        DepthTestFunction.NEVER -> GLES20.GL_NEVER
        DepthTestFunction.LESS -> GLES20.GL_LESS
        DepthTestFunction.EQUAL -> GLES20.GL_EQUAL
        DepthTestFunction.LESS_OR_EQUAL -> GLES20.GL_LEQUAL
        DepthTestFunction.GREATER -> GLES20.GL_GREATER
        DepthTestFunction.NOT_EQUAL -> GLES20.GL_NOTEQUAL
        DepthTestFunction.GREATER_OR_EQUAL -> GLES20.GL_GEQUAL
        DepthTestFunction.ALWAYS -> GLES20.GL_ALWAYS
    }

    /**
     * Sets facets to be culled to [faceCullingMode].
     */
    actual fun glCullFace(faceCullingMode: FaceCullingMode) {
        logger.debug(message = "Setting face culling mode: $faceCullingMode")
        if (faceCullingMode.isFaceCullingEnabled) {
            GLES20.glEnable(GLES20.GL_CULL_FACE)
        } else {
            GLES20.glDisable(GLES20.GL_CULL_FACE)
        }
        faceCullingMode.toInt()?.let { depthFunc -> GLES20.glDepthFunc(depthFunc) }
    }

    private fun FaceCullingMode.toInt(): Int? = when (this) {
        FaceCullingMode.DISABLED -> null
        FaceCullingMode.FRONT -> GLES20.GL_FRONT
        FaceCullingMode.BACK -> GLES20.GL_BACK
        FaceCullingMode.FRONT_AND_BACK -> GLES20.GL_FRONT_AND_BACK
    }

    /**
     * Enables blending.
     */
    actual fun glEnableBlending() {
        GLES20.glEnable(GLES20.GL_BLEND)
    }

    /**
     * Disables blending.
     */
    actual fun glDisableBlending() {
        GLES20.glDisable(GLES20.GL_BLEND)
    }

    /**
     * Sets functions for computing [source][sourceFactor] and [destination][destinationFactor] factors.
     */
    actual fun glBlendingFunction(
        sourceFactor: BlendingFactorFunction,
        destinationFactor: BlendingFactorFunction
    ) {
        GLES20.glBlendFunc(sourceFactor.toInt(), destinationFactor.toInt())
    }

    @Suppress("ComplexMethod")
    private fun BlendingFactorFunction.toInt(): Int = when (this) {
        BlendingFactorFunction.ZERO -> GLES20.GL_ZERO
        BlendingFactorFunction.ONE -> GLES20.GL_ONE
        BlendingFactorFunction.SOURCE_COLOR -> GLES20.GL_SRC_COLOR
        BlendingFactorFunction.ONE_MINUS_SOURCE_COLOR -> GLES20.GL_ONE_MINUS_SRC_COLOR
        BlendingFactorFunction.DESTINATION_COLOR -> GLES20.GL_DST_COLOR
        BlendingFactorFunction.ONE_MINUS_DESTINATION_COLOR -> GLES20.GL_ONE_MINUS_DST_COLOR
        BlendingFactorFunction.SOURCE_ALPHA -> GLES20.GL_SRC_ALPHA
        BlendingFactorFunction.ONE_MINUS_SOURCE_ALPHA -> GLES20.GL_ONE_MINUS_SRC_ALPHA
        BlendingFactorFunction.DESTINATION_ALPHA -> GLES20.GL_DST_ALPHA
        BlendingFactorFunction.ONE_MINUS_DESTINATION_ALPHA -> GLES20.GL_ONE_MINUS_DST_ALPHA
        BlendingFactorFunction.CONSTANT_COLOR -> GLES20.GL_CONSTANT_COLOR
        BlendingFactorFunction.ONE_MINUS_CONSTANT_COLOR -> GLES20.GL_ONE_MINUS_CONSTANT_COLOR
        BlendingFactorFunction.CONSTANT_ALPHA -> GLES20.GL_CONSTANT_ALPHA
        BlendingFactorFunction.ONE_MINUS_CONSTANT_ALPHA -> GLES20.GL_ONE_MINUS_CONSTANT_ALPHA
    }

    /**
     * Sets blending constant [color] for [BlendingFactorFunction.CONSTANT_COLOR],
     * [BlendingFactorFunction.ONE_MINUS_CONSTANT_COLOR], [BlendingFactorFunction.CONSTANT_ALPHA]
     * and [BlendingFactorFunction.ONE_MINUS_CONSTANT_ALPHA].
     */
    actual fun glBlendingColor(color: Vec4) {
        GLES20.glBlendColor(color.r, color.g, color.b, color.a)
    }

    /**
     * Sets width of lines to a given [lineWidth].
     */
    actual fun glLineWidth(lineWidth: Float) {
        GLES20.glLineWidth(lineWidth)
    }

    /**
     * Sets the viewport to have its bottom-left corner at ([x], [y]),
     * and a given [width] and [height].
     */
    actual fun glViewport(x: Int, y: Int, width: Int, height: Int) {
        logger.debug(message = "Setting viewport: x=$x, y=$y, width=$width, height=$height")
        GLES20.glViewport(x, y, width, height)
    }

    /**
     * Clears given buffers to the predefined clear values.
     */
    actual fun glClear(vararg buffers: ClearableBufferType) {
        if (buffers.isEmpty()) return
        GLES20.glClear(
            buffers.map { clearTarget -> clearTarget.toInt() }
                .reduce { a, b -> a or b }
        )
    }

    private fun ClearableBufferType.toInt(): Int = when (this) {
        ClearableBufferType.COLOR_BUFFER -> GLES20.GL_COLOR_BUFFER_BIT
        ClearableBufferType.DEPTH_BUFFER -> GLES20.GL_DEPTH_BUFFER_BIT
        ClearableBufferType.STENCIL_BUFFER -> GLES20.GL_STENCIL_BUFFER_BIT
    }

    /**
     * Generates buffer handles and writes them to a given [bufferHandles] array.
     *
     * The number of generated buffer handles is equal to the size of the given
     * [bufferHandles] array.
     */
    actual fun glGenBuffers(bufferHandles: IntArray) {
        GLES20.glGenBuffers(bufferHandles.size, bufferHandles, 0)
    }

    /**
     * Binds a given [bufferHandle] to a given buffer [type].
     */
    actual fun glBindBuffer(type: BufferType, bufferHandle: Int) {
        GLES20.glBindBuffer(type.toInt(), bufferHandle)
    }

    private fun BufferType.toInt(): Int = when (this) {
        BufferType.ARRAY_BUFFER -> GLES20.GL_ARRAY_BUFFER
        BufferType.ELEMENT_ARRAY_BUFFER -> GLES20.GL_ELEMENT_ARRAY_BUFFER
    }

    /**
     * Creates a buffer of integer values and fills it with data.
     */
    actual fun glBufferData(type: BufferType, data: IntBufferData, usage: BufferUsage) {
        GLES20.glBufferData(
            type.toInt(),
            data.sizeInBytes,
            data.nioBuffer,
            usage.toInt()
        )
    }

    private fun BufferUsage.toInt(): Int = when (this) {
        BufferUsage.STREAM_DRAW -> GLES20.GL_STREAM_DRAW
        BufferUsage.STATIC_DRAW -> GLES20.GL_STATIC_DRAW
        BufferUsage.DYNAMIC_DRAW -> GLES20.GL_DYNAMIC_DRAW
    }

    /**
     * Creates a buffer of floating point values and fills it with data.
     */
    actual fun glBufferData(type: BufferType, data: FloatBufferData, usage: BufferUsage) {
        GLES20.glBufferData(
            type.toInt(),
            data.sizeInBytes,
            data.nioBuffer,
            usage.toInt()
        )
    }

    /**
     * Deletes buffers represented by given [bufferHandles].
     */
    actual fun glDeleteBuffers(bufferHandles: IntArray) {
        GLES20.glDeleteBuffers(bufferHandles.size, bufferHandles, 0)
    }

    /**
     * Generates texture handles and writes them to a given [textureHandles] array.
     *
     * The number of generated texture handles is equal to the the size of the given
     * [textureHandles] array.
     */
    actual fun glGenTextures(textureHandles: IntArray) {
        GLES20.glGenTextures(textureHandles.size, textureHandles, 0)
    }

    /**
     * Binds a given [textureHandle] to a given texture [type].
     */
    actual fun glBindTexture(type: TextureType, textureHandle: Int) {
        GLES20.glBindTexture(type.toInt(), textureHandle)
    }

    private fun TextureType.toInt(): Int = when (this) {
        TextureType.TEXTURE_2D -> GLES20.GL_TEXTURE_2D
        TextureType.TEXTURE_CUBE_MAP -> GLES20.GL_TEXTURE_CUBE_MAP
    }

    /**
     * Gets maximum size of a texture of a given [type].
     */
    actual fun glMaxTextureSize(type: TextureType): Int = when (type) {
        TextureType.TEXTURE_2D -> glGetInteger(GLES20.GL_MAX_TEXTURE_SIZE)
        TextureType.TEXTURE_CUBE_MAP -> glGetInteger(GLES20.GL_MAX_CUBE_MAP_TEXTURE_SIZE)
    }

    private fun glGetInteger(parameter: Int): Int {
        val result = IntArray(size = 1)
        GLES20.glGetIntegerv(parameter, result, 0)
        return result.first()
    }

    /**
     * Generates mipmaps for the currently selected texture of a given [type].
     */
    actual fun glGenerateMipmap(type: TextureType) {
        GLES20.glGenerateMipmap(type.toInt())
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
        GLES20.glTexParameteri(type.toInt(), GLES20.GL_TEXTURE_MIN_FILTER, minFilter.toInt())
        GLES20.glTexParameteri(type.toInt(), GLES20.GL_TEXTURE_MAG_FILTER, magFilter.toInt())
    }

    private fun TextureMinFilter.toInt(): Int = when (this) {
        TextureMinFilter.NEAREST -> GLES20.GL_NEAREST
        TextureMinFilter.LINEAR -> GLES20.GL_LINEAR
        TextureMinFilter.NEAREST_MIPMAP_NEAREST -> GLES20.GL_NEAREST_MIPMAP_NEAREST
        TextureMinFilter.LINEAR_MIPMAP_NEAREST -> GLES20.GL_LINEAR_MIPMAP_NEAREST
        TextureMinFilter.NEAREST_MIPMAP_LINEAR -> GLES20.GL_NEAREST_MIPMAP_LINEAR
        TextureMinFilter.LINEAR_MIPMAP_LINEAR -> GLES20.GL_LINEAR_MIPMAP_LINEAR
    }

    private fun TextureMagFilter.toInt(): Int = when (this) {
        TextureMagFilter.NEAREST -> GLES20.GL_NEAREST
        TextureMagFilter.LINEAR -> GLES20.GL_LINEAR
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
        GLES20.glTexParameteri(type.toInt(), GLES20.GL_TEXTURE_WRAP_S, wrapS.toInt())
        GLES20.glTexParameteri(type.toInt(), GLES20.GL_TEXTURE_WRAP_T, wrapT.toInt())
    }

    private fun TextureWrap.toInt(): Int = when (this) {
        TextureWrap.CLAMP_TO_EDGE -> GLES20.GL_CLAMP_TO_EDGE
        TextureWrap.REPEAT -> GLES20.GL_REPEAT
        TextureWrap.MIRRORED_REPEAT -> GLES20.GL_MIRRORED_REPEAT
    }

    /**
     * Deletes textures represented by given [textureHandles].
     */
    actual fun glDeleteTextures(textureHandles: IntArray) {
        GLES20.glDeleteTextures(textureHandles.size, textureHandles, 0)
    }

    /**
     * Selects active [textureIndex] starting with 0.
     */
    actual fun glActiveTexture(textureIndex: Int) {
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0 + textureIndex)
    }

    /**
     * Returns a range of indices supported by [glActiveTexture].
     */
    actual fun glTextureIndices(): IntRange =
        0 until glGetInteger(GLES20.GL_MAX_COMBINED_TEXTURE_IMAGE_UNITS)

    /**
     * Creates a shader of a given [shaderType] and returns its handle.
     */
    actual fun glCreateShader(shaderType: ShaderType): Int =
        GLES20.glCreateShader(shaderType.toInt())

    private fun ShaderType.toInt(): Int = when (this) {
        ShaderType.VERTEX_SHADER -> GLES20.GL_VERTEX_SHADER
        ShaderType.FRAGMENT_SHADER -> GLES20.GL_FRAGMENT_SHADER
    }

    /**
     * Sets [source] of the shader identified by a given [shaderHandle].
     */
    actual fun glShaderSource(shaderHandle: Int, source: String) {
        GLES20.glShaderSource(shaderHandle, source)
    }

    /**
     * Compiles a shader identified by a given [shaderHandle].
     */
    actual fun glCompileShader(shaderHandle: Int) {
        GLES20.glCompileShader(shaderHandle)
    }

    /**
     * Returns `true` if shader identified by a given [shaderHandle] has been successfully compiled.
     */
    actual fun glGetShaderCompileStatus(shaderHandle: Int): Boolean {
        val output = IntArray(size = 1)
        GLES20.glGetShaderiv(shaderHandle, GLES20.GL_COMPILE_STATUS, output, 0)
        return booleanOf(output.first())
    }

    /**
     * Returns information log for the shader identified by a given [shaderHandle].
     */
    actual fun glGetShaderInfoLog(shaderHandle: Int): String =
        GLES20.glGetShaderInfoLog(shaderHandle)

    /**
     * Deletes a shader identified by a given [shaderHandle].
     */
    actual fun glDeleteShader(shaderHandle: Int) {
        GLES20.glDeleteShader(shaderHandle)
    }

    /**
     * Returns `true` if shader identified by a given [shaderHandle] has been marked for deletion.
     */
    actual fun glGetShaderDeleteStatus(shaderHandle: Int): Boolean {
        val output = IntArray(size = 1)
        GLES20.glGetShaderiv(shaderHandle, GLES20.GL_DELETE_STATUS, output, 0)
        return booleanOf(output.first())
    }

    /**
     * Creates a program and returns its handle.
     */
    actual fun glCreateProgram(): Int =
        GLES20.glCreateProgram()

    /**
     * Attaches shader identified by a given [shaderHandle] to program identified by
     * a given [programHandle].
     */
    actual fun glAttachShader(programHandle: Int, shaderHandle: Int) {
        GLES20.glAttachShader(programHandle, shaderHandle)
    }

    /**
     * Links program identified by a given [programHandle].
     */
    actual fun glLinkProgram(programHandle: Int) {
        GLES20.glLinkProgram(programHandle)
    }

    /**
     * Returns `true` if program identified by a given [programHandle] has been successfully linked.
     */
    actual fun glGetProgramLinkStatus(programHandle: Int): Boolean {
        val output = IntArray(size = 1)
        GLES20.glGetProgramiv(programHandle, GLES20.GL_LINK_STATUS, output, 0)
        return booleanOf(output.first())
    }

    /**
     * Returns information log for the program identified by a given [programHandle].
     */
    actual fun glGetProgramInfoLog(programHandle: Int): String =
        GLES20.glGetProgramInfoLog(programHandle)

    /**
     * Validates program identified by a given [programHandle].
     */
    actual fun glValidateProgram(programHandle: Int) {
        GLES20.glValidateProgram(programHandle)
    }

    /**
     * Returns `true` if program identified by a given [programHandle] has been successfully
     * validated.
     */
    actual fun glGetProgramValidateStatus(programHandle: Int): Boolean {
        val output = IntArray(size = 1)
        GLES20.glGetProgramiv(programHandle, GLES20.GL_VALIDATE_STATUS, output, 0)
        return booleanOf(output.first())
    }

    /**
     * Uses a program identified by a given [programHandle].
     */
    actual fun glUseProgram(programHandle: Int) {
        GLES20.glUseProgram(programHandle)
    }

    /**
     * Deletes a program identified by a given [programHandle].
     */
    actual fun glDeleteProgram(programHandle: Int) {
        GLES20.glDeleteProgram(programHandle)
    }

    /**
     * Returns `true` if program identified by a given [programHandle] has been marked for deletion.
     */
    actual fun glGetProgramDeleteStatus(programHandle: Int): Boolean {
        val output = IntArray(size = 1)
        GLES20.glGetProgramiv(programHandle, GLES20.GL_DELETE_STATUS, output, 0)
        return booleanOf(output.first())
    }

    /**
     * Returns location of uniform variable with a given [name]
     * from program identified by a given [programHandle].
     */
    actual fun glGetUniformLocation(programHandle: Int, name: String): Int =
        GLES20.glGetUniformLocation(programHandle, name)

    /**
     * Returns location of attribute variable with a given [name]
     * from program identified by a given [programHandle].
     */
    actual fun glGetAttributeLocation(programHandle: Int, name: String): Int =
        GLES20.glGetAttribLocation(programHandle, name)

    /**
     * Sets [value] of integer uniform variable at a given [location] for current program.
     */
    actual fun glUniform(location: Int, value: Int) {
        GLES20.glUniform1i(location, value)
    }

    /**
     * Sets [value] of floating point uniform variable at a given [location] for current program.
     */
    actual fun glUniform(location: Int, value: Float) {
        GLES20.glUniform1f(location, value)
    }

    /**
     * Sets [value] of 2D vector uniform variable at a given [location] for current program.
     */
    actual fun glUniform(location: Int, value: Vec2) {
        GLES20.glUniform2f(location, value.x, value.y)
    }

    /**
     * Sets [value] of 3D vector uniform variable at a given [location] for current program.
     */
    actual fun glUniform(location: Int, value: Vec3) {
        GLES20.glUniform3f(location, value.x, value.y, value.z)
    }

    /**
     * Sets [value] of 4D vector uniform variable at a given [location] for current program.
     */
    actual fun glUniform(location: Int, value: Vec4) {
        GLES20.glUniform4f(location, value.x, value.y, value.z, value.w)
    }

    /**
     * Sets [values] of integer array uniform variable at a given [location] for current program.
     */
    actual fun glUniform(location: Int, vararg values: Int) {
        GLES20.glUniform1iv(location, values.size, values, 0)
    }

    /**
     * Sets [values] of floating point array uniform variable at a given [location] for current program.
     */
    actual fun glUniform(location: Int, vararg values: Float) {
        GLES20.glUniform1fv(location, values.size, values, 0)
    }

    /**
     * Sets [values] of 2D vector array uniform variable at a given [location] for current program.
     */
    actual fun glUniform(location: Int, vararg values: Vec2) {
        GLES20.glUniform2fv(location, values.size, values.flatMap { it.toList() }.toFloatArray(), 0)
    }

    /**
     * Sets [values] of 3D vector array uniform variable at a given [location] for current program.
     */
    actual fun glUniform(location: Int, vararg values: Vec3) {
        GLES20.glUniform3fv(location, values.size, values.flatMap { it.toList() }.toFloatArray(), 0)
    }

    /**
     * Sets [values] of 4D vector array uniform variable at a given [location] for current program.
     */
    actual fun glUniform(location: Int, vararg values: Vec4) {
        GLES20.glUniform4fv(location, values.size, values.flatMap { it.toList() }.toFloatArray(), 0)
    }

    /**
     * Sets [values] of 2×2 matrix array uniform variable at a given [location] for current program.
     *
     * Optionally, if the [transpose] flag is set to `true`, transpose matrices will be set instead.
     */
    actual fun glUniform(location: Int, vararg values: Mat2, transpose: Boolean) {
        GLES20.glUniformMatrix2fv(
            location,
            values.size,
            transpose,
            values.flatMap { it.elements }.toFloatArray(),
            0
        )
    }

    /**
     * Sets [values] of 3×3 matrix array uniform variable at a given [location] for current program.
     *
     * Optionally, if the [transpose] flag is set to `true`, transpose matrices will be set instead.
     */
    actual fun glUniform(location: Int, vararg values: Mat3, transpose: Boolean) {
        GLES20.glUniformMatrix3fv(
            location,
            values.size,
            transpose,
            values.flatMap { it.elements }.toFloatArray(),
            0
        )
    }

    /**
     * Sets [values] of 4×4 matrix array uniform variable at a given [location] for current program.
     *
     * Optionally, if the [transpose] flag is set to `true`, transpose matrices will be set instead.
     */
    actual fun glUniform(location: Int, vararg values: Mat4, transpose: Boolean) {
        GLES20.glUniformMatrix4fv(
            location,
            values.size,
            transpose,
            values.flatMap { it.elements }.toFloatArray(),
            0
        )
    }

    /**
     * Enables vertex attributes array at a given [location] for current program.
     */
    actual fun glEnableVertexAttribArray(location: Int) {
        GLES20.glEnableVertexAttribArray(location)
    }

    /**
     * Disables vertex attributes array at a given [location] for current program.
     */
    actual fun glDisableVertexAttribArray(location: Int) {
        GLES20.glDisableVertexAttribArray(location)
    }

    /**
     * Sets vertex attributes array at a given [location] for current program.
     */
    actual fun glVertexAttribPointer(
        location: Int,
        vectorSize: Int,
        normalized: Boolean,
        stride: Int,
        offset: Int
    ) {
        GLES20.glVertexAttribPointer(
            location,
            vectorSize,
            GLES20.GL_FLOAT,
            normalized,
            stride,
            offset
        )
    }

    /**
     * Draws a given [number][count] of primitives of type specified by [mode],
     * starting with [offset].
     */
    actual fun glDrawArrays(mode: DrawingMode, count: Int, offset: Int) {
        GLES20.glDrawArrays(mode.toInt(), offset, count)
    }

    private fun DrawingMode.toInt(): Int = when (this) {
        DrawingMode.POINTS -> GLES20.GL_POINTS
        DrawingMode.LINES -> GLES20.GL_LINES
        DrawingMode.LINE_LOOP -> GLES20.GL_LINE_LOOP
        DrawingMode.LINE_STRIP -> GLES20.GL_LINE_STRIP
        DrawingMode.TRIANGLES -> GLES20.GL_TRIANGLES
        DrawingMode.TRIANGLE_STRIP -> GLES20.GL_TRIANGLE_STRIP
        DrawingMode.TRIANGLE_FAN -> GLES20.GL_TRIANGLE_FAN
    }

    /**
     * Draws a given [number][count] of primitives of type specified by [mode],
     * using a buffer of vertex array indices, starting with [offset].
     */
    actual fun glDrawElements(mode: DrawingMode, count: Int, offset: Int) {
        GLES20.glDrawElements(mode.toInt(), count, GLES20.GL_UNSIGNED_INT, offset)
    }

    /**
     * Reads pixels from frame buffer.
     */
    actual fun glReadPixels(
        x: Int,
        y: Int,
        width: Int,
        height: Int,
        format: PixelFormat
    ): ByteArray {
        val bytesCount = width * height * format.bytesPerPixel
        val buffer = ByteBuffer.allocateDirect(bytesCount)
        GLES20.glReadPixels(x, y, width, height, format.toInt(), GLES20.GL_UNSIGNED_BYTE, buffer)
        val output = ByteArray(size = bytesCount)
        buffer.rewind()
        buffer.get(output)
        return output
    }

    private fun PixelFormat.toInt(): Int = when (this) {
        PixelFormat.ALPHA -> GLES20.GL_ALPHA
        PixelFormat.RGB -> GLES20.GL_RGB
        PixelFormat.RGBA -> GLES20.GL_RGBA
    }
}
