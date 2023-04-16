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

data class Mat4D(override val elements: List<Double>) : Mat4<Double>() {

    override val field: Field<Double> get() = DoubleField

    init { validate() }

    override fun create(elements: List<Double>): Mat4D = Mat4D(elements)
    override fun createVector(elements: List<Double>): Vec4<Double> = Vec4.fromList(elements)

    override fun submatrix(withoutRow: Int, withoutCol: Int): Mat3<Double> =
        Mat3(
            indices.flatMap { col -> indices.map { row -> row to col } }
                .filter { (row, col) -> row != withoutRow && col != withoutCol }
                .map { (row, col) -> this[row, col] }
        )

    override fun toFloatMatrix(): Mat4<Float> = Mat4(elements.map { it.toFloat() })
    override fun toDoubleMatrix(): Mat4<Double> = this

    override fun toString(): String = toString(className = "Mat4")
}
