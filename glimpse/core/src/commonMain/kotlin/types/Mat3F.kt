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

internal data class Mat3F(override val elements: List<Float>) : Mat3<Float>() {

    override val field: Field<Float> get() = FloatField

    init { validate() }

    override fun create(elements: List<Float>): Mat3F = Mat3F(elements)
    override fun createVector(elements: List<Float>): Vec3<Float> = Vec3.fromList(elements)

    override fun submatrix(withoutRow: Int, withoutCol: Int): Mat2<Float> =
        Mat2(
            indices.flatMap { col -> indices.map { row -> row to col } }
                .filter { (row, col) -> row != withoutRow && col != withoutCol }
                .map { (row, col) -> this[row, col] }
        )

    override fun toFloatMatrix(): Mat3<Float> = this
    override fun toDoubleMatrix(): Mat3<Double> = Mat3(elements.map { it.toDouble() })

    override fun toString(): String = toString(className = "Mat3")
}
