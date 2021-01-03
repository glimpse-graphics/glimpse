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

import com.squareup.javapoet.ClassName
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.ParameterizedTypeName
import com.squareup.javapoet.TypeSpec
import graphics.glimpse.processor.poet.model.ShaderParamsModel
import graphics.glimpse.processor.poet.model.annotatedClassName
import graphics.glimpse.processor.poet.model.programExecutorClassName
import javax.lang.model.element.Modifier

object ProgramExecutorClassPoet {

    private const val CONSTRUCTOR_PARAM_NAME_PROGRAM = "program"

    private val baseProgramExecutorClassName =
        ClassName.get("graphics.glimpse.shaders", "BaseProgramExecutor")
    private val programClassName =
        ClassName.get("graphics.glimpse.shaders", "Program")

    fun generateRendererClass(model: ShaderParamsModel): TypeSpec =
        TypeSpec.classBuilder(model.programExecutorClassName)
            .superclass(ParameterizedTypeName.get(baseProgramExecutorClassName, model.annotatedClassName))
            .addModifiers(Modifier.PUBLIC)
            .addMethod(generatePrimaryConstructor())
            .addMethod(ApplyParamsFunPoet.generateFun(model))
            .addMethod(DrawMeshFunPoet.generateFun(model))
            .build()

    private fun generatePrimaryConstructor(): MethodSpec =
        MethodSpec.constructorBuilder()
            .addModifiers(Modifier.PUBLIC)
            .addParameter(programClassName, CONSTRUCTOR_PARAM_NAME_PROGRAM)
            .addCode("super(\$N);", CONSTRUCTOR_PARAM_NAME_PROGRAM)
            .build()
}
