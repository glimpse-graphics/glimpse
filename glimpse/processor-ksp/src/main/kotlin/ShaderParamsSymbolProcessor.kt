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

package graphics.glimpse.ksp

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated

/**
 * KSP processor generating concrete implementations of ProgramExecutor in Kotlin.
 */
class ShaderParamsSymbolProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger
) : SymbolProcessor {

    /**
     * Processes classes annotated with `ShaderParams` annotation.
     */
    override fun process(resolver: Resolver): List<KSAnnotated> {
        val annotatedSymbols = resolver.getSymbolsWithAnnotation(ANNOTATION_NAME)
        val annotatedClassVisitor = ShaderParamsClassVisitor()

        for (annotatedSymbol in annotatedSymbols) {
            logger.info("Processing shader parameters", annotatedSymbol)
            try {
                annotatedSymbol.accept(annotatedClassVisitor, codeGenerator)
            } catch (kspException: KSPException) {
                logger.error(kspException.message ?: "Error processing shader parameters", kspException.symbol)
            } catch (expected: Throwable) {
                logger.error(expected.message ?: "Error processing shader parameters", annotatedSymbol)
            }
        }

        return emptyList()
    }

    companion object {
        private const val ANNOTATION_NAME = "graphics.glimpse.shaders.annotations.ShaderParams"
    }
}
