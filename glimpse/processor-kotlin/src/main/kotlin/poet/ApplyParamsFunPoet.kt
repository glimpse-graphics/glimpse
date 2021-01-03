/*
 * Copyright 2020 Slawomir Czerwinski
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
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import graphics.glimpse.processor.poet.model.ShaderParamsModel
import graphics.glimpse.processor.poet.model.UniformModel
import graphics.glimpse.processor.poet.model.annotatedClassName
import graphics.glimpse.processor.poet.model.propertyName

@Suppress("TooManyFunctions")
object ApplyParamsFunPoet {

    private const val FUNCTION_NAME = "applyParams"
    private const val FUNCTION_NAME_HANDLE = "useAtIndex"
    private const val PARAM_NAME_GL_ADAPTER = "gl"
    private const val PARAM_NAME_PARAMS = "shaderParams"

    private const val STATEMENT_FORMAT_UNIFORM = "glUniform(gl, %S, %N.%N)"
    private const val STATEMENT_FORMAT_USE_TEXTURE = "%N.%N.%N(gl, %L)"
    private const val STATEMENT_FORMAT_UNIFORM_TEXTURE = "glUniform(gl, %S, %L)"

    private val glimpseAdapterClassName =
        ClassName("graphics.glimpse", "GlimpseAdapter")

    fun generateFun(model: ShaderParamsModel): FunSpec =
        FunSpec.builder(FUNCTION_NAME)
            .addModifiers(KModifier.OVERRIDE)
            .addParameter(PARAM_NAME_GL_ADAPTER, glimpseAdapterClassName)
            .addParameter(PARAM_NAME_PARAMS, model.annotatedClassName)
            .generateStatements(model.uniforms)
            .build()

    private fun FunSpec.Builder.generateStatements(
        uniforms: List<UniformModel>
    ): FunSpec.Builder {
        var textureIndex = 0
        for (uniform in uniforms) {
            addEmptyLine()
            addComment(uniform.comment)
            when (uniform.type) {
                UniformModel.Type.VALUE -> addStatement(uniform)
                UniformModel.Type.TEXTURE -> addStatementTexture(uniform, textureIndex++)
            }
        }
        return this
    }

    private fun FunSpec.Builder.addStatementTexture(
        uniform: UniformModel,
        textureIndex: Int
    ): FunSpec.Builder {
        addStatement(
            STATEMENT_FORMAT_USE_TEXTURE,
            PARAM_NAME_PARAMS, uniform.propertyName, FUNCTION_NAME_HANDLE, textureIndex
        )
        addStatement(
            STATEMENT_FORMAT_UNIFORM_TEXTURE,
            uniform.name, textureIndex
        )
        return this
    }

    private fun FunSpec.Builder.addStatement(uniform: UniformModel): FunSpec.Builder {
        addStatement(
            STATEMENT_FORMAT_UNIFORM,
            uniform.name, PARAM_NAME_PARAMS, uniform.propertyName
        )
        return this
    }

    private fun FunSpec.Builder.addEmptyLine() {
        addCode("\n")
    }
}
