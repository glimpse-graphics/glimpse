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

package graphics.glimpse.shaders

import graphics.glimpse.GlimpseAdapter
import graphics.glimpse.types.Mat2
import graphics.glimpse.types.Mat3
import graphics.glimpse.types.Mat4
import graphics.glimpse.types.Vec2
import graphics.glimpse.types.Vec3
import graphics.glimpse.types.Vec4

/**
 * Base for generated [ProgramExecutor] implementations.
 */
abstract class BaseProgramExecutor<T>(
    private val program: Program
) : ProgramExecutor<T> {

    private val uniformLocations = mutableMapOf<String, Int>()
    private val attributeLocations = mutableMapOf<String, Int>()

    /**
     * Tells the given [OpenGL adapter][gl] to use the [program] contained in the executor.
     */
    override fun useProgram(gl: GlimpseAdapter) {
        program.use(gl)
    }

    /**
     * Returns location of uniform variable with a given [name]
     * from the [program] contained in the executor.
     */
    protected fun getUniformLocation(gl: GlimpseAdapter, name: String): Int =
        uniformLocations.getOrPut(name) {
            gl.glGetUniformLocation(program.handle, name)
        }

    /**
     * Returns location of attribute variable with a given [name]
     * from the [program] contained in the executor.
     */
    protected fun getAttributeLocation(gl: GlimpseAdapter, name: String): Int =
        attributeLocations.getOrPut(name) {
            gl.glGetAttributeLocation(program.handle, name)
        }

    /**
     * Sets [value] of boolean uniform variable with a given [name] for the [program]
     * contained in the executor.
     *
     * A boolean uniform is converted to an integer value of `GL_TRUE` or `GL_FALSE`.
     *
     * @since v1.1.0
     */
    protected fun glUniform(gl: GlimpseAdapter, name: String, value: Boolean) {
        gl.glUniform(getUniformLocation(gl, name), value)
    }

    /**
     * Sets [value] of integer uniform variable with a given [name] for the [program]
     * contained in the executor.
     */
    protected fun glUniform(gl: GlimpseAdapter, name: String, value: Int) {
        gl.glUniform(getUniformLocation(gl, name), value)
    }

    /**
     * Sets [values] of integer array uniform variable with a given [name] for the [program]
     * contained in the executor.
     */
    protected fun glUniform(gl: GlimpseAdapter, name: String, values: IntArray) {
        @Suppress("SpreadOperator")
        gl.glUniform(getUniformLocation(gl, name), *values)
    }

    /**
     * Sets [value] of floating point uniform variable with a given [name] for the [program]
     * contained in the executor.
     */
    protected fun glUniform(gl: GlimpseAdapter, name: String, value: Float) {
        gl.glUniform(getUniformLocation(gl, name), value)
    }

    /**
     * Sets [values] of floating point array uniform variable with a given [name] for the [program]
     * contained in the executor.
     */
    protected fun glUniform(gl: GlimpseAdapter, name: String, values: FloatArray) {
        @Suppress("SpreadOperator")
        gl.glUniform(getUniformLocation(gl, name), *values)
    }

    /**
     * Sets [value] of 2D vector uniform variable with a given [name] for the [program]
     * contained in the executor.
     */
    protected fun glUniform(gl: GlimpseAdapter, name: String, value: Vec2) {
        gl.glUniform(getUniformLocation(gl, name), value)
    }

    /**
     * Sets [values] of 2D vector array uniform variable with a given [name] for the [program]
     * contained in the executor.
     */
    protected fun glUniform(gl: GlimpseAdapter, name: String, values: Array<Vec2>) {
        @Suppress("SpreadOperator")
        gl.glUniform(getUniformLocation(gl, name), *values)
    }

    /**
     * Sets [value] of 3D vector uniform variable with a given [name] for the [program]
     * contained in the executor.
     */
    protected fun glUniform(gl: GlimpseAdapter, name: String, value: Vec3) {
        gl.glUniform(getUniformLocation(gl, name), value)
    }

    /**
     * Sets [values] of 3D vector array uniform variable with a given [name] for the [program]
     * contained in the executor.
     */
    protected fun glUniform(gl: GlimpseAdapter, name: String, values: Array<Vec3>) {
        @Suppress("SpreadOperator")
        gl.glUniform(getUniformLocation(gl, name), *values)
    }

    /**
     * Sets [value] of 4D vector uniform variable with a given [name] for the [program]
     * contained in the executor.
     */
    protected fun glUniform(gl: GlimpseAdapter, name: String, value: Vec4) {
        gl.glUniform(getUniformLocation(gl, name), value)
    }

    /**
     * Sets [values] of 4D vector array uniform variable with a given [name] for the [program]
     * contained in the executor.
     */
    protected fun glUniform(gl: GlimpseAdapter, name: String, values: Array<Vec4>) {
        @Suppress("SpreadOperator")
        gl.glUniform(getUniformLocation(gl, name), *values)
    }

    /**
     * Sets [value] of 2×2 matrix uniform variable with a given [name] for the [program]
     * contained in the executor.
     */
    protected fun glUniform(gl: GlimpseAdapter, name: String, value: Mat2) {
        gl.glUniform(getUniformLocation(gl, name), value)
    }

    /**
     * Sets [values] of 2×2 matrix array uniform variable with a given [name] for the [program]
     * contained in the executor.
     */
    protected fun glUniform(gl: GlimpseAdapter, name: String, values: Array<Mat2>) {
        @Suppress("SpreadOperator")
        gl.glUniform(getUniformLocation(gl, name), *values)
    }

    /**
     * Sets [value] of 3×3 matrix uniform variable with a given [name] for the [program]
     * contained in the executor.
     */
    protected fun glUniform(gl: GlimpseAdapter, name: String, value: Mat3) {
        gl.glUniform(getUniformLocation(gl, name), value)
    }

    /**
     * Sets [values] of 3×3 matrix array uniform variable with a given [name] for the [program]
     * contained in the executor.
     */
    protected fun glUniform(gl: GlimpseAdapter, name: String, values: Array<Mat3>) {
        @Suppress("SpreadOperator")
        gl.glUniform(getUniformLocation(gl, name), *values)
    }

    /**
     * Sets [value] of 4×4 matrix uniform variable with a given [name] for the [program]
     * contained in the executor.
     */
    protected fun glUniform(gl: GlimpseAdapter, name: String, value: Mat4) {
        gl.glUniform(getUniformLocation(gl, name), value)
    }

    /**
     * Sets [values] of 4×4 matrix array uniform variable with a given [name] for the [program]
     * contained in the executor.
     */
    protected fun glUniform(gl: GlimpseAdapter, name: String, values: Array<Mat4>) {
        @Suppress("SpreadOperator")
        gl.glUniform(getUniformLocation(gl, name), *values)
    }

    /**
     * Sets vertex attributes array at a given [location] for the [program] contained
     * in the executor.
     */
    protected fun glVertexAttribPointer(gl: GlimpseAdapter, location: Int, vectorSize: Int) {
        gl.glVertexAttribPointer(location, vectorSize)
    }

    /**
     * Clears cached locations of variables.
     */
    @Deprecated(message = "Use dispose(GlimpseAdapter) instead")
    override fun dispose() {
        uniformLocations.clear()
        attributeLocations.clear()
    }

    /**
     * Clears cached locations of variables and disposes the program.
     *
     * @since v1.1.0
     */
    override fun dispose(gl: GlimpseAdapter) {
        uniformLocations.clear()
        attributeLocations.clear()
        program.dispose(gl)
    }
}
