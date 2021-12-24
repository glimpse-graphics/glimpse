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
import com.google.devtools.ksp.symbol.KSValueArgument
import com.google.devtools.ksp.visitor.KSTopDownVisitor
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FunSpec

/**
 * Visitor adding `@Attribute`s to `drawMesh` method.
 */
abstract class AttributesVisitor : KSTopDownVisitor<FunSpec.Builder, FunSpec.Builder>() {

    final override fun defaultHandler(node: KSNode, data: FunSpec.Builder): FunSpec.Builder = data

    final override fun visitAnnotation(annotation: KSAnnotation, data: FunSpec.Builder): FunSpec.Builder {
        return super.visitAnnotation(annotation, data)
            .apply {
                if (annotation.isAttribute()) {
                    val arguments = annotation.arguments
                        .associate { argument -> argument.name?.asString().orEmpty() to argument.value }
                    addAttributeComment(arguments)
                    addAttributeStatements(Attribute(arguments))
                }
            }
    }

    private fun FunSpec.Builder.addAttributeComment(arguments: Map<String, Any?>): FunSpec.Builder {
        val argumentsString = arguments.asSequence()
            .joinToString { (name, value) -> "$name = $value" }
        addComment(COMMENT_FORMAT_ATTRIBUTE.format(argumentsString))
        return this
    }

    /**
     * Implement this method to add `drawMesh` method statements.
     */
    protected abstract fun FunSpec.Builder.addAttributeStatements(attribute: Attribute): FunSpec.Builder

    private fun KSAnnotation.isAttribute(): Boolean =
        shortName.asString() == ANNOTATION_NAME_ATTRIBUTE

    final override fun visitValueArgument(valueArgument: KSValueArgument, data: FunSpec.Builder): FunSpec.Builder {
        (valueArgument.value as? KSNode)?.accept(this, data)
        (valueArgument.value as? Iterable<*>)?.filterIsInstance<KSNode>()?.forEach { it.accept(this, data) }
        (valueArgument.value as? Array<*>)?.filterIsInstance<KSNode>()?.forEach { it.accept(this, data) }
        return super.visitValueArgument(valueArgument, data)
    }

    /**
     * Returns name of attribute location variable.
     */
    protected fun attributeLocation(attribute: Attribute): CodeBlock =
        CodeBlock.of(STATEMENT_FORMAT_ATTRIBUTE_LOCATION_VAL, attribute.name)

    /**
     * Attribute arguments wrapper.
     */
    protected class Attribute(arguments: Map<String, Any?>) {
        val name: String by arguments
        val role: Any by arguments
        val vectorSize: Int by arguments
    }

    companion object {
        private const val ANNOTATION_NAME_ATTRIBUTE = "Attribute"

        private const val COMMENT_FORMAT_ATTRIBUTE = "@Attribute(%s)"

        private const val STATEMENT_FORMAT_ATTRIBUTE_LOCATION_VAL = "%LLocation"
    }
}
