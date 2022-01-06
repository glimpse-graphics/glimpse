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

package graphics.glimpse.ksp

import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider

/**
 * Provider of KSP processor generating concrete implementations of ProgramExecutor in Kotlin.
 */
class ShaderParamsSymbolProcessorProvider : SymbolProcessorProvider {

    /**
     * Creates a new instance of [ShaderParamsSymbolProcessor].
     */
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor =
        ShaderParamsSymbolProcessor(environment.codeGenerator, environment.logger)
}
