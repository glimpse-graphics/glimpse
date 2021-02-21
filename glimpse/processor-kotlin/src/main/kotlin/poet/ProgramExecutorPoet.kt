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

import com.squareup.kotlinpoet.FileSpec
import graphics.glimpse.processor.poet.model.ShaderParamsModel
import graphics.glimpse.processor.poet.model.annotatedClassName
import graphics.glimpse.processor.poet.model.programExecutorClassName
import java.io.File

object ProgramExecutorPoet {

    private const val HEADER_COMMENT_FORMAT = "Generated program executor for shader params: %L"

    fun generateRenderer(model: ShaderParamsModel, outputDir: String) {
        FileSpec.builder(
            model.annotatedPackageName,
            model.programExecutorClassName.simpleName
        )
            .addComment(HEADER_COMMENT_FORMAT, model.annotatedClassName.canonicalName)
            .addType(ProgramExecutorClassPoet.generateRendererClass(model))
            .build()
            .writeTo(File(outputDir))
    }
}
