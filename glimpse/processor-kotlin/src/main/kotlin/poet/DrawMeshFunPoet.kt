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

package graphics.glimpse.processor.poet

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import graphics.glimpse.processor.poet.model.AttributeModel
import graphics.glimpse.processor.poet.model.ShaderParamsModel

object DrawMeshFunPoet {

    private const val FUNCTION_NAME_DRAW_MESH = "drawMesh"
    private const val PARAM_NAME_GL_ADAPTER = "gl"
    private const val PARAM_NAME_MESH = "mesh"

    private const val STATEMENT_FORMAT_GET_ATTRIBUTE_LOCATION =
        "val %L = getAttributeLocation(gl, %S)"

    private const val STATEMENT_FORMAT_ATTRIBUTE_LOCATION_VAL =
        "%LLocation"

    private const val STATEMENT_FORMAT_USE_MESH_BUFFER =
        "%N.useBuffer(gl, %L)"

    private const val STATEMENT_FORMAT_ENABLE_ATTRIBUTE =
        "gl.glEnableVertexAttribArray(%L)"

    private const val STATEMENT_FORMAT_ATTRIBUTE_POINTER =
        "glVertexAttribPointer(gl, %L, %L)"

    private const val STATEMENT_FORMAT_DISABLE_ATTRIBUTE =
        "gl.glDisableVertexAttribArray(%L)"

    private const val STATEMENT_FORMAT_DRAW_ARRAYS =
        "%N.draw(gl)"

    private const val COMMENT_DRAWING = "Drawing vertices:"
    private const val COMMENT_CLEANUP = "Disabling arrays:"

    private val glimpseAdapterClassName =
        ClassName("graphics.glimpse", "GlimpseAdapter")
    private val meshClassName =
        ClassName("graphics.glimpse.meshes", "Mesh")

    fun generateFun(model: ShaderParamsModel): FunSpec =
        FunSpec.builder(FUNCTION_NAME_DRAW_MESH)
            .addModifiers(KModifier.OVERRIDE)
            .addParameter(PARAM_NAME_GL_ADAPTER, glimpseAdapterClassName)
            .addParameter(PARAM_NAME_MESH, meshClassName)
            .generateStatements(model.attributes)
            .build()

    private fun FunSpec.Builder.generateStatements(
        attributes: List<AttributeModel>
    ): FunSpec.Builder {
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

    private fun FunSpec.Builder.addAttributeStatement(
        attribute: AttributeModel
    ): FunSpec.Builder {
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

    private fun FunSpec.Builder.addEmptyLine() {
        addCode("\n")
    }
}
