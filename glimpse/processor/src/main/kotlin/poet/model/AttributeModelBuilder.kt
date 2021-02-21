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
import javax.lang.model.element.VariableElement
import javax.lang.model.util.SimpleAnnotationValueVisitor8
import javax.tools.Diagnostic

class AttributeModelBuilder(
    annotationMirror: AnnotationMirror,
    private val messager: Messager
) : SimpleAnnotationValueVisitor8<Unit, String>() {

    private var name: String = ""
    private var arrayBufferIndex: Int = NO_BUFFER_INDEX
    private var vectorSize: Int = NO_VECTOR_SIZE
    private val comment = annotationMirror.toString()

    override fun visitString(stringValue: String, param: String) {
        if (param == "name") {
            messager.printMessage(Diagnostic.Kind.NOTE, "Attribute name: $stringValue")
            name = stringValue
        }
    }

    override fun visitEnumConstant(enumValue: VariableElement, param: String) {
        if (param == "role") {
            messager.printMessage(Diagnostic.Kind.NOTE, "Attribute role: $enumValue")
            arrayBufferIndex = when (enumValue.simpleName.toString()) {
                "POSITIONS" -> POSITIONS_BUFFER_INDEX
                "TEX_COORDS" -> TEX_COORDS_BUFFER_INDEX
                "NORMALS" -> NORMALS_BUFFER_INDEX
                "TANGENTS" -> TANGENTS_BUFFER_INDEX
                else -> NO_BUFFER_INDEX
            }
        }
    }

    override fun visitInt(intValue: Int, param: String) {
        if (param == "vectorSize") {
            messager.printMessage(Diagnostic.Kind.NOTE, "Attribute vector size: $intValue")
            vectorSize = intValue
        }
    }

    fun build(): AttributeModel = AttributeModel(name, arrayBufferIndex, vectorSize, comment)

    companion object {
        private const val NO_BUFFER_INDEX = -1
        private const val NO_VECTOR_SIZE = -1

        private const val POSITIONS_BUFFER_INDEX = 0
        private const val TEX_COORDS_BUFFER_INDEX = 1
        private const val NORMALS_BUFFER_INDEX = 2
        private const val TANGENTS_BUFFER_INDEX = 3
    }
}
