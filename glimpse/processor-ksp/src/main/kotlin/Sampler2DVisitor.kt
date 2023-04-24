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

import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSNode
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.google.devtools.ksp.symbol.KSValueArgument
import com.google.devtools.ksp.visitor.KSTopDownVisitor
import com.squareup.kotlinpoet.FunSpec
import graphics.glimpse.ksp.poet.addEmptyLine

/**
 * Visitor adding properties annotated as `@Sampler2D` to `applyParams` method.
 */
class Sampler2DVisitor : KSTopDownVisitor<FunSpec.Builder, FunSpec.Builder>() {

    private var textureIndex = 0

    override fun defaultHandler(node: KSNode, data: FunSpec.Builder): FunSpec.Builder = data

    override fun visitPropertyDeclaration(property: KSPropertyDeclaration, data: FunSpec.Builder): FunSpec.Builder {
        val annotation = property.annotations.singleOrNull(::isSampler2D)
        val uniformName = annotation?.arguments?.singleOrNull(::isName)?.value?.toString()
        val arraySize = annotation?.arguments?.singleOrNull(::isSize)?.value?.toString()?.toInt()
        val propertyName = property.simpleName.asString()

        return super.visitPropertyDeclaration(property, data).apply {
            if (uniformName != null && arraySize != null) {
                addEmptyLine()
                addComment(COMMENT_FORMAT_SAMPLER2D, annotation, uniformName, arraySize)
                if (arraySize < 0) {
                    addTextureStatements(uniformName, propertyName, textureIndex++)
                } else {
                    addTexturesListStatements(uniformName, propertyName, textureIndex, textureIndex + arraySize)
                    textureIndex += arraySize
                }
            }
        }
    }

    private fun isSampler2D(annotation: KSAnnotation): Boolean =
        annotation.shortName.asString() == ANNOTATION_NAME_SAMPLER2D

    private fun isName(argument: KSValueArgument): Boolean =
        argument.name?.asString() == ARGUMENT_NAME_NAME

    private fun isSize(argument: KSValueArgument): Boolean =
        argument.name?.asString() == ARGUMENT_NAME_SIZE

    private fun FunSpec.Builder.addTextureStatements(
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

    private fun FunSpec.Builder.addTexturesListStatements(
        uniformName: String,
        propertyName: String,
        startTextureIndex: Int,
        endTextureIndex: Int
    ): FunSpec.Builder {
        for ((index, textureIndex) in (startTextureIndex until endTextureIndex).withIndex()) {
            addStatement(
                STATEMENT_FORMAT_USE_TEXTURE_FROM_LIST,
                PARAM_NAME_PARAMS, propertyName, index, FUNCTION_NAME_HANDLE, textureIndex
            )
        }
        addStatement(
            STATEMENT_FORMAT_UNIFORM_TEXTURES_LIST,
            uniformName, startTextureIndex, endTextureIndex
        )
        return this
    }

    companion object {
        private const val ANNOTATION_NAME_SAMPLER2D = "Sampler2D"
        private const val ARGUMENT_NAME_NAME = "name"
        private const val ARGUMENT_NAME_SIZE = "size"

        private const val FUNCTION_NAME_HANDLE = "useAtIndex"
        private const val PARAM_NAME_PARAMS = "shaderParams"

        private const val COMMENT_FORMAT_SAMPLER2D = "%L(name = %S, size = %L)"
        private const val STATEMENT_FORMAT_USE_TEXTURE = "%N.%N.%N(gl, textureIndex = %L)"
        private const val STATEMENT_FORMAT_UNIFORM_TEXTURE = "glUniform(gl, name = %S, value = %L)"
        private const val STATEMENT_FORMAT_USE_TEXTURE_FROM_LIST = "%N.%N[%L].%N(gl, textureIndex = %L)"
        private const val STATEMENT_FORMAT_UNIFORM_TEXTURES_LIST =
            "glUniform(gl, name = %S, values = (%L until %L).toList().toIntArray())"
    }
}
