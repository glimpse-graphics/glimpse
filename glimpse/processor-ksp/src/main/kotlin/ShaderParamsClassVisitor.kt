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

package graphics.glimpse.ksp

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSNode
import com.google.devtools.ksp.visitor.KSDefaultVisitor
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.TypeSpec
import graphics.glimpse.ksp.poet.addEmptyLine

/**
 * Visitor generating code of a `ProgramExecutor` from class annotated with `@ShaderParams`.
 */
class ShaderParamsClassVisitor : KSDefaultVisitor<CodeGenerator, Unit>() {

    override fun defaultHandler(node: KSNode, data: CodeGenerator) = Unit

    override fun visitClassDeclaration(classDeclaration: KSClassDeclaration, data: CodeGenerator) {
        super.visitClassDeclaration(classDeclaration, data)

        val classDeclarationFile = requireNotNull(classDeclaration.containingFile) { "Class declaration not in a file" }
        val packageName = classDeclaration.packageName.asString()
        val shaderParamsName = classDeclaration.simpleName.asString()
        val programExecutorName = shaderParamsName + PROGRAM_EXECUTOR_CLASS_NAME_SUFFIX

        data.createNewFile(
            dependencies = Dependencies(aggregating = false, classDeclarationFile),
            packageName = packageName,
            fileName = programExecutorName
        ).use { output ->
            val writer = output.bufferedWriter()
            FileSpec.builder(packageName, programExecutorName)
                .addFileComment(HEADER_COMMENT_FORMAT, classDeclaration.qualifiedName?.asString().orEmpty())
                .addType(
                    buildProgramExecutorTypeSpec(
                        packageName,
                        programExecutorName,
                        shaderParamsName,
                        classDeclaration
                    )
                )
                .build()
                .writeTo(writer)
            writer.flush()
            writer.close()
        }
    }

    private fun buildProgramExecutorTypeSpec(
        packageName: String,
        programExecutorName: String,
        shaderParamsName: String,
        classDeclaration: KSClassDeclaration
    ) =
        TypeSpec.classBuilder(ClassName(packageName, programExecutorName))
            .primaryConstructor(
                FunSpec.constructorBuilder()
                    .addParameter(CONSTRUCTOR_PARAM_NAME_PROGRAM, programClassName)
                    .build()
            )
            .superclass(baseProgramExecutorClassName.parameterizedBy(ClassName(packageName, shaderParamsName)))
            .addSuperclassConstructorParameter("%N", CONSTRUCTOR_PARAM_NAME_PROGRAM)
            .addAnnotation(
                AnnotationSpec.builder(Suppress::class)
                    .addMember("%S, %S", "MaxLineLength", "MagicNumber")
                    .build()
            )
            .addFunction(buildApplyParamsFunSpec(packageName, shaderParamsName, classDeclaration))
            .addFunction(buildDrawMeshFunSpec(classDeclaration))
            .build()

    private fun buildApplyParamsFunSpec(
        packageName: String,
        shaderParamsName: String,
        classDeclaration: KSClassDeclaration
    ) = FunSpec.builder(FUNCTION_NAME_APPLY_PARAMS)
        .addModifiers(KModifier.OVERRIDE)
        .addParameter(PARAM_NAME_GL_ADAPTER, glimpseAdapterClassName)
        .addParameter(PARAM_NAME_PARAMS, ClassName(packageName, shaderParamsName))
        .apply { classDeclaration.accept(UniformVisitor(), data = this) }
        .apply { classDeclaration.accept(Sampler2DVisitor(), data = this) }
        .build()

    private fun buildDrawMeshFunSpec(
        classDeclaration: KSClassDeclaration
    ) = FunSpec.builder(FUNCTION_NAME_DRAW_MESH)
        .addModifiers(KModifier.OVERRIDE)
        .addParameter(PARAM_NAME_GL_ADAPTER, glimpseAdapterClassName)
        .addParameter(PARAM_NAME_MESH, meshClassName)
        .apply { classDeclaration.accept(InitializeAttributesVisitor(), data = this) }
        .addStatement(STATEMENT_DRAW_ARRAYS)
        .addEmptyLine()
        .apply { classDeclaration.accept(FinalizeAttributesVisitor(), data = this) }
        .build()

    companion object {
        private const val HEADER_COMMENT_FORMAT = "Generated program executor for shader params: %L"
        private const val PROGRAM_EXECUTOR_CLASS_NAME_SUFFIX = "ProgramExecutor"
        private const val CONSTRUCTOR_PARAM_NAME_PROGRAM = "program"

        private const val FUNCTION_NAME_APPLY_PARAMS = "applyParams"
        private const val FUNCTION_NAME_DRAW_MESH = "drawMesh"
        private const val PARAM_NAME_GL_ADAPTER = "gl"
        private const val PARAM_NAME_PARAMS = "shaderParams"
        private const val PARAM_NAME_MESH = "mesh"

        private const val STATEMENT_DRAW_ARRAYS = "mesh.draw(gl)"

        private val baseProgramExecutorClassName =
            ClassName("graphics.glimpse.shaders", "BaseProgramExecutor")
        private val programClassName =
            ClassName("graphics.glimpse.shaders", "Program")

        private val glimpseAdapterClassName =
            ClassName("graphics.glimpse", "GlimpseAdapter")
        private val meshClassName =
            ClassName("graphics.glimpse.meshes", "Mesh")
    }
}
