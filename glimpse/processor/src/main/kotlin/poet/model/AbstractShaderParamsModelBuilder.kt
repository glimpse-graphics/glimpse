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

package graphics.glimpse.processor.poet.model

import javax.annotation.processing.Messager
import javax.lang.model.element.AnnotationMirror
import javax.lang.model.element.AnnotationValue
import javax.lang.model.element.Element
import javax.lang.model.type.TypeMirror
import javax.lang.model.util.SimpleAnnotationValueVisitor8
import javax.tools.Diagnostic

abstract class AbstractShaderParamsModelBuilder(
    private val classNameMatcher: ClassNameMatcher,
    private val messager: Messager
) : SimpleAnnotationValueVisitor8<Unit, Element>() {

    protected val uniforms = mutableListOf<UniformModel>()
    protected val attributes = mutableListOf<AttributeModel>()

    override fun visitAnnotation(annotation: AnnotationMirror, param: Element) {
        val annotationType = annotation.annotationType
        when {
            isShaderParams(annotationType) -> visitShaderParams(annotation, param)
            isAttribute(annotationType) -> visitAttribute(annotation)
            isUniform(annotationType) -> visitUniform(annotation, param)
        }
    }

    private fun isShaderParams(type: TypeMirror) =
        classNameMatcher.isEqualTo(
            typeMirror = type,
            packageName = "graphics.glimpse.shaders.annotations",
            simpleName = "ShaderParams"
        )

    private fun visitShaderParams(annotation: AnnotationMirror, param: Element) {
        messager.printMessage(Diagnostic.Kind.NOTE, "Processing shader params: $annotation\n")
        annotation.elementValues.forEach { (_, value) ->
            value.accept(this, param)
        }
    }

    private fun isAttribute(type: TypeMirror) =
        classNameMatcher.isEqualTo(
            typeMirror = type,
            packageName = "graphics.glimpse.shaders.annotations",
            simpleName = "Attribute"
        )

    private fun visitAttribute(annotation: AnnotationMirror) {
        messager.printMessage(Diagnostic.Kind.NOTE, "Processing attribute: $annotation")
        val attributeModelBuilder = AttributeModelBuilder(annotation, messager)
        annotation.elementValues.forEach { (key, value) ->
            value.accept(attributeModelBuilder, key.simpleName.toString())
        }
        attributes.add(attributeModelBuilder.build())
    }

    private fun isUniform(type: TypeMirror) =
        classNameMatcher.isEqualTo(
            typeMirror = type,
            packageName = "graphics.glimpse.shaders.annotations",
            simpleName = "Uniform"
        )

    private fun visitUniform(annotation: AnnotationMirror, param: Element) {
        messager.printMessage(Diagnostic.Kind.NOTE, "Processing uniform: $annotation")
        val uniformModelBuilder = UniformModelBuilder(annotation, param, classNameMatcher, messager)
        annotation.elementValues.forEach { (key, value) ->
            value.accept(uniformModelBuilder, key.simpleName.toString())
        }
        uniforms.add(uniformModelBuilder.build())
    }

    override fun visitArray(values: MutableList<out AnnotationValue>, param: Element) {
        values.forEach { value ->
            value.accept(this, param)
        }
    }

    abstract fun build(): ShaderParamsModel
}
