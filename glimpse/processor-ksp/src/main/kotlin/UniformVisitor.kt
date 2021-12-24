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

import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSNode
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.google.devtools.ksp.symbol.KSTypeReference
import com.google.devtools.ksp.symbol.KSValueArgument
import com.google.devtools.ksp.visitor.KSTopDownVisitor
import com.squareup.kotlinpoet.FunSpec
import graphics.glimpse.ksp.poet.addEmptyLine

/**
 * Visitor adding properties annotated as `@Uniform` to `applyParams` method.
 */
class UniformVisitor : KSTopDownVisitor<FunSpec.Builder, FunSpec.Builder>() {

    var textureIndex = 0

    override fun defaultHandler(node: KSNode, data: FunSpec.Builder): FunSpec.Builder = data

    override fun visitPropertyDeclaration(property: KSPropertyDeclaration, data: FunSpec.Builder): FunSpec.Builder {
        val annotation = property.annotations.singleOrNull(::isUniform)
        val uniformName = annotation?.arguments?.singleOrNull(::isName)?.value?.toString()
        val propertyName = property.simpleName.asString()
        val propertyType = property.type

        return super.visitPropertyDeclaration(property, data).apply {
            if (uniformName != null) {
                addEmptyLine()
                addComment(COMMENT_FORMAT_UNIFORM, annotation, uniformName)
                if (propertyType.isTexture()) addStatementTexture(uniformName, propertyName, textureIndex++)
                else addUniformStatement(uniformName, propertyName)
            }
        }
    }

    private fun isUniform(annotation: KSAnnotation): Boolean =
        annotation.shortName.asString() == ANNOTATION_NAME_UNIFORM

    private fun isName(argument: KSValueArgument): Boolean =
        argument.name?.asString() == ARGUMENT_NAME_NAME

    private fun KSTypeReference.isTexture(): Boolean =
        toString() == TYPE_NAME_TEXTURE

    private fun FunSpec.Builder.addStatementTexture(
        uniformName: String,
        propertyName: String,
        textureIndex: Int
    ): FunSpec.Builder {
        addStatement(
            STATEMENT_FORMAT_USE_TEXTURE,
            PARAM_NAME_PARAMS, propertyName, FUNCTION_NAME_HANDLE, textureIndex
        )
        addStatement(
            STATEMENT_FORMAT_UNIFORM_TEXTURE,
            uniformName, textureIndex
        )
        return this
    }

    private fun FunSpec.Builder.addUniformStatement(
        uniformName: String,
        propertyName: String
    ): FunSpec.Builder {
        addStatement(
            STATEMENT_FORMAT_UNIFORM,
            uniformName, PARAM_NAME_PARAMS, propertyName
        )
        return this
    }

    companion object {
        private const val ANNOTATION_NAME_UNIFORM = "Uniform"
        private const val ARGUMENT_NAME_NAME = "name"
        private const val TYPE_NAME_TEXTURE = "Texture"

        private const val FUNCTION_NAME_HANDLE = "useAtIndex"
        private const val PARAM_NAME_PARAMS = "shaderParams"

        private const val COMMENT_FORMAT_UNIFORM = "%L(name = %S)"
        private const val STATEMENT_FORMAT_UNIFORM = "glUniform(gl, name = %S, %N.%N)"
        private const val STATEMENT_FORMAT_USE_TEXTURE = "%N.%N.%N(gl, textureIndex = %L)"
        private const val STATEMENT_FORMAT_UNIFORM_TEXTURE = "glUniform(gl, name = %S, value = %L)"
    }
}
