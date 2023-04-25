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

package graphics.glimpse.examples.triangle

import graphics.glimpse.shaders.Program
import graphics.glimpse.shaders.ProgramExecutor

/**
 * Creates a program executor for [TriangleShader].
 */
expect fun Program.createTriangleShaderProgramExecutor(): ProgramExecutor<TriangleShader>

/**
 * Creates a program executor for [BlurXShader].
 */
expect fun Program.createBlurXShaderProgramExecutor(): ProgramExecutor<BlurXShader>

/**
 * Creates a program executor for [BlurYShader].
 */
expect fun Program.createBlurYShaderProgramExecutor(): ProgramExecutor<BlurYShader>
