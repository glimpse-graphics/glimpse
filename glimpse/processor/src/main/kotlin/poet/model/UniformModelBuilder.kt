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
import javax.lang.model.element.Element
import javax.lang.model.type.ExecutableType
import javax.lang.model.type.TypeKind
import javax.lang.model.type.TypeMirror
import javax.lang.model.util.SimpleAnnotationValueVisitor8
import javax.tools.Diagnostic

class UniformModelBuilder(
    annotationMirror: AnnotationMirror,
    element: Element,
    private val classNameMatcher: ClassNameMatcher,
    private val messager: Messager
) : SimpleAnnotationValueVisitor8<Unit, String>() {

    private val fieldName: String =
        element.simpleName.toString().removeSuffix(suffix = "\$annotations")

    private var name: String = ""

    private val type: UniformModel.Type =
        try {
            var type = element.asType()
            if (type.kind == TypeKind.EXECUTABLE) {
                type = (type as ExecutableType).returnType
            }
            messager.printMessage(Diagnostic.Kind.NOTE, "Uniform type: $type")
            if (isTexture(type)) UniformModel.Type.TEXTURE
            else UniformModel.Type.VALUE
        } catch (expected: Exception) {
            messager.printMessage(Diagnostic.Kind.WARNING, "Error getting element type: $element")
            UniformModel.Type.VALUE
        }

    private fun isTexture(type: TypeMirror) =
        classNameMatcher.isEqualTo(
            typeMirror = type,
            packageName = "graphics.glimpse.textures",
            simpleName = "Texture"
        )

    private val comment = annotationMirror.toString()

    override fun visitString(stringValue: String, param: String) {
        if (param == "name") {
            messager.printMessage(Diagnostic.Kind.NOTE, "Uniform name: $stringValue")
            name = stringValue
        }
    }

    fun build(): UniformModel = UniformModel(fieldName, name, type, comment)
}
