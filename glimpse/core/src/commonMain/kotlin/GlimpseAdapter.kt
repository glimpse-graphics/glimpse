/*
 * Copyright 2020-2023 Glimpse Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package graphics.glimpse

import graphics.glimpse.buffers.BufferType
import graphics.glimpse.buffers.BufferUsage
import graphics.glimpse.buffers.DoubleBufferData
import graphics.glimpse.buffers.FloatBufferData
import graphics.glimpse.buffers.FloatUniformBuffer
import graphics.glimpse.buffers.IntBufferData
import graphics.glimpse.buffers.IntUniformBuffer
import graphics.glimpse.framebuffers.FramebufferAttachmentType
import graphics.glimpse.framebuffers.FramebufferStatus
import graphics.glimpse.shaders.ShaderType
import graphics.glimpse.textures.TextureInternalFormat
import graphics.glimpse.textures.TextureMagFilter
import graphics.glimpse.textures.TextureMinFilter
import graphics.glimpse.textures.TexturePixelFormat
import graphics.glimpse.textures.TexturePixelType
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
    fun glClearColor(color: Vec3<Float>)

    /**
     * Sets clear values for color buffers to [color].
     */
    fun glClearColor(color: Vec4<Float>)

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
    fun glBlendingColor(color: Vec4<Float>)

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
     * Sets given [vSync] mode.
     *
     * @return `true` if the operation was successful.
     *
     * @since v1.2.0
     */
    fun glVSync(vSync: VSync): Boolean

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
     * Creates a buffer of double-precision floating point values and fills it with data.
     *
     * @since v2.0.0
     */
    fun glBufferData(type: BufferType, data: DoubleBufferData, usage: BufferUsage)

    /**
     * Deletes buffers represented by given [bufferHandles].
     */
    fun glDeleteBuffers(bufferHandles: IntArray)

    /**
     * Generates framebuffer handles and writes them to a given [framebufferHandles] array.
     *
     * The number of generated framebuffer handles is equal to the size of the given
     * [framebufferHandles] array.
     *
     * @since v1.1.0
     */
    fun glGenFramebuffers(framebufferHandles: IntArray)

    /**
     * Binds a given [framebufferHandle] to a framebuffer target.
     *
     * @since v1.1.0
     */
    fun glBindFramebuffer(framebufferHandle: Int)

    /**
     * Attaches renderbuffer to a framebuffer.
     *
     * @since v1.1.0
     */
    fun glFramebufferRenderbuffer(attachmentType: FramebufferAttachmentType, renderbufferHandle: Int)

    /**
     * Attaches texture image to a framebuffer.
     *
     * @since v1.1.0
     */
    fun glFramebufferTexture2D(
        attachmentType: FramebufferAttachmentType,
        textureType: TextureType,
        textureHandle: Int
    )

    /**
     * Returns completeness status of a framebuffer.
     *
     * @since v1.1.0
     */
    fun glCheckFramebufferStatus(): FramebufferStatus

    /**
     * Deletes framebuffers represented by given [framebufferHandles].
     *
     * @since v1.1.0
     */
    fun glDeleteFramebuffers(framebufferHandles: IntArray)

    /**
     * Generates renderbuffer handles and writes them to a given [renderbufferHandles] array.
     *
     * The number of generated renderbuffer handles is equal to the size of the given
     * [renderbufferHandles] array.
     *
     * @since v1.1.0
     */
    fun glGenRenderbuffers(renderbufferHandles: IntArray)

    /**
     * Binds a given [renderbufferHandle] to a renderbuffer target.
     *
     * @since v1.1.0
     */
    fun glBindRenderbuffer(renderbufferHandle: Int)

    /**
     * Specifies format and dimensions of the image in renderbuffer.
     *
     * @since v1.1.0
     */
    fun glRenderbufferStorage(internalFormat: TextureInternalFormat, width: Int, height: Int)

    /**
     * Deletes renderbuffers represented by given [renderbufferHandles].
     *
     * @since v1.1.0
     */
    fun glDeleteRenderbuffers(renderbufferHandles: IntArray)

    /**
     * Generates texture handles and writes them to a given [textureHandles] array.
     *
     * The number of generated texture handles is equal to the size of the given
     * [textureHandles] array.
     */
    fun glGenTextures(textureHandles: IntArray)

    /**
     * Binds a given [textureHandle] to a given texture [type].
     */
    fun glBindTexture(type: TextureType, textureHandle: Int)

    /**
     * Specifies a 2D texture image.
     *
     * @since v1.1.0
     */
    fun glTexImage2D(
        internalFormat: TextureInternalFormat,
        width: Int,
        height: Int,
        pixelFormat: TexturePixelFormat,
        pixelType: TexturePixelType,
        pixelData: ByteArray?
    )

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
     *
     * Since v1.1.0, there is no need to call this function explicitly. To change texture filters,
     * use [texture builder][graphics.glimpse.textures.Texture.Builder.setTextureFilter].
     */
    fun glTexParameterFilter(
        type: TextureType,
        minFilter: TextureMinFilter,
        magFilter: TextureMagFilter
    )

    /**
     * Sets wrap parameter for texture coordinates [S][wrapS] and [T][wrapT] for the currently
     * selected texture of a given [type].
     *
     * Since v1.1.0, there is no need to call this function explicitly. To change texture wrapping,
     * use [texture builder][graphics.glimpse.textures.Texture.Builder.setTextureWrap].
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
     * A boolean value is converted to an integer value of `GL_TRUE` or `GL_FALSE`.
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
     *
     * @since v2.0.0
     */
    fun glUniform(location: Int, value: Vec2<Int>)

    /**
     * Sets [value] of 2D vector uniform variable at a given [location] for current program.
     */
    fun glUniform(location: Int, value: Vec2<Float>)

    /**
     * Sets [value] of 3D vector uniform variable at a given [location] for current program.
     *
     * @since v2.0.0
     */
    fun glUniform(location: Int, value: Vec3<Int>)

    /**
     * Sets [value] of 3D vector uniform variable at a given [location] for current program.
     */
    fun glUniform(location: Int, value: Vec3<Float>)

    /**
     * Sets [value] of 4D vector uniform variable at a given [location] for current program.
     *
     * @since v2.0.0
     */
    fun glUniform(location: Int, value: Vec4<Int>)

    /**
     * Sets [value] of 4D vector uniform variable at a given [location] for current program.
     */
    fun glUniform(location: Int, value: Vec4<Float>)

    /**
     * Sets [values] of boolean array uniform variable at a given [location] for current program.
     *
     * A boolean value is converted to an integer value of `GL_TRUE` or `GL_FALSE`.
     *
     * @since v2.0.0
     */
    fun glUniform(location: Int, vararg values: Boolean)

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
     *
     * @since v2.0.0
     */
    fun glUniform(location: Int, vararg values: Vec2<Int>)

    /**
     * Sets [values] of 2D vector array uniform variable at a given [location] for current program.
     */
    fun glUniform(location: Int, vararg values: Vec2<Float>)

    /**
     * Sets [values] of 3D vector array uniform variable at a given [location] for current program.
     *
     * @since v2.0.0
     */
    fun glUniform(location: Int, vararg values: Vec3<Int>)

    /**
     * Sets [values] of 3D vector array uniform variable at a given [location] for current program.
     */
    fun glUniform(location: Int, vararg values: Vec3<Float>)

    /**
     * Sets [values] of 4D vector array uniform variable at a given [location] for current program.
     *
     * @since v2.0.0
     */
    fun glUniform(location: Int, vararg values: Vec4<Int>)

    /**
     * Sets [values] of 4D vector array uniform variable at a given [location] for current program.
     */
    fun glUniform(location: Int, vararg values: Vec4<Float>)

    /**
     * Sets [values] of 2×2 matrix array uniform variable at a given [location] for current program.
     *
     * Optionally, if the [transpose] flag is set to `true`, transpose matrices will be set instead.
     */
    fun glUniform(location: Int, vararg values: Mat2<Float>, transpose: Boolean = false)

    /**
     * Sets [values] of 3×3 matrix array uniform variable at a given [location] for current program.
     *
     * Optionally, if the [transpose] flag is set to `true`, transpose matrices will be set instead.
     */
    fun glUniform(location: Int, vararg values: Mat3<Float>, transpose: Boolean = false)

    /**
     * Sets [values] of 4×4 matrix array uniform variable at a given [location] for current program.
     *
     * Optionally, if the [transpose] flag is set to `true`, transpose matrices will be set instead.
     */
    fun glUniform(location: Int, vararg values: Mat4<Float>, transpose: Boolean = false)

    /**
     * Sets integer [buffer] uniform variable at a given [location] for current program.
     *
     * @since v2.0.0
     */
    fun glUniform(location: Int, buffer: IntUniformBuffer)

    /**
     * Sets floating point [buffer] uniform variable at a given [location] for current program.
     *
     * @since v2.0.0
     */
    fun glUniform(location: Int, buffer: FloatUniformBuffer)

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
