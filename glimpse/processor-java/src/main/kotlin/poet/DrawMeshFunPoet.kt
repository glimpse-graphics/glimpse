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

package graphics.glimpse.processor.poet

import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.MethodSpec
import graphics.glimpse.processor.poet.model.AttributeModel
import graphics.glimpse.processor.poet.model.ShaderParamsModel
import javax.lang.model.element.Modifier

object DrawMeshFunPoet {

    private const val FUNCTION_NAME_DRAW_MESH = "drawMesh"
    private const val PARAM_NAME_GL_ADAPTER = "gl"
    private const val PARAM_NAME_MESH = "mesh"

    private const val STATEMENT_FORMAT_GET_ATTRIBUTE_LOCATION =
        "int \$L = getAttributeLocation(gl, \$S)"

    private const val STATEMENT_FORMAT_ATTRIBUTE_LOCATION_VAL =
        "\$LLocation"

    private const val STATEMENT_FORMAT_USE_MESH_BUFFER =
        "\$N.useBuffer(gl, \$L)"

    private const val STATEMENT_FORMAT_ENABLE_ATTRIBUTE =
        "gl.glEnableVertexAttribArray(\$L)"

    private const val STATEMENT_FORMAT_ATTRIBUTE_POINTER =
        "glVertexAttribPointer(gl, \$L, \$L)"

    private const val STATEMENT_FORMAT_DISABLE_ATTRIBUTE =
        "gl.glDisableVertexAttribArray(\$L)"

    private const val STATEMENT_FORMAT_DRAW_ARRAYS =
        "\$N.draw(gl)"

    private const val COMMENT_DRAWING = "Drawing vertices:"
    private const val COMMENT_CLEANUP = "Disabling arrays:"

    private val glimpseAdapterClassName =
        ClassName.get("graphics.glimpse", "GlimpseAdapter")
    private val meshClassName =
        ClassName.get("graphics.glimpse.meshes", "Mesh")

    fun generateFun(model: ShaderParamsModel): MethodSpec =
        MethodSpec.methodBuilder(FUNCTION_NAME_DRAW_MESH)
            .addAnnotation(Override::class.java)
            .addModifiers(Modifier.PUBLIC)
            .addParameter(glimpseAdapterClassName, PARAM_NAME_GL_ADAPTER)
            .addParameter(meshClassName, PARAM_NAME_MESH)
            .generateStatements(model.attributes)
            .build()

    private fun MethodSpec.Builder.generateStatements(
        attributes: List<AttributeModel>
    ): MethodSpec.Builder {
        for (attribute in attributes) {
            addComment(attribute.comment)
            addAttributeStatement(attribute)
            addEmptyLine()
        }
        addComment(COMMENT_DRAWING)
        addStatement(
            STATEMENT_FORMAT_DRAW_ARRAYS,
            PARAM_NAME_MESH
        )
        addEmptyLine()
        addComment(COMMENT_CLEANUP)
        for (attribute in attributes) {
            addStatement(STATEMENT_FORMAT_DISABLE_ATTRIBUTE, attributeLocation(attribute))
        }
        return this
    }

    private fun MethodSpec.Builder.addAttributeStatement(
        attribute: AttributeModel
    ): MethodSpec.Builder {
        addStatement(STATEMENT_FORMAT_GET_ATTRIBUTE_LOCATION, attributeLocation(attribute), attribute.name)
        addStatement(STATEMENT_FORMAT_USE_MESH_BUFFER, PARAM_NAME_MESH, attribute.arrayBufferIndex)
        addStatement(STATEMENT_FORMAT_ENABLE_ATTRIBUTE, attributeLocation(attribute))
        addStatement(
            STATEMENT_FORMAT_ATTRIBUTE_POINTER,
            attributeLocation(attribute),
            attribute.vectorSize
        )
        return this
    }

    private fun attributeLocation(attribute: AttributeModel): CodeBlock =
        CodeBlock.of(STATEMENT_FORMAT_ATTRIBUTE_LOCATION_VAL, attribute.name)

    private fun MethodSpec.Builder.addEmptyLine() {
        addCode("\n")
    }
}
