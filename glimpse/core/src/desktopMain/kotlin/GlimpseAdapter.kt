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

import com.jogamp.opengl.GL2ES2
import com.jogamp.opengl.GL2GL3
import graphics.glimpse.buffers.BufferType
import graphics.glimpse.buffers.BufferUsage
import graphics.glimpse.buffers.DoubleBufferData
import graphics.glimpse.buffers.FloatBufferData
import graphics.glimpse.buffers.FloatUniformBuffer
import graphics.glimpse.buffers.IntBufferData
import graphics.glimpse.buffers.IntUniformBuffer
import graphics.glimpse.buffers.UniformBufferDataElementType
import graphics.glimpse.framebuffers.FramebufferAttachmentType
import graphics.glimpse.framebuffers.FramebufferStatus
import graphics.glimpse.logging.GlimpseLogger
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
import java.nio.ByteBuffer

/**
 * Glimpse OpenGL adapter for the given [GL ES 2.0][gles].
 */
@Suppress("TooManyFunctions")
actual class GlimpseAdapter(internal val gles: GL2ES2) {

    private val logger: GlimpseLogger = GlimpseLogger.create(this)

    /**
     * Returns a boolean value for the given integer [value].
     */
    actual fun booleanOf(value: Int): Boolean = value != GL2ES2.GL_FALSE

    /**
     * Sets clear values for color buffers to [color] with alpha channel set to fully opaque.
     */
    actual fun glClearColor(color: Vec3<Float>) {
        logger.debug(message = "Setting clear color: $color")
        gles.glClearColor(color.r, color.g, color.b, 1f)
    }

    /**
     * Sets clear values for color buffers to [color].
     */
    actual fun glClearColor(color: Vec4<Float>) {
        logger.debug(message = "Setting clear color: $color")
        gles.glClearColor(color.r, color.g, color.b, color.a)
    }

    /**
     * Sets clear value for the depth buffer to [depth].
     */
    actual fun glClearDepth(depth: Float) {
        logger.debug(message = "Setting clear depth: $depth")
        gles.glClearDepthf(depth)
    }

    /**
     * Sets the given [depthTestFunction].
     */
    actual fun glDepthTest(depthTestFunction: DepthTestFunction) {
        logger.debug(message = "Setting depth test function: $depthTestFunction")
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
        logger.debug(message = "Setting face culling mode: $faceCullingMode")
        if (faceCullingMode.isFaceCullingEnabled) {
            gles.glEnable(GL2ES2.GL_CULL_FACE)
        } else {
            gles.glDisable(GL2ES2.GL_CULL_FACE)
        }
        faceCullingMode.toInt()?.let { mode -> gles.glCullFace(mode) }
    }

    private fun FaceCullingMode.toInt(): Int? = when (this) {
        FaceCullingMode.DISABLED -> null
        FaceCullingMode.FRONT -> GL2ES2.GL_FRONT
        FaceCullingMode.BACK -> GL2ES2.GL_BACK
        FaceCullingMode.FRONT_AND_BACK -> GL2ES2.GL_FRONT_AND_BACK
    }

    /**
     * Enables blending.
     */
    actual fun glEnableBlending() {
        gles.glEnable(GL2ES2.GL_BLEND)
    }

    /**
     * Disables blending.
     */
    actual fun glDisableBlending() {
        gles.glDisable(GL2ES2.GL_BLEND)
    }

    /**
     * Sets functions for computing [source][sourceFactor] and [destination][destinationFactor] factors.
     */
    actual fun glBlendingFunction(
        sourceFactor: BlendingFactorFunction,
        destinationFactor: BlendingFactorFunction
    ) {
        gles.glBlendFunc(sourceFactor.toInt(), destinationFactor.toInt())
    }

    private fun BlendingFactorFunction.toInt(): Int = when (this) {
        BlendingFactorFunction.ZERO -> GL2ES2.GL_ZERO
        BlendingFactorFunction.ONE -> GL2ES2.GL_ONE
        BlendingFactorFunction.SOURCE_COLOR -> GL2ES2.GL_SRC_COLOR
        BlendingFactorFunction.ONE_MINUS_SOURCE_COLOR -> GL2ES2.GL_ONE_MINUS_SRC_COLOR
        BlendingFactorFunction.DESTINATION_COLOR -> GL2ES2.GL_DST_COLOR
        BlendingFactorFunction.ONE_MINUS_DESTINATION_COLOR -> GL2ES2.GL_ONE_MINUS_DST_COLOR
        BlendingFactorFunction.SOURCE_ALPHA -> GL2ES2.GL_SRC_ALPHA
        BlendingFactorFunction.ONE_MINUS_SOURCE_ALPHA -> GL2ES2.GL_ONE_MINUS_SRC_ALPHA
        BlendingFactorFunction.DESTINATION_ALPHA -> GL2ES2.GL_DST_ALPHA
        BlendingFactorFunction.ONE_MINUS_DESTINATION_ALPHA -> GL2ES2.GL_ONE_MINUS_DST_ALPHA
        BlendingFactorFunction.CONSTANT_COLOR -> GL2ES2.GL_CONSTANT_COLOR
        BlendingFactorFunction.ONE_MINUS_CONSTANT_COLOR -> GL2ES2.GL_ONE_MINUS_CONSTANT_COLOR
        BlendingFactorFunction.CONSTANT_ALPHA -> GL2ES2.GL_CONSTANT_ALPHA
        BlendingFactorFunction.ONE_MINUS_CONSTANT_ALPHA -> GL2ES2.GL_ONE_MINUS_CONSTANT_ALPHA
    }

    /**
     * Sets blending constant [color] for [BlendingFactorFunction.CONSTANT_COLOR],
     * [BlendingFactorFunction.ONE_MINUS_CONSTANT_COLOR], [BlendingFactorFunction.CONSTANT_ALPHA]
     * and [BlendingFactorFunction.ONE_MINUS_CONSTANT_ALPHA].
     */
    actual fun glBlendingColor(color: Vec4<Float>) {
        gles.glBlendColor(color.r, color.g, color.b, color.a)
    }

    /**
     * Enables rasterized line anti-aliasing.
     */
    actual fun glEnableLineSmooth() {
        gles.glEnable(GL2ES2.GL_LINE_SMOOTH)
    }

    /**
     * Disables rasterized line anti-aliasing.
     */
    actual fun glDisableLineSmooth() {
        gles.glDisable(GL2ES2.GL_LINE_SMOOTH)
    }

    /**
     * Sets width of rasterized lines to a given [lineWidth].
     */
    actual fun glLineWidth(lineWidth: Float) {
        gles.glLineWidth(lineWidth)
    }

    /**
     * Enables setting size of rasterized points in vertex shader.
     */
    actual fun glEnableProgramPointSize() {
        gles.glEnable(GL2GL3.GL_VERTEX_PROGRAM_POINT_SIZE)
    }

    /**
     * Disables setting size of rasterized points in vertex shader.
     */
    actual fun glDisableProgramPointSize() {
        gles.glDisable(GL2GL3.GL_VERTEX_PROGRAM_POINT_SIZE)
    }

    /**
     * Sets given [vSync] mode.
     *
     * @return `true` if the operation was successful.
     *
     * @since v1.2.0
     */
    actual fun glVSync(vSync: VSync): Boolean =
        gles.context.setSwapInterval(vSync.toInt())

    private fun VSync.toInt(): Int = when (this) {
        VSync.OFF -> 0
        VSync.ON -> 1
        VSync.ADAPTIVE -> -1
    }

    /**
     * Sets the viewport to have its bottom-left corner at ([x], [y]),
     * and a given [width] and [height].
     */
    actual fun glViewport(x: Int, y: Int, width: Int, height: Int) {
        logger.debug(message = "Setting viewport: x=$x, y=$y, width=$width, height=$height")
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
     * Creates a buffer of double-precision floating point values and fills it with data.
     *
     * @since v2.0.0
     */
    actual fun glBufferData(type: BufferType, data: DoubleBufferData, usage: BufferUsage) {
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
     * Generates framebuffer handles and writes them to a given [framebufferHandles] array.
     *
     * The number of generated framebuffer handles is equal to the size of the given
     * [framebufferHandles] array.
     *
     * @since v1.1.0
     */
    actual fun glGenFramebuffers(framebufferHandles: IntArray) {
        gles.glGenFramebuffers(framebufferHandles.size, framebufferHandles, 0)
    }

    /**
     * Binds a given [framebufferHandle] to a framebuffer target.
     *
     * @since v1.1.0
     */
    actual fun glBindFramebuffer(framebufferHandle: Int) {
        gles.glBindFramebuffer(GL2ES2.GL_FRAMEBUFFER, framebufferHandle)
    }

    /**
     * Returns maximum number of color attachments of a framebuffer.
     *
     * @since v2.0.0
     */
    actual fun glGetMaxColorAttachments(): Int =
        glGetInteger(GL2ES2.GL_MAX_COLOR_ATTACHMENTS)

    /**
     * Attaches renderbuffer to a framebuffer.
     *
     * @since v1.1.0
     */
    actual fun glFramebufferRenderbuffer(attachmentType: FramebufferAttachmentType, renderbufferHandle: Int) {
        gles.glFramebufferRenderbuffer(
            GL2ES2.GL_FRAMEBUFFER,
            attachmentType.toInt(),
            GL2ES2.GL_RENDERBUFFER,
            renderbufferHandle
        )
    }

    private fun FramebufferAttachmentType.toInt(): Int = when (this) {
        FramebufferAttachmentType.Depth -> GL2ES2.GL_DEPTH_ATTACHMENT
        FramebufferAttachmentType.Stencil -> GL2ES2.GL_STENCIL_ATTACHMENT
        is FramebufferAttachmentType.Color -> GL2ES2.GL_COLOR_ATTACHMENT0 + this.index
    }

    /**
     * Attaches texture image to a framebuffer.
     *
     * @since v1.1.0
     */
    actual fun glFramebufferTexture2D(
        attachmentType: FramebufferAttachmentType,
        textureType: TextureType,
        textureHandle: Int
    ) {
        gles.glFramebufferTexture2D(
            GL2ES2.GL_FRAMEBUFFER,
            attachmentType.toInt(),
            textureType.toInt(),
            textureHandle,
            0
        )
    }

    /**
     * Returns completeness status of a framebuffer.
     *
     * @since v1.1.0
     */
    actual fun glCheckFramebufferStatus(): FramebufferStatus =
        framebufferStatusOf(gles.glCheckFramebufferStatus(GL2ES2.GL_FRAMEBUFFER))

    private fun framebufferStatusOf(value: Int): FramebufferStatus = when (value) {
        GL2ES2.GL_FRAMEBUFFER_COMPLETE -> FramebufferStatus.COMPLETE
        GL2ES2.GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT -> FramebufferStatus.INCOMPLETE_ATTACHMENT
        GL2ES2.GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT -> FramebufferStatus.INCOMPLETE_MISSING_ATTACHMENT
        GL2ES2.GL_FRAMEBUFFER_UNSUPPORTED -> FramebufferStatus.UNSUPPORTED
        GL2ES2.GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE -> FramebufferStatus.INCOMPLETE_MULTISAMPLE
        else -> FramebufferStatus.UNKNOWN_STATUS
    }

    /**
     * Specifies the list of [colorBuffers] to be drawn into.
     *
     * @since v2.0.0
     */
    actual fun glDrawBuffers(vararg colorBuffers: FramebufferAttachmentType.Color) {
        gles.glDrawBuffers(colorBuffers.size, colorBuffers.map { it.toInt() }.toIntArray(), 0)
    }

    /**
     * Deletes framebuffers represented by given [framebufferHandles].
     *
     * @since v1.1.0
     */
    actual fun glDeleteFramebuffers(framebufferHandles: IntArray) {
        gles.glDeleteFramebuffers(framebufferHandles.size, framebufferHandles, 0)
    }

    /**
     * Generates renderbuffer handles and writes them to a given [renderbufferHandles] array.
     *
     * The number of generated renderbuffer handles is equal to the size of the given
     * [renderbufferHandles] array.
     *
     * @since v1.1.0
     */
    actual fun glGenRenderbuffers(renderbufferHandles: IntArray) {
        gles.glGenRenderbuffers(renderbufferHandles.size, renderbufferHandles, 0)
    }

    /**
     * Binds a given [renderbufferHandle] to a renderbuffer target.
     *
     * @since v1.1.0
     */
    actual fun glBindRenderbuffer(renderbufferHandle: Int) {
        gles.glBindRenderbuffer(GL2ES2.GL_RENDERBUFFER, renderbufferHandle)
    }

    /**
     * Specifies format and dimensions of the image in renderbuffer.
     *
     * @since v1.1.0
     */
    actual fun glRenderbufferStorage(internalFormat: TextureInternalFormat, width: Int, height: Int) {
        gles.glRenderbufferStorage(GL2ES2.GL_RENDERBUFFER, internalFormat.toInt(), width, height)
    }

    /**
     * Deletes renderbuffers represented by given [renderbufferHandles].
     *
     * @since v1.1.0
     */
    actual fun glDeleteRenderbuffers(renderbufferHandles: IntArray) {
        gles.glDeleteRenderbuffers(renderbufferHandles.size, renderbufferHandles, 0)
    }

    /**
     * Generates texture handles and writes them to a given [textureHandles] array.
     *
     * The number of generated texture handles is equal to the size of the given
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
     * Specifies a 2D texture image.
     *
     * @since v1.1.0
     */
    actual fun glTexImage2D(
        internalFormat: TextureInternalFormat,
        width: Int,
        height: Int,
        pixelFormat: TexturePixelFormat,
        pixelType: TexturePixelType,
        pixelData: ByteArray?
    ) {
        val pixelDataBuffer: ByteBuffer? = pixelData?.let { array ->
            ByteBuffer.allocateDirect(array.size)
                .apply {
                    put(array)
                    rewind()
                }
        }
        gles.glTexImage2D(
            GL2ES2.GL_TEXTURE_2D, 0, internalFormat.toInt(),
            width, height, 0,
            pixelFormat.toInt(), pixelType.toInt(), pixelDataBuffer
        )
    }

    private fun TextureInternalFormat.toInt(): Int = when (this) {
        TextureInternalFormat.DEPTH_COMPONENT -> GL2ES2.GL_DEPTH_COMPONENT
        TextureInternalFormat.DEPTH_STENCIL -> GL2ES2.GL_DEPTH_STENCIL
        TextureInternalFormat.RED -> GL2ES2.GL_RED
        TextureInternalFormat.RG -> GL2ES2.GL_RG
        TextureInternalFormat.RGB -> GL2ES2.GL_RGB
        TextureInternalFormat.RGBA -> GL2ES2.GL_RGBA
        TextureInternalFormat.RGB16F -> GL2ES2.GL_RGB16F
        TextureInternalFormat.RGBA16F -> GL2ES2.GL_RGBA16F
        TextureInternalFormat.RGB32F -> GL2ES2.GL_RGB32F
        TextureInternalFormat.RGBA32F -> GL2ES2.GL_RGBA32F
    }

    private fun TexturePixelFormat.toInt(): Int = when (this) {
        TexturePixelFormat.DEPTH_COMPONENT -> GL2ES2.GL_DEPTH_COMPONENT
        TexturePixelFormat.DEPTH_STENCIL -> GL2ES2.GL_DEPTH_STENCIL
        TexturePixelFormat.RED -> GL2ES2.GL_RED
        TexturePixelFormat.RG -> GL2ES2.GL_RG
        TexturePixelFormat.RGB -> GL2ES2.GL_RGB
        TexturePixelFormat.RGBA -> GL2ES2.GL_RGBA
    }

    private fun TexturePixelType.toInt(): Int = when (this) {
        TexturePixelType.UNSIGNED_BYTE -> GL2ES2.GL_UNSIGNED_BYTE
        TexturePixelType.BYTE -> GL2ES2.GL_BYTE
        TexturePixelType.UNSIGNED_SHORT -> GL2ES2.GL_UNSIGNED_SHORT
        TexturePixelType.SHORT -> GL2ES2.GL_SHORT
        TexturePixelType.UNSIGNED_INT -> GL2ES2.GL_UNSIGNED_INT
        TexturePixelType.INT -> GL2ES2.GL_INT
        TexturePixelType.HALF_FLOAT -> GL2ES2.GL_HALF_FLOAT
        TexturePixelType.FLOAT -> GL2ES2.GL_FLOAT
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
     *
     * Since v1.1.0, there is no need to call this function explicitly. To change texture filters,
     * use [texture builder][graphics.glimpse.textures.Texture.Builder.setTextureFilter].
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
     *
     * Since v1.1.0, there is no need to call this function explicitly. To change texture wrapping,
     * use [texture builder][graphics.glimpse.textures.Texture.Builder.setTextureWrap].
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

    /**
     * Returns location of uniform variable with a given [name]
     * from program identified by a given [programHandle].
     */
    actual fun glGetUniformLocation(programHandle: Int, name: String): Int =
        gles.glGetUniformLocation(programHandle, name)

    /**
     * Returns location of attribute variable with a given [name]
     * from program identified by a given [programHandle].
     */
    actual fun glGetAttributeLocation(programHandle: Int, name: String): Int =
        gles.glGetAttribLocation(programHandle, name)

    /**
     * Sets [value] of boolean uniform variable at a given [location] for current program.
     *
     * A boolean value is converted to an integer value of `GL_TRUE` or `GL_FALSE`.
     *
     * @since v1.1.0
     */
    actual fun glUniform(location: Int, value: Boolean) {
        gles.glUniform1i(location, value.toInt())
    }

    private fun Boolean.toInt(): Int =
        if (this) GL2ES2.GL_TRUE else GL2ES2.GL_FALSE

    /**
     * Sets [value] of integer uniform variable at a given [location] for current program.
     */
    actual fun glUniform(location: Int, value: Int) {
        gles.glUniform1i(location, value)
    }

    /**
     * Sets [value] of floating point uniform variable at a given [location] for current program.
     */
    actual fun glUniform(location: Int, value: Float) {
        gles.glUniform1f(location, value)
    }

    /**
     * Sets [value] of 2D vector uniform variable at a given [location] for current program.
     *
     * @since v2.0.0
     */
    @JvmName("glUniform2i")
    actual fun glUniform(location: Int, value: Vec2<Int>) {
        gles.glUniform2i(location, value.x, value.y)
    }

    /**
     * Sets [value] of 2D vector uniform variable at a given [location] for current program.
     */
    @JvmName("glUniform2f")
    actual fun glUniform(location: Int, value: Vec2<Float>) {
        gles.glUniform2f(location, value.x, value.y)
    }

    /**
     * Sets [value] of 3D vector uniform variable at a given [location] for current program.
     *
     * @since v2.0.0
     */
    @JvmName("glUniform3i")
    actual fun glUniform(location: Int, value: Vec3<Int>) {
        gles.glUniform3i(location, value.x, value.y, value.z)
    }

    /**
     * Sets [value] of 3D vector uniform variable at a given [location] for current program.
     */
    @JvmName("glUniform3f")
    actual fun glUniform(location: Int, value: Vec3<Float>) {
        gles.glUniform3f(location, value.x, value.y, value.z)
    }

    /**
     * Sets [value] of 4D vector uniform variable at a given [location] for current program.
     *
     * @since v2.0.0
     */
    @JvmName("glUniform4i")
    actual fun glUniform(location: Int, value: Vec4<Int>) {
        gles.glUniform4i(location, value.x, value.y, value.z, value.w)
    }

    /**
     * Sets [value] of 4D vector uniform variable at a given [location] for current program.
     */
    @JvmName("glUniform4f")
    actual fun glUniform(location: Int, value: Vec4<Float>) {
        gles.glUniform4f(location, value.x, value.y, value.z, value.w)
    }

    /**
     * Sets [values] of boolean array uniform variable at a given [location] for current program.
     *
     * A boolean value is converted to an integer value of `GL_TRUE` or `GL_FALSE`.
     *
     * @since v2.0.0
     */
    actual fun glUniform(location: Int, vararg values: Boolean) {
        gles.glUniform1iv(location, values.size, values.map { value -> value.toInt() }.toIntArray(), 0)
    }

    /**
     * Sets [values] of integer array uniform variable at a given [location] for current program.
     */
    actual fun glUniform(location: Int, vararg values: Int) {
        gles.glUniform1iv(location, values.size, values, 0)
    }

    /**
     * Sets [values] of floating point array uniform variable at a given [location] for current program.
     */
    actual fun glUniform(location: Int, vararg values: Float) {
        gles.glUniform1fv(location, values.size, values, 0)
    }

    /**
     * Sets [values] of 2D vector array uniform variable at a given [location] for current program.
     *
     * @since v2.0.0
     */
    @JvmName("glUniform2iv")
    actual fun glUniform(location: Int, vararg values: Vec2<Int>) {
        gles.glUniform2iv(location, values.size, values.flatMap { it.toList() }.toIntArray(), 0)
    }

    /**
     * Sets [values] of 2D vector array uniform variable at a given [location] for current program.
     */
    @JvmName("glUniform2fv")
    actual fun glUniform(location: Int, vararg values: Vec2<Float>) {
        gles.glUniform2fv(location, values.size, values.flatMap { it.toList() }.toFloatArray(), 0)
    }

    /**
     * Sets [values] of 3D vector array uniform variable at a given [location] for current program.
     *
     * @since v2.0.0
     */
    @JvmName("glUniform3iv")
    actual fun glUniform(location: Int, vararg values: Vec3<Int>) {
        gles.glUniform3iv(location, values.size, values.flatMap { it.toList() }.toIntArray(), 0)
    }

    /**
     * Sets [values] of 3D vector array uniform variable at a given [location] for current program.
     */
    @JvmName("glUniform3fv")
    actual fun glUniform(location: Int, vararg values: Vec3<Float>) {
        gles.glUniform3fv(location, values.size, values.flatMap { it.toList() }.toFloatArray(), 0)
    }

    /**
     * Sets [values] of 4D vector array uniform variable at a given [location] for current program.
     *
     * @since v2.0.0
     */
    @JvmName("glUniform4iv")
    actual fun glUniform(location: Int, vararg values: Vec4<Int>) {
        gles.glUniform4iv(location, values.size, values.flatMap { it.toList() }.toIntArray(), 0)
    }

    /**
     * Sets [values] of 4D vector array uniform variable at a given [location] for current program.
     */
    @JvmName("glUniform4fv")
    actual fun glUniform(location: Int, vararg values: Vec4<Float>) {
        gles.glUniform4fv(location, values.size, values.flatMap { it.toList() }.toFloatArray(), 0)
    }

    /**
     * Sets [values] of 2×2 matrix array uniform variable at a given [location] for current program.
     *
     * Optionally, if the [transpose] flag is set to `true`, transpose matrices will be set instead.
     */
    actual fun glUniform(location: Int, vararg values: Mat2<Float>, transpose: Boolean) {
        gles.glUniformMatrix2fv(
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
    actual fun glUniform(location: Int, vararg values: Mat3<Float>, transpose: Boolean) {
        gles.glUniformMatrix3fv(
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
    actual fun glUniform(location: Int, vararg values: Mat4<Float>, transpose: Boolean) {
        gles.glUniformMatrix4fv(
            location,
            values.size,
            transpose,
            values.flatMap { it.elements }.toFloatArray(),
            0
        )
    }

    /**
     * Sets integer [buffer] uniform variable at a given [location] for current program.
     *
     * @since v2.0.0
     */
    actual fun glUniform(location: Int, buffer: IntUniformBuffer) {
        val elementSize = buffer.elementType.size * Int.SIZE_BYTES
        val elementsCount = buffer.data.sizeInBytes / elementSize
        when (buffer.elementType) {
            UniformBufferDataElementType.NUMBER ->
                gles.glUniform1iv(location, elementsCount, buffer.data.nioBuffer)
            UniformBufferDataElementType.VEC2 ->
                gles.glUniform2iv(location, elementsCount, buffer.data.nioBuffer)
            UniformBufferDataElementType.VEC3 ->
                gles.glUniform3iv(location, elementsCount, buffer.data.nioBuffer)
            UniformBufferDataElementType.VEC4 ->
                gles.glUniform4iv(location, elementsCount, buffer.data.nioBuffer)
            else -> throw UnsupportedOperationException(
                "Matrix elements of uniform buffer data not supported for integer numbers"
            )
        }
    }

    /**
     * Sets floating point [buffer] uniform variable at a given [location] for current program.
     *
     * @since v2.0.0
     */
    actual fun glUniform(location: Int, buffer: FloatUniformBuffer) {
        val elementSize = buffer.elementType.size * Float.SIZE_BYTES
        val elementsCount = buffer.data.sizeInBytes / elementSize
        when (buffer.elementType) {
            UniformBufferDataElementType.NUMBER ->
                gles.glUniform1fv(location, elementsCount, buffer.data.nioBuffer)
            UniformBufferDataElementType.VEC2 ->
                gles.glUniform2fv(location, elementsCount, buffer.data.nioBuffer)
            UniformBufferDataElementType.VEC3 ->
                gles.glUniform3fv(location, elementsCount, buffer.data.nioBuffer)
            UniformBufferDataElementType.VEC4 ->
                gles.glUniform4fv(location, elementsCount, buffer.data.nioBuffer)
            UniformBufferDataElementType.MAT2 ->
                gles.glUniformMatrix2fv(location, elementsCount, false, buffer.data.nioBuffer)
            UniformBufferDataElementType.MAT3 ->
                gles.glUniformMatrix3fv(location, elementsCount, false, buffer.data.nioBuffer)
            UniformBufferDataElementType.MAT4 ->
                gles.glUniformMatrix4fv(location, elementsCount, false, buffer.data.nioBuffer)
        }
    }

    /**
     * Enables vertex attributes array at a given [location] for current program.
     */
    actual fun glEnableVertexAttribArray(location: Int) {
        gles.glEnableVertexAttribArray(location)
    }

    /**
     * Disables vertex attributes array at a given [location] for current program.
     */
    actual fun glDisableVertexAttribArray(location: Int) {
        gles.glDisableVertexAttribArray(location)
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
        gles.glVertexAttribPointer(
            location,
            vectorSize,
            GL2ES2.GL_FLOAT,
            normalized,
            stride,
            offset.toLong()
        )
    }

    /**
     * Draws a given [number][count] of primitives of type specified by [mode],
     * starting with [offset].
     */
    actual fun glDrawArrays(mode: DrawingMode, count: Int, offset: Int) {
        gles.glDrawArrays(mode.toInt(), offset, count)
    }

    private fun DrawingMode.toInt(): Int = when (this) {
        DrawingMode.POINTS -> GL2ES2.GL_POINTS
        DrawingMode.LINES -> GL2ES2.GL_LINES
        DrawingMode.LINE_LOOP -> GL2ES2.GL_LINE_LOOP
        DrawingMode.LINE_STRIP -> GL2ES2.GL_LINE_STRIP
        DrawingMode.TRIANGLES -> GL2ES2.GL_TRIANGLES
        DrawingMode.TRIANGLE_STRIP -> GL2ES2.GL_TRIANGLE_STRIP
        DrawingMode.TRIANGLE_FAN -> GL2ES2.GL_TRIANGLE_FAN
    }

    /**
     * Draws a given [number][count] of primitives of type specified by [mode],
     * using a buffer of vertex array indices, starting with [offset].
     */
    actual fun glDrawElements(mode: DrawingMode, count: Int, offset: Int) {
        gles.glDrawElements(mode.toInt(), count, GL2ES2.GL_UNSIGNED_INT, offset.toLong())
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
        gles.glReadPixels(x, y, width, height, format.toInt(), GL2ES2.GL_UNSIGNED_BYTE, buffer)
        val output = ByteArray(size = bytesCount)
        buffer.rewind()
        buffer.get(output)
        return output
    }

    private fun PixelFormat.toInt(): Int = when (this) {
        PixelFormat.ALPHA -> GL2ES2.GL_ALPHA
        PixelFormat.RGB -> GL2ES2.GL_RGB
        PixelFormat.RGBA -> GL2ES2.GL_RGBA
    }
}
