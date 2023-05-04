/*
 * Copyright 2020-2023 Glimpse Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package graphics.glimpse.geom.freeform

import graphics.glimpse.logging.GlimpseLogger
import graphics.glimpse.types.Vec2
import kotlin.reflect.KClass

internal class Surface4BuilderImpl<T>(
    private val type: KClass<T>
) : Surface4.Builder<T> where T : Number, T : Comparable<T> {

    private val logger: GlimpseLogger = GlimpseLogger.create(this)

    private var freeformType: FreeformType? = null
    private var degree: Vec2<Int> = Vec2.nullVector()
    private var controlVertices: List<ControlVertex4<T>> = emptyList()
    private var knotsU: List<T> = emptyList()
    private var knotsV: List<T> = emptyList()

    override fun ofType(freeformType: FreeformType): Surface4.Builder<T> {
        this.freeformType = freeformType
        return this
    }

    override fun withDegree(degree: Vec2<Int>): Surface4.Builder<T> {
        this.degree = degree
        return this
    }

    override fun withControlVertices(controlVertices: List<ControlVertex4<T>>): Surface4.Builder<T> {
        this.controlVertices = controlVertices
        return this
    }

    override fun withControlVertices(vararg controlVertices: ControlVertex4<T>): Surface4.Builder<T> {
        this.controlVertices = controlVertices.toList()
        return this
    }

    override fun withKnotsU(knotsU: List<T>): Surface4.Builder<T> {
        this.knotsU = knotsU
        return this
    }

    override fun withKnotsU(vararg knotsU: T): Surface4.Builder<T> {
        this.knotsU = knotsU.toList()
        return this
    }

    override fun withKnotsV(knotsV: List<T>): Surface4.Builder<T> {
        this.knotsV = knotsV
        return this
    }

    override fun withKnotsV(vararg knotsV: T): Surface4.Builder<T> {
        this.knotsV = knotsV.toList()
        return this
    }

    override fun build(): Surface4<T> {
        if (freeformType != FreeformType.B_SPLINE && (knotsU + knotsV).isNotEmpty()) {
            logger.warn("Bezier surfaces do not require knots vectors")
        }

        requireNotNull(degree.u > 0) { "Surface degree must be greater than 0" }
        requireNotNull(degree.v > 0) { "Surface degree must be greater than 0" }

        return Surface4Impl(
            freeformType = requireNotNull(this.freeformType) { "Surface type must be specified" },
            degree = this.degree,
            controlVertices = this.controlVertices,
            knotsU = this.knotsU,
            knotsV = this.knotsV,
            type = this.type
        )
    }
}
