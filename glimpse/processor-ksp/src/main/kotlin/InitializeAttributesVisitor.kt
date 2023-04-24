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

import com.squareup.kotlinpoet.FunSpec
import graphics.glimpse.ksp.poet.addEmptyLine

/**
 * Visitor adding initialization of `@Attribute`s to `drawMesh` method.
 */
class InitializeAttributesVisitor : AttributesVisitor() {

    override fun FunSpec.Builder.addAttributeStatements(attribute: Attribute): FunSpec.Builder {
        addStatement(STATEMENT_FORMAT_GET_ATTRIBUTE_LOCATION, attributeLocation(attribute), attribute.name)
        addStatement(STATEMENT_FORMAT_USE_MESH_BUFFER, attribute.role.toString())
        addStatement(STATEMENT_FORMAT_ENABLE_ATTRIBUTE, attributeLocation(attribute))
        addStatement(STATEMENT_FORMAT_ATTRIBUTE_POINTER, attributeLocation(attribute), attribute.vectorSize)
        addEmptyLine()
        return this
    }

    companion object {
        private const val STATEMENT_FORMAT_GET_ATTRIBUTE_LOCATION = "val %L = getAttributeLocation(gl, %S)"
        private const val STATEMENT_FORMAT_USE_MESH_BUFFER = "mesh.useBuffer(gl, %L.ordinal)"
        private const val STATEMENT_FORMAT_ENABLE_ATTRIBUTE = "gl.glEnableVertexAttribArray(%L)"
        private const val STATEMENT_FORMAT_ATTRIBUTE_POINTER = "glVertexAttribPointer(gl, %L, %L)"
    }
}
