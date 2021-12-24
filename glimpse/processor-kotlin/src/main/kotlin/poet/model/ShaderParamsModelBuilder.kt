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

package graphics.glimpse.processor.poet.model

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.asTypeName
import javax.annotation.processing.Messager
import javax.lang.model.element.Element

class ShaderParamsModelBuilder(
    private val element: Element,
    classNameMatcher: ClassNameMatcher,
    messager: Messager
) : AbstractShaderParamsModelBuilder(classNameMatcher, messager) {

    override fun build(): ShaderParamsModel {
        @Suppress("EXPERIMENTAL_API_USAGE")
        val annotatedClassName = element.asType().asTypeName() as ClassName
        return ShaderParamsModel(
            originatingElement = element,
            annotatedPackageName = annotatedClassName.packageName,
            annotatedSimpleName = annotatedClassName.simpleName,
            uniforms = uniforms,
            attributes = attributes
        )
    }
}
