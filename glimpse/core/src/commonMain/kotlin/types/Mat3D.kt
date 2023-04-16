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

internal data class Mat3D(override val elements: List<Double>) : Mat3<Double>() {

    override val field: Field<Double> get() = DoubleField

    init { validate() }

    override fun create(elements: List<Double>): Mat3D = Mat3D(elements)
    override fun createVector(elements: List<Double>): Vec3<Double> = Vec3.fromList(elements)

    override fun submatrix(withoutRow: Int, withoutCol: Int): Mat2<Double> =
        Mat2(
            indices.flatMap { col -> indices.map { row -> row to col } }
                .filter { (row, col) -> row != withoutRow && col != withoutCol }
                .map { (row, col) -> this[row, col] }
        )

    override fun toFloatMatrix(): Mat3<Float> = Mat3(elements.map { it.toFloat() })
    override fun toDoubleMatrix(): Mat3<Double> = this

    override fun toString(): String = toString(className = "Mat3")
}
