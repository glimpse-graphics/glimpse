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

package graphics.glimpse.processor

import graphics.glimpse.processor.poet.ProgramExecutorPoet
import graphics.glimpse.processor.poet.model.KotlinClassNameMatcher
import graphics.glimpse.processor.poet.model.ShaderParamsModelBuilder
import javax.annotation.processing.SupportedSourceVersion
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.tools.Diagnostic

/**
 * Annotation processor generating concrete implementations of ProgramExecutor in Kotlin.
 */
@SupportedSourceVersion(SourceVersion.RELEASE_11)
class ShaderParamsProcessor : AbstractShaderParamsProcessor() {

    override fun processShaderParams(element: Element) {
        val outputDir = requireNotNull(processingEnv.options[OPTION_KAPT_OUTPUT_DIR]) {
            "Output Kapt directory for Kotlin not defined"
        }
        println(processingEnv.options)
        processingEnv.messager.printMessage(
            Diagnostic.Kind.NOTE,
            "Output directory: $outputDir"
        )
        val shaderParamsModel = buildShaderParamsModel(
            element = element,
            builder = ShaderParamsModelBuilder(
                element = element,
                classNameMatcher = KotlinClassNameMatcher(),
                messager = processingEnv.messager
            )
        )
        ProgramExecutorPoet.generateRenderer(shaderParamsModel, outputDir)
    }

    companion object {
        private const val OPTION_KAPT_OUTPUT_DIR = "kapt.kotlin.generated"
    }
}
