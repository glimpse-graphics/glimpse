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
 */

package graphics.glimpse.ksp

import com.squareup.kotlinpoet.FunSpec

/**
 * Visitor adding finalization of `@Attribute`s to `drawMesh` method.
 */
class FinalizeAttributesVisitor : AttributesVisitor() {

    override fun FunSpec.Builder.addAttributeStatements(attribute: Attribute): FunSpec.Builder {
        addStatement(STATEMENT_FORMAT_DISABLE_ATTRIBUTE, attributeLocation(attribute))
        return this
    }

    companion object {
        private const val STATEMENT_FORMAT_DISABLE_ATTRIBUTE = "gl.glDisableVertexAttribArray(%L)"
    }
}
