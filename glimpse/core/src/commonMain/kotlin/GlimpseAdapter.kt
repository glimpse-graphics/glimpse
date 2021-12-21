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

package graphics.glimpse

import graphics.glimpse.buffers.BufferType
import graphics.glimpse.buffers.BufferUsage
import graphics.glimpse.buffers.FloatBufferData
import graphics.glimpse.buffers.IntBufferData
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

/**
 * Glimpse OpenGL adapter.
 */
@Suppress("TooManyFunctions")
expect class GlimpseAdapter {

    /**
     * Returns a boolean value for the given integer [value].
     */
    fun booleanOf(value: Int): Boolean

    /**
     * Sets clear values for color buffers to [color] with alpha channel set to fully opaque.
     */
    fun glClearColor(color: Vec3)

    /**
     * Sets clear values for color buffers to [color].
     */
    fun glClearColor(color: Vec4)

    /**
     * Sets clear value for the depth buffer to [depth].
     */
    fun glClearDepth(depth: Float)

    /**
     * Sets the given [depthTestFunction].
     */
    fun glDepthTest(depthTestFunction: DepthTestFunction)

    /**
     * Sets facets to be culled to [faceCullingMode].
     */
    fun glCullFace(faceCullingMode: FaceCullingMode)

    /**
     * Enables blending.
     */
    fun glEnableBlending()

    /**
     * Disables blending.
     */
    fun glDisableBlending()

    /**
     * Sets functions for computing [source][sourceFactor] and [destination][destinationFactor] factors.
     */
    fun glBlendingFunction(sourceFactor: BlendingFactorFunction, destinationFactor: BlendingFactorFunction)

    /**
     * Sets blending constant [color] for [BlendingFactorFunction.CONSTANT_COLOR],
     * [BlendingFactorFunction.ONE_MINUS_CONSTANT_COLOR], [BlendingFactorFunction.CONSTANT_ALPHA]
     * and [BlendingFactorFunction.ONE_MINUS_CONSTANT_ALPHA].
     */
    fun glBlendingColor(color: Vec4)

    /**
     * Enables rasterized line anti-aliasing.
     */
    fun glEnableLineSmooth()

    /**
     * Disables rasterized line anti-aliasing.
     */
    fun glDisableLineSmooth()

    /**
     * Sets width of rasterized lines to a given [lineWidth].
     */
    fun glLineWidth(lineWidth: Float)

    /**
     * Enables setting size of rasterized points in vertex shader.
     */
    fun glEnableProgramPointSize()

    /**
     * Disables setting size of rasterized points in vertex shader.
     */
    fun glDisableProgramPointSize()

    /**
     * Sets the viewport to have its bottom-left corner at ([x], [y]),
     * and a given [width] and [height].
     */
    fun glViewport(x: Int = 0, y: Int = 0, width: Int, height: Int)

    /**
     * Clears given buffers to the predefined clear values.
     */
    fun glClear(vararg buffers: ClearableBufferType)

    /**
     * Generates buffer handles and writes them to a given [bufferHandles] array.
     *
     * The number of generated buffer handles is equal to the size of the given
     * [bufferHandles] array.
     */
    fun glGenBuffers(bufferHandles: IntArray)

    /**
     * Binds a given [bufferHandle] to a given buffer [type].
     */
    fun glBindBuffer(type: BufferType, bufferHandle: Int)

    /**
     * Creates a buffer of integer values and fills it with data.
     */
    fun glBufferData(type: BufferType, data: IntBufferData, usage: BufferUsage)

    /**
     * Creates a buffer of floating point values and fills it with data.
     */
    fun glBufferData(type: BufferType, data: FloatBufferData, usage: BufferUsage)

    /**
     * Deletes buffers represented by given [bufferHandles].
     */
    fun glDeleteBuffers(bufferHandles: IntArray)

    /**
     * Generates texture handles and writes them to a given [textureHandles] array.
     *
     * The number of generated texture handles is equal to the the size of the given
     * [textureHandles] array.
     */
    fun glGenTextures(textureHandles: IntArray)

    /**
     * Binds a given [textureHandle] to a given texture [type].
     */
    fun glBindTexture(type: TextureType, textureHandle: Int)

    /**
     * Gets maximum size of a texture of a given [type].
     */
    fun glMaxTextureSize(type: TextureType): Int

    /**
     * Generates mipmaps for the currently selected texture of a given [type].
     */
    fun glGenerateMipmap(type: TextureType)

    /**
     * Sets texture [minifying][minFilter] and [magnifying][magFilter] filters for the currently
     * selected texture of a given [type].
     */
    fun glTexParameterFilter(
        type: TextureType,
        minFilter: TextureMinFilter,
        magFilter: TextureMagFilter
    )

    /**
     * Sets wrap parameter for texture coordinates [S][wrapS] and [T][wrapT] for the currently
     * selected texture of a given [type].
     */
    fun glTexParameterWrap(
        type: TextureType,
        wrapS: TextureWrap,
        wrapT: TextureWrap
    )

    /**
     * Deletes textures represented by given [textureHandles].
     */
    fun glDeleteTextures(textureHandles: IntArray)

    /**
     * Selects active [textureIndex] starting with 0.
     */
    fun glActiveTexture(textureIndex: Int)

    /**
     * Returns a range of indices supported by [glActiveTexture].
     */
    fun glTextureIndices(): IntRange

    /**
     * Creates a shader of a given [shaderType] and returns its handle.
     */
    fun glCreateShader(shaderType: ShaderType): Int

    /**
     * Sets [source] of the shader identified by a given [shaderHandle].
     */
    fun glShaderSource(shaderHandle: Int, source: String)

    /**
     * Compiles a shader identified by a given [shaderHandle].
     */
    fun glCompileShader(shaderHandle: Int)

    /**
     * Returns `true` if shader identified by a given [shaderHandle] has been successfully compiled.
     */
    fun glGetShaderCompileStatus(shaderHandle: Int): Boolean

    /**
     * Returns information log for the shader identified by a given [shaderHandle].
     */
    fun glGetShaderInfoLog(shaderHandle: Int): String

    /**
     * Deletes a shader identified by a given [shaderHandle].
     */
    fun glDeleteShader(shaderHandle: Int)

    /**
     * Returns `true` if shader identified by a given [shaderHandle] has been marked for deletion.
     */
    fun glGetShaderDeleteStatus(shaderHandle: Int): Boolean

    /**
     * Creates a program and returns its handle.
     */
    fun glCreateProgram(): Int

    /**
     * Attaches shader identified by a given [shaderHandle] to program identified by
     * a given [programHandle].
     */
    fun glAttachShader(programHandle: Int, shaderHandle: Int)

    /**
     * Links program identified by a given [programHandle].
     */
    fun glLinkProgram(programHandle: Int)

    /**
     * Returns `true` if program identified by a given [programHandle] has been successfully linked.
     */
    fun glGetProgramLinkStatus(programHandle: Int): Boolean

    /**
     * Returns information log for the program identified by a given [programHandle].
     */
    fun glGetProgramInfoLog(programHandle: Int): String

    /**
     * Validates program identified by a given [programHandle].
     */
    fun glValidateProgram(programHandle: Int)

    /**
     * Returns `true` if program identified by a given [programHandle] has been successfully
     * validated.
     */
    fun glGetProgramValidateStatus(programHandle: Int): Boolean

    /**
     * Uses a program identified by a given [programHandle].
     */
    fun glUseProgram(programHandle: Int)

    /**
     * Deletes a program identified by a given [programHandle].
     */
    fun glDeleteProgram(programHandle: Int)

    /**
     * Returns `true` if program identified by a given [programHandle] has been marked for deletion.
     */
    fun glGetProgramDeleteStatus(programHandle: Int): Boolean

    /**
     * Returns location of uniform variable with a given [name]
     * from program identified by a given [programHandle].
     */
    fun glGetUniformLocation(programHandle: Int, name: String): Int

    /**
     * Returns location of attribute variable with a given [name]
     * from program identified by a given [programHandle].
     */
    fun glGetAttributeLocation(programHandle: Int, name: String): Int

    /**
     * Sets [value] of boolean uniform variable at a given [location] for current program.
     *
     * A boolean uniform is converted to an integer value of `GL_TRUE` or `GL_FALSE`.
     *
     * @since v1.1.0
     */
    fun glUniform(location: Int, value: Boolean)

    /**
     * Sets [value] of integer uniform variable at a given [location] for current program.
     */
    fun glUniform(location: Int, value: Int)

    /**
     * Sets [value] of floating point uniform variable at a given [location] for current program.
     */
    fun glUniform(location: Int, value: Float)

    /**
     * Sets [value] of 2D vector uniform variable at a given [location] for current program.
     */
    fun glUniform(location: Int, value: Vec2)

    /**
     * Sets [value] of 3D vector uniform variable at a given [location] for current program.
     */
    fun glUniform(location: Int, value: Vec3)

    /**
     * Sets [value] of 4D vector uniform variable at a given [location] for current program.
     */
    fun glUniform(location: Int, value: Vec4)

    /**
     * Sets [values] of integer array uniform variable at a given [location] for current program.
     */
    fun glUniform(location: Int, vararg values: Int)

    /**
     * Sets [values] of floating point array uniform variable at a given [location] for current program.
     */
    fun glUniform(location: Int, vararg values: Float)

    /**
     * Sets [values] of 2D vector array uniform variable at a given [location] for current program.
     */
    fun glUniform(location: Int, vararg values: Vec2)

    /**
     * Sets [values] of 3D vector array uniform variable at a given [location] for current program.
     */
    fun glUniform(location: Int, vararg values: Vec3)

    /**
     * Sets [values] of 4D vector array uniform variable at a given [location] for current program.
     */
    fun glUniform(location: Int, vararg values: Vec4)

    /**
     * Sets [values] of 2×2 matrix array uniform variable at a given [location] for current program.
     *
     * Optionally, if the [transpose] flag is set to `true`, transpose matrices will be set instead.
     */
    fun glUniform(location: Int, vararg values: Mat2, transpose: Boolean = false)

    /**
     * Sets [values] of 3×3 matrix array uniform variable at a given [location] for current program.
     *
     * Optionally, if the [transpose] flag is set to `true`, transpose matrices will be set instead.
     */
    fun glUniform(location: Int, vararg values: Mat3, transpose: Boolean = false)

    /**
     * Sets [values] of 4×4 matrix array uniform variable at a given [location] for current program.
     *
     * Optionally, if the [transpose] flag is set to `true`, transpose matrices will be set instead.
     */
    fun glUniform(location: Int, vararg values: Mat4, transpose: Boolean = false)

    /**
     * Enables vertex attributes array at a given [location] for current program.
     */
    fun glEnableVertexAttribArray(location: Int)

    /**
     * Disables vertex attributes array at a given [location] for current program.
     */
    fun glDisableVertexAttribArray(location: Int)

    /**
     * Sets vertex attributes array at a given [location] for current program.
     */
    fun glVertexAttribPointer(
        location: Int,
        vectorSize: Int,
        normalized: Boolean = false,
        stride: Int = 0,
        offset: Int = 0
    )

    /**
     * Draws a given [number][count] of primitives of type specified by [mode],
     * starting with [offset].
     */
    fun glDrawArrays(mode: DrawingMode, count: Int, offset: Int = 0)

    /**
     * Draws a given [number][count] of primitives of type specified by [mode],
     * using a buffer of vertex array indices, starting with [offset].
     */
    fun glDrawElements(mode: DrawingMode, count: Int, offset: Int = 0)

    /**
     * Reads pixels from frame buffer.
     */
    fun glReadPixels(
        x: Int = 0,
        y: Int = 0,
        width: Int,
        height: Int,
        format: PixelFormat = PixelFormat.RGBA
    ): ByteArray
}
