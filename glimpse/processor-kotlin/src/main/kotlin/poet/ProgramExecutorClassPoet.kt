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

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.TypeSpec
import graphics.glimpse.processor.poet.model.ShaderParamsModel
import graphics.glimpse.processor.poet.model.annotatedClassName
import graphics.glimpse.processor.poet.model.programExecutorClassName

object ProgramExecutorClassPoet {

    private const val CONSTRUCTOR_PARAM_NAME_PROGRAM = "program"

    private val baseProgramExecutorClassName =
        ClassName("graphics.glimpse.shaders", "BaseProgramExecutor")
    private val programClassName =
        ClassName("graphics.glimpse.shaders", "Program")

    fun generateRendererClass(model: ShaderParamsModel): TypeSpec =
        TypeSpec.classBuilder(model.programExecutorClassName)
            .primaryConstructor(generatePrimaryConstructor())
            .superclass(baseProgramExecutorClassName.parameterizedBy(model.annotatedClassName))
            .addSuperclassConstructorParameter("%N", CONSTRUCTOR_PARAM_NAME_PROGRAM)
            .addAnnotation(
                AnnotationSpec.builder(Suppress::class)
                    .addMember("%S, %S", "MaxLineLength", "MagicNumber")
                    .build()
            )
            .addFunction(ApplyParamsFunPoet.generateFun(model))
            .addFunction(DrawMeshFunPoet.generateFun(model))
            .addOriginatingElement(model.originatingElement)
            .build()

    private fun generatePrimaryConstructor(): FunSpec =
        FunSpec.constructorBuilder()
            .addParameter(CONSTRUCTOR_PARAM_NAME_PROGRAM, programClassName)
            .build()
}
