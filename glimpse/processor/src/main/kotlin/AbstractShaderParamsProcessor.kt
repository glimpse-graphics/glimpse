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

import graphics.glimpse.processor.poet.model.AbstractShaderParamsModelBuilder
import graphics.glimpse.processor.poet.model.ShaderParamsModel
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic

abstract class AbstractShaderParamsProcessor : AbstractProcessor() {

    override fun getSupportedAnnotationTypes(): MutableSet<String> =
        mutableSetOf("graphics.glimpse.shaders.annotations.ShaderParams")

    override fun process(
        annotations: MutableSet<out TypeElement>,
        roundEnvironment: RoundEnvironment
    ): Boolean {
        processingEnv.messager.printMessage(
            Diagnostic.Kind.WARNING,
            "Use of Glimpse Kapt processor is discouraged. Consider using KSP processor instead."
        )
        annotations.flatMap { annotation ->
            roundEnvironment.getElementsAnnotatedWith(annotation)
        }.forEach { element ->
            processShaderParams(element)
        }
        return true
    }

    protected abstract fun processShaderParams(element: Element)

    protected fun buildShaderParamsModel(
        element: Element,
        builder: AbstractShaderParamsModelBuilder
    ): ShaderParamsModel {
        element.annotationMirrors.forEach { annotationMirror ->
            builder.visitAnnotation(annotationMirror, element)
        }
        element.enclosedElements.forEach { enclosedElement ->
            val annotatedElement = element.enclosedElements.find {
                it.simpleName.toString() == enclosedElement.simpleName.toString()
                    .removeSuffix(suffix = "\$annotations")
            }
            enclosedElement.annotationMirrors.forEach { annotationMirror ->
                builder.visitAnnotation(annotationMirror, annotatedElement ?: enclosedElement)
            }
        }
        return builder.build()
    }
}
