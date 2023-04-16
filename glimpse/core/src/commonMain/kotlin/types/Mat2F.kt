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

package graphics.glimpse.types

internal data class Mat2F(override val elements: List<Float>) : Mat2<Float>() {

    override val field: Field<Float> get() = FloatField

    init { validate() }

    override fun create(elements: List<Float>): Mat2F = Mat2F(elements)
    override fun createVector(elements: List<Float>): Vec2<Float> = Vec2.fromList(elements)

    override fun toFloatMatrix(): Mat2<Float> = this
    override fun toDoubleMatrix(): Mat2<Double> = Mat2(elements.map { it.toDouble() })

    override fun toString(): String = toString(className = "Mat2")
}
