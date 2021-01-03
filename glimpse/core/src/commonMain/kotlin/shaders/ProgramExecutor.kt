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

package graphics.glimpse.shaders

import graphics.glimpse.GlimpseAdapter
import graphics.glimpse.meshes.Mesh
import graphics.glimpse.shaders.annotations.ShaderParams

/**
 * An interface implemented by program executors.
 *
 * Program executors are not intended to be implemented manually. Instead, they should be generated
 * from classes annotated with [ShaderParams].
 */
interface ProgramExecutor<T> {

    /**
     * Tells the given [OpenGL adapter][gl] to use the program contained in the executor.
     */
    fun useProgram(gl: GlimpseAdapter)

    /**
     * Applies shader parameters from an instance of [ShaderParams]-annotated type [T].
     */
    fun applyParams(gl: GlimpseAdapter, shaderParams: T)

    /**
     * Draws a given [mesh] using the program contained in the executor.
     */
    fun drawMesh(gl: GlimpseAdapter, mesh: Mesh)

    /**
     * Disposes the program executor.
     */
    fun dispose()
}
